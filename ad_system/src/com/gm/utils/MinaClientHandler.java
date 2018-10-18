package com.gm.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class MinaClientHandler extends IoHandlerAdapter {

	public MinaClientHandler() {
		// TODO Auto-generated constructor stub
	}

	// 当一个客端端连结进入时
	private static final Log log = LogFactory.getLog(MinaClientHandler.class);

	public void sessionOpened(IoSession session) throws Exception {
		log.info("server  : " + session.getRemoteAddress());
	}

	// 当客户端关闭时
	public void sessionClosed(IoSession session) {
		log.info("Disconnect from server!");
	}

	// 当服务器发送的消息到达时:
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String s = (String) message;
		// Write the received data back to remote peer
		// log.info("收到服务端发来的消息: " + s);
		session.setAttribute("result", s);
		// session.write("zzzzz");
		session.close(true);
	}

	// 发送消息给服务器
	public void messageSent(IoSession session, Object message) throws Exception {
		log.info("发送消息给服务端: " + message);
	}

	// 发送消息异常
	public void exceptionCaught(IoSession session, Throwable cause) {
		session.close(true);
	}

	// //sessiong空闲
	// public void sessionIdle( IoSession session, IdleStatus status )
	// {
	// }
	// 创建 session
	public void sessionCreated(IoSession session) {

	}
}
