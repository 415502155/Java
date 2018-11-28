package sng.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import sng.entity.ChargeDetail;

/**
 * 金额计算转换工具类
 * Title: MoneyUtil
 * Description: 
 * Company: 世纪伟业
 * @author Liuyang
 * @date 2018年1月18日上午10:34:59
 */
public class MoneyUtil {

	/**
	 * 将单位是分的字段值转换为单位是元的
	 * @param list 对象列表
	 * @param fields 要转换的字段集合
	 * @return
	 */
	public static List<ChargeDetail> fromFENtoYUAN(List<ChargeDetail> list,String[] fields){
		for (ChargeDetail t : list) {
			for (String field : fields) {
				try {
					PropertyDescriptor prop = new PropertyDescriptor(field, ChargeDetail.class);
					String str = prop.getReadMethod().invoke(t).toString();
					if(StringUtils.isNotEmpty(str)){
						if(str=="0"){
							prop.getWriteMethod().invoke(t, "0"); 
						}else if(str.length()==1){
							prop.getWriteMethod().invoke(t, "0.0"+str); 
						}else if(str.length()==2){
							if(str.endsWith("0")){
								prop.getWriteMethod().invoke(t, "0."+str.substring(0,1)); 
							}else{
								prop.getWriteMethod().invoke(t, "0."+str); 
							}
						}else{
							String yuan = str.substring(0, str.length()-2);
							String fen = str.substring(str.length()-2);
							if(fen.equals("00")){
								prop.getWriteMethod().invoke(t, yuan); 
							}else if(fen.endsWith("0")){
								prop.getWriteMethod().invoke(t, yuan+"."+fen.substring(0,1)); 
							}else{
								prop.getWriteMethod().invoke(t, yuan+"."+fen); 
							}
						}
					}
				} catch (IntrospectionException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} 
			}
		}
		return list;
	}

	/**
	 * 将以“元”为单位的金额转换成以“分”为单位的金额
	 * @param money
	 * @return
	 * @throws Exception 
	 */
	public static String fromYUANtoFEN(String money) throws Exception {
		NumberFormat nf = new DecimalFormat("##.00");
		String m = "";
		BigDecimal mon = new BigDecimal(money);
		try {
			m = nf.format(mon);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(); 
		}
		m = m.replace(".", "");
		if(m.startsWith("0")){
			return m.substring(1);
		}else{
			return m;
		}
	}

	/**
	 * 将以“分”为单位的金额转换成以“元”为单位的金额
	 * @param money
	 * @return
	 * @throws Exception 
	 */
	public static String fromFENtoYUAN(String money) throws Exception {
		if("0".equals(money)){
			return "0";
		}else if(money.length()==1){
			return "0.0"+money; 
		}else if(money.length()==2){
			if(money.endsWith("0")){
				return "0."+money.substring(0,1);
			}else{
				return "0."+money;
			}
		}else{
			String yuan = money.substring(0, money.length()-2);
			String fen = money.substring(money.length()-2);
			if(fen.equals("00")){
				return yuan;
			}else if(fen.endsWith("0")){
				return yuan+"."+fen.substring(0,1);
			}else{
				return yuan+"."+fen;
			}
		}
	}

	/**
	 * 比较金额大小，返回“是否第一个比第二个大”
	 * @param refundMoney
	 * @param money
	 * @return
	 * @throws Exception 
	 */
	public static boolean isFirstLarger(String refundMoney, String money) {
		BigDecimal refund = new BigDecimal(refundMoney);
		BigDecimal mon = new BigDecimal(money);
		return refund.compareTo(mon)>0;
	}
}
