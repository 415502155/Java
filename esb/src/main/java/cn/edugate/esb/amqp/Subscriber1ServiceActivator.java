package cn.edugate.esb.amqp;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.message.GenericMessage;

public class Subscriber1ServiceActivator {

	private static Log logger = LogFactory
			.getLog(Subscriber1ServiceActivator.class);

	public void logXml(GenericMessage<?> msg) throws Exception {
		String text = msg.toString();
		logger.info("### PAYLOAD ###" + text);
	}
}
