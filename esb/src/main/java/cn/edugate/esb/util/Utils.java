package cn.edugate.esb.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Element;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

//import cn.edugate.api.client.ResourcesAccess;
import cn.edugate.esb.comparator.MapKeyComparator;
import cn.edugate.esb.entity.Grade;

public class Utils {
	public final static String MD5(String s) {
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < md.length; i++) {
				int val = ((int) md[i]) & 0xff;
				if (val < 16)
					sb.append("0");
				sb.append(Integer.toHexString(val));
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

	public static String getFirstPath(String path) {
		String ret = path;
		if (!"/".equals(path)) {
			int start = path.indexOf('/');
			int end = path.indexOf('/', 1);
			String name = ret.substring(start + 1, end);
			ret = "/" + name + "/";
		}
		return ret;
	}

	public static String streamToString(InputStream inputStream) throws IOException {
		Writer writer = new StringWriter();
		byte[] b = new byte[4096];
		for (int n; (n = inputStream.read(b)) != -1;) {
			writer.append(new String(b, 0, n));
		}
		return writer.toString();
	}

	public static <T> T getPOJOFromXml(Element e, Class<T> pojoClass) throws Exception {
		T t = pojoClass.newInstance();
		if (e != null) {
			for (Object po : e.elements()) {
				Element pe = (Element) po;
				String name = pe.attributeValue("name");
				String value = pe.attributeValue("value");
				if (name != null && value != null && !"".equals(name.trim())) {
					name = name.trim();
					value = value.trim();
					String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
					Method[] methods = t.getClass().getMethods();
					Method method = null;
					for (int i = 0; i < methods.length; i++) {
						if (methods[i].getName().equals(methodName)) {
							method = methods[i];
							break;
						}
					}
					if (method != null) {
						if (method.getParameterTypes().length == 1) {
							@SuppressWarnings("rawtypes")
							Class c = method.getParameterTypes()[0];
							Object[] args = new Object[1];
							if (c == Double.class) {
								args[0] = Double.parseDouble(value);
							} else if (c == int.class) {
								args[0] = Integer.parseInt(value);
							} else if (c == Long.class) {
								args[0] = Long.parseLong(value);
							} else if (c == boolean.class) {
								args[0] = Boolean.parseBoolean(value);
							} else {
								args[0] = value;
							}
							method.invoke(t, args);
						}
					}
				}
			}
		}
		return t;
	}

	/**
	 * 使用 Map按key进行排序
	 * 
	 * @param <T>
	 * @param map
	 * @return
	 */
	public static <T> Map<String, T> sortMapByKey(Map<String, T> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}

		Map<String, T> sortMap = new TreeMap<String, T>(new MapKeyComparator());

		sortMap.putAll(map);

		return sortMap;
	}	

	public static String makecode() {
		// String str = "abcdefghijkmnopqrstuvwsyzABCDEFGHIJKMNOPQRSTUVWSYZ0123456789";
		String str = "0123456789";
		String code = "";
		for (int i = 0; i < 4; i++) {
			Random rand = new Random();
			int j = rand.nextInt(str.length());
			code += str.substring(j, j + 1);
		}
		return code;
	}

	/**
	 * 根据组织ID和上传的文件，进行组织的Logo上传
	 * @param orgID
	 * @param file
	 * @return
	 */
/*	public static String uploadOrgLogo(String orgID, MultipartFile file) {
		ResourcesAccess resourceAccess = new ResourcesAccess("http://oa.edugate.cn/web.resources/api/");
		return resourceAccess.uploadLogo(orgID, file);
	}*/
	
	/**
	 * 根据组织ID获取机构Logo的显示路径
	 * @param orgID
	 * @return
	 */
/*	public static String getDisplayURL4OrgLogo(String orgID) {
		ResourcesAccess resourceAccess = new ResourcesAccess("http://oa.edugate.cn/web.resources/api/");
		return resourceAccess.getLogoFilePath(orgID);
	}*/
	
	/**
     * 获取map中第一个数据值
     *
     * @param <K> Key的类型
     * @param <V> Value的类型
     * @param map 数据源
     * @return 返回的值
     */
    public static <K, V> K getFirstOrNull(Map<K, V> map) {
        K obj = null;
        for (Entry<K, V> entry : map.entrySet()) {
            obj = entry.getKey();
            if (obj != null) {
                break;
            }
        }
        return obj;
    }
    
