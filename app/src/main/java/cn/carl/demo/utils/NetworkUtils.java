package cn.carl.demo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import cn.carl.demo.xcodescanner.APP;

/**
 * Created by carl on 2018/5/15.
 */

public class NetworkUtils {

    public static boolean isConnected() {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    private static NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager manager = (ConnectivityManager) APP.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) return null;
        return manager.getActiveNetworkInfo();
    }

}
