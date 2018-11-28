package cn.edugate.esb.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.codehaus.jackson.type.TypeReference;
import org.dom4j.Element;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.edugate.esb.EduConfig;
import cn.edugate.esb.service.ResourcesService;
import cn.edugate.esb.util.HttpRequest.HttpRequestException;


/***
 * 
 * 
 * @Name: 通用工具实现类
 * @Description:  
 * @date  2013-3-20 
 * @version V1.0
 */
@Component
public class UtilImpl implements Util {
	static Logger logger=LoggerFactory.getLogger(UtilImpl.class);
	
	private EduConfig eduConfig;
	@Autowired
	private ResourcesService resourcesService;
	@Autowired
	public void setEduConfig(EduConfig eduConfig) {
		this.eduConfig = eduConfig;
	}
	public UtilImpl()
	{
		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ls.office.util.Util#now()
	 */
	@Override
	public Date now() {
		
		return LSHelper.now();
	}

	@Override
	public Date getDate(Date date) {
		return LSHelper.getDate(date);
	}
	
	@Override
	public Date getTime(Date date) {
		return LSHelper.getTime(date);
	}
	
	
	
	@Override
	public <T> List<T> getPojoFromRequest(Object data, Class<T> pojoClass) {
		// TODO Auto-generated method stub
		return this.getPojoFromRequest(data, pojoClass, null);
	}
	@Override
	public <T> List<T> getPojoFromRequest(Object data, Class<T> pojoClass,
			JacksonSetting setting) {
		// TODO Auto-generated method stub
		return LSHelper.getPojoFromRequest(data, pojoClass,setting);
	}

	@Override
	public <T> T getPOJOFromXml(Element e, Class<T> pojoClass) throws Exception {
		T t = pojoClass.newInstance();
		if(e != null) {
			for (Object po : e.elements()) {
				Element pe = (Element) po;
				String name = pe.attributeValue("name");
				String value= pe.attributeValue("value");
				if(name != null && value != null && !"".equals(name.trim())) {
					name = name.trim();
					value = value.trim();
					String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
					Method[] methods = t.getClass().getMethods();
					Method method = null;
					for (int i = 0; i < methods.length; i++) {
						if(methods[i].getName().equals(methodName)) {
							method = methods[i];
							break;
						}
					}
					if(method != null) {
						if(method.getParameterTypes().length == 1) {
							@SuppressWarnings("rawtypes")
							Class c = method.getParameterTypes()[0];
							Object[] args = new Object[1];
							if(c == Double.class) {
								args[0] = Double.parseDouble(value);
							} else if(c == int.class) {
								args[0] = Integer.parseInt(value);
							} else if(c == Long.class) {
								args[0] = Long.parseLong(value);
							} else if(c == boolean.class) {
								args[0] = Boolean.parseBoolean(value);
							} else {
								args[0] = value;
							}
							method.invoke(t, args);
						}
					}
				}
			}
		}	
		return t;
	}
	@Override
	public <T> T getPojoFromJSON(Object data, Class<T> pojoClass) {
		// TODO Auto-generated method stub
		return this.getPojoFromJSON(data, pojoClass,null);
	}
	@Override
	public <T> T getPojoFromJSON(Object data, Class<T> pojoClass,JacksonSetting setting) {
		// TODO Auto-generated method stub
		return LSHelper.getPojoFromJSON(data, pojoClass,setting);
	}
	
	@Override
	public <T> T getPojoFromJSON(Object data, TypeReference<T> typeRef) {
		// TODO Auto-generated method stub
		return this.getPojoFromJSON(data, typeRef,null);
	}
	@Override
	public <T> T getPojoFromJSON(Object data, TypeReference<T> typeRef,
			JacksonSetting setting) {
		// TODO Auto-generated method stub
		return LSHelper.getPojoFromJSON(data, typeRef,setting);
	}


	@Override
	public <T> List<T> getPojosFromJSON(Object data, Class<T> pojoClass) {
		// TODO Auto-generated method stub
		return this.getPojosFromJSON(data, pojoClass,null);
	}
	@Override
	public <T> List<T> getPojosFromJSON(Object data, Class<T> pojoClass,
			JacksonSetting setting) {
		// TODO Auto-generated method stub
		return LSHelper.getPojosFromJSON(data, pojoClass,setting);
	}

	
	
	@Override
	public String getJSONFromPOJO(Object data) {
		// TODO Auto-generated method stub
		return this.getJSONFromPOJO(data, null);
	}
	
	@Override
	public String getJSONFromPOJO(Object data,JacksonSetting setting) {
		// TODO Auto-generated method stub
		return LSHelper.getJSONFromPOJO(data,setting);
	}

	

	@Override
	public Set<Class<?>> getClasses(String packageName) {
		// TODO Auto-generated method stub
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		// 是否循环迭代
		boolean recursive = true;
		// 获取包的名字 并进行替换
		String packageDirName = packageName.replace('.', '/');
		// 定义一个枚举的集合 并进行循环来处理这个目录下的things
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader()
					.getResources(packageDirName);
			// 循环迭代下去
			while (dirs.hasMoreElements()) {
				// 获取下一个元素
				URL url = dirs.nextElement();
				// 得到协议的名称
				String protocol = url.getProtocol();
				// 如果是以文件的形式保存在服务器上
				if ("file".equals(protocol)) {
					// 获取包的物理路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 以文件的方式扫描整个包下的文件 并添加到集合中
					findClassesByFile(packageName, filePath,
							recursive, classes);
				} else if ("jar".equals(protocol)) {
					// 如果是jar包文件
					findClassesByJar(packageName, classes,
							recursive, packageDirName, url);
				}
			}
		} catch (IOException e) {
			logger.error("", e);
		}

		return classes;
	}

