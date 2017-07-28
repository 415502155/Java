/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.util;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.io.Writer;
import org.apache.log4j.Logger;

public class TemplateUtil {

    private static Logger logger = Logger.getLogger(TemplateUtil.class);

    private static Configuration configure = null;

    /**
     * 根据htm模板生成html文件
     *
     * @param dataMap Map 需要填入模板的数据
     * @param templateFileDir 模板文件目录(如：/lottery/no/pick4_template)
     * @param templateFileName 模板文件名称(如：4D_lucky_no_template.htm)
     * @param goalFilePath 生成文件路径
     * @return
     */
    public static int createHTML(Map<String, Object> dataMap, String templateFileDir, String templateFileName, String goalFilePath) {
        try {
            configure = new Configuration();
            configure.setDefaultEncoding("utf-8");

            //加载需要装填的模板
            Template template = null;
            //加载模板文件
            configure.setDirectoryForTemplateLoading(new File(templateFileDir));
            //设置对象包装器
            configure.setObjectWrapper(new DefaultObjectWrapper());
            //设置异常处理器
            configure.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
            //定义Template对象
            template = configure.getTemplate(templateFileName);
            //输出文件
            File outFile = new File(goalFilePath);
            if (outFile.exists()) {
                //文件存在，删除
                outFile.delete();
            } else {
                //文件不存在，先创建路径
                String filePath = goalFilePath.substring(0, goalFilePath.lastIndexOf(System.getProperty("file.separator")));
                File dir = new File(filePath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
            }

            outFile = new File(goalFilePath);

            Writer out = null;
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
            template.process(dataMap, out);
            return 0;
//            outFile.delete();
        } catch (Exception e) {
            logger.error("createHTML error", e);
            return -1;
        }
    }

}
