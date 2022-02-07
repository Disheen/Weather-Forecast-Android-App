package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Searchable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=new Intent(Searchable.this,Detailed.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar1);
        String address=getIntent().getStringExtra("address");
        Log.i("add1",address);
        getSupportActionBar().setTitle(address); //string is custom name you want

        getCoordinate(intent,address);

    }

    private void getCoordinate(Intent intent,String address) {
        Log.i("add",address);
        RequestQueue queue= Volley.newRequestQueue(Searchable.this);
        String url="https://helloworld-329001.wm.r.appspot.com/getCoordinate?address="+address;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject main_response=new JSONObject(response);
                    String lat=main_response.getString("lat");
                    String lon=main_response.getString("lon");
                    Log.i("lat", lat);
                    Log.i("lon", lon);

                    String coord=lat+","+lon;
                    Log.i("Coord", coord);



                    getResults(coord,intent,address);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
            }
        });
        queue.add(stringRequest);

    }

    private void getResults(String loc,Intent intent,String addr) {
        RequestQueue queue= Volley.newRequestQueue(Searchable.this);
        String url="https://helloworld-329001.wm.r.appspot.com/getForecast?data="+loc;
        Log.i("URL",url);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    setContentView(R.layout.activity_searchable);
                    FloatingActionButton fab_r = findViewById(R.id.remFav);
                    fab_r.setEnabled(false);
                    TextView add=(TextView) findViewById(R.id.textView3);


                    JSONObject main_response=new JSONObject(response);
                    JSONObject data=main_response.getJSONObject("data");
                    JSONArray timelines=data.getJSONArray("timelines");
                    JSONObject timeline=timelines.getJSONObject(1);
                    JSONArray intervals=timeline.getJSONArray("intervals");
                    Log.i("Forecast", String.valueOf(intervals));
                    String[] weather_code= new String[]{"4201","4001","4200","6201","6001","6200","6000","4000","7101","7000","7102","5101","5000","5100","5001","8000","2100","2000","1001","1102","1101","1100","1000"};
                    List l= Arrays.asList(weather_code);
                    String[] weather_status=new String[]{"Heavy Rain","Rain","Light Rain","Heavy Freezing Rain","Freezing Rain","Light Freezing Rain","Freezing Drizzle","Drizzle","Heavy Ice Pellets","Ice Pellets","Light Ice Pellets","Heavy Snow","Snow","Light Snow","Flurries","Thunderstorm","Light Fog","Fog","Cloudy","Mostly Cloudy","Partly Cloudy","Mostly Clear","Clear"};
                    String[] weather_icon=new String[]{"ic_rain_heavy","ic_rain","ic_rain_light","ic_freezing_rain_heavy","ic_freezing_rain","ic_freezing_rain_light","ic_freezing_drizzle","ic_drizzle","ic_ice_pellets_heavy","ic_ice_pellets","ic_ice_pellets_light","ic_snow_heavy","ic_snow","ic_snow_light","ic_flurries","ic_flurries","ic_fog_light","ic_fog","ic_cloudy","ic_mostly_cloudy","ic_partly_cloudy_day","ic_mostly_clear_day","ic_clear_day"};


                    JSONObject elem=intervals.getJSONObject(0);
                    JSONObject values=elem.getJSONObject("values");
                    TextView temp=(TextView) findViewById(R.id.textView);
                    float t=values.getInt("temperature");

                    temp.setText(String.format("%.0f", t)+"°F");

                    intent.putExtra("location",addr);
                    intent.putExtra("temp",values.getString("temperature"));
                    add.setText(addr);
                    CardView card1=(CardView) findViewById(R.id.card1);

                    card1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(intent);
                        }
                    });

                    String w_code=values.getString("weatherCode");
                    int index=l.indexOf(w_code);
                    String stat=weather_status[index];
                    TextView w_stat=(TextView) findViewById(R.id.textView2);
                    w_stat.setText(stat);
                    String ic=weather_icon[index];
                    ImageView card_Image=(ImageView) findViewById(R.id.imageView7);
                    int resID = getResources().getIdentifier(ic, "drawable", getPackageName());
                    card_Image.setImageResource(resID);
                    intent.putExtra("windspeed",values.getString("windSpeed"));
                    intent.putExtra("pressure",values.getString("pressureSeaLevel"));
                    intent.putExtra("temperature",String.format("%.0f", t)+"°F");
                    intent.putExtra("humidity",values.getString("humidity"));
                    intent.putExtra("visibility",values.getString("visibility"));
                    intent.putExtra("cloudCover",values.getString("cloudCover"));
                    intent.putExtra("uvIndex",values.getString("uvIndex"));
                    intent.putExtra("precipitationProbability",values.getString("precipitationProbability"));
                    intent.putExtra("icon_R_ID",String.valueOf(resID));
                    intent.putExtra("status", stat);
                    intent.putExtra("G_CC",values.getInt("cloudCover"));
                    intent.putExtra("G_H",values.getInt("humidity"));
                    intent.putExtra("G_P",values.getInt("precipitationProbability"));

                    FloatingActionButton fab_a = findViewById(R.id.addFav);
                    fab_a.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(Searchable.this,addr+" was added to favorites", Toast.LENGTH_SHORT).show();
                            fab_a.setEnabled(false);
                            fab_r.setEnabled(true);
                        }
                    });
                    fab_r.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(Searchable.this,addr+" was removed from favorites", Toast.LENGTH_SHORT).show();
                            fab_a.setEnabled(true);
                            fab_r.setEnabled(false);
                        }
                    });


                    TextView wind_Speed=(TextView) findViewById(R.id.textView16);
                    String speed=values.getString("windSpeed")+"mph";
                    wind_Speed.setText(speed);

                    TextView humidity=(TextView) findViewById(R.id.textView8);
                    String hum=values.getString("humidity");
                    double d = Double.parseDouble(hum);
                    int h = (int) d;
                    String s=h+"%";
                    humidity.setText(s);

                    TextView pressure=(TextView) findViewById(R.id.textView18);
                    String press=values.getString("pressureSeaLevel")+"inHg";
                    pressure.setText(press);

                    TextView visibility=(TextView) findViewById(R.id.textView17);
                    String vis=values.getString("visibility")+"mi";
                    visibility.setText(vis);


                    Object[][] daily_chart= new Object[intervals.length()][3];
                    for (int j=0;j<intervals.length();j++){
                        JSONObject el=intervals.getJSONObject(j);
                        JSONObject value=el.getJSONObject("values");

                        for(int k=0;k<3;k++){
                            if(k==0){
                                daily_chart[j][k]=getFormatted_C_Date(el.getString("startTime"));
                            }
                            if(k==1){
                                int tmin =value.getInt("temperatureMin");
                                daily_chart[j][k]=tmin;
                                Log.i("TM",  daily_chart[j][k].toString());

                            }
                            if(k==2){
                                int tmax=value.getInt("temperatureMax");
                                daily_chart[j][k]=tmax;
                                Log.i("TM",  daily_chart[j][k].toString());

                            }
                        }
                    }
                    Log.i("c", (String) daily_chart[0][0]);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("list", daily_chart);
                    intent.putExtras(mBundle);

                    intent.putExtra("SD",daily_chart);
