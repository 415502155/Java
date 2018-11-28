package cn.edugate.esb.disruptor;

import com.lmax.disruptor.EventFactory;

public class SmsTaskEvent {
	private SmsTask smsTask;
	
	 public SmsTask getSmsTask() {
		return smsTask;
	}

	public void setSmsTask(SmsTask smsTask) {
		this.smsTask = smsTask;
	}

	public final static  EventFactory<SmsTaskEvent> EVENT_FACTORY = new EventFactory<SmsTaskEvent>(){
        public SmsTaskEvent newInstance(){
            return new SmsTaskEvent();
        }
    };
}
