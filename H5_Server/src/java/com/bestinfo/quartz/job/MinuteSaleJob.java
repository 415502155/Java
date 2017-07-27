package com.bestinfo.quartz.job;

import com.bestinfo.bean.h5.taskPlan.MonitorMinuteSales;
import com.bestinfo.dao.h5.monitor.IMonitorMinuteSaleDao;
import com.bestinfo.dao.h5.taskPlan.ITastPlanDao;
import static com.bestinfo.service.impl.TaskPlanService.getTimeByMinute;
import com.bestinfo.util.TimeUtil;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MinuteSaleJob {

    private static final Logger logger = Logger.getLogger(MinuteSaleJob.class);
    @Resource
    private ITastPlanDao iTaskPlanDao;
    @Resource
    private IMonitorMinuteSaleDao iMinuteSaleDao;
    @Resource
    private JdbcTemplate vaniJdbcTemplate;  //vani库
    @Resource
    private JdbcTemplate metaJdbcTemplate;  //meta库

    public synchronized void masterThread() throws FileNotFoundException, IOException {
        logger.warn("******************** MinuteSaleJob  start  ********************");
        String beginTime = getTimeByMinute(-1);
        String endTime = getTimeByMinute(0);
        logger.info("startTime:" + beginTime + ",endTime:" + endTime);

        long dateCountStart = System.currentTimeMillis();
        MonitorMinuteSales lastMinuteData = iTaskPlanDao.getLastMinuteData(vaniJdbcTemplate, beginTime, endTime);
        if (lastMinuteData == null) {
            lastMinuteData = new MonitorMinuteSales();
        }
        logger.info("lastMinuteMoney:" + lastMinuteData.getSale_money().intValue() + ",lastMinuteCount:" + lastMinuteData.getSale_number());

        long dateCountEnd = System.currentTimeMillis();
        String processTime = TimeUtil.getTime2(dateCountEnd - dateCountStart);
        logger.info("processTime:" + processTime);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(beginTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        iMinuteSaleDao.insertMinuteSale(metaJdbcTemplate, date, lastMinuteData.getSale_money().intValue(), lastMinuteData.getSale_number());
        logger.warn("******************** MinuteSaleJob  complete  ********************");
    }

    public static String getTimeByMinute(int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }
}
