package cn.edugate.esb.authentication;

import javax.persistence.Transient;
import org.codehaus.jackson.annotate.JsonIgnore;

public class ACSet {
	protected  String name;
	protected  String text;	
	protected  int value;
	protected Boolean checked=null;
	
	
	public ACSet() {
		
	}
	public ACSet(AC ac) {
		this(ac.name(),ac.getText(),ac.getValue());
		checked=Boolean.FALSE;
	}
	public ACSet(String name,String text,int value) {
		this.name = name;
		this.text = text;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}	
	
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
	@Override
    @Transient
    @JsonIgnore
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("name: ").append(this.name).append("; ");
        sb.append("text: ").append(this.text).append("; ");
        sb.append("value: ").append(this.value).append("; ");
        sb.append("checked: ").append(this.checked).append("; ");
        return sb.toString();
    }
	
}
