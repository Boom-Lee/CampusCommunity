package com.liyuji.app.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Util {
    /**
     * 手机调试本机地址
     */
    public final static String SERVER_ADDR = "http://8.136.180.205:8181/api/";
//    public final static String SERVER_ADDR = "http://192.168.3.25:8181/api/";
    public static final String NET_UNCONNECTION = "网络未连接";
    public static final int ARITCLEFRAGMENT = 1;
    public static final int SCHEDULERAGMENT = 2;
    public static final int ANNONYMOUSFRAGMENT = 3;
    public static final int PERSONALFRAGMENT = 4;


//    /**
//     * 判断网络是否连接
//     *
//     * @param ctxt
//     * @return true :连接 ； false: 断开
//     */
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public static boolean isNetworkConnected(Context ctxt) {
//        ConnectivityManager cm = (ConnectivityManager) ctxt.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkCapabilities networkCapabilities = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            networkCapabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
//        }
//        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
//    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
}
