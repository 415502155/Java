package cn.edugate.esb.redis;

import java.util.Calendar;
import java.util.Date;



/**
 * 
 * 
 * @Name: 跨时间段变更过期策略
 * @Description: 跨时间段变更过期策略
 */
public class TimeChangeStrategy<T> implements ExpiredStrategy<T>,java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TimeField chageField=TimeField.NONE;
	
	
	public TimeField getChageField() {
		return chageField;
	}



	public void setChageField(TimeField chageField) {
		this.chageField = chageField;
	}
	

	public TimeChangeStrategy()
	{
		
	}

	
	public TimeChangeStrategy(TimeField chageField) {
		this.chageField = chageField;
	}


	/**
	 * 数据是否过期
	 * @param info 缓存信息
	 * @return 数据是否过期
	 */ 
	public boolean isExpired(CacheInfo<T> info) {
		boolean expired=true;
		if(info.getUpdateTime()==null)
		{
			expired= true;
		}else if(this.chageField==TimeField.NONE)
		{
			expired= false;
		}else
		{
			Calendar now= Calendar.getInstance();
			now.setTime(new Date());
			Calendar update= Calendar.getInstance();
			update.setTime(info.getUpdateTime());
			
			if (this.chageField==TimeField.HOUR) {
				expired=now.get(Calendar.YEAR)>update.get(Calendar.YEAR)
						||now.get(Calendar.MONTH)>update.get(Calendar.MONTH)
						||now.get(Calendar.DAY_OF_MONTH)>update.get(Calendar.DAY_OF_MONTH)
						||now.get(Calendar.HOUR_OF_DAY)>update.get(Calendar.HOUR_OF_DAY);
						
			}
			if (this.chageField==TimeField.DATE) {
				expired=now.get(Calendar.YEAR)>update.get(Calendar.YEAR)
						||now.get(Calendar.MONTH)>update.get(Calendar.MONTH)
						||now.get(Calendar.DAY_OF_MONTH)>update.get(Calendar.DAY_OF_MONTH);
						
			}
			if (this.chageField==TimeField.MONTH) {
				expired=now.get(Calendar.YEAR)>update.get(Calendar.YEAR)
						||now.get(Calendar.MONTH)>update.get(Calendar.MONTH);
						
			}
			if (this.chageField==TimeField.YEAR) {
				expired=now.get(Calendar.YEAR)>update.get(Calendar.YEAR);
						
			}
			
		}
		return expired;
	}



	@Override
	public void updateCache(CacheInfo<T> info) {
		// TODO Auto-generated method stub
		
	}

	

}
