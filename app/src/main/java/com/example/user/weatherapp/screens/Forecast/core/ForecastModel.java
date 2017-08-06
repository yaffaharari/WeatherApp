package com.example.user.weatherapp.screens.Forecast.core;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.text.format.Time;
import android.util.Log;

import com.example.user.weatherapp.api.ForecastApi;
import com.example.user.weatherapp.models.WeatherContract;
import com.example.user.weatherapp.models.WeatherContract.WeatherEntry;
import com.example.user.weatherapp.models.WeatherItem;
import com.example.user.weatherapp.models.WeatherList;
import com.example.user.weatherapp.screens.Forecast.ForecastActivity;
import com.example.user.weatherapp.utility.rx.NetwortUtils;

import java.util.ArrayList;
import java.util.Vector;

import rx.Observable;

/**
 * Created by user on 16/07/2017.
 */

public class ForecastModel {

    ForecastActivity forecastActivity;
    ForecastApi api;

    public ForecastModel(ForecastActivity context, ForecastApi api){
        this.forecastActivity = context;
        this.api = api;
    }

    Observable<Boolean> isNetworkAvailable(){
        return NetwortUtils.isNetworkAvailableObserable(forecastActivity);
    }

    Observable<WeatherList> provideApiResult(){
        return api.getWeatherList();
    }

    Observable<Boolean> isWeatherIsInsertedToDB(WeatherList weatherListObj){
        return Observable.create(subscriber -> {
            if(insertWeatherListToDB(weatherListObj)){
               subscriber.onNext(true);}
            else {
            subscriber.onNext(false);}
        });
    }

    public Boolean insertWeatherListToDB(WeatherList weatherListObj){
        long locationId = addLocation("3333", weatherListObj.getCity().getName(), weatherListObj.getCity().getCoord().getLat(), weatherListObj.getCity().getCoord().getLon());
        ArrayList<WeatherItem> list = new ArrayList<>(weatherListObj.getElements());
        Vector<ContentValues> cVVector = new Vector<>(list.size());
        //Calendar gc = new GregorianCalendar();
        Time dayTime = new Time();
        dayTime.setToNow();
        int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);
        dayTime = new Time();
        for (int i = 0; i < list.size(); i++) {
            //gc.add(Calendar.DAY_OF_WEEK, 1);
            WeatherItem w = list.get(i);
            long dateTime = dayTime.setJulianDay(julianStartDay + i);

            ContentValues weatherValues = new ContentValues();

            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_LOC_KEY, locationId);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_DATE, dateTime/*gc.get(Calendar.DAY_OF_WEEK)*/);
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_HUMIDITY, w.getHumidity());
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_PRESSURE, w.getPressure());
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WIND_SPEED, w.getSpeed());
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_DEGREES, w.getDeg());
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP, w.getTemp().getMax());
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP, w.getTemp().getMin());
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_SHORT_DESC, w.getWeather().get(0).getDescription());
            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WEATHER_ID, w.getWeather().get(0).getId());

            cVVector.add(weatherValues);
        }

        if(cVVector.size() > 0){
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            forecastActivity.getContentResolver().bulkInsert(WeatherEntry.CONTENT_URI, cvArray);
            forecastActivity.getContentResolver().delete(WeatherEntry.CONTENT_URI, WeatherEntry.COLUMN_DATE + " <= ?",
                    new String[]{Long.toString(dayTime.setJulianDay(julianStartDay-1))});
            Log.d("tag", "Sync Complete. " + cVVector.size() + " Inserted");
            return true;
        }
        return false;
    }

    long addLocation(String locationSetting, String cityName, double lat, double lon) {
        long locationId;

        // First, check if the location with this city name exists in the db
        Cursor locationCursor = forecastActivity.getContentResolver().query(
                WeatherContract.LocationEntry.CONTENT_URI,
                new String[]{WeatherContract.LocationEntry._ID},
                WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING + " = ?",
                new String[]{locationSetting},
                null);

        if (locationCursor.moveToFirst()) {
            int locationIdIndex = locationCursor.getColumnIndex(WeatherContract.LocationEntry._ID);
            locationId = locationCursor.getLong(locationIdIndex);
        } else {
            // Now that the content provider is set up, inserting rows of data is pretty simple.
            // First create a ContentValues object to hold the data you want to insert.
            ContentValues locationValues = new ContentValues();

            // Then add the data, along with the corresponding name of the data type,
            // so the content provider knows what kind of value is being inserted.
            locationValues.put(WeatherContract.LocationEntry.COLUMN_CITY_NAME, cityName);
            locationValues.put(WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING, locationSetting);
            locationValues.put(WeatherContract.LocationEntry.COLUMN_COORD_LAT, lat);
            locationValues.put(WeatherContract.LocationEntry.COLUMN_COORD_LONG, lon);

            // Finally, insert location data into the database.
            Uri insertedUri = forecastActivity.getContentResolver().insert(
                    WeatherContract.LocationEntry.CONTENT_URI,
                    locationValues
            );

            // The resulting URI contains the ID for the row.  Extract the locationId from the Uri.
            locationId = ContentUris.parseId(insertedUri);
        }

        locationCursor.close();
        // Wait, that worked?  Yes!
        return locationId;
    }

    public Cursor getAllData(){
        String sortOrder = WeatherContract.WeatherEntry.COLUMN_DATE + " ASC";
        String locationSetting = "3333";
        Uri weatherForLocationUri = WeatherContract.WeatherEntry.buildWeatherLocationWithStartDate(
                locationSetting, System.currentTimeMillis());
        Cursor c = forecastActivity.getContentResolver().query(weatherForLocationUri, null, null, null, sortOrder);
        if(c.getCount() > 0 ) {
            Log.d("tag", String.valueOf(c.getCount()));
            return c;
        }
        return null;
    }

  public void goToWeatherDetails(){
      Log.d("tag", "go to weather details");
  }
}
