package com.example.yunxuan.menjin.utils;


import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 *
 * okHttp请求网络返回数据
 */

public class HttpUtil {

    public static void sendOkHttpRequest(String usertoken,String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().addHeader("token",usertoken).url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