    public static void main(String[] args) {
    	Utils u = new Utils();
    	//List<Grade> newTypeGradeList = u.getGradeList4NewType(192, "1");
    	//System.out.println(newTypeGradeList.size());
    }
    
    /**
     * 根据年级类型来获取新增的三种年级类型实体
     * @param gradeType
     * @return
     */
	public static Grade getGrade4NewType(int orgId, int gradeType, int gradeNum) {
		Grade grade = null;

		if (orgId > 0 && gradeType > 0 && gradeNum > 0) {
			if (1 == gradeType) {
				if (10 == gradeNum) {
					grade = new Grade();
					grade.setOrg_id(orgId);
					grade.setGrade_name("幼儿园新生年级");
					grade.setGrade_type(1);
					grade.setGrade_number(10);
				} else if (18 == gradeNum) {
					grade = new Grade();
					grade.setOrg_id(orgId);
					grade.setGrade_name("幼儿园应届毕业");
					grade.setGrade_type(1);
					grade.setGrade_number(18);
				} else if (19 == gradeNum) {
					grade = new Grade();
					grade.setOrg_id(orgId);
					grade.setGrade_name("幼儿园往届毕业");
					grade.setGrade_type(1);
					grade.setGrade_number(19);
				}
			} else if (2 == gradeType) {
				if (20 == gradeNum) {
					grade = new Grade();
					grade.setOrg_id(orgId);
					grade.setGrade_name("小学新生年级");
					grade.setGrade_type(2);
					grade.setGrade_number(20);
				} else if (28 == gradeNum) {
					grade = new Grade();
					grade.setOrg_id(orgId);
					grade.setGrade_name("小学应届毕业");
					grade.setGrade_type(2);
					grade.setGrade_number(28);
				} else if (29 == gradeNum) {
					grade = new Grade();
					grade.setOrg_id(orgId);
					grade.setGrade_name("小学往届毕业");
					grade.setGrade_type(2);
					grade.setGrade_number(29);
				}
			} else if (3 == gradeType) {
				if (30 == gradeNum) {
					grade = new Grade();
					grade.setOrg_id(orgId);
					grade.setGrade_name("初中新生年级");
					grade.setGrade_type(3);
					grade.setGrade_number(30);
				} else if (38 == gradeNum) {
					grade = new Grade();
					grade.setOrg_id(orgId);
					grade.setGrade_name("初中应届毕业");
					grade.setGrade_type(3);
					grade.setGrade_number(38);
				} else if (39 == gradeNum) {
					grade = new Grade();
					grade.setOrg_id(orgId);
					grade.setGrade_name("初中往届毕业");
					grade.setGrade_type(3);
					grade.setGrade_number(39);
				}
			} else if (4 == gradeType) {
				if (40 == gradeNum) {
					grade = new Grade();
					grade.setOrg_id(orgId);
					grade.setGrade_name("高中新生年级");
					grade.setGrade_type(4);
					grade.setGrade_number(40);
				} else if (48 == gradeNum) {
					grade = new Grade();
					grade.setOrg_id(orgId);
					grade.setGrade_name("高中应届毕业");
					grade.setGrade_type(4);
					grade.setGrade_number(48);
				} else if (49 == gradeNum) {
					grade = new Grade();
					grade.setOrg_id(orgId);
					grade.setGrade_name("高中往届毕业");
					grade.setGrade_type(4);
					grade.setGrade_number(49);
				}
			}
		}

		return grade;
	}
    
    
    /**
     * 根据年级名称获取年级实体
     * @param gradeName
     * @return
     */
    public static Grade getGradeEntity(String gradeName) {
    	Grade grade = new Grade();
    	
    	grade.setGrade_name(gradeName);
    	if ("小小班".equals(gradeName)) {
    		grade.setGrade_type(1);
    		grade.setGrade_number(11);
    	} else if ("小班".equals(gradeName)) {
    		grade.setGrade_type(1);
    		grade.setGrade_number(12);
    	} else if ("中班".equals(gradeName)) {
    		grade.setGrade_type(1);
    		grade.setGrade_number(13);
    	} else if ("大班".equals(gradeName)) {
    		grade.setGrade_type(1);
    		grade.setGrade_number(14);
    	} else if ("学前班".equals(gradeName)) {
    		grade.setGrade_type(1);
    		grade.setGrade_number(15);
    	} else if ("一年级".equals(gradeName)) {
    		grade.setGrade_type(2);
    		grade.setGrade_number(21);
    	} else if ("二年级".equals(gradeName)) {
    		grade.setGrade_type(2);
    		grade.setGrade_number(22);
    	} else if ("三年级".equals(gradeName)) {
    		grade.setGrade_type(2);
    		grade.setGrade_number(23);
    	} else if ("四年级".equals(gradeName)) {
    		grade.setGrade_type(2);
    		grade.setGrade_number(24);
    	} else if ("五年级".equals(gradeName)) {
    		grade.setGrade_type(2);
    		grade.setGrade_number(25);
    	} else if ("六年级".equals(gradeName)) {
    		grade.setGrade_type(2);
    		grade.setGrade_number(26);
    	} else if ("七年级".equals(gradeName)) {
    		grade.setGrade_type(3);
    		grade.setGrade_number(32);
    	} else if ("八年级".equals(gradeName)) {
    		grade.setGrade_type(3);
    		grade.setGrade_number(33);
    	} else if ("九年级".equals(gradeName)) {
    		grade.setGrade_type(3);
    		grade.setGrade_number(34);
    	} else if ("高一".equals(gradeName)) {
    		grade.setGrade_type(4);
    		grade.setGrade_number(41);
    	} else if ("高二".equals(gradeName)) {
    		grade.setGrade_type(4);
    		grade.setGrade_number(42);
    	} else if ("高三".equals(gradeName)) {
    		grade.setGrade_type(4);
    		grade.setGrade_number(43);
    	} else if ("六年级（仅供部分学校使用）".equals(gradeName)) {
    		grade.setGrade_name("六年级");
    		grade.setGrade_type(3);
    		grade.setGrade_number(31);
    	} else {
    		grade = null;
    	}
    	return grade;
    }
	