	private void findClassesByJar(String packageName, Set<Class<?>> classes,
			boolean recursive, String packageDirName, URL url) {
		// 定义一个JarFile
		JarFile jar;
		try {
			// 获取jar
			jar = ((JarURLConnection) url.openConnection())
					.getJarFile();
			// 从此jar包 得到一个枚举类
			Enumeration<JarEntry> entries = jar.entries();
			// 同样的进行循环迭代
			while (entries.hasMoreElements()) {
				// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
				JarEntry entry = entries.nextElement();
				String name = entry.getName();
				// 如果是以/开头的
				if (name.charAt(0) == '/') {
					// 获取后面的字符串
					name = name.substring(1);
				}
				// 如果前半部分和定义的包名相同
				if (name.startsWith(packageDirName)) {
					int idx = name.lastIndexOf('/');
					// 如果以"/"结尾 是一个包
					if (idx != -1) {
						// 获取包名 把"/"替换成"."
						packageName = name.substring(0, idx)
								.replace('/', '.');
					}
					// 如果可以迭代下去 并且是一个包
					if ((idx != -1) || recursive) {
						// 如果是一个.class文件 而且不是目录
						if (name.endsWith(".class")
								&& !entry.isDirectory()) {
							// 去掉后面的".class" 获取真正的类名
							String className = name.substring(
									packageName.length() + 1,
									name.length() - 6);
							Class<?> clazz=loadClass(packageName + '.'	+ className);
							// 添加到classes
							if(clazz!=null)classes.add(clazz);
						}
					}
				}
			}
		} catch (IOException e) {
			logger.error("", e);
		}
	}

	private Class<?> loadClass(String name){
		Class<?> clazz=null;
		try {
			clazz=Thread.currentThread().getContextClassLoader()
					.loadClass(name);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("loadClass error:",e);
		}
		return clazz;
	}

