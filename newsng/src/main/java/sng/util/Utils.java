package sng.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ObjectUtils;

import sng.comparator.MapKeyComparator;


public class Utils {
	public final static String MD5(String s) {
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < md.length; i++) {
				int val = ((int) md[i]) & 0xff;
				if (val < 16)
					sb.append("0");
				sb.append(Integer.toHexString(val));
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

	public static String getFirstPath(String path) {
		String ret = path;
		if (!"/".equals(path)) {
			int start = path.indexOf('/');
			int end = path.indexOf('/', 1);
			String name = ret.substring(start + 1, end);
			ret = "/" + name + "/";
		}
		return ret;
	}

	public static String streamToString(InputStream inputStream) throws IOException {
		Writer writer = new StringWriter();
		byte[] b = new byte[4096];
		for (int n; (n = inputStream.read(b)) != -1;) {
			writer.append(new String(b, 0, n));
		}
		return writer.toString();
	}

	public static <T> T getPOJOFromXml(Element e, Class<T> pojoClass) throws Exception {
		T t = pojoClass.newInstance();
		if (e != null) {
			for (Object po : e.elements()) {
				Element pe = (Element) po;
				String name = pe.attributeValue("name");
				String value = pe.attributeValue("value");
				if (name != null && value != null && !"".equals(name.trim())) {
					name = name.trim();
					value = value.trim();
					String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
					Method[] methods = t.getClass().getMethods();
					Method method = null;
					for (int i = 0; i < methods.length; i++) {
						if (methods[i].getName().equals(methodName)) {
							method = methods[i];
							break;
						}
					}
					if (method != null) {
						if (method.getParameterTypes().length == 1) {
							@SuppressWarnings("rawtypes")
							Class c = method.getParameterTypes()[0];
							Object[] args = new Object[1];
							if (c == Double.class) {
								args[0] = Double.parseDouble(value);
							} else if (c == int.class) {
								args[0] = Integer.parseInt(value);
							} else if (c == Long.class) {
								args[0] = Long.parseLong(value);
							} else if (c == boolean.class) {
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


	public static String makecode() {
		// String str = "abcdefghijkmnopqrstuvwsyzABCDEFGHIJKMNOPQRSTUVWSYZ0123456789";
		String str = "0123456789";
		String code = "";
		for (int i = 0; i < 4; i++) {
			Random rand = new Random();
			int j = rand.nextInt(str.length());
			code += str.substring(j, j + 1);
		}
		return code;
	}

	
	
	/**
     * 获取map中第一个数据值
     *
     * @param <K> Key的类型
     * @param <V> Value的类型
     * @param map 数据源
     * @return 返回的值
     */
    public static <K, V> K getFirstOrNull(Map<K, V> map) {
        K obj = null;
        for (Entry<K, V> entry : map.entrySet()) {
            obj = entry.getKey();
            if (obj != null) {
                break;
            }
        }
        return obj;
    }
    
   
    /**
     * 数据库保存时获取真实值
     * @param arg
     * @return
     */
    public static String getValue(String arg) {
		String result = StringUtils.isBlank(arg) ? null : arg.trim();
		return result;
	}
    
	public static String getPathById(Integer id) {
		String path = ((int) Math.floor(id / 10000) % 100) + File.separator;
		path += ((int) Math.floor(id / 100) % 100) + File.separator;
		return path;
	}

	public static Byte getSexByStr(String ssex) {
		// TODO Auto-generated method stub
		Byte sex = null;
		switch (ssex) {
		case "男":
			sex = (byte)0;
			break;
		case "女":
			sex = (byte)1;
			break;
		default:
			break;
		}
		return sex;
	}
	

	public static String getPathByPicName(String logo) {
		try{
			String[] params = logo.split("\\.");
			Integer id = Integer.parseInt(params[0]);
			//String url = ESBPropertyReader.getProperty("esbRequestUrl")+"res/pic/"+Utils.getPathById(id)+id.toString()+"."+params[1];
			String url = ESBPropertyReader.getProperty("resourceUrl")+"res/pic/"+Utils.getPathById(id)+id.toString()+"."+params[1];
			return url;
		}catch(Exception e){
			return "";
		}
	}
	
	/**
	 * 获取对象中属性值为null的属性名称
	 * @param source
	 * @return
	 */
	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			if (src.getPropertyDescriptor(pd.getName()) != null) {
				Method getMethod = pd.getReadMethod();// 获得get方法
				if (getMethod != null) {
					Object srcValue = src.getPropertyValue(pd.getName());
					if (srcValue == null) {
						emptyNames.add(pd.getName());
					} else if (srcValue instanceof CharSequence) {
						if (ObjectUtils.isEmpty((String) srcValue)) {
							emptyNames.add(pd.getName());
						}
					}
				}
			}
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	/**
	 * 使用 Map按key进行排序
	 * 
	 * @param <T>
	 * @param map
	 * @return
	 */
	public static <T> Map<String, T> sortMapByKey(Map<String, T> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}

		Map<String, T> sortMap = new TreeMap<String, T>(new MapKeyComparator());

		sortMap.putAll(map);

		return sortMap;
	}
	
}
