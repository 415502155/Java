package cn.edugate.esb.authentication;

import java.util.ArrayList;
import java.util.List;

import cn.edugate.esb.util.Select;
import cn.edugate.esb.util.Selector;


public class FPSet{
	
	protected  String name;
	protected  String text;
	protected  String fun_id;
	private final List<ACSet> acsets=new ArrayList<ACSet>();
	
	
	public String getFun_id() {
		return fun_id;
	}
	public void setFun_id(String fun_id) {
		this.fun_id = fun_id;
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
	
	public List<ACSet> getAcsets() {
		return acsets;
	}

	public FPSet(FP fp)
	{
		this.name=fp.getName();
		this.text=fp.getText();
		this.fun_id=fp.getFun_id();
		acsets.addAll(Selector.from(fp.getAcs())
				.select(new Select<ACSet, AC>(){
						@Override
						public ACSet apply(AC e) {
							// TODO Auto-generated method stub
							return new ACSet(e);
						}
					}
				));
				
	}
	
	public int getValue() {
		int v=0;
		for(ACSet node:this.acsets)
		{
			if(Boolean.TRUE==node.checked)
			{
				v=v|node.getValue();
			}
		}
		return v;
	}
	
	public void setValue(int value) {
		for(ACSet node:this.acsets)
		{
			if(AC.hasPermission(value,node.getValue()))
			{
				node.setChecked(true);
			}else
			{
				node.setChecked(false);
			}
		}
	}	
	
	public List<String> getAcNames()
	{
		List<String> v=new ArrayList<String>();
		for(ACSet node:this.acsets)
		{
			if(Boolean.TRUE==node.checked)
			{
				v.add(node.getName());
			}
		}		
		return v;
	}
	
	public void setAcNames(List<String> acNames)
	{
		
		for(ACSet node:this.acsets)
		{
			if(acNames.contains(node.getName()))
			{
				node.setChecked(true);
			}else
			{
				node.setChecked(false);
			}
		}
		this.setValue(this.getValue());
	}
	
	public List<String> getTexts()
	{
		List<String> v=new ArrayList<String>();
		for(ACSet node:this.acsets)
		{
			if(Boolean.TRUE==node.checked)
			{
				v.add(node.getText());
			}
		}		
		return v;
	}
	
	public void setTexts(List<String> texts)
	{
		
		for(ACSet node:this.acsets)
		{
			if(texts.contains(node.getText()))
			{
				node.setChecked(true);
			}else
			{
				node.setChecked(false);
			}
		}
		this.setValue(this.getValue());
	}
}
