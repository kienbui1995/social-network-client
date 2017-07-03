package com.joker.thanglong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.joker.thanglong.Fragment.TestScheduleFragment;

public class StudentActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FrameLayout frRoot;
    private LinearLayout lnDiemDanh;
    private LinearLayout lnClassroom;
    private LinearLayout lnTimetable;
    private LinearLayout lnLichThi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        addView();
        addToolbar();
        addEvent();
    }

    private void addEvent() {
        lnLichThi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().add(R.id.fr_root,new TestScheduleFragment())
                        .addToBackStack(null).commit();
            }
        });

    }

    private void addView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        frRoot = (FrameLayout) findViewById(R.id.fr_root);
        lnDiemDanh = (LinearLayout) findViewById(R.id.lnDiemDanh);
        lnClassroom = (LinearLayout) findViewById(R.id.lnEducation);
        lnTimetable = (LinearLayout) findViewById(R.id.lnGroup);
        lnLichThi = (LinearLayout) findViewById(R.id.lnLichThi);
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
