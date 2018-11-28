package cn.edugate.esb.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import cn.edugate.esb.eduEnum.EnumRoleLabel;
import cn.edugate.esb.entity.Course;

/**
 * 
 * @author fangjian
 * 
 */
public class ImportExcelUtil {

	/**
	 * 从请求中读取Excel文件
	 * 
	 * @param currentDepartmentName
	 * @param request
	 * @param response
	 * @param session
	 * @return Excel的工作簿对象
	 * @throws Exception
	 * @author fangjian
	 */
	public static Workbook getExcelFromRequest(HttpServletRequest request) throws Exception {
		Workbook wb = null;

		// 检查form是否有enctype="multipart/form-data"
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 检查是否有文件上传
			Iterator<String> iter = multiRequest.getFileNames();
			if (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {// 若有上传的文件，则将Excel中的信息封装为mapList，并返回
					wb = new HSSFWorkbook(file.getInputStream());
				}
			}
		}
		return wb;
	}

	public static MultipartFile getExcelFileFromRequest(HttpServletRequest request) {
		MultipartFile file = null;
		// 检查form是否有enctype="multipart/form-data"
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 检查是否有文件上传
			System.out.println(multiRequest.getContentType());
			Iterator<String> iter = multiRequest.getFileNames();
			if (iter.hasNext()) {
				file = multiRequest.getFile(iter.next());
			}
		}
		return file;
	}

	/**
	 * 校验导入的Excel文件标题行是否为标准行
	 * 
	 * @param wb
	 * @return
	 * @throws Exception
	 * @author sw
	 */
	public static String verificationImportExcelHeadLine(Workbook wb, String[] columnName) throws Exception {
		String result = "";

		try {
			Sheet sheet = wb.getSheetAt(0);

			Row row = sheet.getRow(0);
			if (row != null && row.getLastCellNum() >= columnName.length) {
				for (int idx = 0; idx < columnName.length; idx++) {
					String value = getCellValue(row.getCell(idx));
					if (StringUtils.isBlank(value) || !columnName[idx].equals(value)) {
						result = "标题行第" + (idx + 1) + "列名称错误！";
						throw new Exception();
					}
				}
			} else {
				result = "上传文件首行不能为空！";
			}
		} catch (Exception ex) {

		}
		return result;
	}

	/**
	 * 获取单元格中的值（去除字符串中所有的空格、回车、换行符、制表符及全角空格）
	 * 
	 * @param cell
	 * @return 以字符串表示的单元格中的值 无值返回空字符串
	 * @author fangjian 2014年4月4日下午7:59:33
	 */
	private static String getCellValue(Cell cell) {
		if (cell != null) {
			cell.setCellType(Cell.CELL_TYPE_STRING);
			return replaceAllBlank(cell.getStringCellValue());
		}
		return "";
	}
	
	
	/**
	 * 获取单元格中的值（去除字符串中所有的空格、回车、换行符、制表符及全角空格）
	 * 
	 * @param cell
	 * @return 以字符串表示的单元格中的值 无值返回空字符串
	 * @author fangjian 2014年4月4日下午7:59:33
	 */
	private static String getCellValue(Cell cell, String numberFormatPattern, String dateFormatPattern) {
		
		String cellValue = null;
		if (cell != null) {
			switch (cell.getCellType()) {
				case Cell.CELL_TYPE_BLANK:
					cellValue = "";
					break;
				case Cell.CELL_TYPE_ERROR:
					cellValue = Byte.toString(cell.getErrorCellValue());
					break;
				case Cell.CELL_TYPE_STRING:
					cellValue = cell.getRichStringCellValue().getString();
					cellValue = replaceAllBlank(cellValue);
					break;
				// excel中日期也是数字，因此需要判断
				case Cell.CELL_TYPE_NUMERIC:
					// 处理日期格式、时间格式
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						SimpleDateFormat sdf = null;
						if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
							sdf = new SimpleDateFormat("HH:mm");
						} else {// 日期
							if (StringUtils.isNotBlank(dateFormatPattern)) {
								sdf = new SimpleDateFormat(dateFormatPattern);
							} else {
								sdf = new SimpleDateFormat("yyyy-MM-dd");
							}
						}
						Date date = cell.getDateCellValue();
						cellValue = sdf.format(date);
					} else if (cell.getCellStyle().getDataFormat() == 58) {
						// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
						SimpleDateFormat sdf = null;
						if (StringUtils.isNotBlank(dateFormatPattern)) {
							sdf = new SimpleDateFormat(dateFormatPattern);
						} else {
							sdf = new SimpleDateFormat("yyyy-MM-dd");
						}
						double value = cell.getNumericCellValue();
						Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
						cellValue = sdf.format(date);
					} else {
						double value = cell.getNumericCellValue();
						// CellStyle style = cell.getCellStyle();
						DecimalFormat format = null;
						if (StringUtils.isNotBlank(numberFormatPattern)) {
							format = new DecimalFormat(numberFormatPattern);
						} else {
							format = new DecimalFormat("#.#");
						}
						/*
						 * String temp = style.getDataFormatString(); // 单元格设置成常规 if (temp.equals("General")) {
						 * format.applyPattern("#.###"); }
						 */
						cellValue = format.format(value);
					}
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					cellValue = Boolean.toString(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_FORMULA:
					// 公式格式，读取计算后的值，而不是公式本身
					FormulaEvaluator evaluator = cell.getRow().getSheet().getWorkbook().getCreationHelper()
							.createFormulaEvaluator();
					CellValue cellValue4Formula = evaluator.evaluate(cell);

					// System.out.println(cellValue4Formula.getStringValue());

					switch (cellValue4Formula.getCellType()) {
						case Cell.CELL_TYPE_BOOLEAN:
							cellValue = String.valueOf(cellValue4Formula.getBooleanValue());
							break;
						case Cell.CELL_TYPE_NUMERIC:
							double value = cellValue4Formula.getNumberValue();
							DecimalFormat format = null;
							if (StringUtils.isNotBlank(numberFormatPattern)) {
								format = new DecimalFormat(numberFormatPattern);
							} else {
								format = new DecimalFormat("#.#");
							}
							cellValue = format.format(value);
							break;
						case Cell.CELL_TYPE_STRING:
							cellValue = String.valueOf(cellValue4Formula.getStringValue());
							break;
						case Cell.CELL_TYPE_BLANK:
							break;
						case Cell.CELL_TYPE_ERROR:
							break;
						// CELL_TYPE_FORMULA will never happen
						case Cell.CELL_TYPE_FORMULA:
							break;
					}
					break;
				default:
					cellValue = "";
			}
		} else {
			cellValue = "";
		}

		if (StringUtils.isNotBlank(cellValue)) {
			cellValue = cellValue.trim();
		}
		return cellValue;
	}

	/**
	 * 去除字符串中所有的空格、回车、换行符、制表符及全角空格
	 * 
	 * @param str
	 * @return
	 * @author fangjian
	 */
	public static String replaceAllBlank(String str) {
		String returnStr = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			returnStr = m.replaceAll("");
			returnStr = returnStr.replaceAll("　", "");
		}
		return returnStr;
	}

	/**
	 * 从Excel中读取导入的年级班级信息
	 * 
	 * @param wb
	 * @return
	 * @throws Exception
	 * @author sw
	 */
	public static List<String[]> getImportTeachersFromExcel(Workbook wb) throws Exception {
		List<String[]> teacherArray = new ArrayList<String[]>();

		Sheet sheet = wb.getSheetAt(0);

		// 第一行标题默认为:0"教师组名称", 1"教师姓名*", 2"教师卡号", 3"教师性别", 4"教师生日", 5"民族", 6"手机号码*", 7"号码类型*", 8"教师住址", 9"备注", 10"用户角色", 11"所在部门", 12"证件类型", 13"证件号码"
		// 从第二行开始，一行一行读取，当【教师姓名*】、【电话号码*】、【号码类型*】有任一不存在时，表示excel读取结束
		int rowNum = 1;
		Row row = sheet.getRow(rowNum);
		while (row != null && (hasValue(row.getCell(1)) && hasValue(row.getCell(6)))) {
			String[] teacher = new String[14];

			teacher[0] = getCellValue(row.getCell(0));
			teacher[1] = getCellValue(row.getCell(1));
			teacher[2] = getCellValue(row.getCell(2));
			teacher[3] = getCellValue(row.getCell(3));
			teacher[4] = getCellValue(row.getCell(4));
			teacher[5] = getCellValue(row.getCell(5));
			teacher[6] = getCellValue(row.getCell(6));
			teacher[7] = getCellValue(row.getCell(7));
			teacher[8] = getCellValue(row.getCell(8));
			teacher[9] = getCellValue(row.getCell(9));
			teacher[10] = getCellValue(row.getCell(10));
			String deptName = getCellValue(row.getCell(11));
			if (StringUtils.isBlank(deptName)) {
				deptName = "默认";
			}
			teacher[11] = deptName;
			teacher[12] = getCellValue(row.getCell(12));
			teacher[13] = getCellValue(row.getCell(13));

			// 将获取的教师存入集合
			teacherArray.add(teacher);
			// 下一行
			row = sheet.getRow(++rowNum);
		}

		return teacherArray;
	}
	
	
	/**
	 * 从Excel中读取导入的年级班级信息
	 * 
	 * @param wb
	 * @return
	 * @throws Exception
	 * @author sw
	 */
	public static List<String[]> getUpgradeDataFromExcel(Workbook wb) throws Exception {
		List<String[]> dataArray = new ArrayList<String[]>();

		Sheet sheet = wb.getSheetAt(0);

		// 第一行标题默认为:0"学校ID", 1"学校标致名称",
		// 从第二行开始，一行一行读取，当【学校ID】、【学校标致名称】有任一不存在时，表示excel读取结束
		int rowNum = 1;
		Row row = sheet.getRow(rowNum);
		while (row != null && (hasValue(row.getCell(0)) && hasValue(row.getCell(1)))) {
			String[] rowData = new String[2];

			rowData[0] = getCellValue(row.getCell(0), null, null);
			rowData[1] = getCellValue(row.getCell(1), null, null);

			// 将获取的教师存入集合
			dataArray.add(rowData);
			// 下一行
			row = sheet.getRow(++rowNum);
		}

		return dataArray;
	}

	/**
	 * 判断单元格是否有值
	 * 
	 * @param cell
	 * @return ture 有值 false 无值
	 * @author fangjian 2014年4月4日下午7:55:07
	 */
	private static boolean hasValue(Cell cell) {
		boolean flag = false;
		if (cell != null) {
			cell.setCellType(Cell.CELL_TYPE_STRING);
			if (cell.getStringCellValue().trim().length() > 0) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 验证输入汉字
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean IsChinese(String str) {
		String regex = "^[\u4e00-\u9fa5]+$";
		return match(regex, str);
	}

	/**
	 * @param regex 正则表达式字符串
	 * @param str 要匹配的字符串
	 * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	 */
	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 从Excel中读取学生（及家长）信息
	 * 
	 * @param wb
	 * @return
	 * @throws Exception
	 * @author fangjian 2016年7月25日 下午4:44:15
	 */
	public static List<String[]> getStudentAndParentTableFromExcel(Workbook wb) throws Exception {
		// 定义用于存放工作表的集合
		List<String[]> studentAndParentTable = new ArrayList<String[]>();
		// 获取工作表
		Sheet sheet = wb.getSheetAt(0);
		// 第一行标题默认为:【操作员姓名(*) 】、【学校名称(*)】、【年级名称(*)】、【班级名称(*)】、【学生姓名(*)】、【学生性别】、【学生卡号】、【出生日期】、
		// 【家长1姓名(*)】、【家长1性别(*)】、【家长1亲子关系(*)】、【家长1手机号(*)】、【家长1号码类型(*)】、【家长1业务类型(*)】、
		// 【家长2姓名(*)】、【家长2性别(*)】、【家长2亲子关系(*)】、【家长2手机号(*)】、【家长2号码类型(*)】、【家长2业务类型(*)】
		// 从第二行开始，一行一行读取，当孩子信息字段（0-4）有一个不存在时，表示excel读取结束
		int rowNum = 1;
		Row row = sheet.getRow(rowNum);
		while (row != null
				&& (hasValue(row.getCell(1)) && hasValue(row.getCell(2)) && hasValue(row.getCell(3)) && hasValue(row.getCell(4)))) {

			// 每个studentAndParent代表一行记录
			String[] studentAndParent = new String[20];

			studentAndParent[0] = getCellValue(row.getCell(0));// 操作员姓名(*)
			studentAndParent[1] = getCellValue(row.getCell(1));// 学校名称(*)
			studentAndParent[2] = getCellValue(row.getCell(2));// 年级名称(*)
			studentAndParent[3] = getCellValue(row.getCell(3));// 班级名称(*)
			studentAndParent[4] = getCellValue(row.getCell(4));// 学生姓名(*)
			studentAndParent[5] = getCellValue(row.getCell(5));// 学生性别
			studentAndParent[6] = getCellValue(row.getCell(6));// 学生卡号
			studentAndParent[7] = getCellValue(row.getCell(7));// 出生日期

			studentAndParent[8] = getCellValue(row.getCell(8));// 家长1姓名(*)
			studentAndParent[9] = getCellValue(row.getCell(9));// 家长1性别(*)
			studentAndParent[10] = getCellValue(row.getCell(10));// 家长1亲子关系(*)
			studentAndParent[11] = getCellValue(row.getCell(11));// 家长1手机号(*)
			studentAndParent[12] = getCellValue(row.getCell(12));// 家长1号码类型(*)
			studentAndParent[13] = getCellValue(row.getCell(13));// 家长1业务类型(*)

			studentAndParent[14] = getCellValue(row.getCell(14));// 家长2姓名(*)
			studentAndParent[15] = getCellValue(row.getCell(15));// 家长2性别(*)
			studentAndParent[16] = getCellValue(row.getCell(16));// 家长2亲子关系(*)
			studentAndParent[17] = getCellValue(row.getCell(17));// 家长2手机号(*)
			studentAndParent[18] = getCellValue(row.getCell(18));// 家长2号码类型(*)
			studentAndParent[19] = getCellValue(row.getCell(19));// 家长2业务类型(*)

			// 将获取的学生和家长信息存入集合
			studentAndParentTable.add(studentAndParent);

			// 获取下一行
			row = sheet.getRow(++rowNum);
		}
		return studentAndParentTable;
	}

	public static String makeExcelFileWithErrorInfo(String excelName, String sheetName, String[] columnName,
			List<String[]> errorRows, String path) throws Exception {
		try {
			String excelFileName = excelName + "_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".xls";
			// 新建Excel工作簿
			Workbook workbook = new HSSFWorkbook();
			// 定义单元格样式
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			// 得到默认页面上的Font
			Font font = workbook.getFontAt((short) 0);
			font.setFontName("宋体");
			font.setFontHeightInPoints((short) 11);
			cellStyle.setFont(font);
			// 定义错误信息字体
			HSSFFont redFont = (HSSFFont) workbook.createFont();
			redFont.setFontName("宋体");
			redFont.setFontHeightInPoints((short) 11);
			redFont.setColor(HSSFColor.RED.index);// 颜色

			// 新建sheet，使用之前sheet页的名称
			Sheet sheet = workbook.createSheet(sheetName);

			// 创建标题行
			Row row = sheet.createRow(0);
			row.setRowStyle(cellStyle);

			// 写入标题行上的列名称
			Cell cell = null;
			for (int i = 0; i < columnName.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(columnName[i]);
				cell.setCellStyle(cellStyle);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				sheet.setDefaultColumnStyle(i, cellStyle);
				sheet.setColumnWidth(i, columnName[i].getBytes().length * 500);// fangjian:字符集宽度等上到服务器上面后再测试(若是utf-8的话，会更宽一些)
			}
			// 加入错误信息列
			cell = row.createCell(columnName.length);
			cell.setCellValue("错误信息");
			cell.setCellStyle(cellStyle);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			sheet.setDefaultColumnStyle(columnName.length, cellStyle);
			sheet.setColumnWidth(columnName.length, "错误信息".length() * 500);
			// sheet.setDefaultColumnStyle(i, cellStyle);

			// 写入错误内容
			for (int idx = 0; idx < errorRows.size(); idx++) {
				String[] errorRow = errorRows.get(idx);
				row = sheet.createRow(idx + 1);
				for (int columnIdx = 0; columnIdx <= columnName.length; columnIdx++) {
					if (columnIdx < columnName.length) {
						row.createCell(columnIdx, Cell.CELL_TYPE_STRING).setCellValue(errorRow[columnIdx]);
					} else {
						// 错误信息列用红色字体显示
						cell = row.createCell(columnIdx);
						cell.setCellValue(errorRow[columnIdx]);
						CellStyle errorCellStyle = workbook.createCellStyle();
						errorCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
						errorCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
						errorCellStyle.setFont(redFont);
						cell.setCellStyle(errorCellStyle);
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						sheet.setDefaultColumnStyle(columnName.length, cellStyle);
						sheet.setColumnWidth(columnName.length, "错误信息".length() * 500);
					}
				}
			}

			// 返回错误的Excel路径到页面
			String fullExcelFileName = path + File.separator + excelFileName;
			// System.out.println(fullExcelFileName);
			FileOutputStream fileOut = new FileOutputStream(fullExcelFileName);
			workbook.write(fileOut);
			fileOut.close();

			return excelFileName;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("生成包含错误信息的Excel文件失败，请联系管理员！");
		}
	}

	/**
	 * 获取临时资源路径
	 * 
	 * @return
	 */
	public static String getTempResourcePath() {
		// 调用Resources API在资源服务器上获取temp目录
		// Setting setting = Setting.getInstance();
		// ResourcesAccess resourcesAccess = new ResourcesAccess(setting.get("ResourcesApiUrl").getValue());
		// // 通过orgID和appID获取对应的资源存储路径，失败则返回“”字符串
		// return resourcesAccess.getTempPath();
		return "";
	}
	
	
	/**
	 * 获取单元格表头 列名
	 * 
	 * @param wb
	 * @return
	 * @throws Exception
	 * @author sw
	 */
	public static String[] getTechRangeExcelHeadLine(Workbook wb,Map<String,String> courMap) throws Exception {
		String[] excel = null;
		try {
			Sheet sheet = wb.getSheetAt(0);
			Row row = sheet.getRow(0);
			if (row != null) {
				int coloumNum = row.getPhysicalNumberOfCells();
				excel = new String[coloumNum];
				for (int idx = 0; idx < coloumNum; idx++) {
					String value = getCellValue(row.getCell(idx));
//					if(idx==3)//年级组长
//						if(!value.equals(EnumRoleLabel.年级组长.name()))
//							return null;
//					if(idx==4)//班主任
//						if(!value.equals(EnumRoleLabel.班主任.name()))
//							return null;
//					if(idx>4)//课程
//						if(courMap.get(value)==null)
//							return null;

					excel[idx] = value;
				}
			}
		} catch (Exception ex) {

		}
		return excel;
	}
	
	/**
	 * 获取单元格表头 列名
	 * 
	 * @param wb
	 * @return
	 * @throws Exception
	 * @author sw
	 */
	public static String[] verificationTechRangeExcelHeadLine(Workbook wb,Map<String,String> courMap) throws Exception {
		String[] excel = null;
		try {
			Sheet sheet = wb.getSheetAt(0);
			Row row = sheet.getRow(0);
			if (row != null) {
				int coloumNum = row.getPhysicalNumberOfCells();
				excel = new String[coloumNum];
				for (int idx = 0; idx < coloumNum; idx++) {
					String value = getCellValue(row.getCell(idx));
					if(idx==3)//年级组长
						if(!value.equals(EnumRoleLabel.年级组长.name()))
							return null;
					if(idx==4)//班主任
						if(!value.equals(EnumRoleLabel.班主任.name()))
							return null;
					if(idx>4)//课程
						if(courMap.get(value)==null)
							return null;

					excel[idx] = value;
				}
			}
		} catch (Exception ex) {

		}
		return excel;
	}
	
	/**
	 * 从Excel中读取导入的教师授课信息
	 * 
	 * @param wb
	 * @return
	 * @throws Exception
	 * @author sw
	 */
	public static List<String[]> getImportTechRangeFromExcel(Workbook wb) throws Exception {
		List<String[]> teacherArray = new ArrayList<String[]>();

		Sheet sheet = wb.getSheetAt(0);
		// // 第一行标题默认为:0"教师姓名", 1"教师手机号*", 2"角色", 3"年级组长", 4"班主任", "课程集合"
		int rowNum = 0;
		Row row = sheet.getRow(rowNum);
		Row rowone = sheet.getRow(0);
		int coloumNum = rowone.getPhysicalNumberOfCells();
		while (row != null && (hasValue(row.getCell(0)) && hasValue(row.getCell(1)))) {
		

			String[] excel = new String[coloumNum];
			for (int idx = 0; idx < coloumNum; idx++) {
				String value = getCellValue(row.getCell(idx));
				
				excel[idx] = value;
			}
		
			// 将获取的教师存入集合
			teacherArray.add(excel);
			// 下一行
			row = sheet.getRow(++rowNum);
		}

		return teacherArray;
	}
	
	
	
	
	
	
	/**
	 * 获取单元格表头 列名
	 * 
	 * @param wb
	 * @return
	 * @throws Exception
	 * @author sw
	 */
	public static String[] getTechGroupExcelHeadLine(Workbook wb) throws Exception {
		String[] excel = null;
		try {
			Sheet sheet = wb.getSheetAt(0);
			Row row = sheet.getRow(0);
			if (row != null) {
				int coloumNum = row.getPhysicalNumberOfCells();
				excel = new String[coloumNum];
				for (int idx = 0; idx < coloumNum; idx++) {
					String value = getCellValue(row.getCell(idx));
					if(idx==0)
						if(!value.equals("原教师组"))
							return null;
					if(idx==1)
						if(!value.equals("教师姓名"))
							return null;
					if(idx==2)
						if(!value.equals("教师手机号"))
							return null;
					if(idx==3)
						if(!value.equals("新教师组"))
							return null;

					excel[idx] = value;
				}
			}
		} catch (Exception ex) {

		}
		return excel;
	}
	
	
	
	/**
	 * 从Excel中读取导入的教师授课信息
	 * 
	 * @param wb
	 * @return
	 * @throws Exception
	 * @author sw
	 */
	public static List<String[]> getImportTechGroupFromExcel(Workbook wb) throws Exception {
		List<String[]> teacherArray = new ArrayList<String[]>();

		Sheet sheet = wb.getSheetAt(0);
		// // 第一行标题默认为:0"教师姓名", 1"教师手机号*", 2"角色", 3"年级组长", 4"班主任", "课程集合"
		int rowNum = 1;
		Row row = sheet.getRow(rowNum);
		Row rowone = sheet.getRow(0);
		int coloumNum = rowone.getPhysicalNumberOfCells();
		while (row != null && hasValue(row.getCell(1))&& hasValue(row.getCell(2))) {
		

			String[] excel = new String[coloumNum];
			for (int idx = 0; idx < coloumNum; idx++) {
				String value = getCellValue(row.getCell(idx));
				
				excel[idx] = value;
			}
		
			// 将获取的教师存入集合
			teacherArray.add(excel);
			// 下一行
			row = sheet.getRow(++rowNum);
		}

		return teacherArray;
	}
	
	
	
	
	
}
