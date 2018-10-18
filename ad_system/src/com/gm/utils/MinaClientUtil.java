package com.gm.utils;

import java.net.InetSocketAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;


public class MinaClientUtil {

	public MinaClientUtil() {
		// TODO Auto-generated constructor stub
	}

	private static final Log log = LogFactory.getLog(MinaClientUtil.class);
	private static final String ADDRESS = "10.1.101.98";
	private static final int PORT = 40060;
	

	public static String request(String content) {
		// 实际应用中，这里可以生成一个线程来监听
		// Create TCP/IP connector.
		NioSocketConnector connector = new NioSocketConnector();

		// 创建接收数据的过滤器
		DefaultIoFilterChainBuilder chain = connector.getFilterChain();

		// 设定这个过滤器将一行一行(/r/n)的读取数据
		chain.addLast("codec", new ProtocolCodecFilter(
				new TextLineCodecFactory()));

		// 设定服务器端的消息处理器:一个SamplMinaServerHandler对象,
		connector.setHandler(new MinaClientHandler());

		// 连结到服务器:
		try {
			ConnectFuture cf = connector.connect(new InetSocketAddress(
					ADDRESS, PORT));
			cf.awaitUninterruptibly();
			IoSession session = cf.getSession();
			session.write(content);
			session.getCloseFuture().awaitUninterruptibly();
//			connector.broadcast("hello server");
			String result = (String) session.getAttribute("result");
//			log.info("收到服务器端消息：" + result);
			return result;
		} catch (Exception e) {
			connector.dispose();
			log.error("未能连接到服务器");
		}
		return null;
	}
}