    /**
     * 数据库保存时获取真实值
     * @param arg
     * @return
     */
    public static String getValue(String arg) {
		String result = StringUtils.isBlank(arg) ? null : arg.trim();
		return result;
	}
    
	public static String getPathById(Integer id) {
		String path = ((int) Math.floor(id / 10000) % 100) + File.separator;
		path += ((int) Math.floor(id / 100) % 100) + File.separator;
		return path;
	}

	public static Byte getSexByStr(String ssex) {
		// TODO Auto-generated method stub
		Byte sex = null;
		switch (ssex) {
		case "男":
			sex = (byte)0;
			break;
		case "女":
			sex = (byte)1;
			break;
		default:
			break;
		}
		return sex;
	}
	/**
	 * 亲子关系（0：父亲，1：母亲，2：爷爷，3：奶奶，4：外公，5：外婆，6：其他）
	 * @param irelation
	 * @return
	 */
	public static Integer getRelationByStr(String irelation) {
		// TODO Auto-generated method stub
		Integer relation = 6;
		switch (irelation) {
		case "父亲":
			relation = 0;
			break;
		case "母亲":
			relation = 1;
			break;
		case "爷爷":
			relation = 2;
			break;		
		case "奶奶":
			relation = 3;
			break;
		case "外公":
			relation = 4;
			break;
		case "外婆":
			relation = 5;
			break;
		default:
			relation = 6;
			break;
		}
		return relation;
	}

