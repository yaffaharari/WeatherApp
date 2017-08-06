package com.example.user.weatherapp.application;

import android.app.Application;

import com.example.user.weatherapp.BuildConfig;
import com.example.user.weatherapp.application.builder.AppComponent;
import com.example.user.weatherapp.application.builder.AppContextModule;
import com.example.user.weatherapp.application.builder.DaggerAppComponent;
import com.example.user.weatherapp.models.WeatherDbHelper;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by user on 16/07/2017.
 */

public class AppController extends Application {

    private static AppComponent appComponent;

    @Inject
    WeatherDbHelper helper;

    @Override
    public void onCreate() {
        super.onCreate();
        initialiseLogger();
        initAppComponent();
    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder().appContextModule(new AppContextModule(this)).build();
    }

    private void initialiseLogger() {
        if(BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
        else{
            Timber.plant(new Timber.Tree(){
                @Override
                protected void log(int priority, String tag, String message, Throwable t) {

                }
            });
        }
    }

    public static AppComponent getAppComponent(){
        return appComponent;
    }
}
