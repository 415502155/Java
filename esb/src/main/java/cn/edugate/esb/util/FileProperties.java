package cn.edugate.esb.util;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.io.IOUtils;

public final class FileProperties {
	private FileProperties() {
    }

    private static final Properties props;
    static {
        InputStream in = FileProperties.class.getClassLoader().getResourceAsStream("file.properties");
        props = new Properties();
        try {
            props.load(in);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
    public static String getProperty(String name) {
        return props.getProperty(name);
    }
}
