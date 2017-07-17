package com.gm.utils.project;

public class ProjectComment {
//	  if (stripos($user_agent, "iPhone")!==false) {
//	        $brand = 'iPhone';
//	    } else if (stripos($user_agent, "SAMSUNG")!==false || stripos($user_agent, "Galaxy")!==false || 
//	    		strpos($user_agent, "GT-")!==false || strpos($user_agent, "SCH-")!==false || strpos($user_agent, "SM-")!==false) {
//	        $brand = '三星';
//	    } else if (stripos($user_agent, "Huawei")!==false || stripos($user_agent, "Honor")!==false || 
//	    		stripos($user_agent, "H60-")!==false || stripos($user_agent, "H30-")!==false) {
//	        $brand = '华为';
//	    } else if (stripos($user_agent, "Lenovo")!==false) {
//	        $brand = '联想';
//	    } else if (strpos($user_agent, "MI-ONE")!==false || strpos($user_agent, "MI 1S")!==false || 
//	    		strpos($user_agent, "MI 2")!==false || strpos($user_agent, "MI 3")!==false || 
//	    		strpos($user_agent, "MI 4")!==false || strpos($user_agent, "MI-4")!==false) {
//	        $brand = '小米';
//	    } else if (strpos($user_agent, "HM NOTE")!==false || strpos($user_agent, "HM201")!==false) {
//	        $brand = '红米';
//	    } else if (stripos($user_agent, "Coolpad")!==false || strpos($user_agent, "8190Q")!==false || strpos($user_agent, "5910")!==false) {
//	        $brand = '酷派';
//	    } else if (stripos($user_agent, "ZTE")!==false || stripos($user_agent, "X9180")!==false || 
//	    		stripos($user_agent, "N9180")!==false || stripos($user_agent, "U9180")!==false) {
//	        $brand = '中兴';
//	    } else if (stripos($user_agent, "OPPO")!==false || strpos($user_agent, "X9007")!==false || 
//	    		strpos($user_agent, "X907")!==false || strpos($user_agent, "X909")!==false || 
//	    		strpos($user_agent, "R831S")!==false || strpos($user_agent, "R827T")!==false || 
//	    		strpos($user_agent, "R821T")!==false || strpos($user_agent, "R811")!==false || strpos($user_agent, "R2017")!==false) {
//	        $brand = 'OPPO';
//	    } else if (strpos($user_agent, "HTC")!==false || stripos($user_agent, "Desire")!==false) {
//	        $brand = 'HTC';
//	    } else if (stripos($user_agent, "vivo")!==false) {
//	        $brand = 'vivo';
//	    } else if (stripos($user_agent, "K-Touch")!==false) {
//	        $brand = '天语';
//	    } else if (stripos($user_agent, "Nubia")!==false || stripos($user_agent, "NX50")!==false || stripos($user_agent, "NX40")!==false) {
//	        $brand = '努比亚';
//	    } else if (strpos($user_agent, "M045")!==false || strpos($user_agent, "M032")!==false || strpos($user_agent, "M355")!==false) {
//	        $brand = '魅族';
//	    } else if (stripos($user_agent, "DOOV")!==false) {
//	        $brand = '朵唯';
//	    } else if (stripos($user_agent, "GFIVE")!==false) {
//	        $brand = '基伍';
//	    } else if (stripos($user_agent, "Gionee")!==false || strpos($user_agent, "GN")!==false) {
//	        $brand = '金立';
//	    } else if (stripos($user_agent, "HS-U")!==false || stripos($user_agent, "HS-E")!==false) {
//	        $brand = '海信';
//	    } else if (stripos($user_agent, "Nokia")!==false) {
//	        $brand = '诺基亚';
//	    } else {
//	        $brand = '其他手机';
//	    }
	/**
	 * 根据手机型号获取手机品牌
	 * @param mobile_agent
	 * @return
	 */
	public static String getFirm(String mobile_agent){
		String firm="未知";
		if(mobile_agent.contains("SAMSUNG") || mobile_agent.contains("Galaxy") || mobile_agent.contains("GT-") || 
				mobile_agent.contains("SCH-") || mobile_agent.contains("SM-")  ){
			firm="三星";
		}else if(mobile_agent.contains("Huawei") || mobile_agent.contains("Honor") || mobile_agent.contains("H60-") || mobile_agent.contains("H30-")){
			firm="华为";
		}else if(mobile_agent.contains("Lenovo") ){
			firm="联想";
		}else if(mobile_agent.contains("MI-ONE") || mobile_agent.contains("MI 1S") || mobile_agent.contains("MI 2") || 
				mobile_agent.contains("MI 3") || mobile_agent.contains("MI 4") || mobile_agent.contains("MI-4")){
			firm="小米";
		}else if(mobile_agent.contains("HM NOTE") || mobile_agent.contains("HM201")){
			firm="红米";
		}else if(mobile_agent.contains("Coolpad") || mobile_agent.contains("8190Q") || mobile_agent.contains("5910")){
			firm="酷派";
		}else if(mobile_agent.contains("ZTE") || mobile_agent.contains("X9180") || mobile_agent.contains("X9180") || mobile_agent.contains("U9180")){
			firm="中兴";
		}else if(mobile_agent.contains("OPPO") || mobile_agent.contains("X907") || mobile_agent.contains("X9007") || mobile_agent.contains("X909") || 
				mobile_agent.contains("R831S") || mobile_agent.contains("R827T") || mobile_agent.contains("R821T") || 
				mobile_agent.contains("R811") || mobile_agent.contains("R2017")){
			firm="OPPO";
		}else if(mobile_agent.contains("HTC") || mobile_agent.contains("Desire")){
			firm="HTC";
		}else if(mobile_agent.contains("vivo")){
			firm="vivo";
		}else if(mobile_agent.contains("K-Touch")){
			firm="天语";
		}else if(mobile_agent.contains("Nubia") || mobile_agent.contains("NX50") || mobile_agent.contains("NX40")){
			firm="努比亚";
		}else if(mobile_agent.contains("M045") || mobile_agent.contains("M032") || mobile_agent.contains("M355")){
			firm="魅族";
		}else if(mobile_agent.contains("DOOV")){
			firm="基伍";
		}else if(mobile_agent.contains("Gionee") || mobile_agent.contains("GN")){
			firm="金立";
		}else if(mobile_agent.contains("HS-U") || mobile_agent.contains("HS-E")){
			firm="海信";
		}else if(mobile_agent.contains("Nokia")){
			firm="诺基亚";
		}
		return firm;
	}

}
