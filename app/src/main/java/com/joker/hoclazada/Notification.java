package com.joker.hoclazada;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by joker on 2/9/17.
 */

public class Notification extends AppCompatActivity{
    public Notification(String s) {

        setContentView(R.layout.custom_item_giohang);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi = inflater.inflate(R.layout.custom_item_giohang,null);
        TextView notification = (TextView) findViewById(R.id.txtNotification);
        notification.setText(s);
    }
}
