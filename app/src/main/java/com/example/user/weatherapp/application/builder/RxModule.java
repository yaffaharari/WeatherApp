package com.example.user.weatherapp.application.builder;

import com.example.user.weatherapp.utility.rx.AppRxSchedulers;
import com.example.user.weatherapp.utility.rx.RxSchedulers;

import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 16/07/2017.
 */
@Module
public class RxModule {

    @Provides
    RxSchedulers provideRxSchedulers(){
        return new AppRxSchedulers();
    }
}
