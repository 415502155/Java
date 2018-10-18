package tk.mybatis.springboot.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 投注机信息
 */
//@Entity
//@Table(name = "t_tmn_info")
public class t_tmn_info implements Serializable {

    private static final long serialVersionUID = -7368010059937151297L;
    private Integer terminal_serial_no;//投注机序号
//    @Id
//    @Column(name = "terminal_id", unique = false, nullable = false)
    private Integer terminal_id;//投注机号
    private Integer terminal_phy_id;//物理卡号
    private Integer terminal_initial_time;//投注机初始化时间
    private String safe_card_id;//加密芯片
    private Integer city_id;//地市编号
    private Integer district_id;//所属区县
    private String station_name;//站点名称
    private String terminal_address;//站点地址
    private String station_phone;//站点电话
    private String owner_name;//户主名
    private String owner_phone;//负责人电话
    private String linkman;//联系人
    private String linkman_phone;//联系人电话
    private Date regist_date;//注册时间
    private Integer software_id;//软件id
    private Integer upgrade_mark;//升级标记
    private String software_version;//软件版本
    private Integer terminal_type;//终端类型
    private Integer terminal_status;//终端状态
    private Integer agentfee_type;//代销费内扣方式
    private Integer tmn_sale_deduct;//销售注销实时扣款
    private Integer tmn_cash_deduct;//兑奖实时扣款
    private Integer comm_type;//通讯方式
    private String dial_name;//拨号帐户
    private String dial_pwd;//拨号密码
    private String account_id;//默认扣款帐户
    private String dealer_id;//代销商编号
    private Integer terminal_syn_no;//终端同步字
    private String terminal_value_str;//终端设备使用价值
    private BigDecimal terminal_value;//终端设备使用价值
    private String local_terminal_id;//本地终端编号    
    private String public_key;//通讯密钥
    private Integer notice_syn_no;//终端新通知
    private Integer station_rank;//站点等级
    private Integer download_comm;//下载通讯参数

    //终端信息列表展示页面中，显示名称列所用字段
    private String city_name;//城市名称
    private String district_name;//区县名称
    private String software_name;//软件名称

//    private Integer province_id;//省编号
    public String getLinkman_phone() {
        return linkman_phone;
    }

    public void setLinkman_phone(String linkman_phone) {
        this.linkman_phone = linkman_phone;
    }

    public Integer getTerminal_serial_no() {
        return terminal_serial_no;
    }

    public void setTerminal_serial_no(Integer terminal_serial_no) {
        this.terminal_serial_no = terminal_serial_no;
    }

    public Integer getTerminal_id() {
        return terminal_id;
    }

    public void setTerminal_id(Integer terminal_id) {
        this.terminal_id = terminal_id;
    }

    public Integer getTerminal_phy_id() {
        return terminal_phy_id;
    }

    public void setTerminal_phy_id(Integer terminal_phy_id) {
        this.terminal_phy_id = terminal_phy_id;
    }

    public Integer getTerminal_initial_time() {
        return terminal_initial_time;
    }

    public void setTerminal_initial_time(Integer terminal_initial_time) {
        this.terminal_initial_time = terminal_initial_time;
    }

    public String getSafe_card_id() {
        return safe_card_id;
    }

    public void setSafe_card_id(String safe_card_id) {
        this.safe_card_id = safe_card_id;
    }

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

    public Integer getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(Integer district_id) {
        this.district_id = district_id;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getTerminal_address() {
        return terminal_address;
    }

    public void setTerminal_address(String terminal_address) {
        this.terminal_address = terminal_address;
    }

    public String getStation_phone() {
        return station_phone;
    }

    public void setStation_phone(String station_phone) {
        this.station_phone = station_phone;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getOwner_phone() {
        return owner_phone;
    }

    public void setOwner_phone(String owner_phone) {
        this.owner_phone = owner_phone;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public Date getRegist_date() {
        return regist_date;
    }

    public void setRegist_date(Date regist_date) {
        this.regist_date = regist_date;
    }

    public Integer getSoftware_id() {
        return software_id;
    }

    public void setSoftware_id(Integer software_id) {
        this.software_id = software_id;
    }

    public Integer getUpgrade_mark() {
        return upgrade_mark;
    }

    public void setUpgrade_mark(Integer upgrade_mark) {
        this.upgrade_mark = upgrade_mark;
    }

    public String getSoftware_version() {
        return software_version;
    }

    public void setSoftware_version(String software_version) {
        this.software_version = software_version;
    }

    public Integer getTerminal_type() {
        return terminal_type;
    }

    public void setTerminal_type(Integer terminal_type) {
        this.terminal_type = terminal_type;
    }

    public Integer getTerminal_status() {
        return terminal_status;
    }

    public void setTerminal_status(Integer terminal_status) {
        this.terminal_status = terminal_status;
    }

    public Integer getAgentfee_type() {
        return agentfee_type;
    }

    public void setAgentfee_type(Integer agentfee_type) {
        this.agentfee_type = agentfee_type;
    }

    public Integer getTmn_sale_deduct() {
        return tmn_sale_deduct;
    }

    public void setTmn_sale_deduct(Integer tmn_sale_deduct) {
        this.tmn_sale_deduct = tmn_sale_deduct;
    }

    public Integer getTmn_cash_deduct() {
        return tmn_cash_deduct;
    }

    public void setTmn_cash_deduct(Integer tmn_cash_deduct) {
        this.tmn_cash_deduct = tmn_cash_deduct;
    }

    public Integer getComm_type() {
        return comm_type;
    }

    public void setComm_type(Integer comm_type) {
        this.comm_type = comm_type;
    }

    public String getDial_name() {
        return dial_name;
    }

    public void setDial_name(String dial_name) {
        this.dial_name = dial_name;
    }

    public String getDial_pwd() {
        return dial_pwd;
    }

    public void setDial_pwd(String dial_pwd) {
        this.dial_pwd = dial_pwd;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(String dealer_id) {
        this.dealer_id = dealer_id;
    }

    public Integer getTerminal_syn_no() {
        return terminal_syn_no;
    }

    public void setTerminal_syn_no(Integer terminal_syn_no) {
        this.terminal_syn_no = terminal_syn_no;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

//    public Integer getProvince_id() {
//        return province_id;
//    }
//
//    public void setProvince_id(Integer province_id) {
//        this.province_id = province_id;
//    }
    public String getSoftware_name() {
        return software_name;
    }

    public void setSoftware_name(String software_name) {
        this.software_name = software_name;
    }

    public String getTerminal_value_str() {
        return terminal_value_str;
    }

    public void setTerminal_value_str(String terminal_value_str) {
        this.terminal_value_str = terminal_value_str;
    }

    public BigDecimal getTerminal_value() {
        return terminal_value;
    }

    public void setTerminal_value(BigDecimal terminal_value) {
        this.terminal_value = terminal_value;
    }

    public String getLocal_terminal_id() {
        return local_terminal_id;
    }

    public void setLocal_terminal_id(String local_terminal_id) {
        this.local_terminal_id = local_terminal_id;
    }

    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    public Integer getNotice_syn_no() {
        return notice_syn_no;
    }

    public void setNotice_syn_no(Integer notice_syn_no) {
        this.notice_syn_no = notice_syn_no;
    }

    public Integer getStation_rank() {
        return station_rank;
    }

    public void setStation_rank(Integer station_rank) {
        this.station_rank = station_rank;
    }

    public Integer getDownload_comm() {
        return download_comm;
    }

    public void setDownload_comm(Integer download_comm) {
        this.download_comm = download_comm;
    }

}
