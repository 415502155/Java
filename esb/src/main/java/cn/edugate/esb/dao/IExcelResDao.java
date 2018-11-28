package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.ExcelRes;

public interface IExcelResDao extends BaseDAO<ExcelRes>{

	public abstract List<ExcelRes> getExcelResList(int type, int user_id, int org_id, int proce_type);
}
