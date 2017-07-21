package com.joker.thanglong;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.joker.thanglong.Fragment.Group.TimetableFragment;
import com.joker.thanglong.Model.TimeTableModel;
import com.joker.thanglong.Ultil.ProfileInstance;

import java.util.ArrayList;
import java.util.HashMap;

import Entity.EntityTerm;


public class TimeTableActivity extends AppCompatActivity {
    private Spinner txtInfoTimeTable;
    private TimeTableModel timeTableModel;
    private FrameLayout frRoot;
    public static int term;
    public static int check;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        check = getIntent().getIntExtra("check",1);
        timeTableModel = new TimeTableModel(this);
        addControl();
        setupSpinner();
        addToolbar();
    }

    private void addToolbar() {
        toolbar.setTitle("Thời khóa biểu");
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


    private void addControl() {
        txtInfoTimeTable = (Spinner) findViewById(R.id.spnTerm);
        frRoot = (FrameLayout) findViewById(R.id.frRoot);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }
    private void setupSpinner() {
        if (check==1){
            timeTableModel.getTerm(ProfileInstance.getProfileInstance(this).getProfile().getCode(),new TimeTableModel.VolleyCallbackGetTerm() {
                @Override
                public void onSuccess(final ArrayList<EntityTerm> itemsTerm) {
                    ArrayList<String> listNameTerm = new ArrayList<String>();
                    for (int i = 0; i < itemsTerm.size(); i++) {
                        listNameTerm.add(itemsTerm.get(i).getName());
                    }
                    final HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
                    for (int i = 0; i < itemsTerm.size(); i++)
                    {
                        spinnerMap.put((i+1),itemsTerm.get(i).getName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item_term,listNameTerm);
                    txtInfoTimeTable.setAdapter(adapter);
                    txtInfoTimeTable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            term=itemsTerm.get(i).getCode();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getSupportFragmentManager().beginTransaction().replace(R.id.frRoot,new TimetableFragment(),"timetable")
                                            .addToBackStack(null).commit();
                                }
                            },1000);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            });
        }
        else if (check == 2){
            timeTableModel.getTeacherTerm(ProfileInstance.getProfileInstance(this).getProfile().getCode(),new TimeTableModel.VolleyCallbackGetTerm() {
                @Override
                public void onSuccess(final ArrayList<EntityTerm> itemsTerm) {
                    ArrayList<String> listNameTerm = new ArrayList<String>();
                    for (int i = 0; i < itemsTerm.size(); i++) {
                        listNameTerm.add(itemsTerm.get(i).getName());
                    }
                    final HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
                    for (int i = 0; i < itemsTerm.size(); i++)
                    {
                        spinnerMap.put((i+1),itemsTerm.get(i).getName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item_term,listNameTerm);
                    txtInfoTimeTable.setAdapter(adapter);
                    txtInfoTimeTable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            term=itemsTerm.get(i).getCode();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getSupportFragmentManager().beginTransaction().replace(R.id.frRoot,new TimetableFragment(),"timetable")
                                            .addToBackStack(null).commit();
                                }
                            },1000);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            });

        }
    }
}
