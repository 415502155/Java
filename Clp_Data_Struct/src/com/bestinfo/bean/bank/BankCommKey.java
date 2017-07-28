package com.bestinfo.bean.bank;

import java.io.Serializable;

/**
 * 信息-银行通讯密钥表
 * @author zyk
 */
public class BankCommKey implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 2301779458797802214L;
	private String bank_id;        //银行编号
    private String Encrypt_type;   //加密方式
    private String system_key;     //系统密钥
    private String system_pub_key; //系统公钥
    private String bank_pub_key;   //银行公钥
    private Integer work_status;   //使用状态

    /**
     * @return the bank_id
     */
    public String getBank_id() {
        return bank_id;
    }

    /**
     * @param bank_id the bank_id to set
     */
    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    /**
     * @return the Encrypt_type
     */
    public String getEncrypt_type() {
        return Encrypt_type;
    }

    /**
     * @param Encrypt_type the Encrypt_type to set
     */
    public void setEncrypt_type(String Encrypt_type) {
        this.Encrypt_type = Encrypt_type;
    }

    /**
     * @return the system_key
     */
    public String getSystem_key() {
        return system_key;
    }

    /**
     * @param system_key the system_key to set
     */
    public void setSystem_key(String system_key) {
        this.system_key = system_key;
    }

    /**
     * @return the system_pub_key
     */
    public String getSystem_pub_key() {
        return system_pub_key;
    }

    /**
     * @param system_pub_key the system_pub_key to set
     */
    public void setSystem_pub_key(String system_pub_key) {
        this.system_pub_key = system_pub_key;
    }

    /**
     * @return the bank_pub_key
     */
    public String getBank_pub_key() {
        return bank_pub_key;
    }

    /**
     * @param bank_pub_key the bank_pub_key to set
     */
    public void setBank_pub_key(String bank_pub_key) {
        this.bank_pub_key = bank_pub_key;
    }

    /**
     * @return the work_status
     */
    public Integer getWork_status() {
        return work_status;
    }

    /**
     * @param work_status the work_status to set
     */
    public void setWork_status(Integer work_status) {
        this.work_status = work_status;
    }

}
