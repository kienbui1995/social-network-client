package com.joker.hoclazada;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import april.yun.JPagerSlidingTabStrip2;
import april.yun.other.JTabStyleDelegate;

import static april.yun.other.JTabStyleBuilder.STYLE_DEFAULT;

public class DemoActivity extends AppCompatActivity {
    private ViewPager pager;
    private JPagerSlidingTabStrip2 tabButtom;
    ViewPagerAdapterDemo viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        pager = (ViewPager) findViewById(R.id.pager);
        tabButtom = (JPagerSlidingTabStrip2) findViewById(R.id.tab_buttom);
        setupStrip(tabButtom.getTabStyleDelegate(), STYLE_DEFAULT);
        tabButtom.getTabStyleDelegate()
                .setFrameColor(Color.TRANSPARENT)
                .setIndicatorColor(Color.TRANSPARENT)
                .setTabIconGravity(Gravity.TOP)//图标显示在top
                .setIndicatorHeight(-8)//设置的高小于0 会显示在tab顶部 否则底部
                .setDividerColor(Color.TRANSPARENT);
        int[] mSelectors = new int[] { R.drawable.tab1, R.drawable.tab2, R.drawable.tab3, R.drawable.tab4 };
        viewPagerAdapter = new ViewPagerAdapterDemo(getSupportFragmentManager(),mSelectors);
        pager.setAdapter(viewPagerAdapter);
        tabButtom.bindViewPager(pager);
        tabButtom.setPromptNum(0,12);
        tabButtom.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabButtom.setPromptNum(0,0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
    private void setupStrip(JTabStyleDelegate tabStyleDelegate, int type) {
        tabStyleDelegate.setJTabStyle(type)
                .setShouldExpand(true)
                .setFrameColor(Color.parseColor("#45C01A"))
                .setTabTextSize(getDimen(R.dimen.tabstrip_textsize))
                .setTextColor(Color.parseColor("#45C01A"),Color.GRAY)
                //.setTextColor(R.drawable.tabstripbg)
                .setDividerColor(Color.parseColor("#45C01A"))
                .setDividerPadding(0)
                .setUnderlineColor(Color.parseColor("#3045C01A"))
                .setUnderlineHeight(0)
                .setIndicatorColor(Color.parseColor("#7045C01A"))
                .setIndicatorHeight(getDimen(R.dimen.sug_event_tabheight));
    }
    private int getDimen(int dimen) {
        return (int) getResources().getDimension(dimen);
    }
}
