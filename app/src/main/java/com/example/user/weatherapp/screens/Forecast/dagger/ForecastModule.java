package com.example.user.weatherapp.screens.Forecast.dagger;


import com.example.user.weatherapp.api.ForecastApi;
import com.example.user.weatherapp.screens.Forecast.ForecastActivity;
import com.example.user.weatherapp.screens.Forecast.core.ForecastModel;
import com.example.user.weatherapp.screens.Forecast.core.ForecastPresenter;
import com.example.user.weatherapp.screens.Forecast.core.ForecastView;
import com.example.user.weatherapp.utility.rx.RxSchedulers;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by user on 16/07/2017.
 */
@Module
public class ForecastModule {

    ForecastActivity forecastActivity;

    public ForecastModule(ForecastActivity context){
        this.forecastActivity = context;
    }

    @ForecastScope
    @Provides
    ForecastActivity provideContext(){
        return forecastActivity;
    }

    @ForecastScope
    @Provides
    ForecastView provideView(){
        return new ForecastView(forecastActivity);
    }

    @ForecastScope
    @Provides
    ForecastPresenter providePresenter(RxSchedulers schedulers, ForecastView view, ForecastModel model){
        CompositeSubscription subscription = new CompositeSubscription();
        return new ForecastPresenter(schedulers, model, view, subscription);
    }

    @ForecastScope
    @Provides
    ForecastModel provideModel(ForecastApi api){
        return new ForecastModel(forecastActivity, api);
    }
}
