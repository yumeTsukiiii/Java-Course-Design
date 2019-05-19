package com.yumetsuki.base.data;

/**医院数据类
 * @author 王小伟
 * @version 1.0*/
public class Hospital {

    private String code;  //医院编码
    private String name;  //医院名称
    private String level;  //医院等级
    private String category;  //医院类别
    private String address;  //医院地址

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