	/**
	 * 根据school.foundation中grade类型返回edugate_base中的grade
	 * school.foundation年级类型（NULL：未知；0：幼儿年级；1：小学低年级；2：初中年级；3：高中年级；4：特殊类；5：小学高年级；）
	 * @param gradeType
	 * @return
	 */
	public static List<Grade> addGradeByType(String gradeType) {
		List<Grade> list = new ArrayList<Grade>();
		if(gradeType.indexOf("0")!=-1){
			Grade grade11 = new Grade();
			grade11.setGrade_type(1);
			grade11.setGrade_number(11);
			grade11.setGrade_name("小小班");
			list.add(grade11);
			Grade grade12 = new Grade();
			grade12.setGrade_type(1);
			grade12.setGrade_number(12);
			grade12.setGrade_name("小班");
			list.add(grade12);
			Grade grade13 = new Grade();
			grade13.setGrade_type(1);
			grade13.setGrade_number(13);
			grade13.setGrade_name("中班");
			list.add(grade13);
			Grade grade14 = new Grade();
			grade14.setGrade_type(1);
			grade14.setGrade_number(14);
			grade14.setGrade_name("大班");
			list.add(grade14);
		}
		if(gradeType.indexOf("1")!=-1){
			Grade grade21 = new Grade();
			grade21.setGrade_type(2);
			grade21.setGrade_number(21);
			grade21.setGrade_name("一年级");
			list.add(grade21);
			Grade grade22 = new Grade();
			grade22.setGrade_type(2);
			grade22.setGrade_number(22);
			grade22.setGrade_name("二年级");
			list.add(grade22);
			Grade grade23 = new Grade();
			grade23.setGrade_type(2);
			grade23.setGrade_number(23);
			grade23.setGrade_name("三年级");
			list.add(grade23);
		}
		if(gradeType.indexOf("5")!=-1){
			Grade grade24 = new Grade();
			grade24.setGrade_type(2);
			grade24.setGrade_number(24);
			grade24.setGrade_name("四年级");
			list.add(grade24);
			Grade grade25 = new Grade();
			grade25.setGrade_type(2);
			grade25.setGrade_number(25);
			grade25.setGrade_name("五年级");
			list.add(grade25);
			Grade grade26 = new Grade();
			grade26.setGrade_type(2);
			grade26.setGrade_number(26);
			grade26.setGrade_name("六年级");
			list.add(grade26);
		}
		if(gradeType.indexOf("2")!=-1){
			Grade grade32 = new Grade();
			grade32.setGrade_type(3);
			grade32.setGrade_number(32);
			grade32.setGrade_name("七年级");
			list.add(grade32);
			Grade grade33 = new Grade();
			grade33.setGrade_type(3);
			grade33.setGrade_number(33);
			grade33.setGrade_name("八年级");
			list.add(grade33);
			Grade grade34 = new Grade();
			grade34.setGrade_type(3);
			grade34.setGrade_number(34);
			grade34.setGrade_name("九年级");
			list.add(grade34);
		}
		if(gradeType.indexOf("3")!=-1){
			Grade grade41 = new Grade();
			grade41.setGrade_type(4);
			grade41.setGrade_number(41);
			grade41.setGrade_name("高一");
			list.add(grade41);
			Grade grade42 = new Grade();
			grade42.setGrade_type(4);
			grade42.setGrade_number(42);
			grade42.setGrade_name("高二");
			list.add(grade42);
			Grade grade43 = new Grade();
			grade43.setGrade_type(4);
			grade43.setGrade_number(43);
			grade43.setGrade_name("高三");
			list.add(grade43);
		}
		return list;
	}
    
	
	/**
	 * 获取对象中属性值为null的属性名称
	 * @param source
	 * @return
	 */
	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			if (src.getPropertyDescriptor(pd.getName()) != null) {
				Method getMethod = pd.getReadMethod();// 获得get方法
				if (getMethod != null) {
					Object srcValue = src.getPropertyValue(pd.getName());
					if (srcValue == null) {
						emptyNames.add(pd.getName());
					} else if (srcValue instanceof CharSequence) {
						if (StringUtils.isBlank((String) srcValue)) {
							emptyNames.add(pd.getName());
						}
					}
				}
			}
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	public static void formatGradeType(Grade grade) {
		// TODO Auto-generated method stub
		String gradeName = grade.getGrade_name();
		if ("小小班".equals(gradeName)) {
    		grade.setGrade_type(1);
    		grade.setGrade_number(11);
    	} else if ("小班".equals(gradeName)) {
    		grade.setGrade_type(1);
    		grade.setGrade_number(12);
    	} else if ("中班".equals(gradeName)) {
    		grade.setGrade_type(1);
    		grade.setGrade_number(13);
    	} else if ("大班".equals(gradeName)) {
    		grade.setGrade_type(1);
    		grade.setGrade_number(14);
    	} else if ("学前班".equals(gradeName)) {
    		grade.setGrade_type(1);
    		grade.setGrade_number(15);
    	} else if ("一年级".equals(gradeName)) {
    		grade.setGrade_type(2);
    		grade.setGrade_number(21);
    	} else if ("二年级".equals(gradeName)) {
    		grade.setGrade_type(2);
    		grade.setGrade_number(22);
    	} else if ("三年级".equals(gradeName)) {
    		grade.setGrade_type(2);
    		grade.setGrade_number(23);
    	} else if ("四年级".equals(gradeName)) {
    		grade.setGrade_type(2);
    		grade.setGrade_number(24);
    	} else if ("五年级".equals(gradeName)) {
    		grade.setGrade_type(2);
    		grade.setGrade_number(25);
    	} else if ("六年级".equals(gradeName)) {
    		grade.setGrade_type(2);
    		grade.setGrade_number(26);
    	} else if ("七年级".equals(gradeName)) {
    		grade.setGrade_type(3);
    		grade.setGrade_number(32);
    	} else if ("八年级".equals(gradeName)) {
    		grade.setGrade_type(3);
    		grade.setGrade_number(33);
    	} else if ("九年级".equals(gradeName)) {
    		grade.setGrade_type(3);
    		grade.setGrade_number(34);
    	} else if ("高一".equals(gradeName)) {
    		grade.setGrade_type(4);
    		grade.setGrade_number(41);
    	} else if ("高二".equals(gradeName)) {
    		grade.setGrade_type(4);
    		grade.setGrade_number(42);
    	} else if ("高三".equals(gradeName)) {
    		grade.setGrade_type(4);
    		grade.setGrade_number(43);
    	} else if ("六年级（仅供部分学校使用）".equals(gradeName)) {
    		grade.setGrade_name("六年级");
    		grade.setGrade_type(3);
    		grade.setGrade_number(31);
    	}
	}
	
	
	
