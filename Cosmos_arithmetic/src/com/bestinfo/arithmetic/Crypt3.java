package com.bestinfo.arithmetic;

import com.bestinfo.arithmetic.struct.CipherMeltInfo;
import com.bestinfo.arithmetic.struct.PktDecData;
import com.bestinfo.arithmetic.struct.province_id;
import com.bestinfo.arithmetic.struct.province_value;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.zip.DeflaterOutputStream;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author YangRong
 */
public class Crypt3 {

    private static final Logger logger = Logger.getLogger(Crypt3.class);
    private static final int SHORT_CIPHER_LEN = 2;
    private static final int LONG_CIPHER_LEN = 3;
    private static final int LONG_TICKET_CIPHER_LEN = 24;
    private static final int SHORT_TICKET_CIPHER_LEN = 23;
    private static final byte[] TICKET_CIPHER_KEY_B = {(byte) 0x38, (byte) 0xbd, (byte) 0xbd, (byte) 0x80, (byte) 0xaa, (byte) 0xa0, (byte) 0xd4,
        (byte) 0xbd, (byte) 0xd6, (byte) 0x90, (byte) 0x18, (byte) 0x8f, (byte) 0xd0, (byte) 0xb2,
        (byte) 0x56, (byte) 0xd0, (byte) 0x93, (byte) 0x92, (byte) 0x2e, (byte) 0x18, (byte) 0xa6,
        (byte) 0x5b, (byte) 0x59, (byte) 0xb1, (byte) 0x28, (byte) 0x7e, (byte) 0xba, (byte) 0x1b,
        (byte) 0x5d, (byte) 0x4d, (byte) 0x83, (byte) 0x63};
    private static final int TICKET_CIPHER_TYPE = 0;
    private static final int MD5_LEN = 16;//md5签名字节数
    private static final int RANDKEY_LEN = 32;//随机密钥长度
//    private static final int PROVINCE_MAX_NUM = 10;
    private static final int FNV_64_PRIME_LOW = 0x1b3;	/* lower bits of FNV prime */

    private static final int FNV_64_PRIME_SHIFT = 8;		/* top FNV prime shift above 2^32 */

