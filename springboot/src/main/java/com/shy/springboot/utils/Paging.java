package com.shy.springboot.utils;

import java.util.ArrayList;
import java.util.List;

public class Paging<T> {
	private int code = 200;

	/**
	 * 总页数
	 */
	private long size = 1;
	/**
	 * 总记录数
	 */
	private long total = 0;

	/**
	 * 第几页，从第一页开始
	 */
	private int page = 1;

	/**
	 * 起始记录序号
	 */
	private int start = 0;

	/**
	 * 每页记录数
	 */
	private int limit = 25;

	/**
	 * 总页数
	 */
	// private long totalPage=0;

	private List<T> data = new ArrayList<T>();

	private boolean success = false;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Long getTotalPage() {
		return (this.total + this.getLimit() - 1) / this.getLimit();
	}
	// public void setTotalPage(long totalPage) {
	// this.totalPage = totalPage;
	// }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
}
