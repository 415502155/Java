package com.shihy.springboot.scheduled;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.shihy.springboot.entity.User;
import com.shihy.springboot.service.UserService;

import lombok.extern.slf4j.Slf4j;
/***
 * 一个cron表达式有至少6个（也可能7个）有空格分隔的时间元素。 
 *	按顺序依次为 
 *	秒（0~59） 
 *	分钟（0~59） 
 *	小时（0~23） 
 *	天（月）（0~31，但是你需要考虑你月的天数） 
 *	月（0~11） 
 *	天（星期）（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT） 
 *	年份（1970－2099）
 *	其中每个元素可以是一个值(如6),一个连续区间(9-12),一个间隔时间(8-18/4)(/表示每隔4小时),一个列表(1,3,5),通配符。由于"月份中的日期"和"星期中的日期"这两个元素互斥的,必须要对其中一个设置?
 *	0 0 10,14,16 * * ? 每天上午10点，下午2点，4点 
 *	0 0/30 9-17 * * ? 朝九晚五工作时间内每半小时 
 *	0 0 12 ? * WED 表示每个星期三中午12点 
 *	"0 0 12 * * ?" 每天中午12点触发 
 *	"0 15 10 ? * *" 每天上午10:15触发 
 *	"0 15 10 * * ?" 每天上午10:15触发 
 *	"0 15 10 * * ? *" 每天上午10:15触发 
 *	"0 15 10 * * ? 2005" 2005年的每天上午10:15触发 
 *	"0 * 14 * * ?" 在每天下午2点到下午2:59期间的每1分钟触发 
 *	"0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发 
 *	"0 0/5 14,18 * * ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发 
 *	"0 0-5 14 * * ?" 在每天下午2点到下午2:05期间的每1分钟触发 
 *	"0 10,44 14 ? 3 WED" 每年三月的星期三的下午2:10和2:44触发 
 *	"0 15 10 ? * MON-FRI" 周一至周五的上午10:15触发 
 *	"0 15 10 15 * ?" 每月15日上午10:15触发 
 *	"0 15 10 L * ?" 每月最后一日的上午10:15触发 
 *	"0 15 10 ? * 6L" 每月的最后一个星期五上午10:15触发 
 *	"0 15 10 ? * 6L 2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发 
 *	"0 15 10 ? * 6#3" 每月的第三个星期五上午10:15触发
 * 
 * 
 * @author sjwy-0001
 *
 */
@PropertySource({"classpath:config.properties"})
@Component
@Slf4j
public class UserTask {
	@Resource
	private UserService userService;
	
    @Async
    @Scheduled(cron = "${userjobs.cron}")
    public void userScheduled() throws InterruptedException {
    	log.info("userScheduled starting ：{},  Start Time :", LocalDateTime.now());
    	List<User> userList = userService.getUserList();
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.put("key", userList);
    	log.info("userScheduled starting ：{},  End Time :", LocalDateTime.now());
        log.info("userScheduled Result：{} , Data :", jsonObject);
    }
/*
    @Scheduled(fixedRate = 1000)
    public void scheduled2() throws InterruptedException {
        Thread.sleep(3000);
        log.info("scheduled2 每1秒执行一次：{}", LocalDateTime.now());
    }

    @Scheduled(fixedDelay = 3000)
    public void scheduled3() throws InterruptedException {
        Thread.sleep(5000);
        log.info("scheduled3 上次执行完毕后隔3秒继续执行：{}", LocalDateTime.now());
    }*/
}
