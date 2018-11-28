package cn.edugate.esb.amqp;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import cn.edugate.esb.EduConfig;
import cn.edugate.esb.entity.ExcelRes;
import cn.edugate.esb.service.ResourcesService;
import cn.edugate.esb.service.TeacherService;
import cn.edugate.esb.util.ImportExcelUtil;
import cn.edugate.esb.util.Utils;


public class BatchAddTeacherActivator {

	private static Log logger = LogFactory.getLog(BatchAddTeacherActivator.class);
	
	@Autowired
	private ResourcesService resourcesService;
	
	@Autowired
	private EduConfig eduConfig;
	
	private static String[] ColumnName4ImportTeacher = { "教师组名称", "教师姓名*", "教师卡号", "教师性别", "教师生日", "民族", "手机号码*", "号码类型*", "教师住址", "备注", "用户角色", "所在部门", "证件类型", "证件号码" };
	
	@Autowired
	private TeacherService teacherService;

	public void getExcelFileAndProcessData(Message<String> msg) throws Exception {

		String fileID = msg.getPayload();
		String errorMessage = "";
		try {
			if (StringUtils.isNotBlank(fileID)) {
				// 根据fileID查询res_excel表中记录
				ExcelRes excelResEntity = resourcesService.getExcelRes(Integer.valueOf(fileID));
				if (excelResEntity != null) {
					// 根据文件ID拿到Excel文件，机构ID等信息

					String basePath = this.eduConfig.getPath();
					String excelPath = basePath + File.separator + "res" + File.separator + "excel" + File.separator;
					String tempPath = basePath + File.separator + "res" + File.separator + "temp" + File.separator;
					String localFilePath = excelPath + Utils.getPathById(Integer.valueOf(fileID));

					// 从远程地址获取文件到本地
					File dir = new File(localFilePath);
					if (!dir.isDirectory()) {
						dir.mkdirs();
					}
					String filepathA = resourcesService.getRomoteFile(excelResEntity.getGroup_name(), excelResEntity.getPath(),
							localFilePath + fileID + "." + excelResEntity.getExt());
					//System.out.println(filepathA);

					File file = new File(localFilePath + fileID + "." + excelResEntity.getExt());
					FileInputStream in = new FileInputStream(file);
					MultipartFile multiFile = new MockMultipartFile("转换文件", in);

					int orgID = excelResEntity.getOrg_id();

					if (file != null) {
						Workbook wb = new HSSFWorkbook(multiFile.getInputStream());
						if (wb != null) {
							String headLineCheckResult = ImportExcelUtil.verificationImportExcelHeadLine(wb,
									ColumnName4ImportTeacher);
							if (StringUtils.isBlank(headLineCheckResult)) {
								List<String[]> excelRowArray = ImportExcelUtil.getImportTeachersFromExcel(wb);
								wb = null;
								if (!excelRowArray.isEmpty()) {
									List<String[]> errorList = teacherService.validateAndSaveTeacher(orgID, excelRowArray);
									if (!errorList.isEmpty()) {
										String completeFileName = ImportExcelUtil.makeExcelFileWithErrorInfo(excelResEntity
												.getMessage().substring(0, excelResEntity.getMessage().indexOf(".")) + "错误信息",
												"批量导入教师", ColumnName4ImportTeacher, errorList, tempPath);
										// 将保存在temp文件夹下的excel保存到数据库
										file = new File(tempPath + completeFileName);
										in.close();
										in = new FileInputStream(file);
										multiFile = new MockMultipartFile(completeFileName, in);

										String ext = completeFileName.substring(completeFileName.lastIndexOf(".") + 1);
										Long length = multiFile.getSize();

										String fileName = this.resourcesService.saveExcel(1, 0, multiFile.getInputStream(),
												length.intValue(), ext, orgID, completeFileName, (byte) 0,false);
										String result_id = fileName.substring(0, fileName.indexOf("."));

										excelResEntity.setFinished(true);
										excelResEntity.setResult_id(Integer.valueOf(result_id));
										excelResEntity.setUpdated_time(new Date());
										resourcesService.updateExcelResEntity(excelResEntity);

										in.close();
									} else {
										// 没有错误信息说明，此次导入的excel内容已经全部成功
										excelResEntity.setFinished(true);
										excelResEntity.setUpdated_time(new Date());
										resourcesService.updateExcelResEntity(excelResEntity);
									}
								} else {
									errorMessage = "未在导入文件中发现数据！";

									// 将错误信息写入excel
									List<String[]> errorList = new ArrayList<String[]>();
									String[] errorInfo = new String[ColumnName4ImportTeacher.length + 1];
									errorInfo[0] = errorMessage;
									errorList.add(errorInfo);

									String completeFileName = ImportExcelUtil.makeExcelFileWithErrorInfo(excelResEntity
											.getMessage().substring(0, excelResEntity.getMessage().indexOf(".")) + "错误信息",
											"批量导入教师", ColumnName4ImportTeacher, errorList, tempPath);
									// 将保存在temp文件夹下的excel保存到数据库
									file = new File(tempPath + completeFileName);
									in.close();
									in = new FileInputStream(file);
									multiFile = new MockMultipartFile(completeFileName, in);

									String ext = completeFileName.substring(completeFileName.lastIndexOf(".") + 1);
									Long length = multiFile.getSize();

									String fileName = this.resourcesService.saveExcel(1, 0, multiFile.getInputStream(),
											length.intValue(), ext, orgID, completeFileName, (byte) 0,false);
									String result_id = fileName.substring(0, fileName.indexOf("."));

									excelResEntity.setFinished(true);
									excelResEntity.setResult_id(Integer.valueOf(result_id));
									excelResEntity.setUpdated_time(new Date());
									resourcesService.updateExcelResEntity(excelResEntity);

									in.close();
								}
							} else {
								errorMessage = headLineCheckResult;
								// 将错误信息写入excel
								List<String[]> errorList = new ArrayList<String[]>();
								String[] errorInfo = new String[ColumnName4ImportTeacher.length + 1];
								errorInfo[0] = errorMessage;
								errorList.add(errorInfo);

								String completeFileName = ImportExcelUtil.makeExcelFileWithErrorInfo(excelResEntity.getMessage()
										.substring(0, excelResEntity.getMessage().indexOf(".")) + "错误信息", "批量导入教师",
										ColumnName4ImportTeacher, errorList, tempPath);
								// 将保存在temp文件夹下的excel保存到数据库
								file = new File(tempPath + completeFileName);
								in.close();
								in = new FileInputStream(file);
								multiFile = new MockMultipartFile(completeFileName, in);

								String ext = completeFileName.substring(completeFileName.lastIndexOf(".") + 1);
								Long length = multiFile.getSize();

								String fileName = this.resourcesService.saveExcel(1, 0, multiFile.getInputStream(),
										length.intValue(), ext, orgID, completeFileName, (byte) 0,false);
								String result_id = fileName.substring(0, fileName.indexOf("."));

								excelResEntity.setFinished(true);
								excelResEntity.setResult_id(Integer.valueOf(result_id));
								excelResEntity.setUpdated_time(new Date());
								resourcesService.updateExcelResEntity(excelResEntity);

								in.close();
							}
						}
					} else {
						// logger.info(" PAYLOAD ERROR ：文件未找到！上传增加教师文件ID=" + msg.getPayload());
						errorMessage = "文件未找到！上传增加教师文件ID=" + msg.getPayload();
					}
				} else {
					errorMessage = "查询上传记录时出现错误！";
				}
			}
		} catch (Exception ex) {
			errorMessage = ex.getMessage();
		}
		System.out.println(errorMessage);

	}
}
