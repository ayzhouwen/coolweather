package com.coolweather.app.model;

/**
 * Created by admin on 2017/1/4.
 */

//api:极速数据http://www.jisuapi.com/api/area/
public class AddressBase {
    private int  id;
    private String name;
    private int parentid ; //上级id
    private String parentname; //上级名称
    private String areacode;//区号
    private String zipcode;//邮编
    private String  depth;//区域等级(深度) 冗余字段，用来查找

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
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

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getParentname() {
        return parentname;
    }

    public void setParentname(String parentname) {
        this.parentname = parentname;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
