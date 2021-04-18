package com.liyuji.app.utils;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author L
 */
public class OkHttpCallbackFile extends OkHttpCallback implements Callback {
    private final String TAG = "com.liyuji";

    public String url;
    public byte[] result;


    public int position;
    public  OkHttpCallbackFile(int position){
        this.position=position;
    }

    public OkHttpCallbackFile(){}

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Log.e(TAG, "url: " + url);
        result = response.body().bytes();
        
        Log.e(TAG, "请求成功: " + result);
        onFinish("success", result,position);
    }


    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "url: " + url);
        Log.e(TAG, "请求失败:" + e.toString());
        onFinish("failure", e.toString().getBytes());
    }

    public void onFinish(String status, byte[] msg) {
        Log.e(TAG, "url: " + url + " status：" + status);
    }
    public void onFinish(String status, byte[] msg, int position) {
        Log.e(TAG, "url: " + url + " status：" + status);
    }


}

