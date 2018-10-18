package tk.mybatis.springboot.util;

public enum ReturnMsg {

    SUCCESS("0", "成功"),
    SALE_TICKET_EXCEPTION("-666","sale ticket ex");
    private String code;
    private String msg;

    private ReturnMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
