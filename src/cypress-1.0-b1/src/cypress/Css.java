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

import java.util.*;

public class Css
{

   static java.awt.Color ALICEBLUE = new java.awt.Color( 240, 248, 255 );
   static java.awt.Color ANTIQUEWHITE = new java.awt.Color( 250, 235, 215 );

   // todo: move 16 web colors to salsa package
   static java.awt.Color AQUA = new java.awt.Color( 0, 255, 255 );
   static java.awt.Color AQUAMARINE = new java.awt.Color( 127, 255, 212 );
   static java.awt.Color AZURE = new java.awt.Color( 240, 255, 255 );
   static java.awt.Color BEIGE = new java.awt.Color( 245, 245, 220 );
   static java.awt.Color BISQUE = new java.awt.Color( 255, 228, 196 );
   static java.awt.Color BLACK = new java.awt.Color( 0, 0, 0 );
   static java.awt.Color BLANCHEDALMOND = new java.awt.Color( 255, 255, 205 );
   static java.awt.Color BLUE = new java.awt.Color( 0, 0, 255 );
   static java.awt.Color BLUEVIOLET = new java.awt.Color( 138, 43, 226 );
   static java.awt.Color BROWN = new java.awt.Color( 165, 42, 42 );
   static java.awt.Color BURLYWOOD = new java.awt.Color( 222, 184, 135 );
   static java.awt.Color CADETBLUE = new java.awt.Color( 95, 158, 160 );
   static java.awt.Color CHARTREUSE = new java.awt.Color( 127, 255, 0 );
   static java.awt.Color CHOCOLATE = new java.awt.Color( 210, 105, 30 );
   static java.awt.Color CORAL = new java.awt.Color( 255, 127, 80 );
   static java.awt.Color CORNFLOWERBLUE = new java.awt.Color( 100, 149, 237 );
   static java.awt.Color CORNSILK = new java.awt.Color( 255, 248, 220 );
   static java.awt.Color CRIMSON = new java.awt.Color( 220, 20, 60 );
   static java.awt.Color CYAN = new java.awt.Color( 0, 255, 255 );
   static java.awt.Color DARKBLUE = new java.awt.Color( 0, 0, 139 );
   static java.awt.Color DARKCYAN = new java.awt.Color( 0, 139, 139 );
   static java.awt.Color DARKGOLDENROD = new java.awt.Color( 184, 134, 11 );
   static java.awt.Color DARKGRAY = new java.awt.Color( 169, 169, 169 );
   static java.awt.Color DARKGREEN = new java.awt.Color( 0, 100, 0 );
   static java.awt.Color DARKKHAKI = new java.awt.Color( 189, 183, 107 );
   static java.awt.Color DARKMAGENTA = new java.awt.Color( 139, 0, 139 );
   static java.awt.Color DARKOLIVEGREEN = new java.awt.Color( 85, 107, 47 );
   static java.awt.Color DARKORANGE = new java.awt.Color( 255, 140, 0 );
   static java.awt.Color DARKORCHID = new java.awt.Color( 153, 50, 204 );
   static java.awt.Color DARKRED = new java.awt.Color( 139, 0, 0 );
   static java.awt.Color DARKSALMON = new java.awt.Color( 233, 150, 122 );
   static java.awt.Color DARKSEAGREEN = new java.awt.Color( 143, 188, 143 );
   static java.awt.Color DARKSLATEBLUE = new java.awt.Color( 72, 61, 139 );
   static java.awt.Color DARKSLATEGRAY = new java.awt.Color( 47, 79, 79 );
   static java.awt.Color DARKTURQUOISE = new java.awt.Color( 0, 206, 209 );
   static java.awt.Color DARKVIOLET = new java.awt.Color( 148, 0, 211 );
   static java.awt.Color DEEPPINK = new java.awt.Color( 255, 20, 147 );
   static java.awt.Color DEEPSKYBLUE = new java.awt.Color( 0, 191, 255 );
   static java.awt.Color DIMGRAY = new java.awt.Color( 105, 105, 105 );
   static java.awt.Color DODGERBLUE = new java.awt.Color( 30, 144, 255 );
   static java.awt.Color FIREBRICK = new java.awt.Color( 178, 34, 34 );
   static java.awt.Color FLORALWHITE = new java.awt.Color( 255, 250, 240 );
   static java.awt.Color FORESTGREEN = new java.awt.Color( 34, 139, 34 );
   static java.awt.Color FUCHSIA = new java.awt.Color( 255, 0, 255 );
   static java.awt.Color GAINSBORO = new java.awt.Color( 220, 220, 220 );
   static java.awt.Color GHOSTWHITE = new java.awt.Color( 248, 248, 255 );
   static java.awt.Color GOLD = new java.awt.Color( 255, 215, 0 );
   static java.awt.Color GOLDENROD = new java.awt.Color( 218, 165, 32 );
   static java.awt.Color GRAY = new java.awt.Color( 128, 128, 128 );
   static java.awt.Color GREEN = new java.awt.Color( 0, 128, 0 );
   static java.awt.Color GREENYELLOW = new java.awt.Color( 173, 255, 47 );
   static java.awt.Color HONEYDEW = new java.awt.Color( 240, 255, 240 );
   static java.awt.Color HOTPINK = new java.awt.Color( 255, 105, 180 );
   static java.awt.Color INDIANRED = new java.awt.Color( 205, 92, 92 );
   static java.awt.Color INDIGO = new java.awt.Color( 75, 0, 130 );
   static java.awt.Color IVORY = new java.awt.Color( 255, 240, 240 );
   static java.awt.Color KHAKI = new java.awt.Color( 240, 230, 140 );
   static java.awt.Color LAVENDER = new java.awt.Color( 230, 230, 250 );
   static java.awt.Color LAVENDERBLUSH = new java.awt.Color( 255, 240, 245 );
   static java.awt.Color LAWNGREEN = new java.awt.Color( 124, 252, 0 );
   static java.awt.Color LEMONCHIFFON = new java.awt.Color( 255, 250, 205 );
   static java.awt.Color LIGHTBLUE = new java.awt.Color( 173, 216, 230 );
   static java.awt.Color LIGHTCORAL = new java.awt.Color( 240, 128, 128 );
   static java.awt.Color LIGHTCYAN = new java.awt.Color( 224, 255, 255 );
   static java.awt.Color LIGHTGOLDENRODYELLOW = new java.awt.Color( 250, 250, 210 );
   static java.awt.Color LIGHTGREEN = new java.awt.Color( 144, 238, 144 );
   static java.awt.Color LIGHTGREY = new java.awt.Color( 211, 211, 211 );
   static java.awt.Color LIGHTPINK = new java.awt.Color( 255, 182, 193 );
   static java.awt.Color LIGHTSALMON = new java.awt.Color( 255, 160, 122 );
   static java.awt.Color LIGHTSEAGREEN = new java.awt.Color( 32, 178, 170 );
   static java.awt.Color LIGHTSKYBLUE = new java.awt.Color( 135, 206, 250 );
   static java.awt.Color LIGHTSLATEGRAY = new java.awt.Color( 119, 136, 153 );
   static java.awt.Color LIGHTSTEELBLUE = new java.awt.Color( 176, 196, 222 );
   static java.awt.Color LIGHTYELLOW = new java.awt.Color( 255, 255, 224 );
   static java.awt.Color LIME = new java.awt.Color( 0, 255, 0 );
   static java.awt.Color LIMEGREEN = new java.awt.Color( 50, 205, 50 );
   static java.awt.Color LINEN = new java.awt.Color( 250, 240, 230 );
   static java.awt.Color MAGENTA = new java.awt.Color( 255, 0, 255 );
   static java.awt.Color MAROON = new java.awt.Color( 128, 0, 0 );
   static java.awt.Color MEDIUMAQUAMARINE = new java.awt.Color( 102, 205, 170 );
   static java.awt.Color MEDIUMBLUE = new java.awt.Color( 0, 0, 205 );
   static java.awt.Color MEDIUMORCHID = new java.awt.Color( 186, 85, 211 );
   static java.awt.Color MEDIUMPURPLE = new java.awt.Color( 147, 112, 219 );
   static java.awt.Color MEDIUMSEAGREEN = new java.awt.Color( 60, 179, 113 );
   static java.awt.Color MEDIUMSLATEBLUE = new java.awt.Color( 123, 104, 238 );
   static java.awt.Color MEDIUMSPRINGGREEN = new java.awt.Color( 0, 250, 154 );
   static java.awt.Color MEDIUMTURQUOISE = new java.awt.Color( 72, 209, 204 );
   static java.awt.Color MEDIUMVIOLETRED = new java.awt.Color( 199, 21, 133 );
   static java.awt.Color MIDNIGHTBLUE = new java.awt.Color( 25, 25, 112 );
   static java.awt.Color MINTCREAM = new java.awt.Color( 245, 255, 250 );
   static java.awt.Color MISTYROSE = new java.awt.Color( 255, 228, 225 );
   static java.awt.Color MOCCASIN = new java.awt.Color( 255, 228, 181 );
   static java.awt.Color NAVAJOWHITE = new java.awt.Color( 255, 222, 173 );
   static java.awt.Color NAVY = new java.awt.Color( 0, 0, 128 );
   static java.awt.Color OLDLACE = new java.awt.Color( 253, 245, 230 );
   static java.awt.Color OLIVE = new java.awt.Color( 128, 128, 0 );
   static java.awt.Color OLIVEDRAB = new java.awt.Color( 107, 142, 35 );
   static java.awt.Color ORANGE = new java.awt.Color( 255, 165, 0 );
   static java.awt.Color ORANGERED = new java.awt.Color( 255, 69, 0 );
   static java.awt.Color ORCHID = new java.awt.Color( 218, 112, 214 );
   static java.awt.Color PALEGOLDENROD = new java.awt.Color( 238, 232, 170 );
   static java.awt.Color PALEGREEN = new java.awt.Color( 152, 251, 152 );
   static java.awt.Color PALETURQUOISE = new java.awt.Color( 175, 238, 238 );
   static java.awt.Color PALEVIOLETRED = new java.awt.Color( 219, 112, 147 );
   static java.awt.Color PAPAYAWHIP = new java.awt.Color( 255, 239, 213 );
   static java.awt.Color PEACHPUFF = new java.awt.Color( 255, 218, 185 );
   static java.awt.Color PERU = new java.awt.Color( 205, 133, 63 );
   static java.awt.Color PINK = new java.awt.Color( 255, 192, 203 );
   static java.awt.Color PLUM = new java.awt.Color( 221, 160, 221 );
   static java.awt.Color POWDERBLUE = new java.awt.Color( 176, 224, 230 );
   static java.awt.Color PURPLE = new java.awt.Color( 128, 0, 128 );
   static java.awt.Color RED = new java.awt.Color( 255, 0, 0 );
   static java.awt.Color ROSYBROWN = new java.awt.Color( 188, 143, 143 );
   static java.awt.Color ROYALBLUE = new java.awt.Color( 65, 105, 225 );
   static java.awt.Color SADDLEBROWN = new java.awt.Color( 139, 69, 19 );
   static java.awt.Color SALMON = new java.awt.Color( 250, 128, 114 );
   static java.awt.Color SANDYBROWN = new java.awt.Color( 244, 164, 96 );
   static java.awt.Color SEAGREEN = new java.awt.Color( 46, 139, 87 );
   static java.awt.Color SEASHELL = new java.awt.Color( 255, 245, 238 );
   static java.awt.Color SIENNA = new java.awt.Color( 160, 82, 45 );
   static java.awt.Color SILVER = new java.awt.Color( 192, 192, 192 );
   static java.awt.Color SKYBLUE = new java.awt.Color( 135, 206, 235 );
   static java.awt.Color SLATEBLUE = new java.awt.Color( 106, 90, 205 );
   static java.awt.Color SLATEGRAY = new java.awt.Color( 112, 128, 144 );
   static java.awt.Color SNOW = new java.awt.Color( 255, 250, 250 );
   static java.awt.Color SPRINGGREEN = new java.awt.Color( 0, 255, 127 );
   static java.awt.Color STEELBLUE = new java.awt.Color( 70, 130, 180 );
   static java.awt.Color TAN = new java.awt.Color( 210, 180, 140 );
   static java.awt.Color TEAL = new java.awt.Color( 0, 128, 128 );
   static java.awt.Color THISTLE = new java.awt.Color( 216, 191, 216 );
   static java.awt.Color TOMATO = new java.awt.Color( 253, 99, 71 );
   static java.awt.Color TURQUOISE = new java.awt.Color( 64, 224, 208 );
   static java.awt.Color VIOLET = new java.awt.Color( 238, 130, 238 );
   static java.awt.Color WHEAT = new java.awt.Color( 245, 222, 179 );
   static java.awt.Color WHITE = new java.awt.Color( 255, 255, 255 );
   static java.awt.Color WHITESMOKE = new java.awt.Color( 245, 245, 245 );
   static java.awt.Color YELLOW = new java.awt.Color( 255, 255, 0 );
   static java.awt.Color YELLOWGREEN = new java.awt.Color( 154, 205, 50 );
   private static TypeInfo UNDEFINED_TYPE_INFO;

