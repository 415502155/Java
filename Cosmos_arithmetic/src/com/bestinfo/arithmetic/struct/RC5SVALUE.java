/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.arithmetic.struct;

/**
 *
 * @author sunfan
 */
public class RC5SVALUE {

    public static final long w = 32L;             /* word size in bits                 */

    public static final long r = 12L;             /* number of rounds                  */

    public static final long b = 16L;             /* number of bytes in key            */

    public static final long c = 4L;             /* number  words in key = ceil(8*b/w)*/

    public static final long t = 26L;            /* size of table S = 2*(r+1) words   */

}
