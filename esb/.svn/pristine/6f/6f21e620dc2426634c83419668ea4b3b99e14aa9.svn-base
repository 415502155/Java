package cn.edugate.esb.dao.imp;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;

import cn.edugate.esb.dao.DBListener;
import cn.edugate.esb.util.SpringContextUtil;

public class DBListenerImpl implements DBListener{
	
	@Qualifier("fanoutChannel")
	MessageChannel messageChannel;
	@Autowired
	public void setMessageChannel(MessageChannel messageChannel) {
		this.messageChannel = messageChannel;
	}

	@Override
	public void changed(ChangeType changeType, Class<?> enType, Object en) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("class", enType.getName());
		map.put("type", changeType.getClass().getName());
		map.put("key", en.toString());
		JSONArray array = new JSONArray();
		array.put(map);
		Message<String> message = MessageBuilder.withPayload(array.toString()).build();
		((MessageChannel) SpringContextUtil.getBean("fanoutChannel")).send(message);
		messageChannel.send(message);
		System.err.println(message);
	}

}