   private static Map _typeInfoMap;


   public static java.awt.Color getColor( int code )
   {
      switch ( code )
      {
         case Color.Id.AQUA:
            return AQUA;
         case Color.Id.BLACK:
            return BLACK;
         case Color.Id.BLUE:
            return BLUE;
         case Color.Id.FUCHSIA:
            return FUCHSIA;
         case Color.Id.GRAY:
            return GRAY;
         case Color.Id.GREEN:
            return GREEN;
         case Color.Id.LIME:
            return LIME;
         case Color.Id.MAROON:
            return MAROON;
         case Color.Id.NAVY:
            return NAVY;
         case Color.Id.OLIVE:
            return OLIVE;
         case Color.Id.PURPLE:
            return PURPLE;
         case Color.Id.RED:
            return RED;
         case Color.Id.SILVER:
            return SILVER;
         case Color.Id.TEAL:
            return TEAL;
         case Color.Id.WHITE:
            return WHITE;
         case Color.Id.YELLOW:
            return YELLOW;
         case Color.Id.ALICEBLUE:
            return ALICEBLUE;
         case Color.Id.ANTIQUEWHITE:
            return ANTIQUEWHITE;
         case Color.Id.AQUAMARINE:
            return AQUAMARINE;
         case Color.Id.AZURE:
            return AZURE;
         case Color.Id.BEIGE:
            return BEIGE;
         case Color.Id.BISQUE:
            return BISQUE;
         case Color.Id.BLANCHEDALMOND:
            return BLANCHEDALMOND;
         case Color.Id.BLUEVIOLET:
            return BLUEVIOLET;
         case Color.Id.BROWN:
            return BROWN;
         case Color.Id.BURLYWOOD:
            return BURLYWOOD;
         case Color.Id.CADETBLUE:
            return CADETBLUE;
         case Color.Id.CHARTREUSE:
            return CHARTREUSE;
         case Color.Id.CHOCOLATE:
            return CHOCOLATE;
         case Color.Id.CORAL:
            return CORAL;
         case Color.Id.CORNFLOWERBLUE:
            return CORNFLOWERBLUE;
         case Color.Id.CORNSILK:
            return CORNSILK;
         case Color.Id.CRIMSON:
            return CRIMSON;
         case Color.Id.CYAN:
            return CYAN;
         case Color.Id.DARKBLUE:
            return DARKBLUE;
         case Color.Id.DARKCYAN:
            return DARKCYAN;
         case Color.Id.DARKGOLDENROD:
            return DARKGOLDENROD;
         case Color.Id.DARKGRAY:
            return DARKGRAY;
         case Color.Id.DARKGREEN:
            return DARKGREEN;
         case Color.Id.DARKKHAKI:
            return DARKKHAKI;
         case Color.Id.DARKMAGENTA:
            return DARKMAGENTA;
         case Color.Id.DARKOLIVEGREEN:
            return DARKOLIVEGREEN;
         case Color.Id.DARKORANGE:
            return DARKORANGE;
         case Color.Id.DARKORCHID:
            return DARKORCHID;
         case Color.Id.DARKRED:
            return DARKRED;
         case Color.Id.DARKSALMON:
            return DARKSALMON;
         case Color.Id.DARKSEAGREEN:
            return DARKSEAGREEN;
         case Color.Id.DARKSLATEBLUE:
            return DARKSLATEBLUE;
         case Color.Id.DARKSLATEGRAY:
            return DARKSLATEGRAY;
         case Color.Id.DARKTURQUOISE:
            return DARKTURQUOISE;
         case Color.Id.DARKVIOLET:
            return DARKVIOLET;
         case Color.Id.DEEPPINK:
            return DEEPPINK;
         case Color.Id.DEEPSKYBLUE:
            return DEEPSKYBLUE;
         case Color.Id.DIMGRAY:
            return DIMGRAY;
         case Color.Id.DODGERBLUE:
            return DODGERBLUE;
         case Color.Id.FIREBRICK:
            return FIREBRICK;
         case Color.Id.FLORALWHITE:
            return FLORALWHITE;
         case Color.Id.FORESTGREEN:
            return FORESTGREEN;
         case Color.Id.GAINSBORO:
            return GAINSBORO;
         case Color.Id.GHOSTWHITE:
            return GHOSTWHITE;
         case Color.Id.GOLD:
            return GOLD;
         case Color.Id.GOLDENROD:
            return GOLDENROD;
         case Color.Id.GREENYELLOW:
            return GREENYELLOW;
         case Color.Id.HONEYDEW:
            return HONEYDEW;
         case Color.Id.HOTPINK:
            return HOTPINK;
         case Color.Id.INDIANRED:
            return INDIANRED;
         case Color.Id.INDIGO:
            return INDIGO;
         case Color.Id.IVORY:
            return IVORY;
         case Color.Id.KHAKI:
            return KHAKI;
         case Color.Id.LAVENDER:
            return LAVENDER;
         case Color.Id.LAVENDERBLUSH:
            return LAVENDERBLUSH;
         case Color.Id.LAWNGREEN:
            return LAWNGREEN;
         case Color.Id.LEMONCHIFFON:
            return LEMONCHIFFON;
         case Color.Id.LIGHTBLUE:
            return LIGHTBLUE;
         case Color.Id.LIGHTCORAL:
            return LIGHTCORAL;
         case Color.Id.LIGHTCYAN:
            return LIGHTCYAN;
         case Color.Id.LIGHTGOLDENRODYELLOW:
            return LIGHTGOLDENRODYELLOW;
         case Color.Id.LIGHTGREEN:
            return LIGHTGREEN;
         case Color.Id.LIGHTGREY:
            return LIGHTGREY;
         case Color.Id.LIGHTPINK:
            return LIGHTPINK;
         case Color.Id.LIGHTSALMON:
            return LIGHTSALMON;
         case Color.Id.LIGHTSEAGREEN:
            return LIGHTSEAGREEN;
         case Color.Id.LIGHTSKYBLUE:
            return LIGHTSKYBLUE;
         case Color.Id.LIGHTSLATEGRAY:
            return LIGHTSLATEGRAY;
         case Color.Id.LIGHTSTEELBLUE:
            return LIGHTSTEELBLUE;
         case Color.Id.LIGHTYELLOW:
            return LIGHTYELLOW;
         case Color.Id.LIMEGREEN:
            return LIMEGREEN;
         case Color.Id.LINEN:
            return LINEN;
         case Color.Id.MAGENTA:
            return MAGENTA;
         case Color.Id.MEDIUMAQUAMARINE:
            return MEDIUMAQUAMARINE;
         case Color.Id.MEDIUMBLUE:
            return MEDIUMBLUE;
         case Color.Id.MEDIUMORCHID:
            return MEDIUMORCHID;
         case Color.Id.MEDIUMPURPLE:
            return MEDIUMPURPLE;
         case Color.Id.MEDIUMSEAGREEN:
            return MEDIUMSEAGREEN;
         case Color.Id.MEDIUMSLATEBLUE:
            return MEDIUMSLATEBLUE;
         case Color.Id.MEDIUMSPRINGGREEN:
            return MEDIUMSPRINGGREEN;
         case Color.Id.MEDIUMTURQUOISE:
            return MEDIUMTURQUOISE;
         case Color.Id.MEDIUMVIOLETRED:
            return MEDIUMVIOLETRED;
         case Color.Id.MIDNIGHTBLUE:
            return MIDNIGHTBLUE;
         case Color.Id.MINTCREAM:
            return MINTCREAM;
         case Color.Id.MISTYROSE:
            return MISTYROSE;
         case Color.Id.MOCCASIN:
            return MOCCASIN;
         case Color.Id.NAVAJOWHITE:
            return NAVAJOWHITE;
         case Color.Id.OLDLACE:
            return OLDLACE;
         case Color.Id.OLIVEDRAB:
            return OLIVEDRAB;
         case Color.Id.ORANGE:
            return ORANGE;
         case Color.Id.ORANGERED:
            return ORANGERED;
         case Color.Id.ORCHID:
            return ORCHID;
         case Color.Id.PALEGOLDENROD:
            return PALEGOLDENROD;
         case Color.Id.PALEGREEN:
            return PALEGREEN;
         case Color.Id.PALETURQUOISE:
            return PALETURQUOISE;
         case Color.Id.PALEVIOLETRED:
            return PALEVIOLETRED;
         case Color.Id.PAPAYAWHIP:
            return PAPAYAWHIP;
         case Color.Id.PEACHPUFF:
            return PEACHPUFF;
         case Color.Id.PERU:
            return PERU;
         case Color.Id.PINK:
            return PINK;
         case Color.Id.PLUM:
            return PLUM;
         case Color.Id.POWDERBLUE:
            return POWDERBLUE;
         case Color.Id.ROSYBROWN:
            return ROSYBROWN;
         case Color.Id.ROYALBLUE:
            return ROYALBLUE;
         case Color.Id.SADDLEBROWN:
            return SADDLEBROWN;
         case Color.Id.SALMON:
            return SALMON;
         case Color.Id.SANDYBROWN:
            return SANDYBROWN;
         case Color.Id.SEAGREEN:
            return SEAGREEN;
         case Color.Id.SEASHELL:
            return SEASHELL;
         case Color.Id.SIENNA:
            return SIENNA;
         case Color.Id.SKYBLUE:
            return SKYBLUE;
         case Color.Id.SLATEBLUE:
            return SLATEBLUE;
         case Color.Id.SLATEGRAY:
            return SLATEGRAY;
         case Color.Id.SNOW:
            return SNOW;
         case Color.Id.SPRINGGREEN:
            return SPRINGGREEN;
         case Color.Id.STEELBLUE:
            return STEELBLUE;
         case Color.Id.TAN:
            return TAN;
         case Color.Id.THISTLE:
            return THISTLE;
         case Color.Id.TOMATO:
            return TOMATO;
         case Color.Id.TURQUOISE:
            return TURQUOISE;
         case Color.Id.VIOLET:
            return VIOLET;
         case Color.Id.WHEAT:
            return WHEAT;
         case Color.Id.WHITESMOKE:
            return WHITESMOKE;
         case Color.Id.YELLOWGREEN:
            return YELLOWGREEN;
         default:
            return BLACK;
      }
   }

