package com.lck.reverse.entity.respon;

import java.util.List;

public class PageInfo<T> {

    private int pageNum;//当前页数

    private int pageSize;//每页记录数

    private int totalCount;//总记录数

    private int totalPage;//一共多少页

    private List<T> data;
    

    public PageInfo() {
        super();
    }

    public PageInfo(int pageNum, int pageSize, List<T> data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.data = data;
    }

    public PageInfo(int pageNum, int pageSize, int totalCount, List<T> data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        this.data = data;
    }
    
    public int getPageNum() {
        return pageNum;
    }

    public PageInfo setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public PageInfo setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public PageInfo setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }


    public int getTotalPage() {
        return totalPage;
    }

    public PageInfo setTotalPage(int totalPage) {
        this.totalPage = totalPage;
        return this;
    }

    public List<T> getData() {
        return data;
    }

    public PageInfo setData(List<T> data) {
        this.data = data;
        return this;
    }
}
