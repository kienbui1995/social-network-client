package com.joker.hoclazada;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class SignUpIn extends AppCompatActivity {
    private LinearLayout activitySignUpIn;
    private Toolbar idToolbar;
    private TabLayout idTabLayout;
    ViewPager viewPager;
    LoginViewPagerAdapter loginViewPagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_in);
        addControll();
        //Toolbar
        idToolbar.setTitle("Trang đăng nhập/đăng ký");
        setSupportActionBar(idToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
        idToolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        idToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //TabHost
        idTabLayout.setupWithViewPager(viewPager);
        loginViewPagerAdapter = new LoginViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(loginViewPagerAdapter);



    }

    private void addControll() {
        idToolbar = (Toolbar) findViewById(R.id.idToolbar);
        idTabLayout = (TabLayout) findViewById(R.id.idTabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
