package com.joker.thanglong;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.joker.thanglong.Ultil.SettingUtil;

public class SettingActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RadioGroup rgNewFeed;
    private RadioButton rbNew;
    private RadioButton rbPopular;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        addControl();
        initTab();
        getSetting();
        changeSetting();
    }

    private void changeSetting() {
        //Set Newfeed
        rgNewFeed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                int a = rgNewFeed.indexOfChild(findViewById(i));
                SettingUtil.getSettingUtil(getApplicationContext()).setNewsfeed(a+1);
                Toast.makeText(SettingActivity.this, "Thay đổi thành công", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getSetting() {
        //GetNewFeedSetting
        switch (SettingUtil.getSettingUtil(this).getSetting().getNewsfeed()){
            case 1:
                rbNew.setChecked(true);
                break;
            case 2:
                rbPopular.setChecked(true);
                break;
        }
    }



    private void initTab() {
        toolbar.setTitle("Cài đặt");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
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
    }

    private void addControl() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rgNewFeed = (RadioGroup) findViewById(R.id.rgNews);
        rbNew = (RadioButton) findViewById(R.id.rbNews);
        rbPopular = (RadioButton) findViewById(R.id.rbPopular);
    }
}
