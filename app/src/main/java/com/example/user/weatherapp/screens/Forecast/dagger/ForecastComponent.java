package com.example.user.weatherapp.screens.Forecast.dagger;

import com.example.user.weatherapp.application.builder.AppComponent;
import com.example.user.weatherapp.screens.Forecast.ForecastActivity;

import dagger.Component;

/**
 * Created by user on 16/07/2017.
 */
@ForecastScope
@Component(modules = {ForecastModule.class}, dependencies = {AppComponent.class})
public interface ForecastComponent {
    void inject(ForecastActivity forecastActivity);
}
