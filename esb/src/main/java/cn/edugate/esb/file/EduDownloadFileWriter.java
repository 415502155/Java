package cn.edugate.esb.file;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.cleverframe.fastdfs.protocol.storage.callback.DownloadCallback;
import org.cleverframe.fastdfs.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EduDownloadFileWriter<T> implements DownloadCallback<T> {
	
	static Logger logger=LoggerFactory.getLogger(EduDownloadFileWriter.class);	
	private String fileName;

    public EduDownloadFileWriter(String fileName) {
        this.fileName = fileName;
    }
	@SuppressWarnings("unchecked")
	@Override
	public T receive(InputStream inputStream) throws IOException {
		// TODO Auto-generated method stub
		FileOutputStream out = null;
        InputStream in = null;
        try {
        	logger.info("=====EduDownloadFileWriter========"+fileName);
            out = new FileOutputStream(fileName);
            in = new BufferedInputStream(inputStream);
            // 閫氳繃ioutil 瀵规帴杈撳叆杈撳嚭娴侊紝瀹炵幇鏂囦欢涓嬭浇
            IOUtils.copy(in, out);
            out.flush();
        } finally {
            // 鍏抽棴娴�            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
        return (T)fileName;
	}

}
