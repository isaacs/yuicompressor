/*
** Cypress - CSS Parser
** Copyright (c) 2001, 2002, 2003 by Gerald Bauer
**
** This program is free software.
**
** You may redistribute it and/or modify it under the terms of the GNU
** Lesser General Public License as published by the Free Software Foundation.
** Version 2.1 of the license should be included with this distribution in
** the file LICENSE, as well as License.html. If the license is not
** included with this distribution, you may find a copy at the FSF web
** site at 'www.gnu.org' or 'www.fsf.org', or you may write to the
** Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139 USA.
**
** THIS SOFTWARE IS PROVIDED AS-IS WITHOUT WARRANTY OF ANY KIND,
** NOT EVEN THE IMPLIED WARRANTY OF MERCHANTABILITY. THE AUTHOR
** OF THIS SOFTWARE, ASSUMES _NO_ RESPONSIBILITY FOR ANY
** CONSEQUENCE RESULTING FROM THE USE, MODIFICATION, OR
** REDISTRIBUTION OF THIS SOFTWARE.
**
*/

package cypress;

import java.io.*;
import java.net.*;
import java.util.*;
import cypress.spi.*;

public class CssParser
{
   protected ConditionFactory _conditionFactory;

   /**
    *  The current token.
    */
   protected int _current;

   protected DocumentHandler _documentHandler;

   protected String _documentUri;
   protected ErrorHandler _errorHandler;
   /**
    *  The lexer used to break the input source into tokens.
    */
   protected CssLexer _lexer;

   protected SelectorFactory _selectorFactory;

   public void setConditionFactory( ConditionFactory factory )
   {
      _conditionFactory = factory;
   }

   public void setDocumentHandler( DocumentHandler handler )
   {
      _documentHandler = handler;
   }

   public void setErrorHandler( ErrorHandler handler )
   {
      _errorHandler = handler;
   }

   public void setSelectorFactory( SelectorFactory factory )
   {
      _selectorFactory = factory;
   }


   public String formatMessage( String key, Object[] args )
   {
      return key;
   }

   public CssValue parsePropertyValue( InputSource source )
          throws CssParseException, IOException
   {
      _lexer = new CssLexer( characterStream( source ) );
      return parsePropertyValueInternal();
   }

   public CssValue parsePropertyValue( String source )
          throws CssParseException, IOException
   {
      _lexer = new CssLexer( source );
      return parsePropertyValueInternal();
   }

   public void parseRule( InputSource source )
          throws CssParseException, IOException
   {
      _lexer = new CssLexer( characterStream( source ) );
      parseRuleInternal();
   }

   public void parseRule( String source )
          throws CssParseException, IOException
   {
      _lexer = new CssLexer( source );
      parseRuleInternal();
   }

   public List parseSelectors( InputSource source )
          throws CssParseException, IOException
   {
      _lexer = new CssLexer( characterStream( source ) );
      return parseSelectorsInternal();
   }

   public List parseSelectors( String source )
          throws CssParseException, IOException
   {
      _lexer = new CssLexer( source );
      return parseSelectorsInternal();
   }

   public void parseStyleDeclaration( InputSource source )
          throws CssParseException, IOException
   {
      _lexer = new CssLexer( characterStream( source ) );
      parseStyleDeclarationInternal();
   }

   public void parseStyleDeclaration( String source )
          throws CssParseException, IOException
   {
      _lexer = new CssLexer( source );
      parseStyleDeclarationInternal();
   }

   public void parseStyleSheet( InputSource source )
          throws CssParseException, IOException
   {
      _lexer = new CssLexer( characterStream( source ) );

      try
      {
         _documentHandler.startDocument( source );

         _current = _lexer.next();
         switch ( _current )
         {
            case Css.Token.COMMENT:
               _documentHandler.comment( _lexer.getStringValue() );
         }

         skipSpacesAndCDOCDC();

         loop :
         for( ; ;  )
         {
            switch ( _current )
            {
               case Css.Token.EOF:
                  break loop;
               default:
                  parseRuleSet();
            }
            skipSpacesAndCDOCDC();
         }
      }
      finally
      {
         _documentHandler.endDocument( source );
         _lexer = null;
      }
   }


