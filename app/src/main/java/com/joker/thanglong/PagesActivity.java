package com.joker.thanglong;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import adapter.PagesViewPagerAdapter;

public class PagesActivity extends AppCompatActivity {
    private ImageView imgAnhBia;
    private ImageButton imgThayAnhBia;
    private Toolbar toolbar;
    private ImageView imageView;
    private TextView txtNamePage;
    private TextView txtShortNamePage;
    private Button btnFollowPage;
    private Button btnMessagePage;
    private TabLayout tabHost;
    private FrameLayout frContent;
    private ViewPager viewPager;
    private PagesViewPagerAdapter pagesViewPagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pages);
        addControll();
        addEvent();
    }

    private void addControll() {
        imgAnhBia = (ImageView) findViewById(R.id.imgAnhBia);
        imgThayAnhBia = (ImageButton) findViewById(R.id.imgThayAnhBia);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageView = (ImageView) findViewById(R.id.imageView);
        txtNamePage = (TextView) findViewById(R.id.txtNamePage);
        txtShortNamePage = (TextView) findViewById(R.id.txtShortNamePage);
        btnFollowPage = (Button) findViewById(R.id.btnFollowPage);
        btnMessagePage = (Button) findViewById(R.id.btnMessagePage);
        tabHost = (TabLayout) findViewById(R.id.tabHost);
        frContent = (FrameLayout) findViewById(R.id.frContent);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }
    private void addEvent() {
        toolbar.setTitle("Tin tức Thăng Long");
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
        tabHost.setupWithViewPager(viewPager);
        pagesViewPagerAdapter = new PagesViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagesViewPagerAdapter);
    }
}
