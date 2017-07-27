/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.arithmetic;

import com.bestinfo.arithmetic.struct.CipherMeltInfo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Hex;

/**
 *
 * @author sunfan
 */
public class ShortCipherUtil {

    private static final Logger logger = Logger.getLogger(ShortCipherUtil.class);

    //完全按照c的方式返回,但是直接使用该short,会出现负数!!需要放大至int
    public int xmk_crc16(byte[] ptr, int count) {
        int crc, i;
        crc = 0;
        int j = 0;
        while (--count >= 0) {
//            crc = (crc ^ (((int) * ptr++) << 8));
            int temp = (int) (ptr[j++] & 0xff);
            crc = (int) (crc ^ (int) (temp << 8));
            for (i = 0; i < 8; ++i) {
//                if (crc & 0x8000) {
                if ((crc & 0x8000) != 0) {
//                    crc = ((crc << 1) ^ 0x1021);
                    crc = (int) ((crc << 1) ^ 0x1021);
                } else {
//                    crc = crc << 1;
                    crc = (int) (crc << 1);
                }
            }
        }
        return (int) (crc & 0xFFFF);
    }

    private int check_sum(long x) {
        int sum = 0;
//	unsigned char s[32];
//        sprintf((char *) s, "%08x", x);
        String s = new Formatter().format("%08x", (x & 0x00000000ffffffffL)).toString();//yr 改为 0x00000000ffffffffL,原来没有L
//        sum = xmk_crc16(s, 8);
        sum = xmk_crc16(s.getBytes(), 8);
//        System.out.println("sum:" + sum);
        return sum;
    }

    public String make_ltcipherS(int node, int game_id, int period, int seq_no, Calendar tm_time) {
        try {
            long[] shortCipher = make_ltcipher(node, game_id, period, seq_no, tm_time);
            shortCipher[0] = shortCipher[0] & 0x00000000ffffffffL;
            shortCipher[1] = shortCipher[1] & 0x00000000ffffffffL;
            String s = String.format("%08x%08x", shortCipher[0], shortCipher[1]);
            s = s + "xxxxxxxx";
            return s;
        } catch (Exception e) {
            return null;
        }

    }
    
