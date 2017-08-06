package com.example.user.weatherapp.screens.Forecast.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.weatherapp.R;
import com.example.user.weatherapp.utility.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.subjects.PublishSubject;

/**
 * Created by user on 16/07/2017.
 */

public class ForecastViewHolder extends ViewHolder {

    Context context;
    View view;

    @BindView(R.id.list_item_icon)
    ImageView iconView;
    @BindView(R.id.list_item_date_textview)
    TextView dateView;
    @BindView(R.id.list_item_forecast_textview)
    TextView descriptionView;
    @BindView(R.id.list_item_high_textview)
    TextView highTempView;
    @BindView(R.id.list_item_low_textview)
    TextView lowTempView;

    public ForecastViewHolder(Context c, View itemView, PublishSubject<Integer> clickSubject) {
        super(itemView);
        this.context = c;
        this.view = itemView;
        ButterKnife.bind(this, view);
        itemView.setOnClickListener(v -> clickSubject.onNext(getAdapterPosition()));
    }
    void bind(int weatherId, long date, String desc, double high, double low){
        Glide.with(view.getContext())
                .load(Utility.getArtResourceForWeatherCondition(weatherId))
                .into(iconView);
        dateView.setText(TextUtils.isEmpty(Utility.getFriendlyDayString(context, date)) ? "missing date" : Utility.getFriendlyDayString(context, date));
        descriptionView.setText(desc);
        iconView.setContentDescription(descriptionView.getText());
        highTempView.setText(Utility.formatTemperature(context, high));
        lowTempView.setText(Utility.formatTemperature(context, low));
    }
}
