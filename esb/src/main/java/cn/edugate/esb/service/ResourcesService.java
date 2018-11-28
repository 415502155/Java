package cn.edugate.esb.service;

import java.io.InputStream;
import java.util.List;

import cn.edugate.esb.entity.ExcelRes;
import cn.edugate.esb.entity.Picture;

/**
 * 资源服务接口 Title:ResourcesService Description: Company:SJWY
 * 
 * @author:Huangcf
 * @Date:2017年6月11日上午10:16:20
 */
public interface ResourcesService {

	public String uploadfile();

	public Picture getResPicture(Integer id);

	public String getRomoteFile(String group_name, String path, String localfilepath);

	public String savePicture(Integer type, Integer user_id, InputStream inputStream, Integer length, String ext, Integer width,
			Integer height);

	public ExcelRes getExcelRes(Integer id);

	public String saveExcel(Integer type, Integer user_id, InputStream inputStream, Integer length, String ext, Integer org_id,
			String message, Byte proce_type,Boolean finished);
	
	public abstract void updateExcelResEntity(ExcelRes excelResEntity);
	
	public abstract List<ExcelRes> getExcelResList(int type, int user_id, int org_id, int proce_type);

	public List<Picture> getPictures();

	public void savePicture(Picture picture);
	
}