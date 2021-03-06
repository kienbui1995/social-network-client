package com.joker.thanglong;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import adapter.ManagerMemberGroupPagerAdapter;

public class ManagerMemberGroupActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout idTabLayout;
    private ViewPager viewPager;
    private ManagerMemberGroupPagerAdapter adapter;
    public static int idGr;
    public static int privacy;
    public static boolean isAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_member_group);
        addView();
        initToolbar();
        intTab();
    }

    private void getData() {
        Intent intent = getIntent();
        idGr = intent.getIntExtra("idGroup",1);
        privacy = intent.getIntExtra("privacyGroup",1);
        isAdmin = intent.getBooleanExtra("isAdminGroup",false);
        Log.d("id+privacy",idGr+ " "+ privacy);
    }

    private void intTab() {
        idTabLayout.setupWithViewPager(viewPager);

    }

    private void initToolbar() {
        getData();
        toolbar.setTitle("Thành viên");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //TabHost
        idTabLayout.setupWithViewPager(viewPager);
        adapter = new ManagerMemberGroupPagerAdapter(getSupportFragmentManager(),isAdmin,privacy);
        viewPager.setAdapter(adapter);
    }

    private void addView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        idTabLayout = (TabLayout) findViewById(R.id.idTabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }
}
