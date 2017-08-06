package com.example.user.weatherapp.application.builder;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 16/07/2017.
 */
@Module
public class AppContextModule {

    private final Context context;

    public AppContextModule(Context context){
        this.context = context;
    }

    @AppScope
    @Provides
    Context provideAppContext(){
        return context;
    }
}
