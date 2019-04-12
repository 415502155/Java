package com.shihy.springboot.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanGenerator;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:30:35
 *
 */
public class CommonUtils {

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 格式化日期
     * @param date
     * @param pattern 如果为null则默认格式为yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String dateFormat(Date date, String pattern) {
        if (pattern == null) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 格式化日期
     * @param date
     * @param pattern 如果为null则默认格式为yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String dateFormat(Timestamp date, String pattern) {
        if (pattern == null) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        return new SimpleDateFormat(pattern).format(date);
    }
    
    /**
     * 格式化日期
     * @param date
     * @param pattern 如果为null则默认格式为yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String dateFormat(String date, String pattern) throws ParseException {
        if (pattern == null) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        Date d=stringToDate(date,pattern);
        return new SimpleDateFormat(pattern).format(d);
    }

    /**
     * 将String类型的日期格式化为Date类型
     * @param date
     * @param pattern 如果为null则默认格式为yyyy-MM-dd HH:mm:ss
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String date, String pattern) throws ParseException {
        if (pattern == null) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        DateFormat formater = new SimpleDateFormat(pattern);
        return formater.parse(date);
    }

    /**
     * 格式化数字 自动省去末尾0
     * @param num
     * @param value 保留的小数位
     * @return
     */
    public static String numberFormat(double num, int value) {
        NumberFormat formater = DecimalFormat.getInstance();
        formater.setMaximumFractionDigits(value);
        formater.setGroupingUsed(false);
        return formater.format(num);
    }

    /**
     * 格式化数字 自动省去末尾0
     * @param str
     * @param value 保留的小数位
     * @return
     */
    public static String numberFormat(String str, int value) {
        double num = Double.parseDouble(str);
        NumberFormat formater = DecimalFormat.getInstance();
        formater.setMaximumFractionDigits(value);
        formater.setGroupingUsed(false);
        return formater.format(num);
    }

    /**
     * 格式化数字
     * @param str
     * @param formatStyle
     *            传入null时默认为0.00
     * @return
     */
    public static String numberFormat(String str, String formatStyle) {
        if (str == null || ("").equals(str)) {
            return "";
        }
        if (formatStyle == null) {
            formatStyle = "0.00";
        }
        Double num = Double.parseDouble(str);
        DecimalFormat formater = new DecimalFormat(formatStyle);
        formater.setRoundingMode(RoundingMode.HALF_UP);
        return formater.format(num);
    }

    /**
     * 格式化数字
     * @param num
     * @param formatStyle
     *            传入null时默认为0.00
     * @return
     */
    public static String numberFormat(double num, String formatStyle) {
        if (formatStyle == null) {
            formatStyle = "0.00";
        }
        DecimalFormat formater = new DecimalFormat(formatStyle);
        formater.setRoundingMode(RoundingMode.HALF_UP);
        return formater.format(num);
    }

    /**
     * util.Date转换为sql.Date
     * @param date
     * @return
     */
    public static java.sql.Date utilToSql(Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * 返回传入的日期的前N天或后N天的日期
     * @param appDate
     * @param format
     * @param days
     * @return
     */
    public static String getFutureDay(String appDate, String format, int days) {
        String future = "";
        try {
            Calendar calendar = GregorianCalendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Date date = simpleDateFormat.parse(appDate);
            calendar.setTime(date);
            calendar.add(Calendar.DATE, days);
            date = calendar.getTime();
            future = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return future;
    }
    
    /**
     * 返回传入的日期的前N个月或后N个月的日期
     * @param appDate
     * @param format
     * @param months
     * @return
     */
    public static String getFutureMonth(String appDate, String format, int months) {
        String future = "";
        try {
            Calendar calendar = GregorianCalendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Date date = simpleDateFormat.parse(appDate);
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, months);
            date = calendar.getTime();
            future = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return future;
    }

    /**
     * 返回当前的时间戳
     */
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }


    /**
     * 取得随机字符串
     * @param len
     *            长度
     * @param type
     *            类型 1:数字+字母混合 2:字母 3:数字
     * @return
     */
    public static String getRandomStr(int len, int type) {
        String str = "";
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            if (type == 1) {
                String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
                if (("char").equals(charOrNum)) {
                    str += (char) (choice + random.nextInt(26));
                } else if (("num").equals(charOrNum)) {
                    str += String.valueOf(random.nextInt(10));
                }
            } else if (type == 2) {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                str += (char) (choice + random.nextInt(26));
            } else if (type == 3) {
                str += String.valueOf(random.nextInt(10));
            } else {
                str = "";
            }
        }
        return str;
    }

    /**
     * 根据长度和给定的字符数组生成随机字符串
     * @param len 字符串长度
     * @param charStr 字符数据组
     * @return
     */
    public static String getRandomStr(int len, String charStr) {
        String result = "";
        if (charStr == null) {
            return result;
        }
        int max = charStr.length();
        if (max == 0) {
            return result;
        }
        
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            int choice = random.nextInt(max) % max;
            result += charStr.charAt(choice);
        }
        return result;
    }

