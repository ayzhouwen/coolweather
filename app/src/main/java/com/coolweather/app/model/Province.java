package com.coolweather.app.model;

/**
 * Created by admin on 2017/1/3.
 */

public class Province {
    private int id;
    private String provinceName;
    private String provienceCode;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }
    public String getProvinceName(){
        return provinceName;
    }

    public String getProvienceCode() {
        return provienceCode;
    }

    public void setProvienceCode(String provienceCode) {
        this.provienceCode = provienceCode;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}
