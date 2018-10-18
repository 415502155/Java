package test;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gm.utils.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:conf/applicationContext.xml" })
public class ClientActionLogTest {
	
	public Logger log = Logger.getLogger(getClass()); // 日志 
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testDept() {
	}
	
	
	@Test
	public void testqueryPurchaseCollectInfo(){
		//five_analy_redis.analyDataTimely(new Date());//测试通过
		//five_analy.analyDataTimely(new Date());//测试通过

		//analy.analyAllData(new Date());//测试通过
		//analy.analyData(new Date());//测试通过
	//	recharge_analy.analyData();//测试通过
		//hour_analy.analyDataTimely(new Date());//测试通过
		//analy.analyAllData(new Date());//测试通过
		
		//login_analy.analyData(new Date());//测试通过
		//sys_level_analy.analyAllData(new Date());//测试通过
		//sys_level_analy.analyData(new Date());//测试通过
		//week_rep                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   ort_analy.analyAllData(new Date());//测试通过
		//find_treasure_analy.analyAllData(new Date());//测试通过
		//find_treasure_analy.analyData(new Date());//测试通过
		//month_report_analy.analyAllData(new Date());//测试通过
		
		//conbined_analy.analyData(new Date());  //测试通过
		//dept_sid_data_analy.analyData(new Date());//测试通过
		//recharge_login_ragion_analy.analyData(new Date());//测试通过
		//recharge_ragion_analy.analyData(new Date());//测试通过
	
		Date day = DateUtil.StringTodate("2015-09-01", DateUtil.DATE_FORMAT);
		//login_ragion_analy.analyAllData(curdate);//测试通过
		//login_ragion_analy.analyData(curdate);//测试通过
		//rechare_create_interval_analy.analyData(curdate);//测试通过
		//add_sub_money_analy.analyData(day);//测试通过
		//task_analy.analyAllData(day);//测试通过
		//task_analy.analyData(day);//测试通过
		//user_client_analy.analyAllData(day);//测试通过
		//logout_analy.analyAllData(day);//测试通过
		//logout_analy.analyData(day);//测试通过
		
		/*week_report_analy.analyAllData(day);//测试通过
		month_report_analy.analyAllData(day);//测试通过
*/		//analy.analyAllData(day);
		
		//fivemin_oneday_util_analy.analyDataTimely(day);
		
		//onehour_oneday_analy.analyDataTimely(day);
		//five_analy_redis.analyDataTimely(day);
		
		//user_info_analy.analyData(day);
	/*	report_analy.analyAllData(day);
		report_analy.analyData(day);*/
		//onehour_oneday_analy.analyDataTimely(day);//测试通过
		//zs_analy.analyData(day);
		/*channel_analy.analyAllData(day);
		channel_analy.analyData(day);*/
		//test_analy.analyAllData(day);
		/*zs_dynamic_analy.analyAllData(day);
		zs_dynamic_analy.analyData(day);*/

	}
	
}
