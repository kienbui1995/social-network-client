package com.joker.thanglong.Ultil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.joker.thanglong.R;

/**
 * Created by joker on 5/15/17.
 */

public class CommentUlti {
    private RecyclerView rcvComment;
    private EditText btnCommentInput;
    private ImageButton btnSubmmitComment;
    private Button btnLikeList;
    private PopupWindow popWindow;

    public CommentUlti(){

    }

    public void onShowUp(View v,Activity activity, int position){
        LayoutInflater layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflate the custom popup layout
        View inflatedView = layoutInflater.inflate(R.layout.activity_comment_post, null,false);
        //Inint control
        btnLikeList = (Button) inflatedView.findViewById(R.id.btnStatusLike);
        rcvComment = (RecyclerView) inflatedView.findViewById(R.id.rcvComment);
        btnCommentInput = (EditText) inflatedView.findViewById(R.id.btnCommentInput);
        btnSubmmitComment = (ImageButton) inflatedView.findViewById(R.id.btnSubmmitComment);
        Display display = activity.getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        popWindow = new PopupWindow(inflatedView, width,height, true );
        // set a background drawable with rounders corners
        popWindow.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.fb_popup_bg));
        popWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popWindow.setAnimationStyle(R.style.PopupAnimation);
        popWindow.setOutsideTouchable(true);
        // show the popup at bottom of the screen and set some margin at bottom ie,
        popWindow.showAtLocation(v, Gravity.BOTTOM, 0,130);
    }

}
