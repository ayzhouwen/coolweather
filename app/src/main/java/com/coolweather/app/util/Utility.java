package com.coolweather.app.util;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

import java.util.List;

/**
 * Created by admin on 2017/1/4.
 */
//由于原文的接口的借口无法使用,新接口返回的是json数据,方法里的实现有些不一样
//
public class Utility {
    //解析和处理服务器返回的省级数据
    public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, String
            response) {
        if (!TextUtils.isEmpty(response)){
            JSONObject jobj = JSON.parseObject(response);
            JSONArray jArray=jobj.getJSONArray("result");
            List<Province> list=JSONArray.parseArray(jArray.toString(), Province.class);
            if (list !=null &&list.size()>0){
                try {
                    for (Province node:list){
                         coolWeatherDB.saveProvince(node);

                      ;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Utility","保存省出错");
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    //解析和处理服务器返回的市级数据
    public static boolean handleCitiesResponse( CoolWeatherDB coolWeatherDB,String response,int provinceId){
        if (!TextUtils.isEmpty(response)){
            {
                JSONObject jobj = JSON.parseObject(response);
                JSONArray jArray=jobj.getJSONArray("result");
                List<City> list=JSONArray.parseArray(jArray.toString(), City.class);
                if (list !=null &&list.size()>0){

                    try {
                        for (City node:list){
                            coolWeatherDB.saveCity(node);


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("Utility","保存城市出错");
                        return false;
                    }
                    return true;
                }
            }
        }
        return  false;
    }

    //解析和处理服务器返回的县级数据
    public static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB,String response,int cityId){

            if (!TextUtils.isEmpty(response)){
                {
                    JSONObject jobj = JSON.parseObject(response);
                    JSONArray jArray=jobj.getJSONArray("result");
                    List<County> list=JSONArray.parseArray(jArray.toString(), County.class);
                    if (list !=null &&list.size()>0){
                        try {
                            for (County node:list){
                                    coolWeatherDB.saveCounty(node);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("Utility","保存地区出错");
                            return false;
                        }
                        return true;
                    }
                }
            }
            return  false;

    }
}
