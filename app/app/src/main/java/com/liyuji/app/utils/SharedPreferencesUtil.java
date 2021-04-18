package com.liyuji.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 存用户信息以及登录状态
 *
 * @author L
 */
public class SharedPreferencesUtil {

    private static SharedPreferencesUtil sharedPreferencesUtil;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String FILENAME = "cc";

    private SharedPreferencesUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SharedPreferencesUtil getInstance(Context context) {

        if (sharedPreferencesUtil == null) {
            synchronized (SharedPreferencesUtil.class) {
                if (sharedPreferencesUtil == null) {
                    sharedPreferencesUtil = new SharedPreferencesUtil(context);
                }
            }
        }
        return sharedPreferencesUtil;

    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();

    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }


    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public boolean readBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public String readString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public Object readObject(String key, Class clazz) {
        String str = sharedPreferences.getString(key, "");
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        return gson.fromJson(str, clazz);
    }


    public void delete(String key) {
        editor.remove(key).commit();
    }

    public void clear() {
        editor.clear().commit();
    }


}
