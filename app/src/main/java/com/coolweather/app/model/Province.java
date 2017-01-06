package com.coolweather.app.model;

/**
 * Created by admin on 2017/1/3.
 */

public class Province extends AddressBase {
    private String provinceName;
    private String provienceCode;

    public String getProvienceCode() {
        return provienceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvienceCode(String provienceCode) {
        this.provienceCode = provienceCode;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}
