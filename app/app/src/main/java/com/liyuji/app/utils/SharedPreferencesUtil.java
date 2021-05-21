package com.liyuji.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

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
        /**
         * 通过Context调用该方法获得对象
         */
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

    /**
     * 存储boolean类型
     *
     * @param key
     * @param value
     */
    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 存储String类型
     *
     * @param key
     * @param value
     */
    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 存储int类型
     *
     * @param key
     * @param value
     */
    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 读取Boolean类型
     *
     * @param key
     * @return
     */
    public boolean readBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    /**
     * 读取String类型
     *
     * @param key
     * @return
     */
    public String readString(String key) {
        return sharedPreferences.getString(key, "");
    }

    /**
     * 读取int类型
     *
     * @param key
     * @return
     */
    public int readInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    /**
     * 删除单个
     *
     * @param key
     */
    public void delete(String key) {
        editor.remove(key).commit();
    }

    /**
     * 清空
     */
    public void clear() {
        editor.clear().commit();
    }

}

//
//    public Object readObject(String key, Class clazz) {
//        String str = sharedPreferences.getString(key, "");
//        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd HH:mm:ss")
//                .create();
//        return gson.fromJson(str, clazz);
//    }