	/**
	 * 以文件的形式来获取包下的所有Class
	 * 
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	private void findClassesByFile(String packageName,
			String packagePath, final boolean recursive, Set<Class<?>> classes) {
		// 获取此包的目录 建立一个File
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			// log.warn("用户定义包名 " + packageName + " 下没有任何文件");
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File file) {
				return (recursive && file.isDirectory())
						|| (file.getName().endsWith(".class"));
			}
		});
		// 循环所有文件
		for (File file : dirfiles) {
			// 如果是目录 则继续扫描
			if (file.isDirectory()) {
				findClassesByFile(
						packageName + "." + file.getName(),
						file.getAbsolutePath(), recursive, classes);
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0,
						file.getName().length() - 6);
				Class<?> clazz=loadClass(packageName + '.'	+ className);
				// 添加到classes
				if(clazz!=null)classes.add(clazz);
			}
		}
	}

	@Override
	public Set<Class<?>> getClasses(Package pack) {
		// TODO Auto-generated method stub
		return this.getClasses(pack.getName());
	}

	@Override
	public org.hibernate.criterion.Order[] getOrders(String sort) 
	{
		List<org.hibernate.criterion.Order> orders=new ArrayList<org.hibernate.criterion.Order>();
		if(sort!=null)
		{
			List<Order> sorts=this.getPojosFromJSON(sort,Order.class);
			if(sorts!=null)
			{
				for(Order s : sorts)
				{
					orders.add(s.toHOrder());
				}
			}
		}
		
		return orders.toArray(new org.hibernate.criterion.Order[]{});
	}
	
	@Override
	public Filter[] getFilters(String filters) {
		// TODO Auto-generated method stub
		List<Filter> filterList=new ArrayList<Filter>();
		if(filters!=null)
		{
			List<Filter> fs=this.getPojosFromJSON(filters,Filter.class);
			if(fs!=null)
			{
				for(Filter f : fs)
				{
					filterList.add(f);
				}
			}
		}
		
		return filterList.toArray(new Filter[]{});
	}

	@Override
	public int toInt(Object value, int def) {
		// TODO Auto-generated method stub
		int r=def;
		if(value!=null)
		{
			try
			{
				r=Integer.parseInt(value.toString());
			}catch(Exception e)
			{
				r=def;
			}
		}
		return r;
	}

	@Override
	public long toLong(Object value, long def) {
		// TODO Auto-generated method stub
		long r=def;
		if(value!=null)
		{
			try
			{
				r=Long.parseLong(value.toString());
			}catch(Exception e)
			{
				r=def;
			}
		}
		return r;
	}

	@Override
	public double toDouble(Object value, double def) {
		// TODO Auto-generated method stub
		double r=def;
		if(value!=null)
		{
			try
			{
				r=Double.parseDouble(value.toString());
			}catch(Exception e)
			{
				r=def;
			}
		}
		return r;
	}
	
	public boolean equals(Object s,Object t)
	{
		return LSHelper.equals(s, t);
	}
	
	@Override
	public boolean emptyStr(String input) {
		
		return (input == null) || "".equals(input.trim()); 
	}
	
	@Override
	public Criterion calculatePeriodCriterion(String propertyName, String period) {
		Criterion criterion = null;
		Date today = new Date();
		long todayTime = today.getTime();
		long time = -1;
		long dayTime = 1 * 24 * 60 * 60 * 1000; // 一天的时间毫秒数
		if("day".equalsIgnoreCase(period)) { // 最近一天
			time = todayTime - (1 * dayTime);
		}else if("week".equalsIgnoreCase(period)) { // 最近一周
			time = todayTime - (7 * dayTime);
		}else if("month".equalsIgnoreCase(period)) { // 最近一个月
			time = todayTime - (30 * dayTime);
		}else if("quarter".equalsIgnoreCase(period)) { // 最近一个季度
			time = todayTime - (3 * 30 * dayTime);
		}else if("year".equalsIgnoreCase(period)) { // 最近一年
			time = todayTime - (365 * dayTime);
		}else if("all".equalsIgnoreCase(period)) { // 全部
			return new Conjunction();
		}
		
		if(time > 0) {
			criterion = Restrictions.between(propertyName, new Date(time), today);
		}
		return criterion;
	}
	@Override
	public FilterApply calculatePeriodPropertyFilter(String propertyName, String period) {
		FilterApply filter = null;
		Date today = new Date();
		long todayTime = today.getTime();
		long time = -1;
		long dayTime = 1 * 24 * 60 * 60 * 1000; // 一天的时间毫秒数
		if("day".equalsIgnoreCase(period)) { // 最近一天
			time = todayTime - (1 * dayTime);
		}else if("week".equalsIgnoreCase(period)) { // 最近一周
			time = todayTime - (7 * dayTime);
		}else if("month".equalsIgnoreCase(period)) { // 最近一个月
			time = todayTime - (30 * dayTime);
		}else if("quarter".equalsIgnoreCase(period)) { // 最近一个季度
			time = todayTime - (3 * 30 * dayTime);
		}else if("year".equalsIgnoreCase(period)) { // 最近一年
			time = todayTime - (365 * dayTime);
		}else if("all".equalsIgnoreCase(period)){//全部
			return new PropertyFilter();
		}
		
		if(time > 0) {
			filter = PropertyFilter.ge(propertyName,new Date(time)).and(PropertyFilter.le(propertyName, today));
		}
		return filter;
	}
	
	@Override
	public String abbreviate(String str, int width, String ellipsis) {
		// TODO Auto-generated method stub
		if (str == null || "".equals(str)) {
	        return "";
	    }

	    int d = 0; // byte length
	    int n = 0; // char length
	    for (; n < str.length(); n++) {
	        d = (int) str.charAt(n) > 256 ? d + 2 : d + 1;
	        if (d > width) {
	            break;
	        }
	    }

	    if (d > width) {
	        n = n - ellipsis.length() / 2;
	        return str.substring(0, n > 0 ? n : 0) + ellipsis;
	    }

	    return str = str.substring(0, n);
	}
	@Override
	public String Html2Text(String inputString) {
		// TODO Auto-generated method stub
		String htmlStr = inputString; // 含html标签的字符串
	    String textStr = "";
	    java.util.regex.Pattern p_script;
	    java.util.regex.Matcher m_script;
	    java.util.regex.Pattern p_style;
	    java.util.regex.Matcher m_style;
	    java.util.regex.Pattern p_html;
	    java.util.regex.Matcher m_html;

	    java.util.regex.Pattern p_html1;
	    java.util.regex.Matcher m_html1;

	    try {
	        String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
	        // }
	        String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
	        // }
	        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
	        String regEx_html1 = "<[^>]+";
	        p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
	        m_script = p_script.matcher(htmlStr);
	        htmlStr = m_script.replaceAll(""); // 过滤script标签

	        p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
	        m_style = p_style.matcher(htmlStr);
	        htmlStr = m_style.replaceAll(""); // 过滤style标签

	        p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
	        m_html = p_html.matcher(htmlStr);
	        htmlStr = m_html.replaceAll(""); // 过滤html标签

	        p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
	        m_html1 = p_html1.matcher(htmlStr);
	        htmlStr = m_html1.replaceAll(""); // 过滤html标签

	        textStr = htmlStr;

	    } catch (Exception e) {
	        System.err.println("Html2Text: " + e.getMessage());
	    }

	    return textStr; // 返回文本字符串
	}
	
	@Override
	public String ReadFile(String path) {
		File file = new File(path);		
		BufferedReader reader = null;
		String laststr = "";
		try {			
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {				
				laststr += tempString;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return laststr;
	}
	
	public String SMSsend(String phone, String content) throws Exception {
		// TODO Auto-generated method stub
        String s = HttpRequest.get("http://211.147.239.62:9050/cgi-bin/sendsms" + 
		String.format("?username=%s&password=%s&to=%s&text=%s&subid=%s&msgtype=4",this.eduConfig.getSms().getUserName(),this.eduConfig.getSms().getPassWord(),phone,URLEncoder.encode(content,"gb2312"),"")).body();
		
		return "0".equals(s)?"成功":"失败";
		
	}
	
	@Override
	public String wxsms(String phone, String content) throws Exception{
		// TODO Auto-generated method stub
//		String s = HttpRequest.get("http://211.147.239.62:9050/cgi-bin/sendsms" + 
//				String.format("?username=%s&password=%s&to=%s&text=%s&subid=%s&msgtype=4","sjwy","sjwy-123",phone,URLEncoder.encode(content,"gb2312"),"")).body();
		
		String s = HttpRequest.get("http://211.147.239.62:9050/cgi-bin/sendsms" + 
				String.format("?username=%s&password=%s&to=%s&text=%s&subid=%s&msgtype=4","wxy@sjwy","sjwywxy2336",phone,URLEncoder.encode(content,"gb2312"),"")).body();
		logger.info("send s:"+s);		
				return "0".equals(s)?"成功":"失败";
	}
	
	public String schoolsms(String tel, String text,String title) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String,List<String>>> ls = new ArrayList<Map<String,List<String>>>();
		Map<String,List<String>> data = new HashMap<String, List<String>>();
		List<String> tels = new ArrayList<String>();
		tels.add(tel);
		data.put("tels", tels);
		ls.add(data);
		String sms_url = FileProperties.getProperty("jysms");
//		String url = sms_url + 
//		        String.format("?sdata=%s&text=%s&title=%s",this.getJSONFromPOJO(ls),URLEncoder.encode(text,"UTF-8"), URLEncoder.encode(title,"UTF-8"));
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("sdata", this.getJSONFromPOJO(ls));
		map.put("text", text);
		map.put("title", title);	
        HttpRequest.post(sms_url).form(map).body();
//        HttpRequest.get(url).body();
        
		return "成功";
	}
	
	
	
	@Override
	public String getFilePathUrl(Integer id, String ext) {
		// TODO Auto-generated method stub
		String url = this.eduConfig.getEduconfig().getPicurl()+Utils.getPathById(id)+id.toString()+"."+ext;
		return url;
	}
	
	@Override
	public String getPathByPicName(String logo) {
		try{
		String[] params = logo.split("\\.");
		Integer id = Integer.parseInt(params[0]);
		String url = this.getFilePathUrl(id, params[1]);
		return url;
		}catch(Exception e){
			return "";
		}
	}
	

    /**
     * 上传文件
     * @param request
     * @return 文件名
     */
	@Override
    public String upload(MultipartHttpServletRequest multipartRequest,String fileName){
		String filePathName = "";
		BufferedImage bi;
		try {
			MultipartFile file = multipartRequest.getFile(fileName);
			if (file!=null&&!file.isEmpty()) {
				bi = ImageIO.read(file.getInputStream());
				int width = 0;
				int height = 0;
				width = bi.getWidth();
				height = bi.getHeight();
				String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
				InputStream fin = file.getInputStream(); 
				Long length = file.getSize();
				filePathName = resourcesService.savePicture(Constant.PICTURE_TYPE,Constant.YES,fin,length.intValue(),ext,width,height);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filePathName;
	}
	
	/**
     * 上传文件
     * @param request
     * @return 文件名
     */
	@Override
    public String uploadSWF(MultipartHttpServletRequest multipartRequest,String fileName){
		String filePathName = "";
		@SuppressWarnings("unused")
		BufferedImage bi;
		try {
			MultipartFile file = multipartRequest.getFile(fileName);
			if (file!=null&&!file.isEmpty()) {
				bi = ImageIO.read(file.getInputStream());
				int width = 0;
				int height = 0;
				//width = bi.getWidth();
				//height = bi.getHeight();
				String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
				InputStream fin = file.getInputStream(); 
				Long length = file.getSize();
				filePathName = resourcesService.savePicture(Constant.PICTURE_TYPE,Constant.YES,fin,length.intValue(),ext,width,height);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filePathName;
	}
	
	
	
	@Override
	public Object getPathByExcelName(String filePathName) {
		// TODO Auto-generated method stub
		try{
			String[] params = filePathName.split("\\.");
			Integer id = Integer.parseInt(params[0]);
			String url = this.getExcelFilePathUrl(id, params[1]);
			return url;
			}catch(Exception e){
				return "";
			}
	}
	private String getExcelFilePathUrl(Integer id, String ext) {
		// TODO Auto-generated method stub
		String url = this.eduConfig.getEduconfig().getExcelurl()+Utils.getPathById(id)+id.toString()+"."+ext;
		return url;
	}
	@Override
	public void schoolsms(String[] es, String content, String title) {
		// TODO Auto-generated method stub
		List<Map<String,List<String>>> ls = new ArrayList<Map<String,List<String>>>();
		Map<String,List<String>> data = new HashMap<String, List<String>>();
		List<String> tels = new ArrayList<String>();
		for (String tel : es) {
			tels.add(tel);
		}
		data.put("tels", tels);
		ls.add(data);
		String sms_url = FileProperties.getProperty("jysms");
        try {
//			HttpRequest.get(sms_url + 
//			String.format("?sdata=%s&text=%s&title=%s",this.getJSONFromPOJO(ls),URLEncoder.encode(content,"UTF-8"), URLEncoder.encode(title,"UTF-8"))).body();			
			Map<String, Object> map = new HashMap<String, Object>();
	        map.put("sdata", this.getJSONFromPOJO(ls));
			map.put("text", content);
			map.put("title", title);	
	        HttpRequest.post(sms_url).form(map).body();
			
		} catch (HttpRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
