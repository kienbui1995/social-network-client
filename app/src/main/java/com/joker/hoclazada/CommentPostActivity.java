package com.joker.hoclazada;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import adapter.AdapterComment;

public class CommentPostActivity extends AppCompatActivity {
    private ListView lvComment;
    ArrayList<String> dsComment;
    AdapterComment adapterComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_post);
        addControl();

    }

    private void addControl() {
        lvComment = (ListView) findViewById(R.id.lvComment);
        dsComment = new ArrayList<>();
        dsComment.add("1");
        dsComment.add("1");
        dsComment.add("1");
        dsComment.add("1");
        dsComment.add("1");
        dsComment.add("1");
        dsComment.add("1");
        dsComment.add("1");
        dsComment.add("1");
        dsComment.add("1");
        dsComment.add("1");
        adapterComment = new AdapterComment(this,R.layout.custom_comment,dsComment);
        lvComment.setAdapter(adapterComment);

    }
}
