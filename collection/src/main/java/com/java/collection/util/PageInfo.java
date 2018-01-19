package com.java.collection.util;

/**
 * @author Administrator
 * 分页对象
 *
 * @2018年1月17日
 */

public class PageInfo {
	
	private int pagenum;      //一页的条目数 
	private int curpagesize;  //当前页数 
	private int totalpagenum; //总条目数
	private int totalpagesize;//总页数
	
	public int getStartNum(int pagenum, int curpagesize) {
		return (curpagesize-1)*pagenum;
	}
	
	public int getEndNum(int pagenum, int curpagesize) {
		return curpagesize*pagenum;
	}
	
	public PageInfo() {
		
	}
	
	public PageInfo(int pagenum, int curpagesize, int totalpagenum, int totalpagesize) {
		this.pagenum = pagenum;
		this.curpagesize = curpagesize;
		this.totalpagenum = totalpagenum;
		this.totalpagesize = totalpagesize;
	}
	
	public int getPagenum() {
		return pagenum;
	}
	public void setPagenum(int pagenum) {
		this.pagenum = pagenum;
	}
	public int getCurpagesize() {
		return curpagesize;
	}
	public void setCurpagesize(int curpagesize) {
		this.curpagesize = curpagesize;
	}
	public int getTotalpagenum() {
		return totalpagenum;
	}
	public void setTotalpagenum(int totalpagenum) {
		this.totalpagenum = totalpagenum;
	}
	public int getTotalpagesize() {
		return totalpagesize;
	}
	public void setTotalpagesize(int totalpagesize) {
		this.totalpagesize = totalpagesize;
	}
	
	

}
