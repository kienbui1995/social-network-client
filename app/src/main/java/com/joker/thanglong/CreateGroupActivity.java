package com.joker.thanglong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CreateGroupActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private LinearLayout linearLayout6;
    private LinearLayout linearLayout5;
    private TextView textView22;
    private Button textView23;
    private LinearLayout linearLayout7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        addView();
        addToolbar();
    }

    private void addView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        linearLayout6 = (LinearLayout) findViewById(R.id.linearLayout6);
        linearLayout5 = (LinearLayout) findViewById(R.id.linearLayout5);
        textView22 = (TextView) findViewById(R.id.textView22);
        textView23 = (Button) findViewById(R.id.textView23);
        linearLayout7 = (LinearLayout) findViewById(R.id.linearLayout7);
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
