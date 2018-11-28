package cn.edugate.esb.imp;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.IFunctionUseRangeDao;
import cn.edugate.esb.entity.FunctionUseRange;
import cn.edugate.esb.service.FunctionUseRangeService;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.GradeNumber;

/**
 * 功能适用范围服务实现类
 * Title:FunctionUseRangeServiceImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月16日下午3:45:21
 */
@Component
@Transactional("transactionManager")
public class FunctionUseRangeServiceImpl implements FunctionUseRangeService {

	private static Logger logger = Logger.getLogger(FunctionUseRangeServiceImpl.class);
	
	@Autowired
	private IFunctionUseRangeDao functionUseRangeDao;
	public void setFunctionUseRangeDao(IFunctionUseRangeDao functionUseRangeDao) {
		this.functionUseRangeDao = functionUseRangeDao;
	}

	@Override
	public void add(FunctionUseRange functionUseRange) {
		functionUseRange.setIs_del(Constant.IS_DEL_NO);
		functionUseRange.setInsert_time(new Date());
		functionUseRangeDao.save(functionUseRange);
	}

	@Override
	public void addByGradeNumber(String gradeNumber,FunctionUseRange functionUseRange) {
		Integer funID = functionUseRange.getFun_id();
		Set<String> set = gradeNumberHandler(gradeNumber);
		for (String gradeNum : set) {
			Integer num = Integer.parseInt(gradeNum);
			FunctionUseRange fur = new FunctionUseRange();
			fur.setFun_id(funID);
			if(num>10&&num<20){
				fur.setFur_type(Constant.FUR_TYPE_SCHOOLS);
				fur.setGrade_number(num);
				fur.setOrg_type(Constant.ORG_TYPE_KINDERGARTEN);
				add(fur);
			}else if(num>20&&num<30){
				fur.setFur_type(Constant.FUR_TYPE_SCHOOLS);
				fur.setGrade_number(num);
				fur.setOrg_type(Constant.ORG_TYPE_PRIMARY);
				add(fur);
			}else if(num>30&&num<40){
				fur.setFur_type(Constant.FUR_TYPE_SCHOOLS);
				fur.setGrade_number(num);
				fur.setOrg_type(Constant.ORG_TYPE_JUNIOR);
				add(fur);
			}else if(num>40&&num<50){
				fur.setFur_type(Constant.FUR_TYPE_SCHOOLS);
				fur.setGrade_number(num);
				fur.setOrg_type(Constant.ORG_TYPE_SENIOR);
				add(fur);
			}else if(num>50&&num<60){
				fur.setFur_type(Constant.FUR_TYPE_SCHOOLS);
				fur.setGrade_number(num);
				fur.setOrg_type(Constant.ORG_TYPE_OTHERS);
				add(fur);
			}else if(num==2){
				fur.setFur_type(Constant.FUR_TYPE_TRAINING);
				fur.setGrade_number(num);
				add(fur);
			}else if(num==3){
				fur.setFur_type(Constant.FUR_TYPE_BUREAU);
				fur.setGrade_number(num);
				add(fur);
			}else{
				logger.error("创建功能适用范围失败，遇到了没有预设的编号");
			}
		}
	}
	
	/**
	 * 处理前台页面传过来的年级编号字符串
	 * @param gradeNumber
	 * @return
	 */
	private Set<String> gradeNumberHandler(String gradeNumber){
		Set<String> set = new HashSet<String>(Arrays.asList(gradeNumber.split(",")));
		if(set.contains("1")){
			set.remove("1");
			set.removeAll(Arrays.asList(GradeNumber.allKindergarten));
			set.removeAll(Arrays.asList(GradeNumber.allPrimary));
			set.removeAll(Arrays.asList(GradeNumber.allJunior));
			set.removeAll(Arrays.asList(GradeNumber.allSenior));
			set.addAll(Arrays.asList(GradeNumber.allKindergarten));
			set.addAll(Arrays.asList(GradeNumber.allPrimary));
			set.addAll(Arrays.asList(GradeNumber.allJunior));
			set.addAll(Arrays.asList(GradeNumber.allSenior));
		}else{
			if(set.contains("10")){
				set.remove("10");
				set.removeAll(Arrays.asList(GradeNumber.allKindergarten));
				set.addAll(Arrays.asList(GradeNumber.allKindergarten));
			}
			if(set.contains("20")){
				set.remove("20");
				set.removeAll(Arrays.asList(GradeNumber.allPrimary));
				set.addAll(Arrays.asList(GradeNumber.allPrimary));
			}
			if(set.contains("30")){
				set.remove("30");
				set.removeAll(Arrays.asList(GradeNumber.allJunior));
				set.addAll(Arrays.asList(GradeNumber.allJunior));
			}
			if(set.contains("40")){
				set.remove("40");
				set.removeAll(Arrays.asList(GradeNumber.allSenior));
				set.addAll(Arrays.asList(GradeNumber.allSenior));
			}
		}
		return set;
	}

	@Override
	public String getGradeNumStringByFunID(Integer id) {
		return functionUseRangeDao.getGradeNumStringByFunID(id);
	}

	@Override
	public void updateByGradeNumber(String gradeNumber,FunctionUseRange functionUseRange) {
		deleteByFunctionID(functionUseRange.getFun_id());
		addByGradeNumber(gradeNumber, functionUseRange);
	}

	@Override
	public void deleteByFunctionID(Integer functionID) {
		functionUseRangeDao.deleteByFunctionID(functionID);
	}

}
