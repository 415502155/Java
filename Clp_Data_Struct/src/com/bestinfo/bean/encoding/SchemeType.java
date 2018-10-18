package com.bestinfo.bean.encoding;

import java.io.Serializable;

/**
 *
 * @author hhhh
 */
public class SchemeType implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -323148539490895306L;
	private Integer scheme_type;//方案类型编号
    private String scheme_type_name;//方案类型名称
    private Integer work_status;//工作状态

    public Integer getScheme_type() {
        return scheme_type;
    }

    public void setScheme_type(Integer scheme_type) {
        this.scheme_type = scheme_type;
    }

    public String getScheme_type_name() {
        return scheme_type_name;
    }

    public void setScheme_type_name(String scheme_type_name) {
        this.scheme_type_name = scheme_type_name;
    }

    public Integer getWork_status() {
        return work_status;
    }

    public void setWork_status(Integer work_status) {
        this.work_status = work_status;
    }

}
