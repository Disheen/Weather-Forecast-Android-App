package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

public class Detailed extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        viewPager=(ViewPager) findViewById(R.id.view_pager_id);
        String location=getIntent().getStringExtra("location");
//        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar); //here toolbar is your id in xml
        getSupportActionBar().setTitle(location); //string is custom name you want

        ViewPageAdapter adapter=new ViewPageAdapter(getSupportFragmentManager());
//        adapter.addFragment(new FragmentToday(),"Today");
//        adapter.addFragment(new FragmentWeekly(),"Weeky");
//        adapter.addFragment(new FragmentData(),"Weather Data");
        adapter.addFragment(new FragmentToday(),"Today");
        adapter.addFragment(new f_Data(),"Weeky");
        adapter.addFragment(new f_Data2(),"Weather Data");

        viewPager.setAdapter(adapter);
        tabLayout=(TabLayout) findViewById(R.id.tabLayout_id);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.calendar_today);
        tabLayout.getTabAt(1).setIcon(R.drawable.trending_up);
        tabLayout.getTabAt(2).setIcon(R.drawable.thermometer);

        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);





    }
    public void tweet(MenuItem item){
        String temp=getIntent().getStringExtra("temp");
        String addr=getIntent().getStringExtra("location");
        Log.i("add",addr);
        Log.i("tem",temp);

        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/intent/tweet?text=Check out "+addr+"'s weather! It is "+temp+"°F!&hashtags=CSCI571WeatherSearch"));
        startActivity(intent);
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.twitter,menu);
        return  true;
    }

//    @Override
//    protected void onStop() {

//        super.onStop();
//        Log.i("ST","STOP");
//        getSupportActionBar().setTitle("Los Angeles, California"); //string is custom name you want
//
//    }
}