   public static TypeInfo getTypeInfo( String value )
   {
      if( _typeInfoMap == null )
      {
         UNDEFINED_TYPE_INFO = new TypeInfo( Type.UNDEFINED, 0 );

         // todo: set initial map size for optimization
         _typeInfoMap = new HashMap();

         _typeInfoMap.put( Align.LEFT, new TypeInfo( Type.ALIGN, Align.Id.LEFT ) );
         _typeInfoMap.put( Align.RIGHT, new TypeInfo( Type.ALIGN, Align.Id.RIGHT ) );
         _typeInfoMap.put( Align.CENTER, new TypeInfo( Type.ALIGN, Align.Id.CENTER ) );

         _typeInfoMap.put( VerticalAlign.TOP, new TypeInfo( Type.VERTICAL_ALIGN, VerticalAlign.Id.TOP ) );
         _typeInfoMap.put( VerticalAlign.MIDDLE, new TypeInfo( Type.VERTICAL_ALIGN, VerticalAlign.Id.MIDDLE ) );
         _typeInfoMap.put( VerticalAlign.BOTTOM, new TypeInfo( Type.VERTICAL_ALIGN, VerticalAlign.Id.BOTTOM ) );

         _typeInfoMap.put( BorderStyle.NONE, new TypeInfo( Type.BORDER_STYLE, BorderStyle.Id.NONE ) );
         _typeInfoMap.put( BorderStyle.SOLID, new TypeInfo( Type.BORDER_STYLE, BorderStyle.Id.SOLID ) );
         _typeInfoMap.put( BorderStyle.DOUBLE, new TypeInfo( Type.BORDER_STYLE, BorderStyle.Id.DOUBLE ) );
         _typeInfoMap.put( BorderStyle.GROOVE, new TypeInfo( Type.BORDER_STYLE, BorderStyle.Id.GROOVE ) );
         _typeInfoMap.put( BorderStyle.RIDGE, new TypeInfo( Type.BORDER_STYLE, BorderStyle.Id.RIDGE ) );
         _typeInfoMap.put( BorderStyle.INSET, new TypeInfo( Type.BORDER_STYLE, BorderStyle.Id.INSET ) );
         _typeInfoMap.put( BorderStyle.OUTSET, new TypeInfo( Type.BORDER_STYLE, BorderStyle.Id.OUTSET ) );

         _typeInfoMap.put( Color.AQUA, new TypeInfo( Type.COLOR, Color.Id.AQUA ) );
         _typeInfoMap.put( Color.BLACK, new TypeInfo( Type.COLOR, Color.Id.BLACK ) );
         _typeInfoMap.put( Color.BLUE, new TypeInfo( Type.COLOR, Color.Id.BLUE ) );
         _typeInfoMap.put( Color.FUCHSIA, new TypeInfo( Type.COLOR, Color.Id.FUCHSIA ) );
         _typeInfoMap.put( Color.GRAY, new TypeInfo( Type.COLOR, Color.Id.GRAY ) );
         _typeInfoMap.put( Color.GREEN, new TypeInfo( Type.COLOR, Color.Id.GREEN ) );
         _typeInfoMap.put( Color.LIME, new TypeInfo( Type.COLOR, Color.Id.LIME ) );
         _typeInfoMap.put( Color.MAROON, new TypeInfo( Type.COLOR, Color.Id.MAROON ) );
         _typeInfoMap.put( Color.NAVY, new TypeInfo( Type.COLOR, Color.Id.NAVY ) );
         _typeInfoMap.put( Color.OLIVE, new TypeInfo( Type.COLOR, Color.Id.OLIVE ) );
         _typeInfoMap.put( Color.PURPLE, new TypeInfo( Type.COLOR, Color.Id.PURPLE ) );
         _typeInfoMap.put( Color.RED, new TypeInfo( Type.COLOR, Color.Id.RED ) );
         _typeInfoMap.put( Color.SILVER, new TypeInfo( Type.COLOR, Color.Id.SILVER ) );
         _typeInfoMap.put( Color.TEAL, new TypeInfo( Type.COLOR, Color.Id.TEAL ) );
         _typeInfoMap.put( Color.WHITE, new TypeInfo( Type.COLOR, Color.Id.WHITE ) );
         _typeInfoMap.put( Color.YELLOW, new TypeInfo( Type.COLOR, Color.Id.YELLOW ) );

         _typeInfoMap.put( Color.ALICEBLUE, new TypeInfo( Type.COLOR, Color.Id.ALICEBLUE ) );
         _typeInfoMap.put( Color.ANTIQUEWHITE, new TypeInfo( Type.COLOR, Color.Id.ANTIQUEWHITE ) );
         _typeInfoMap.put( Color.AQUAMARINE, new TypeInfo( Type.COLOR, Color.Id.AQUAMARINE ) );
         _typeInfoMap.put( Color.AZURE, new TypeInfo( Type.COLOR, Color.Id.AZURE ) );
         _typeInfoMap.put( Color.BEIGE, new TypeInfo( Type.COLOR, Color.Id.BEIGE ) );
         _typeInfoMap.put( Color.BISQUE, new TypeInfo( Type.COLOR, Color.Id.BISQUE ) );
         _typeInfoMap.put( Color.BLANCHEDALMOND, new TypeInfo( Type.COLOR, Color.Id.BLANCHEDALMOND ) );
         _typeInfoMap.put( Color.BLUEVIOLET, new TypeInfo( Type.COLOR, Color.Id.BLUEVIOLET ) );
         _typeInfoMap.put( Color.BROWN, new TypeInfo( Type.COLOR, Color.Id.BROWN ) );
         _typeInfoMap.put( Color.BURLYWOOD, new TypeInfo( Type.COLOR, Color.Id.BURLYWOOD ) );
         _typeInfoMap.put( Color.CADETBLUE, new TypeInfo( Type.COLOR, Color.Id.CADETBLUE ) );
         _typeInfoMap.put( Color.CHARTREUSE, new TypeInfo( Type.COLOR, Color.Id.CHARTREUSE ) );
         _typeInfoMap.put( Color.CHOCOLATE, new TypeInfo( Type.COLOR, Color.Id.CHOCOLATE ) );
         _typeInfoMap.put( Color.CORAL, new TypeInfo( Type.COLOR, Color.Id.CORAL ) );
         _typeInfoMap.put( Color.CORNFLOWERBLUE, new TypeInfo( Type.COLOR, Color.Id.CORNFLOWERBLUE ) );
         _typeInfoMap.put( Color.CORNSILK, new TypeInfo( Type.COLOR, Color.Id.CORNSILK ) );
         _typeInfoMap.put( Color.CRIMSON, new TypeInfo( Type.COLOR, Color.Id.CRIMSON ) );
         _typeInfoMap.put( Color.CYAN, new TypeInfo( Type.COLOR, Color.Id.CYAN ) );
         _typeInfoMap.put( Color.DARKBLUE, new TypeInfo( Type.COLOR, Color.Id.DARKBLUE ) );
         _typeInfoMap.put( Color.DARKCYAN, new TypeInfo( Type.COLOR, Color.Id.DARKCYAN ) );
         _typeInfoMap.put( Color.DARKGOLDENROD, new TypeInfo( Type.COLOR, Color.Id.DARKGOLDENROD ) );
         _typeInfoMap.put( Color.DARKGRAY, new TypeInfo( Type.COLOR, Color.Id.DARKGRAY ) );
         _typeInfoMap.put( Color.DARKGREEN, new TypeInfo( Type.COLOR, Color.Id.DARKGREEN ) );
         _typeInfoMap.put( Color.DARKKHAKI, new TypeInfo( Type.COLOR, Color.Id.DARKKHAKI ) );
         _typeInfoMap.put( Color.DARKMAGENTA, new TypeInfo( Type.COLOR, Color.Id.DARKMAGENTA ) );
         _typeInfoMap.put( Color.DARKOLIVEGREEN, new TypeInfo( Type.COLOR, Color.Id.DARKOLIVEGREEN ) );
         _typeInfoMap.put( Color.DARKORANGE, new TypeInfo( Type.COLOR, Color.Id.DARKORANGE ) );
         _typeInfoMap.put( Color.DARKORCHID, new TypeInfo( Type.COLOR, Color.Id.DARKORCHID ) );
         _typeInfoMap.put( Color.DARKRED, new TypeInfo( Type.COLOR, Color.Id.DARKRED ) );
         _typeInfoMap.put( Color.DARKSALMON, new TypeInfo( Type.COLOR, Color.Id.DARKSALMON ) );
         _typeInfoMap.put( Color.DARKSEAGREEN, new TypeInfo( Type.COLOR, Color.Id.DARKSEAGREEN ) );
         _typeInfoMap.put( Color.DARKSLATEBLUE, new TypeInfo( Type.COLOR, Color.Id.DARKSLATEBLUE ) );
         _typeInfoMap.put( Color.DARKSLATEGRAY, new TypeInfo( Type.COLOR, Color.Id.DARKSLATEGRAY ) );
         _typeInfoMap.put( Color.DARKTURQUOISE, new TypeInfo( Type.COLOR, Color.Id.DARKTURQUOISE ) );
         _typeInfoMap.put( Color.DARKVIOLET, new TypeInfo( Type.COLOR, Color.Id.DARKVIOLET ) );
         _typeInfoMap.put( Color.DEEPPINK, new TypeInfo( Type.COLOR, Color.Id.DEEPPINK ) );
         _typeInfoMap.put( Color.DEEPSKYBLUE, new TypeInfo( Type.COLOR, Color.Id.DEEPSKYBLUE ) );
         _typeInfoMap.put( Color.DIMGRAY, new TypeInfo( Type.COLOR, Color.Id.DIMGRAY ) );
         _typeInfoMap.put( Color.DODGERBLUE, new TypeInfo( Type.COLOR, Color.Id.DODGERBLUE ) );
         _typeInfoMap.put( Color.FIREBRICK, new TypeInfo( Type.COLOR, Color.Id.FIREBRICK ) );
         _typeInfoMap.put( Color.FLORALWHITE, new TypeInfo( Type.COLOR, Color.Id.FLORALWHITE ) );
         _typeInfoMap.put( Color.FORESTGREEN, new TypeInfo( Type.COLOR, Color.Id.FORESTGREEN ) );
         _typeInfoMap.put( Color.GAINSBORO, new TypeInfo( Type.COLOR, Color.Id.GAINSBORO ) );
         _typeInfoMap.put( Color.GHOSTWHITE, new TypeInfo( Type.COLOR, Color.Id.GHOSTWHITE ) );
         _typeInfoMap.put( Color.GOLD, new TypeInfo( Type.COLOR, Color.Id.GOLD ) );
         _typeInfoMap.put( Color.GOLDENROD, new TypeInfo( Type.COLOR, Color.Id.GOLDENROD ) );
         _typeInfoMap.put( Color.GREENYELLOW, new TypeInfo( Type.COLOR, Color.Id.GREENYELLOW ) );
         _typeInfoMap.put( Color.HONEYDEW, new TypeInfo( Type.COLOR, Color.Id.HONEYDEW ) );
         _typeInfoMap.put( Color.HOTPINK, new TypeInfo( Type.COLOR, Color.Id.HOTPINK ) );
         _typeInfoMap.put( Color.INDIANRED, new TypeInfo( Type.COLOR, Color.Id.INDIANRED ) );
         _typeInfoMap.put( Color.INDIGO, new TypeInfo( Type.COLOR, Color.Id.INDIGO ) );
         _typeInfoMap.put( Color.IVORY, new TypeInfo( Type.COLOR, Color.Id.IVORY ) );
         _typeInfoMap.put( Color.KHAKI, new TypeInfo( Type.COLOR, Color.Id.KHAKI ) );
         _typeInfoMap.put( Color.LAVENDER, new TypeInfo( Type.COLOR, Color.Id.LAVENDER ) );
         _typeInfoMap.put( Color.LAVENDERBLUSH, new TypeInfo( Type.COLOR, Color.Id.LAVENDERBLUSH ) );
         _typeInfoMap.put( Color.LAWNGREEN, new TypeInfo( Type.COLOR, Color.Id.LAWNGREEN ) );
         _typeInfoMap.put( Color.LEMONCHIFFON, new TypeInfo( Type.COLOR, Color.Id.LEMONCHIFFON ) );
         _typeInfoMap.put( Color.LIGHTBLUE, new TypeInfo( Type.COLOR, Color.Id.LIGHTBLUE ) );
         _typeInfoMap.put( Color.LIGHTCORAL, new TypeInfo( Type.COLOR, Color.Id.LIGHTCORAL ) );
         _typeInfoMap.put( Color.LIGHTCYAN, new TypeInfo( Type.COLOR, Color.Id.LIGHTCYAN ) );
         _typeInfoMap.put( Color.LIGHTGOLDENRODYELLOW, new TypeInfo( Type.COLOR, Color.Id.LIGHTGOLDENRODYELLOW ) );
         _typeInfoMap.put( Color.LIGHTGREEN, new TypeInfo( Type.COLOR, Color.Id.LIGHTGREEN ) );
         _typeInfoMap.put( Color.LIGHTGREY, new TypeInfo( Type.COLOR, Color.Id.LIGHTGREY ) );
         _typeInfoMap.put( Color.LIGHTPINK, new TypeInfo( Type.COLOR, Color.Id.LIGHTPINK ) );
         _typeInfoMap.put( Color.LIGHTSALMON, new TypeInfo( Type.COLOR, Color.Id.LIGHTSALMON ) );
         _typeInfoMap.put( Color.LIGHTSEAGREEN, new TypeInfo( Type.COLOR, Color.Id.LIGHTSEAGREEN ) );
         _typeInfoMap.put( Color.LIGHTSKYBLUE, new TypeInfo( Type.COLOR, Color.Id.LIGHTSKYBLUE ) );
         _typeInfoMap.put( Color.LIGHTSLATEGRAY, new TypeInfo( Type.COLOR, Color.Id.LIGHTSLATEGRAY ) );
         _typeInfoMap.put( Color.LIGHTSTEELBLUE, new TypeInfo( Type.COLOR, Color.Id.LIGHTSTEELBLUE ) );
         _typeInfoMap.put( Color.LIGHTYELLOW, new TypeInfo( Type.COLOR, Color.Id.LIGHTYELLOW ) );
         _typeInfoMap.put( Color.LIMEGREEN, new TypeInfo( Type.COLOR, Color.Id.LIMEGREEN ) );
         _typeInfoMap.put( Color.LINEN, new TypeInfo( Type.COLOR, Color.Id.LINEN ) );
         _typeInfoMap.put( Color.MAGENTA, new TypeInfo( Type.COLOR, Color.Id.MAGENTA ) );
         _typeInfoMap.put( Color.MEDIUMAQUAMARINE, new TypeInfo( Type.COLOR, Color.Id.MEDIUMAQUAMARINE ) );
         _typeInfoMap.put( Color.MEDIUMBLUE, new TypeInfo( Type.COLOR, Color.Id.MEDIUMBLUE ) );
         _typeInfoMap.put( Color.MEDIUMORCHID, new TypeInfo( Type.COLOR, Color.Id.MEDIUMORCHID ) );
         _typeInfoMap.put( Color.MEDIUMPURPLE, new TypeInfo( Type.COLOR, Color.Id.MEDIUMPURPLE ) );
         _typeInfoMap.put( Color.MEDIUMSEAGREEN, new TypeInfo( Type.COLOR, Color.Id.MEDIUMSEAGREEN ) );
         _typeInfoMap.put( Color.MEDIUMSLATEBLUE, new TypeInfo( Type.COLOR, Color.Id.MEDIUMSLATEBLUE ) );
         _typeInfoMap.put( Color.MEDIUMSPRINGGREEN, new TypeInfo( Type.COLOR, Color.Id.MEDIUMSPRINGGREEN ) );
         _typeInfoMap.put( Color.MEDIUMTURQUOISE, new TypeInfo( Type.COLOR, Color.Id.MEDIUMTURQUOISE ) );
         _typeInfoMap.put( Color.MEDIUMVIOLETRED, new TypeInfo( Type.COLOR, Color.Id.MEDIUMVIOLETRED ) );
         _typeInfoMap.put( Color.MIDNIGHTBLUE, new TypeInfo( Type.COLOR, Color.Id.MIDNIGHTBLUE ) );
         _typeInfoMap.put( Color.MINTCREAM, new TypeInfo( Type.COLOR, Color.Id.MINTCREAM ) );
         _typeInfoMap.put( Color.MISTYROSE, new TypeInfo( Type.COLOR, Color.Id.MISTYROSE ) );
         _typeInfoMap.put( Color.MOCCASIN, new TypeInfo( Type.COLOR, Color.Id.MOCCASIN ) );
         _typeInfoMap.put( Color.NAVAJOWHITE, new TypeInfo( Type.COLOR, Color.Id.NAVAJOWHITE ) );
         _typeInfoMap.put( Color.OLDLACE, new TypeInfo( Type.COLOR, Color.Id.OLDLACE ) );
         _typeInfoMap.put( Color.OLIVEDRAB, new TypeInfo( Type.COLOR, Color.Id.OLIVEDRAB ) );
         _typeInfoMap.put( Color.ORANGE, new TypeInfo( Type.COLOR, Color.Id.ORANGE ) );
         _typeInfoMap.put( Color.ORANGERED, new TypeInfo( Type.COLOR, Color.Id.ORANGERED ) );
         _typeInfoMap.put( Color.ORCHID, new TypeInfo( Type.COLOR, Color.Id.ORCHID ) );
         _typeInfoMap.put( Color.PALEGOLDENROD, new TypeInfo( Type.COLOR, Color.Id.PALEGOLDENROD ) );
         _typeInfoMap.put( Color.PALEGREEN, new TypeInfo( Type.COLOR, Color.Id.PALEGREEN ) );
         _typeInfoMap.put( Color.PALETURQUOISE, new TypeInfo( Type.COLOR, Color.Id.PALETURQUOISE ) );
         _typeInfoMap.put( Color.PALEVIOLETRED, new TypeInfo( Type.COLOR, Color.Id.PALEVIOLETRED ) );
         _typeInfoMap.put( Color.PAPAYAWHIP, new TypeInfo( Type.COLOR, Color.Id.PAPAYAWHIP ) );
         _typeInfoMap.put( Color.PEACHPUFF, new TypeInfo( Type.COLOR, Color.Id.PEACHPUFF ) );
         _typeInfoMap.put( Color.PERU, new TypeInfo( Type.COLOR, Color.Id.PERU ) );
         _typeInfoMap.put( Color.PINK, new TypeInfo( Type.COLOR, Color.Id.PINK ) );
         _typeInfoMap.put( Color.PLUM, new TypeInfo( Type.COLOR, Color.Id.PLUM ) );
         _typeInfoMap.put( Color.POWDERBLUE, new TypeInfo( Type.COLOR, Color.Id.POWDERBLUE ) );
         _typeInfoMap.put( Color.ROSYBROWN, new TypeInfo( Type.COLOR, Color.Id.ROSYBROWN ) );
         _typeInfoMap.put( Color.ROYALBLUE, new TypeInfo( Type.COLOR, Color.Id.ROYALBLUE ) );
         _typeInfoMap.put( Color.SADDLEBROWN, new TypeInfo( Type.COLOR, Color.Id.SADDLEBROWN ) );
         _typeInfoMap.put( Color.SALMON, new TypeInfo( Type.COLOR, Color.Id.SALMON ) );
         _typeInfoMap.put( Color.SANDYBROWN, new TypeInfo( Type.COLOR, Color.Id.SANDYBROWN ) );
         _typeInfoMap.put( Color.SEAGREEN, new TypeInfo( Type.COLOR, Color.Id.SEAGREEN ) );
         _typeInfoMap.put( Color.SEASHELL, new TypeInfo( Type.COLOR, Color.Id.SEASHELL ) );
         _typeInfoMap.put( Color.SIENNA, new TypeInfo( Type.COLOR, Color.Id.SIENNA ) );
         _typeInfoMap.put( Color.SKYBLUE, new TypeInfo( Type.COLOR, Color.Id.SKYBLUE ) );
         _typeInfoMap.put( Color.SLATEBLUE, new TypeInfo( Type.COLOR, Color.Id.SLATEBLUE ) );
         _typeInfoMap.put( Color.SLATEGRAY, new TypeInfo( Type.COLOR, Color.Id.SLATEGRAY ) );
         _typeInfoMap.put( Color.SNOW, new TypeInfo( Type.COLOR, Color.Id.SNOW ) );
         _typeInfoMap.put( Color.SPRINGGREEN, new TypeInfo( Type.COLOR, Color.Id.SPRINGGREEN ) );
         _typeInfoMap.put( Color.STEELBLUE, new TypeInfo( Type.COLOR, Color.Id.STEELBLUE ) );
         _typeInfoMap.put( Color.TAN, new TypeInfo( Type.COLOR, Color.Id.TAN ) );
         _typeInfoMap.put( Color.THISTLE, new TypeInfo( Type.COLOR, Color.Id.THISTLE ) );
         _typeInfoMap.put( Color.TOMATO, new TypeInfo( Type.COLOR, Color.Id.TOMATO ) );
         _typeInfoMap.put( Color.TURQUOISE, new TypeInfo( Type.COLOR, Color.Id.TURQUOISE ) );
         _typeInfoMap.put( Color.VIOLET, new TypeInfo( Type.COLOR, Color.Id.VIOLET ) );
         _typeInfoMap.put( Color.WHEAT, new TypeInfo( Type.COLOR, Color.Id.WHEAT ) );
         _typeInfoMap.put( Color.WHITESMOKE, new TypeInfo( Type.COLOR, Color.Id.WHITESMOKE ) );
         _typeInfoMap.put( Color.YELLOWGREEN, new TypeInfo( Type.COLOR, Color.Id.YELLOWGREEN ) );

         _typeInfoMap.put( FontFamily.SERIF, new TypeInfo( Type.FONT_FAMILY, FontFamily.Id.SERIF ) );
         _typeInfoMap.put( FontFamily.SANS_SERIF, new TypeInfo( Type.FONT_FAMILY, FontFamily.Id.SANS_SERIF ) );
         _typeInfoMap.put( FontFamily.MONOSPACE, new TypeInfo( Type.FONT_FAMILY, FontFamily.Id.MONOSPACE ) );

         _typeInfoMap.put( FontStyle.ITALIC, new TypeInfo( Type.FONT_STYLE, FontStyle.Id.ITALIC ) );
         _typeInfoMap.put( FontWeight.BOLD, new TypeInfo( Type.FONT_WEIGHT, FontWeight.Id.BOLD ) );
      }

      TypeInfo info = ( TypeInfo ) _typeInfoMap.get( value );
      if( info == null )
         return UNDEFINED_TYPE_INFO;
      else
         return info;
   }

