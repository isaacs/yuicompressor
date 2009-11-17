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

public class CssInteger extends CssValue
{

   private int _value;

   private CssInteger( int value, int type, CssValue prev )
   {
      super( type, prev );
      _value = value;
   }

   public int getIntegerValue()
   {
      return _value;
   }

   public static CssInteger createInteger( int value, CssValue prev )
   {
      return new CssInteger( value, INTEGER, prev );
   }

   public String toString()
   {
      return "integer: " + _value;
   }
}
