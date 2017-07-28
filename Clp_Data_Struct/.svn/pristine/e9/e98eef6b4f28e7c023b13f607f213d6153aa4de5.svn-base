package com.bestinfo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.log4j.Logger;

/**
 * 压缩文件为.gz格式
 *
 * @author hhhh
 */
public class GZipUtils {

    private static final Logger logger = Logger.getLogger(GZipUtils.class);

    public static final int BUFFER = 1024;
    public static final String EXT = ".gz";

    public static int compress(File file, boolean delete) {
        try {
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(file.getPath() + EXT);

            int re = compress(fis, fos);
            if (re < 0) {
                return -1;
            }

            fis.close();
            fos.flush();
            fos.close();

            if (delete) {
                file.delete();
            }
            return 0;
        } catch (Exception e) {
            logger.error("compress file failed.");
            return -2;
        }

    }

    /**
     * 数据压缩
     *
     * @param is
     * @param os
     */
    private static int compress(InputStream is, OutputStream os) {
        try {
            GZIPOutputStream gos = new GZIPOutputStream(os);
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = is.read(data, 0, BUFFER)) != -1) {
                gos.write(data, 0, count);
            }
            gos.finish();
            gos.flush();
            gos.close();
            return 0;
        } catch (Exception e) {
            logger.error("compress data failed.");
            return -1;
        }
    }

    /**
     * 文件压缩
     *
     * @param path
     * @param delete 是否删除原始文件
     */
    public static int compress(String path, boolean delete) {
        try {
            File file = new File(path);
            int re = compress(file, delete);
            if (re < 0) {
                return -1;
            }
            return 0;
        } catch (Exception e) {
            logger.error("compress file error where path = " + path, e);
            return -2;
        }
    }

//    public static void main(String[] args) {
//        int re = compress("D:\\makeXML\\gameRule1.xml", true);
//        if (re < 0) {
//            System.out.println("压缩文件失败！");
//        }
//    }
    
}
