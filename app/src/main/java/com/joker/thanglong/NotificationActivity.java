package com.joker.thanglong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.joker.thanglong.Model.NotificationModel;

import java.util.ArrayList;

import Entity.EntityNotification;
import adapter.AdapterNotification;

public class NotificationActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rcvListNotification;
    ArrayList<EntityNotification> dsNotification;
    AdapterNotification adapterNotification;
    RecyclerView.LayoutManager layoutManager;
    NotificationModel notificationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        addControll();
        addToolbar();
        initData();
    }

    private void initData() {
        notificationModel = new NotificationModel(this);
        dsNotification = new ArrayList<>();
        notificationModel.getNotification(new NotificationModel.VolleyCallBackNotification() {
            @Override
            public void onSuccess(ArrayList<EntityNotification> notifications) {
                dsNotification = notifications;
                adapterNotification = new AdapterNotification(getApplicationContext(),dsNotification);
                layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                rcvListNotification.setLayoutManager(layoutManager);
                rcvListNotification.setAdapter(adapterNotification);
                adapterNotification.notifyDataSetChanged();
            }
        });

    }

    private void addToolbar() {
        toolbar.setTitle("Thông báo");
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

    private void addControll() {
        toolbar = (Toolbar) findViewById(R.id.toolbarNotification);
        rcvListNotification = (RecyclerView) findViewById(R.id.rcvListNotication);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.thongbao,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
