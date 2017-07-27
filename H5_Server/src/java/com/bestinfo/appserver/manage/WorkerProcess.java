package com.bestinfo.appserver.manage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bestinfo.appserver.initialize.InitializeBean;
import com.bestinfo.bean.h5.monitor.RequestModel;
import com.bestinfo.bean.h5.monitor.Session;
import static com.bestinfo.define.h5.monitor.CommonDefine.COMPARISON_CMD;
import static com.bestinfo.define.h5.monitor.CommonDefine.PLAN_CMD;
import static com.bestinfo.define.h5.monitor.CommonDefine.MONITOR_CMD;
import static com.bestinfo.define.h5.monitor.CommonDefine.RESULT_ANALYSE_REQUEST_ERROR;
import com.bestinfo.service.h5.inter.IComponentsService;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class WorkerProcess implements Runnable, Serializable {

    private static final Logger logger = Logger.getLogger(WorkerProcess.class);

    private RequestModel request = null;
    private HttpServletResponse response = null;

    public WorkerProcess() {

    }

    public WorkerProcess(RequestModel request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public void run() {
        if (request != null && response != null) {
            synchronized (response) {
                if (!response.isCommitted()) {
                    JSONArray reqJsonArray = request.getJsonArray();
                    JSONArray resJsonArray = new JSONArray();
                    Session session = request.getSession();

                    String cmdStr = "";
                    try {
                        for (int i = 0; i < reqJsonArray.size(); i++) {
                            JSONObject reqJson = reqJsonArray.getJSONObject(i);
                            String cmd = String.valueOf(reqJson.get("CMD"));
                            cmdStr += cmd;
                            String beanName = getServiceBeanByCmd(cmd);
                            IComponentsService serviceBean = InitializeBean.getBean(beanName);
                            JSONObject backJson = null;
                            try {
                                backJson = serviceBean.execute(reqJson, request.getRequestIp());
                            } catch (Exception e) {
                                logger.error("ServiceBean " + beanName + " execute error", e);
                                backJson = new JSONObject();
                                backJson.put("CMD", cmd);
                                backJson.put("CODE", RESULT_ANALYSE_REQUEST_ERROR);
                                backJson.put("RESULT", e.getMessage());
                            }
                            resJsonArray.add(backJson);
                        }
                    } catch (Exception e) {
                        logger.error("analyse request error", e);
                    }

                    if (!cmdStr.equals(MONITOR_CMD + "")) {//公共指令的日志不在这个地方打印
                        logger.info("resJson:" + resJsonArray.toJSONString());
                    }

                    try {
                        OutputStream output = response.getOutputStream();
                        DataOutputStream dataWritter = new DataOutputStream(output);
//                        byte[] responseData = new ResponseDataUtil().makeResponseData(request.getEncryptType(),
//                                request.getCmdVersion(), request.getSession(), COMMUNICATE_STATUS_OK, resJsonArray.toJSONString());
//                        if (responseData == null) {
//                            logger.error("response data is null");
//                            dataWritter.write("response data is null".getBytes());
//                            dataWritter.flush();
//                            dataWritter.close();
//                            return;
//                        }
//                        dataWritter.write(responseData);
                        logger.info("response jsonStr: " + resJsonArray.toJSONString());
                        dataWritter.write(resJsonArray.toJSONString().getBytes());
                        dataWritter.flush();
                        dataWritter.close();

//                        //更新Session的cacheData的response数据
//                        SessionUtil.alterCacheResponse(session, responseData);
//
//                        //更新Session到redis中
//                        ApplicationContext ctx = InitializeBean.getApplicationContext();
//                        SessionRedis sessionRedis = (SessionRedis) ctx.getBean("sessionRedis");
//                        sessionRedis.addSession(session);
////                        logger.info("WorkerProcess update Session in redis success");
                    } catch (Exception e) {
                        logger.error("build response data error", e);
                    }
                } else {
                    logger.error("response timeout!");
                }
            }
        }
    }

    /**
     * 根据指令id获取业务处理service类
     *
     * @param cmd
     * @return
     */
    public static String getServiceBeanByCmd(String cmd) {
        String beanName = "";
        switch (Integer.parseInt(cmd)) {
            case PLAN_CMD://计划指令
                beanName = "VersionService";
                break;
            case MONITOR_CMD://公共指令
                beanName = "MonitorService";
                break;
            case COMPARISON_CMD://对比指令
                beanName = "ComparisonService";
                break;
            default:
                break;
        }
        return beanName;
    }

}
