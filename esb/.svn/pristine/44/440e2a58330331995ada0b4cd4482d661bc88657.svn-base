package cn.edugate.esb;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.edugate.esb.pojo.EduModle;
import cn.edugate.esb.pojo.PicConfig;
import cn.edugate.esb.pojo.Sms;
import cn.edugate.esb.util.Utils;


public class EduConfig {
	private static Logger logger = Logger.getLogger(EduConfig.class);
	private EduApplicationContextUtil contextUtil;
	private String configFile;
	private EduModle educonfig;
	private String path;
	private PicConfig picConfig;
	
	public PicConfig getPicConfig() {
		return picConfig;
	}
	public void setPicConfig(PicConfig picConfig) {
		this.picConfig = picConfig;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	private Sms sms;
	
	public Sms getSms() {
		return sms;
	}
	public void setSms(Sms sms) {
		this.sms = sms;
	}
	public EduModle getEduconfig() {
		return educonfig;
	}
	public void setEduconfig(EduModle educonfig) {
		this.educonfig = educonfig;
	}
	public EduApplicationContextUtil getContextUtil() {
		return contextUtil;
	}
	public void setContextUtil(EduApplicationContextUtil contextUtil) {
		this.contextUtil = contextUtil;
	}
	
	public String getConfigFile() {
		return configFile;
	}
	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}
	
	
	@PostConstruct  
    public void  init() {  		
        SAXReader reader = new SAXReader();
		InputStreamReader isr = null;
		Document doc;
		try {
			try {
				this.path = this.contextUtil.getContext().getResource("").getFile().getAbsolutePath();
				isr = new InputStreamReader(new FileInputStream(this.path+configFile), "utf-8");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				doc = reader.read(isr);
				Element root = doc.getRootElement();
				for (Object o : root.elements()) {
					Element e = (Element) o;
					if(e.getName().equals("eduModle")) {
						try {
							EduModle ms = Utils.getPOJOFromXml(e, EduModle.class);
							this.setEduconfig(ms);
						} catch (Exception e1) {
							logger.error("===============add eduModle error==============");
						}						
					}	
					if(e.getName().equals("sms")) {
						try {
							Sms ms = Utils.getPOJOFromXml(e, Sms.class);
							this.setSms(ms);
						} catch (Exception e1) {
							logger.error("===============add sms error==============");
						}						
					}
					if(e.getName().equals("picConfig")) {
						try {
							PicConfig pc = Utils.getPOJOFromXml(e, PicConfig.class);
							this.setPicConfig(pc);
						} catch (Exception e1) {
							logger.error("===============add picConfig error==============");
						}						
					}
				}
			} catch (DocumentException e1) {
				e1.printStackTrace();
			}
			
		} finally {
			if(isr != null) {
				try {
					isr.close();
				} catch (IOException e) {					
				} 
			}
		}
    }  
      
    @PreDestroy  
    public void  dostory(){  
//        System.out.println("I'm  destory method  using  @PreDestroy....."+message);  
    } 
}