//                  intent.putExtra("daily_chart", daily_chart.toString());
                    for (int i=0;i<8;i++){
                        JSONObject ele=intervals.getJSONObject(i);
                        String r_date=ele.getString("startTime");
                        JSONObject val=ele.getJSONObject("values");
                        float min_tem=val.getInt("temperatureMin");
                        float max_tem=val.getInt("temperatureMax");
                        String wc=val.getString("weatherCode");
                        int ind=l.indexOf(wc);

                        String stautus=weather_status[ind];

                        String ic_name=weather_icon[ind];

                        String d_id="date"+(i+1);
                        String mt_id="min_temp"+(i+1);
                        String mxt_id="max_temp"+(i+1);
                        String I_id="ic"+(i+1);
                        int img_id = getResources().getIdentifier(I_id, "id", getPackageName());
                        ImageView table_Image=(ImageView) findViewById(img_id);
                        int ic_id = getResources().getIdentifier(ic_name, "drawable", getPackageName());
                        table_Image.setImageResource(ic_id);

                        int date_id = getResources().getIdentifier(d_id, "id", getPackageName());
                        int min_tem_id = getResources().getIdentifier(mt_id, "id", getPackageName());
                        int max_tem_id = getResources().getIdentifier(mxt_id, "id", getPackageName());


                        TextView date=(TextView) findViewById(date_id);
                        TextView min_temp=(TextView) findViewById(min_tem_id);
                        TextView max_temp=(TextView) findViewById(max_tem_id);
                        date.setText(getFormatted_Date(r_date));
                        min_temp.setText(String.format("%.0f", min_tem));
                        max_temp.setText(String.format("%.0f", max_tem));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
            }
        });
        queue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search...");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("Submit",query);
                Intent i=new Intent(Searchable.this,Searchable.class);
                i.putExtra("address",query);
                startActivity(i);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                RequestQueue queue= Volley.newRequestQueue(Searchable.this);
                String url="https://helloworld-329001.wm.r.appspot.com/autocompletes?data="+newText;
                StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray main_response=new JSONArray(response);
                            Log.i("AC", String.valueOf(main_response));
                            ListView listView;
                            listView=findViewById(R.id.list_view);
                            String[] name= new String[]{"California","New York","Kansas"};
                            String[] suggestions=new String[main_response.length()];
                            for(int i=0;i<main_response.length();i++){
                                suggestions[i]=main_response.getString(i);
                            }
                            Log.i("S", String.valueOf(suggestions));
                            ArrayAdapter arrayAdapter = new ArrayAdapter<String>(Searchable.this, R.layout.row,suggestions);
                            listView.setAdapter(arrayAdapter);
                            arrayAdapter.getFilter().filter(newText);
                            selectItem(searchView);


