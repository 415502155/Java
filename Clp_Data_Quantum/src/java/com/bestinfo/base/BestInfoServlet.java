package com.bestinfo.base;

import com.bestinfo.define.filepath.FilePath;
import com.bestinfo.util.PropertiesUtil;
import java.io.File;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

/**
 * 初始化数据servlet
 */
public class BestInfoServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(BestInfoServlet.class);

    @Override
    public void destroy() {
    }

    @Override
    public void init() {
        try {
            WebApplicationContext wac = ContextLoaderListener.getCurrentWebApplicationContext();

            logger.info("file path init start...");
            String path = wac.getServletContext().getRealPath("/WEB-INF");
            PropertiesUtil pt = new PropertiesUtil();
            String filename = new File(path + "/classes/config/filepath.properties").getAbsolutePath();
            if (pt.loadFile(filename, "UTF-8")) {
                String envirVariableName = pt.getString("envirVariableName");
                logger.info("read from filepath.properties envirVariableName: " + envirVariableName);
                if (envirVariableName != null && !"".equals(envirVariableName.trim())) {
                    FilePath.rootPath = System.getenv(envirVariableName);
                }

                FilePath.clpcenter = pt.getString("clpcenter");
                FilePath.game = pt.getString("game");
                FilePath.game2 = pt.getString("game2");
                FilePath.gambler = pt.getString("gambler");
                FilePath.rule = pt.getString("rule");
                FilePath.account = pt.getString("account");
                FilePath.win = pt.getString("win");
                FilePath.issuec = pt.getString("issuec");
                FilePath.issuet = pt.getString("issuet");
                FilePath.issue = pt.getString("issue");
                FilePath.luckyno = pt.getString("luckyno");
                FilePath.prize = pt.getString("prize");
                FilePath.drawinfo = pt.getString("drawinfo");
                FilePath.saleStat = pt.getString("saleStat");
                FilePath.prizeStat = pt.getString("prizeStat");
                FilePath.soft = pt.getString("soft");
                FilePath.bank = pt.getString("bank");
                FilePath.bulletin = pt.getString("bulletin");
                FilePath.natureTmnDay = pt.getString("natureTmnDay");
                logger.info("rootPath:" + FilePath.rootPath);
                logger.info("clpcenter:" + FilePath.clpcenter);
                logger.info("game:" + FilePath.game);
                logger.info("game2:" + FilePath.game2);
                logger.info("gambler:" + FilePath.gambler);
                logger.info("rule:" + FilePath.rule);
                logger.info("account:" + FilePath.account);
                logger.info("win:" + FilePath.win);
                logger.info("issuec:" + FilePath.issuec);
                logger.info("issuet:" + FilePath.issuet);
                logger.info("issue:" + FilePath.issue);
                logger.info("luckyno:" + FilePath.luckyno);
                logger.info("prize:" + FilePath.prize);
                logger.info("drawinfo:" + FilePath.drawinfo);
                logger.info("saleStat:" + FilePath.saleStat);
                logger.info("prizeStat:" + FilePath.prizeStat);
                logger.info("soft:" + FilePath.soft);
                logger.info("bank:" + FilePath.bank);
                logger.info("bulletin:" + FilePath.bulletin);
                logger.info("natureTmnDay:" + FilePath.natureTmnDay);
                logger.info("file path init complete...");

            } else {
                logger.error("file load error");
            }

        } catch (Exception e) {
            logger.error("", e);
        }
    }

}
