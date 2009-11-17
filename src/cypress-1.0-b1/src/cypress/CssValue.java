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

public class CssValue
{
   public final static int CENTIMETER = 5;

   public final static int DIMENSION = 20;
   public final static int EM = 8;
   public final static int EX = 9;
   public final static int FUNCTION_RECT = 18;

   public final static int FUNCTION_RGB = 17;

   public final static int FUNCTION_USER_DEFINDED = 19;
   public final static int IDENT = 12;
   public final static int INCH = 7;
   public final static int INHERIT = 16;

   public final static int INTEGER = 10;
   public final static int MILLIMETER = 6;

   public final static int OPERATOR_COMMA = 14;
   public final static int OPERATOR_SLASH = 15;
   public final static int PERCENTAGE = 1;
   public final static int PICA = 3;
   public final static int PIXEL = 4;
   public final static int POINT = 2;
   public final static int REAL = 0;

   public final static int STRING = 11;
   public final static int URI = 13;
   private CssValue _next;
   private CssValue _prev;

   private int _type;

   public CssValue( int type, CssValue prev )
   {
      _type = type;
      _prev = prev;

      if( _prev != null )
         _prev.setNext( this );
   }

   public void setNext( CssValue next )
   {
      _next = next;
   }

   public CssValue getNext()
   {
      return _next;
   }

   public CssValue getPrev()
   {
      return _prev;
   }

   public int getType()
   {
      return _type;
   }
}
