package com.gm.utils;

import java.util.Map;

public class HttpSendLog {

	public static void sendSysLog(final Map<String, Object> syslogMap){
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				long starttime=Long.parseLong(syslogMap.get("starttime")+"");
				String errno=syslogMap.get("errno")+"";
				String errmsg=syslogMap.get("errmsg")+"";
				String op=syslogMap.get("op")+"";
				String input=syslogMap.get("input")+"";
				String method = syslogMap.get("method")+"";
				String modulename = syslogMap.get("modulename")+"";
				String sysname = syslogMap.get("sysname")+"";
				long endtime=Long.parseLong(syslogMap.get("endtime")+"");
				String gid=DataSysConstant.LOG_GAME_ID ;

				String output="{\"errno\":"+errno+",\"errmsg\":\""+errmsg+"\"} ";		
				String poststr = "{\"method\":\""+method+"\","
						+ "\"op\":\""+op+"\","
						+ "\"sysname\":\""+sysname+"\","
						+ "\"modulename\":\""+modulename+"\","
						+ "\"input\":"+input+","
						+ "\"output\":"+output+","
						+ "\"runtime\":\""+(endtime-starttime)+"\","
						+ "\"time\":"+endtime+","
						+ "\"gid\":"+gid+""
						+ "}";
				String url= DataSysConstant.HTTP_POST_LOG;
				String ss;
				try {
					System.out.println(poststr);
					ss = HttpInvoker.readStreamPost(url, poststr);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}).start();
	}
	
}
