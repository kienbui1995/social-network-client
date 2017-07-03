package com.joker.thanglong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class TeacherActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FrameLayout frRoot;
    private LinearLayout lnDiemDanh;
    private LinearLayout lnClassroom;
    private LinearLayout lvLichTrinh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        addView();
        addToolbar();
    }

    private void addView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        frRoot = (FrameLayout) findViewById(R.id.fr_root);
        lnDiemDanh = (LinearLayout) findViewById(R.id.lnDiemDanh);
        lnClassroom = (LinearLayout) findViewById(R.id.lnEducation);
        lvLichTrinh = (LinearLayout) findViewById(R.id.lvLichTrinh);
    }
    private void addToolbar() {
        toolbar.setTitle("Chức năng của giảng viên");
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
