package sng.dao;

import java.util.List;
import sng.entity.Vouchermodel;


public interface VoucherModelDao extends BaseDao<Vouchermodel>{
	

	Integer VouModelNum(Integer orgId);
	
	void update(Integer orgId, String picName, String voucherContent, Integer voucherModelId, Integer length, Integer width);

	List<Vouchermodel> getVouModel(Integer orgId);
}
