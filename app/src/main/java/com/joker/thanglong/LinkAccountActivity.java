package com.joker.thanglong;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.joker.thanglong.Model.PostModel;
import com.joker.thanglong.Model.UserModel;
import com.joker.thanglong.Ultil.ProfileInstance;

public class LinkAccountActivity extends AppCompatActivity {
    private EditText edtEmail;
    private Button btnSend;
    private Toolbar toolbar;
    private UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_account);
        addControl();
        addEvent();
        addToolbar();
    }

    private void addEvent() {
        userModel = new UserModel(this);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userModel.requestLink(edtEmail.getText().toString().trim(),
                        ProfileInstance.getProfileInstance(getApplicationContext()).getProfile().getuID(), new UserModel.VolleyCallBackCode() {
                            @Override
                            public void onSuccess(final String code) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        checkCode(code);
                                    }
                                },2000);
                            }
                        });
            }
        });

    }

    private void checkCode(final String code) {
        new  MaterialDialog.Builder(LinkAccountActivity.this)
                .title("Xác thực mã")
                .cancelable(false)
                .content("Mời bạn nhập mã xác thực được gửi trong Email")
                .theme(Theme.LIGHT)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("Nhập mã: ", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if(TextUtils.isEmpty(input))
                        {
                            Toast.makeText(getApplicationContext(), "Chưa nhập mã xác thực", Toast.LENGTH_LONG).show();
                        }else {
                            userModel.authenLink(code, input.toString(), new PostModel.VolleyCallBackCheck() {
                                @Override
                                public void onSuccess(boolean status) {
                                    Toast.makeText(getApplicationContext(), "Xác thực thành công, mời đăng nhập lại", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).show();
    }

    private void addToolbar() {
        toolbar.setTitle("Liên kết mã cá nhân");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStackImmediate();
            }
        });
    }
    private void addControl(){
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        btnSend = (Button) findViewById(R.id.btnSend);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }
}
