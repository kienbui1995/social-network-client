package com.joker.hoclazada.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.joker.hoclazada.R;

/**
 * Created by joker on 2/11/17.
 */

public class PasswordEditText extends EditText {
    Drawable eye,eyeStrike;
    boolean visible= false;
    boolean useStrike = false;
    int ALPHA = (int) (255 * .55f);
    public PasswordEditText(Context context) {
        super(context);
        khoitao(null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        khoitao(attrs);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        khoitao(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        khoitao(attrs);
    }
    private void khoitao(AttributeSet attrs)
    {
        //Kiem tra thuoc tinh xem co cai dat khong
        if (attrs !=null)
        {
            TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.PasswordEditText,0,0);
            this.useStrike = array.getBoolean(R.styleable.PasswordEditText_useStrike,false);
        }
        //Tao 2 cai hinh
        eye = ContextCompat.getDrawable(getContext(),R.drawable.ic_visibility_black_24dp).mutate();
        eyeStrike = ContextCompat.getDrawable(getContext(),R.drawable.ic_visibility_off_black_24dp).mutate();
        caidat();
    }
    private void caidat(){
        setInputType(InputType.TYPE_CLASS_TEXT | (visible? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                InputType.TYPE_TEXT_VARIATION_PASSWORD));
        Drawable[] drawables = getCompoundDrawables(); //Tra ve 1 mang cac drawer trai tren phai duoi
        Drawable drawable = useStrike && !visible? eyeStrike : eye; // Kiem tra xem co set EyeStrike;
        drawable.setAlpha(ALPHA);
        setCompoundDrawablesWithIntrinsicBounds(drawables[0],drawables[1],drawable,drawables[3]);
    }

    //Ke thua cai phuong thuc cham
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && event.getX() >= getRight() - (getCompoundDrawables()[2].getBounds().width()) )//Kiem tra nguoi dung co nhan vao man hinh khong
        {
            visible = !visible;
            caidat();
            invalidate(); // Kiem tra lai man hinh
        }
        return super.onTouchEvent(event);
    }
}