    /**
     * 将字符串的指定位置替换成相同的字符
     * @param str
     * @param startindex 开始替换的位置
     * @param endindex 结束替换的位置
     * @param newChar 替换成的字符
     * @return
     * @throws Exception
     */
    public static String strReplace(String str, int startindex, int endindex, String newChar) throws Exception {
        String s1 = "";
        String s2 = "";
        try {
            s1 = str.substring(0, startindex - 1);
            s2 = str.substring(endindex, str.length());
        } catch (Exception ex) {
            throw new Exception("替换的位数大于字符串的位数");
        }
        Integer length = endindex - startindex;
        String charTmp = newChar;
        for (int i = 0; i < length; i++) {
            newChar += charTmp;
        }
        return s1 + newChar + s2;
    }

    /**
     * 检测密码强度
     * @param pwd
     * @return
     */
    public static int checkPwd(String pwd) {
        String regex = "^(?:([a-z])|([A-Z])|([0-9])|(.)){6,}|(.)+$";
        return pwd.replaceAll(regex, "$1$2$3$4$5").length();
    }
    
    public static Map<String,Object> doParameters(HttpServletRequest request){
		Map<String,Object> param=new HashMap<String,Object>(16);
		Map<String,String[]> paramMap=request.getParameterMap();
		for(String key : paramMap.keySet()){
			String[] value=paramMap.get(key);
			if(value!=null && !"".equals(value[0])){
				param.put(key, StringUtils.join(value));
			}
		}
		return param;
	}
    
    /**
     * 获取客户端真实IP
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {  
        String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if("127.0.0.1".equals(ip)){  
				InetAddress inet=null;
			try {
				inet = InetAddress.getLocalHost();
		    }catch (UnknownHostException e) {
		    	e.printStackTrace();
		    }
		    	ip= inet.getHostAddress();
			}
		} 
		if(ip!=null && ip.length()>15){ 
			if(ip.indexOf(",")>0){
				ip = ip.substring(0,ip.indexOf(","));
			}
		}
        return ip;  
    }
    
    public static boolean isBaseClass(Class<?> clz) {
		return clz != null && clz.getClassLoader() == null;
	}
    
    /**
     * 判断是否为数值
     * @param str
     * @return
     */
    public static boolean isNumber(String str){
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }
    
