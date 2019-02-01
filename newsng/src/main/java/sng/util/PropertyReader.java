package sng.util;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.cleverframe.fastdfs.utils.StringUtils;

public final class PropertyReader {
	
	private static final String filename="config.properties";
	private static String switchs="";
	
	
	private PropertyReader() {
    }

    private static final Properties props;
    static {
        InputStream in = PropertyReader.class.getClassLoader().getResourceAsStream(filename);
        props = new Properties();
        try {
            props.load(in);
            switchs = props.getProperty("switch");
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
    
    /**
     * 获取通用配置内容
     */
    public static String getCommonProperty(String name) {
    	String value = props.getProperty(name);
    	/*if(StringUtils.isBlank(value)){
    		return getProperty(name);
    	}else{
    		return value;
    	}*/
    	return value;
    }
    
    /**
     * 获取当前环境下的配置内容
     */
    public static String getProperty(String name) {
    	String value = props.getProperty(switchs+"."+name);
    	/*if(StringUtils.isBlank(value)){
    		return getCommonProperty(name);
    	}else{
    		return value;
    	}*/
    	return value;
    }
}
