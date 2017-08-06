package com.example.user.weatherapp.utility.rx;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import rx.Observable;

/**
 * Created by user on 16/07/2017.
 */

public class NetwortUtils {

    private static boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activityNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activityNetworkInfo != null && activityNetworkInfo.isConnected();
    }

    public static Observable<Boolean> isNetworkAvailableObserable(Context context){
        return Observable.just(isNetworkAvailable(context));
    }
}
