package cn.chenxubiao.common.bean;

/**
 * Created by chenxb on 17-5-1.
 */
public class CommonBean {
    private int id;
    private String name;

    public CommonBean(){}

    public CommonBean(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
