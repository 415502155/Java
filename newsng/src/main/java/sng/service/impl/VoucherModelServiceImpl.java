package sng.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sng.dao.BaseDaoEx;
import sng.dao.VoucherModelDao;
import sng.entity.Vouchermodel;
import sng.pojo.SessionInfo;
import sng.service.VoucherModelService;
import sng.util.CommonUtils;
import sng.util.Constant;
/***
 * 
 *  @Company sjwy
 *  @Title: VoucherModelServiceImpl.java 
 *  @Description: 凭证模板实现类
 *	@author: shy
 *  @date: 2018年10月29日 下午1:25:49
 */
@Service
@Transactional
public class VoucherModelServiceImpl implements VoucherModelService{

	@Autowired 
	private VoucherModelDao voucherModelDao;
	@Autowired
	private BaseDaoEx baseDaoEx;
	/***
	 * 添加凭证模板
	 */
	@Override
	public void save(Integer orgId, String picName, String voucherContent, Integer length, Integer width) {

		Vouchermodel v = new Vouchermodel();
		v.setOrg_id(orgId);
		v.setVoucher_name("");
		v.setVoucher_content(voucherContent);
		v.setSerial_number_format("");
		v.setBackground_length(length);
		v.setBackground_width(width);
		v.setImg_url(picName);
		v.setIs_del(Constant.IS_DEL_NO);
		v.setInsert_time(new Date());
		
		voucherModelDao.save(v);
	}
	/***
	 * 当前机构下的模板数量 
	 */
	@Override
	public Integer VouModelNum(SessionInfo sessionInfo, Integer orgId) {
		/*String sql = " SELECT COUNT(1) FROM vouchermodel v WHERE v.is_del = 0 AND v.org_id = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(orgId);
		int vouNum = baseDaoEx.countBySql(sql, params);*/
		return voucherModelDao.VouModelNum(orgId);
	}
	
	
	@Override
	public void update(Integer orgId, String picName, String voucherContent, Integer voucherModelId, Integer length, Integer width) {
		String querySql = " SELECT * FROM vouchermodel WHERE is_del = 0 AND org_id = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(orgId);
		List<Vouchermodel> vList = baseDaoEx.queryListBySql(querySql, Vouchermodel.class, params);
		if (null != vList && vList.size() > 0) {
			Vouchermodel v = vList.get(0);
			//if (StringUtils.isNotBlank(picName)) {
				v.setImg_url(picName);
			//}
			//if (StringUtils.isNotBlank(voucherContent)) {
				v.setVoucher_content(voucherContent);
			//}
			//if (null != length) {
				v.setBackground_length(length);
			//}
			//if (null != width) {
				v.setBackground_width(width);
			//}
			v.setUpdate_time(new Date());
			voucherModelDao.update(v);
		}
	}
	
	@Override
	public List<Vouchermodel> getVouModel(SessionInfo sessionInfo, Integer orgId) {
		if (orgId == null) {
			orgId = sessionInfo.getOrgId();
		}
		/*String sql = " SELECT v.voucher_name, v.voucher_content, v.serial_number_format, v.background_length, v.background_width, v.img_url  FROM vouchermodel v WHERE v.is_del = 0 AND v.org_id = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(orgId);
		List<Map<String, Object>> listVou = baseDaoEx.queryListBySql(sql, params);*/
		return voucherModelDao.getVouModel(orgId);
	}
	/***
	 * 移动端： （缴费成功）我的凭证
	 */
	@Override
	public List<Map<String, Object>> getVoucherList(String phone, Integer isMain, Integer orgId) {
		String sql = " SELECT u.user_mobile, uu.user_idnumber, p.parent_id, p.parent_name, s.stud_name, s.stud_id, sp.is_main, \r\n" + 
				"c.clas_name, c.clas_id, c.class_start_date, c.class_week, c.class_unset_time, c.class_begin_time, \r\n" + 
				"c.class_over_time, DATE_FORMAT(c.class_over_time,'%H:%i') class_over_time1, DATE_FORMAT(c.class_begin_time,'%H:%i') class_begin_time1, tuition_fees, c.total_hours, \r\n" + 
				"te.term_name, te.cur_year, \r\n" + 
				"GROUP_CONCAT(t.tech_name) tech_names, \r\n" + 
				"c.category_id, c.subject_id, c.cam_id, \r\n" + 
				"cate.category_name, \r\n" + 
				"sub.subject_name, \r\n" + 
				"cam.cam_name, \r\n" + 
				"sc.voucher_no, cr.building, cr.floor, cr.classroom_name, concat(cr.building,cr.floor,cr.classroom_name) bfc \r\n" + 
				"FROM  edugate_base.org_user u \r\n" + 
				"INNER JOIN  edugate_base.parent p ON p.user_id = u.user_id  AND p.is_del = 0  ##家长表 \r\n" + 
				"INNER JOIN edugate_base.student2parent sp ON sp.parent_id = p.parent_id  AND sp.is_main = ?  AND sp.is_del = 0  ##学生家长关系表 \r\n" + 
				"INNER JOIN edugate_base.student s ON s.stud_id = sp.stud_id  AND s.is_del = 0 ## 学生表 \r\n" + 
				"INNER JOIN edugate_base.org_user uu ON s.user_id = uu.user_id AND uu.is_del = 0 ## 学生表 \r\n" + 
				"INNER JOIN newsng.student_class sc ON s.stud_id = sc.stud_id  AND sc.is_del = 0 AND sc.stu_status = 5 \r\n" + 
				"INNER JOIN newsng.classes c ON c.clas_id = sc.clas_id AND c.is_del = 0 \r\n" + 
				"INNER JOIN newsng.term te ON te.term_id = c.term_id AND te.is_del = 0 \r\n" + 
				"LEFT JOIN newsng.teacher_class tc ON  tc.clas_id = c.clas_id AND tc.is_del = 0 \r\n" + 
				"LEFT JOIN edugate_base.teacher t ON t.tech_id = tc.tech_id AND t.is_del = 0 \r\n" + 
				"INNER JOIN newsng.category cate ON cate.category_id = c.category_id AND cate.is_del = 0 \r\n" + 
				"INNER JOIN newsng.subject sub ON sub.subject_id = c.subject_id AND sub.is_del = 0 \r\n" + 
				"INNER JOIN newsng.campus cam ON cam.cam_id = c.cam_id AND cam.is_del = 0 \r\n" + 
				"INNER JOIN newsng.classroom cr ON cr.classroom_id = c.classroom_id AND cr.is_del = 0 \r\n" + 
				"WHERE u.is_del = 0 \r\n" + 
				"AND u.user_mobile = ? \r\n" + 
				"AND u.org_id = ?\r\n" + 
				"GROUP BY c.clas_id \r\n" + 
				"ORDER BY c.term_id DESC ";
		List<Object> params = new ArrayList<Object>();
		params.add(isMain);
		params.add(phone);
		params.add(orgId);
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = baseDaoEx.queryListBySql(sql, params);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> clas = list.get(i);
				//将 开始生日、结束生日和开课日期  Timestamp格式转换 为yyyy-MM-dd 
				Timestamp startDate =(Timestamp) clas.get("class_start_date");
				clas.put("class_start_date", CommonUtils.dateFormat(startDate, "yyyy-MM-dd"));	
			}
		}
		return list;
	}

}