   public void parseStyleSheet( String uri )
          throws CssParseException, IOException
   {
      parseStyleSheet( new InputSource( uri ) );
   }

   /**
    *  Converts the given input source into a Reader.
    */
   protected Reader characterStream( InputSource source ) throws CssParseException
   {
      Reader r = source.getCharacterStream();
      if( r == null )
      {
         InputStream is = source.getByteStream();
         if( is != null )
         {
            r = characterStream( source, is );
         }
         else
         {
            String uri = source.getUri();
            if( uri != null )
            {
               try
               {
                  URL url = new URL( uri );
                  is = url.openStream();
                  r = characterStream( source, is );
               }
               catch( IOException e )
               {
                  throw new CssParseException( e );
               }
            }
            else
            {
               throw new CssParseException( formatMessage( "empty.source", null ) );
            }
         }
      }
      return r;
   }

   /**
    *  Converts the given input stream into a Reader.
    */
   protected Reader characterStream( InputSource source, InputStream is )
   {
      _documentUri = source.getUri();
      if( _documentUri == null )
         _documentUri = "";

      return new InputStreamReader( is );
   }

   protected CssParseException createCssParseException( String key )
   {
      return createCssParseException( key, null );
   }

   protected CssParseException createCssParseException( String key, Object[] params )
   {
      /*
       *  return new CssParseException(formatMessage(key, params),
       *  _documentURI,
       *  _lexer.getLine(),
       *  _lexer.getColumn());
       */
      return new CssParseException( formatMessage( key, params ),
            _lexer.getLine(),
            _lexer.getColumn() );
   }

   /**
    *  Converts the current lexical unit to a dimension.
    */
   protected CssValue dimension( boolean positive, CssValue prev ) throws CssParseException
   {
      try
      {
         float sgn = ( positive ) ? 1 : -1;
         String val = _lexer.getStringValue();
         int i;
         loop :
         for( i = 0; i < val.length(); i++ )
         {
            switch ( val.charAt( i ) )
            {
               default:
                  break loop;
               case '0':
               case '1':
               case '2':
               case '3':
               case '4':
               case '5':
               case '6':
               case '7':
               case '8':
               case '9':
               case '.':
            }
         }
         nextIgnoreSpaces();
         return CssDimension.createDimension(
               ( sgn * Float.parseFloat( val.substring( 0, i ) ) ),
               val.substring( i ),
               prev );
      }
      catch( NumberFormatException e )
      {
         throw createCssParseException( "number.format" );
      }
   }

