package com.coolweather.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.coolweather.app.util.test;

import app.coolweather.com.coolweather.R;
public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        test t=new test();
        t.testb();
        boolean y= testb();
        Log.d("ChooseAreaActivity",y+"");
        testv();
        int ti=testi();
        Log.d("ChooseAreaActivity","ti:"+ti+"");

    }

    boolean testb(){
        int k=2;
        int j=1;
        k++;
        j++;
        if (k>j){
            Log.d("ChooseAreaActivity",k+"");
            return  true;
        }
        return  false;
    }
    void testv(){
        int k=2;
        int j=1;
        if (k>j){
            Log.d("ChooseAreaActivity",k+"");
            return ;
        }
        return  ;
    }

    int testi(){
        int k=2;
        int j=1;
        if (k>j){
            Log.d("ChooseAreaActivity",k+"");
            return k;
        }
        return  j;
    }
}
