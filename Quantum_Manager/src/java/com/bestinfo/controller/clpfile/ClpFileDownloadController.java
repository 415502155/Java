package com.bestinfo.controller.clpfile;

import com.bestinfo.bean.game.GameDrawInfo;
import com.bestinfo.dao.game.IGameDrawInfoDao;
import com.bestinfo.define.Draw.DrawProStatus;
import com.bestinfo.define.filepath.FilePath;
import com.bestinfo.define.game.ClpGameCode;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URLEncoder;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 中彩文件读取
 */
@Controller
@RequestMapping(value = "/fsfile")
public class ClpFileDownloadController {

    private static final Logger logger = Logger.getLogger(ClpFileDownloadController.class);

    @Resource
    private IGameDrawInfoDao drawInfoDao;

    @Resource
    private JdbcTemplate metaJdbcTemplate;

    /**
     * 根据游戏id和时间获取开奖期名
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/luckyDraw")
    @ResponseBody
    public String getLuckyDraw(HttpServletRequest request) {
        String gameId = ServletRequestUtils.getStringParameter(request, "gameId", "");
        String date = ServletRequestUtils.getStringParameter(request, "date", "");
        logger.info("get lucky draw,game_id:" + gameId + ",date:" + date);
        if ("".equals(gameId) || "".equals(date)) {
            logger.error("client data error,game_id:" + gameId + ",date:" + date);
            return "";
        }

        //开奖日为指定日期，期状态为期结算160到兑奖授权500之间
        List<GameDrawInfo> drawInfoList = drawInfoDao.getDayDrawListBetween2StatusDesc(metaJdbcTemplate,
                Integer.parseInt(gameId), DrawProStatus.STATISTICS, DrawProStatus.GETMONEY, date);
        if (drawInfoList.isEmpty()) {
            return "";
        } else {
            return drawInfoList.get(0).getDraw_name();
        }
    }

    /**
     * 获取文件大小，字节数
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/length")
    @ResponseBody
    public String getFileLength(HttpServletRequest request) {
        String fileName = ServletRequestUtils.getStringParameter(request, "fileName", "");//44_10001_2015071_SALE.RPT
        if ("".equals(fileName)) {
            logger.error("get clp file length,fileName from client is null");
            return "0";
        }

        int gameId = ClpGameCode.codeGameMap.get(fileName.split("_")[1]);
        File file = new File(FilePath.getClpFilePath(gameId, fileName));
        if (!file.exists()) {
            logger.error("file does not exist,filePath:" + FilePath.getClpFilePath(gameId, fileName));
            return "0";
        }
        long length = file.length();
        logger.info("get clp file length,fileName:" + fileName + ",length:" + length);
        return String.valueOf(length);
    }

    /**
     * string方式下载文件内容
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/download")
    @ResponseBody
    public String downloadClpFile(HttpServletRequest request) {
        String fileName = ServletRequestUtils.getStringParameter(request, "fileName", "");//44_10001_2015071_SALE.RPT
        if ("".equals(fileName)) {
            logger.error("download clp file,fileName from client is null");
            return "";
        }
        logger.info("download clp file,fileName:" + fileName);

        int gameId = ClpGameCode.codeGameMap.get(fileName.split("_")[1]);
        File file = new File(FilePath.getClpFilePath(gameId, fileName));
        if (!file.exists()) {
            logger.error("file does not exist,filePath:" + FilePath.getClpFilePath(gameId, fileName));
            return "";
        }

        try {
            return FileUtils.readFileToString(file);
        } catch (IOException e) {
            logger.error("", e);
            return "";
        }
    }

    /**
     * web数据流方式分块下载文件内容
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/downloadBlock")
    public void downloadBlockClpFile(HttpServletRequest request, HttpServletResponse response) {
        try {
            String fileName = ServletRequestUtils.getStringParameter(request, "fileName", "");//44_10001_2015071_SALE.RPT
            String offSet = ServletRequestUtils.getStringParameter(request, "offSet", "0");
            String blockSize = ServletRequestUtils.getStringParameter(request, "blockSize", "0");
            if ("".equals(fileName)) {
                logger.error("download clp file,fileName from client is null");
                return;
            }
            logger.info("download clp file,fileName:" + fileName + ",offSet:" + offSet + ",blockSize:" + blockSize);

            int gameId = ClpGameCode.codeGameMap.get(fileName.split("_")[1]);
            File file = new File(FilePath.getClpFilePath(gameId, fileName));
            if (!file.exists()) {
                logger.error("file does not exist,filePath:" + FilePath.getClpFilePath(gameId, fileName));
                return;
            }
            response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            RandomAccessFile randomFile = new RandomAccessFile(file, "r");
            randomFile.skipBytes(Integer.parseInt(offSet));
            byte buffer[] = new byte[Integer.parseInt(blockSize)];
            int len = randomFile.read(buffer, 0, Integer.parseInt(blockSize));
            OutputStream out = response.getOutputStream();
            if (len > 0) {
                out.write(buffer, 0, len);
            }
            randomFile.close();
            out.close();
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
//        File file = new File("G://44_10001_2016031_WINN.RPT");
//        System.out.println(file.length());
//        RandomAccessFile randomFile = new RandomAccessFile(file, "r");
//        randomFile.skipBytes(374);
//        byte buffer[] = new byte[10];
//        int len = randomFile.read(buffer, 0, 10);
//        System.out.println(len);

        String sFileName = "44_10001_2016031_WINN.RPT";
        int iRet = 0;
        HttpClient Client = new HttpClient();
        Client.getHttpConnectionManager().getParams().setConnectionTimeout(2000);
        GetMethod Method = new GetMethod("http://172.18.0.218:8751/manager/fsfile/downloadBlock?fileName=" + sFileName + "&offSet=0&blockSize=400");
        try {
            int iStatusCode = Client.executeMethod(Method);
            if (iStatusCode == HttpStatus.SC_OK) {
                byte[] btRet = Method.getResponseBody();
                iRet = btRet.length;
                if (btRet != null && btRet.length > 0) {
                    File file = new File("G://" + sFileName);
                    RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
                    accessFile.write(btRet, 0, btRet.length);
                    accessFile.close();
                }
            } else {
                iRet = -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            iRet = -2;
        } finally {
            Method.releaseConnection();
            ((SimpleHttpConnectionManager) Client.getHttpConnectionManager()).shutdown();
        }
    }

}
