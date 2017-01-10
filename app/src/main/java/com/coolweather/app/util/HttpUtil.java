package com.coolweather.app.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by admin on 2017/1/4.
 */

public class HttpUtil {
    public static void sendHttpRequest(final String address, final HttpCallbackListener listener, final String  ecode){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                try {
                    URL url=new URL(address);
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                   // connection.setRequestProperty("Accept-Charset", "utf-8");
                  //  connection.setRequestProperty("contentType", "utf-8");
                    InputStream in=connection.getInputStream();
                  //  BufferedReader reader=new BufferedReader(new InputStreamReader(in,ecode));

                 //  StringBuilder response=new StringBuilder();
                 //   String line;
//
//                        while ((line=reader.readLine())!=null){
//                            response.append(line);
//                        }

                    //注意下面是由于天气接口返回的数据很操蛋,结束位识别不了,所以要手动复制字节数
                    StringBuilder response=new StringBuilder();
                    byte [] tt=new byte[1024];
                    int b=0;

                    try {
                        while((b=in.read(tt))!=-1) {
                            int len = 0;
                            byte[] newt = new byte[0];
                                newt = new byte[b];
                                for (int k = 0; k < b; k++) {
                                    newt[k] = tt[k];
                                }

                            String tzt = new String(newt, "utf-8");
                            response.append(tzt);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    if (listener !=null){
                        //回调onfinish()方法
                        listener.onFinish(response.toString());
                    }


                    in.close();

                }catch (Exception e){
                      if (listener !=null){
                          //回调onerror()方法
                          listener.onError(e);
                      }
                }finally {
                    if (connection !=null){
                        connection.disconnect();
                    }

                }
            }
        }).start();
    }



}
