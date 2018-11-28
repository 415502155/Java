package sng.comparator;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import sng.entity.ChargeDetail;
import sng.util.Constant;


public class ChargeDetailTListSorter implements Comparator<ChargeDetail>{
	@Override  
	public int compare(ChargeDetail c1, ChargeDetail c2) {  
		// 先按支付状态排序:先未支付，后已支付
		if ((c1.getStatus()>Constant.CHARGE_DETAIL_STATUS_NEVER&&c2.getStatus()>Constant.CHARGE_DETAIL_STATUS_NEVER)
				||(Constant.CHARGE_DETAIL_STATUS_NEVER==c1.getStatus()&&Constant.CHARGE_DETAIL_STATUS_NEVER==c2.getStatus())) {
			// 按照学生姓名拼音排序
			Collator c = Collator.getInstance(Locale.CHINA);
			return c.compare(c1.getStud_name(), c2.getStud_name());
		} else {
			return c1.getStatus().compareTo(c2.getStatus());
		}
	} 
}
