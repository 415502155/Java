package com.bestinfo.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 * 对长字符串进行压缩和接压缩
 *
 * @author lvchangrong
 */
public class ZipStrUtils {

    private static final Logger logger = Logger.getLogger(ZipStrUtils.class);

    /**
     * 字符串的压缩(gzip)
     *
     * @param primStr 待压缩的字符串
     * @return 返回压缩后的字符串
     */
    public static String comPressStr(String primStr) {
        if (primStr == null || primStr.length() == 0) {
            return primStr;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(primStr.getBytes());
        } catch (IOException e) {
            logger.error("e :", e);
        } finally {
            if (gzip != null) {
                try {
                    gzip.close();
                } catch (IOException e) {
                    logger.error("e :", e);
                }
            }
        }
        return new Base64().encodeToString(out.toByteArray());
    }

    /**
     * 将Base64转换后的字符串转为字节数组
     *
     * @param compressedStr
     * @return
     */
    public static byte[] base2Byte(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }
        byte[] compressed = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = null;
            GZIPInputStream ginzip = null;
//            compressed = new BASE64Decoder().decodeBuffer(compressedStr);
            compressed = new Base64().decode(compressedStr);
            return compressed;
        } catch (Exception ex) {
            logger.error("ex :", ex);
            return null;
        }
    }

    /**
     * 解压缩 解压后的字节数组转为String
     *
     * @param compressed
     * @return
     */
    public static String unGzip(byte[] compressed) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPInputStream ginzip = null;
        ByteArrayInputStream in = null;
        String decompressed = null;
        try {
            in = new ByteArrayInputStream(compressed);
            ginzip = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = ginzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString();
        } catch (IOException ex) {
            logger.error("e :", ex);
        } finally {
            if (ginzip != null) {
                try {
                    ginzip.close();
                } catch (IOException ex) {
                    logger.error("e :", ex);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    logger.error("e :", ex);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    logger.error("e :", ex);
                }
            }
        }
        return decompressed;
    }

    public static void main(String[] args) {
        String str = "H4sIAAAAAAAAAK2XzY7TMBDHX8XKHeKPOHGkNHtBSBVL1cPyAKGxlqhpUjVZoJy48hQ8AeKK9nlAgrfATlKx3ZlKjLKVKk3G9i/jGf/tOLv6uKvZe3voqrZZBOI5D5htNm1ZNbeL4M3Ny2cmuMqzdm+b/aH6ZPOs6l7Zps1FFk5Wdlvs7LLM0yycrMGzcv/819f739/vx4bBkZWH4oPrIlzvyRxcQ6PkQvOYT01j/+3US8bavfL0NLrPBnGdTu3jwLe2v2n7on7dNvaY8yw8d/j267bv7eH4sMeZa5j2smRVuQh44IYUnV25qRsWcSYN0ynTnImUSc4SzXTEYqYTFmsmJYudWzERMaFYJFnCmXITmBBZt7ebqqid6d777yGr7zbb43ncj11jn+uq6yeTbeqi65YuSBGMts/AIvjz+YvgP398E66mvR+/utv5ibB6nOTpseuLrV376i4Cyf0vCAFaouiU/T9aXCArlGwIZINyI5Sb0CJGuBrlxgSuxLAxitUEbIRQE5TKZ1LNY2o6rAUCVV9YDClGNlQyAvYigOSEQpY4GIguHZYDKWSMCxSXDuthZukEkFs6LIi5WKA2Qy2cvLAkBBCcoVYuwcFAcoZauQjlAtEZauUEygWy89xodumA5gx1RUjswACCS6hlu3RiSCC5hFo4hYOB5hJq5RTKBaJLqJXTGBaILqbmIcbzACQXU/OA7mkSKC6m5sFgWCA4j1VzhSGB3jQ1CxJPA1CcJgsZ/ZICktNPkQcF5BY9TbhAbRE1XHSfVEBtnivnbmgKqE1Rw0U3BwXEpqjholUDWpNUrDsgES78pPR3DHK44YNrTDhetPLMNuW6bWt/7TmZ2abo3q3toWrLF8Uxj13TuWccPd5O/wIOxBS0zw4AAA==";
        System.out.println(ZipStrUtils.unGzip(ZipStrUtils.base2Byte(str)));
    }
}
