package com.joker.thanglong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.joker.thanglong.Fragment.TrackStudentFragment;

public class TrackerActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private LinearLayout lnStudent;
    private LinearLayout lnRoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        addControl();
        addToolbar();
        addEvent();
    }

    private void addEvent() {
        lnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fr_root,new TrackStudentFragment()).addToBackStack(null)
                .commit();
            }
        });
    }

    private void addControl() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lnStudent = (LinearLayout) findViewById(R.id.lnStudent);
        lnRoom = (LinearLayout) findViewById(R.id.lnEducation);
    }
    private void addToolbar() {
        toolbar.setTitle("Chức năng của giám thị");
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
