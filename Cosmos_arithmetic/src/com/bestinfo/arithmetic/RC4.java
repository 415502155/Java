package com.bestinfo.arithmetic;

import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * RC4加解密
 *
 * @author chenliping
 */
public class RC4 {

    private byte[] initKey(byte[] b_key) {
        byte state[] = new byte[256];

        for (int i = 0; i < 256; i++) {
            state[i] = (byte) i;
        }
        int index1 = 0;
        int index2 = 0;
        if (b_key == null || b_key.length == 0) {
            return null;
        }
        for (int i = 0; i < 256; i++) {
            index2 = ((b_key[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;
            byte tmp = state[i];
            state[i] = state[index2];
            state[index2] = tmp;
            index1 = (index1 + 1) % b_key.length;
        }
        return state;
    }

    /**
     * RC4加解密
     *
     * @param input
     * @param mKkey
     * @return
     */
    public byte[] RC4Base(byte[] input, byte[] mKkey) {
        int x = 0;
        int y = 0;
        byte key[] = initKey(mKkey);
        int xorIndex;
        byte[] result = new byte[input.length];

        for (int i = 0; i < input.length; i++) {
            x = (x + 1) & 0xff;
            y = ((key[x] & 0xff) + y) & 0xff;
            byte tmp = key[x];
            key[x] = key[y];
            key[y] = tmp;
            xorIndex = ((key[x] & 0xff) + (key[y] & 0xff)) & 0xff;
            result[i] = (byte) (input[i] ^ key[xorIndex]);
        }
        return result;
    }

    public byte[] RC4Base(byte[] input, String mKkey) {
        return RC4Base(input, mKkey.getBytes());
    }

    /**
     * 用BC做加解密 YangRong
     *
     * @param input
     * @param mKkey
     * @return
     */
    public byte[] RC4BC(byte[] input, byte[] mKkey) {
        try {
            if (input == null || mKkey == null) {
                return null;
            }
            if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
                Security.addProvider(new BouncyCastleProvider());
            }
            org.bouncycastle.crypto.StreamCipher cipher = new org.bouncycastle.crypto.engines.RC4Engine();
            cipher.init(true, new org.bouncycastle.crypto.params.KeyParameter(mKkey));
            byte[] ciphertext = new byte[input.length];
            cipher.processBytes(input, 0, input.length, ciphertext, 0);
            return ciphertext;
        } catch (Exception e) {
          return null;
        }
    }

    /**
     *
     * @param input
     * @param startPos 起始位置 包含
     * @param len 长度
     * @param mKkey
     * @return
     */
    public byte[] RC4BC(byte[] input, int startPos, int len, byte[] mKkey) {
        try {
            if (input == null || mKkey == null) {
                return null;
            }
//            Security.addProvider(new BouncyCastleProvider());
            if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
                Security.addProvider(new BouncyCastleProvider());
            }
            org.bouncycastle.crypto.StreamCipher cipher = new org.bouncycastle.crypto.engines.RC4Engine();
            cipher.init(true, new org.bouncycastle.crypto.params.KeyParameter(mKkey));
            byte[] ciphertext = new byte[len];
            cipher.processBytes(input, startPos, len, ciphertext, 0);
            return ciphertext;
        } catch (Exception e) {
          return null;
        }
    }

    public byte[] RC4BC(byte[] input, int startPos, int len, String mKkey) {
        try {
            if (input == null || mKkey == null) {
               return null;
            }
            byte[] b_key = mKkey.getBytes();
            return RC4BC(input, startPos, len, b_key);
        } catch (Exception e) {
            return null;
        }
    }

    public byte[] RC4BC(byte[] input, String mKkey) {
        try {
            if (input == null || mKkey == null) {
                return null;
            }
            byte[] b_key = mKkey.getBytes();
            return RC4BC(input, b_key);
        } catch (Exception e) {
            return null;
        }
    }

    public String RC4BCS(byte[] input, byte[] mKkey) {
        try {
            if (input == null || mKkey == null) {
                 return null;
            }
            byte[] ciphertext = RC4BC(input, mKkey);
            if (ciphertext == null) {
                 return null;
            }
            return new DataChange().bytes2HexString(ciphertext);
        } catch (Exception e) {
             return null;
        }
    }

    public String RC4BCS(byte[] input, String mKkey) {
        try {
            if (input == null || mKkey == null) {
                 return null;
            }
            byte[] ciphertext = RC4BC(input, mKkey);
            if (ciphertext == null) {
                return null;
            }
            return new DataChange().bytes2HexString(ciphertext);
        } catch (Exception e) {
             return null;
        }
    }

    public String asString(byte[] buf) {
        StringBuilder strbuf = new StringBuilder(buf.length);
        for (int i = 0; i < buf.length; i++) {
            strbuf.append((char) buf[i]);
        }
        return strbuf.toString();
    }

    public byte[] hexString2Bytes(String src) {
        int size = src.length();
        byte[] ret = new byte[size / 2];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < size / 2; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    public String toHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch & 0xFF);
            if (s4.length() == 1) {
                s4 = '0' + s4;
            }
            str = str + s4;
        }
        return str;// 0x表示十六进制
    }

    private byte uniteBytes(byte src0, byte src1) {
        char _b0 = (char) Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
        _b0 = (char) (_b0 << 4);
        char _b1 = (char) Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     */
    public String encryRC4(byte[] data, String key) {
        if (data == null || key == null) {
             return null;
        }
        return toHexString(asString(RC4Base(data, key)));
    }

    public String encryRC4(byte[] data, byte[] key) {
        if (data == null || key == null) {
             return null;
        }
        return toHexString(asString(RC4Base(data, key)));
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     */
    public byte[] decryRC4Byte(String data, String key) {
        if (data == null || key == null) {
            return null;
        }
        byte b_data[] = hexString2Bytes(data);
        return RC4Base(b_data, key);
    }

    public byte[] decryRC4Bbyte(String data, byte[] key) {
        if (data == null || key == null) {
           return null;
        }
        byte b_data[] = hexString2Bytes(data);
        return RC4Base(b_data, key);
    }

}