   public static class TypeInfo
   {
      int _major;
      int _minor;

      public TypeInfo( int major, int minor )
      {
         _major = major;
         _minor = minor;
      }

      public int getMajor()
      {
         return _major;
      }

      public int getMinor()
      {
         return _minor;
      }
   }

   public interface Align
   {
      String LEFT = "left";
      String RIGHT = "right";
      String CENTER = "center";

      public interface Id
      {
         int LEFT = 1;
         int RIGHT = 2;
         int CENTER = 3;
      }
   }

   public interface BorderStyle
   {
      String NONE = "none";
      String SOLID = "solid";
      String DOUBLE = "double";
      String GROOVE = "groove";
      String RIDGE = "ridge";
      String INSET = "inset";
      String OUTSET = "outset";

      public interface Id
      {
         int NONE = 0;
         int SOLID = 1;
         int DOUBLE = 2;
         int GROOVE = 3;
         int RIDGE = 4;
         int INSET = 5;
         int OUTSET = 6;
      }
   }

   public interface Color
   {
      String AQUA = "aqua";
      String BLACK = "black";
      String BLUE = "blue";
      String FUCHSIA = "fuchsia";
      String GRAY = "gray";
      String GREEN = "green";
      String LIME = "lime";
      String MAROON = "maroon";
      String NAVY = "navy";
      String OLIVE = "olive";
      String PURPLE = "purple";
      String RED = "red";
      String SILVER = "silver";
      String TEAL = "teal";
      String WHITE = "white";
      String YELLOW = "yellow";

