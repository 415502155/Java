package cn.edugate.esb.imp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import cn.edugate.esb.EduApplicationContextUtil;
import cn.edugate.esb.pojo.BackMenuPojo;
import cn.edugate.esb.pojo.SubMenuPojo;
import cn.edugate.esb.service.ManageService;
import cn.edugate.esb.util.Utils;

@Component
@Transactional("transactionManager")
public class ManageImpl implements ManageService {
	private static Logger logger = Logger.getLogger(ManageImpl.class);
	private EduApplicationContextUtil contextUtil;
	private String configFile="/etc/tree.xml";
	@Autowired
	public void setContextUtil(EduApplicationContextUtil contextUtil) {
		this.contextUtil = contextUtil;
	}

	@Override
	public List<BackMenuPojo> getBackMenus() {
		// TODO Auto-generated method stub
		SAXReader reader = new SAXReader();
		InputStreamReader isr = null;
		Document doc;
		try {
			String path;
			path = this.contextUtil.getContext().getResource("").getFile().getAbsolutePath();
			isr = new InputStreamReader(new FileInputStream(path+configFile), "utf-8");
			doc = reader.read(isr);
			Element root = doc.getRootElement();
			List<BackMenuPojo> xmlmenus = new ArrayList<BackMenuPojo>();
			for (Object o : root.elements()) {
				Element e = (Element) o;					
				if(e.getName().equals("tree")) {	
					BackMenuPojo menu = new BackMenuPojo();
					List<SubMenuPojo> subs = new ArrayList<SubMenuPojo>();
					for (Object mio : e.elements()){
						Element me = (Element) mio;						
						if(me.attributeValue("name")!=null&&me.attributeValue("name").equals("title")) {
							menu.setTitle(me.attributeValue("value"));					
						}else{
							if(me.getName().equals("sub")) {
								SubMenuPojo sub = Utils.getPOJOFromXml(me, SubMenuPojo.class);
								subs.add(sub);
							}
						}
					}
					menu.setSubs(subs);
					xmlmenus.add(menu);
				}					
			}	
			return xmlmenus;
		} catch (Exception e1) {			
			logger.error("===============add BackMenuPojo error==============");
			return null;
		}finally {
			if(isr != null) {
				try {
					isr.close();
				} catch (IOException e) {					
				} 
			}
		}
	}
	
	

}
