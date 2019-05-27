package com.shy.springboot;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.shy.springboot.entity.User;
import com.shy.springboot.utils.FileUtil;

public class test {
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		/*int a = 07;
		String abc = "012346";
		Integer aaaInteger = Integer.parseInt(abc);
		System.out.println("123--------:"+aaaInteger);
		int b = 2;
		System.out.println(a);
		System.out.println(a-b);*/
		//testAdd();
		List<User> userList = new ArrayList<User>();
		for (int i = 0; i < 12; i++) {
			User user = new User();
			user.setUser_name("aaaaa_"+i);
			userList.add(user);
		}
		List<List<User>> averageAssign = averageAssign(userList, 3);
		
		String fileName = "C:\\sjwy\\20190527\\1136.txt";
	    FileUtil.makeNewFile(fileName);
	    FileUtil.fileAppend(fileName, "今天是2019年5月27日，星期一，天气晴；");
	    FileUtil.fileAppend(fileName, "今天是2019年5月27日，星期一，天气晴1；");
	    String fileName2 = "C:\\sjwy\\20190527\\1146.txt";
	    FileUtil.makeNewFile(fileName2, "aaaaaaa");
	    File file = new File(fileName);
	    String fileContent = FileUtil.getFileContent(file);
	    System.out.println(fileContent);

	    
	}
	/***
	 * @deprecated    将一组数据平均分成n组 
	 * 例如：list中有11条数据，分成3个(n)list，每一个list平均三条还剩余两条，会先把前两个list分别加一条（0*3 + 1, 1*3 + 1）、（1*3 + 1, 2*3 + 1）
		其中offset=2为记录不能平均分配的数量，最后一个list会按照（2*3+2,3*3+2）分配，其中的2即为offset
		如果整除没有余数，循环i到n，每次分配（i*(总数/n), (i+1)*(总数/n)）
	 * @param source  要分组的数据源
	 * @param n       平均分成n组
	 * @return
	 */
	public static <T> List<List<T>> averageAssign(List<T> source, int n) {
	    List<List<T>> result = new ArrayList<List<T>>();
	    int remainder = source.size() % n;  //余数
	    int number = source.size() / n;  //商
	    int offset = 0;//
	    for (int i = 0; i < n; i++) {
	        List<T> value = null;
	        //当余数不为0，循环把余数循环加入到每个组中
	        if (remainder > 0) {
	            value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
	            remainder--;
	            offset++;
	        } else {
	            value = source.subList(i * number + offset, (i + 1) * number + offset);
	        }
	        result.add(value);
	    }
	    return result;
	}
	
	public static void testAdd() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		/***
		 * String.format
		 * %s : 字符串类型
		 * %n ： 换行
		 * %d ： 整数类型
		 * %f ： 浮点类型
		 * %05d : 100 结果为：00100  5为位数
		 */

		Date date=new Date();
		String str = String.format("Hi,%s%s%s,您好，您收到一条短信：“%s”。%n"
				+ "请及时查看。【请关注查看】， 100的一半是：%d %n "
				+ "浮点数：%f 通过率为： %d%%， 商品的折扣是%d%% %n 数字前面补0 %09d 将某个金额以三位加逗号分割 ：%,d%n"
				+ " 年-月-日格式：%tF%n 月/日/年格式：%tD%n HH:MM:SS PM格式（12时制）：%tr%n HH:MM:SS格式（24时制）：%tT%n HH:MM格式（24时制）：%tR%n"
				+ "%16$d,%17$s"
				, "admin", "是个网站", "管理员", "售房网", 50, 12.88, 10, 85, 100, 999666333, date, date, date, date, date, 1234, "456"
				);
		String str1 = String.format("Hi,%1$s是个管理员，家庭住址在%2$s， 今年%3$d岁", "admin","天津市南开区咸阳路",30); 
		System.out.println(str);
		
		System.out.println(str1);
		/*ArrayList<Integer> array = new ArrayList<Integer>();
		Class c = array.getClass();
		Method[] declaredMethods = c.getDeclaredMethods();
		int length = declaredMethods.length;
		Method method = c.getMethod("add", Object.class);
		method.invoke(array, "ssss");
		for (int i = 0; i < length; i++) {
			System.out.println(declaredMethods[i]);
		}
		String aaa = "aaa";
		Integer le = aaa.length();
		System.out.println("aaa :" + aaa + " __________ le :" + le);
		Map map = new LinkedHashMap();
		map.put("key", "value");
		map.put("kkey", 1);
		map.put("kkkey", "admin");
		System.out.println(map);
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			System.out.println(map.get(key));
		}
		System.out.println(array.toString());*/
	}
	
	public static  void testList() {
		Object[] objects = {"aaaaa","SSS","CCC","111","123","1122",11,22,33,"admin"};
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < 10; i++) {
			list.add(objects[i]);
		}
		
	} 
}