      String ALICEBLUE = "aliceblue";
      String ANTIQUEWHITE = "antiquewhite";
      String AQUAMARINE = "aquamarine";
      String AZURE = "azure";
      String BEIGE = "beige";
      String BISQUE = "bisque";
      String BLANCHEDALMOND = "blanchedalmond";
      String BLUEVIOLET = "blueviolet";
      String BROWN = "brown";
      String BURLYWOOD = "burlywood";
      String CADETBLUE = "cadetblue";
      String CHARTREUSE = "chartreuse";
      String CHOCOLATE = "chocolate";
      String CORAL = "coral";
      String CORNFLOWERBLUE = "cornflowerblue";
      String CORNSILK = "cornsilk";
      String CRIMSON = "crimson";
      String CYAN = "cyan";
      String DARKBLUE = "darkblue";
      String DARKCYAN = "darkcyan";
      String DARKGOLDENROD = "darkgoldenrod";
      String DARKGRAY = "darkgray";
      String DARKGREEN = "darkgreen";
      String DARKKHAKI = "darkkhaki";
      String DARKMAGENTA = "darkmagenta";
      String DARKOLIVEGREEN = "darkolivegreen";
      String DARKORANGE = "darkorange";
      String DARKORCHID = "darkorchid";
      String DARKRED = "darkred";
      String DARKSALMON = "darksalmon";
      String DARKSEAGREEN = "darkseagreen";
      String DARKSLATEBLUE = "darkslateblue";
      String DARKSLATEGRAY = "darkslategray";
      String DARKTURQUOISE = "darkturquoise";
      String DARKVIOLET = "darkviolet";
      String DEEPPINK = "deeppink";
      String DEEPSKYBLUE = "deepskyblue";
      String DIMGRAY = "dimgray";
      String DODGERBLUE = "dodgerblue";
      String FIREBRICK = "firebrick";
      String FLORALWHITE = "floralwhite";
      String FORESTGREEN = "forestgreen";
      String GAINSBORO = "gainsboro";
      String GHOSTWHITE = "ghostwhite";
      String GOLD = "gold";
      String GOLDENROD = "goldenrod";
      String GREENYELLOW = "greenyellow";
      String HONEYDEW = "honeydew";
      String HOTPINK = "hotpink";
      String INDIANRED = "indianred";
      String INDIGO = "indigo";
      String IVORY = "ivory";
      String KHAKI = "khaki";
      String LAVENDER = "lavender";
      String LAVENDERBLUSH = "lavenderblush";
      String LAWNGREEN = "lawngreen";
      String LEMONCHIFFON = "lemonchiffon";
      String LIGHTBLUE = "lightblue";
      String LIGHTCORAL = "lightcoral";
      String LIGHTCYAN = "lighcyan";
      String LIGHTGOLDENRODYELLOW = "lightgoldenrodyellow";
      String LIGHTGREEN = "lightgreen";
      String LIGHTGREY = "lightgrey";
      String LIGHTPINK = "lightpink";
      String LIGHTSALMON = "lightsalmon";
      String LIGHTSEAGREEN = "lightseagreen";
      String LIGHTSKYBLUE = "lightskyblue";
      String LIGHTSLATEGRAY = "lightslategray";
      String LIGHTSTEELBLUE = "lightsteelblue";
      String LIGHTYELLOW = "lightyellow";
      String LIMEGREEN = "limegreen";
      String LINEN = "linen";
      String MAGENTA = "magenta";
      String MEDIUMAQUAMARINE = "mediumaquamarine";
      String MEDIUMBLUE = "mediumblue";
      String MEDIUMORCHID = "mediumorchid";
      String MEDIUMPURPLE = "mediumpurple";
      String MEDIUMSEAGREEN = "mediumseagreen";
      String MEDIUMSLATEBLUE = "mediumslateblue";
      String MEDIUMSPRINGGREEN = "mediumspringgreen";
      String MEDIUMTURQUOISE = "mediumturquoise";
      String MEDIUMVIOLETRED = "mediumvioletred";
      String MIDNIGHTBLUE = "midnightblue";
      String MINTCREAM = "mintcream";
      String MISTYROSE = "mistyrose";
      String MOCCASIN = "moccasin";
      String NAVAJOWHITE = "navajowhite";
      String OLDLACE = "oldlace";
      String OLIVEDRAB = "olivedrab";
      String ORANGE = "orange";
      String ORANGERED = "orangered";
      String ORCHID = "orchid";
      String PALEGOLDENROD = "palegoldenrod";
      String PALEGREEN = "palegreen";
      String PALETURQUOISE = "paleturquoise";
      String PALEVIOLETRED = "palevioletred";
      String PAPAYAWHIP = "papayawhip";
      String PEACHPUFF = "peachpuff";
      String PERU = "peru";
      String PINK = "pink";
      String PLUM = "plum";
      String POWDERBLUE = "powderblue";
      String ROSYBROWN = "rosybrown";
      String ROYALBLUE = "royalblue";
      String SADDLEBROWN = "saddlebrown";
      String SALMON = "salmon";
      String SANDYBROWN = "sandybrown";
      String SEAGREEN = "seagreen";
      String SEASHELL = "seashell";
      String SIENNA = "sienna";
      String SKYBLUE = "skyblue";
      String SLATEBLUE = "slateblue";
      String SLATEGRAY = "slategray";
      String SNOW = "snow";
      String SPRINGGREEN = "springgreen";
      String STEELBLUE = "steelblue";
      String TAN = "tan";
      String THISTLE = "thistle";
      String TOMATO = "tomato";
      String TURQUOISE = "turquoise";
      String VIOLET = "violet";
      String WHEAT = "wheat";
      String WHITESMOKE = "whitesmoke";
      String YELLOWGREEN = "yellowgreen";


