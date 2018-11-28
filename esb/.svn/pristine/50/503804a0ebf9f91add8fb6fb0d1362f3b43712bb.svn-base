package cn.edugate.esb.disruptor;

import org.apache.log4j.Logger;

import com.lmax.disruptor.WorkHandler;

public class SmsTaskEventHandler implements WorkHandler<SmsTaskEvent> {
	private static Logger logger = Logger.getLogger(SmsTaskEventHandler.class);

	@Override
	public void onEvent(SmsTaskEvent event) throws Exception {
		// TODO Auto-generated method stub
		SmsTask task = event.getSmsTask();
		if(task.getTargets()!=null&&task.getTargets().size()>0){
			
			logger.info("onEvent=======Targets========"+task.getTargets());
		}
		logger.info("onEvent==============="+task.getData());
	}

}
