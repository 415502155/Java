package cn.edugate.esb.util;

import java.util.LinkedHashMap;

public class Constant {

	/**
	 * 删除失败
	 */
	public static final Integer DELETE_FAIL = -1;
	/**
	 * 是否删除（是）
	 */
	public static final Integer IS_DEL_YES = 1;
	/**
	 * 是否删除（否）
	 */
	public static final Integer IS_DEL_NO = 0;
	/**
	 * 机构类型(幼儿园)
	 */
	public static final Integer ORG_TYPE_KINDERGARTEN = 1;
	/**
	 * 机构类型(小学)
	 */
	public static final Integer ORG_TYPE_PRIMARY = 2;
	/**
	 * 机构类型(初中)
	 */
	public static final Integer ORG_TYPE_JUNIOR = 3;
	/**
	 * 机构类型(高中)
	 */
	public static final Integer ORG_TYPE_SENIOR = 4;
	/**
	 * 机构类型(其他)
	 */
	public static final Integer ORG_TYPE_OTHERS = 5;
	/**
	 * 适用范围类型(学校)
	 */
	public static final Integer FUR_TYPE_SCHOOLS = 0;
	/**
	 * 适用范围类型(培训机构)
	 */
	public static final Integer FUR_TYPE_TRAINING = 2;
	/**
	 * 适用范围类型(教育局)
	 */
	public static final Integer FUR_TYPE_BUREAU = 3;
	/**
	 * 组成员类型：学生
	 */
	public static final Integer GROUPMEMBER_TYPE_STUDENT = 1;
	/**
	 * 组成员类型：教师
	 */
	public static final Integer GROUPMEMBER_TYPE_TEACHER = 2;
	/**
	 * 组类型:机构内组
	 */
	public static final Integer GROUP_TYPE_INNER = 2;
	/**
	 * 组类型:机构外组
	 */
	public static final Integer GROUP_TYPE_OUTTER = 3;
	/**
	 * 组类型:学生组
	 */
	public static final Integer GROUP_TYPE_STUDENT = 4;
	/**
	 * 特殊菜单分组类型
	 */
	public static Integer MENU_CATEGORY_ZHENSHENGKEJI = 1;
	/**
	 * 菜单类型:使用中
	 */
	public static final Integer MENU_TYPE_USING = 0;
	/**
	 * 菜单类型:未使用
	 */
	public static final Integer MENU_TYPE_STAYING = 1;
	/**
	 * 菜单类型:试用中
	 */
	public static final Integer MENU_TYPE_TRYING = 2;
	/**
	 * 菜单状态:正常
	 */
	public static final Integer MENU_STATUS_NORMAL = 0;
	/**
	 * 菜单状态:维护
	 */
	public static final Integer MENU_STATUS_MAINTENANCE = 1;
	/**
	 * 菜单状态:过期
	 */
	public static final Integer MENU_STATUS_EXPIRE = 2;
	/**
	 * 菜单打开方式：默认
	 */
	public static final Integer MENU_OPEN_MODE_DEFAULT = 0;
	
	/**
	 * 机构最高权限
	 */
	public static final Integer ROLE_MANAGER = 2;
	/**
	 * 身份 教师
	 */
	public static final Integer IDENTITY_TEACHER = 1;
	/**
	 * 身份 学生
	 */
	public static final Integer IDENTITY_STUDENT = 2;
	/**
	 * 身份 家长
	 */
	public static final Integer IDENTITY_PARENT = 0;
	/**
	 * 云平台最高管理员
	 */
	public static final Integer IDENTITY_MANAGER = 99;
	/**
	 * WEB
	 */
	public static final Integer IDENTITY_WEB = 9;
	
	public static final String MANAGER_DEFAULT_LOGINNAME = "edugate";
	
	/**********键值对类型**************/
	/**
	 * 机构UI包
	 */
	public static Integer KEY_VALUE_UI = 1;
	/**
	 * 菜单分组category
	 */
	public static Integer KEY_MENU_CATEGORY = 2;
	
	
	
	/**
	 * 同机构类型下是否是当前使用学校
	 */
	public static final Integer CURRENTYES = 0;
	/**
	 * 同机构类型下是否是当前使用学校
	 */
	public static final Integer CURRENTNO = 1;
	
	/**
	 * 通用：是
	 */
	public static final Integer YES = 1;
	/**
	 * 通用：否
	 */
	public static final Integer NO = 0;
	
	
	/**
	 * 资源图片来源类型：云平台
	 */
	public static final Integer PICTURE_TYPE = 3;

	
	
	
	/**
	 * 
	 */
	public static final Integer TEACHER = 1;
	/**
	 * 
	 */
	public static final Integer MOBILE = 2;
	
