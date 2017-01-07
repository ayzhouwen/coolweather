package com.coolweather.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

                        for (City node:list){
                            coolWeatherDB.saveCity(node);
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

    //解析服务器返回的json数据,并将解析出的数据存储到本地
    public  static void handleWeatherResponse(Context context,String response){
        try {
            JSONObject jsonObject=JSONObject.parseObject(response);
            JSONObject weatherInfo=jsonObject.getJSONObject("weatherinfo");
            String cityName=weatherInfo.getString("city");
            String weatherCode=weatherInfo.getString("cityid");
            String temp1=weatherInfo.getString("temp1");
            String temp2=weatherInfo.getString("temp2");
            String weatherDesp=weatherInfo.getString("weather");
            String publishTime=weatherInfo.getString("ptime");
            saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,publishTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //将服务器返回的所有天气存储到SharePreferences文件中
    public  static  void saveWeatherInfo(Context context,String cityName,String weatherCode,
                                         String temp1,String temp2,String weatherDesp,
                                         String publishTime){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(context).edit();
            editor.putBoolean("city_selected",true);
            editor.putString("city_name",cityName);
            editor.putString("weather_code",weatherCode);
            editor.putString("temp1",temp1);
            editor.putString("temp2",temp2);
            editor.putString("weather_desp",weatherDesp);
            editor.putString("publish_time",publishTime);
            editor.putString("current_date",sdf.format(new Date()));
            editor.commit();
    }

}