      public interface Id
      {
         int AQUA = 1;
         int BLACK = 2;
         int BLUE = 3;
         int FUCHSIA = 4;
         int GRAY = 5;
         int GREEN = 6;
         int LIME = 7;
         int MAROON = 8;
         int NAVY = 9;
         int OLIVE = 10;
         int PURPLE = 11;
         int RED = 12;
         int SILVER = 13;
         int TEAL = 14;
         int WHITE = 15;
         int YELLOW = 16;

         int ALICEBLUE = 17;
         int ANTIQUEWHITE = 18;
         int AQUAMARINE = 19;
         int AZURE = 20;
         int BEIGE = 21;
         int BISQUE = 22;
         int BLANCHEDALMOND = 23;
         int BLUEVIOLET = 24;
         int BROWN = 25;
         int BURLYWOOD = 26;
         int CADETBLUE = 27;
         int CHARTREUSE = 28;
         int CHOCOLATE = 29;
         int CORAL = 30;
         int CORNFLOWERBLUE = 31;
         int CORNSILK = 32;
         int CRIMSON = 33;
         int CYAN = 34;
         int DARKBLUE = 35;
         int DARKCYAN = 36;
         int DARKGOLDENROD = 37;
         int DARKGRAY = 38;
         int DARKGREEN = 39;
         int DARKKHAKI = 40;
         int DARKMAGENTA = 41;
         int DARKOLIVEGREEN = 42;
         int DARKORANGE = 43;
         int DARKORCHID = 44;
         int DARKRED = 45;
         int DARKSALMON = 46;
         int DARKSEAGREEN = 47;
         int DARKSLATEBLUE = 48;
         int DARKSLATEGRAY = 49;
         int DARKTURQUOISE = 50;
         int DARKVIOLET = 51;
         int DEEPPINK = 52;
         int DEEPSKYBLUE = 53;
         int DIMGRAY = 54;
         int DODGERBLUE = 55;
         int FIREBRICK = 56;
         int FLORALWHITE = 57;
         int FORESTGREEN = 58;
         int GAINSBORO = 59;
         int GHOSTWHITE = 60;
         int GOLD = 61;
         int GOLDENROD = 62;
         int GREENYELLOW = 63;
         int HONEYDEW = 64;
         int HOTPINK = 65;
         int INDIANRED = 66;
         int INDIGO = 67;
         int IVORY = 68;
         int KHAKI = 69;
         int LAVENDER = 70;
         int LAVENDERBLUSH = 71;
         int LAWNGREEN = 72;
         int LEMONCHIFFON = 73;
         int LIGHTBLUE = 74;
         int LIGHTCORAL = 75;
         int LIGHTCYAN = 76;
         int LIGHTGOLDENRODYELLOW = 77;
         int LIGHTGREEN = 78;
         int LIGHTGREY = 79;
         int LIGHTPINK = 80;
         int LIGHTSALMON = 81;
         int LIGHTSEAGREEN = 82;
         int LIGHTSKYBLUE = 83;
         int LIGHTSLATEGRAY = 84;
         int LIGHTSTEELBLUE = 85;
         int LIGHTYELLOW = 86;
         int LIMEGREEN = 87;
         int LINEN = 88;
         int MAGENTA = 89;
         int MEDIUMAQUAMARINE = 90;
         int MEDIUMBLUE = 91;
         int MEDIUMORCHID = 92;
         int MEDIUMPURPLE = 93;
         int MEDIUMSEAGREEN = 94;
         int MEDIUMSLATEBLUE = 95;
         int MEDIUMSPRINGGREEN = 96;
         int MEDIUMTURQUOISE = 97;
         int MEDIUMVIOLETRED = 98;
         int MIDNIGHTBLUE = 99;
         int MINTCREAM = 100;
         int MISTYROSE = 101;
         int MOCCASIN = 102;
         int NAVAJOWHITE = 103;
         int OLDLACE = 104;
         int OLIVEDRAB = 105;
         int ORANGE = 106;
         int ORANGERED = 107;
         int ORCHID = 108;
         int PALEGOLDENROD = 109;
         int PALEGREEN = 110;
         int PALETURQUOISE = 111;
         int PALEVIOLETRED = 112;
         int PAPAYAWHIP = 113;
         int PEACHPUFF = 114;
         int PERU = 115;
         int PINK = 116;
         int PLUM = 117;
         int POWDERBLUE = 118;
         int ROSYBROWN = 119;
         int ROYALBLUE = 120;
         int SADDLEBROWN = 121;
         int SALMON = 122;
         int SANDYBROWN = 123;
         int SEAGREEN = 124;
         int SEASHELL = 125;
         int SIENNA = 126;
         int SKYBLUE = 127;
         int SLATEBLUE = 128;
         int SLATEGRAY = 129;
         int SNOW = 130;
         int SPRINGGREEN = 131;
         int STEELBLUE = 132;
         int TAN = 133;
         int THISTLE = 134;
         int TOMATO = 135;
         int TURQUOISE = 136;
         int VIOLET = 137;
         int WHEAT = 138;
         int WHITESMOKE = 139;
         int YELLOWGREEN = 140;
      }
   }

