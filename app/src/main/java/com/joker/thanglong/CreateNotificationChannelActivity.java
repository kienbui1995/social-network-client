package com.joker.thanglong;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.joker.thanglong.CustomView.DeleteEditText;
import com.joker.thanglong.Model.ChannelModel;
import com.joker.thanglong.Model.PostModel;

import Entity.EntityNotificationChannel;

public class CreateNotificationChannelActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DeleteEditText txtTitle;
    private EditText txtMessage;
    private EditText txtTime;
    private EditText txtPlace;
    private FloatingActionButton fabSubmit;
    private ChannelModel channelModel;
    private EntityNotificationChannel entityNotificationChannel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notification_channel);
        addView();
        addToolbar();
        fabSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtTitle.getText().toString().isEmpty() && txtMessage.getText().toString().isEmpty()){
                    txtTitle.setError("Không được để trống");
                    txtMessage.setError("Không được để trống");
                }else {
                    writeNoti();
                }
            }
        });
    }

    private void writeNoti() {
        channelModel = new ChannelModel(this);
        entityNotificationChannel = new EntityNotificationChannel();
        entityNotificationChannel.setChannel(ChannelActivity.channelInfo);
        entityNotificationChannel.setMessage(txtMessage.getText().toString().trim());
        entityNotificationChannel.setTime(txtTime.getText().toString().trim());
        entityNotificationChannel.setTittle(txtTitle.getText().toString().trim());
        entityNotificationChannel.setPlace(txtPlace.getText().toString().trim());
        channelModel.writeNotification(entityNotificationChannel, new PostModel.VolleyCallBackCheck() {
            @Override
            public void onSuccess(boolean status) {
                Toast.makeText(CreateNotificationChannelActivity.this, "Cập nhật thông báo thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void addToolbar() {
        toolbar.setTitle("Viết thông báo");
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

    private void addView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTitle = (DeleteEditText) findViewById(R.id.txtTitle);
        txtMessage = (EditText) findViewById(R.id.txtMessage);
        txtTime = (EditText) findViewById(R.id.txtTime);
        txtPlace = (EditText) findViewById(R.id.txtPlace);
        fabSubmit = (FloatingActionButton) findViewById(R.id.fabSubmit);
    }

}