   /**
    *  Converts a hash unit to a RGB color.
    */
   protected CssValue hexcolor( CssValue prev ) throws CssParseException
   {
      String val = _lexer.getStringValue();
      int len = val.length();
      CssValue params = null;
      switch ( len )
      {
         case 3:
            char rc = Character.toLowerCase( val.charAt( 0 ) );
            char gc = Character.toLowerCase( val.charAt( 1 ) );
            char bc = Character.toLowerCase( val.charAt( 2 ) );
            if( !CssCharUtils.isHexadecimal( rc ) ||
                  !CssCharUtils.isHexadecimal( gc ) ||
                  !CssCharUtils.isHexadecimal( bc ) )
            {
               throw createCssParseException( "rgb.color", new Object[]{val} );
            }
            int t;
            int r = t = ( rc >= '0' && rc <= '9' ) ? rc - '0' : rc - 'a' + 10;
            t <<= 4;
            r |= t;
            int g = t = ( gc >= '0' && gc <= '9' ) ? gc - '0' : gc - 'a' + 10;
            t <<= 4;
            g |= t;
            int b = t = ( bc >= '0' && bc <= '9' ) ? bc - '0' : bc - 'a' + 10;
            t <<= 4;
            b |= t;
            params = CssInteger.createInteger( r, null );
            CssValue tmp;
            tmp = CssOperator.createComma( params );
            tmp = CssInteger.createInteger( g, tmp );
            tmp = CssOperator.createComma( tmp );
            tmp = CssInteger.createInteger( b, tmp );
            break;
         case 6:
            char rc1 = Character.toLowerCase( val.charAt( 0 ) );
            char rc2 = Character.toLowerCase( val.charAt( 1 ) );
            char gc1 = Character.toLowerCase( val.charAt( 2 ) );
            char gc2 = Character.toLowerCase( val.charAt( 3 ) );
            char bc1 = Character.toLowerCase( val.charAt( 4 ) );
            char bc2 = Character.toLowerCase( val.charAt( 5 ) );
            if( !CssCharUtils.isHexadecimal( rc1 ) ||
                  !CssCharUtils.isHexadecimal( rc2 ) ||
                  !CssCharUtils.isHexadecimal( gc1 ) ||
                  !CssCharUtils.isHexadecimal( gc2 ) ||
                  !CssCharUtils.isHexadecimal( bc1 ) ||
                  !CssCharUtils.isHexadecimal( bc2 ) )
            {
               throw createCssParseException( "rgb.color" );
            }
            r = ( rc1 >= '0' && rc1 <= '9' ) ? rc1 - '0' : rc1 - 'a' + 10;
            r <<= 4;
            r |= ( rc2 >= '0' && rc2 <= '9' ) ? rc2 - '0' : rc2 - 'a' + 10;
            g = ( gc1 >= '0' && gc1 <= '9' ) ? gc1 - '0' : gc1 - 'a' + 10;
            g <<= 4;
            g |= ( gc2 >= '0' && gc2 <= '9' ) ? gc2 - '0' : gc2 - 'a' + 10;
            b = ( bc1 >= '0' && bc1 <= '9' ) ? bc1 - '0' : bc1 - 'a' + 10;
            b <<= 4;
            b |= ( bc2 >= '0' && bc2 <= '9' ) ? bc2 - '0' : bc2 - 'a' + 10;
            params = CssInteger.createInteger( r, null );
            tmp = CssOperator.createComma( params );
            tmp = CssInteger.createInteger( g, tmp );
            tmp = CssOperator.createComma( tmp );
            tmp = CssInteger.createInteger( b, tmp );
            break;
         default:
            throw createCssParseException( "rgb.color", new Object[]{val} );
      }
      nextIgnoreSpaces();
      return CssBuiltInFunction.createRgb( params, prev );
   }

   /**
    *  Advances to the next token, ignoring comments.
    */
   protected int next()
   {
      try
      {
         for( ; ;  )
         {
            _lexer.clearBuffer();
            _current = _lexer.next();
            if( _current == Css.Token.COMMENT )
               _documentHandler.comment( _lexer.getStringValue() );
            else
               break;
         }
         return _current;
      }
      catch( CssParseException e )
      {
         reportError( e );
         return _current;
      }
   }

   /**
    *  Advances to the next token and skip the spaces, ignoring comments.
    */
   protected int nextIgnoreSpaces()
   {
      try
      {
         loop :
         for( ; ;  )
         {
            _lexer.clearBuffer();
            _current = _lexer.next();
            switch ( _current )
            {
               case Css.Token.COMMENT:
                  _documentHandler.comment( _lexer.getStringValue() );
                  break;
               default:
                  break loop;
               case Css.Token.SPACE:
            }
         }
         return _current;
      }
      catch( CssParseException e )
      {
         _errorHandler.error( createCssParseException( e.getMessage() ) );
         return _current;
      }
   }

   /**
    *  Converts the current lexical unit to a float.
    */
   protected float number( boolean positive ) throws CssParseException
   {
      try
      {
         float sgn = ( positive ) ? 1 : -1;
         String val = _lexer.getStringValue();
         nextIgnoreSpaces();
         return sgn * Float.parseFloat( val );
      }
      catch( NumberFormatException e )
      {
         throw createCssParseException( "number.format" );
      }
   }