   public interface FontFamily
   {
      String SERIF = "serif";
      String SANS_SERIF = "sans-serif";
      String MONOSPACE = "monospace";

      public interface Id
      {
         int SERIF = 1;
         int SANS_SERIF = 2;
         int MONOSPACE = 3;
      }
   }

   public interface FontStyle
   {
      String ITALIC = "italic";

      public interface Id
      {
         int ITALIC = 1;
      }
   }

   public interface FontWeight
   {
      String BOLD = "bold";

      public interface Id
      {
         int BOLD = 1;
      }
   }

   public interface Property
   {
      String MIN_WIDTH = "min-width";
      String MAX_WIDTH = "max-width";
      String WIDTH = "width";

      String MIN_HEIGHT = "min-height";
      String MAX_HEIGHT = "max-height";
      String HEIGHT = "height";

      String MARGIN = "margin";
      String BORDER = "border";
      String PADDING = "padding";

      String BACKGROUND = "background";
      String COLOR = "color";
      String FONT = "font";

      String ALIGN = "align";
      String VERTICAL_ALIGN = "vertical-align";
   }

   public interface Token
   {

      /**
       *  Represents the EOF lexical unit.
       */
      int EOF = 0;

      /**
       *  Represents the '{' lexical unit.
       */
      int LEFT_CURLY_BRACE = 1;