    /**
     * 动态添加属性值
     * @param t 实体
     * @param columns 属性数组
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <T> T generateBean(T t,String... columns){
		BeanGenerator generator = new BeanGenerator();
		generator.setSuperclass(t.getClass());
		for(String str : columns){
			generator.addProperty(str, Object.class); 
		}
		Object o=generator.create();
		BeanUtils.copyProperties(t, o);
		return (T)o;
	}
    
    /**
     * 下载文件
     * @param request
     * @param response
     * @param realPath 文件真实路径
     * @param fileName 给下载的文件起个名字,需要带后缀
     * @throws IOException
     */
    public static void downFile(HttpServletRequest request,HttpServletResponse response,String realPath,String fileName) throws IOException{
    	String agent = request.getHeader("USER-AGENT");
		response.setContentType("application/x-download");
		if(agent!=null && (agent.indexOf("Firefox")>-1 || agent.indexOf("Safari")>-1)){
			response.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes("UTF-8"),"ISO8859-1"));
		}else{
			response.setHeader("Content-disposition", "attachment; filename="+ URLEncoder.encode(fileName, "UTF-8"));
		}
		InputStream in=new BufferedInputStream(new FileInputStream(realPath));
		OutputStream os=response.getOutputStream();
		byte[] bit=new byte[1024];
		int i=0;
		while (-1 != (i = in.read(bit, 0, bit.length))){
			os.write(bit,0,i);
		}
		os.flush();
		os.close();
		in.close();
    }
    
    /**
     * 将\r\n替换为<br>
     * @param str
     * @return
     */
    public static String replaceRN(String str){
    	return str.replaceAll("\r\n", "<br>").replaceAll("\n\r", "<br>").replaceAll("\n", "<br>").replaceAll("\r", "<br>");
    }
    
    /**
     * 判断浏览器版本
     * @param agent
     * @return
     */
    public static String getBrowserName(String agent){
    	agent=agent.toLowerCase();
    	String browser="other";
    	if(agent.indexOf("msie 6")>-1){
    		browser="ie6";
    	}else if(agent.indexOf("msie 7")>-1){
			browser="ie7";
		}else if(agent.indexOf("msie 8")>-1){
			browser="ie8";
		}else if(agent.indexOf("msie 9")>-1){
			browser="ie9";
		}else if(agent.indexOf("msie 10")>-1){
			browser="ie10";
		}else if(agent.indexOf("gecko")>-1 && agent.indexOf("rv:11")>-1){
			browser="ie11";
		}else if(agent.indexOf("chrome")>-1){
			browser="chrome";
		}else if(agent.indexOf("firefox")>-1){
			browser="firefox";
		}
		return browser;
    }
    
    /**
     * 判断是否为空
     * <pre>
     * isBlank(null)      = true
     * isBlank("")        = true
     * isBlank(" ")       = true
     * isBlank("bob")     = false
     * isBlank("  bob  ") = false
     * </pre>
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 实体转Map
     * @param bean
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map bean2map(Object bean){
    	Map map=new HashMap(16);
    	try{
	    	Class type=bean.getClass();
	    	BeanInfo beanInfo=Introspector.getBeanInfo(type);
	    	PropertyDescriptor[] propertyDescriptor=beanInfo.getPropertyDescriptors();
	    	for(PropertyDescriptor descriptor : propertyDescriptor){
	    		String propertyName=descriptor.getName();
    			if(!"class".contentEquals(propertyName)){
	    			Method readMethod=descriptor.getReadMethod();
	    			Object result=readMethod.invoke(bean);
	    			map.put(propertyName, result);
	    		}
	    	}
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    	return map;
    }
    
    /**
     * Map转实体
     * @param type
     * @param map
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static Object map2bean(Class type,Map map){
    	Object obj=null;
    	try{
    		obj=type.newInstance();
    		BeanInfo beanInfo=Introspector.getBeanInfo(type);
    		PropertyDescriptor[] propertyDescriptor=beanInfo.getPropertyDescriptors();
	    	for(PropertyDescriptor descriptor : propertyDescriptor){
	    		String propertyName=descriptor.getName();
	    		if(map.containsKey(propertyName)){
	    			Object value=map.get(propertyName);
	    			descriptor.getWriteMethod().invoke(obj, value);
	    		}
	    	}
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    	return obj;
    }
    
    /**
     * 取得最小值和最大值之间的随机整数
     * @param min
     * @param max
     * @return
     */
    public static int getRandomInteger(int min,int max) {
    	Random rand=new Random();
		return rand.nextInt(max-min+1)+min;
    }

    public static void main(String[] args) {
        try {
        	
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /***
     * 
     *  @Description: 根据传入的身份证号截取出生日期
     *  @param idCard
     *  @return
     */
	public static String getBirthday (String idNumber){ 
		String year = idNumber.substring(6, 10);// 截取年
		String month = idNumber.substring(10, 12);// 截取月份 
		String day = idNumber.substring(12, 14);// 截取天 
		return year+"-"+month+"-"+day;
	}
	
	public static BigDecimal jian(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2);
	}
	
	public static BigDecimal jian(String v1,String v2){
		BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2);
	}
	
	/***
	 * @Description: 数字截取后几位,四舍五入
	 * @param: d
	 * @param: digit
	 */
	public static double round(double d, int digit) {
		
		BigDecimal b = new BigDecimal(d);
		return b.setScale(digit, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	/***
	 * 
	 * @Description: 四舍五入
	 * @param d
	 * @param type 如： "#.00" 即表示为保留小数点后两位  几个0代表保留的位数
	 * @return   
	 * @return double  
	 * @throws @throws
	 */
	public static double subDecimalFormat(double d, String type) {
		if (StringUtils.isBlank(type)) {
			type = "#";
		}
		java.text.DecimalFormat df = new java.text.DecimalFormat(type); 
		double d1 = Double.parseDouble(df.format(d));
		return d1;
	}
	

	/**
	 * @Description: 将浮点数转为百分数
	 * @param d
	 * @param IntegerDigits 小数点前保留的位数
	 * @param FractionDigits 小数点后保留的位数
	 * @return String
	 * @time 2018年10月1日 上午11:09:35
	 */
	public static String getPercentFormat(double d,int integerDigits,int fractionDigits){
		  NumberFormat nf = java.text.NumberFormat.getPercentInstance(); 
		  nf.setMaximumIntegerDigits(integerDigits);//小数点前保留几位
		  nf.setMinimumFractionDigits(fractionDigits);// 小数点后保留几位
		  String str = nf.format(d);
		  return str;
	}
	
	/**
	 * @Description: 将百分数转为浮点数
	 * @param d
	 * @return double
	 * @time 2018年10月1日 上午11:09:35
	 */
	public static double getPercentToDouble(String d, int digit){
		NumberFormat nf=NumberFormat.getPercentInstance();
		Number m = null;
		try {
			m = nf.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    return round(Double.parseDouble(String.valueOf(m.toString())), digit) ;
	}
	/***
	 * 
	 * 对象改为局部变量每一个线程拥有一个单独的SimpleDateFormat对象。 
	 * 我们可以将SimpleDateFormat改为一个局部变量使每个线程拥有一个SimpleDateFormat对象实例，这样在多线程环境下每个线程都用属于自己的实例去操作日期，
	 * 因为每个线程中程序是串行执行这样就可以保证线程安全性了。但是这种做法的缺点是每创建一个线程就会新创建一个SimpleDateFormat实例，当线程退出时该对象就会被销毁，
	 * 这样就会频繁地创建和销毁对象，效率较低。
	 * 我们用ThreadLocal创建一个日期工具类DateUtil，ThreadLocal以日期模式为key，以SimpleDateFormat为值，
	 * 对于同一种日期模式，同一个线程，只会创建同一个SimpleDateFormat实例。
	 */
	//***********************************************begining***************************************************//
	private static ThreadLocal<Map<String, SimpleDateFormat>> dateFormatMap = new ThreadLocal<Map<String,SimpleDateFormat>>(){
		@Override
		protected Map<String, SimpleDateFormat> initialValue() {
			// TODO Auto-generated method stub
			return new HashMap<String, SimpleDateFormat>(16);
		}
	};
	
	private static SimpleDateFormat getSimpleDateFormat(final String pattern) {
		Map<String, SimpleDateFormat> map = dateFormatMap.get();
		SimpleDateFormat simpleDateFormat = map.get(pattern);
		if (simpleDateFormat == null) {
			simpleDateFormat = new SimpleDateFormat(pattern);
			map.put(pattern, simpleDateFormat);
		}
		return simpleDateFormat;
	}
	
	public static String format(Date date, String pattern) {
		return getSimpleDateFormat(pattern).format(date);
	}	
	
	public static Date parse(String date, String pattern) throws ParseException {
		return getSimpleDateFormat(pattern).parse(date);
	}
	//***********************************************ending***************************************************//
	/***
	 * @Description: 并集
	 * @param @param list1
	 * @param @param list2
	 * @return void  
	 * @throws @throws
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Integer> unionSet(List<Integer> list1, List<Integer> list2) {
		//求并集
		Set result = new HashSet();
		result.addAll(list1);
		result.addAll(list2);
		List<Integer> list = new ArrayList<>(result);
		return list;
	}
	/***
	 * @Description: 交集
	 * @param @param list1
	 * @param @param list2
	 * @return void  
	 * @throws @throws
	 */
	public static List<Integer> intersectionSet(List<Integer> list1, List<Integer> list2) {
		//List<Integer> list1 = Arrays.asList(arr1);
		//List<Integer> list2 = Arrays.asList(arr2);
		
		// 创建集合 求交集
		Collection<Integer> c1 = new ArrayList<Integer>(list1);
		Collection<Integer> c2 = new ArrayList<Integer>(list2);
		c1.retainAll(c2);
		List<Integer> list = new ArrayList<Integer>(c1);
		System.out.println("arr1与arr2交集结果：" + c1);
		return list;
	}
	/***
	 * @Description: arr1集合中存在的元素但是arr2中不存在
	 * @param @param list1
	 * @param @param list2
	 * @param @return
	 * @return List<Integer>  
	 * @throws @throws
	 */
	public static List<Integer> existence(List<Integer> list1, List<Integer> list2) {
		int len1 = list1.size();
		int len2 = list2.size();
		//求arr1存在的元素，arr2不存在
		List<Integer> l1 = new ArrayList<Integer>();
		for(int i = 0; i < len1; i++) {
			Integer num = list1.get(i);
			if (!list2.contains(num)) {
				l1.add(num);
			}
		}
		System.out.println("arr1集合中存在的元素但是arr2中不存在 ：" + l1);
		return l1;
	}
	
	public static Date getDate(Long addTime) {
		long current = System.currentTimeMillis();//当前时间毫秒数
        long add_min = current+addTime;//
        Date date = new Date(add_min);
		return date;
	}
}
