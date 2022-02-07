package com.example.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentToday extends Fragment {
    private Object ImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("I","Today");
        String windSpeed = getActivity().getIntent().getStringExtra("windspeed")+"mph";
        String pressure = getActivity().getIntent().getStringExtra("pressure")+"inHg";
        String temperature = getActivity().getIntent().getStringExtra("temperature");
        String humidity = getActivity().getIntent().getStringExtra("humidity")+"%";
        String visibility = getActivity().getIntent().getStringExtra("visibility")+"mi";
        String cloudCover = getActivity().getIntent().getStringExtra("cloudCover")+"%";
        String uvIndex = getActivity().getIntent().getStringExtra("uvIndex");
        String precipitationProbability = getActivity().getIntent().getStringExtra("precipitationProbability")+"%";
        String status = getActivity().getIntent().getStringExtra("status");
        String icon_R_ID = getActivity().getIntent().getStringExtra("icon_R_ID");
        Log.i("I",icon_R_ID);
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = lf.inflate(R.layout.fragment_today, container, false); //pass the correct layout name for the fragment


        TextView wind=(TextView) view.findViewById(R.id.textWind_Speed);
        wind.setText(windSpeed);

        TextView Ozone=(TextView) view.findViewById(R.id.text_Ozone);
        Ozone.setText(uvIndex);
        TextView Cloud_cover=(TextView) view.findViewById(R.id.textCloud_Cover);
        Cloud_cover.setText(cloudCover);

        TextView humidity_t=(TextView) view.findViewById(R.id.textHumidity);
        humidity_t.setText(humidity);

        TextView status1=(TextView) view.findViewById(R.id.WeatherStatus);
        status1.setText(status);



        TextView pressure_t=(TextView) view.findViewById(R.id.textPressure);
        pressure_t.setText(pressure);

        TextView vis=(TextView) view.findViewById(R.id.textVisibility);
        vis.setText(visibility);

        TextView prec=(TextView) view.findViewById(R.id.textPrecipitation);
        prec.setText(precipitationProbability);

        TextView tem=(TextView) view.findViewById(R.id.text_Temperature);
        tem.setText(temperature);

        ImageView icon=(ImageView) view.findViewById(R.id.IWeatherStatus);
        icon.setImageResource(Integer.parseInt(icon_R_ID));



        return view;
        }
    }
