package com.coolweather.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CoolWeatherOpenHelper extends SQLiteOpenHelper {


    //provice表建表语句
    public  static final String CREATE_PROVINCE="create table Province ("+
            "atno integer primary key autoincrement,"+
            "id integer ,"+
            "province_name text,"+
            "province_code text)";
    //city表
    public  static final String CREATE_CITY="create table City("+
            "atno integer primary key autoincrement,"
            +"id integer ,"
            +"city_name text,"
            +"city_code text,"
            +"province_id integer)";
    //county表
    public  static final String CREATE_COUNTY="create table County("
            +"atno integer primary key autoincrement,"
            +"id integer ,"
            + "county_name text,"
            +"county_code text,"
            +"city_id integer)";

    public  CoolWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVINCE);//创建Province表
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Province");
        db.execSQL("drop table if exists City");
        db.execSQL("drop table if exists County");
        onCreate(db);
    }
}