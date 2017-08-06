package com.example.user.weatherapp.screens.Forecast.core;

import android.util.Log;

import com.example.user.weatherapp.utility.rx.RxSchedulers;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by user on 16/07/2017.
 */

public class ForecastPresenter {

    ForecastView view;
    ForecastModel model;
    RxSchedulers rxSchedulers;
    CompositeSubscription subscription;
    private Subscription lastKnownLocationSubscription;

    public ForecastPresenter(RxSchedulers schedulers, ForecastModel model, ForecastView view, CompositeSubscription sub) {
        this.rxSchedulers = schedulers;
        this.view = view;
        this.model = model;
        this.subscription = sub;
    }

    public void onCreate() {
        subscription.add(getApiResult());
        subscription.add(respondToClick());
      //  subscription.add(getLastKnownLocation());
    }

    public void onDestroy(){
        subscription.clear();
    }

    private Subscription respondToClick() {
        return view.itemClick().subscribe(integer -> model.goToWeatherDetails());
    }

    private Subscription getApiResult() {
        return model.isNetworkAvailable().doOnNext(isNetworkAvailable ->{
            if(!isNetworkAvailable)
                Log.d("no conn", "no conn");})
            .filter(aBoolean -> true)
            .flatMap(aBoolean -> model.provideApiResult())
            .subscribeOn(rxSchedulers.internet())
            .observeOn(rxSchedulers.androidThread())
            .subscribe(w ->{
                        Log.d("tag", "good");
                       // model.addLocation("3333", s.getCity().getName(), s.getCity().getCoord().getLat(), s.getCity().getCoord().getLon());
                      //  model.insertWeatherListToDB(w);
                Log.d("tag", w.getCity().getCoord().getLat().toString());
                        model.isWeatherIsInsertedToDB(w)
                                .subscribeOn(rxSchedulers.internet())
                                .observeOn(rxSchedulers.androidThread())
                                .subscribe(aBoolean -> {
                                        Log.d("insert weather list", "done");
                                        view.loadCursor(model.getAllData());});
                         },
                      throwable -> Log.d("error occur", throwable.toString()));
    }

  /*  private Subscription getLastKnownLocation() {
       return lastKnownLocationSubscription = view.getLastKnownLocationObservable()
                .map(location -> {
                    if(location != null)
                        return location.getLatitude() + " " + location.getLongitude() + " (" + location.getAccuracy() + ")";
                    return "no location available";
                })
                .subscribe(s -> Log.d("ForecastPresenter", s));
    }*/
}
