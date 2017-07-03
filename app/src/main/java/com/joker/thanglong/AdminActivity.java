package com.joker.thanglong;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class AdminActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FrameLayout frRoot;
    private LinearLayout lnUsers;
    private LinearLayout lnEducation;
    private LinearLayout lnGroup;
    private LinearLayout lnChannel;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        addControl();
        addToolbar();
        addEvent();
    }

    private void addEvent() {
        lnGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CreateGroupActivity.class));
            }
        });
        lnUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().add(R.id.fr_root,new ManageUserFragment())
                        .addToBackStack(null).commit();
            }
        });
    }

    private void addControl() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        frRoot = (FrameLayout) findViewById(R.id.fr_root);
        lnUsers = (LinearLayout) findViewById(R.id.lnUsers);
        lnEducation = (LinearLayout) findViewById(R.id.lnEducation);
        lnGroup = (LinearLayout) findViewById(R.id.lnGroup);
        lnChannel = (LinearLayout) findViewById(R.id.lnChannel);
    }
    private void addToolbar() {
        toolbar.setTitle("Chức năng của sinh viên");
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

    }
}
