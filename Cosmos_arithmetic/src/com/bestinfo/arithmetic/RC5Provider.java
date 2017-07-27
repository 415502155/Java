/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.arithmetic;

import com.bestinfo.arithmetic.struct.RC5SVALUE;

/**
 *
 * @author sunfan
 */
public class RC5Provider {

//    private BigInteger[] S = new BigInteger[RC5SVALUE.t];
    private long[] S = new long[(int) RC5SVALUE.t];
    private long P = 0xb7e15163L, Q = 0x9e3779b9L;  /* magic constants             */


    //#define ROTL(x,y) (((x)<<(y&(w-1))) | ((x)>>(w-(y&(w-1)))))
    public long ROTL(long x, long y) {
//        long r = (long) ((((x & 0x00000000ffffffffL) << ((y & 0x00000000ffffffffL) & ((RC5SVALUE.w - 1L) & 0x00000000ffffffffL)) & 0x00000000ffffffffL) & 0x00000000ffffffffL | ((x & 0x00000000ffffffffL) >>> (RC5SVALUE.w - ((y & 0x00000000ffffffffL) & ((RC5SVALUE.w - 1L) & 0x00000000ffffffffL))) & 0x00000000ffffffffL) & 0x00000000ffffffffL) & 0x00000000ffffffffL);
        long tempx = x;
        long tempy = y;
        long r = (((tempx & 0x00000000ffffffffL) << (tempy & 0x00000000ffffffffL & (RC5SVALUE.w - 1))) & 0x00000000ffffffffL | ((tempx & 0x00000000ffffffffL) >>> (RC5SVALUE.w - (tempy & (RC5SVALUE.w - 1)))) & 0x00000000ffffffffL);
        return r;
    }

    //#define ROTR(x,y) (((x)>>(y&(w-1))) | ((x)<<(w-(y&(w-1)))))
    public long ROTR(long x, long y) {
//        long r = (long) ((((x & 0x00000000ffffffffL) >>> ((y & 0x00000000ffffffffL) & ((RC5SVALUE.w - 1L) & 0x00000000ffffffffL)) & 0x00000000ffffffffL) & 0x00000000ffffffffL | ((x & 0x00000000ffffffffL) << (RC5SVALUE.w - ((y & 0x00000000ffffffffL) & ((RC5SVALUE.w - 1L) & 0x00000000ffffffffL))) & 0x00000000ffffffffL) & 0x00000000ffffffffL) & 0x00000000ffffffffL);
        long tempx = x;
        long tempy = y;
        long r = (((tempx & 0x00000000ffffffffL) >>> (tempy & 0x00000000ffffffffL & (RC5SVALUE.w - 1))) & 0x00000000ffffffffL | ((tempx & 0x00000000ffffffffL) << (RC5SVALUE.w - (tempy & (RC5SVALUE.w - 1)))) & 0x00000000ffffffffL);
        return r;
    }

    public long[] RC5_ENCRYPT(long[] pt) {
        long A = (pt[0] + S[0]), B = (pt[1] + S[1]);
        for (int i = 1; i <= RC5SVALUE.r; i++) {
            A = (ROTL((A) ^ (B), B) + S[2 * i]);
            B = (ROTL((B) ^ (A), A) + S[2 * i + 1]);
        }
        long[] ct = new long[2];
        ct[0] = A & 0x00000000ffffffffL;
        ct[1] = B & 0x00000000ffffffffL;
        return ct;
    }

    public long[] RC5_DECRYPT(long[] ct) {
        long B = ct[1], A = ct[0];
        for (int i = (int) RC5SVALUE.r; i > 0; i--) {
            B = ((ROTR((B - S[2 * i + 1]), A)) ^ (A));
            A = ((ROTR(A - S[2 * i], B)) ^ (B));
        }
        long[] pt = new long[2];
        pt[1] = (B - S[1]) & 0x00000000ffffffffL;
        pt[0] = (A - S[0]) & 0x00000000ffffffffL;
        return pt;
    }

    public void RC5_SETUP(byte[] K) {
        long i, j, k, u = RC5SVALUE.w / 8, A, B;
        long[] L = new long[(int) RC5SVALUE.c];
        /* Initialize L, then S, then mix key into S */
        for (i = ((int) RC5SVALUE.b - 1), L[(int) RC5SVALUE.c - 1] = 0; i != -1; i--) {
//            System.out.println((long) (K[(int) i] & 0x00FF));
            L[(int) i / (int) u] = (long) ((long) (L[(int) i / (int) u] << 8) + (long) (K[(int) i] & 0x00FF));
//            System.out.println("L[" + ((int) i / (int) u) + "]:" + L[(int) i / (int) u]);
        }
        for (S[0] = P, i = 1; i < RC5SVALUE.t; i++) {
            S[(int) i] = (long) (S[(int) i - 1] + Q);
//            System.out.println("S[" + (int) i + "]:" + S[(int) i]);
        }
        for (A = B = i = j = k = 0L; k < 3L * RC5SVALUE.t; k++, i = (i + 1L) % RC5SVALUE.t, j = (j + 1L) % RC5SVALUE.c) {
//            A = S[i] = ROTL(S[i] + (A + B), 3);
            A = S[(int) i] = ROTL((long) (S[(int) i] + (A + B)), 3L);
//            System.out.println("A:" + A);
//            B = L[j] = ROTL(L[j] + (A + B), (A + B));
            B = L[(int) j] = ROTL((long) (L[(int) j] + (long) (A + B)), (A + B));
//            System.out.println("B:" + B);
        }
    }

    public void printS() {
        for (long l : S) {
            System.out.println("S:" + l);
        }
    }

