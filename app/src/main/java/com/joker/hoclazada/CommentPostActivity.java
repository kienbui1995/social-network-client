package com.joker.hoclazada;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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
//        lvComment.setAdapter(adapterComment);
    }

    public void onShowPopup(View v){

        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // inflate the custom popup layout
        final View inflatedView = layoutInflater.inflate(R.layout.activity_comment_post, null,false);
        // find the ListView in the popup layout

        // get device size
        Display display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
//        mDeviceHeight = size.y;

        // fill the data to the list items
        addControl();


        // set height depends on the device size
        popWindow = new PopupWindow(inflatedView, size.x - 50,size.y - 400, true );
        // set a background drawable with rounders corners
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.fb_popup_bg));
        // make it focusable to show the keyboard to enter in `EditText`
        popWindow.setFocusable(true);
        // make it outside touchable to dismiss the popup window
        popWindow.setOutsideTouchable(true);

        // show the popup at bottom of the screen and set some margin at bottom ie,
        popWindow.showAtLocation(v, Gravity.BOTTOM, 0,100);
    }
}
