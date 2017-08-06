package com.example.user.weatherapp.screens.Forecast.list;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.weatherapp.R;
import com.example.user.weatherapp.models.WeatherContract.WeatherEntry;
import com.example.user.weatherapp.models.WeatherItem;

import java.util.ArrayList;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by user on 16/07/2017.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastViewHolder>{

    private final PublishSubject<Integer> itemClicks = PublishSubject.create();
    ArrayList<WeatherItem> weatherList = new ArrayList<>();
    Cursor mCursor;

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_item, parent, false);
        return new ForecastViewHolder(parent.getContext(), view, itemClicks);
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
      //  WeatherItem w = weatherList.get(position);
        if (mCursor.moveToPosition(position)) {
            int weatherId = mCursor.getInt(mCursor.getColumnIndex(WeatherEntry.COLUMN_WEATHER_ID));
            Long date = mCursor.getLong(mCursor.getColumnIndex(WeatherEntry.COLUMN_DATE));
            String desc = mCursor.getString(mCursor.getColumnIndex(WeatherEntry.COLUMN_SHORT_DESC));
            double high = mCursor.getDouble(mCursor.getColumnIndex(WeatherEntry.COLUMN_MAX_TEMP));
            double low = mCursor.getDouble(mCursor.getColumnIndex(WeatherEntry.COLUMN_MIN_TEMP));
            holder.bind(weatherId, date, desc, high, low);
        }
    }

    @Override
    public int getItemCount() {
        if(mCursor != null && mCursor.getCount() > 0)
            return mCursor.getCount();
        return 0;
    }

    public Observable<Integer> observeClicks(){
        return itemClicks;
    }

    public void observeCursor(Cursor c){
        mCursor = c;
        notifyDataSetChanged();
    }

}
