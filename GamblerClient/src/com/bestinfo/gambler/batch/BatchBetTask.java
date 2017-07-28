package com.bestinfo.gambler.batch;

import com.bestinfo.define.system.SendType;
import com.bestinfo.gambler.bet.SelfTerminals;
import com.bestinfo.gambler.bet.Telephone;
import com.bestinfo.gambler.createBetNumber.Count;
import com.bestinfo.protocols.bet.PBetSchemeRequst;
import com.bestinfo.protocols.message.AppHeader;

/**
 * 投注任务
 */
public class BatchBetTask implements Runnable {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(BatchBetTask.class);
    private Count counter = null;
    private AppHeader header = null;
    private PBetSchemeRequst req = null;
    private boolean isusertrue = false;
    private String ticketnums = null;

    public BatchBetTask(AppHeader head, PBetSchemeRequst req, boolean isusertrue, String ticketnums, Count c) {
        this.header = head;
        this.isusertrue = isusertrue;
        this.ticketnums = ticketnums;
        this.req = req;
        this.counter = c;
    }

    @Override
    public void run() {

        try {

            if (header.getType() == SendType.PHONE) {
                new Telephone().SendAppointBetXml(header, req, Integer.parseInt(ticketnums), isusertrue, counter);
            } else if (header.getType() == SendType.HELPUS) {
                logger.info("run +++ " + System.currentTimeMillis());
                new SelfTerminals().SendAppointBetXml(header, req, Integer.parseInt(ticketnums), isusertrue, counter);
                logger.info("run ----- " + System.currentTimeMillis());
            }
            // long startTime=System.currentTimeMillis();
            // new Bet().SaleAppointTicket(header, req, Integer.parseInt(ticketnums), isusertrue,counter);
            //  long endTime=System.currentTimeMillis();
            // logger.info("共用时间:"+(endTime-startTime)/1000F);
        } catch (NumberFormatException e) {
            //information.setText(e.toString());
            logger.error("", e);
            //return;
        }
//        information.setText(Thread.currentThread().getName()+"  完成！");
//        logger.info("完成！");
    }

}
