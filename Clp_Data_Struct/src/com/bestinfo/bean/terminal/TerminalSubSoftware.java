package com.bestinfo.bean.terminal;

import java.io.Serializable;
import java.util.Date;

/**
 * 信息-终端软件分包信息表
 *
 * @author hhhh
 */
public class TerminalSubSoftware implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7290597871807936597L;
	/**
     * 软件编号
     */
    private Integer software_id;
    /**
     * 子包编号
     */
    private Integer sub_id;
    /**
     * 子包名称
     */
    private String sub_package_name;
    /**
     * 子包版本
     */
    private String sub_package_version;
    /**
     * 软件尺寸
     */
    private Integer package_size;
    /**
     * 发布时间
     */
    private Date release_time;
    /**
     * 软件目录
     */
    private String package_dir;
    /**
     * 使用状态
     */
    private Integer work_status;
    /**
     * 是否主程序
     */
    private Integer main_app;

    public Integer getSoftware_id() {
        return software_id;
    }

    public void setSoftware_id(Integer software_id) {
        this.software_id = software_id;
    }

    public Integer getSub_id() {
        return sub_id;
    }

    public void setSub_id(Integer sub_id) {
        this.sub_id = sub_id;
    }

    public String getSub_package_name() {
        return sub_package_name;
    }

    public void setSub_package_name(String sub_package_name) {
        this.sub_package_name = sub_package_name;
    }

    public String getSub_package_version() {
        return sub_package_version;
    }

    public void setSub_package_version(String sub_package_version) {
        this.sub_package_version = sub_package_version;
    }

    public Integer getPackage_size() {
        return package_size;
    }

    public void setPackage_size(Integer package_size) {
        this.package_size = package_size;
    }

    public Date getRelease_time() {
        return release_time;
    }

    public void setRelease_time(Date release_time) {
        this.release_time = release_time;
    }

    public String getPackage_dir() {
        return package_dir;
    }

    public void setPackage_dir(String package_dir) {
        this.package_dir = package_dir;
    }

    public Integer getWork_status() {
        return work_status;
    }

    public void setWork_status(Integer work_status) {
        this.work_status = work_status;
    }

    public Integer getMain_app() {
        return main_app;
    }

    public void setMain_app(Integer main_app) {
        this.main_app = main_app;
    }

}
