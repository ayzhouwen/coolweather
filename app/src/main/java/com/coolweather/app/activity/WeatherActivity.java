package com.coolweather.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coolweather.app.model.WeatherCode;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import app.coolweather.com.coolweather.R;

/**
 * Created by admin on 2017/1/7.
 */

public class WeatherActivity extends Activity implements View.OnClickListener {
    private LinearLayout weatherInfoLayout;
    //显示城市名
    private TextView cityNameText;

    //用于显示发布时间
    private TextView publishText;

    //用于显示天气描述信息
    private TextView weatherDespText;

    //用于显示气温1
    private TextView temp1Text;

    //用于显示气温2
    private TextView temp2Text;
    //用于显示当期日期
    private TextView currentDateText;
    //切换城市
    private Button switchCity;
    //更新天气
    private Button refreshWeather;

    //市,县天气地区号,赌气与天气接口对接时使用,前期地区号直接从文本中读取
    private List<WeatherCode> weatherList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);
        //初始化各控件
        weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
        cityNameText = (TextView) findViewById(R.id.city_name);
        publishText = (TextView) findViewById(R.id.push_text);
        weatherDespText = (TextView) findViewById(R.id.weather_desp);
        temp1Text = (TextView) findViewById(R.id.temp1);
        temp2Text = (TextView) findViewById(R.id.temp2);
        currentDateText = (TextView) findViewById(R.id.current_date);
        switchCity = (Button) findViewById(R.id.switch_city);
        refreshWeather = (Button) findViewById(R.id.refresh_weather);
        String couunty_name = getIntent().getStringExtra("couunty_name");
        String city_name = getIntent().getStringExtra("city_name");
        if (!TextUtils.isEmpty(couunty_name)||!TextUtils.isEmpty(city_name)) {
            //有县级代号时就去查询天气
            publishText.setText("同步中...");
            weatherInfoLayout.setVisibility(View.INVISIBLE);
            cityNameText.setVisibility(View.INVISIBLE);
            queryWeatherCode(couunty_name,city_name);
        } else {
            //没有县级代号时就直接显示本地天气
            showWeather();
        }
        switchCity.setOnClickListener(this);
        refreshWeather.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_city:
                Intent intent = new Intent(this, ChooseAreaActivity.class);
                intent.putExtra("from_weather_activity", true);
                startActivity(intent);
                finish();
                break;
            case R.id.refresh_weather:
                publishText.setText("同步中...");
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                String weatherCode = prefs.getString("weather_code", "");
                if (!TextUtils.isEmpty(weatherCode)) {
                    queryWeatherInfo(weatherCode);
                    ;

                }
                break;

        }
    }

    //查询县级代号所对应的天气代号,原文需要掉API,现在根据县名称只从文本文件中查找,
    //如果找不到比如是市区名名称,则用市名称再次查找,哎天气接口很烂,我也没办法
    private void queryWeatherCode(String countyName,String cityName ) {

        if (weatherList.size() <1) {
            InputStream inputStream = getResources().openRawResource(R.raw.weathercode);
            InputStreamReader inputStreamReader = null;
            inputStreamReader = new InputStreamReader(inputStream);//默认执行的utf-8
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuffer sb = new StringBuffer("");
            String line;
            try {
                while ((line = reader.readLine()) != null) {
//                sb.append(line);
//                sb.append("\n");
                    String[] sarr = line.split("\\s+"); //正则切割空格
                    if (sarr.length > 2) {
                        WeatherCode wc = new WeatherCode();
                        wc.setAreaString(sarr[1]);
                        wc.setwCode(sarr[2]);
                        weatherList.add(wc);

                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       String wc=  findWeatherCode(countyName);
        if (TextUtils.isEmpty(wc)){
            wc=  findWeatherCode(cityName);
        }
        String address = "http://www.weather.com.cn/data/cityinfo/" + wc + ".html";
        queryFromServer(address,wc);

    }

    private  String findWeatherCode(String countyName){
        if (TextUtils.isEmpty(countyName)){
            return "";
        }
        for (WeatherCode node:weatherList){
            if (node.getAreaString().equals(countyName)||node.getAreaString().contains(countyName)||
                    countyName.contains(node.getAreaString())){
                    return node.getwCode();
            }
        }
        return  "";
    }
    //查询天气代号所对应的天气
    private void queryWeatherInfo(String weatherCode) {
        String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
        queryFromServer(address, "weatherCode");

    }

    //根据传入的地址和类型去向服务器查询天气代号或者天气信息
    private void queryFromServer(final String address, final String type) {
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
               // if ("weatherCode".equals(type)) {
                    //处理服务器返回的天气信息
                    Utility.handleWeatherResponse(WeatherActivity.this, response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });

               // }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        publishText.setText("同步失败");
                    }
                });
            }
        },"UTF-8");
    }

    //从SharedPreferences文件中读取存储的天气信息,并显示到界面上
    private void showWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        cityNameText.setText(prefs.getString("city_name", ""));
        temp1Text.setText(prefs.getString("temp1", ""));
        temp2Text.setText(prefs.getString("temp2", ""));
        weatherDespText.setText(prefs.getString("weather_desp", ""));
        publishText.setText("今天" + prefs.getString("publish_time", "") + "发布");
        currentDateText.setText(prefs.getString("currentDate", ""));
        weatherInfoLayout.setVisibility(View.VISIBLE);
        cityNameText.setVisibility(View.VISIBLE);
    }
}
