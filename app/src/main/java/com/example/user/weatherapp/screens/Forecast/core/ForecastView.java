package com.example.user.weatherapp.screens.Forecast.core;

import android.Manifest.permission;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.example.user.weatherapp.R;
import com.example.user.weatherapp.databinding.FragmentForecastBinding;
import com.example.user.weatherapp.screens.Forecast.ForecastActivity;
import com.example.user.weatherapp.screens.Forecast.list.ForecastAdapter;
import com.google.android.gms.location.LocationRequest;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;

/**
 * Created by user on 16/07/2017.
 */

public class ForecastView {

    FragmentForecastBinding binding;
    View view;
    ForecastAdapter adapter;
    private ReactiveLocationProvider locationProvider;
    Observable<Location> lastKnownLocationObservable;
    LocationRequest locationRequest;

    public ForecastView(ForecastActivity context) {
        FrameLayout parent = new FrameLayout(context);
        parent.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_forecast, parent, false);
        view = binding.getRoot();

        adapter = new ForecastAdapter();
        binding.fragmentForecastRecycleview.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        binding.fragmentForecastRecycleview.setLayoutManager(mLayoutManager);

        locationProvider = new ReactiveLocationProvider(context);
        if (ActivityCompat.checkSelfPermission(context, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lastKnownLocationObservable = locationProvider.getLastKnownLocation();
       /* locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setNumUpdates(5)
                .setInterval(100);*/
    }

    public Observable<Location> getLastKnownLocationObservable(){
        return lastKnownLocationObservable;
    }

    public View getView(){
        return view;
    }

    public Observable<Integer> itemClick(){
        return adapter.observeClicks();
    }

    public void loadCursor(Cursor c){
        adapter.observeCursor(c);
    }
}
