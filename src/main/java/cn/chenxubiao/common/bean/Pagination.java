package cn.chenxubiao.common.bean;

/**
 * Created by chenxb on 17-5-14.
 */
public class Pagination {

    private int totalPage;
    private int totalCount;
    private int page = 1;
    private int size = 20;

    public Pagination() {
    }

    public Pagination(int totalPage, int totalCount, int page, int size) {
        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.page = page;
        this.size = size;
    }

    public int getTotalPage() {
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
