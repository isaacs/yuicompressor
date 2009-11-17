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

/**
 *  A collection of utility functions for a CSS scanner.
 */

public class CssCharUtils
{

   /**
    *  The set of the valid hexadecimal characters.
    */
   protected final static int[] HEXADECIMAL = {0, 67043328, 126, 126};
   /**
    *  The set of the valid identifier start characters.
    */
   protected final static int[] IDENTIFIER_START = {0, 0, 134217726, 134217726};

   /**
    *  The set of the valid name characters.
    */
   protected final static int[] NAME = {0, 67051520, 134217726, 134217726};

   /**
    *  The set of the valid string characters.
    */
   protected final static int[] STRING = {512, -133, -1, 2147483647};

   /**
    *  The set of the valid uri characters.
    */
   protected final static int[] URI = {0, -902, -1, 2147483647};

   /**
    *  This class does not need to be instantiated.
    */
   protected CssCharUtils() { }

   /**
    *  Tests whether the given character is a valid hexadecimal character.
    */
   public static boolean isHexadecimal( char c )
   {
      return c < 128 && ( ( HEXADECIMAL[c / 32] & ( 1 << ( c % 32 ) ) ) != 0 );
   }

   /**
    *  Tests whether the given character is a valid identifier start character.
    */
   public static boolean isIdentifierStart( char c )
   {
      return c >= 128 || ( ( IDENTIFIER_START[c / 32] & ( 1 << ( c % 32 ) ) ) != 0 );
   }

   /**
    *  Tests whether the given character is a valid name character.
    */
   public static boolean isName( char c )
   {
      return c >= 128 || ( ( NAME[c / 32] & ( 1 << ( c % 32 ) ) ) != 0 );
   }

   /**
    *  Tests whether the given character is a valid space.
    */
   public static boolean isSpace( char c )
   {
      return ( c <= 0x0020 ) &&
            ( ( ( ( ( 1L << '\t' ) |
            ( 1L << '\n' ) |
            ( 1L << '\r' ) |
            ( 1L << '\f' ) |
            ( 1L << 0x0020 ) ) >> c ) & 1L ) != 0 );
   }

   /**
    *  Tests whether the given character is a valid string character.
    */
   public static boolean isString( char c )
   {
      return c >= 128 || ( ( STRING[c / 32] & ( 1 << ( c % 32 ) ) ) != 0 );
   }

   /**
    *  Tests whether the given character is a valid URI character.
    */
   public static boolean isUri( char c )
   {
      return c >= 128 || ( ( URI[c / 32] & ( 1 << ( c % 32 ) ) ) != 0 );
   }
}
