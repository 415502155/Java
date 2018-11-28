package sng.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelImport {
	private final static String Excel_2003 = ".xls"; // 2003 版本的excel
	private final static String Excel_2007 = ".xlsx"; // 2007 版本的excel

	/**
	 * @param in
	 * @param fileName
	 * @param columNum
	 * 自定义列数
	 * @return
	 */
	public List<List<Object>> getListByExcel(InputStream in, String fileName) throws Exception {
		List<List<Object>> list = null;

		// 创建Excel工作簿
		Workbook work = this.getWorkbook(in, fileName);
		if (work == null) {
			throw new Exception("创建Excel工作簿为空！");
		}
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;

		list = new ArrayList<List<Object>>();
		// 遍历Excel中的所有sheet
		for (int i = 0; i < work.getNumberOfSheets(); i++) {
			sheet = work.getSheetAt(i);
			if (sheet == null) {
				continue;
			}
			// 遍历当前sheet中的所有行
			// int totalRow = sheet.getPhysicalNumberOfRows();//如果excel有格式，这种方式取值不准确
			int totalRow = sheet.getPhysicalNumberOfRows();
			for (int j = sheet.getFirstRowNum(); j < totalRow; j++) {
				row = sheet.getRow(j);
				if (row != null && !"".equals(row)) {
					// 获取第一个单元格的数据是否存在
					Cell fristCell = row.getCell(0);
					if (fristCell != null) {
						// 遍历所有的列
						List<Object> li = new ArrayList<Object>();
						// int totalColum = row.getLastCellNum();
						for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
							cell = row.getCell(y);
							String callCal = this.getCellValue(cell) + "";
							li.add(callCal);
						}
						list.add(li);
					}
				}

			}
		}
		in.close();
		return list;
	}

	/**
	 * 描述：根据文件后缀，自动适应上传文件的版本
	 * 
	 * @param inStr,fileName
	 * @return
	 * @throws Exception
	 */
	public Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
		Workbook work = null;
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		if (Excel_2003.equals(fileType)) {
			work = new HSSFWorkbook(inStr);// 2003 版本的excel
		} else if (Excel_2007.equals(fileType)) {
			work = new XSSFWorkbook(inStr);// 2007 版本的excel
		} else {
			throw new Exception("解析文件格式有误！");
		}
		return work;
	}

	/**
	 * 描述：对表格中数值进行格式化
	 * 
	 * @param cell
	 * @return
	 */
	public Object getCellValue(Cell cell) {
		Object value = null;
		DecimalFormat df1 = new DecimalFormat("0");// 格式化number，string字符
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");// 日期格式化
		DecimalFormat df2 = new DecimalFormat("0.00");// 格式化数字
		if (cell != null && !"".equals(cell)) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				value = cell.getRichStringCellValue().getString();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if ("General".equals(cell.getCellStyle().getDataFormatString())) {
					value = df1.format(cell.getNumericCellValue());
				} else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
					value = sdf.format(cell.getDateCellValue());
				} else if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					value = sdf.format(date);
				} else {
					value = df2.format(cell.getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				value = cell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_BLANK:
				value = "";
				break;
			default:
				break;
			}
		}
		return value;
	}

	public String getFormat(String str) {
		if (str.equals("null")) {
			str = "";
			return str;
		} else {
			return str;
		}
	}

	public Integer getFormats(Integer str) {
		if (str == null) {
			str = 0;
			return str;
		} else {
			return str;
		}
	}
	
	/**
	 * @MethodName : excelToList
	 * @Description : 将Excel转化为List
	 * @param in : 输入流
	 * @param sheetIndex ：要导入的工作表序号
	 * @param entityClass ：实体class
	 * @param fieldMap ：Excel中的中文列头和类的英文属性的对应关系Map
	 */
	/*public static <T> List<T> excelToList(InputStream in, String sheetName, Class<T> entityClass, LinkedHashMap<String, String> fieldMap) {
		// 定义要返回的list
		List<T> resultList = new ArrayList<T>();
		try {
			// 根据Excel数据源创建WorkBook
			jxl.Workbook wb = jxl.Workbook.getWorkbook(in);
			// 获取工作表
//			Sheet sheet = wb.getSheet(sheetName);
			jxl.Sheet sheet = wb.getSheet(0);
			// 获取工作表的有效行数
			int realRows = 0;
			for (int i = 0; i < sheet.getRows(); i++) {

				int nullCols = 0;
				for (int j = 0; j < sheet.getColumns(); j++) {
					jxl.Cell currentCell = sheet.getCell(j, i);
					if (currentCell == null
							|| "".equals(currentCell.getContents().toString())) {
						nullCols++;
					}
				}
				if (nullCols == sheet.getColumns()) {
					break;
				} else {
					realRows++;
				}
			}
			// 如果Excel中没有数据则提示错误
			if (realRows <= 1) {
				throw new Exception("Excel文件中没有任何数据");
			}
			jxl.Cell[] firstRow = sheet.getRow(0);
			String[] excelFieldNames = new String[firstRow.length];
			// 获取Excel中的列名
			for (int i = 0; i < firstRow.length; i++) {
				excelFieldNames[i] = firstRow[i].getContents().toString().trim();
			}
			// 判断需要的字段在Excel中是否都存在
			boolean isExist = true;
			List<String> excelFieldList = Arrays.asList(excelFieldNames);
			for (String cnName : fieldMap.keySet()) {
				if (!excelFieldList.contains(cnName)) {
					isExist = false;
					break;
				}
			}
			// 如果有列名不存在，则抛出异常，提示错误
			if (!isExist) {
				throw new Exception("Excel中缺少必要的字段，或字段名称有误");
			}
			// 将列名和列号放入Map中,这样通过列名就可以拿到列号
			LinkedHashMap<String, Integer> colMap = new LinkedHashMap<String, Integer>();
			for (int i = 0; i < excelFieldNames.length; i++) {
				colMap.put(excelFieldNames[i], firstRow[i].getColumn());
			}
			// 将sheet转换为list
			for (int i = 1; i < realRows; i++) {
				// 新建要转换的对象
				T entity = entityClass.newInstance();
				// 给对象中的字段赋值
				for (Entry<String, String> entry : fieldMap.entrySet()) {
					// 获取中文字段名
					String cnNormalName = entry.getKey();
					// 获取英文字段名
					String enNormalName = entry.getValue();
					// 根据中文字段名获取列号
					int col = colMap.get(cnNormalName);
					// 获取当前单元格中的内容
					String content = sheet.getCell(col, i).getContents().toString().trim();
					ReflectUtil.setProperty(entity, enNormalName, content);
				}
				resultList.add(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}*/
	
	
	/***
	 * poi 导入 返回list<class>
	 */
	 /** 
     * poi读取excle 生成实体集合 
     * @param <E> 
     * @return 
     */  
    private static List<String> columns;//要解析excel中的列名  
    private static int sheetNum = 0;//要解析的sheet下标 
    public static <E> List<E> readExcel(InputStream inStream,Class<E> clazz){  //(File file,Class<E> clazz)
  
        //InputStream inStream = null;  
        List<E> eList = new ArrayList<E>();  
        try {  
            //inStream = new FileInputStream(file);  
            HSSFWorkbook workbook = new HSSFWorkbook(inStream);  
            HSSFSheet sheet = workbook.getSheetAt(sheetNum);//获得表  
            int lastRowNum = sheet.getLastRowNum();//最后一行  
            for(int i = 1 ;i < lastRowNum;i++){  
                HSSFRow row = sheet.getRow(i);//获得行  
                String rowJson = readExcelRow(row);  
                E _e = JsonUtil.getObjectFromJson(rowJson, clazz);
                eList.add(_e);  
            }  
        } catch (Exception e) {  
            try {  
                //inStream = new FileInputStream(file);  
                XSSFWorkbook workbook = new XSSFWorkbook(inStream);  
                XSSFSheet sheet = workbook.getSheetAt(sheetNum);  
                int lastRowNum = sheet.getLastRowNum();//最后一行  
                for(int i = 1 ;i < lastRowNum;i++){  
                    XSSFRow row = sheet.getRow(i);//获得行  
                    String rowJson = readExcelRow(row);  
                    E _e = JsonUtil.getObjectFromJson(rowJson, clazz);
                    eList.add(_e);  
                }  
            } catch (Exception e1) {  
                e1.printStackTrace();  
            }  
        }finally{  
            close(null,inStream);  
        }  
        return eList;  
    }
    
    /** 
     * 将json转换为Bean实例 
     * @param <E> 
     * @return 
     */  
    /*private static <E> E json2Bean(String json,Class<E> clazz){  
        JSONObject jsonObj = JSONObject.fromObject(json);  
        Method[] methods = clazz.getDeclaredMethods();  
        try {  
            E _e = clazz.newInstance();  
            for(Method m:methods){  
                String name = m.getName();  
                if(name.startsWith("set")){  
                    String keyPrev = name.substring(3,4).toLowerCase();  
                    String keyNext = name.substring(4);  
                    String val = "";  
                    try {  
                        val = jsonObj.getString(keyPrev+keyNext);  
                    } catch (Exception e) {  
                        val = "";  
                    }  
                    m.invoke(_e, val);  
                }  
            }  
            return _e;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    } */
    
    /** 
     * 读取行值 
     * @return 
     */  
    private static String readExcelRow(HSSFRow row){  
        StringBuilder rowJson = new StringBuilder();  
        int lastCellNum = ExcelImport.columns.size();//最后一个单元格  
        rowJson.append("{");  
        for(int i = 0 ;i<lastCellNum;i++){  
            HSSFCell cell = row.getCell(i);  
            String cellVal = readCellValue(cell);  
            rowJson.append(toJsonItem(columns.get(i), cellVal));  
            if(i<lastCellNum-1)  
                rowJson.append(",");  
        }  
        rowJson.append("}");  
        return rowJson.toString();  
    }  
    /** 
     * 读取行值 
     * @return 
     */  
    private static String readExcelRow(XSSFRow row){  
        StringBuilder rowJson = new StringBuilder();  
        int lastCellNum = ExcelImport.columns.size();//最后一个单元格  
        rowJson.append("{");  
        for(int i = 0 ;i<lastCellNum;i++){  
            XSSFCell cell = row.getCell(i);  
            String cellVal = readCellValue(cell);  
            rowJson.append(toJsonItem(columns.get(i), cellVal));  
            if(i<lastCellNum-1)  
                rowJson.append(",");  
        }  
        rowJson.append("}");  
        return rowJson.toString();  
    }  
  
    /** 
     * 读取单元格的值 
     * @param hssfCell 
     * @return 
     */  
    @SuppressWarnings("static-access")  
    private static String readCellValue(HSSFCell hssfCell) {  
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {  
            return String.valueOf(hssfCell.getBooleanCellValue());  
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {  
            hssfCell.setCellType(1);//设置为String  
            String str_temp = String.valueOf(hssfCell.getRichStringCellValue());//得到值  
            return str_temp;  
        }else if(hssfCell.getCellType() == hssfCell.CELL_TYPE_FORMULA){  
            int val = hssfCell.getCachedFormulaResultType();  
            return val+"";  
        } else {  
            return String.valueOf(hssfCell.getRichStringCellValue());  
        }  
    }  
    /** 
     * 读取单元格的值 
     * @param hssfCell 
     * @return 
     */  
    @SuppressWarnings("static-access")  
    private static String readCellValue(XSSFCell sssfCell) {  
        if (sssfCell.getCellType() == sssfCell.CELL_TYPE_BOOLEAN) {  
            return String.valueOf(sssfCell.getBooleanCellValue());  
        } else if (sssfCell.getCellType() == sssfCell.CELL_TYPE_NUMERIC) {  
            sssfCell.setCellType(1);//设置为String  
            String str_temp = String.valueOf(sssfCell.getRichStringCellValue());//得到值  
            return str_temp;  
        }else if(sssfCell.getCellType() == sssfCell.CELL_TYPE_FORMULA){  
            int val = sssfCell.getCachedFormulaResultType();  
            return val+"";  
        }else {  
            return String.valueOf(sssfCell.getRichStringCellValue());  
        }  
    }  
    /** 
     * 转换为json对 
     * @return 
     */  
    private static String toJsonItem(String name,String val){  
        return "\""+name+"\":\""+val+"\"";  
    }  
    /** 
     * 关闭io流 
     * @param fos 
     * @param fis 
     */  
    private static void close(OutputStream out, InputStream in) {  
        if (in != null) {  
            try {  
                in.close();  
            } catch (IOException e) {  
                System.out.println("InputStream关闭失败");  
                e.printStackTrace();  
            }  
        }  
        if (out != null) {  
            try {  
                out.close();  
            } catch (IOException e) {  
                System.out.println("OutputStream关闭失败");  
                e.printStackTrace();  
            }  
        }  
    }

	public static List<String> getColumns() {
		return columns;
	}

	public static void setColumns(List<String> columns) {
		ExcelImport.columns = columns;
	}

	public static int getSheetNum() {
		return sheetNum;
	}

	public static void setSheetNum(int sheetNum) {
		ExcelImport.sheetNum = sheetNum;
	}  
    
    
}
