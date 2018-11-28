package sng.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

import sng.constant.Consts;
import sng.pojo.ClassTeacher;

public class ValidateObject {
	
	public static boolean validate(ClassTeacher c, StringBuffer errorBuffer, boolean legal) {

		String clasTypeName = c.getClas_type_name();

		if (clasTypeName == null) {
			legal = false;
			errorBuffer.append("班级类型不能为空;");
		} else {
			clasTypeName = clasTypeName.trim();
			if (clasTypeName.equals(Consts.CLASS_TYPE_MAP.get("1").trim()) || clasTypeName.equals(Consts.CLASS_TYPE_MAP.get("2").trim())) {

			} else {
				legal = false;
				errorBuffer.append("班级类型必须填写“新生班”或“老生班”;");
			}
		}
		//班级容量必须是正整数
		String size = c.getSize();
		if (null != size) {
			size = size.trim();
			boolean sizeFlag = isNumber(size);
			if (false == sizeFlag) {
				legal = false;
				errorBuffer.append("班级容量必须是正整数;");
			}
		} else {
			legal = false;
			errorBuffer.append("班级容量不能为空;");
		}

		String totalHours = c.getTotal_hours();
		if (null != totalHours) {
			totalHours = totalHours.trim();
			boolean totalHourFlag = isNumber(totalHours);
			if (totalHourFlag == false) {
				legal = false;
				errorBuffer.append("总课时数必须是正整数;");
			}
		} else {
			legal = false;
			errorBuffer.append("总课时数不能为空;");
		}

		//起始生日、结束生日格式为2014-3-12   开课日期格式为2014-3-12
		String startBirthday = "";
		if (null != c.getStart_birthday()) {
			startBirthday = c.getStart_birthday().trim();
			boolean startFlag = isValidDate(startBirthday);
			if (startFlag == false) {
				legal = false;
				errorBuffer.append("起始生日日期格式必须为“2020-01-01”;");
			}
		} else {
			legal = false;
			errorBuffer.append("起始生日不能为空;");
		}
		String endBirthday = "";
		if (null != c.getEnd_birthday()) {
			endBirthday = c.getEnd_birthday().trim();
			boolean endFlag = isValidDate(endBirthday);
			if (endFlag == false) {
				legal = false;
				errorBuffer.append("结束生日日期格式必须为“2020-01-01”;");
			}
		} else {
			legal = false;
			errorBuffer.append("结束生日不能为空;");
		}
		String classStartDate = "";
		if (null != c.getClass_start_date()) {
			classStartDate = c.getClass_start_date().trim();
			boolean classStartDateFlag = isValidDate(classStartDate);
			if (classStartDateFlag == false) {
				legal = false;
				errorBuffer.append("开课时间日期格式必须为“2020-01-01”;");
			}
		} else {
			legal = false;
			errorBuffer.append("开课时间不能为空;");
		}
		/***
		 * 固定时间 和 不固定时间 验证
		 * 
		 * 两个时间都为空 ，提示只能有一个为空
		 * 
		 * 两个时间都不为空， 提示只能有一个为空
		 */
		
		if (null != c.getClass_unset_time() && c.getClass_week_name() == null 
				&& null == c.getClass_begin_time()
				&& null == c.getClass_over_time())		
		{//填写不固定时间  不校验
			
		} else if (null == c.getClass_unset_time() && c.getClass_week_name() != null 
				&& null != c.getClass_begin_time()
				&& null != c.getClass_over_time())		
		{//填写固定时间  进行校验
			String classWeekName = c.getClass_week_name().trim();
			if (classWeekName.equals(Consts.WEEK_MAP.get("1")) || 
					classWeekName.equals(Consts.WEEK_MAP.get("2")) ||
					classWeekName.equals(Consts.WEEK_MAP.get("3")) ||
					classWeekName.equals(Consts.WEEK_MAP.get("4")) ||
					classWeekName.equals(Consts.WEEK_MAP.get("5")) ||
					classWeekName.equals(Consts.WEEK_MAP.get("6")) ||
					classWeekName.equals(Consts.WEEK_MAP.get("7"))
					) {
				
			} else {
				legal = false;
				errorBuffer.append("上课星期格式必须是“周一”到“周日”;");
			}

			String classBeginTime = c.getClass_begin_time().trim();
			StringBuffer begin = new StringBuffer("2000-01-01 ");
			begin.append(classBeginTime).append(":00");
			boolean classBeginTimeFlag = isValidDate(begin.toString());
			if (classBeginTimeFlag == false) {
				legal = false;
				errorBuffer.append("上课时间格式必须为“20:00”;");
			}
			String classOverTime = c.getClass_over_time().trim();
			StringBuffer over = new StringBuffer("2000-01-01 ");
			over.append(classOverTime).append(":00");
			boolean classOverTimeFlag = isValidDate(over.toString());
			if (classOverTimeFlag == false) {
				legal = false;
				errorBuffer.append("下课时间格式必须为“20:00”;");
			}
		}else {
			legal = false;
			errorBuffer.append("（上课星期、上课时间、下课时间）或不固定时间，填写其中一项即可;");
		}
		StringBuffer validatefees = new StringBuffer();//学费校验信息
		//学费标准必须是正数
		if (null != c.getTuition_fees()) {
			boolean numFlag = true;
			Map feesMap = validateFees(c.getTuition_fees(), validatefees, numFlag);//校验学费格式是否正确
			Integer fessFlag = (Integer) feesMap.get("success");
			String feesInfo = (String) feesMap.get("msg");//校验学费信息
			//isNumber(c.getTuition_fees().trim());
			if (fessFlag == -1) {
				legal = false;
				errorBuffer.append(feesInfo);
			}
		} else {
			legal = false;
			errorBuffer.append("学费标准不能为空;");
		}
		return legal;
	}
	
