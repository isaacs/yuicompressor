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

import java.io.IOException;
import java.io.Reader;

/**
 *  CSS scanner - an object which decodes CSS lexical units.
 */
public class CssLexer
{

   /**
    *  The characters to skip to create the string which represents the current
    *  token.
    */
   protected int _blankCharacters;

   /**
    *  The recording buffer.
    */
   protected char[] _buffer = new char[128];

   /**
    *  The current column.
    */
   protected int _column;

   /**
    *  The current char.
    */
   protected int _current;

   /**
    *  The end offset of the last lexical unit.
    */
   protected int _end;

   /**
    *  The current line.
    */
   protected int _line = 1;

   /**
    *  The current position in the buffer.
    */
   protected int _position;

   /**
    *  The reading buffer.
    */
   protected char[] _readBuffer;

   /**
    *  The current read buffer count.
    */
   protected int _readCount;

   /**
    *  The current position in the read buffer.
    */
   protected int _readPosition;

   protected Reader _reader;

   /**
    *  The start offset of the last lexical unit.
    */
   protected int _start;

   /**
    *  The type of the current lexical unit.
    */
   protected int _type;

   /**
    *  Creates a new Scanner object.
    *
    *@param  r                      The reader to scan.
    *@exception  CssParseException  Description of the Exception
    */
   public CssLexer( Reader r ) throws CssParseException
   {
      try
      {
         _reader = r;
         _readBuffer = new char[4096];
         _current = nextChar();
      }
      catch( IOException e )
      {
         throw new CssParseException( e );
      }
   }

   /**
    *  Creates a new Scanner object.
    *
    *@param  r                      The reader to scan.
    *@param  s                      Description of the Parameter
    *@exception  CssParseException  Description of the Exception
    */
   public CssLexer( String s ) throws CssParseException
   {
      try
      {
         _reader = null;
         _readBuffer = s.toCharArray();
         _readPosition = 0;
         _readCount = _readBuffer.length;
         collapseCRNL( 0 );
         if( _readCount == 0 )
            _current = -1;
         else
            _current = nextChar();
      }
      catch( IOException e )
      {
         throw new CssParseException( e );
      }
   }

   /**
    *  Returns the buffer used to store the chars.
    */
   public char[] getBuffer()
   {
      return _buffer;
   }

   /**
    *  Returns the current column.
    */
   public int getColumn()
   {
      return _column;
   }

   /**
    *  Returns the end offset of the last lexical unit.
    */
   public int getEnd()
   {
      return _end;
   }

   /**
    *  Returns the current line.
    */
   public int getLine()
   {
      return _line;
   }

   /**
    *  Returns the start offset of the last lexical unit.
    */
   public int getStart()
   {
      return _start;
   }

   /**
    *  Returns the string representation of the current lexical unit.
    */
   public String getStringValue()
   {
      return new String( _buffer, _start, _end - _start );
   }

   /**
    *  The current lexical unit type like defined in Css.Token
    */
   public int getType()
   {
      return _type;
   }

   /**
    *  Compares the given int with the given character, ignoring case.
    */
   protected static boolean isEqualIgnoreCase( int i, char c )
   {
      return ( i == -1 ) ? false : Character.toLowerCase( ( char ) i ) == c;
   }

   /**
    *  Clears the buffer.
    */
   public void clearBuffer()
   {
      if( _position <= 0 )
         _position = 0;
      else
      {
         _buffer[0] = _buffer[_position - 1];
         _position = 1;
      }
   }

   /**
    *  Returns the next token.
    */
   public int next() throws CssParseException
   {
      _blankCharacters = 0;
      _start = _position - 1;
      nextToken();
      _end = _position - endGap();
      return _type;
   }

