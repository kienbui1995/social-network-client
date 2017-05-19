package com.joker.thanglong.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.joker.thanglong.CustomView.PasswordEditText;
import com.joker.thanglong.R;
import com.joker.thanglong.Ultil.SystemHelper;
import com.joker.thanglong.Ultil.VolleyHelper;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPasswordFragment extends Fragment {
    private PasswordEditText edtNewPassword;
    private PasswordEditText edtRepeatPassword;
    private Button btnChangePassword;
    String key;
    String id;


    public ForgotPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        key = getArguments().getString("key");
        id = getArguments().getString("id");
        addControl(view);
        addEvent();
        return view;
    }
    private void addEvent() {
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> params= new HashMap<>();
                params.put("id",id);
                params.put("recovery_key",key);
                params.put("new_password", SystemHelper.MD5(edtNewPassword.getText().toString().trim()));
                Log.d("jsonChangePassword",new JSONObject(params).toString());
                VolleyHelper volleyHelper = new VolleyHelper(getActivity(),getResources().getString(R.string.url));
                volleyHelper.put("renew_password", new JSONObject(params), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("ChangePasswordSuc",response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ChangePasswordErr", String.valueOf(VolleyHelper.checkErrorCode(error)));

                    }
                });
            }
        });
    }
    private void addControl(View view) {
        edtNewPassword = (PasswordEditText) view.findViewById(R.id.edtNewPassword);
        edtRepeatPassword = (PasswordEditText) view.findViewById(R.id.edtRepeatPassword);
        btnChangePassword = (Button) view.findViewById(R.id.btnChangePassword);
    }
}
