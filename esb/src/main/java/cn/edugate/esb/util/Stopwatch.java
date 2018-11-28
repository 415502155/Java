package cn.edugate.esb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 秒表
 * 
 */
public class Stopwatch {
	private final static Logger logger = LoggerFactory.getLogger(Stopwatch.class);
	
	private boolean nano;

	private long from;

	private long to;

	public static Stopwatch begin() {
		Stopwatch sw = new Stopwatch();
		sw.start();
		return sw;
	}

	public static Stopwatch beginNano() {
		Stopwatch sw = new Stopwatch();
		sw.nano = true;
		sw.start();
		return sw;
	}

	public static Stopwatch create() {
		return new Stopwatch();
	}

	public static Stopwatch createNano() {
		Stopwatch sw = new Stopwatch();
		sw.nano = true;
		return sw;
	}

	public static Stopwatch run(Runnable atom) {
		Stopwatch sw = begin();
		atom.run();
		sw.stop();
		return sw;
	}
	
	public static Stopwatch run(String message,Runnable atom) {
		Stopwatch sw=run(atom);
		logger.info(message+" "+sw.toString());
		return sw;
	}

	public static Stopwatch runNano(Runnable atom) {
		Stopwatch sw = beginNano();
		atom.run();
		sw.stop();
		return sw;
	}
	
	public static Stopwatch runNano(String message,Runnable atom) {
		Stopwatch sw=runNano(atom);
		logger.info(message+" "+sw.toString());
		return sw;
	}

	public long start() {
		from = nano ? System.nanoTime() : System.currentTimeMillis();
		return from;
	}

	public long stop() {
		to = nano ? System.nanoTime() : System.currentTimeMillis();
		return to;
	}

	public long getDuration() {
		return to - from;
	}

	public long getStartTime() {
		return from;
	}

	public long getEndTime() {
		return to;
	}

	@Override
	public String toString() {
		return String.format(	"Total: %d%s : [%s]=>[%s]",
								this.getDuration(),
								(nano ? "ns" : "ms"),
								new java.sql.Timestamp(from).toString(),
								new java.sql.Timestamp(to).toString());
	}
	
	public String toString(String msg) {
		return String.format("%s >> %s",msg,this.toString());
	}
	
	public void log(String msg)
	{
		this.stop();
		logger.info(this.toString(msg));
	}

}
