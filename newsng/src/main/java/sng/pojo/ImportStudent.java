package sng.pojo;

import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImportStudent extends BaseRowModel implements Serializable{

	/**
	 * 导入  学员 实体
	 */
	private static final long serialVersionUID = 1L;
	@ExcelProperty(index = 0)
	private String clas_name;
	@ExcelProperty(index = 1)
	private String stud_name;
	@ExcelProperty(index = 2)
	private String user_idnumber;
	@ExcelProperty(index = 3)
	private String stu_status;

	private String err_msg;

	public String getClas_name() {
		return clas_name;
	}

	public void setClas_name(String clas_name) {
		this.clas_name = clas_name;
	}

	public String getStud_name() {
		return stud_name;
	}

	public void setStud_name(String stud_name) {
		this.stud_name = stud_name;
	}

	public String getUser_idnumber() {
		return user_idnumber;
	}

	public void setUser_idnumber(String user_idnumber) {
		this.user_idnumber = user_idnumber;
	}

	public String getStu_status() {
		return stu_status;
	}

	public void setStu_status(String stu_status) {
		this.stu_status = stu_status;
	}

	public String getErr_msg() {
		return err_msg;
	}

	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
