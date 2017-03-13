package com.joker.hoclazada.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.joker.hoclazada.MainActivity;
import com.joker.hoclazada.R;

import java.util.Arrays;

/**
 * Created by joker on 2/10/17.
 */

public class DangNhap extends Fragment implements View.OnClickListener{
    Button btnFacebook;
    CallbackManager callbackManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dangnhap,container,false);
        FacebookSdk.sdkInitialize(getContext().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }

            @Override
            public void onCancel() {
                Log.d("kiemtra","thoat");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("kiemtra","loi");
            }
        });
        btnFacebook = (Button) view.findViewById(R.id.btnFacebook);
        btnFacebook.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        LoginManager.getInstance().logInWithReadPermissions(DangNhap.this, Arrays.asList("public_profile","user_friends"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
