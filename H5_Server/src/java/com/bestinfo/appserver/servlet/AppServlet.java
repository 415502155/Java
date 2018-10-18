package com.bestinfo.appserver.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bestinfo.appserver.initialize.InitializeBean;
import static com.bestinfo.define.h5.monitor.CommonDefine.ADD_TMN_INREDIS_CMD;
import static com.bestinfo.define.h5.monitor.CommonDefine.COMPARISON_CMD;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import static com.bestinfo.define.h5.monitor.CommonDefine.MONITOR_CMD;
import static com.bestinfo.define.h5.monitor.CommonDefine.PLAN_CMD;
import static com.bestinfo.define.h5.monitor.CommonDefine.RESULT_ANALYSE_REQUEST_ERROR;
import com.bestinfo.service.h5.inter.IComponentsService;
import java.io.PrintWriter;

public class AppServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(AppServlet.class);

    public AppServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputStream input = request.getInputStream();
        DataInputStream dataReader = new DataInputStream(input);

//        String callbackFunName = request.getParameter("callback");//得到js函数名称 
        response.setContentType("text/xml;charset=UTF-8");
        /**
         * 设置响应头允许ajax跨域访问*
         */
        response.setHeader("Access-Control-Allow-Origin", "*");//'Access-Control-Allow-Origin:*'
        /*星号表示所有的异域请求都可以接受，*/
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");

        PrintWriter out = response.getWriter();

//        OutputStream output = response.getOutputStream();
//        DataOutputStream dataWritter = new DataOutputStream(output);
//        String reqJsonStr = dataReader.readUTF();
        String reqJsonStr = dataReader.readLine();
        if (reqJsonStr.isEmpty()) {
            logger.info("Read data length error!");
//            dataWritter.write("Request format error!".getBytes());
//            dataWritter.flush();
//            dataWritter.close();
//            output.write("Request format error!".getBytes());
            out.write("Read data length error!");
            return;
        } else {
            logger.info("reqJsonStr: " + reqJsonStr);
        }

        try {
            JSONObject jsonDataObj = JSON.parseObject(reqJsonStr);
            JSONArray reqJsonArray = (JSONArray) jsonDataObj.get("REQ");
            String cmdStr = "";
            JSONArray resJsonArray = new JSONArray();

            for (int i = 0; i < reqJsonArray.size(); i++) {
                JSONObject reqJson = reqJsonArray.getJSONObject(i);
                String cmd = String.valueOf(reqJson.get("CMD"));
                cmdStr += cmd;
                String beanName = getServiceBeanByCmd(cmd);
                IComponentsService serviceBean = InitializeBean.getBean(beanName);
                JSONObject backJson = null;
                try {
                    backJson = serviceBean.execute(reqJson, null);
                } catch (Exception e) {
                    logger.error("ServiceBean " + beanName + " execute error", e);
                    backJson = new JSONObject();
                    backJson.put("CMD", cmd);
                    backJson.put("CODE", RESULT_ANALYSE_REQUEST_ERROR);
                    backJson.put("RESULT", e.getMessage());
                }
                resJsonArray.add(backJson);
            }

            if (!cmdStr.equals(MONITOR_CMD + "")) {//公共指令的日志不在这个地方打印
                logger.info("resJson:" + resJsonArray.toJSONString());
            }

            logger.info("response jsonStr: " + resJsonArray.toJSONString());
            response.setHeader("content-type", "text/html;charset=UTF-8");
            out.write(resJsonArray.toJSONString());
//            output.write(resJsonArray.toJSONString().getBytes("UTF-8"));
//            dataWritter.write(resJsonArray.toJSONString().getBytes());
//            dataWritter.flush();
//            dataWritter.close();
        } catch (Exception e) {
            logger.error("handle after bodyData error", e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
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
                beanName = "TaskPlanService";
                break;
            case MONITOR_CMD://公共指令
                beanName = "MonitorService";
                break;
            case COMPARISON_CMD://对比指令
                beanName = "ComparisonService";
                break;
            case ADD_TMN_INREDIS_CMD://测试往redis中添加终端机信息
                beanName = "AddTmnInRedisService";
                break;
            default:
                break;
        }
        return beanName;
    }

}
