package cn.edugate.esb.disruptor;

import java.util.List;
import cn.edugate.esb.pojo.TargetPojo;

public class SmsTask {

    private List<TargetPojo> targets;
    private String data;
	private String text;
	private String txtnum;
		private String type;
//    private Integer type;
    
	public String getTxtnum() {
		return txtnum;
	}

	public void setTxtnum(String txtnum) {
		this.txtnum = txtnum;
	}
	
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getData() {
		return data;
	}


	public void setData(String data) {
		this.data = data;
	}
    
    public List<TargetPojo> getTargets() {
		return targets;
	}


	public void setTargets(List<TargetPojo> targets) {
		this.targets = targets;
	}
//    public String getData() {
//		return data;
//	}
//
//
//	public void setData(String data) {
//		this.data = data;
//	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


//	public Integer getType() {
//		return type;
//	}
//
//
//	public void setType(Integer type) {
//		this.type = type;
//	}


	public SmsTask(List<TargetPojo> targets, String text){
    	this.targets = targets;
        this.text = text;
    }
	
	public SmsTask(String data, String text){
    	this.data = data;
        this.text = text;
    }
	
	public SmsTask(String data, String text,String type){
    	this.data = data;
        this.text = text;
        this.type = type;
    }
	public SmsTask(String data, String text,String type,String txtnum){
    	this.data = data;
        this.text = text;
        this.type = type;
        this.txtnum = txtnum;
    }
}
