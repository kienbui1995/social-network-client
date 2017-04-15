package com.joker.hoclazada;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import adapter.AdapterHomeListGroup;

public class HomeListGroupActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout idTabLayout;
    ViewPager viewPager;
    AdapterHomeListGroup adapterHomeListGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_list_group);
        idTabLayout = (TabLayout) findViewById(R.id.idTabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Quản lý nhóm ");
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        idTabLayout.setupWithViewPager(viewPager);
        adapterHomeListGroup = new AdapterHomeListGroup(getSupportFragmentManager());
        viewPager.setAdapter(adapterHomeListGroup);



    }



    private void initData() {

    }




}
