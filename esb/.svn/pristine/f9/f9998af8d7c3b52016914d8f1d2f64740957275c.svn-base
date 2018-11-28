package cn.edugate.esb.dao.imp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IDataDao;
import cn.edugate.esb.pojo.Data;

/**
 * 数据查询统计Dao实现类
 * Title: IDataImpl
 * Description: 
 * Company: 世纪伟业
 * @author Liuyang
 * @date 2018年5月13日下午2:23:45
 */
@Repository
public class IDataImpl extends BaseDAOImpl<Data> implements IDataDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Data> getList(Integer type, String area, String ids) {
		String sql = "";
		switch (type) {
			case 1:
				sql = "SELECT yy.org_name_cn AS org_name, yy.clas_name, yy.stu_num AS student_num, xx.studs AS student_bind, CONCAT( ROUND( IFNULL(xx.studs, 0) / IFNULL(yy.stu_num, 0) * 100, 2 ), '%' ) AS student_rate FROM ( SELECT gg.org_id, gg.org_name_cn, gg.clas_name, gg.clas_id, COUNT(gg.stud_id) studs FROM ( SELECT * FROM ( SELECT o.org_id, o.org_name_cn,  IF ( w.org_id IS NULL, '未开通', '已开通' ) AS config, GROUP_CONCAT(  IF ( u.openid IS NULL OR TRIM(u.openid) = '', '未关注', '已关注' ) ) gz, s.stud_id, s.stud_name, cl.clas_id, cl.clas_name FROM student s LEFT JOIN class2student cs ON cs.stud_id = s.stud_id AND cs.is_del = 0 LEFT JOIN classes cl ON cl.clas_id = cs.clas_id AND cl.org_id = s.org_id AND cl.is_del = 0 AND cl.is_graduated = 0 LEFT JOIN grade2clas gc ON gc.clas_id = cl.clas_id AND gc.is_del = 0 LEFT JOIN grade g ON g.grade_id = gc.grade_id AND g.is_del = 0 AND g.org_id = s.org_id AND g.org_id = cl.org_id LEFT JOIN student2parent sp ON s.stud_id = sp.stud_id AND sp.is_del = 0 LEFT JOIN parent p ON p.parent_id = sp.parent_id AND p.is_del = 0 AND p.org_id = s.org_id AND p.org_id = cl.org_id AND p.org_id = g.org_id LEFT JOIN org_user u ON p.user_id = u.user_id AND u.org_id = s.org_id AND u.org_id = cl.org_id AND u.org_id = g.org_id AND u.org_id = p.org_id AND u.is_del = 0 AND u.identity = 0 LEFT JOIN organization o ON o.org_id = s.org_id AND o.org_id = g.org_id AND o.org_id = p.org_id AND o.org_id = u.org_id AND o.is_del = 0 LEFT JOIN wx_config w ON w.org_id = s.org_id AND w.org_id = cl.org_id AND w.org_id = g.org_id AND w.org_id = p.org_id AND w.org_id = u.org_id AND w.org_id = o.org_id WHERE s.is_del = 0 GROUP BY s.stud_id ) gg1 WHERE gg1.org_name_cn != '' AND gg1.gz LIKE '%已关注%' ) gg GROUP BY gg.org_id, gg.org_name_cn, gg.clas_name, gg.clas_id ) xx RIGHT JOIN ( SELECT o.org_id,o.org_name_cn,c.clas_id AS stu_clas_id,CONCAT(g.grade_name,' ',c.clas_name) AS clas_name,COUNT(cs.stud_id) AS stu_num FROM organization o INNER JOIN classes c ON o.org_id=c.org_id AND c.is_del=0 AND c.is_graduated=0 INNER JOIN grade2clas gc ON c.clas_id=gc.clas_id AND gc.is_del=0 INNER JOIN grade g ON g.grade_id=gc.grade_id AND g.org_id=c.org_id AND g.org_id=o.org_id AND g.is_del=0 INNER JOIN wx_config w ON w.org_id=o.org_id AND w.org_id=c.org_id AND w.org_id=g.org_id LEFT JOIN class2student cs ON cs.clas_id=c.clas_id AND cs.clas_id=gc.clas_id AND cs.is_del=0 GROUP BY c.clas_id ORDER BY o.org_id ASC, c.clas_id ASC ) yy ON xx.clas_id = yy.stu_clas_id";
				break;
			case 2:
				sql = "SELECT o.org_name_cn as org_name, bb.clas_name as clas_name, bb.tech_name as teacher_name, bb.content as note, DATE_FORMAT(bb.insert_time,'%Y-%m-%d %h:%i:%s') as time, bb.pl_num as pinglun_num, bb.dz_num as dianzan_num FROM edugate_base.organization o INNER JOIN (SELECT b.*, c.clas_name FROM shijiwxy.blog b INNER JOIN edugate_base.classes c ON b.clas_id = c.clas_id AND c.is_graduated = 0) bb  ON o.org_id = bb.org_id WHERE o.org_id NOT IN (192, 167)";
				break;
			case 3:
				sql = "SELECT o.org_name_cn as org_name, w.tech_name as teacher_name, w.title as note, DATE_FORMAT(w.insert_time,'%Y-%m-%d %h:%i:%s') as time, w.num as num FROM shijiwxy.wages w INNER JOIN edugate_base.organization o ON w.org_id = o.org_id WHERE w.org_id NOT IN (167, 192) AND w.is_del = 0";
				break;
			case 4:
				sql = "SELECT o.org_name_cn as org_name, n.content as note, DATE_FORMAT(n.created_time,'%Y-%m-%d %h:%i:%s') as time, n.target_uids_num as num, (CASE n.type WHEN '1' THEN '校园通知' WHEN '2' THEN '教师通知' WHEN '3' THEN '作业' END) as type, n.name as teacher_name FROM edugate_base.organization o INNER JOIN (SELECT * FROM (SELECT * FROM wx_notice_0.w_notice_0 wn UNION SELECT * FROM wx_notice_0.w_notice_1  UNION SELECT * FROM wx_notice_0.w_notice_2 UNION SELECT * FROM wx_notice_0.w_notice_3) no1 UNION SELECT * FROM (SELECT * FROM wx_notice_1.w_notice_0 wn UNION SELECT * FROM wx_notice_1.w_notice_1 UNION SELECT * FROM wx_notice_1.w_notice_2 UNION SELECT * FROM wx_notice_1.w_notice_3) no2 UNION SELECT * FROM (SELECT  * FROM wx_notice_2.w_notice_0 wn UNION SELECT * FROM wx_notice_2.w_notice_1 UNION SELECT * FROM wx_notice_2.w_notice_2  UNION SELECT * FROM wx_notice_2.w_notice_3) no2 UNION SELECT * FROM (SELECT * FROM wx_notice_3.w_notice_0 wn UNION SELECT * FROM wx_notice_3.w_notice_1 UNION SELECT * FROM wx_notice_3.w_notice_2 UNION SELECT * FROM wx_notice_3.w_notice_3) no2) n ON o.org_id = n.org_id WHERE o.org_id NOT IN (167, 192)";
				break;
			case 5:
				sql = "SELECT o.org_name_cn AS org_name, IFNULL(bb.`学生关注数`, 0) AS student_bind, IFNULL(aa.`学生总人数`, 0) AS student_num, CONCAT( ROUND( IFNULL(bb.`学生关注数`, 0) / IFNULL(aa.`学生总人数`, 0) * 100, 2 ), '%' ) AS student_rate, IFNULL(dd.`教师关注数`, 0) AS teacher_bind, IFNULL(cc.`教师总人数`, 0) AS teacher_num, CONCAT( ROUND( IFNULL(dd.`教师关注数`, 0) / IFNULL(cc.`教师总人数`, 0) * 100, 2 ), '%' ) AS teacher_rate, IFNULL(ff.`家长关注数`, 0) AS parent_bind, IFNULL(ee.`家长总人数`, 0) AS parent_num, CONCAT( ROUND( IFNULL(ff.`家长关注数`, 0) / IFNULL(ee.`家长总人数`, 0) * 100, 2 ), '%' ) AS parent_rate FROM wx_config w LEFT JOIN organization o ON o.org_id = w.org_id AND o.is_del = 0 LEFT JOIN ( SELECT s.org_id, COUNT(1) AS '学生总人数' FROM student s INNER JOIN class2student cs ON s.stud_id = cs.stud_id AND cs.is_del=0 INNER JOIN classes cls ON cls.clas_id = cs.clas_id AND cls.org_id = s.org_id AND cls.is_del = 0 AND cls.is_graduated = 0 WHERE s.is_del = 0 GROUP BY s.org_id ) aa ON aa.org_id = w.org_id LEFT JOIN ( SELECT a.org_id, a.org_name_cn, COUNT(1) AS '学生关注数' FROM ( SELECT o.org_id, o.org_name_cn,  IF ( w.org_id IS NULL, '未开通', '已开通' ) AS config, GROUP_CONCAT(  IF ( u.openid IS NULL OR TRIM(u.openid) = '', '未关注', '已关注' ) ) gz FROM student s LEFT JOIN class2student cs ON cs.stud_id = s.stud_id AND cs.is_del = 0 LEFT JOIN classes cl ON cl.clas_id = cs.clas_id AND cl.org_id = s.org_id AND cl.is_del = 0 AND cl.is_graduated = 0 LEFT JOIN grade2clas gc ON gc.clas_id = cl.clas_id AND gc.is_del = 0 LEFT JOIN grade g ON g.grade_id = gc.grade_id AND g.is_del = 0 AND g.org_id = s.org_id AND g.org_id = cl.org_id LEFT JOIN student2parent sp ON s.stud_id = sp.stud_id AND sp.is_del = 0 LEFT JOIN parent p ON p.parent_id = sp.parent_id AND p.is_del = 0 AND p.org_id = s.org_id AND p.org_id = cl.org_id AND p.org_id = g.org_id LEFT JOIN org_user u ON p.user_id = u.user_id AND u.org_id = s.org_id AND u.org_id = cl.org_id AND u.org_id = g.org_id AND u.org_id = p.org_id AND u.is_del = 0 AND u.identity = 0 LEFT JOIN organization o ON o.org_id = s.org_id AND o.org_id = g.org_id AND o.org_id = p.org_id AND o.org_id = u.org_id AND o.is_del = 0 LEFT JOIN wx_config w ON w.org_id = s.org_id AND w.org_id = cl.org_id AND w.org_id = g.org_id AND w.org_id = p.org_id AND w.org_id = u.org_id AND w.org_id = o.org_id WHERE s.is_del = 0 GROUP BY s.stud_id ) a WHERE a.gz LIKE '%已关注%' GROUP BY a.org_id ) bb ON aa.org_id = bb.org_id AND bb.org_id = w.org_id LEFT JOIN ( SELECT t.org_id, COUNT(1) AS '教师总人数' FROM teacher t INNER JOIN org_user ou ON t.user_id = ou.user_id AND t.org_id = ou.org_id AND ou.is_del = 0 AND ou.identity = 1 WHERE t.is_del = 0 GROUP BY t.org_id ) cc ON cc.org_id = w.org_id LEFT JOIN ( SELECT c.org_id, COUNT(1) AS '教师关注数' FROM ( SELECT o.org_id, IF ( u.openid IS NULL OR TRIM(u.openid) = '', '未关注', '已关注' ) AS gz FROM teacher t INNER JOIN org_user u ON t.org_id = u.org_id AND u.is_del = 0 AND u.identity = 1 AND t.user_id = u.user_id INNER JOIN organization o ON t.org_id = o.org_id AND o.org_id = u.org_id AND o.is_del = 0 WHERE t.is_del = 0 ) c WHERE c.gz LIKE '已关注' GROUP BY c.org_id ) dd ON dd.org_id = cc.org_id AND dd.org_id = w.org_id LEFT JOIN ( SELECT DISTINCT p.parent_id, p.org_id, COUNT(1) AS '家长总人数' FROM parent p INNER JOIN student2parent sp ON p.parent_id = sp.parent_id AND sp.is_del = 0 INNER JOIN student s ON s.stud_id = sp.stud_id AND s.org_id = p.org_id AND s.is_del = 0 INNER JOIN class2student cs ON cs.stud_id = s.stud_id AND cs.is_del = 0 INNER JOIN classes cl ON cl.clas_id = cs.clas_id AND cl.is_del = 0 AND cl.is_graduated = 0 AND cl.org_id = p.org_id AND cl.org_id = s.org_id WHERE p.is_del = 0 GROUP BY p.org_id ) ee ON ee.org_id = w.org_id LEFT JOIN ( SELECT e.org_id, COUNT(1) AS '家长关注数' FROM ( SELECT o.org_id, IF ( u.openid IS NULL OR TRIM(u.openid) = '', '未关注', '已关注' ) AS gz FROM parent t INNER JOIN org_user u ON t.org_id = u.org_id AND u.is_del = 0 AND u.identity = 0 AND t.user_id = u.user_id INNER JOIN organization o ON t.org_id = o.org_id AND o.org_id = u.org_id AND o.is_del = 0 INNER JOIN student2parent sp ON t.parent_id = sp.parent_id AND sp.is_del = 0 INNER JOIN student s ON s.stud_id = sp.stud_id AND s.org_id = t.org_id AND s.is_del = 0 AND s.org_id = t.org_id AND s.org_id = o.org_id AND s.org_id = u.org_id INNER JOIN class2student cs ON cs.stud_id = s.stud_id AND cs.is_del = 0 INNER JOIN classes cl ON cl.clas_id = cs.clas_id AND cl.is_del = 0 AND cl.is_graduated = 0 AND cl.org_id = t.org_id AND cl.org_id = s.org_id AND cl.org_id = t.org_id AND cl.org_id = u.org_id AND cl.org_id = o.org_id WHERE t.is_del = 0 ) e WHERE e.gz LIKE '已关注' GROUP BY e.org_id ) ff ON ff.org_id = ee.org_id AND ff.org_id = w.org_id ";
				break;
			case 6:
				sql = "SELECT o.org_name_cn as org_name, c.tech_name as teacher_name, c.item as note, DATE_FORMAT(c.insert_time,'%Y-%m-%d %h:%i:%s') as time, DATE_FORMAT(c.start_time,'%Y-%m-%d %h:%i:%s') as start_time, DATE_FORMAT(c.end_time,'%Y-%m-%d %h:%i:%s') as end_time, c.money as money, c.ys_num as ys_num, c.ss_num as ss_num, c.ys_money as ys_money, (c.ss_money / 100) as ss_money, (CASE status WHEN '1' THEN '待执行' WHEN '2' THEN '已撤回'  WHEN '3' THEN (IF(c.end_time > SYSDATE(), '正在收费', '已结束')) WHEN '4' THEN '执行失败' WHEN '5' THEN '手动关闭' WHEN '6' THEN '已结束' END) as status FROM shijiwxy.charge c LEFT JOIN edugate_base.organization o  ON c.org_id = o.org_id WHERE c.org_id != 167 AND c.is_del=0 AND c.item NOT LIKE '%测试%' AND c.org_id NOT IN (167, 192)";
				break;
			case 7:
				sql = " SELECT o.org_name_cn as org_name, IF(w.org_id IS NULL,'未开通','已开通') as type, g.grade_name as note, cl.clas_name as clas_name, s.stud_name as teacher_name, group_concat( ( CASE sp.relation WHEN '0' THEN '父亲' WHEN '1' THEN '母亲' WHEN '2' THEN	'爷爷' WHEN '3' THEN	'奶奶' WHEN '4' THEN	'外公' WHEN '5' THEN	'外婆' WHEN '6' THEN	'其他' END ), '-', p.parent_name, ':', u.user_mobile, ' ', IF ( u.openid IS NULL OR TRIM(u.openid) = '', '未关注', '已关注' ) )  as status FROM student s LEFT JOIN class2student cs ON cs.stud_id = s.stud_id AND cs.is_del = 0 LEFT JOIN classes cl ON cl.clas_id = cs.clas_id AND cl.org_id = s.org_id AND cl.is_del = 0 AND cl.is_graduated = 0 LEFT JOIN grade2clas gc ON gc.clas_id = cl.clas_id AND gc.is_del = 0 LEFT JOIN grade g ON g.grade_id = gc.grade_id AND g.is_del = 0 AND g.org_id = s.org_id AND g.org_id = cl.org_id LEFT JOIN student2parent sp ON s.stud_id = sp.stud_id AND sp.is_del = 0 LEFT JOIN parent p ON p.parent_id = sp.parent_id AND p.is_del = 0 AND p.org_id = s.org_id AND p.org_id = cl.org_id AND p.org_id = g.org_id LEFT JOIN org_user u ON p.user_id = u.user_id AND u.org_id = s.org_id AND u.org_id = cl.org_id AND u.org_id = g.org_id AND u.org_id = p.org_id AND u.is_del = 0 AND u.identity=0 LEFT JOIN organization o ON o.org_id = s.org_id AND o.org_id = g.org_id AND o.org_id = p.org_id AND o.org_id = u.org_id AND o.is_del = 0 LEFT JOIN wx_config w ON w.org_id=s.org_id  AND w.org_id=cl.org_id AND w.org_id=g.org_id  AND w.org_id=p.org_id AND w.org_id=u.org_id AND w.org_id=o.org_id WHERE s.is_del=0 ";
				if(StringUtils.isNotBlank(ids)){
					sql += " AND o.org_id in  :ids  GROUP BY s.stud_id ORDER BY o.org_name_cn,grade_name,clas_name ";
				}else if(StringUtils.isNotBlank(area)){
					sql += " AND o.area LIKE :area GROUP BY s.stud_id ORDER BY o.org_name_cn,grade_name,clas_name ";
				}else{
					sql += " GROUP BY s.stud_id ORDER BY o.org_name_cn,grade_name,clas_name ";
				}
				break;
			case 8:
				sql = "SELECT o.org_name_cn as org_name,IF(w.org_id IS NULL,'未开通','已开通') as type,d.dep_name,t.tech_name as teacher_name,u.user_mobile as mobile,IF ( u.openid IS NULL OR TRIM(u.openid) = '', '未关注', '已关注' ) as is_bind FROM teacher t INNER JOIN org_user u ON t.org_id=u.org_id AND u.is_del=0 AND u.identity=1 AND t.user_id=u.user_id INNER JOIN organization o ON t.org_id=o.org_id AND o.org_id=u.org_id AND o.is_del=0  LEFT JOIN wx_config w ON w.org_id=t.org_id AND w.org_id=u.org_id AND w.org_id=o.org_id  LEFT JOIN department d ON d.org_id=t.org_id AND d.org_id=u.org_id AND d.org_id=o.org_id AND d.org_id=w.org_id AND d.is_del=0 AND t.dep_id=d.dep_id WHERE t.is_del=0 ";
				if(StringUtils.isNotBlank(ids)){
					sql += " AND o.org_id in  :ids  ORDER BY o.org_name_cn ";
				}else if(StringUtils.isNotBlank(area)){
					sql += " AND o.area LIKE :area ORDER BY o.org_name_cn ";
				}else{
					sql += " ORDER BY o.org_name_cn ";
				}
				break;
			case 9:
				sql = "SELECT z.org_name_cn as org_name,az.azz as teacher_num,g.gg as teacher_bind,z.zz as student_num,CONCAT(ROUND(IFNULL(g.gg, 0) / IFNULL(z.zz, 0) * 100, 2), '%') as teacher_rate FROM (SELECT a.org_name_cn,COUNT(a.tech_name) as zz,a.org_id FROM (SELECT o.org_name_cn,t.tech_name,r.role_name,u.openid,o.org_id FROM org_user u INNER JOIN tech2role tr ON u.user_id=tr.tech_id AND tr.is_del=0 INNER JOIN teacher t ON t.user_id=u.user_id AND t.user_id=tr.tech_id AND t.is_del=0 AND t.org_id=u.org_id INNER JOIN role r ON r.org_id=u.org_id AND r.org_id=t.org_id AND r.role_id=tr.role_id AND r.is_del=0 INNER JOIN organization o ON o.org_id=u.org_id AND o.org_id=t.org_id AND o.org_id=r.org_id AND o.is_del=0 WHERE o.org_id IN ( SELECT org_id FROM wx_config ) AND u.identity=1 AND u.is_del=0 GROUP BY t.tech_id ) a GROUP BY a.org_id) z INNER JOIN (SELECT b.org_name_cn,COUNT(b.tech_name) as gg,b.org_id FROM (SELECT o.org_name_cn,t.tech_name,r.role_name,u.openid,o.org_id FROM org_user u INNER JOIN tech2role tr ON u.user_id=tr.tech_id AND tr.is_del=0 INNER JOIN teacher t ON t.user_id=u.user_id AND t.user_id=tr.tech_id AND t.is_del=0 AND t.org_id=u.org_id INNER JOIN role r ON r.org_id=u.org_id AND r.org_id=t.org_id AND r.role_id=tr.role_id AND r.is_del=0 INNER JOIN organization o ON o.org_id=u.org_id AND o.org_id=t.org_id AND o.org_id=r.org_id AND o.is_del=0 WHERE o.org_id IN ( SELECT org_id FROM wx_config ) AND u.identity=1 AND u.openid IS NOT NULL AND TRIM(u.openid) <> '' AND u.is_del=0 GROUP BY t.tech_id ) b GROUP BY b.org_id) g ON z.org_id=g.org_id INNER JOIN  (SELECT c.org_name_cn,COUNT(c.tech_name) as azz,c.org_id FROM  (SELECT  o.org_name_cn,o.org_id,t.tech_name FROM org_user u  INNER JOIN teacher t ON t.user_id=u.user_id AND t.is_del=0 AND t.org_id=u.org_id INNER JOIN organization o ON o.org_id=u.org_id AND o.org_id=t.org_id  AND o.is_del=0  WHERE o.org_id IN ( SELECT org_id FROM wx_config ) AND u.identity=1 AND u.is_del=0 GROUP BY t.tech_id ) c GROUP BY c.org_id) az ON az.org_id=g.org_id AND az.org_id=z.org_id";
				break;
			default:
				return new ArrayList<Data>();
		}
		Session session=this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Data.class));
		switch (type) {
			case 7:
				if(StringUtils.isNotBlank(ids)){
					query.setParameterList("ids", ids.split(","));
				}else if(StringUtils.isNotBlank(area)){
					query.setString("area", "%"+area+"%");
				}
				break;
			case 8:
				if(StringUtils.isNotBlank(ids)){
					query.setParameterList("ids", ids.split(","));
				}else if(StringUtils.isNotBlank(area)){
					query.setString("area", "%"+area+"%");
				}
				break;
			default:
				break;
		}
		List<Data> list = query.list();
		switch (type) {
			case 7:
				for (int i = 0; i < list.size(); i++) {
					if(list.get(i).getStatus().indexOf("已关注")>-1){
						list.get(i).setIs_bind("已关注");
					}else{
						list.get(i).setIs_bind("未关注");
					}
					list.get(i).setId(i+1);
				}
				break;
			default:
				for (int i = 0; i < list.size(); i++) {
					list.get(i).setId(i+1);
				}
				break;
		}
		return list;
	}

	
}
