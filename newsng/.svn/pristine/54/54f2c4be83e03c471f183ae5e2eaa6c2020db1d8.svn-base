package sng.util;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.io.IOUtils;

public final class ESBPropertyReader {
	private ESBPropertyReader() {
    }

    private static final Properties props;
    static {
        InputStream in = ESBPropertyReader.class.getClassLoader().getResourceAsStream("esb.properties");
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
