package com.joker.hoclazada;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ChatActivity extends AppCompatActivity {
    private LinearLayout activityChat;
    private Toolbar toolbarNotification;
    private ListView lvMessages;
    private EditText edtNewMessage;
    private ImageView submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        addControll();
        setupTabs();
    }

    private void addControll() {
        activityChat = (LinearLayout) findViewById(R.id.activity_chat);
        toolbarNotification = (Toolbar) findViewById(R.id.toolbarNotification);
        lvMessages = (ListView) findViewById(R.id.lvMessages);
        edtNewMessage = (EditText) findViewById(R.id.edtNewMessage);
        submitButton = (ImageView) findViewById(R.id.submit_button);

    }
    private void setupTabs() {
        toolbarNotification.setTitle("Hoài Nam");
        setSupportActionBar(toolbarNotification);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbarNotification.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbarNotification.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
