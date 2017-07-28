package com.bestinfo.gambler.entity;


//import cn.com.bestinfo.protocols.message.AppResResult;
import com.bestinfo.protocols.message.AppResResult;
import java.util.List;

/**
 *
 * @author zhen
 */
public class AgentSerialReqRes {

    @Override
    public String toString() {
        return "AgentSerialReqRes{" + "appResResult=" + appResResult + ", gambler_num=" + gambler_num + ", applySerials=" + applySerials + '}';
    }

    public AgentSerialReqRes() {
    }

    public AgentSerialReqRes(AppResResult appResResult, int gambler_num, List<ApplySerial> applySerials) {
        this.appResResult = appResResult;
        this.gambler_num = gambler_num;
        this.applySerials = applySerials;
    }
    //返回结果描述
    private AppResResult appResResult;

    public AppResResult getAppResResult() {
        return appResResult;
    }

    public void setAppResResult(AppResResult appResResult) {
        this.appResResult = appResResult;
    }
    //彩民个数
    private int gambler_num;

    public int getGambler_num() {
        return gambler_num;
    }

    public void setGambler_num(int gambler_num) {
        this.gambler_num = gambler_num;
    }
    private List<ApplySerial> applySerials;

    public List<ApplySerial> getApplySerials() {
        return applySerials;
    }

    public void setApplySerials(List<ApplySerial> applySerials) {
        this.applySerials = applySerials;
    }

    //内部类
    public class ApplySerial {

        @Override
        public String toString() {
            return "ApplySerial{" + "gambler_name=" + gambler_name + ", num=" + num + ", serial_startNo=" + serial_startNo + ", serial_endNo=" + serial_endNo + '}';
        }

        public ApplySerial() {
        }

        public ApplySerial(String gambler_name, int num, String serial_startNo, String serial_endNo) {
            this.gambler_name = gambler_name;
            this.num = num;
            this.serial_startNo = serial_startNo;
            this.serial_endNo = serial_endNo;
        }
        //用户名
        private String gambler_name;
        //序列号个数
        private int num;
        //起始序列号字符串
        private String serial_startNo;
        //结束序列号字符串
        private String serial_endNo;

        public String getGambler_name() {
            return gambler_name;
        }

        public void setGambler_name(String gambler_name) {
            this.gambler_name = gambler_name;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getSerial_startNo() {
            return serial_startNo;
        }

        public void setSerial_startNo(String serial_startNo) {
            this.serial_startNo = serial_startNo;
        }

        public String getSerial_endNo() {
            return serial_endNo;
        }

        public void setSerial_endNo(String serial_endNo) {
            this.serial_endNo = serial_endNo;
        }
    }
}