   /**
    *  Parses a CSS2 expression.
    *
    *@param  lex  The type of the current lexical unit.
    */
   protected CssValue parseExpression( boolean param ) throws CssParseException
   {
      CssValue result = parseTerm( null );
      CssValue curr = result;

      for( ; ;  )
      {
         boolean op = false;
         switch ( _current )
         {
            case Css.Token.COMMA:
               op = true;
               curr = CssOperator.createComma( curr );
               nextIgnoreSpaces();
               break;
            case Css.Token.DIVIDE:
               op = true;
               curr = CssOperator.createSlash( curr );
               nextIgnoreSpaces();
         }
         if( param )
         {
            if( _current == Css.Token.RIGHT_BRACE )
            {
               if( op )
                  throw createCssParseException( "token", new Object[]{new Integer( _current )} );

               return result;
            }
            curr = parseTerm( curr );
         }
         else
         {
            switch ( _current )
            {
               case Css.Token.IMPORTANT_SYMBOL:
               case Css.Token.SEMI_COLON:
               case Css.Token.RIGHT_CURLY_BRACE:
               case Css.Token.EOF:
                  if( op )
                     throw createCssParseException( "token", new Object[]{new Integer( _current )} );

                  return result;
               default:
                  curr = parseTerm( curr );
            }
         }
      }
   }

   /**
    *  Parses a CSS2 function.
    */
   protected CssValue parseFunction( boolean positive, CssValue prev ) throws CssParseException
   {
      String name = _lexer.getStringValue();
      nextIgnoreSpaces();

      CssValue params = parseExpression( true );

      if( _current != Css.Token.RIGHT_BRACE )
         throw createCssParseException( "token", new Object[]{new Integer( _current )} );

      nextIgnoreSpaces();

      predefined :
      switch ( name.charAt( 0 ) )
      {
         case 'r':
         case 'R':
            CssValue lu;
            if( name.equalsIgnoreCase( "rgb" ) )
            {
               lu = params;
               if( lu == null )
                  break;

               switch ( lu.getType() )
               {
                  default:
                     break predefined;
                  case CssValue.INTEGER:
                  case CssValue.PERCENTAGE:
                     lu = lu.getNext();
               }
               if( lu == null )
                  break;

               switch ( lu.getType() )
               {
                  default:
                     break predefined;
                  case CssValue.OPERATOR_COMMA:
                     lu = lu.getNext();
               }
               if( lu == null )
                  break;

               switch ( lu.getType() )
               {
                  default:
                     break predefined;
                  case CssValue.INTEGER:
                  case CssValue.PERCENTAGE:
                     lu = lu.getNext();
               }
               if( lu == null )
                  break;

               switch ( lu.getType() )
               {
                  default:
                     break predefined;
                  case CssValue.OPERATOR_COMMA:
                     lu = lu.getNext();
               }
               if( lu == null )
                  break;

               switch ( lu.getType() )
               {
                  default:
                     break predefined;
                  case CssValue.INTEGER:
                  case CssValue.PERCENTAGE:
                     lu = lu.getNext();
               }
               if( lu != null )
                  break;

               return CssBuiltInFunction.createRgb( params, prev );
            }
      }

      return CssUserFunction.createFunction( name, params, prev );
   }

   /**
    *  Parses property value using the current scanner.
    */
   protected CssValue parsePropertyValueInternal()
          throws CssParseException, IOException
   {
      nextIgnoreSpaces();

      CssValue exp = null;

      try
      {
         exp = parseExpression( false );
      }
      catch( CssParseException e )
      {
         reportError( e );
         throw e;
      }

      _lexer = null;

      if( _current != Css.Token.EOF )
         _errorHandler.fatal( createCssParseException( "eof.expected" ) );

      return exp;
   }

   /**
    *  Parses a rule.
    */
   protected void parseRule()
   {
      parseRuleSet();
   }

   /**
    *  Parses a rule using the current scanner.
    */
   protected void parseRuleInternal()
          throws CssParseException, IOException
   {
      nextIgnoreSpaces();
      parseRule();
      _lexer = null;
   }

