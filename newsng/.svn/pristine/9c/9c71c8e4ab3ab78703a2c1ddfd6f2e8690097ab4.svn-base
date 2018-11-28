package sng.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllAddress {
	/**
	 * 获取统一局域网的所有IP地址
	 * @return 所有IP地址的List集合
	 */
	public static List<String> getIPs() {
		List<String> list = new ArrayList<String>();
		Runtime r = Runtime.getRuntime();
		Process p;
		try {
			p = r.exec("arp -a");
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String inline;
			while ((inline = br.readLine()) != null) {
				if(!"".equals(inline.trim())){
					if (inline.indexOf("---") > -1) {
						continue;
					}
					if(inline.indexOf("Internet") > -1){
						continue;
					}
					// 有效IP
					String[] str = inline.split(" {4}");
					list.add(str[0]);
//					System.out.println(inline);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 获取同一局域网内的IP和Mac
	 * @return 以IP地址为Key, Mac地址为Value的Map
	 */
	public static Map<String, String> getAllIPAndMac(){
		  Map<String,String> map = new HashMap<String,String>();
		  Runtime r = Runtime.getRuntime();
			Process p;
			try {
				p = r.exec("arp -a");
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String inline;
				while ((inline = br.readLine()) != null) {
					if(!"".equals(inline.trim())){
						if (inline.indexOf("---") > -1) {
							continue;
						}
						if(inline.indexOf("Internet") > -1){
							continue;
						}
						// 有效IP
						String[] arr = inline.split(" {4}");
						String ip = arr[0].trim();
						String mac = "00-00-00-00-00-00";
						for(int i = 1; i < arr.length; i ++){
							String str = arr[i].trim();
							if(stringIsMac(str)){
								mac = str;
								break;
							}
						}
						map.put(ip, mac);
					}
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		  return map;
	}
	
	 /**
	  * 根据正则表达式判断是否为Mac地址
	 * @param val
	 * @return true:是Mac地址，false：不是Mac地址
	 */
	private static boolean stringIsMac(String val) {  
        String trueMacAddress = "^([0-9a-fA-F]{2})(([/\\s:-][0-9a-fA-F]{2}){5})$";  
        // 这是真正的MAC地址；正则表达式；  
        return val.matches(trueMacAddress);
	}  
	
	/**
	 * 根据IP提取主机名
	 * @param ips
	 * @return 以IP地址为Key,主机名为Value的Map
	 */
	public static Map<String, String> getHostnames(List<String> ips){
        Map<String,String> map = new HashMap<String,String>();
        System.out.println("正在提取hostname...");
        for(String ip : ips){
            String command = "ping -a " + ip;
            Runtime r = Runtime.getRuntime();
            Process p;
            try {
                p = r.exec(command);
                BufferedReader br = new BufferedReader(new InputStreamReader(p .getInputStream()));
                String inline;
                while ((inline = br.readLine()) != null) {
                    if(inline.indexOf("[") > -1){
                        int start = inline.indexOf("Ping ");
                        int end = inline.indexOf("[");
                        String hostname = inline.substring(start+"Ping ".length(),end-1);
                        System.out.println(hostname);
                        map.put(ip,hostname);
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("提取结束！");
        return map;
    }
	
	public static void main(String[] args) {
		System.out.println(getIPs());
		System.out.println(getAllIPAndMac());
	}

}
