package com.joker.thanglong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import adapter.AdapterListViewNotification;

public class NotificationActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView lvNotifi;
    ArrayList<String> dsNotification;
    AdapterListViewNotification adapterListViewNotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        addControll();

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
        lvNotifi = (ListView) findViewById(R.id.lvThongBao);
        dsNotification = new ArrayList<>();
        dsNotification.add("1");
        dsNotification.add("1");
        dsNotification.add("1");
        dsNotification.add("1");
        dsNotification.add("1");
        dsNotification.add("1");
        dsNotification.add("1");
        dsNotification.add("1");
        dsNotification.add("1");
        dsNotification.add("1");
        dsNotification.add("1");
        dsNotification.add("1");
        dsNotification.add("1");
        dsNotification.add("1");
        dsNotification.add("1");
        dsNotification.add("1");
        adapterListViewNotification = new AdapterListViewNotification(this,R.layout.custom_notification,dsNotification);
        lvNotifi.setAdapter(adapterListViewNotification);

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