//


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", String.valueOf(error));
                    }
                });
//                TextView s_text= findViewById(R.id.ac);
//                Log.i("V", String.valueOf(s_text));
//                if(s_text!=null) {
//                    Log.i("IV", String.valueOf(s_text));
//
//                    s_text.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
////                                                      Intent i=new Intent(MainActivity.this,Searchable.class);
////                                                     startActivity(i);
//                            Log.i("city", String.valueOf(s_text));
//                            String city = (String) s_text.getText();
//                            Log.i("city", city);
//                            searchView.setQuery(city, true);
////
//                        }
////
//                    });
//                }

                queue.add(stringRequest);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void selectItem(SearchView searchView) {
//        ListView s_text= findViewById(R.id.list_view);
//        Log.i("V", String.valueOf(s_text));
//        if(s_text!=null) {
//            Log.i("IV", String.valueOf(s_text));
//
//            s_text.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                                                      Intent i=new Intent(MainActivity.this,Searchable.class);
////                                                     startActivity(i);
//                    Log.i("city", String.valueOf(s_text));
//                    String city = (String) s_text.getText();
//                    Log.i("city", city);
//                    searchView.setQuery(city, true);
////
//                }
////
//            });
//        }

    }

    public String getFormatted_Date(String r_date) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSS'Z'");
        SimpleDateFormat output = new SimpleDateFormat("MM/dd/yyyy");
        Date d = null;
        try
        {
            d = input.parse(r_date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        String formatted = output.format(d);

        return formatted;
    }
    public String getFormatted_C_Date(String r_date) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSS'Z'");
        SimpleDateFormat output = new SimpleDateFormat("MMM d, yyyy");
        Date d = null;
        try
        {
            d = input.parse(r_date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        String formatted = output.format(d);

        return formatted;
    }


}