	public static boolean isValidNumber(String str) {
		boolean convertSuccess = true;
		try {
			Integer number = Integer.parseInt(str);
		} catch (Exception e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}
	
	//判断字符串是否可以转换为整型或浮点
	public static boolean isNumber(String str){
		String reg = "^[0-9]+(.[0-9]+)?$";
		return str.matches(reg);
	}
	
	public static boolean isValidDate(String str) {
		boolean convertSuccess = true;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			convertSuccess = false;
		} catch (NullPointerException e) {
			convertSuccess = false;
		}

		return convertSuccess;
	}

	public static void main(String[] args) {
		boolean flag = isNumber("8.89");
		
		String str1 = "10:10";
		StringBuffer sb1 = new StringBuffer("2000-01-01 ");
		sb1.append(str1).append(":00");
		String timeStr = sb1.toString();
		System.out.println(timeStr);
		System.out.println(timeStr.substring(11, 16));
		//System.out.println(flag);
	}
	
	public static Integer getClassWeek1(Integer classWeek, String classWeekName) {
		switch (classWeekName) {
		case "周一":
			classWeek = 1;
			break;
		case "周二":
			classWeek = 2;
			break;
		case "周三":
			classWeek = 3;
			break;
		case "周四":
			classWeek = 4;
			break;
		case "周五":
			classWeek = 5;
			break;
		case "周六":
			classWeek = 6;
			break;
		case "周日":
			classWeek = 7;
			break;
		default:
			classWeek = 8;
			break;
		}
		return classWeek;
	}
	
	public static Integer getClassWeek(Integer classWeek, String classWeekName) {
		
		if (classWeekName.equals("周一")) {
			classWeek = 1;
		} else if (classWeekName.equals("周二")) {
			classWeek = 2;
		} else if (classWeekName.equals("周三")) {
			classWeek = 3;
		} else if (classWeekName.equals("周四")) {
			classWeek = 4;
		} else if (classWeekName.equals("周五")) {
			classWeek = 5;
		} else if (classWeekName.equals("周六")) {
			classWeek = 6;
		} else if (classWeekName.equals("周日")) {
			classWeek = 7;
		} else {
			classWeek = 8;
		}
		return classWeek;
	}
	
    //根据value值获取到对应的一个key值
    public static String getKey(Map<String,Integer> map, Integer value){
        String key = null;
        //Map,HashMap并没有实现Iteratable接口.不能用于增强for循环.
        for(String getKey: map.keySet()){
            if(map.get(getKey).equals(value)){
                key = getKey;
            }
        }
        return key;
        //这个key肯定是最后一个满足该条件的key.
    }

    //根据value值获取到对应的所有的key值
    public static List<String> getKeyList(Map<String,String> map, String value){
        List<String> keyList = new ArrayList();
        for(String getKey: map.keySet()){
            if(map.get(getKey).equals(value)){
                keyList.add(getKey);
            }
        }
        return keyList;
    }
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map validateFees(String tuitionFees, StringBuffer errFees, boolean flag) {
		Map map = new HashMap<>();
		try {
			if (!NumberUtils.isParsable(tuitionFees)) {
				errFees.append("输入的缴费金额不是数字;");
				flag = false;
			}else if(tuitionFees.contains(".") && tuitionFees.split("\\.")[1].length()>2){
				errFees.append("输入的缴费金额只能精确到分;");
				flag = false;
			}else if(new BigDecimal(MoneyUtil.fromYUANtoFEN(tuitionFees)).compareTo(new BigDecimal(Constant.MAX_MONEY))>0){
				errFees.append("输入的缴费金额过大;");
				flag = false;
			}else if(new BigDecimal(MoneyUtil.fromYUANtoFEN(tuitionFees)).compareTo(new BigDecimal(Constant.MIN_MONEY))<0){
				errFees.append("输入的缴费金额错误;");
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			errFees.append("输入的缴费金额不正确;");
			flag = false;
		}
		if (flag == false) {
			map.put("success", -1);
			map.put("msg", errFees.toString());
		} else {
			map.put("success", 0);
			map.put("msg", errFees.toString());
		}
		return map;
	}
}
