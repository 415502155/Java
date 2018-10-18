/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.controller.clpdata;



import com.alibaba.fastjson.JSONArray;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class DoPost extends HttpServlet{
    private static final Logger logger = Logger.getLogger(DoPost.class);
    
         public void doGet(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
             /**设置响应头允许ajax跨域访问**/
        response.setHeader("Access-Control-Allow-Origin","*");//'Access-Control-Allow-Origin:*'
        /*星号表示所有的异域请求都可以接受，*/
        response.setHeader("Access-Control-Allow-Methods","GET,POST");
             //response.setCharacterEncoding("UTF-8"); 
            // response.setContentType("text/plain");  
//         logger.info("jin laile :hahahahahha");
         String json = readJSONString(request);
         logger.info("jsonobj:"+json);
         logger.info("REQ:"+request.getParameter("REQ"));
         logger.info("REF:"+request.getParameter("REF"));

         //String a =request.getParameter("data");
         //JSONObject jSONObject=JSONObject.parseObject(a);
         //JSONObject jsona= JSONObject.parseObject(jSONObject.getString("REQ"));
         //System.out.println(jsona.getString("USER_NAME"));
         //System.out.println(jSONObject);
         //String string=JSONArray.toJSONString(request.getParameter("data"));
         //logger.info("string:"+string);
         JSONObject jsonObj = JSONObject.fromObject(request.getParameter("REQ"));//将前台传过来的所有记录的json字符串强制转换成json对象
         logger.info("zhan json:"+jsonObj);
         //JSONObject json=JSONObject.fromObject(jsonObj.getString("PWD"));
//         InputStream in=request.getInputStream();
//            byte[] b=new byte[1024];
//            int k=0;
//            StringBuilder sber=new StringBuilder();
//            while((k=in.read(b))!=-1){
//            sber.append(new String(b,0,k,request.getCharacterEncoding()));
//            }
//            logger.info("jiehsoude stream :"+sber.toString());     
//          StringBuilder json = new StringBuilder();
//            String line = null;
//            try {
//                BufferedReader reader = request.getReader();
//                while((line = reader.readLine()) != null) {
//                json.append(line);
//                }
//            }catch(Exception e) {
//                System.out.println(e.toString());
//            }
//
//           logger.info("jiehsoude :"+json.toString());        
         response.setCharacterEncoding("UTF-8");//设置将字符以"UTF-8"编码输出到客户端浏览器
         //通过设置响应头控制浏览器以UTF-8的编码显示数据，如果不加这句话，那么浏览器显示的将是乱码
         //response.setHeader("content-type", "text/html;charset=UTF-8");
         //response.setHeader("content-type", "application/json");
         //response.setHeader("content-type", "text/plain");
         
         String callbackFunName =request.getParameter("callback");//得到js函数名称   
         response.setContentType("text/javascript");
         PrintWriter out = response.getWriter();
         Map map = new LinkedHashMap();
         map.put("status", "0");
         map.put("msg", "Request success");
         String result=JSONArray.toJSONString(map);
         //out.write(callbackFunName + "([ { name:\"John\"}])");
         out.write(result);

     }
 
     public void doPost(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
         doGet(request, response);     
     }
     private String readJSONString(HttpServletRequest request){
        StringBuffer json = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while((line = reader.readLine()) != null) {
                json.append(line);
            }
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
        return json.toString();
    }
}
