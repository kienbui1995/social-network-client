package com.joker.thanglong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;

import adapter.AdapterComment;

public class CommentPostActivity extends AppCompatActivity {
    private PopupWindow popWindow;
    private ListView lvComment;
    ArrayList<String> dsComment;
    AdapterComment adapterComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