      /**
       *  Represents the '}' lexical unit.
       */
      int RIGHT_CURLY_BRACE = 2;

      /**
       *  Represents the '=' lexical unit.
       */
      int EQUAL = 3;

      /**
       *  Represents the '+' lexical unit.
       */
      int PLUS = 4;

      /**
       *  Represents the '-' lexical unit.
       */
      int MINUS = 5;

      /**
       *  Represents the ',' lexical unit.
       */
      int COMMA = 6;

      /**
       *  Represents the '.' lexical unit.
       */
      int DOT = 7;

      /**
       *  Represents the ';' lexical unit.
       */
      int SEMI_COLON = 8;

      /**
       *  Represents the '>' lexical unit.
       */
      int PRECEDE = 9;

      /**
       *  Represents the '/' lexical unit.
       */
      int DIVIDE = 10;

      /**
       *  Represents the '[' lexical unit.
       */
      int LEFT_BRACKET = 11;

      /**
       *  Represents the ']' lexical unit.
       */
      int RIGHT_BRACKET = 12;

      /**
       *  Represents the '*' lexical unit.
       */
      int ANY = 13;

      /**
       *  Represents the '(' lexical unit.
       */
      int LEFT_BRACE = 14;

      /**
       *  Represents the ')' lexical unit.
       */
      int RIGHT_BRACE = 15;

      /**
       *  Represents the ':' lexical unit.
       */
      int COLON = 16;

      /**
       *  Represents the white space lexical unit.
       */
      int SPACE = 17;

      /**
       *  Represents the comment lexical unit.
       */
      int COMMENT = 18;

      /**
       *  Represents the string lexical unit.
       */
      int STRING = 19;

      /**
       *  Represents the identifier lexical unit.
       */
      int IDENTIFIER = 20;

      /**
       *  Represents the '<!--' lexical unit.
       */
      int CDO = 21;

      /**
       *  Represents the '-->' lexical unit.
       */
      int CDC = 22;

      /**
       *  Represents the '!important' lexical unit.
       */
      int IMPORTANT_SYMBOL = 23;

      /**
       *  Represents an integer.
       */
      int INTEGER = 24;

      /**
       *  Represents the '|=' lexical unit.
       */
      int DASHMATCH = 25;

      /**
       *  Represents the '~=' lexical unit.
       */
      int INCLUDES = 26;

      /**
       *  Represents the '#name' lexical unit.
       */
      int HASH = 27;

      /**
       *  Represents the '@import' lexical unit.
       */
      int IMPORT_SYMBOL = 28;

      /**
       *  Represents the '@ident' lexical unit.
       */
      int AT_KEYWORD = 29;

      /**
       *  Represents the '@charset' lexical unit.
       */
      int CHARSET_SYMBOL = 30;

      /**
       *  Represents the '@font-face' lexical unit.
       */
      int FONT_FACE_SYMBOL = 31;

      /**
       *  Represents the '@media' lexical unit.
       */
      int MEDIA_SYMBOL = 32;

      /**
       *  Represents the '@page' lexical unit.
       */
      int PAGE_SYMBOL = 33;

      /**
       *  Represents a dimension lexical unit.
       */
      int DIMENSION = 34;

      /**
       *  Represents a ex lexical unit.
       */
      int EX = 35;

      /**
       *  Represents a em lexical unit.
       */
      int EM = 36;

      /**
       *  Represents a cm lexical unit.
       */
      int CM = 37;

      /**
       *  Represents a mm lexical unit.
       */
      int MM = 38;

      /**
       *  Represents a in lexical unit.
       */
      int IN = 39;

      /**
       *  Represents a ms lexical unit.
       */
      int MS = 40;

      /**
       *  Represents a hz lexical unit.
       */
      int HZ = 41;

      /**
       *  Represents a % lexical unit.
       */
      int PERCENTAGE = 42;

      /**
       *  Represents a s lexical unit.
       */
      int S = 43;

      /**
       *  Represents a pc lexical unit.
       */
      int PC = 44;

      /**
       *  Represents a pt lexical unit.
       */
      int PT = 45;

      /**
       *  Represents a px lexical unit.
       */
      int PX = 46;

      /**
       *  Represents a deg lexical unit.
       */
      int DEG = 47;

      /**
       *  Represents a rad lexical unit.
       */
      int RAD = 48;

      /**
       *  Represents a grad lexical unit.
       */
      int GRAD = 49;

      /**
       *  Represents a khz lexical unit.
       */
      int KHZ = 50;

      /**
       *  Represents a 'url(URI)' lexical unit.
       */
      int URI = 51;

      /**
       *  Represents a 'ident(' lexical unit.
       */
      int FUNCTION = 52;

      /**
       *  Represents a unicode range lexical unit.
       */
      int UNICODE_RANGE = 53;

      /**
       *  represents a real number.
       */
      int REAL = 54;
   }

   public interface Type
   {
      int UNDEFINED = 0;
      int BORDER_STYLE = 1;
      int COLOR = 2;
      int FONT_FAMILY = 3;
      int FONT_STYLE = 4;
      int FONT_WEIGHT = 5;
      int ALIGN = 6;
      int VERTICAL_ALIGN = 7;
   }

   public interface VerticalAlign
   {
      String TOP = "top";
      String MIDDLE = "middle";
      String BOTTOM = "bottom";

      public interface Id
      {
         int TOP = 1;
         int MIDDLE = 2;
         int BOTTOM = 3;
      }
   }
}
