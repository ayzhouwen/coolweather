package com.coolweather.app.model;

/**
 * Created by admin on 2017/1/8.
 */

public class WeatherCode {
    private String areaString; //地区名
    private String wCode; //天气代码

    public String getAreaString() {
        return areaString;
    }

    public void setAreaString(String areaString) {
        this.areaString = areaString;
    }

    public String getwCode() {
        return wCode;
    }

    public void setwCode(String wCode) {
        this.wCode = wCode;
    }
}