	/**
	 * 生成工作簿对象
	 * 
	 * @param arrSheetName sheet名称数组
	 * @param columnName 标题列名称数组
	 * @param extension 文件扩展名（xls或xlsx）
	 * @return 工作簿对象
	 * @throws Exception
	 */
	public static Workbook makeWorkbook(String sheetName, String[] columnName, List<String[]> excelData, String extension)
			throws Exception {
		try {
			// 创建一个EXCEL
			Workbook wb = null;
			if ("xls".equals(extension)) {
				wb = new HSSFWorkbook();
			} else {
				wb = new XSSFWorkbook();
			}

			// 创建数据格式
			DataFormat dataFmt = wb.createDataFormat();
			CellStyle cellStyle = wb.createCellStyle();
			cellStyle.setDataFormat(dataFmt.getFormat("@"));
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			// 得到默认页面上的Font
			Font font = wb.getFontAt((short) 0);
			font.setFontName("宋体");
			font.setFontHeightInPoints((short) 11);
			cellStyle.setFont(font);

			// 生成sheet页名称
			Sheet sheet = wb.createSheet(sheetName);
			// 创建标题行
			Row titleRow = sheet.createRow(0);
			// 写入标题行上的列名称
			Cell cell = null;
			for (int i = 0; i < columnName.length; i++) {
				cell = titleRow.createCell(i);
				cell.setCellValue(columnName[i]);
				cell.setCellStyle(cellStyle);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				sheet.setDefaultColumnStyle(i, cellStyle);
				sheet.setColumnWidth(i, columnName[i].getBytes().length * 500);// fangjian:字符集宽度等上到服务器上面后再测试(若是utf-8的话，会更宽一些)
			}

			// 如果有数据，则进行输出
			if (excelData != null && excelData.size() > 0) {
				for (int rowIndex = 0; rowIndex < excelData.size(); rowIndex++) {
					String[] rowData = excelData.get(rowIndex);
					Row exampleDataRow = sheet.createRow(rowIndex + 1);
					cell = null;
					for (int i = 0; i < rowData.length; i++) {
						cell = exampleDataRow.createCell(i);
						// 设置单元格类型为文本
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(rowData[i]);
						cell.setCellStyle(cellStyle);
					}
				}
			}
			return wb;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("生成工作簿对象时发生异常");
		}
	}
	
	
	/**
	 * 生成并输出Excel文件
	 * 
	 * @param response 响应对象
	 * @param workBook 工作簿对象
	 * @param fileName 输出文件名
	 * @throws Exception
	 * @author fangjian
	 */
	public static void makeAndExportExcelFile(HttpServletResponse response, Workbook workBook, String fileName) throws Exception {
		try {
			response.reset();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
			OutputStream fileOut = response.getOutputStream();
			workBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("生成工作簿对象时发生异常（IOException）");
		}
	}
	
	/**
	 * 获取excel文件
	 * @param request
	 * @return
	 */
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
}