   /**
    *  Parses a ruleset.
    */
   protected void parseRuleSet()
   {
      List sl = null;

      try
      {
         sl = parseSelectorList();
      }
      catch( CssParseException e )
      {
         reportError( e );
         return;
      }

      try
      {
         _documentHandler.startSelector( sl );

         if( _current != Css.Token.LEFT_CURLY_BRACE )
         {
            reportError( "left.curly.brace" );
            if( _current == Css.Token.RIGHT_CURLY_BRACE )
               nextIgnoreSpaces();

         }
         else
         {
            nextIgnoreSpaces();

            try
            {
               parseStyleDeclaration( true );
            }
            catch( CssParseException e )
            {
               reportError( e );
            }
         }
      }
      finally
      {
         _documentHandler.endSelector( sl );
      }
   }

   /**
    *  Parses a selector.
    */
   protected Selector parseSelector() throws CssParseException
   {
      return parseSimpleSelector();
   }

   /**
    *  Parses a selector list
    */
   protected List parseSelectorList() throws CssParseException
   {
      ArrayList selectors = new ArrayList();
      selectors.add( parseSelector() );

      for( ; ;  )
      {
         if( _current != Css.Token.COMMA )
            return selectors;

         nextIgnoreSpaces();
         selectors.add( parseSelector() );
      }
   }

   /**
    *  Parses selectors using the current scanner.
    */
   protected List parseSelectorsInternal()
          throws CssParseException, IOException
   {
      nextIgnoreSpaces();
      List ret = parseSelectorList();
      _lexer = null;
      return ret;
   }

   /**
    *  Parses a simple selector.
    */
   protected Selector parseSimpleSelector() throws CssParseException
   {
      Selector result;

      switch ( _current )
      {
         case Css.Token.IDENTIFIER:
            result = _selectorFactory.createElementSelector( _lexer.getStringValue() );
            next();
            break;
         case Css.Token.ANY:
            next();
         // fall through to default branch; no break
         default:
            result = _selectorFactory.createAnySelector();
      }

      Condition cond = null;

      switch ( _current )
      {
         case Css.Token.HASH:
            cond = _conditionFactory.createIdCondition( _lexer.getStringValue() );
            next();
            break;
         case Css.Token.DOT:
            if( next() != Css.Token.IDENTIFIER )
               throw createCssParseException( "identifier" );

            cond = _conditionFactory.createClassCondition( _lexer.getStringValue() );
            next();
            break;
      }

      skipSpaces();

      if( cond != null )
         result = _selectorFactory.createConditionalSelector( result, cond );

      return result;
   }

   /**
    *  Parses the given reader.
    */
   protected void parseStyleDeclaration( boolean inSheet )
          throws CssParseException
   {
      for( ; ;  )
      {
         switch ( _current )
         {
            case Css.Token.EOF:
               if( inSheet )
                  throw createCssParseException( "eof" );
               return;
            case Css.Token.RIGHT_CURLY_BRACE:
               if( !inSheet )
                  throw createCssParseException( "eof.expected" );
               nextIgnoreSpaces();
               return;
            case Css.Token.SEMI_COLON:
               nextIgnoreSpaces();
               continue;
            default:
               throw createCssParseException( "identifier" );
            case Css.Token.IDENTIFIER:
         }

         String name = _lexer.getStringValue();

         if( nextIgnoreSpaces() != Css.Token.COLON )
            throw createCssParseException( "colon" );

         nextIgnoreSpaces();

         CssValue exp = null;

         try
         {
            exp = parseExpression( false );
         }
         catch( CssParseException e )
         {
            reportError( e );
         }

         if( exp != null )
         {
            boolean important = false;
            if( _current == Css.Token.IMPORTANT_SYMBOL )
            {
               important = true;
               nextIgnoreSpaces();
            }
            _documentHandler.property( name, exp, important );
         }
      }
   }

   /**
    *  Parses a style declaration using the current scanner.
    */
   protected void parseStyleDeclarationInternal()
          throws CssParseException, IOException
   {
      nextIgnoreSpaces();
      try
      {
         parseStyleDeclaration( false );
      }
      catch( CssParseException e )
      {
         reportError( e );
      }
      finally
      {
         _lexer = null;
      }
   }

