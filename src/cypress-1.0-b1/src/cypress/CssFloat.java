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

public class CssFloat extends CssValue
{

   private float _value;

   private CssFloat( float value, int type, CssValue prev )
   {
      super( type, prev );
      _value = value;
   }

   public float getFloatValue()
   {
      return _value;
   }

   public static CssFloat createCentimeter( float value, CssValue prev )
   {
      return new CssFloat( value, CENTIMETER, prev );
   }

   public static CssFloat createEm( float value, CssValue prev )
   {
      return new CssFloat( value, EM, prev );
   }

   public static CssFloat createEx( float value, CssValue prev )
   {
      return new CssFloat( value, EX, prev );
   }


   public static CssFloat createInch( float value, CssValue prev )
   {
      return new CssFloat( value, INCH, prev );
   }

   public static CssFloat createMillimeter( float value, CssValue prev )
   {
      return new CssFloat( value, MILLIMETER, prev );
   }

   public static CssFloat createPercentage( float value, CssValue prev )
   {
      return new CssFloat( value, PERCENTAGE, prev );
   }

   public static CssFloat createPica( float value, CssValue prev )
   {
      return new CssFloat( value, PICA, prev );
   }

   public static CssFloat createPixel( float value, CssValue prev )
   {
      return new CssFloat( value, PIXEL, prev );
   }

   public static CssFloat createPoint( float value, CssValue prev )
   {
      return new CssFloat( value, POINT, prev );
   }

   public static CssFloat createReal( float value, CssValue prev )
   {
      return new CssFloat( value, REAL, prev );
   }

   public String toString()
   {
      return "float: " + _value;
   }
}
