package com.coolweather.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/1/3.
 */

public class CoolWeatherDB {
    //数据库名
    public  static final String DB_NAME="cool_weather";
    //数据库版本
    public  static final int VERSION=13;

    private static CoolWeatherDB coolWeatherDB;

    private SQLiteDatabase db;

    //构造函数私有化
    private CoolWeatherDB(Context context){
        CoolWeatherOpenHelper dbHelper=new CoolWeatherOpenHelper(context,DB_NAME,
                null,VERSION);
        db=dbHelper.getWritableDatabase();

    }

    //获取CoolWeatherDB的实例
    public synchronized static CoolWeatherDB getInstance(Context context){
        if (coolWeatherDB==null){
            coolWeatherDB=new CoolWeatherDB(context);
        }
        return  coolWeatherDB;
    }

    //将province实例存储到数据库

    public void saveProvince(Province province){
        if (province !=null){
            ContentValues values=new ContentValues();
            values.put("province_name",province.getName());
            values.put("province_code",province.getAreacode());
            values.put("id",province.getId());
            db.insert("Province",null,values);
        }
    }
    //从数据库读取全国所有省份的省份信息

    public List<Province> loadProvinces(){
        List<Province> list=new ArrayList<>();
        Cursor cursor =db.query("Province",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                Province province=new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvienceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }while (cursor.moveToNext());
        }
        return  list;
    }

    //将city实例存储到数据库
    public void saveCity(City city){
        if (city !=null){
            ContentValues values =new ContentValues();
            values.put("city_name",city.getName());
            values.put("city_code",city.getAreacode());
            values.put("province_id",city.getParentid());
            values.put("id",city.getId());
            db.insert("City",null,values);

        }
    }

    //从数据库读取某省下所有城市信息

    public  List<City> loadCities(int provinceId){
        List<City> list=new ArrayList<>();
        Cursor cursor =db.query("City",null,"province_id=?",new String[]{String.valueOf(provinceId)},
                null,null,null
                );
        if (cursor.moveToFirst()){
            do {
                City city=new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                list.add(city);

            }while (cursor.moveToNext());
        }

        return list;
    }

    //将County实例存储到数据库
    public  void saveCounty(County county){
        if (county !=null){
            ContentValues values =new ContentValues();
            values.put("county_name",county.getName());
            values.put("county_code",county.getAreacode());
            values.put("city_id",county.getCityId());
            values.put("id",county.getId());
            db.insert("County",null,values);
        }
    }

    //从数据库读取某城市下所有县信息

    public List<County> loadCounties(int cityId){
        List<County> list=new ArrayList<>();
        Cursor cursor =db.query("County",null,"city_id=?",
                new String[]{String.valueOf(cityId)},null,null,null);
        if (cursor.moveToFirst()){
            do {
                County county =new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cityId);
                list.add(county);

            }while (cursor.moveToNext());
        }
        return  list;
    }

}
