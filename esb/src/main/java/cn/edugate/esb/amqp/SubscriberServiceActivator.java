package cn.edugate.esb.amqp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.Message;

public class SubscriberServiceActivator {

	private static Log logger = LogFactory
			.getLog(SubscriberServiceActivator.class);

	public void logXml(Message<?> msg) throws Exception {
		if (msg.getPayload() instanceof byte[]){
			logger.info(" PAYLOAD ###" + new String((byte[])msg.getPayload()));
		}else{
			logger.info(" PAYLOAD ###" + msg.getPayload());
		}
	}
}
