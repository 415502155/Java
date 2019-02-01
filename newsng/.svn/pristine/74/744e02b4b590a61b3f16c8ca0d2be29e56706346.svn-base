package sng.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @类 名： MQService
 * @功能描述： 调用MQ的封装接口
 * @作者信息： LiuYang
 * @创建时间： 2018年12月21日上午11:14:25
 */
@Service
@Transactional
public interface MQService {

	/**
	 * 调用MQ消息队列
	 * @param mqName 消息队列名称，自动添加配置文件config.properties里的前后缀，前缀${item}_,后缀_${switch}${custom}
	 */
	public abstract void sendMessage(String mqName,String routingKey, Object object);
	
	/**
	 * 调用MQ消息队列
	 * @param delay 延迟毫秒数
	 * @param mqName 消息队列名称，自动添加配置文件config.properties里的前后缀，前缀${item}_,后缀_${switch}${custom}
	 */
	public abstract void sendMessage(String mqName,String routingKey, Object object, int delay);
}
