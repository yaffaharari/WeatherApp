package com.example.user.weatherapp.utility.rx;

import rx.Scheduler;

/**
 * Created by user on 16/07/2017.
 */

public interface RxSchedulers {

    Scheduler runOnBackground();

    Scheduler io();

    Scheduler compute();

    Scheduler androidThread();

    Scheduler internet();

}
