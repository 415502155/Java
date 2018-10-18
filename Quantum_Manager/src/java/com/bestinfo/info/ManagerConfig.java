package com.bestinfo.info;

public class ManagerConfig {

    /**
     * 查询文件系统ETL循环次数
     */
    public static int ETL_LOOP_COUNT = 10;

    /**
     * 查询文件系统ETL循环间隔时间
     */
    public static int ETL_INTERVAL_TIME = 2;

    /**
     * 下载文件线程数
     */
    public static int DOWNLOAD_THREAD_NUM = 10;

    /**
     * 每次通信下载字节数
     */
    public static int DOWNLOAD_BLOCK_SIZE = 16384;
}