   /**
    *  Scans the decimal part of a number.
    */
   protected int dotNumber() throws IOException
   {
      loop :
      for( ; ;  )
      {
         switch ( nextChar() )
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
         }
      }
      return numberUnit( false );
   }

   /**
    *  Returns the end gap of the current lexical unit.
    */
   protected int endGap()
   {
      int result = ( _current == -1 ) ? 0 : 1;
      switch ( _type )
      {
         case Css.Token.FUNCTION:
         case Css.Token.STRING:
         case Css.Token.S:
         case Css.Token.PERCENTAGE:
            result += 1;
            break;
         case Css.Token.COMMENT:
         case Css.Token.HZ:
         case Css.Token.EM:
         case Css.Token.EX:
         case Css.Token.PC:
         case Css.Token.PT:
         case Css.Token.PX:
         case Css.Token.CM:
         case Css.Token.MM:
         case Css.Token.IN:
         case Css.Token.MS:
            result += 2;
            break;
         case Css.Token.KHZ:
         case Css.Token.DEG:
         case Css.Token.RAD:
            result += 3;
            break;
         case Css.Token.GRAD:
            result += 4;
      }
      return result + _blankCharacters;
   }

   /**
    *  Scans an escape sequence, if one.
    */
   protected void escape() throws IOException
   {
      if( CssCharUtils.isHexadecimal( ( char ) _current ) )
      {
         nextChar();
         if( !CssCharUtils.isHexadecimal( ( char ) _current ) )
         {
            if( CssCharUtils.isSpace( ( char ) _current ) )
               nextChar();
            return;
         }
         nextChar();
         if( !CssCharUtils.isHexadecimal( ( char ) _current ) )
         {
            if( CssCharUtils.isSpace( ( char ) _current ) )
               nextChar();
            return;
         }
         nextChar();
         if( !CssCharUtils.isHexadecimal( ( char ) _current ) )
         {
            if( CssCharUtils.isSpace( ( char ) _current ) )
               nextChar();
            return;
         }
         nextChar();
         if( !CssCharUtils.isHexadecimal( ( char ) _current ) )
         {
            if( CssCharUtils.isSpace( ( char ) _current ) )
               nextChar();
            return;
         }
         nextChar();
         if( !CssCharUtils.isHexadecimal( ( char ) _current ) )
         {
            if( CssCharUtils.isSpace( ( char ) _current ) )
               nextChar();
            return;
         }
      }
      if( ( _current >= ' ' && _current <= '~' ) || _current >= 128 )
      {
         nextChar();
         return;
      }
      throw new CssParseException( "character", _line, _column );
   }

   /**
    *  Sets the value of the current char to the next character or -1 if the end
    *  of stream has been reached.
    */
   protected int nextChar() throws IOException
   {
      if( _readPosition == _readCount && !fillReadBuffer() )
         return _current = -1;

      if( _current != 10 )
         _column++;
      else
      {
         _line++;
         _column = 1;
      }

      _current = _readBuffer[_readPosition++];

      if( _position == _buffer.length )
      {
         char[] t = new char[_position * 3 / 2];
         System.arraycopy( _buffer, 0, t, 0, _position );
         _buffer = t;
      }

      return _buffer[_position++] = ( char ) _current;
   }

   /**
    *  Returns the next token.
    */
   protected void nextToken() throws CssParseException
   {
      try
      {
         switch ( _current )
         {
            case -1:
               _type = Css.Token.EOF;
               return;
            case '{':
               nextChar();
               _type = Css.Token.LEFT_CURLY_BRACE;
               return;
            case '}':
               nextChar();
               _type = Css.Token.RIGHT_CURLY_BRACE;
               return;
            case '=':
               nextChar();
               _type = Css.Token.EQUAL;
               return;
            case '+':
               nextChar();
               _type = Css.Token.PLUS;
               return;
            case ',':
               nextChar();
               _type = Css.Token.COMMA;
               return;
            case ';':
               nextChar();
               _type = Css.Token.SEMI_COLON;
               return;
            case '>':
               nextChar();
               _type = Css.Token.PRECEDE;
               return;
            case '[':
               nextChar();
               _type = Css.Token.LEFT_BRACKET;
               return;
            case ']':
               nextChar();
               _type = Css.Token.RIGHT_BRACKET;
               return;
            case '*':
               nextChar();
               _type = Css.Token.ANY;
               return;
            case '(':
               nextChar();
               _type = Css.Token.LEFT_BRACE;
               return;
            case ')':
               nextChar();
               _type = Css.Token.RIGHT_BRACE;
               return;
            case ':':
               nextChar();
               _type = Css.Token.COLON;
               return;
            case ' ':
            case '\t':
            case '\r':
            case '\n':
            case '\f':
               do
               {
                  nextChar();
               }while ( CssCharUtils.isSpace( ( char ) _current ) );
               _type = Css.Token.SPACE;
               return;
            case '/':
               nextChar();
               if( _current != '*' )
               {
                  _type = Css.Token.DIVIDE;
                  return;
               }
               // Comment
               nextChar();
               _start = _position - 1;
               do
               {
                  while( _current != -1 && _current != '*' )
                  {
                     nextChar();
                  }

                  do
                  {
                     nextChar();
                  }while ( _current != -1 && _current == '*' );
               }while ( _current != -1 && _current != '/' );

               if( _current == -1 )
                  throw new CssParseException( "eof", _line, _column );
               nextChar();
               _type = Css.Token.COMMENT;
               return;
            case '\'':
               // String1
               _type = string1();
               return;
            case '"':
               // String2
               _type = string2();
               return;
            case '<':
               nextChar();
               if( _current != '!' )
                  throw new CssParseException( "character", _line, _column );
               nextChar();
               if( _current == '-' )
               {
                  nextChar();
                  if( _current == '-' )
                  {
                     nextChar();
                     _type = Css.Token.CDO;
                     return;
                  }
               }
               throw new CssParseException( "character", _line, _column );
            case '-':
               nextChar();
               if( _current != '-' )
               {
                  _type = Css.Token.MINUS;
                  return;
               }
               nextChar();
               if( _current == '>' )
               {
                  nextChar();
                  _type = Css.Token.CDC;
                  return;
               }
               throw new CssParseException( "character", _line, _column );
            case '|':
               nextChar();
               if( _current == '=' )
               {
                  nextChar();
                  _type = Css.Token.DASHMATCH;
                  return;
               }
               throw new CssParseException( "character", _line, _column );
            case '~':
               nextChar();
               if( _current == '=' )
               {
                  nextChar();
                  _type = Css.Token.INCLUDES;
                  return;
               }
               throw new CssParseException( "character", _line, _column );
            case '#':
               nextChar();
               if( CssCharUtils.isName( ( char ) _current ) )
               {
                  _start = _position - 1;
                  do
                  {
                     nextChar();
                     if( _current == '\\' )
                     {
                        nextChar();
                        escape();
                     }
                  }while ( _current != -1 &&
                        CssCharUtils.isName( ( char ) _current ) );
                  _type = Css.Token.HASH;
                  return;
               }
               throw new CssParseException( "character", _line, _column );
            case '!':
               do
               {
                  nextChar();
               }while ( _current != -1 && CssCharUtils.isSpace( ( char ) _current ) );
               if( isEqualIgnoreCase( _current, 'i' ) &&
                     isEqualIgnoreCase( nextChar(), 'm' ) &&
                     isEqualIgnoreCase( nextChar(), 'p' ) &&
                     isEqualIgnoreCase( nextChar(), 'o' ) &&
                     isEqualIgnoreCase( nextChar(), 'r' ) &&
                     isEqualIgnoreCase( nextChar(), 't' ) &&
                     isEqualIgnoreCase( nextChar(), 'a' ) &&
                     isEqualIgnoreCase( nextChar(), 'n' ) &&
                     isEqualIgnoreCase( nextChar(), 't' ) )
               {
                  nextChar();
                  _type = Css.Token.IMPORTANT_SYMBOL;
                  return;
               }
               if( _current == -1 )
                  throw new CssParseException( "eof", _line, _column );
               else
                  throw new CssParseException( "character", _line, _column );
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
               _type = number();
               return;
            case '.':
               switch ( nextChar() )
               {
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
                     _type = dotNumber();
                     return;
                  default:
                     _type = Css.Token.DOT;
                     return;
               }
            case 'u':
            case 'U':
               nextChar();
               switch ( _current )
               {
                  case 'r':
                  case 'R':
                     nextChar();
                     switch ( _current )
                     {
                        case 'l':
                        case 'L':
                           nextChar();
                           switch ( _current )
                           {
                              case '(':
                                 do
                                 {
                                    nextChar();
                                 }while ( _current != -1 &&
                                       CssCharUtils.isSpace( ( char ) _current ) );
                                 switch ( _current )
                                 {
                                    case '\'':
                                       string1();
                                       _blankCharacters += 2;
                                       while( _current != -1 &&
                                             CssCharUtils.isSpace( ( char ) _current ) )
                                       {
                                          _blankCharacters++;
                                          nextChar();
                                       }
                                       if( _current == -1 )
                                       {
                                          throw new CssParseException( "eof", _line, _column );
                                       }
                                       if( _current != ')' )
                                       {
                                          throw new CssParseException( "character", _line, _column );
                                       }
                                       nextChar();
                                       _type = Css.Token.URI;
                                       return;
                                    case '"':
                                       string2();
                                       _blankCharacters += 2;
                                       while( _current != -1 &&
                                             CssCharUtils.isSpace( ( char ) _current ) )
                                       {
                                          _blankCharacters++;
                                          nextChar();
                                       }
                                       if( _current == -1 )
                                          throw new CssParseException( "eof", _line, _column );

                                       if( _current != ')' )
                                          throw new CssParseException( "character", _line, _column );

                                       nextChar();
                                       _type = Css.Token.URI;
                                       return;
                                    case ')':
                                       throw new CssParseException( "character", _line, _column );
                                    default:
                                       if( !CssCharUtils.isUri( ( char ) _current ) )
                                          throw new CssParseException( "character", _line, _column );
                                       _start = _position - 1;
                                       do
                                       {
                                          nextChar();
                                       }while ( _current != -1 &&
                                             CssCharUtils.isUri( ( char ) _current ) );
                                       _blankCharacters++;
                                       while( _current != -1 &&
                                             CssCharUtils.isSpace( ( char ) _current ) )
                                       {
                                          _blankCharacters++;
                                          nextChar();
                                       }
                                       if( _current == -1 )
                                          throw new CssParseException( "eof", _line, _column );

                                       if( _current != ')' )
                                          throw new CssParseException( "character", _line, _column );

                                       nextChar();
                                       _type = Css.Token.URI;
                                       return;
                                 }
                           }
                     }
               }
               while( _current != -1 &&
                     CssCharUtils.isName( ( char ) _current ) )
               {
                  nextChar();
               }

               if( _current == '(' )
               {
                  nextChar();
                  _type = Css.Token.FUNCTION;
                  return;
               }
               _type = Css.Token.IDENTIFIER;
               return;
            default:
               if( CssCharUtils.isIdentifierStart( ( char ) _current ) )
               {
                  // Identifier
                  do
                  {
                     nextChar();
                     if( _current == '\\' )
                     {
                        nextChar();
                        escape();
                     }
                  }while ( _current != -1 &&
                        CssCharUtils.isName( ( char ) _current ) );
                  if( _current == '(' )
                  {
                     nextChar();
                     _type = Css.Token.FUNCTION;
                     return;
                  }
                  _type = Css.Token.IDENTIFIER;
                  return;
               }
               nextChar();
               throw new CssParseException( "character", _line, _column );
         }
      }
      catch( IOException e )
      {
         throw new CssParseException( e );
      }
   }

   /**
    *  Scans a number.
    */
   protected int number() throws IOException
   {
      loop :
      for( ; ;  )
      {
         switch ( nextChar() )
         {
            case '.':
               switch ( nextChar() )
               {
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
                     return dotNumber();
               }
               throw new CssParseException( "character", _line, _column );
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
         }
      }
      return numberUnit( true );
   }

   /**
    *  Scans the unit of a number.
    */
   protected int numberUnit( boolean integer ) throws IOException
   {
      switch ( _current )
      {
         case '%':
            nextChar();
            return Css.Token.PERCENTAGE;
         case 'c':
         case 'C':
            switch ( nextChar() )
            {
               case 'm':
               case 'M':
                  nextChar();
                  if( _current != -1 &&
                        CssCharUtils.isName( ( char ) _current ) )
                  {
                     do
                     {
                        nextChar();
                     }while ( _current != -1 &&
                           CssCharUtils.isName( ( char ) _current ) );
                     return Css.Token.DIMENSION;
                  }
                  return Css.Token.CM;
               default:
                  while( _current != -1 &&
                        CssCharUtils.isName( ( char ) _current ) )
                  {
                     nextChar();
                  }
                  return Css.Token.DIMENSION;
            }
         case 'd':
         case 'D':
            switch ( nextChar() )
            {
               case 'e':
               case 'E':
                  switch ( nextChar() )
                  {
                     case 'g':
                     case 'G':
                        nextChar();
                        if( _current != -1 &&
                              CssCharUtils.isName( ( char ) _current ) )
                        {
                           do
                           {
                              nextChar();
                           }while ( _current != -1 &&
                                 CssCharUtils.isName( ( char ) _current ) );
                           return Css.Token.DIMENSION;
                        }
                        return Css.Token.DEG;
                  }
               default:
                  while( _current != -1 &&
                        CssCharUtils.isName( ( char ) _current ) )
                  {
                     nextChar();
                  }
                  return Css.Token.DIMENSION;
            }
         case 'e':
         case 'E':
            switch ( nextChar() )
            {
               case 'm':
               case 'M':
                  nextChar();
                  if( _current != -1 &&
                        CssCharUtils.isName( ( char ) _current ) )
                  {
                     do
                     {
                        nextChar();
                     }while ( _current != -1 &&
                           CssCharUtils.isName( ( char ) _current ) );
                     return Css.Token.DIMENSION;
                  }
                  return Css.Token.EM;
               case 'x':
               case 'X':
                  nextChar();
                  if( _current != -1 &&
                        CssCharUtils.isName( ( char ) _current ) )
                  {
                     do
                     {
                        nextChar();
                     }while ( _current != -1 &&
                           CssCharUtils.isName( ( char ) _current ) );
                     return Css.Token.DIMENSION;
                  }
                  return Css.Token.EX;
               default:
                  while( _current != -1 &&
                        CssCharUtils.isName( ( char ) _current ) )
                  {
                     nextChar();
                  }
                  return Css.Token.DIMENSION;
            }
         case 'g':
         case 'G':
            switch ( nextChar() )
            {
               case 'r':
               case 'R':
                  switch ( nextChar() )
                  {
                     case 'a':
                     case 'A':
                        switch ( nextChar() )
                        {
                           case 'd':
                           case 'D':
                              nextChar();
                              if( _current != -1 &&
                                    CssCharUtils.isName( ( char ) _current ) )
                              {
                                 do
                                 {
                                    nextChar();
                                 }while ( _current != -1 &&
                                       CssCharUtils.isName( ( char ) _current ) );
                                 return Css.Token.DIMENSION;
                              }
                              return Css.Token.GRAD;
                        }
                  }
               default:
                  while( _current != -1 &&
                        CssCharUtils.isName( ( char ) _current ) )
                  {
                     nextChar();
                  }
                  return Css.Token.DIMENSION;
            }
         case 'h':
         case 'H':
            nextChar();
            switch ( _current )
            {
               case 'z':
               case 'Z':
                  nextChar();
                  if( _current != -1 &&
                        CssCharUtils.isName( ( char ) _current ) )
                  {
                     do
                     {
                        nextChar();
                     }while ( _current != -1 &&
                           CssCharUtils.isName( ( char ) _current ) );
                     return Css.Token.DIMENSION;
                  }
                  return Css.Token.HZ;
               default:
                  while( _current != -1 &&
                        CssCharUtils.isName( ( char ) _current ) )
                  {
                     nextChar();
                  }
                  return Css.Token.DIMENSION;
            }
         case 'i':
         case 'I':
            switch ( nextChar() )
            {
               case 'n':
               case 'N':
                  nextChar();
                  if( _current != -1 &&
                        CssCharUtils.isName( ( char ) _current ) )
                  {
                     do
                     {
                        nextChar();
                     }while ( _current != -1 &&
                           CssCharUtils.isName( ( char ) _current ) );
                     return Css.Token.DIMENSION;
                  }
                  return Css.Token.IN;
               default:
                  while( _current != -1 &&
                        CssCharUtils.isName( ( char ) _current ) )
                  {
                     nextChar();
                  }
                  return Css.Token.DIMENSION;
            }
         case 'k':
         case 'K':
            switch ( nextChar() )
            {
               case 'h':
               case 'H':
                  switch ( nextChar() )
                  {
                     case 'z':
                     case 'Z':
                        nextChar();
                        if( _current != -1 &&
                              CssCharUtils.isName( ( char ) _current ) )
                        {
                           do
                           {
                              nextChar();
                           }while ( _current != -1 &&
                                 CssCharUtils.isName( ( char ) _current ) );
                           return Css.Token.DIMENSION;
                        }
                        return Css.Token.KHZ;
                  }
               default:
                  while( _current != -1 &&
                        CssCharUtils.isName( ( char ) _current ) )
                  {
                     nextChar();
                  }
                  return Css.Token.DIMENSION;
            }
         case 'm':
         case 'M':
            switch ( nextChar() )
            {
               case 'm':
               case 'M':
                  nextChar();
                  if( _current != -1 &&
                        CssCharUtils.isName( ( char ) _current ) )
                  {
                     do
                     {
                        nextChar();
                     }while ( _current != -1 &&
                           CssCharUtils.isName( ( char ) _current ) );
                     return Css.Token.DIMENSION;
                  }
                  return Css.Token.MM;
               case 's':
               case 'S':
                  nextChar();
                  if( _current != -1 &&
                        CssCharUtils.isName( ( char ) _current ) )
                  {
                     do
                     {
                        nextChar();
                     }while ( _current != -1 &&
                           CssCharUtils.isName( ( char ) _current ) );
                     return Css.Token.DIMENSION;
                  }
                  return Css.Token.MS;
               default:
                  while( _current != -1 &&
                        CssCharUtils.isName( ( char ) _current ) )
                  {
                     nextChar();
                  }
                  return Css.Token.DIMENSION;
            }
         case 'p':
         case 'P':
            switch ( nextChar() )
            {
               case 'c':
               case 'C':
                  nextChar();
                  if( _current != -1 &&
                        CssCharUtils.isName( ( char ) _current ) )
                  {
                     do
                     {
                        nextChar();
                     }while ( _current != -1 &&
                           CssCharUtils.isName( ( char ) _current ) );
                     return Css.Token.DIMENSION;
                  }
                  return Css.Token.PC;
               case 't':
               case 'T':
                  nextChar();
                  if( _current != -1 &&
                        CssCharUtils.isName( ( char ) _current ) )
                  {
                     do
                     {
                        nextChar();
                     }while ( _current != -1 &&
                           CssCharUtils.isName( ( char ) _current ) );
                     return Css.Token.DIMENSION;
                  }
                  return Css.Token.PT;
               case 'x':
               case 'X':
                  nextChar();
                  if( _current != -1 &&
                        CssCharUtils.isName( ( char ) _current ) )
                  {
                     do
                     {
                        nextChar();
                     }while ( _current != -1 &&
                           CssCharUtils.isName( ( char ) _current ) );
                     return Css.Token.DIMENSION;
                  }
                  return Css.Token.PX;
               default:
                  while( _current != -1 &&
                        CssCharUtils.isName( ( char ) _current ) )
                  {
                     nextChar();
                  }
                  return Css.Token.DIMENSION;
            }
         case 'r':
         case 'R':
            switch ( nextChar() )
            {
               case 'a':
               case 'A':
                  switch ( nextChar() )
                  {
                     case 'd':
                     case 'D':
                        nextChar();
                        if( _current != -1 &&
                              CssCharUtils.isName( ( char ) _current ) )
                        {
                           do
                           {
                              nextChar();
                           }while ( _current != -1 &&
                                 CssCharUtils.isName( ( char ) _current ) );
                           return Css.Token.DIMENSION;
                        }
                        return Css.Token.RAD;
                  }
               default:
                  while( _current != -1 &&
                        CssCharUtils.isName( ( char ) _current ) )
                  {
                     nextChar();
                  }
                  return Css.Token.DIMENSION;
            }
         case 's':
         case 'S':
            nextChar();
            return Css.Token.S;
         default:
            if( _current != -1 &&
                  CssCharUtils.isIdentifierStart( ( char ) _current ) )
            {
               do
               {
                  nextChar();
               }while ( _current != -1 &&
                     CssCharUtils.isName( ( char ) _current ) );
               return Css.Token.DIMENSION;
            }
            return ( integer ) ? Css.Token.INTEGER : Css.Token.REAL;
      }
   }

   /**
    *  Scans a single quoted string.
    */
   protected int string1() throws IOException
   {
      nextChar();
      _start = _position - 1;
      loop :
      for( ; ;  )
      {
         switch ( nextChar() )
         {
            case -1:
               throw new CssParseException( "eof", _line, _column );
            case '\'':
               break loop;
            case '"':
               break;
            case '\\':
               switch ( nextChar() )
               {
                  case '\n':
                  case '\f':
                     break;
                  default:
                     escape();
               }
               break;
            default:
               if( !CssCharUtils.isString( ( char ) _current ) )
                  throw new CssParseException( "character", _line, _column );
         }
      }
      nextChar();
      return Css.Token.STRING;
   }

   /**
    *  Scans a double quoted string.
    */
   protected int string2() throws IOException
   {
      nextChar();
      _start = _position - 1;
      loop :
      for( ; ;  )
      {
         switch ( nextChar() )
         {
            case -1:
               throw new CssParseException( "eof", _line, _column );
            case '\'':
               break;
            case '"':
               break loop;
            case '\\':
               switch ( nextChar() )
               {
                  case '\n':
                  case '\f':
                     break;
                  default:
                     escape();
               }
               break;
            default:
               if( !CssCharUtils.isString( ( char ) _current ) )
                  throw new CssParseException( "character", _line, _column );
         }
      }
      nextChar();
      return Css.Token.STRING;
   }

   private void collapseCRNL( int src )
   {
      // Now collapse cr/nl...
      while( src < _readCount )
      {
         if( _readBuffer[src] != 13 )
            src++;
         else
         {
            _readBuffer[src] = 10;
            src++;
            if( src >= _readCount )
               break;

            if( _readBuffer[src] == 10 )
            {
               // We now need to collapse some of the chars to
               // eliminate cr/nl pairs.  This is where we do it...
               int dst = src;
               // start writing where this 10 is
               src++;
               // skip reading this 10.
               while( src < _readCount )
               {
                  if( _readBuffer[src] == 13 )
                  {
                     _readBuffer[dst++] = 10;
                     src++;
                     if( src >= _readCount )
                        break;

                     if( _readBuffer[src] == 10 )
                        src++;

                     continue;
                  }
                  _readBuffer[dst++] = _readBuffer[src++];
               }
               _readCount = dst;
               break;
            }
         }
      }
   }

   private boolean fillReadBuffer() throws IOException
   {
      if( _readCount != 0 )
      {
         if( _readPosition == _readCount )
         {
            _readBuffer[0] = _readBuffer[_readCount - 1];
            _readCount = 1;
            _readPosition = 1;
         }
         else
         {
            // we keep the last char in our readBuffer.
            System.arraycopy( _readBuffer, _readPosition - 1, _readBuffer, 0,
                  _readCount - _readPosition + 1 );
            _readCount = ( _readCount - _readPosition ) + 1;
            _readPosition = 1;
         }
      }

      // No reader so can't extend...
      if( _reader == null )
         return ( _readCount != _readPosition );

      // remember where the fill starts...
      int src = _readCount - 1;
      if( src < 0 )
         src = 0;

      // Refill the readBuffer...
      int read = _reader.read( _readBuffer, _readCount,
            _readBuffer.length - _readCount );
      if( read == -1 )
         return _readCount != _readPosition;

      _readCount += read;
      // add in chars read.
      collapseCRNL( src );
      // Now collapse cr/nl...

      return _readCount != _readPosition;
   }
}
