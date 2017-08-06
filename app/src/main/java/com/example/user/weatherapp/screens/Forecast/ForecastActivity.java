package com.example.user.weatherapp.screens.Forecast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.user.weatherapp.application.AppController;
import com.example.user.weatherapp.screens.Forecast.core.ForecastPresenter;
import com.example.user.weatherapp.screens.Forecast.core.ForecastView;
import com.example.user.weatherapp.screens.Forecast.dagger.DaggerForecastComponent;
import com.example.user.weatherapp.screens.Forecast.dagger.ForecastModule;

import javax.inject.Inject;

/**
 * Created by user on 16/07/2017.
 */

public class ForecastActivity extends AppCompatActivity {

    @Inject
    ForecastView view;
    @Inject
    ForecastPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerForecastComponent.builder().appComponent(AppController.getAppComponent()).forecastModule(new ForecastModule(this)).build().inject(this);
        System.out.println("example");
        setContentView(view.getView());
        presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
