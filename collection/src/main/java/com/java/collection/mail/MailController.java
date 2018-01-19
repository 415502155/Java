package com.java.collection.mail;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.java.collection.util.ReturnMsg;

/**
 * @author Administrator
 * 自己给自己发送可以
 * 发送到QQ邮箱就报错554
 *
 * @2018年1月18日
 */
@RestController  
@RequestMapping(value = "/sende")  
public class MailController {  
	private static Logger logger = Logger.getLogger(MailController.class);
	
    @Autowired  
    JavaMailSender mailSender;  
      
    @RequestMapping(value = "/mail")  
    public Object sendEmail()  
    {  
    	logger.info("..........incoming....................");
    	JSONObject jsonObject = new JSONObject();
        try  
        {  
            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();  
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);  
            message.setFrom("18515470815@163.com");  
            message.setTo("18515470815@163.com");  
            message.setSubject("路漫漫其修远兮");  
            message.setText("您好：</br>nbspp;nbsp;nbsp;nnbsp;吾将上下而求索");  
            //message.setRecipient(Message.RecipientType.CC, new InternetAddress("用户名@163.com"));
            this.mailSender.send(mimeMessage);  
            jsonObject.put("CODE", ReturnMsg.SUCCESS.getCode());
            jsonObject.put("MSG", ReturnMsg.SUCCESS.getMsg());
            return jsonObject;  
        }  
        catch(Exception ex)  
        {  
        	logger.info("Exception ex :"+ex);
            jsonObject.put("CODE", ReturnMsg.ERROR.getCode());
            jsonObject.put("MSG", ReturnMsg.ERROR.getMsg());
            return jsonObject;  
        }  
    }  
}  