   /**
    *  Parses a CSS2 term.
    */
   protected CssValue parseTerm( CssValue prev ) throws CssParseException
   {
      boolean plus = true;
      boolean sgn = false;

      switch ( _current )
      {
         case Css.Token.MINUS:
            plus = false;
         case Css.Token.PLUS:
            next();
            sgn = true;
         default:
            switch ( _current )
            {
               case Css.Token.INTEGER:
                  int s = ( plus ) ? 1 : -1;
                  int val = s * Integer.parseInt( _lexer.getStringValue() );
                  nextIgnoreSpaces();
                  return CssInteger.createInteger( val, prev );
               case Css.Token.REAL:
                  return CssFloat.createReal( number( plus ), prev );
               case Css.Token.PERCENTAGE:
                  return CssFloat.createPercentage( number( plus ), prev );
               case Css.Token.PT:
                  return CssFloat.createPoint( number( plus ), prev );
               case Css.Token.PC:
                  return CssFloat.createPica( number( plus ), prev );
               case Css.Token.PX:
                  return CssFloat.createPixel( number( plus ), prev );
               case Css.Token.CM:
                  return CssFloat.createCentimeter( number( plus ), prev );
               case Css.Token.MM:
                  return CssFloat.createMillimeter( number( plus ), prev );
               case Css.Token.IN:
                  return CssFloat.createInch( number( plus ), prev );
               case Css.Token.EM:
                  return CssFloat.createEm( number( plus ), prev );
               case Css.Token.EX:
                  return CssFloat.createEx( number( plus ), prev );
               case Css.Token.DIMENSION:
                  return dimension( plus, prev );
               case Css.Token.FUNCTION:
                  return parseFunction( plus, prev );
            }
            if( sgn )
               throw createCssParseException( "token", new Object[]{new Integer( _current )} );
      }
      switch ( _current )
      {
         case Css.Token.STRING:
            String val = _lexer.getStringValue();
            nextIgnoreSpaces();
            return CssString.createString( val, prev );
         case Css.Token.IDENTIFIER:
            val = _lexer.getStringValue();
            nextIgnoreSpaces();

            if( val.equalsIgnoreCase( "inherit" ) )
               return CssOperator.createInherit( prev );
            else
               return CssString.createIdent( val, prev );
         case Css.Token.URI:
            val = _lexer.getStringValue();
            nextIgnoreSpaces();
            return CssString.createUri( val, prev );
         case Css.Token.HASH:
            return hexcolor( prev );
         default:
            throw createCssParseException( "token", new Object[]{new Integer( _current )} );
      }
   }

   /**
    *  Reports a parsing error.
    */
   protected void reportError( String key )
   {
      reportError( key, null );
   }

   /**
    *  Reports a parsing error.
    */
   protected void reportError( String key, Object[] params )
   {
      reportError( createCssParseException( key, params ) );
   }

   /**
    *  Reports a parsing error.
    */
   protected void reportError( CssParseException e )
   {
      _errorHandler.error( e );

      int cbraces = 1;
      for( ; ;  )
      {
         switch ( _current )
         {
            case Css.Token.EOF:
               return;
            case Css.Token.SEMI_COLON:
            case Css.Token.RIGHT_CURLY_BRACE:
               if( --cbraces == 0 )
               {
                  nextIgnoreSpaces();
                  return;
               }
            case Css.Token.LEFT_CURLY_BRACE:
               cbraces++;
         }
         nextIgnoreSpaces();
      }
   }

   /**
    *  Skips the white spaces.
    */
   protected int skipSpaces()
   {
      int lex = _lexer.getType();
      while( lex == Css.Token.SPACE )
      {
         lex = next();
      }
      return lex;
   }

   /**
    *  Skips the white spaces and CDO/CDC untis.
    */
   protected int skipSpacesAndCDOCDC()
   {
      loop :
      for( ; ;  )
      {
         switch ( _current )
         {
            default:
               break loop;
            case Css.Token.COMMENT:
            case Css.Token.SPACE:
            case Css.Token.CDO:
            case Css.Token.CDC:
         }
         _lexer.clearBuffer();
         next();
      }
      return _current;
   }
}