    public static void main(String[] args) {
        byte[] key = {(byte) 0x43, (byte) 0xb1, (byte) 0xe6, (byte) 0x5c, (byte) 0x07, (byte) 0x0c, (byte) 0x14, (byte) 0x7b,
            (byte) 0x6b, (byte) 0xa9, (byte) 0x3e, (byte) 0x52, (byte) 0x55, (byte) 0xf0, (byte) 0x26, (byte) 0x0b};
//        System.out.println((long) (key[9] & 0xff));
        RC5Provider rcpil = new RC5Provider();
        rcpil.RC5_SETUP(key);
//        rcpil.printS();
        long[] l = new long[]{2287488226L, 1512035467L};
        long[] el = rcpil.RC5_ENCRYPT(l);
        System.out.println("encrypted:");
        System.out.println(el[0]);
        System.out.println(el[1]);
        long[] RC5_DECRYPT = rcpil.RC5_DECRYPT(el);
        System.out.println("decrypted:");
        System.out.println(RC5_DECRYPT[0]);
        System.out.println(RC5_DECRYPT[1]);


        String tempS = String.format("%010d%010d", el[0], el[1]);
        System.out.println(tempS);
        char[] tempCA = tempS.toCharArray();
        char c0 = tempCA[0];
        int c0Resplace = c0 + 5;
        tempCA[0] = (char) c0Resplace;
        char c10 = tempCA[10];
        int c10Replace = c10 + 5;
        tempCA[10] = (char) c10Replace;

        String finalKey = String.format("%c%c%c%c%c %c%c%c%c%c %c%c%c%c%c %c%c%c%c%c",
                tempCA[0], tempCA[1], tempCA[2], tempCA[3], tempCA[4], tempCA[5], tempCA[6], tempCA[7],
                tempCA[8], tempCA[9], tempCA[10], tempCA[11], tempCA[12], tempCA[13], tempCA[14], tempCA[15], tempCA[16],
                tempCA[17], tempCA[18], tempCA[19]);
        System.out.println(finalKey);
    }

    public String encryptLtcipher(long[] toBeCiphered) {
        byte[] key = {(byte) 0x43, (byte) 0xb1, (byte) 0xe6, (byte) 0x5c, (byte) 0x07, (byte) 0x0c, (byte) 0x14, (byte) 0x7b,
            (byte) 0x6b, (byte) 0xa9, (byte) 0x3e, (byte) 0x52, (byte) 0x55, (byte) 0xf0, (byte) 0x26, (byte) 0x0b};

        RC5_SETUP(key);
//        long[] l = new long[]{2287488226L, 1512035467L};
        long[] el = RC5_ENCRYPT(toBeCiphered);
//        System.out.println("encrypted:");
//        System.out.println(el[0]);
//        System.out.println(el[1]);
//        long[] RC5_DECRYPT = RC5_DECRYPT(el);
//        System.out.println("decrypted:");
//        System.out.println(RC5_DECRYPT[0]);
//        System.out.println(RC5_DECRYPT[1]);
        String tempS = String.format("%010d%010d", el[0], el[1]);
//        System.out.println(tempS);
        char[] tempCA = tempS.toCharArray();
        char c0 = tempCA[0];
        int c0Resplace = c0 + 5;
        tempCA[0] = (char) c0Resplace;
        char c10 = tempCA[10];
        int c10Replace = c10 + 5;
        tempCA[10] = (char) c10Replace;

        String finalKey = String.format("%c%c%c%c%c %c%c%c%c%c %c%c%c%c%c %c%c%c%c%c",
                tempCA[0], tempCA[1], tempCA[2], tempCA[3], tempCA[4], tempCA[5], tempCA[6], tempCA[7],
                tempCA[8], tempCA[9], tempCA[10], tempCA[11], tempCA[12], tempCA[13], tempCA[14], tempCA[15], tempCA[16],
                tempCA[17], tempCA[18], tempCA[19]);
//        System.out.println(finalKey);
        return finalKey;
    }

    public long[] decryptLtcipher(String s) {
        long[] cipher = new long[2];
        char[] tempArray = s.toCharArray();
        char[] midArray = new char[20];
        midArray[0] = (char) (tempArray[0] - 5);
        midArray[1] = tempArray[1];
        midArray[2] = tempArray[2];
        midArray[3] = tempArray[3];
        midArray[4] = tempArray[4];

        midArray[5] = tempArray[6];
        midArray[6] = tempArray[7];
        midArray[7] = tempArray[8];
        midArray[8] = tempArray[9];
        midArray[9] = tempArray[10];

        midArray[10] = (char) (tempArray[12] - 5);
        midArray[11] = tempArray[13];
        midArray[12] = tempArray[14];
        midArray[13] = tempArray[15];
        midArray[14] = tempArray[16];

        midArray[15] = tempArray[18];
        midArray[16] = tempArray[19];
        midArray[17] = tempArray[20];
        midArray[18] = tempArray[21];
        midArray[19] = tempArray[22];

        String LongValueMidStr = new String(midArray);

        String l1 = LongValueMidStr.substring(0, 10);

        String l2 = LongValueMidStr.substring(10, LongValueMidStr.length());
        cipher[0] = Long.parseLong(l1);
        cipher[1] = Long.parseLong(l2);
        byte[] key = {(byte) 0x43, (byte) 0xb1, (byte) 0xe6, (byte) 0x5c, (byte) 0x07, (byte) 0x0c, (byte) 0x14, (byte) 0x7b,
            (byte) 0x6b, (byte) 0xa9, (byte) 0x3e, (byte) 0x52, (byte) 0x55, (byte) 0xf0, (byte) 0x26, (byte) 0x0b};

        RC5_SETUP(key);

        long[] RC5_DECRYPT = RC5_DECRYPT(cipher);

        return RC5_DECRYPT;
    }
}