    private static final int[] ticket_CRC8 = {0, 94, 188, 226, 97, 63, 221, 131, 194, 156,
        126, 32, 163, 253, 31, 65, 157, 195, 33, 127,
        252, 162, 64, 30, 95, 1, 227, 189, 62, 96,
        130, 220, 35, 125, 159, 193, 66, 28, 254, 160,
        225, 191, 93, 3, 128, 222, 60, 98, 190, 224,
        2, 92, 223, 129, 99, 61, 124, 34, 192, 158,
        29, 67, 161, 255, 70, 24, 250, 164, 39, 121,
        155, 197, 132, 218, 56, 102, 229, 187, 89, 7,
        219, 133, 103, 57, 186, 228, 6, 88, 25, 71,
        165, 251, 120, 38, 196, 154, 101, 59, 217, 135,
        4, 90, 184, 230, 167, 249, 27, 69, 198, 152,
        122, 36, 248, 166, 68, 26, 153, 199, 37, 123,
        58, 100, 134, 216, 91, 5, 231, 185, 140, 210,
        48, 110, 237, 179, 81, 15, 78, 16, 242, 172,
        47, 113, 147, 205, 17, 79, 173, 243, 112, 46,
        204, 146, 211, 141, 111, 49, 178, 236, 14, 80,
        175, 241, 19, 77, 206, 144, 114, 44, 109, 51,
        209, 143, 12, 82, 176, 238, 50, 108, 142, 208,
        83, 13, 239, 177, 240, 174, 76, 18, 145, 207,
        45, 115, 202, 148, 118, 40, 171, 245, 23, 73,
        8, 86, 180, 234, 105, 55, 213, 139, 87, 9,
        235, 181, 54, 104, 138, 212, 149, 203, 41, 119,
        244, 170, 72, 22, 233, 183, 85, 11, 136, 214,
        52, 106, 43, 117, 151, 201, 74, 20, 246, 168,
        116, 42, 200, 150, 21, 75, 169, 247, 182, 232,
        10, 84, 215, 137, 107, 53};

//    public class province {
//
//        public province_id pro_id;
//        public province_value pro_value;
//    };
//    int province_init(province[] pro_struct) {
//        if (pro_struct == null) {
//            return -1;
//        }
//
//        pro_struct[0].pro_id = province_id.PRO_GUANGDONG;
//        pro_struct[0].pro_value = province_value.PRO_GUANGDONG_VALUE;
//        pro_struct[1].pro_id = province_id.PRO_SHANGHAI;
//        pro_struct[1].pro_value = province_value.PRO_SHANGHAI_VALUE;
//
//        return 0;
//    }
//    int get_pro_id_from_pro_value(int pro_value) {
//        province[] pro_struct = new province[PROVINCE_MAX_NUM];
//        int i;
//        province_init(pro_struct);
//
//        for (i = 0; i < PROVINCE_MAX_NUM; i++) {
//            if (pro_struct[i].pro_value.getValue() == pro_value) {
//                return pro_struct[i].pro_id.getId();
//            }
//        }
//
//        return -1;
//    }
    /**
     * 上传报文解密
     *
     * @param pkt 上传报文
     * @param sessionKey 会话密钥
     * @param inv
     * @return
     */
    public PktDecData pktDecrypt(byte[] pkt, byte[] sessionKey, byte[] inv) {
        try {
            if (pkt.length <= (MD5_LEN + RANDKEY_LEN)) {
                logger.error("pkt len is too short,len:" + pkt.length);
                return null;
            }
            PktDecData pdd = new PktDecData();
//            byte[] msgkey_encrypt = new byte[RANDKEY_LEN];
//            System.arraycopy(pkt, pkt.length - RANDKEY_LEN, msgkey_encrypt, 0, RANDKEY_LEN);
            EncrypDES3 des3 = new EncrypDES3();
//            msgKeyBright = des3.Decryptor(msgkey_encrypt, sessionKey);//随机密钥,采用无padding算法,msgkey_encrypt长度必须为8的倍数            
            byte[] a = des3.decryptor(pkt, pkt.length - RANDKEY_LEN, RANDKEY_LEN, sessionKey, inv);
            pdd.setMsgKeyBright(a);
//            logger.info("msg key bright:"+Hex.encodeHexString(pdd.msgKeyBright));
            if (pdd.getMsgKeyBright() == null || pdd.getMsgKeyBright().length == 0) {
                logger.error("fail to decrptor msg random key with seesionKey!" + "  sessionKey:" + Hex.encodeHexString(sessionKey));
                return null;
            }
            RC4 rc4 = new RC4();
//            byte[] msg_md5_encrypt = new byte[pkt.length - RANDKEY_LEN];
//            System.arraycopy(pkt, 0, msg_md5_encrypt, 0, pkt.length - RANDKEY_LEN);
//            byte[] msg_md5 = rc4.RC4BC(msg_md5_encrypt, msgKeyBright);
            byte[] msg_md5 = rc4.RC4BC(pkt, 0, pkt.length - RANDKEY_LEN, pdd.getMsgKeyBright());
            if (msg_md5 == null || msg_md5.length == 0) {
                logger.error("fail to decriptor msg with random key,random key:" + Hex.encodeHexString(pdd.getMsgKeyBright()));
                return null;
            }
            byte[] msg = Arrays.copyOfRange(msg_md5, 0, msg_md5.length - MD5_LEN);
            byte[] md5 = Arrays.copyOfRange(msg_md5, msg_md5.length - MD5_LEN, msg_md5.length);

//            byte[] msg = new byte[msg_md5.length - MD5_LEN];
//            byte[] md5 = new byte[MD5_LEN];
//            System.arraycopy(msg_md5, 0, msg, 0, msg_md5.length - MD5_LEN);
//            System.arraycopy(msg_md5, msg_md5.length - MD5_LEN, md5, 0, MD5_LEN);
            if (!checkDigest(msg, md5)) {
                logger.error("digest is not ok! \nmsg:" + Hex.encodeHexString(msg) + "\ndigest:" + Hex.encodeHexString(md5));
                return null;
            }
//            pdd.pktDecrypt = msg;
            pdd.setPktDecrypt(msg);

            return pdd;
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;

    }

    /**
     * 测试用
     *
     * @param msg
     * @param msgKey
     * @param sessionKey
     * @param inv
     * @return
     */
    public byte[] pktEncryptTest(byte[] msg, byte[] msgKey, byte[] sessionKey, byte[] inv) {
        byte[] msgEnc = pktEncrypt(msg, msgKey);
        EncrypDES3 des3 = new EncrypDES3();
        byte[] msgKeyEnc = des3.encrytor(msgKey, sessionKey, inv);
//        byte[] pkt = Arrays..concatenate(msgEnc, msgKeyEnc);
        byte[] pkt = new byte[msgEnc.length + msgKeyEnc.length];
        System.arraycopy(msgEnc, 0, pkt, 0, msgEnc.length);
        System.arraycopy(msgKeyEnc, 0, pkt, msgEnc.length, msgKeyEnc.length);
        return pkt;

    }

    /**
     * 下送报文加密
     *
     * @param msg 消息体
     * @param msgKey 随机密钥明文
     * @return
     */
    public byte[] pktEncrypt(byte[] msg, byte[] msgKey) {
        try {
            if (msg == null) {
                logger.error("msg is null");
                return null;
            }
            if (msgKey == null) {
                logger.error("msgKey is null");
                return null;
            }
            if (msgKey.length != RANDKEY_LEN) {
                logger.error("msgKey len is not correcct,len:" + msgKey.length);
                return null;
            }
            byte[] md5 = DigestUtils.md5(msg);
            if (md5 == null) {
                logger.error("md5 digest fail,msg=" + Hex.encodeHexString(md5));
                return null;
            }
//            byte[] msg_md5 =Arrays.concatenate(msg, md5);
            byte[] msg_md5 = new byte[msg.length + md5.length];
            System.arraycopy(msg, 0, msg_md5, 0, msg.length);
            System.arraycopy(md5, 0, msg_md5, msg.length, MD5_LEN);
            RC4 rc4 = new RC4();
//            byte[] msg_md5_encrypt = rc4.RC4Base(msg_md5, msgKey);
            byte[] msg_md5_encrypt = rc4.RC4BC(msg_md5, msgKey);
            if (msg_md5_encrypt == null || msg_md5_encrypt.length == 0) {
                logger.error("des3 encrytor error,msg_md5:" + Hex.encodeHexString(msg_md5)
                        + " msgKey:" + Hex.encodeHexString(msgKey));
            }
            return msg_md5_encrypt;
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;

    }

    public byte[] pktDecryptTest(byte[] pkt, byte[] randKey) {
        try {
            RC4 rc4 = new RC4();
            byte[] dpkt = rc4.RC4BC(pkt, randKey);
            byte[] src = new byte[dpkt.length - MD5_LEN];
            System.arraycopy(dpkt, 0, src, 0, src.length);
            byte[] md5 = new byte[MD5_LEN];
            System.arraycopy(dpkt, src.length, md5, 0, MD5_LEN);
            if (checkDigest(src, md5)) {
                return src;
            } else {
                logger.error("packg md5 is not ok");
                return null;
            }
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

    }

    /**
     * 校验MD5是否一致
     *
     * @param msg
     * @param md5
     * @return
     */
    public boolean checkDigest(byte[] msg, byte[] md5) {
        byte[] bt = DigestUtils.md5(msg);
        return Arrays.equals(md5, bt);
//        if (bt == null || bt.length != md5.length) {
//            return false;
//        }
//        for (int i = 0; i < bt.length; i++) {
//            if (bt[i] != md5[i]) {
//                return false;
//            }
//
//        }
//        return true;
    }

    /**
     * 生成CRC码
     *
     * @param s
     * @return
     */
    static int ticketGetCrc8(byte[] s) {
        int crc, tmp;
        int i;
        crc = 0;
        for (i = 0; i < s.length; i++) {
            tmp = crc;
            int k = s[i]; //java byte类型带符号
            k = k & 0xff;
            tmp ^= k;
            crc = ticket_CRC8[tmp];
        }
        return (crc);
    }

    /**
     * 根据cipher HEX字符串，还原生成时用到的相关信息
     *
     * @param cipherS
     * @return
     */
    public CipherMeltInfo meltTicketCipherS(String cipherS) {
        try {
            int[] cipher = hexStringToInts(cipherS);
            return meltTicketCipher(cipher);
        } catch (Exception e) {
            logger.error("sring cipher:" + cipherS, e);
            return null;
        }
    }

    public boolean checkCipher(String cipherS, int gameId, int tkDraw, int terminalId, int serialNum, String betTime) {
        try {
            int[] cipher = hexStringToInts(cipherS);
            return checkCipher(cipher, gameId, tkDraw, terminalId, serialNum, betTime);
        } catch (Exception e) {
            logger.error("", e);
            return false;
        }
    }
    /**
     * 校验密码
     *
     * @param cipherS
     * @param gameId
     * @param draw 大期号
     * @param tkDraw 快开游戏:快开期号 普通游戏:大期号
     * @param terminalId
     * @param serialNum
     * @param betTime
     * @return
     */
    public boolean checkCipher(String cipherS, int gameId, int draw, int tkDraw, int terminalId, int serialNum, String betTime) {
        try {

            int[] cipher = hexStringToInts(cipherS);
            if (cipher.length == SHORT_CIPHER_LEN) {
                return new ShortCipherUtil().checkShortCipher(cipher, gameId, draw, terminalId, serialNum, betTime);
            } else if (cipher.length == LONG_CIPHER_LEN) {
                return checkLongCipher(cipher, gameId, tkDraw, terminalId, serialNum, betTime);
            }
            return false;
        } catch (Exception e) {
            logger.error("", e);
            return false;
        }
    }
        /**
     * 校验密码
     *
     * @param cipher
     * @param gameId
     * @param tkDraw
     * @param terminalId
     * @param serialNum
     * @param betTime
     * @return
     */
    public boolean checkLongCipher(int[] cipher, int gameId, int tkDraw, int terminalId, int serialNum, String betTime) {
        try {
            CipherMeltInfo cmi = meltTicketCipher(cipher);
            if (cmi == null) {
                logger.error("cipher melt fail,cipher=" + intsToHexString(cipher));
                return false;
            }
            int pro_id ;
            int tmpv = terminalId / 1000000;
            if (tmpv == province_value.PRO_GUANGDONG_VALUE.getValue()) {
                pro_id = province_id.PRO_GUANGDONG.getId();
            } else if (tmpv == province_value.PRO_SHANGHAI_VALUE.getValue()) {
                pro_id = province_id.PRO_SHANGHAI.getId();
            } else {
                logger.error(" province id from terminalId is error! terminalId=" + terminalId);
                return false;
            }
            if (cmi.getPro_id() != pro_id) {
                logger.error("province id is not eaual with cipher,melt pro_id=" + cmi.getPro_id() + ",pro_id from terminalId=" + pro_id);
                return false;
            }

            if (cmi.getGame_id() != gameId % 32) {
                logger.error("melt game_id is not correct ,melt game id=" + cmi.getGame_id() + " ticket game id =" + gameId);
                return false;
            }
            if (cmi.getPeriod() != tkDraw % 10000) {
                logger.error("melt period is not correct,melt period =" + cmi.getPeriod() + " ticket tk draw=" + tkDraw);
                return false;
            }
            if (cmi.getNode() != terminalId % 1000000) {
                logger.error("melt nod is not correct,melt node=" + cmi.getNode() + "  terminalId=" + terminalId);
                return false;
            }
            if (cmi.getSeq_no() != serialNum % 60000) {
                logger.error("melt seq no is not correct,melt seq no=" + cmi.getSeq_no() + "  ticket serial num=" + serialNum);
                return false;
            }
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cal.setTime(sdf.parse(betTime));
            if (cmi.getSecond() != (cal.get(Calendar.SECOND) % 60) || cmi.getMinute() != (cal.get(Calendar.MINUTE) % 60)
                    || (cal.get(Calendar.HOUR_OF_DAY) % 24) != cmi.getHour() || (cal.get(Calendar.DAY_OF_MONTH) % 31) != cmi.getDay()
                    || (cal.get(Calendar.MONTH) % 12) != cmi.getMonth() || (cal.get(Calendar.YEAR) % 20) != cmi.getYear()) {
                logger.error("melt bet time is not correct,melt belt  time=" + cmi.getYear() + " " + cmi.getMonth() + " " + cmi.getDay()
                        + " " + cmi.getHour() + " " + cmi.getMinute() + " " + cmi.getSecond() + " ticket bet time=" + betTime);
                return false;
            }
            return true;
        } catch (ParseException e) {
            logger.error("", e);
            return false;
        }
    }

    /**
     * 校验密码
     *
     * @param cipher
     * @param gameId
     * @param tkDraw
     * @param terminalId
     * @param serialNum
     * @param betTime
     * @return
     */
    public boolean checkCipher(int[] cipher, int gameId, int tkDraw, int terminalId, int serialNum, String betTime) {
        try {
            CipherMeltInfo cmi = meltTicketCipher(cipher);
            if (cmi == null) {
                logger.error("cipher melt fail,cipher=" + intsToHexString(cipher));
                return false;
            }
            if (cmi.getGame_id() != gameId % 32) {
                logger.error("melt game_id is not correct ,melt game id=" + cmi.getGame_id() + " ticket game id =" + gameId);
                return false;
            }
            if (cmi.getPeriod() != tkDraw % 10000) {
                logger.error("melt period is not correct,melt period =" + cmi.getPeriod() + " ticket tk draw=" + tkDraw);
                return false;
            }
            if (cmi.getNode() != terminalId % 1000000) {
                logger.error("melt nod is not correct,melt node=" + cmi.getNode() + "  terminalId=" + terminalId);
                return false;
            }
            if (cmi.getSeq_no() != serialNum % 60000) {
                logger.error("melt seq no is not correct,melt seq no=" + cmi.getSeq_no() + "  ticket serial num=" + serialNum);
                return false;
            }
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cal.setTime(sdf.parse(betTime));
            if (cmi.getSecond() != (cal.get(Calendar.SECOND) % 60) || cmi.getMinute() != (cal.get(Calendar.MINUTE) % 60)
                    || (cal.get(Calendar.HOUR_OF_DAY) % 24) != cmi.getHour() || (cal.get(Calendar.DAY_OF_MONTH) % 31) != cmi.getDay()
                    || (cal.get(Calendar.MONTH) % 12) != cmi.getMonth() || (cal.get(Calendar.YEAR) % 20) != cmi.getYear()) {
                logger.error("melt bet time is not correct,melt belt  time=" + cmi.getYear() + " " + cmi.getMonth() + " " + cmi.getDay()
                        + " " + cmi.getHour() + " " + cmi.getMinute() + " " + cmi.getSecond() + " ticket bet time=" + betTime);
                return false;
            }
            return true;
        } catch (ParseException e) {
            logger.error("", e);
            return false;
        }
    }

    /**
     * 根据cipher，还原生成时用到的相关信息
     *
     * @param cipher int数组　长度：４
     * @return CipherMeltInfo
     */
    public CipherMeltInfo meltTicketCipher(int[] cipher) {
        try {
            BigInteger bn;
            CipherMeltInfo cmi = new CipherMeltInfo();
//            Calendar cal = Calendar.getInstance();

            byte[] buf = new byte[12];
            for (int i = 0; i < 3; i++) {
                System.arraycopy(intToBytes(cipher[i]), 0, buf, i * 4, 4);
            }
            bn = new BigInteger(buf);
            BigInteger[] tmp_bn;
            tmp_bn = bn.divideAndRemainder(new BigInteger("256"));
            int check_code = tmp_bn[1].intValue();
            int check_code1 = ticketGetCrc8(Hex.encodeHexString(tmp_bn[0].toByteArray()).toUpperCase().getBytes());
            if (check_code != check_code1) {
                logger.error("check_code is not same!check_code:" + check_code + " check_code1:" + check_code1);
                return null;
            }
            cmi.setCheck_code(check_code);

            tmp_bn = tmp_bn[0].divideAndRemainder(new BigInteger("10"));
            cmi.setPro_id(tmp_bn[1].intValue());
            tmp_bn = tmp_bn[0].divideAndRemainder(new BigInteger("20"));
            int yr = tmp_bn[1].intValue();
            cmi.setYear(yr);
//            if(yr<14){
//                yr=yr+20;
//            }
//            cal.set(Calendar.YEAR,yr+2000  ); 
            tmp_bn = tmp_bn[0].divideAndRemainder(new BigInteger("12"));
//            cal.set(Calendar.MONTH, tmp_bn[1].intValue());
            cmi.setMonth(tmp_bn[1].intValue());
            tmp_bn = tmp_bn[0].divideAndRemainder(new BigInteger("31"));
            cmi.setDay(tmp_bn[1].intValue());
//            int ti = tmp_bn[1].intValue();
//            if (ti == 0) {
//                ti = 31;
//            }
//            cal.set(Calendar.DAY_OF_MONTH, ti);
            tmp_bn = tmp_bn[0].divideAndRemainder(new BigInteger("24"));
//            cal.set(Calendar.HOUR_OF_DAY, tmp_bn[1].intValue());
            cmi.setHour(tmp_bn[1].intValue());
            tmp_bn = tmp_bn[0].divideAndRemainder(new BigInteger("10000"));
            cmi.setPeriod(tmp_bn[1].intValue());
            tmp_bn = tmp_bn[0].divideAndRemainder(new BigInteger("32"));
            int game_id = tmp_bn[1].intValue();
//            if (game_id == 0) {
//                game_id = 32;
//            }
            cmi.setGame_id(game_id);
            tmp_bn = tmp_bn[0].divideAndRemainder(new BigInteger("1000000"));//YR 2014.10.21 原来500000
            cmi.setNode(tmp_bn[1].intValue());
            tmp_bn = tmp_bn[0].divideAndRemainder(new BigInteger("60000"));
            cmi.setSeq_no(tmp_bn[1].intValue());
            tmp_bn = tmp_bn[0].divideAndRemainder(new BigInteger("60"));
            cmi.setMinute(tmp_bn[1].intValue());
            cmi.setSecond((tmp_bn[0].intValue() - 1) % 60);
//            cal.set(Calendar.MINUTE, tmp_bn[1].intValue());
//            tmp_bn = tmp_bn[0].divideAndRemainder(new BigInteger("60"));
//            cal.set(Calendar.SECOND,(tmp_bn[0].intValue()-1)%60);
//            cmi.setTm_time(cal.getTime());

//            
//            //YR 2014.10.21 add
//            if (cmi.getPro_id() == province_id.PRO_GUANGDONG.getId()) {
//                int node = cmi.getNode();
//                cmi.setNode(province_value.PRO_GUANGDONG_VALUE.getValue() * 1000000 + node);
//            } else if (cmi.getPro_id() == province_id.PRO_SHANGHAI.getId()) {
//                int node = cmi.getNode();
//                cmi.setNode(province_value.PRO_SHANGHAI_VALUE.getValue() * 1000000 + node);
//            } else {
//                return null;
//            }
            return cmi;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }
//    public CipherMeltInfo melt_ticket_cipher(int[] cipher) {
//        try {
//            BigInteger bn;
//            CipherMeltInfo cmi = new CipherMeltInfo();
//            Calendar cal = Calendar.getInstance();
//
//            byte[] buf = new byte[12];
//            for (int i = 0; i < 3; i++) {
//                System.arraycopy(intToBytes(cipher[i]), 0, buf, i * 4, 4);
//            }
//            bn = new BigInteger(buf);
//            BigInteger[] tmp_bn;
//            tmp_bn = bn.divideAndRemainder(new BigInteger("256"));
//            int check_code = tmp_bn[1].intValue();
//            int check_code1 = ticket_get_crc8(Hex.encodeHexString(tmp_bn[0].toByteArray()).toUpperCase().getBytes());
//            if (check_code != check_code1) {
//                logger.error("check_code is not same!check_code:" + check_code + " check_code1:" + check_code1);
//                return null;
//            }
//            cmi.setCheck_code(check_code);
//            tmp_bn = tmp_bn[0].divideAndRemainder(new BigInteger("50"));//YR 2014.10.21 原来100
//            cal.set(Calendar.YEAR, tmp_bn[1].intValue() + 2000);//YR 2014.10.21 add 2000
//            tmp_bn = tmp_bn[0].divideAndRemainder(new BigInteger("12"));
//            cal.set(Calendar.MONTH, tmp_bn[1].intValue());
//            tmp_bn = tmp_bn[0].divideAndRemainder(new BigInteger("31"));
//            int ti = tmp_bn[1].intValue();
//            if (ti == 0) {
//                ti = 31;
//            }
//            cal.set(Calendar.DAY_OF_MONTH, ti);
//            tmp_bn = tmp_bn[0].divideAndRemainder(new BigInteger("24"));
//            cal.set(Calendar.HOUR_OF_DAY, tmp_bn[1].intValue());
//            tmp_bn = tmp_bn[0].divideAndRemainder(new BigInteger("60"));
//            cal.set(Calendar.MINUTE, tmp_bn[1].intValue());
//            tmp_bn = tmp_bn[0].divideAndRemainder(new BigInteger("60"));
//            cal.set(Calendar.SECOND, tmp_bn[1].intValue());
//            cmi.setTm_time(cal.getTime());
//            tmp_bn = tmp_bn[0].divideAndRemainder(new BigInteger("10000"));
//            cmi.setPeriod(tmp_bn[1].intValue());
//            tmp_bn = tmp_bn[0].divideAndRemainder(new BigInteger("32"));
//            int game_id = tmp_bn[1].intValue();
//            if (game_id == 0) {
//                game_id = 32;
//            }
//            cmi.setGame_id(game_id);
//            tmp_bn = tmp_bn[0].divideAndRemainder(new BigInteger("60000"));
//            cmi.setSeq_no(tmp_bn[1].intValue());
//            tmp_bn = tmp_bn[0].divideAndRemainder(new BigInteger("1000000"));//YR 2014.10.21 原来500000
//            cmi.setNode(tmp_bn[1].intValue());
//            tmp_bn = tmp_bn[0].divideAndRemainder(new BigInteger("10"));
//            cmi.setPro_id(tmp_bn[1].intValue());
//            //YR 2014.10.21 add
//            if (cmi.getPro_id() == province_id.PRO_GUANGDONG.getId()) {
//                int node = cmi.getNode();
//                cmi.setNode(province_value.PRO_GUANGDONG_VALUE.getValue() * 1000000 + node);
//            } else if (cmi.getPro_id() == province_id.PRO_SHANGHAI.getId()) {
//                int node = cmi.getNode();
//                cmi.setNode(province_value.PRO_SHANGHAI_VALUE.getValue() * 1000000 + node);
//            } else {
//                return null;
//            }
//
//            return cmi;
//        } catch (Exception e) {
//            logger.error("", e);
//            return null;
//        }
//    }

    /**
     * 生成彩票密码16进制字符串
     *
     * @param node
     * @param game_id
     * @param period
     * @param seq_no
     * @param tm_time
     * @return
     */
    public String makeTicketCipherS(int node, int game_id, int period, int seq_no, Date tm_time) {
        try {
            int[] cipher = makeTicketCipher(node, game_id, period, seq_no, tm_time);
            if (cipher == null) {
                return null;
            }
            String cipherS = intsToHexString(cipher);
            return cipherS;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 生成彩票cipher
     *
     * @param node 站点
     * @param game_id 游戏id
     * @param period 期号
     * @param seq_no 流水号
     * @param tm_time 时间
     * @return int数组，长度：４
     */
    public int[] makeTicketCipher(int node, int game_id, int period, int seq_no, Date tm_time) {
        try {
            int[] cipher;
            BigInteger bn;
            Integer tmp_ul;
            int pro_id;
            int tmpv;
            tmpv = node / 1000000;
            if (tmpv == province_value.PRO_GUANGDONG_VALUE.getValue()) {
                pro_id = province_id.PRO_GUANGDONG.getId();
            } else if (tmpv == province_value.PRO_SHANGHAI_VALUE.getValue()) {
                pro_id = province_id.PRO_SHANGHAI.getId();
            } else {
                logger.error(" get province id error! ");
                return null;
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(tm_time);

            tmp_ul = cal.get(Calendar.SECOND) + 1;
            bn = new BigInteger(tmp_ul.toString());

            bn = bn.multiply(new BigInteger("60"));
            tmp_ul = cal.get(Calendar.MINUTE);
            bn = bn.add(new BigInteger(tmp_ul.toString()));

            bn = bn.multiply(new BigInteger("60000"));
            tmp_ul = seq_no % 60000;
            bn = bn.add(new BigInteger(tmp_ul.toString()));

            bn = bn.multiply(new BigInteger("1000000"));//原来500000
            tmp_ul = node % 1000000;
            bn = bn.add(new BigInteger(tmp_ul.toString()));

            bn = bn.multiply(new BigInteger("32"));
            tmp_ul = game_id % 32;
            bn = bn.add(new BigInteger(tmp_ul.toString()));

            bn = bn.multiply(new BigInteger("10000"));
            tmp_ul = period % 10000;
            bn = bn.add(new BigInteger(tmp_ul.toString()));

            bn = bn.multiply(new BigInteger("24"));
            tmp_ul = cal.get(Calendar.HOUR_OF_DAY) % 24;
            bn = bn.add(new BigInteger(tmp_ul.toString()));

            bn = bn.multiply(new BigInteger("31"));
            tmp_ul = cal.get(Calendar.DAY_OF_MONTH) % 31;
            bn = bn.add(new BigInteger(tmp_ul.toString()));

            bn = bn.multiply(new BigInteger("12"));
            tmp_ul = cal.get(Calendar.MONTH) % 12;
            bn = bn.add(new BigInteger(tmp_ul.toString()));

            bn = bn.multiply(new BigInteger("20"));//原来100
            tmp_ul = cal.get(Calendar.YEAR) % 20;
            bn = bn.add(new BigInteger(tmp_ul.toString()));

            bn = bn.multiply(new BigInteger("10"));
            tmp_ul = pro_id % 10;
            bn = bn.add(new BigInteger(tmp_ul.toString()));

            String s = Hex.encodeHexString(bn.toByteArray()).toUpperCase();
            int check_code = ticketGetCrc8(s.getBytes());//big endian 不知道对不对
            bn = bn.multiply(new BigInteger("256"));
            tmp_ul = check_code % 256;//原程序300
            bn = bn.add(new BigInteger(tmp_ul.toString()));
            byte[] hex_str = bn.toByteArray(); //原程序还转成了字符串

            if (hex_str.length <= 8 || hex_str.length > 12) {
                logger.error("len too big !len=" + hex_str.length);
                return null;
            }
            cipher = new int[3];
            byte[] tmp = new byte[4];
            System.arraycopy(hex_str, hex_str.length - 4, tmp, 0, 4);
            cipher[2] = bytesToInt(tmp);
            tmp = new byte[4];
            System.arraycopy(hex_str, hex_str.length - 8, tmp, 0, 4);
            cipher[1] = bytesToInt(tmp);
            tmp = new byte[4];
            System.arraycopy(hex_str, 0, tmp, 12 - hex_str.length, hex_str.length - 8);
            cipher[0] = bytesToInt(tmp);
            return cipher;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

    }
//    public int[] make_ticket_cipher(int node, int game_id, int period, int seq_no, Date tm_time) {
//        try {
//            int[] cipher;
//            BigInteger bn;
//            Integer tmp_ul;
//            int pro_id;
//            //pro_id = get_pro_id_from_pro_value(node / 1000000);
//            int tmpv;
//            tmpv = node / 1000000;
//            if (tmpv == province_value.PRO_GUANGDONG_VALUE.getValue()) {
//                pro_id = province_id.PRO_GUANGDONG.getId();
//            } else if (tmpv == province_value.PRO_SHANGHAI_VALUE.getValue()) {
//                pro_id = province_id.PRO_SHANGHAI.getId();
//            } else {
//                logger.error(" get province id error! ");
//                return null;
//            }
//            tmp_ul = pro_id % 10;
//            bn = new BigInteger(tmp_ul.toString());
//            bn = bn.multiply(new BigInteger("1000000"));//原来500000
//            tmp_ul = node % 1000000;
//            bn = bn.add(new BigInteger(tmp_ul.toString()));
//
//            bn = bn.multiply(new BigInteger("60000"));
//            tmp_ul = seq_no % 60000;
//            bn = bn.add(new BigInteger(tmp_ul.toString()));
//
//            bn = bn.multiply(new BigInteger("32"));
//            tmp_ul = game_id % 32;
//            bn = bn.add(new BigInteger(tmp_ul.toString()));
//
//            bn = bn.multiply(new BigInteger("10000"));
//            tmp_ul = period % 10000;
//            bn = bn.add(new BigInteger(tmp_ul.toString()));
//
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(tm_time);
//
//            bn = bn.multiply(new BigInteger("60"));
//            tmp_ul = cal.get(Calendar.SECOND);
//            bn = bn.add(new BigInteger(tmp_ul.toString()));
//
//            bn = bn.multiply(new BigInteger("60"));
//            tmp_ul = cal.get(Calendar.MINUTE);
//            bn = bn.add(new BigInteger(tmp_ul.toString()));
//
//            bn = bn.multiply(new BigInteger("24"));
//            tmp_ul = cal.get(Calendar.HOUR_OF_DAY) % 24;
//            bn = bn.add(new BigInteger(tmp_ul.toString()));
//
//            bn = bn.multiply(new BigInteger("31"));
//            tmp_ul = cal.get(Calendar.DAY_OF_MONTH) % 31;
//            bn = bn.add(new BigInteger(tmp_ul.toString()));
//
//            bn = bn.multiply(new BigInteger("12"));
//            tmp_ul = cal.get(Calendar.MONTH) % 12;
//            bn = bn.add(new BigInteger(tmp_ul.toString()));
//
//            bn = bn.multiply(new BigInteger("50"));//原来100
//            tmp_ul = cal.get(Calendar.YEAR) % 50;
//            bn = bn.add(new BigInteger(tmp_ul.toString()));
//
//            String s = Hex.encodeHexString(bn.toByteArray()).toUpperCase();
//            int check_code = ticket_get_crc8(s.getBytes());//big endian 不知道对不对
//            bn = bn.multiply(new BigInteger("256"));
//            tmp_ul = check_code % 300;//原程序300
//            bn = bn.add(new BigInteger(tmp_ul.toString()));
//            byte[] hex_str = bn.toByteArray(); //原程序还转成了字符串
//
//            if (hex_str.length <= 8 || hex_str.length > 12) {
//                logger.error("len too big !len=" + hex_str.length);
//                return null;
//            }
//            cipher = new int[3];
//            byte[] tmp = new byte[4];
//            System.arraycopy(hex_str, hex_str.length - 4, tmp, 0, 4);
//            cipher[2] = bytesToInt(tmp);
//            tmp = new byte[4];
//            System.arraycopy(hex_str, hex_str.length - 8, tmp, 0, 4);
//            cipher[1] = bytesToInt(tmp);
//            tmp = new byte[4];
//            System.arraycopy(hex_str, 0, tmp, 12 - hex_str.length, hex_str.length - 8);
//            cipher[0] = bytesToInt(tmp);
//            return cipher;
//        } catch (Exception e) {
//            logger.error("", e);
//            return null;
//        }
//
//    }

    /**
     * 彩票密码加密
     *
     * @param key_type
     * @param key
     * @param cipherS 彩票密码16进制字符串
     * @return
     */
    private String encryptTicketCipherS(int key_type, String key, String cipherS) {
        try {
            int[] cipher = hexStringToInts(cipherS);
            return encryptTicketCipher(key_type, key, cipher);
        } catch (Exception e) {
            logger.error("String cipher:" + cipherS);
            return null;
        }

    }

    /**
     * 彩票密码加密
     *
     * @param cipherS 彩票密码16进制字符串
     * @return
     */
    public String encryptTicketCipherS(String cipherS) {
        try {
            int[] cipher = hexStringToInts(cipherS);
            return encryptTicketCipher(TICKET_CIPHER_TYPE, TICKET_CIPHER_KEY_B, cipher);
        } catch (Exception e) {
            logger.error("String cipher:" + cipherS);
            return null;
        }

    }

    private String encryptTicketCipherS(int key_type, byte[] key, String cipherS) {
        try {
            int[] cipher = hexStringToInts(cipherS);
            return encryptTicketCipher(key_type, key, cipher);
        } catch (Exception e) {
            logger.error("String cipher:" + cipherS, e);
            return null;
        }

    }

    /**
     * 彩票cipher加密
     *
     * @param key_type 类型　１：头字节+0x80
     * @param key 密钥字符串
     * @param cipher 彩票密码
     * @return 加密后的１６进制字符串 24个字符
     */
    private String encryptTicketCipher(int key_type, String key, Integer[] cipher) {
        try {
            String s;
            byte high_byte;
            byte[] tmp_buf;
            byte[] buf = new byte[11];
            tmp_buf = intToBytes(cipher[0]);
            high_byte = tmp_buf[0];
            if (key_type == 1) {
                high_byte += 0x80;
            }
            int p = 0;
            System.arraycopy(tmp_buf, 1, buf, p, 3);
            p = p + 3;
            tmp_buf = intToBytes(cipher[1]);
            System.arraycopy(tmp_buf, 0, buf, p, 4);
            p = p + 4;
            tmp_buf = intToBytes(cipher[2]);
            System.arraycopy(tmp_buf, 0, buf, p, 4);
            RC4 rc4 = new RC4();
//            s = rc4.encry_RC4(buf, key);
            s = rc4.RC4BCS(buf, key);
            String hbs = Integer.toHexString(high_byte & 0xFF);
            if (hbs.length() == 1) {
                hbs = '0' + hbs;
            }
            s = hbs + s;
            return s.toUpperCase();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

    }

    /**
     * 彩票cipher加密
     *
     * @param key_type 类型　１：头字节+0x80
     * @param key 密钥字符串
     * @param cipher 彩票密码
     * @return 加密后的１６进制字符串 24个字符
     */
    private String encryptTicketCipher(int key_type, String key, int[] cipher) {
        try {
            String s;
            byte high_byte;
            byte[] tmp_buf;
            byte[] buf = new byte[11];
            tmp_buf = intToBytes(cipher[0]);
            high_byte = tmp_buf[0];
            if (key_type == 1) {
                high_byte += 0x80;
            }
            int p = 0;
            System.arraycopy(tmp_buf, 1, buf, p, 3);
            p = p + 3;
            tmp_buf = intToBytes(cipher[1]);
            System.arraycopy(tmp_buf, 0, buf, p, 4);
            p = p + 4;
            tmp_buf = intToBytes(cipher[2]);
            System.arraycopy(tmp_buf, 0, buf, p, 4);
            RC4 rc4 = new RC4();
//            s = rc4.encry_RC4(buf, key);
            s = rc4.RC4BCS(buf, key);
            String hbs = Integer.toHexString(high_byte & 0xFF);
            if (hbs.length() == 1) {
                hbs = '0' + hbs;
            }
            s = hbs + s;
            return s.toUpperCase();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

    }

    private String encryptTicketCipher(int key_type, byte[] key, int[] cipher) {
        try {
            String s;
            byte high_byte;
            byte[] tmp_buf;
            byte[] buf = new byte[11];
            tmp_buf = intToBytes(cipher[0]);
            high_byte = tmp_buf[0];
            if (key_type == 1) {
                high_byte += 0x80;
            }
            int p = 0;
            System.arraycopy(tmp_buf, 1, buf, p, 3);
            p = p + 3;
            tmp_buf = intToBytes(cipher[1]);
            System.arraycopy(tmp_buf, 0, buf, p, 4);
            p = p + 4;
            tmp_buf = intToBytes(cipher[2]);
            System.arraycopy(tmp_buf, 0, buf, p, 4);
            RC4 rc4 = new RC4();
//            s = rc4.encry_RC4(buf, key);
            s = rc4.RC4BCS(buf, key);
            String hbs = Integer.toHexString(high_byte & 0xFF);
            if (hbs.length() == 1) {
                hbs = '0' + hbs;
            }
            s = hbs + s;
            return s.toUpperCase();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

    }

    /**
     * 票面cipher解密,返回彩票密码16进制字符串
     *
     * @param key
     * @param s
     * @return
     */
    public String decryptTicketCipherS(String key, String s) {
        try {
            int[] cipher = decryptTicketCipher(key, s);
            if (cipher == null) {
                return null;
            }
            String cipherS = intsToHexString(cipher);
            return cipherS;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 票面cipher解密,返回彩票密码16进制字符串,key用类的内部变量
     *
     * @param s
     * @return
     */
    public String decryptTicketCipherS(String s) {
        try {
            int[] cipher = decryptTicketCipher(TICKET_CIPHER_KEY_B, s);
            if (cipher == null) {
                return null;
            }
            String cipherS = intsToHexString(cipher);
            return cipherS;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 票面cipher解密
     *
     * @param key 密钥
     * @param s 加密的１６进制字符串
     * @return 彩票密码　int[4]数组
     */
    private int[] decryptTicketCipher(byte[] key, String s) {
        try {
            byte[] buf;
            byte high_byte = (byte) Integer.parseInt(s.substring(0, 2), 16);
            if ((high_byte & 0x80) != 0) {
                high_byte -= 0x80;
            }
//            DataChange dc = new DataChange();//
//            buf = dc.HexString2Bytes(s.substring(2));//22字符->11个字节
            RC4 rc4 = new RC4();
//            String ds = rc4.encry_RC4(buf, key);
//            buf = dc.HexString2Bytes(ds);

//            buf = rc4.decry_RC4_byte(s.substring(2), key);
            buf = rc4.RC4BC(Hex.decodeHex(s.substring(2).toCharArray()), key);
            byte[] tmp_buf = new byte[12];
            tmp_buf[0] = high_byte;
            System.arraycopy(buf, 0, tmp_buf, 1, 11);
            int[] cipher = new int[3];
            cipher[0] = bytesToInt(tmp_buf, 0);
            cipher[1] = bytesToInt(tmp_buf, 4);
            cipher[2] = bytesToInt(tmp_buf, 8);
            return cipher;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

    }

    /**
     * 票面cipher解密
     *
     * @param key 密钥字符串
     * @param s 加密的１６进制字符串
     * @return 彩票密码　int[4]数组
     */
    private int[] decryptTicketCipher(String key, String s) {
        try {
            byte[] buf;
            byte high_byte = (byte) Integer.parseInt(s.substring(0, 2), 16);
            if ((high_byte & 0x80) != 0) {
                high_byte -= 0x80;
            }
//            DataChange dc = new DataChange();//
//            buf = dc.HexString2Bytes(s.substring(2));//22字符->11个字节
            RC4 rc4 = new RC4();
//            String ds = rc4.encry_RC4(buf, key);
//            buf = dc.HexString2Bytes(ds);

//            buf = rc4.decry_RC4_byte(s.substring(2), key);
            buf = rc4.RC4BC(Hex.decodeHex(s.substring(2).toCharArray()), key);
            byte[] tmp_buf = new byte[12];
            tmp_buf[0] = high_byte;
            System.arraycopy(buf, 0, tmp_buf, 1, 11);
            int[] cipher = new int[3];
            cipher[0] = bytesToInt(tmp_buf, 0);
            cipher[1] = bytesToInt(tmp_buf, 4);
            cipher[2] = bytesToInt(tmp_buf, 8);
            return cipher;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

    }

//    /*
//     * fnv_64_buf - perform a 64 bit Fowler/Noll/Vo hash on a buffer
//     *
//     * input:
//     *	buf	- start of buffer to hash
//     *	len	- length of buffer in octets
//     *	hval	- previous hash value or 0 if first call
//     *
//     * returns:
//     *	64 bit hash as a static hash type
//     *
//     * NOTE: To use the 64 bit FNV-0 historic hash, use FNV0_64_INIT as the hval
//     *	 argument on the first call to either fnv_64_buf() or fnv_64_str().
//     *
//     * NOTE: To use the recommended 64 bit FNV-1 hash, use FNV1_64_INIT as the hval
//     *	 argument on the first call to either fnv_64_buf() or fnv_64_str().
//     */
//    //无用，测试用
//    private long fnv64aBufLong(byte[] buf, long hval) {
//        long[] val = new long[4];
//        long[] tmp = new long[4];
//
//        val[0] = (hval & 0xffffffffL);
//        val[1] = (val[0] >>> 16);
//        val[0] &= 0xffffL;
//        val[2] = ((hval >>> 32) & 0xffffffffL);
//        val[3] = (val[2] >>> 16);
//        val[2] &= 0xffffL;
//        for (int i = 0; i < buf.length; i++) {
//            long k = buf[i];
//            k = k & 0xffL;
//            val[0] ^= k;
//            val[0] = val[0] & 0xffffffffL;
//            /*
//             * multiply by the 64 bit FNV magic prime mod 2^64
//             *
//             * Using 0x100000001b3 we have the following digits base 2^16:
//             *
//             *	0x0	0x100	0x0	0x1b3
//             *
//             * which is the same as:
//             *
//             *	0x0	1<<FNV_64_PRIME_SHIFT	0x0	FNV_64_PRIME_LOW
//             */
//            /* multiply by the lowest order digit base 2^16 */
//            tmp[0] = ((val[0] & 0xffffffffL) * FNV_64_PRIME_LOW) & 0xffffffffL;
//            tmp[1] = ((val[1] & 0xffffffffL) * FNV_64_PRIME_LOW) & 0xffffffffL;
//            tmp[2] = ((val[2] & 0xffffffffL) * FNV_64_PRIME_LOW) & 0xffffffffL;
//            tmp[3] = ((val[3] & 0xffffffffL) * FNV_64_PRIME_LOW) & 0xffffffffL;
//            /* multiply by the other non-zero digit */
//            tmp[2] = (tmp[2] + (val[0] << FNV_64_PRIME_SHIFT) & 0xffffffffL) & 0xffffffffL;	/* tmp[2] += val[0] * 0x100 */
//
//            tmp[3] = (tmp[3] + (val[1] << FNV_64_PRIME_SHIFT) & 0xffffffffL) & 0xffffffffL;	/* tmp[3] += val[1] * 0x100 */
//            /* propagate carries */
//
//            tmp[1] = (tmp[1] + (tmp[0] >>> 16)) & 0xffffffffL;
//            val[0] = tmp[0] & 0xffffL;
//            tmp[2] = (tmp[2] + (tmp[1] >>> 16)) & 0xffffffffL;
//            val[1] = tmp[1] & 0xffffL;
//            val[3] = (tmp[3] + (tmp[2] >>> 16));
//            val[2] = tmp[2] & 0xffffL;
//            /*
//             * Doing a val[3] &= 0xffff; is not really needed since it simply
//             * removes multiples of 2^64.  We can discard these excess bits
//             * outside of the loop when we convert to Fnv64_t.
//             */
//
//            /* xor the bottom with the current octet */
//        }
//        long tmp_long = (((val[3] & 0xffffL) << 16) | val[2]) & 0xffffffffL;
//        long tmp_long1 = ((val[1] << 16) | val[0]) & 0xffffffffL;
//        return (tmp_long << 32) | tmp_long1;
//    }
    /*
     * fnv_64_buf - perform a 64 bit Fowler/Noll/Vo hash on a buffer
     *
     * input:
     *	buf	- start of buffer to hash
     *	len	- length of buffer in octets
     *	hval	- previous hash value or 0 if first call
     *
     * returns:
     *	64 bit hash as a static hash type
     *
     * NOTE: To use the 64 bit FNV-0 historic hash, use FNV0_64_INIT as the hval
     *	 argument on the first call to either fnv_64_buf() or fnv_64_str().
     *
     * NOTE: To use the recommended 64 bit FNV-1 hash, use FNV1_64_INIT as the hval
     *	 argument on the first call to either fnv_64_buf() or fnv_64_str().
     */
    //
    private byte[] fnv64aBufLong(byte[] buf, long hval) {
        long[] val = new long[4];
        long[] tmp = new long[4];

        val[0] = (hval & 0xffffffffL);
        val[1] = (val[0] >>> 16);
        val[0] &= 0xffffL;
        val[2] = ((hval >>> 32) & 0xffffffffL);
        val[3] = (val[2] >>> 16);
        val[2] &= 0xffffL;
        for (int i = 0; i < buf.length; i++) {
            long k = buf[i];
            k = k & 0xffL;
            val[0] ^= k;
            val[0] = val[0] & 0xffffffffL;
            /*
             * multiply by the 64 bit FNV magic prime mod 2^64
             *
             * Using 0x100000001b3 we have the following digits base 2^16:
             *
             *	0x0	0x100	0x0	0x1b3
             *
             * which is the same as:
             *
             *	0x0	1<<FNV_64_PRIME_SHIFT	0x0	FNV_64_PRIME_LOW
             */
            /* multiply by the lowest order digit base 2^16 */
            tmp[0] = ((val[0] & 0xffffffffL) * FNV_64_PRIME_LOW) & 0xffffffffL;
            tmp[1] = ((val[1] & 0xffffffffL) * FNV_64_PRIME_LOW) & 0xffffffffL;
            tmp[2] = ((val[2] & 0xffffffffL) * FNV_64_PRIME_LOW) & 0xffffffffL;
            tmp[3] = ((val[3] & 0xffffffffL) * FNV_64_PRIME_LOW) & 0xffffffffL;
            /* multiply by the other non-zero digit */
            tmp[2] = (tmp[2] + (val[0] << FNV_64_PRIME_SHIFT) & 0xffffffffL) & 0xffffffffL;	/* tmp[2] += val[0] * 0x100 */

            tmp[3] = (tmp[3] + (val[1] << FNV_64_PRIME_SHIFT) & 0xffffffffL) & 0xffffffffL;	/* tmp[3] += val[1] * 0x100 */
            /* propagate carries */

            tmp[1] = (tmp[1] + (tmp[0] >>> 16)) & 0xffffffffL;
            val[0] = tmp[0] & 0xffffL;
            tmp[2] = (tmp[2] + (tmp[1] >>> 16)) & 0xffffffffL;
            val[1] = tmp[1] & 0xffffL;
            val[3] = (tmp[3] + (tmp[2] >>> 16));
            val[2] = tmp[2] & 0xffffL;
            /*
             * Doing a val[3] &= 0xffff; is not really needed since it simply
             * removes multiples of 2^64.  We can discard these excess bits
             * outside of the loop when we convert to Fnv64_t.
             */

            /* xor the bottom with the current octet */
        }
        byte[] tmpByte = new byte[8];

        tmpByte[0] = (byte) ((val[1] >>> 8) & 0xffL);
        tmpByte[1] = (byte) (val[1] & 0xffL);
        tmpByte[2] = (byte) ((val[0] >>> 8) & 0xffL);
        tmpByte[3] = (byte) (val[0] & 0xffL);
        tmpByte[4] = (byte) ((val[3] >>> 8) & 0xffL);
        tmpByte[5] = (byte) (val[3] & 0xffL);
        tmpByte[6] = (byte) ((val[2] >>> 8) & 0xffL);
        tmpByte[7] = (byte) (val[2] & 0xffL);
        return tmpByte;
//        long tmp_long1 = (((val[3] & 0xffffL) << 16) | val[2]) & 0xffffffffL;
//        long tmp_long0 = ((val[1] << 16) | val[0]) & 0xffffffffL;
//        return (tmp_long0 << 32) | tmp_long1;
    }

    /*
     * fnv_64a_buf - perform a 64 bit Fowler/Noll/Vo FNV-1a hash on a buffer
     *
     * input:
     *	buf	- start of buffer to hash
     *	len	- length of buffer in octets
     *	hval	- previous hash value or 0 if first call
     *
     * returns:
     *	64 bit hash as a static hash type
     *
     * NOTE: To use the recommended 64 bit FNV-1a hash, use FNV1A_64_INIT as the
     * 	 hval arg on the first call to either fnv_64a_buf() or fnv_64a_str().
     */
    /**
     * 生成64位hash值 a算法
     *
     * @param　buf 字节数组
     * @param hval 初始hash
     * @return 64bit hash
     */
    public long fnv64aBuf(byte[] buf, long hval) {
        int[] val = new int[4];
        int[] tmp = new int[4];

        val[0] = (int) (hval & 0xffffffffL);
        val[1] = (val[0] >>> 16);
        val[0] &= 0xffff;
        val[2] = (int) ((hval >>> 32) & 0xffffffffL);
        val[3] = (val[2] >>> 16);
        val[2] &= 0xffff;
        for (int i = 0; i < buf.length; i++) {

            /* xor the bottom with the current octet */
            int k = (int) buf[i];
            k = k & 0xff;

            val[0] ^= k;

            /*
             * multiply by the 64 bit FNV magic prime mod 2^64
             *
             * Using 0x100000001b3 we have the following digits base 2^16:
             *
             *	0x0	0x100	0x0	0x1b3
             *
             * which is the same as:
             *
             *	0x0	1<<FNV_64_PRIME_SHIFT	0x0	FNV_64_PRIME_LOW
             */
            /* multiply by the lowest order digit base 2^16 */
            tmp[0] = val[0] * FNV_64_PRIME_LOW;
            tmp[1] = val[1] * FNV_64_PRIME_LOW;
            tmp[2] = val[2] * FNV_64_PRIME_LOW;
            tmp[3] = val[3] * FNV_64_PRIME_LOW;
            /* multiply by the other non-zero digit */
            tmp[2] += val[0] << FNV_64_PRIME_SHIFT;	/* tmp[2] += val[0] * 0x100 */

            tmp[3] += val[1] << FNV_64_PRIME_SHIFT;	/* tmp[3] += val[1] * 0x100 */
            /* propagate carries */

            tmp[1] += (tmp[0] >>> 16);
            val[0] = tmp[0] & 0xffff;
            tmp[2] += (tmp[1] >>> 16);
            val[1] = tmp[1] & 0xffff;
            val[3] = tmp[3] + (tmp[2] >>> 16);
            val[2] = tmp[2] & 0xffff;
            val[3] &= 0xffff;//c没有这句,java int带符号,加上好像更保险 ,测试过,有没有这句,结果都一样
            /*
             * Doing a val[3] &= 0xffff; is not really needed since it simply
             * removes multiples of 2^64.  We can discard these excess bits
             * outside of the loop when we convert to Fnv64_t.
             */
        }
        long tmp_long = ((long) (((val[3] & 0xffff) << 16) | val[2])) & 0xffffffffL;
        long tmp_long1 = ((long) ((val[1] << 16) | val[0])) & 0xffffffffL;
        return (tmp_long << 32) | tmp_long1;
    }

    /**
     * 生成64位hash值
     *
     * @param　buf 字节数组
     * @param hval 初始hash
     * @return 64bit hash
     */
    public long fnv64Buf(byte[] buf, long hval) {
        int[] val = new int[4];
        int[] tmp = new int[4];

        val[0] = (int) (hval & 0xffffffffL);
        val[1] = (val[0] >>> 16);
        val[0] &= 0xffff;
        val[2] = (int) ((hval >>> 32) & 0xffffffffL);
        val[3] = (val[2] >>> 16);
        val[2] &= 0xffff;
        for (int i = 0; i < buf.length; i++) {
            /*
             * multiply by the 64 bit FNV magic prime mod 2^64
             *
             * Using 0x100000001b3 we have the following digits base 2^16:
             *
             *	0x0	0x100	0x0	0x1b3
             *
             * which is the same as:
             *
             *	0x0	1<<FNV_64_PRIME_SHIFT	0x0	FNV_64_PRIME_LOW
             */
            /* multiply by the lowest order digit base 2^16 */
            tmp[0] = val[0] * FNV_64_PRIME_LOW;
            tmp[1] = val[1] * FNV_64_PRIME_LOW;
            tmp[2] = val[2] * FNV_64_PRIME_LOW;
            tmp[3] = val[3] * FNV_64_PRIME_LOW;
            /* multiply by the other non-zero digit */
            tmp[2] += val[0] << FNV_64_PRIME_SHIFT;	/* tmp[2] += val[0] * 0x100 */

            tmp[3] += val[1] << FNV_64_PRIME_SHIFT;	/* tmp[3] += val[1] * 0x100 */
            /* propagate carries */

            tmp[1] += (tmp[0] >>> 16);
            val[0] = tmp[0] & 0xffff;
            tmp[2] += (tmp[1] >>> 16);
            val[1] = tmp[1] & 0xffff;
            val[3] = tmp[3] + (tmp[2] >>> 16);
            val[2] = tmp[2] & 0xffff;
            val[3] &= 0xffff;
            /*
             * Doing a val[3] &= 0xffff; is not really needed since it simply
             * removes multiples of 2^64.  We can discard these excess bits
             * outside of the loop when we convert to Fnv64_t.
             */
            /* xor the bottom with the current octet */
            int k = (int) buf[i];
            k = k & 0xff;
            val[0] ^= k;
        }
        long tmp_long = ((long) (((val[3] & 0xffff) << 16) | val[2])) & 0xffffffffL;
        long tmp_long1 = ((long) ((val[1] << 16) | val[0])) & 0xffffffffL;
        return (tmp_long << 32) | tmp_long1;
    }

//    public static byte[] longToBytes(long data) {
//        byte[] bytes = new byte[8];
//        bytes[7] = (byte) (data & 0xff);
//        bytes[6] = (byte) ((data >> 8) & 0xff);
//        bytes[5] = (byte) ((data >> 16) & 0xff);
//        bytes[4] = (byte) ((data >> 24) & 0xff);
//        bytes[3] = (byte) ((data >> 32) & 0xff);
//        bytes[2] = (byte) ((data >> 40) & 0xff);
//        bytes[1] = (byte) ((data >> 48) & 0xff);
//        bytes[0] = (byte) ((data >> 56) & 0xff);
//        return bytes;
//    }
    private static byte[] intToBytes(int n) {
        byte[] b = new byte[4];
        b[3] = (byte) (n & 0xff);
        b[2] = (byte) (n >>> 8 & 0xff);
        b[1] = (byte) (n >>> 16 & 0xff);
        b[0] = (byte) (n >>> 24 & 0xff);
        return b;
    }

    private static int bytesToInt(byte b[]) {
        return b[3] & 0xff
                | (b[2] & 0xff) << 8
                | (b[1] & 0xff) << 16
                | (b[0] & 0xff) << 24;
    }

    private static int bytesToInt(byte b[], int startIndex) {
        return b[3 + startIndex] & 0xff
                | (b[2 + startIndex] & 0xff) << 8
                | (b[1 + startIndex] & 0xff) << 16
                | (b[0 + startIndex] & 0xff) << 24;
    }

//    public String bytesToHexString(byte[] src) {
//        StringBuilder stringBuilder = new StringBuilder("");
//        if (src == null || src.length <= 0) {
//            return null;
//        }
//        for (int i = 0; i < src.length; i++) {
//            int v = src[i] & 0xFF;
//            String hv = Integer.toHexString(v);
//            if (hv.length() < 2) {
//                stringBuilder.append(0);
//            }
////            stringBuilder.append(hv + " ");
//            stringBuilder.append(hv);
//        }
//        return stringBuilder.toString();
//    }
    public int[] hexStringToInts(String src) {
        try {
            if (src == null || src.length() <= 7) {
                return null;
            }
            src = src.trim();
            int[] res = new int[src.length() / 8];
//            DataChange dc = new DataChange();
//            byte[] bt = dc.HexString2Bytes(src);
            byte[] bt = Hex.decodeHex(src.toCharArray());
            for (int i = 0; i < bt.length / 4; i++) {
                res[i] = bytesToInt(bt, i * 4);
            }
            return res;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public String intsToHexString(Integer[] src) {
        try {
            StringBuilder stringBuilder = new StringBuilder("");
            if (src == null || src.length <= 0) {
                return null;
            }
            for (int i = 0; i < src.length; i++) {
                byte[] bts = intToBytes(src[i]);
                String hv = Hex.encodeHexString(bts);
                stringBuilder.append(hv);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public String intsToHexString(int[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            byte[] bts = intToBytes(src[i]);
            String hv = Hex.encodeHexString(bts);
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    private final byte[] comm_deskey_key = {(byte) 0x5a, (byte) 0x82, (byte) 0x79, (byte) 0x99, (byte) 75, (byte) 159, (byte) 208, (byte) 94,
        (byte) 4, (byte) 24, (byte) 164, (byte) 236, (byte) 194, (byte) 224, (byte) 65, (byte) 110,
        (byte) 15, (byte) 81, (byte) 203, (byte) 204, (byte) 36, (byte) 145, (byte) 175, (byte) 80};
    private final byte[] comm_deskey_iv = {(byte) 150, (byte) 26, (byte) 210, (byte) 113, (byte) 90, (byte) 21, (byte) 73, (byte) 116};
    private static final int EVP_MAX_MD_SIZE = 64;	/* longest known is SHA512 */

    private static final int EVP_MAX_KEY_LENGTH = 64;
    private static final int EVP_MAX_IV_LENGTH = 16;
    private static final int DESKEY_LEN = (EVP_MAX_IV_LENGTH + EVP_MAX_KEY_LENGTH + EVP_MAX_MD_SIZE);
    private static final int DESKEY_RANDNUM_LEN = (EVP_MAX_IV_LENGTH + EVP_MAX_KEY_LENGTH);
    private static final int SESSIONKEY_LEN = 24;
    private static final int INV_LEN = 8;

    public String pktEncryptDes3S(String src) {
        try {
            byte[] pktEnc = pktEncryptDes3(src.getBytes("UTF-8"), comm_deskey_key, comm_deskey_iv);
            return Base64.encodeBase64String(pktEnc);
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    public String pktDecryptDes3S(String src) {
        try {
            byte[] pktEnc = Base64.decodeBase64(src);
            byte[] pktdec = pktDecryptDes3(pktEnc, comm_deskey_key, comm_deskey_iv);
            return new String(pktdec, "UTF-8");
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    public byte[] pktEncryptDes3(byte[] src, byte[] desKey, byte[] inv) {
        try {
            if (src == null || src.length == 0 || desKey == null
                    || desKey.length != comm_deskey_key.length || inv.length != comm_deskey_iv.length) {
                return null;
            }
            IvParameterSpec iv = new IvParameterSpec(inv);  //初始化向量
            SecretKey sk = new SecretKeySpec(desKey, "DESede");
            Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");//CBC模式,填充
            c.init(Cipher.ENCRYPT_MODE, sk, iv);
            byte[] cipherByte = c.doFinal(src);
            return cipherByte;
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;

    }

    public byte[] pktDecryptDes3(byte[] src, byte[] desKey, byte[] inv) {
        try {
            if (src == null || src.length == 0 || desKey == null
                    || desKey.length != comm_deskey_key.length || inv.length != comm_deskey_iv.length) {
                return null;
            }
            IvParameterSpec iv = new IvParameterSpec(inv);  //初始化向量
            SecretKey sk = new SecretKeySpec(desKey, "DESede");
            Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");//CBC模式,填充
            c.init(Cipher.DECRYPT_MODE, sk, iv);
            byte[] cipherByte = c.doFinal(src);
            return cipherByte;
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;

    }

    public byte[] getDesKey(byte[] meltKey) {
        if (meltKey.length != DESKEY_LEN) {
            return null;
        }
//        return Arrays.copyOfRange(meltKey, EVP_MAX_IV_LENGTH, EVP_MAX_IV_LENGTH + comm_deskey_key.length);
        return Arrays.copyOfRange(meltKey, 0, SESSIONKEY_LEN);
    }

    public byte[] getDesInv(byte[] meltKey) {
        if (meltKey.length != DESKEY_LEN) {
            return null;
        }
//        return Arrays.copyOfRange(meltKey, 0, comm_deskey_iv.length);
        return Arrays.copyOfRange(meltKey, SESSIONKEY_LEN, SESSIONKEY_LEN + INV_LEN);
    }

    public byte[] makeCommDeskey(byte[] desKey) {
        try {
            if (desKey == null || desKey.length != DESKEY_RANDNUM_LEN) {
                logger.error("input deskey is not correct");
                return null;
            }
            byte[] md5 = DigestUtils.md5(desKey);
            byte[] ckey = new byte[DESKEY_LEN];
            System.arraycopy(desKey, 0, ckey, 0, desKey.length);
            System.arraycopy(md5, 0, ckey, DESKEY_RANDNUM_LEN, MD5_LEN);
            IvParameterSpec iv = new IvParameterSpec(comm_deskey_iv);  //初始化向量
            SecretKey skey = new SecretKeySpec(comm_deskey_key, "DESede");
            Cipher c = Cipher.getInstance("DESede/CBC/NoPadding");//CBC模式,无填充
            c.init(Cipher.ENCRYPT_MODE, skey, iv);
            byte[] cipherByte = c.doFinal(ckey);
            return cipherByte;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public byte[] meltCommDeskey(byte[] ckey) {
        try {
            if (ckey.length != DESKEY_LEN) {
                logger.error("key len is not correct,keylen=" + ckey.length);
                return null;
            }
//            Security.addProvider(new com.sun.crypto.provider.SunJCE());
            IvParameterSpec iv = new IvParameterSpec(comm_deskey_iv);  //初始化向量
            SecretKey deskey = new SecretKeySpec(comm_deskey_key, "DESede");
            Cipher c = Cipher.getInstance("DESede/CBC/NoPadding");//CBC模式,无填充
            c.init(Cipher.DECRYPT_MODE, deskey, iv);
            byte[] cipherByte = c.doFinal(ckey);
//            byte[] cipherByte1= c.update(ckey,0,DESKEY_LEN);
//            byte[] cipherByte = c.doFinal(cipherByte1);

            byte[] deskeyRand = Arrays.copyOfRange(cipherByte, 0, DESKEY_RANDNUM_LEN);
            byte[] deskeyRandMd5 = Arrays.copyOfRange(cipherByte, DESKEY_RANDNUM_LEN, DESKEY_RANDNUM_LEN + MD5_LEN);
            if (checkDigest(deskeyRand, deskeyRandMd5) == false) {
                logger.error("melt deskey md5 fail");
                return null;
            }
            return cipherByte;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

    }

    /**
     * 产生指定长度随机字符串
     *
     * @param length
     * @return
     */
    public String randomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }

        return buf.toString();
    }

//    public int[] make_ticket_cipher_test(int pro_id, int node, int game_id, int period, int seq_no, Date tm_time) {
//        try {
//            int[] cipher;
//            BigInteger bn;
//            Integer tmp_ul;
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(tm_time);
//            
//            tmp_ul = cal.get(Calendar.SECOND) + 1;
//            bn = new BigInteger(tmp_ul.toString());
//            
//            bn = bn.multiply(new BigInteger("60"));
//            tmp_ul = cal.get(Calendar.MINUTE);
////            tmp_ul=59;
//            bn = bn.add(new BigInteger(tmp_ul.toString()));
//            
//            bn = bn.multiply(new BigInteger("60000"));
//            tmp_ul = seq_no % 60000;
////            tmp_ul=59999;
//            bn = bn.add(new BigInteger(tmp_ul.toString()));
//            
//            bn = bn.multiply(new BigInteger("1000000"));//原来500000
//            tmp_ul = node % 1000000;
////            tmp_ul=1000000-1;
//            bn = bn.add(new BigInteger(tmp_ul.toString()));
//            
//            bn = bn.multiply(new BigInteger("32"));
//            tmp_ul = game_id % 32;
////            tmp_ul=31;
//            bn = bn.add(new BigInteger(tmp_ul.toString()));
//            
//            bn = bn.multiply(new BigInteger("10000"));
//            tmp_ul = period % 10000;
////            tmp_ul=9999;
//            bn = bn.add(new BigInteger(tmp_ul.toString()));
//            
//            bn = bn.multiply(new BigInteger("24"));
//            tmp_ul = cal.get(Calendar.HOUR_OF_DAY) % 24;
////            tmp_ul=23;
//            bn = bn.add(new BigInteger(tmp_ul.toString()));
//            
//            bn = bn.multiply(new BigInteger("31"));
//            tmp_ul = cal.get(Calendar.DAY_OF_MONTH) % 31;
////            tmp_ul=30;
//            bn = bn.add(new BigInteger(tmp_ul.toString()));
//            
//            bn = bn.multiply(new BigInteger("12"));
//            tmp_ul = cal.get(Calendar.MONTH) % 12;
////            tmp_ul=11;
//            bn = bn.add(new BigInteger(tmp_ul.toString()));
//            
//            bn = bn.multiply(new BigInteger("20"));//原来100
//            tmp_ul = cal.get(Calendar.YEAR) % 20;
////            tmp_ul=19;
//            bn = bn.add(new BigInteger(tmp_ul.toString()));
//            
//            bn = bn.multiply(new BigInteger("10"));
//            tmp_ul = pro_id % 10;
////            tmp_ul=9;
//            bn = bn.add(new BigInteger(tmp_ul.toString()));
//            
//            String s = Hex.encodeHexString(bn.toByteArray()).toUpperCase();
//            int check_code = ticket_get_crc8(s.getBytes());//big endian 不知道对不对
//            bn = bn.multiply(new BigInteger("256"));
//            tmp_ul = check_code % 256;//原程序300
////            tmp_ul=255; 
//            bn = bn.add(new BigInteger(tmp_ul.toString()));
//            int bitcount = bn.bitCount();
//            int bitlen = bn.bitLength();
//            byte[] hex_str = bn.toByteArray(); //原程序还转成了字符串
//            if (hex_str.length <= 8 || hex_str.length > 12) {
//                logger.error("len too big !len=" + hex_str.length);
//                return null;
//            }
//            cipher = new int[3];
//            byte[] tmp = new byte[4];
//            System.arraycopy(hex_str, hex_str.length - 4, tmp, 0, 4);
//            cipher[2] = bytesToInt(tmp);
//            tmp = new byte[4];
//            System.arraycopy(hex_str, hex_str.length - 8, tmp, 0, 4);
//            cipher[1] = bytesToInt(tmp);
//            tmp = new byte[4];
//            System.arraycopy(hex_str, 0, tmp, 12 - hex_str.length, hex_str.length - 8);
//            cipher[0] = bytesToInt(tmp);
//            return cipher;
//        } catch (Exception e) {
//            logger.error("", e);
//            return null;
//        }
//        
//    }
    private final byte[] ticketDesKey = {
        (byte) 0x43, (byte) 0xb1, (byte) 0xe6, (byte) 0x5c, (byte) 0x07, (byte) 0x0c, (byte) 0x14, (byte) 0x7b,
        (byte) 0x6b, (byte) 0xa9, (byte) 0x3e, (byte) 0x52, (byte) 0x55, (byte) 0xf0, (byte) 0x26, (byte) 0x0b,
        (byte) 0x6b, (byte) 0xa9, (byte) 0x3e, (byte) 0x52, (byte) 0x55, (byte) 0xf0, (byte) 0x26, (byte) 0x0b};
    private final byte[] ticketDesKeyInv = {
        (byte) 0x6b, (byte) 0xa9, (byte) 0x3e, (byte) 0x52, (byte) 0x55, (byte) 0xf0, (byte) 0x26, (byte) 0x0b};

    /**
     * 生成彩票校验码
     *
     * @param tmnNo
     * @param betTime
     * @param randStr
     * @param gameCode
     * @param drawName
     * @param serialNo
     * @param betMode
     * @param playId
     * @param multi
     * @param betLine
     * @return
     */
    public String makeTicketSign(String tmnNo, String betTime, String randStr,
            String gameCode, String drawName, String serialNo, String betMode,
            String playId, String multi, String betLine) {
        try {

            if (tmnNo == null || betTime == null || gameCode == null || drawName == null
                    || serialNo == null || betMode == null || playId == null || multi == null
                    || betLine == null) {
                return null;
            }

            StringBuilder allInfo = new StringBuilder();
            allInfo.append(tmnNo);
            allInfo.append(betTime);
            if (randStr != null) {
                allInfo.append(randStr);
            }
            allInfo.append(gameCode);
            allInfo.append(drawName);
            allInfo.append(serialNo);
            allInfo.append(betMode);
            allInfo.append(playId);
            allInfo.append(multi);
            allInfo.append(betLine);
//            String test = "000000012015-01-01 00:00:00lottery20153d00011041000106##";
            byte[] src = allInfo.toString().getBytes();
            byte[] dest = new byte[src.length + 1];
            dest[src.length] = 0;
            System.arraycopy(src, 0, dest, 0, src.length);
            byte[] allInfoB = compress(dest);
            if (allInfoB == null) {
                return null;
            }
            byte[] allInfoHash = fnv64aBufLong(allInfoB, 0);
            byte[] encryptHash = ticketSignEncrpt(allInfoHash, ticketDesKey, ticketDesKeyInv);
            if (encryptHash == null) {
                return null;
            }
            return String.format("%02X%02X %02X%02X %02X%02X %02X%02X",
                    encryptHash[0], encryptHash[1], encryptHash[2], encryptHash[3],
                    encryptHash[4], encryptHash[5], encryptHash[6], encryptHash[7]
            );
        } catch (Exception ex) {
            logger.error("", ex);
            return null;
        }
    }

    /**
     * 将字符串压缩成二进制
     *
     * @param data
     * @return
     */
    public byte[] compress(byte[] data) {
        ByteArrayOutputStream bos;
        DeflaterOutputStream zos;
        try {
            bos = new ByteArrayOutputStream();
            zos = new DeflaterOutputStream(bos);
            zos.write(data);
            zos.close();
            return bos.toByteArray();
        } catch (IOException e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * DES加密,彩票校验码用
     *
     * @param src
     * @param desKey
     * @param inv
     * @return
     */
    private byte[] ticketSignEncrpt(byte[] src, byte[] desKey, byte[] inv) {
        try {
            if (src == null || src.length == 0 || desKey == null || inv == null) {
                return null;
            }
            IvParameterSpec iv = new IvParameterSpec(inv);  //初始化向量
            SecretKey sk = new SecretKeySpec(desKey, "DESede");
            Cipher c = Cipher.getInstance("DESede/CBC/NoPadding");//CBC模式,无填充,输入是8字节摘要
            c.init(Cipher.ENCRYPT_MODE, sk, iv);
            byte[] cipherByte = c.doFinal(src);
            return cipherByte;
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;

    }
}
