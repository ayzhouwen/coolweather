package com.coolweather.app.model;

/**
 * Created by admin on 2017/1/3.
 */

public class County extends AddressBase {
    private String countyName;
    private String countyCode;
    private int cityId;

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getCityId() {
        return this.getParentid();
    }

    public String getCountyCode() {
        return countyCode;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }
}
