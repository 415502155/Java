package com.bestinfo.dao.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页实体
 *
 * @author yangyf
 */
public class Pagination<T> implements Serializable {

    /**
     * 一页数据默认20条
     */
    private int pageSize = 20;
    /**
     * 当前页码
     */
    private int pageNumber;
    /**
     * 上一页
     */
    private int upPage;
    /**
     * 下一页
     */
    private int nextPage;
    /**
     * 一共有多少条数据
     */
    private long totalCount;
    /**
     * 一共有多少页
     */
    private int totalPage;
    /**
     * 返回数据列表
     */
    private List<T> rows = new ArrayList<T>();
    /**
     * 分页的url
     */
    private String pageUrl;

    public Pagination() {
    }

    public Pagination(int pageNumber, int pageSize, long total, List<T> rows) {
        this.setPageNumber(pageNumber);
        this.setPageSize(pageSize);
        this.setTotalCount(total);
        this.setRows(rows);
        this.init();
    }

    /**
     * 初始化计算分页
     */
    private void init() {
        this.setTotalPage();// 设置一共页数
        this.setUpPage();// 设置上一页
        this.setNextPage();// 设置下一页
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    /**
     * 获取第一条记录位置
     *
     * @return
     */
    public int getFirstResult() {
        return (this.getPageNumber() - 1) * this.getPageSize();
    }

    /**
     * 获取最后记录位置
     *
     * @return
     */
    public int getLastResult() {
        return this.getPageNumber() * this.getPageSize();
    }

    /**
     * 计算一共多少页
     */
    public void setTotalPage() {
        this.totalPage = (int) ((this.totalCount % this.pageSize > 0) ? (this.totalCount / this.pageSize + 1)
                                                          : this.totalCount / this.pageSize);
    }

    /**
     * 设置 上一页
     */
    public void setUpPage() {
        this.upPage = (this.pageNumber > 1) ? this.pageNumber - 1 : this.pageNumber;
    }

    /**
     * 设置下一页
     */
    public void setNextPage() {
        this.nextPage = (this.pageNumber == this.totalPage) ? this.pageNumber : this.pageNumber + 1;
    }

    public int getNextPage() {
        return nextPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getUpPage() {
        return upPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount2) {
        this.totalCount = totalCount2;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }
}
