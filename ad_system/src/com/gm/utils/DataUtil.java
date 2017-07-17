package com.gm.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
/**
 * 各种数据格式转换类
 * @author Administrator
 *
 */
public class DataUtil {
	/**
	 * 返回百分比，两位小数
	 * @param y
	 * @param z
	 * @return
	 */
	public  static String myPercent(Long y,Long z){
		   String baifenbi="";//接受百分比的值
		   double baiy=y*1.0;
		   double baiz=z*1.0;
		   double fen=baiy/baiz;
		   String temp=fen+"";
		   if(!temp.equals("NaN") && !temp.equals("Infinity"))
		   {
		    	DecimalFormat df1 = new DecimalFormat("##00.00%");    //##.00%   百分比格式，后面不足2位的用0补齐
			    baifenbi= df1.format(fen);  
			    
			    if(baifenbi.length()==4)
			    {
			    	baifenbi="00"+baifenbi;
			    }
			    return baifenbi;
		   }
		   return "0.00%";
	}
	
	/**
	 * 两数相除返回，两位小数
	 * @param y
	 * @param z
	 * @return
	 */
	public  static String division2(int y,int z){
		   String result="";//结果值
		   double baiy=y*1.0;
		   double baiz=z*1.0;
		   double fen=baiy/baiz;
		   String temp=fen+"";
		   if(!temp.equals("NaN") && !temp.equals("Infinity"))
		   {
		    	DecimalFormat df1 = new DecimalFormat("##0.00");    //##.00%   百分比格式，后面不足2位的用0补齐
			    result= df1.format(fen); 
			    if(result.equals(".00"))
			    {
			    	result="0.00";
			    }
			    return result;
		   }
		   return "0.00";
	}
	
	public  static double division3(double y,double z){
		   double fen=y/z;
		   String temp=fen+"";
		   if(temp.equals("NaN") || temp.equals("Infinity"))
		   {
			    double result=0.00;
			    return result;
		   }
		   return fen;
	}
	
	
	/**
	 * 两数相除返回，四位小数
	 * @param y
	 * @param z
	 * @return
	 */
	public  static String division4(int y,int z){
		   String result="";//结果值
		   double baiy=y*1.0;
		   double baiz=z*1.0;
		   double fen=baiy/baiz;
		   String temp=fen+"";
		   if(!temp.equals("NaN"))
		   {
		    	DecimalFormat df1 = new DecimalFormat("##00.0000");    //##.00%   百分比格式，后面不足2位的用0补齐
			    result= df1.format(fen);  
			    return result;
		   }
		   return "0.0000";
	}
	/**
	 * 两数相除返回，两位小数和两位整数
	 * @param y
	 * @param z
	 * @return
	 */
	public  static String division2_2(int y,int z){
		   String result="";//结果值
		   double baiy=y*1.0;
		   double baiz=z*1.0;
		   double fen=baiy/baiz;
		   String temp=fen+"";
		   if(!temp.equals("NaN") && !temp.equals("Infinity"))
		   {
		    	DecimalFormat df1 = new DecimalFormat("##0.00");    //##.00%   百分比格式，后面不足2位的用0补齐
		    	fen=fen*100;
			    result= df1.format(fen);  
			    return result;
		   }
		   return "0.00";
	}
	/**
	 * 返回value,这个数值count位数的字符串。
	 * @param value
	 * @param count
	 * @return
	 */
	public static String toStringNum(double value,int count){
		NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(count);
        return nf.format(value);
	}
//	
//	/**
//	 * map对象转 json串,根据keylist顺序
//	 * @return
//	 */
//	public static String mapToJsonString(List<Map<String,Object>> listMap ,List<String> keylist){
//		StringBuffer rt = new StringBuffer();
//		rt.append("[");
//		for(int i=0;i<listMap.size();i++){
//			rt.append("{");
//			Map map = listMap.get(i);
//			String valueStr = "";
//			for( int k=0;k<keylist.size();k++ ){
//				String keystr = keylist.get(k);
//				valueStr = (String)map.get(keystr);
//				//rt.append((char)34+keystr+(char)34+":"+(char)34+valueStr+(char)34);
//				rt.append("'"+keystr+"':'"+valueStr+"'");
//				if(k != keylist.size()-1){
//					rt.append(",");
//				}
//			}
//			if(i != listMap.size()-1){
//				rt.append("},");
//			}else{
//				rt.append("}");
//			}
//		}
//		rt.append("]");
////		String rt_str = rt.toString();
////		//rt_str.replaceAll(regex, replacement)
////		rt_str.replace("","");
//		return rt.toString();
//	}
	
	public static void main(String[] args) {
		String aa = DataUtil.division2_2(60, 60);
		System.out.println(aa);
	}
}
