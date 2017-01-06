package com.coolweather.app.model;

/**
 * Created by admin on 2017/1/3.
 */

public class City extends  AddressBase {
    private String cityName;
    private String cityCode;
    private int provinceId;

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public int getProvinceId() {
        return this.getParentid();
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
