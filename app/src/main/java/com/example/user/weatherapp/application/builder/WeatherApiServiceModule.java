package com.example.user.weatherapp.application.builder;


import com.example.user.weatherapp.api.ForecastApi;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 16/07/2017.
 */
@Module
public class WeatherApiServiceModule {

    private static final String BASE_URL = "http://api.openweathermap.org";

    @AppScope
    @Provides
    ForecastApi provideApiService(OkHttpClient client, GsonConverterFactory gson, RxJavaCallAdapterFactory rxAdapter){
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(gson)
                .addCallAdapterFactory(rxAdapter)
                .build();
        return retrofit.create(ForecastApi.class);
    }
}
