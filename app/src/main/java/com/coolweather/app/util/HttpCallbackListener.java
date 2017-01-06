package com.coolweather.app.util;

/**
 * Created by admin on 2017/1/4.
 */

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
