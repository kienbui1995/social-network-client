package com.joker.hoclazada;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import adapter.FollowViewPagerAdapter;

public class FollowActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabHost;
    private ViewPager viewPager;
    private FollowViewPagerAdapter followViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        addControl();
        initControl();
    }

    private void initControl() {
        //Toolbar
        toolbar.setTitle("Danh sách Follow");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //Tabhost
        tabHost.setupWithViewPager(viewPager);
        followViewPagerAdapter = new FollowViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(followViewPagerAdapter);
    }

    private void addControl() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabHost = (TabLayout) findViewById(R.id.tabHost);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }
}
