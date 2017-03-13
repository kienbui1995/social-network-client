package com.joker.hoclazada.CustomView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.joker.hoclazada.R;

/**
 * Created by joker on 2/11/17.
 */

public class DeleteEditText extends EditText {
    Drawable crossX,noneX;
    Boolean visible = false;
    public DeleteEditText(Context context) {
        super(context);
        khoitao();
    }

    public DeleteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        khoitao();
    }

    public DeleteEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        khoitao();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DeleteEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        khoitao();
    }
    public void khoitao(){
        //Khoi tao cai X
        crossX = ContextCompat.getDrawable(getContext(), R.drawable.ic_clear_black_24dp).mutate();
        noneX = ContextCompat.getDrawable(getContext(),android.R.drawable.screen_background_light_transparent).mutate();
        cauhinh();
    }
    public void cauhinh(){
//        setInputType(InputType.TYPE_CLASS_NUMBER);
        Drawable[] drawables = getCompoundDrawables();
        Drawable drawable = visible? crossX:noneX;
        setCompoundDrawablesWithIntrinsicBounds(drawables[0],drawables[1],drawable,drawables[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && event.getX() >= getRight() - (getCompoundDrawables()[2].getBounds().width()))
        {
            setText("");
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (lengthAfter == 0  && start  == 0)
        {
            visible = false;
            cauhinh();
        }else {
            visible = true;
            cauhinh();
        }


    }
}
