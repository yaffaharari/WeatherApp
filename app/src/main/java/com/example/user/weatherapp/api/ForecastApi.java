package com.example.user.weatherapp.api;


import com.example.user.weatherapp.models.WeatherList;

import retrofit2.http.GET;
import rx.Observable;


/**
 * Created by user on 16/07/2017.
 */

public interface ForecastApi {

    //http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=14&APPID=eac05378c68283f873d36048e644041e
    @GET("/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=14&APPID=eac05378c68283f873d36048e644041e")
    Observable<WeatherList> getWeatherList();
}