	/**
	 * 全部权限
	 */
	public static final String ALL_AUTH = "all";
	
	
	public static String getRelation(Integer code)
    {
        switch (code)
        {
            case 0:
                return "父亲";
            case 1:
            	return "母亲";
            case 2:
            	return "爷爷";
            case 3:
            	return "奶奶";
            case 4:
            	return "外公";
            case 5:
            	return "外婆";
 
            default:
            	return "其他";
        }
    }
	

	public static LinkedHashMap<String,String> getExcelMap(String type)
    {
		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
		map.put("id", "序号");
        switch (type)
        {
            case "1":
            	map.put("org_name", "学校名称");
            	map.put("clas_name", "班级名称");
            	map.put("student_num", "学生总人数");
            	map.put("student_bind", "学生关注人数");
            	map.put("student_rate", "班级关注比例");
            	return map;
            case "2":
            	map.put("org_name", "学校名称");
            	map.put("clas_name", "班级名称");
            	map.put("teacher_name", "教师名称");
            	map.put("note", "内容");
            	map.put("time", "创建时间");
            	map.put("pinglun_num", "评论数量");
            	map.put("dianzan_num", "点赞数量");
            	return map;
            case "3":
            	map.put("org_name", "学校名称");
            	map.put("teacher_name", "创建教师");
            	map.put("note", "说明");
            	map.put("time", "创建时间");
            	map.put("num", "接收人总数");
            	return map;
            case "4":
            	map.put("org_name", "学校名称");
            	map.put("note", "内容");
            	map.put("time", "创建时间");
            	map.put("num", "发送人数");
            	map.put("type", "通知类型");
            	map.put("teacher_name", "教师姓名");
            	return map;
            case "5":
            	map.put("org_name", "学校名称");
            	map.put("student_rate", "学生关注比例");
            	map.put("student_num", "学生总人数");
            	map.put("student_bind", "学生关注人数");
            	map.put("teacher_rate", "教师关注比例");
            	map.put("teacher_num", "教师总人数");
            	map.put("teacher_bind", "教师关注人数");
            	map.put("parent_rate", "家长关注比例");
            	map.put("parent_num", "家长总人数");
            	map.put("parent_bind", "家长关注人数");
            	return map;
            case "6":
            	map.put("org_name", "学校名称");
            	map.put("teacher_name", "教师姓名");
            	map.put("note", "收费项目");
            	map.put("time", "创建时间");
            	map.put("start_time", "支付开始时间");
            	map.put("end_time", "支付结束时间");
            	map.put("money", "收费标准");
            	map.put("ys_num", "应收人数");
            	map.put("ss_num", "实收人数");
            	map.put("ys_money", "应收总额");
            	map.put("ss_money", "实收总额");
            	map.put("status", "当前状态");
            	return map;
            case "7":
            	map.put("org_name", "学校名称");
            	map.put("type", "是否开通支付");
            	map.put("note", "年级名称");
            	map.put("clas_name", "班级名称");
            	map.put("teacher_name", "学生姓名");
            	map.put("is_bind", "是否至少一位家长关注");
            	map.put("status", "关注详情");
            	return map;
            case "8":
            	map.put("org_name", "学校名称");
            	map.put("type", "是否开通支付");
            	map.put("dep_name", "所在部门");
            	map.put("teacher_name", "教师姓名");
            	map.put("mobile", "手机号码");
            	map.put("is_bind", "是否关注");
            	return map;
            case "9":
            	map.put("org_name", "学校名称");
            	map.put("teacher_num", "教师总人数");
            	map.put("teacher_bind", "角色教师关注数");
            	map.put("student_num", "角色教师总人数");
            	map.put("teacher_rate", "角色教师关注率");
            	return map;
 
            default:
            	return map;
        }
    }
	
	
	/**
	 * 幼儿园，小学，初中，高中的大致课程名称
	 */
	public static String[] CourseName4YouErYuan = { "音乐", "美术", "书法", "手工", "体育" };

	public static String[] CourseName4XiaoXue = { "语文", "数学", "英语", "体育" };

	public static String[] CourseName4ChuZhong = { "语文", "数学", "英语", "体育", "历史", "地理", "生物", "化学", "物理" };

	public static String[] CourseName4GaoZhong = { "语文", "数学", "英语", "体育", "历史", "地理", "生物", "化学", "物理", "政治" };
	
	/**
	 * 年级编号  应届
	 */
	public static final int GRADE_NUMBER_YINGJIE = 8;
	/**
	 * 年级编号  往届
	 */
	public static final int GRADE_NUMBER_WANGJIE = 9;
	
	
	/**
	 * 是否毕业 
	 */
	public static final Integer CLAS_IS_GRADUATED_YES = 1;
	/**
	 * 是否毕业 
	 */
	public static final Integer CLAS_IS_GRADUATED_NO = 0;
}