    public int[] make_ltcipherI(int node, int game_id, int period, int seq_no, Calendar tm_time) {
        try {
            long[] shortCipher = make_ltcipher(node, game_id, period, seq_no, tm_time);
            return longArray2IntArray(shortCipher);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 生成彩票密码
     *
     * @param node
     * @param game_id
     * @param period
     * @param seq_no
     * @param tm_time
     * @return
     */
    public long[] make_ltcipher(int node, int game_id, int period, int seq_no, Calendar tm_time) {
        long[] cipher;
        cipher = new long[2];
        long check_code;

        cipher[1] = (long) (game_id % 16);
        cipher[1] *= 30000L;
        cipher[1] += (long) (seq_no % 30000);
        cipher[1] *= 2L;
        cipher[1] += (long) (tm_time.get(Calendar.HOUR_OF_DAY) % 2);
        //System.out.println(tm_time.get(Calendar.HOUR_OF_DAY));
        cipher[1] *= 60L;
        cipher[1] += (long) (tm_time.get(Calendar.MINUTE));
        //System.out.println(tm_time.get(Calendar.MINUTE));
        cipher[1] *= 60L;
        cipher[1] += (long) (tm_time.get(Calendar.SECOND));
        //System.out.println(tm_time.get(Calendar.SECOND));
        node %= 1000000L;
        cipher[0] = node % 500000L;
        cipher[0] *= 1000L;
        cipher[0] += (long) (period % 1000);
        check_code = (long) check_sum(cipher[0]);
        check_code += (long) check_sum(cipher[1]);
        check_code %= 4L;
        cipher[0] *= 4L;
        //System.out.println("check_code:" + check_code);
        cipher[0] += check_code;

        //添加一个位运算,屏蔽最高4字节
        cipher[0] &= 0x00000000ffffffffL;//yr 改为 0x00000000ffffffffL,原来没有L
        cipher[0] ^= 0x80000000L;
        return cipher;
    }

    /**
     * 分解彩票密码,杨荣写
     *
     * @param cipheri
     * @return
     */
    public CipherMeltInfo meltShortCipher(int[] cipheri) {

        long[] cipher = intArray2LongArray(cipheri);
        return meltShortCipher(cipher);

    }

    /**
     * 分解彩票密码,杨荣写
     *
     * @param cipher
     * @return
     */
    public CipherMeltInfo meltShortCipher(long[] cipher) {
        try {

            CipherMeltInfo cmi = new CipherMeltInfo();
            if (cipher == null) {
                logger.error("melt cipher fail ,cipher is null ");
                return null;
            }
            if (cipher.length != 2) {
                logger.error("melt cipher fail,cipher len wrong,len is  " + cipher.length);
                return null;
            }
            cipher[0] &= 0x00000000ffffffffL;
            cipher[1] &= 0x00000000ffffffffL;
            cipher[0] ^= 0x80000000L;
            Long checkCode = cipher[0] % 4;
            cipher[0] = cipher[0] / 4;
            Long checkCode0 = (long) check_sum(cipher[0]);
            Long checkCode1 = (long) check_sum(cipher[1]);
            Long checkCodeA = (checkCode0 + checkCode1) % 4;
            if (checkCode != checkCodeA) {
                logger.error("melt cipher fail,check code is not equal,melt checkCode:" + checkCode + " actual checkCode:" + checkCodeA);
                return null;

            }
            cmi.setCheck_code(checkCode.intValue());
            Long second = cipher[1] % 60;
            cmi.setSecond(second.intValue());
            cipher[1] = cipher[1] / 60;
            Long minute = cipher[1] % 60;
            cmi.setMinute(minute.intValue());
            cipher[1] = cipher[1] / 60;
            Long hour = cipher[1] % 2;
            cmi.setHour(hour.intValue());
            cipher[1] = cipher[1] / 2;
            Long seq = cipher[1] % 30000;
            cmi.setSeq_no(seq.intValue());
            cipher[1] = cipher[1] / 30000;
            Long gameid = cipher[1] % 16;
            cmi.setGame_id(gameid.intValue());
            Long drawno = cipher[0] % 1000;
            cmi.setPeriod(drawno.intValue());
            cipher[0] = cipher[0] / 1000;
            Long node = cipher[0] % 500000;
            cmi.setNode(node.intValue());
            return cmi;
        } catch (Exception ex) {
            logger.error("melt ticket short cipher exception", ex);
            return null;
        }
    }

    public boolean checkShortCipher(int[] cipher, int gameId, int tkDraw, int terminalId, int serialNum, String betTime) {
        try {
            CipherMeltInfo cmi = meltShortCipher(cipher);
            if (cmi == null) {
                logger.error("cipher melt fail,cipher=" + cipher);
                return false;
            }
            if (cmi.getGame_id() != gameId % 16) {
                logger.error("melt game_id is not correct ,melt game id=" + cmi.getGame_id() + " ticket game id =" + gameId);
                return false;
            }
            if (cmi.getPeriod() != tkDraw % 1000) {
                logger.error("melt period is not correct,melt period =" + cmi.getPeriod() + " ticket tk draw=" + tkDraw);
                return false;
            }
            if (cmi.getNode() != (terminalId % 1000000) % 500000) {
                logger.error("melt nod is not correct,melt node=" + cmi.getNode() + "  terminalId=" + terminalId);
                return false;
            }
            if (cmi.getSeq_no() != serialNum % 30000) {
                logger.error("melt seq no is not correct,melt seq no=" + cmi.getSeq_no() + "  ticket serial num=" + serialNum);
                return false;
            }
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cal.setTime(sdf.parse(betTime));
            if (cmi.getSecond() != (cal.get(Calendar.SECOND) % 60) || cmi.getMinute() != (cal.get(Calendar.MINUTE) % 60)
                    || (cal.get(Calendar.HOUR_OF_DAY) % 2) != cmi.getHour()) {
                logger.error("melt bet time is not correct,melt belt  time=" + cmi.getHour() + ":" + cmi.getMinute() + ":" + cmi.getSecond() + " ticket bet time=" + betTime);
                return false;
            }
            return true;
        } catch (ParseException e) {
            logger.error("", e);
            return false;
        }
    }

    /**
     * 生成票面密码,两个10位10进制数
     *
     * @param passwords
     * @return
     */
    public String encryptLtcipher(int[] passwords) {
        try {
            long[] cipher = intArray2LongArray(passwords);
            RC5Provider rc5p = new RC5Provider();
            return rc5p.encryptLtcipher(cipher);
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public String encryptLtcipher(long[] passwords) {
        try {
            RC5Provider rc5p = new RC5Provider();
            return rc5p.encryptLtcipher(passwords);
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public long[] decryptLtcipherL(String ticketCipher) {
        try {
            RC5Provider rc5p = new RC5Provider();
            return rc5p.decryptLtcipher(ticketCipher);
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public int[] decryptLtcipher(String ticketCipher) {
        try {
            RC5Provider rc5p = new RC5Provider();
            long[] cp = rc5p.decryptLtcipher(ticketCipher);
            return longArray2IntArray(cp);
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

///**
// * 和RC5Provider里的encryptLtcipher一样
// * @param passwords
// * @return 
// */
//    public  String convertPwd(long[] passwords) {
//        byte[] key = {(byte) 0x43, (byte) 0xb1, (byte) 0xe6, (byte) 0x5c, (byte) 0x07, (byte) 0x0c, (byte) 0x14, (byte) 0x7b,
//            (byte) 0x6b, (byte) 0xa9, (byte) 0x3e, (byte) 0x52, (byte) 0x55, (byte) 0xf0, (byte) 0x26, (byte) 0x0b};
//        RC5Provider rcpil = new RC5Provider();
//        rcpil.RC5_SETUP(key);
////        rcpil.printS();
//        long[] l = new long[]{passwords[0], passwords[1]};
//        long[] el = rcpil.RC5_ENCRYPT(l);
//        long[] RC5_DECRYPT = rcpil.RC5_DECRYPT(el);
//        String tempS = String.format("%010d%010d", el[0], el[1]);
////        System.out.println(tempS);
//        char[] tempCA = tempS.toCharArray();
//        char c0 = tempCA[0];
//        int c0Resplace = c0 + 5;
//        tempCA[0] = (char) c0Resplace;
//        char c10 = tempCA[10];
//        int c10Replace = c10 + 5;
//        tempCA[10] = (char) c10Replace;
//
//        String finalKey = String.format("%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c",
//                tempCA[0], tempCA[1], tempCA[2], tempCA[3], tempCA[4], tempCA[5], tempCA[6], tempCA[7],
//                tempCA[8], tempCA[9], tempCA[10], tempCA[11], tempCA[12], tempCA[13], tempCA[14], tempCA[15], tempCA[16],
//                tempCA[17], tempCA[18], tempCA[19]);
////        System.out.println("finalKey:" + finalKey);
//        return finalKey;
//    }
    public long[] intArray2LongArray(int[] cipheri) {
        if (cipheri == null) {
            return null;
        }
        long[] cipherl = new long[cipheri.length];
        for (int i = 0; i < cipheri.length; i++) {
            cipherl[i] = cipheri[i] & 0x00000000ffffffffL;
        }
        return cipherl;
    }

    public int[] longArray2IntArray(long[] cipherl) {
        if (cipherl == null) {
            return null;
        }
        int[] cipheri = new int[cipherl.length];
        for (int i = 0; i < cipherl.length; i++) {
            cipheri[i] = (int) (cipherl[i] & 0x00000000ffffffffL);
        }
        return cipheri;
    }

    public static void main(String[] args) {

        long[] lss = new long[0];
        int i1 = 0x8999ffff;
        Long l1 = Long.valueOf(Integer.toString(i1));
        l1 &= 0x00000000ffffffffL;

        String checkCode = null;
        long tm_no = 44035001;
        short game_id = 7;
        short draw_no = 1817;
        short serial_no = 10;
        short tyear = 2015;
        short tmonth = 7;
        short tday = 22;
        short thour = 13;
        short tmin = 27;
        short tsec = 35;
        short bet_mode = 1;
        short rows = 1;
        short cols = 10;
        String stakes = "01020304050607080000";
        byte game_code = 'H';
        String[] spot_no = new String[]{"0", "0", "0", "0", "0"};
        Short[] multi = new Short[]{1, 0, 0, 0, 0};

        /*        System.out.println(pwd.makeTicketChkcode(checkCode, (long)44045999, (short)1, (short)2087, (short)0, 
         (short)(2013 - 1900), (short)8, (short)4, (short)16, (short)46, (short)29, 
         (short)0, (short)1, (short)8, "0203101517232500", (byte)'H', spot_no, multi));
         */
        /*        System.out.println(pwd.makeTicketChkcode(checkCode, (long)44045999, (short)1, (short)2089, (short)0, 
         (short)(2013 - 1900), (short)8, (short)7, (short)15, (short)58, (short)14, 
         (short)0, (short)2, (short)8, "01020304050607001112131415161700", (byte)'H', spot_no, multi));
         */
//        System.out.println(pwd.makeTicketChkcode(tm_no, game_id, draw_no, serial_no,
//                (short) (tyear - 1900), tmonth, tday, thour, tmin, tsec,
//                bet_mode, rows, cols, stakes, game_code, spot_no, multi));
//        Calendar c = new GregorianCalendar();
//        c.set(Calendar.YEAR, 2013);
//        c.set(Calendar.MONTH, 06);
//        c.set(Calendar.DATE, 19);
//        c.set(Calendar.HOUR, 10);
//        c.set(Calendar.MINUTE, 35);
//        c.set(Calendar.SECOND, 3);
//        System.out.println(c);
        try {
            Calendar c2 = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dt = df.parse("2015-09-14 11:05:46");
//        new Date(new Timestamp(2013, 6, 19, 10, 35, 3, 0).getTime());
//        Timestamp te = Timestamp.valueOf("2013-06-19 18:06:38");
//        c2.setTimeInMillis(new Date(new Timestamp(2013, 6, 19, 13, 33, 39, 0).getTime()).getTime());
            c2.setTime(dt);
//
//        //取出终端流水号--------------------------------Integer.parseInt(appHeader.getTerminal_id())
            ShortCipherUtil scu = new ShortCipherUtil();
            long[] pass = scu.make_ltcipher(44015003, 7, 31, 11, c2);
//            int i = 0xcdfe5fff;
//            pass[0] = i & 0x00000000ffffffffL;
            long[] ls = new long[4];
            ls[0] = pass[0];
//        ls[0] = Long.parseLong("2891480637");//2891480637
            System.out.println(String.format("%8x", ls[0]));
            ls[1] = pass[1];
//        ls[1] = Long.parseLong("1512020011");//1512020013
            System.out.println(String.format("%8x", ls[1]));
//            System.out.println(scu.make_ltcipherS(44035002, 6, 1471, 16, c2));
//        ls[2]=0;
//        ls[3]=0;
////        ls[2] = 0;
////        ls[3] = 0;
////        long[] s=new long[]{0xac587e3f,0x5a1fd429};
            String s1 = scu.encryptLtcipher(ls);
            String s2 = scu.encryptLtcipher(pass);
            String s3 = new RC5Provider().encryptLtcipher(pass);
            System.out.println(s2);
            int sl = s3.length();
            String s5 = s3.trim();
            long[] s4 = new RC5Provider().decryptLtcipher(s5);

            CipherMeltInfo cmi = scu.meltShortCipher(pass);
//            int[] scip= scu.decryptLtcipher("44542 23235 67789 09098");
            int[] cip = scu.decryptLtcipher(s2);
            boolean tf = scu.checkShortCipher(cip, 7, 1819, 44035001, 10, "2015-07-22 13:27:35");
            int jk = 2;
        } catch (Exception e) {

        }
    }
}
