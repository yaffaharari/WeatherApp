package com.example.user.weatherapp.application.builder;

import android.content.Context;

import com.example.user.weatherapp.api.ForecastApi;
import com.example.user.weatherapp.utility.rx.RxSchedulers;

import dagger.Component;

/**
 * Created by user on 16/07/2017.
 */
@AppScope
@Component(modules = {AppContextModule.class, NetworkModule.class, RxModule.class, WeatherApiServiceModule.class})
public interface AppComponent {

    Context getAppContext();

    RxSchedulers rxSchedulers();

    ForecastApi apiService();

}
