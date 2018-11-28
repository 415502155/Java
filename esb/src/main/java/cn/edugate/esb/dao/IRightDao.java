package cn.edugate.esb.dao;

import cn.edugate.esb.entity.Right;
import cn.edugate.esb.util.Paging;

public interface IRightDao extends BaseDAO<Right>{

	void getAllWithPaging(Paging<Right> paging, String searchName);

	Long getTotalCountByName(String searchName);
}
