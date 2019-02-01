package com.shihy.springboot.utils;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class EasyExcelUtil {
	
	public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass,String fileName,boolean isCreateHeader, HttpServletRequest request, HttpServletResponse response){
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, request, response, exportParams);

    }
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass,String fileName, HttpServletRequest request, HttpServletResponse response){
        defaultExport(list, pojoClass, fileName, request, response, new ExportParams(title, sheetName));
    }
    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletRequest request, HttpServletResponse response){
        defaultExport(list, fileName, request, response);
    }

    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletRequest request, HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,pojoClass,list);
        if (workbook != null);
        downLoadExcel(fileName, request, response, workbook);
    }
    /***
     * 
     * @Description: EXCEL下载
     * @param fileName 文件名称
     * @param request
     * @param response
     * @param workbook   
     * @return void  
     * @throws @throws
     */
    @Autowired
	private static void downLoadExcel(String fileName, HttpServletRequest request, HttpServletResponse response, Workbook workbook) {
        try {
        	String agent = request.getHeader("USER-AGENT");
    		response.setContentType("application/x-download");
    		response.setCharacterEncoding("UTF-8");
    		response.setHeader("content-Type", "application/vnd.ms-excel");
    		if(agent!=null && (agent.indexOf("Firefox")>-1 || agent.indexOf("Safari")>-1)){
    			response.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes("UTF-8"),"ISO8859-1"));
    		}else{
    			response.setHeader("Content-disposition", "attachment; filename="+ URLEncoder.encode(fileName, "UTF-8"));
    		}
            /*response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));*/
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
           log.info("downLoadExcel IOException :" + e);
        }
    }
    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletRequest request, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null);
        downLoadExcel(fileName, request, response, workbook);
    }

    public static <T> List<T> importExcel(String filePath,Integer titleRows,Integer headerRows, Class<T> pojoClass){
        if (StringUtils.isBlank(filePath)){
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        }catch (NoSuchElementException e){
            log.info("模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
        return list;
    }
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass){
        if (file == null){
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        }catch (NoSuchElementException e){
            log.info("excel文件不能为空");
        } catch (Exception e) {
        	log.info(e.getMessage());
        }
        return list;
    }

}
