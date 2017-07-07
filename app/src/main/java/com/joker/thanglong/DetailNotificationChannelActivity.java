package com.joker.thanglong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joker.thanglong.Model.ChannelModel;
import com.joker.thanglong.Ultil.SystemHelper;

import Entity.EntityNotificationChannel;

public class DetailNotificationChannelActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView avatar;
    private TextView txtNameChannel;
    private TextView txtShortNamePage;
    private Button btnMessagePage;
    private TextView txtPlace;
    private TextView txtDateTime;
    private TextView txtMessage;
    private ImageView imgPhoto;
    private TextView txtTitle;
    ChannelModel channelModel;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_notification_channel);
        id= getIntent().getIntExtra("id",1);
        addControl();
        addToolbar();
        addData();
    }



    private void addData() {
        channelModel = new ChannelModel(getApplicationContext());
        channelModel.getDetailNotiChannel(id, new ChannelModel.VolleyCallbackDetailNotificationChannel() {
            @Override
            public void onSuccess(EntityNotificationChannel entityNotificationChannel) {
                txtNameChannel.setText(entityNotificationChannel.getChannel().getName());
                txtMessage.setText(entityNotificationChannel.getMessage());
                txtTitle.setText(entityNotificationChannel.getTittle());
                Glide.with(getApplicationContext()).load(entityNotificationChannel.getChannel().getAvatar())
                        .crossFade().into(avatar);
                txtDateTime.setText(SystemHelper.getTimeAgo(entityNotificationChannel.getCreated_at())+"");
                if (entityNotificationChannel.getPlace() == null){
                    txtPlace.setText(entityNotificationChannel.getChannel().getName());
                }else {
                    txtPlace.setText(entityNotificationChannel.getPlace());
                }
                if (entityNotificationChannel.getPhoto() != null){
                    imgPhoto.setVisibility(View.VISIBLE);
                    Glide.with(getApplicationContext()).load(entityNotificationChannel.getPhoto())
                            .crossFade().into(imgPhoto);
                }
            }
        });
    }

    private void addControl() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        avatar = (ImageView) findViewById(R.id.avatar);
        txtNameChannel = (TextView) findViewById(R.id.txtNameChannel);
        txtShortNamePage = (TextView) findViewById(R.id.txtShortNamePage);
        btnMessagePage = (Button) findViewById(R.id.btnMessagePage);
        txtPlace = (TextView) findViewById(R.id.txtPlace);
        txtDateTime = (TextView) findViewById(R.id.txtDateTime);
        txtMessage = (TextView) findViewById(R.id.txtMessage);
        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
    }
    private void addToolbar() {
        toolbar.setTitle("Xem bài viết");
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
