package com.joker.thanglong.Fragment;

import com.google.firebase.iid.FirebaseInstanceId;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.joker.thanglong.CustomView.DeleteEditText;
import com.joker.thanglong.CustomView.PasswordEditText;
import com.joker.thanglong.MainActivity;
import com.joker.thanglong.Model.ModelDangNhap;
import com.joker.thanglong.R;
import com.joker.thanglong.Ultil.DeviceUltil;
import com.joker.thanglong.Ultil.SystemHelper;
import com.joker.thanglong.Ultil.VolleyHelper;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import Entity.EntitySetting;
import Entity.EntityUserProfile;
import io.realm.Realm;

import static com.joker.thanglong.Ultil.VolleyHelper.checkErrorCode;

/**
 * Created by joker on 2/10/17.
 */

public class DangNhap extends Fragment implements View.OnClickListener,Validator.ValidationListener{
    private TextView txtForgotPassword;
    Validator validator;
    @Length(min = 6,max=15,message = "Tên đăng nhập phải từ 6 đến 15 kí tự",trim = true)
    private DeleteEditText edtUsername;
    @Password(scheme = Password.Scheme.ANY,min = 6)
    private PasswordEditText edtPassword;
    private Button btnLogin;
    private Realm realm;
    EntityUserProfile entityUserProfile;
    CallbackManager callbackManager;
    DeviceUltil deviceUltil;
    String recoveryEmail;
    Button btnFacebook;
    EntitySetting entitySetting;
    //token

    //Forgot password
    String key;
    String id;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dangnhap,container,false);
        FacebookSdk.sdkInitialize(getContext().getApplicationContext());
        addControl(view);
        //Validate
        validator = new Validator(this);
        validator.setValidationListener(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });
        //Khoi tao realm
        Realm.init(getActivity());
        //Khoi tao doi tuong Realm
        realm = Realm.getDefaultInstance();

        callbackManager = CallbackManager.Factory.create();
        deviceUltil = new DeviceUltil(getActivity());
        deviceUltil.CheckPermission();


        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
            //Save pref
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject, GraphResponse response) {
                                entityUserProfile = new EntityUserProfile();
                                try {
                                    entityUserProfile.setEmail(jsonObject.getString("email"));
                                    entityUserProfile.setFbID(jsonObject.getString("id"));
                                    entityUserProfile.setFirst_name(jsonObject.getString("first_name"));
                                    entityUserProfile.setLast_name(jsonObject.getString("last_name"));
                                    entityUserProfile.setGender(jsonObject.getString("gender"));
                                    entityUserProfile.setAvatar("https://graph.facebook.com/" + jsonObject.getString("id") + "/picture?type=large");
                                    entityUserProfile.setTokenFB(loginResult.getAccessToken().getToken().toString());
                                    //Tao hoac cap nhat du lieu
                                    realm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            realm.copyToRealmOrUpdate(entityUserProfile);
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


//                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//                                SharedPreferences.Editor editor = prefs.edit();
//                                editor.putString("fb_token",loginResult.getAccessToken().getToken() );
//                                editor.apply();
//                                // Getting FB User Data
//                                Bundle facebookData = getFacebookData(jsonObject);
                                Log.d("responeFb", "onCompleted: " + response.toString());
                                checkFacebook(jsonObject);
//                                insertApi(jsonObject);

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name,last_name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
                //Insert API


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
        btnFacebook.setOnClickListener(this);
        return view;
    }

    private void addControl(View view) {
        edtUsername = (DeleteEditText) view.findViewById(R.id.edtUsernameLogin);
        edtPassword = (PasswordEditText) view.findViewById(R.id.edtPassword);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        btnFacebook = (Button) view.findViewById(R.id.btnFacebook);
        txtForgotPassword = (TextView) view.findViewById(R.id.txtForgotPassword);
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(getActivity())
                        .title("Quên mật khẩu")
                        .cancelable(false)
                        .content("Nhập email của bạn")
                        .theme(Theme.LIGHT)
                        .inputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS | InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT)
                        .input("Nhập email", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(final MaterialDialog dialog, CharSequence input) {
                                {
                                    if(TextUtils.isEmpty(input))
                                    {
                                        Toast.makeText(getActivity(), "No emails", Toast.LENGTH_LONG).show();
                                    }else {
                                        HashMap<String,String> params= new HashMap<>();
                                        recoveryEmail = input.toString().trim();
                                        params.put("email",recoveryEmail);
                                        VolleyHelper volleyHelper = new VolleyHelper(getActivity(),getResources().getString(R.string.url));
                                        volleyHelper.post("forgot_password", new JSONObject(params), new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                dialog.dismiss();
                                                Log.d("forgotSuccess", String.valueOf(response));
                                                Handler handler = new Handler();
                                                final MaterialDialog finalDialog = dialog;
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        CheckCode();
                                                    }
                                                },3000);

                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.d("forgotSuccessa", String.valueOf(VolleyHelper.checkErrorCode(error)));
                                            }
                                        });
                                    }

                                }
                                Toast.makeText(getActivity(), input.toString(), Toast.LENGTH_SHORT).show();
                            }

                        }).show();
            }
        });
    }

    private void CheckCode() {
        new  MaterialDialog.Builder(getActivity())
                .title("Xác thực mã")
                .cancelable(false)
                .content("Mời bạn nhập mã xác thực được gửi trong Email")
                .theme(Theme.LIGHT)
                .inputType(InputType.TYPE_CLASS_NUMBER)
                .input("Nhập mã: ", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if(TextUtils.isEmpty(input))
                        {
                            Toast.makeText(getActivity(), "Chưa nhập mã xác thực", Toast.LENGTH_LONG).show();
                        }else {
                            HashMap<String,String> params= new HashMap<>();
                            params.put("email",recoveryEmail);
                            params.put("recovery_code",input.toString().trim());
                            Log.d("recovery",new JSONObject(params).toString());
                            VolleyHelper volleyHelper = new VolleyHelper(getActivity(),getResources().getString(R.string.url));
                            volleyHelper.post("verify_recovery_code", new JSONObject(params), new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("forgotSuccess",response.toString());
                                    try {
                                        JSONObject jsonObject = response.getJSONObject("data");
                                        id = jsonObject.getString("id");
                                        key = jsonObject.getString(("recovery_key"));
                                        Bundle bundle = new Bundle();
                                        bundle.putString("id",id);
                                        bundle.putString("key",key);
                                        ForgotPasswordFragment forgotPasswordFragment = new ForgotPasswordFragment();
                                        forgotPasswordFragment.setArguments(bundle);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.setCustomAnimations(R.anim.slide_out_right,R.anim.slide_out_right);
                                        fragmentTransaction.replace(R.id.view_root,forgotPasswordFragment);
                                        fragmentTransaction.commit();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("forgotError", String.valueOf(VolleyHelper.checkErrorCode(error)));

                                }
                            });
                        }
                    }
                }).show();
    }

    private void eventDangNhapThuCong() {
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        String device = SystemHelper.MD5(deviceUltil.getImei());

        VolleyHelper volleyHelper = new VolleyHelper(getActivity(),getResources().getString(R.string.url));
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("username",username);
        params.put("password",password);
        params.put("device",device);
        volleyHelper.post("login", new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                    startActivity(new Intent(getActivity(),MainActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                checkErrorCode(error);
            }
        });


    }

    @Override
    public void onClick(View view) {
        LoginManager.getInstance().logInWithReadPermissions(DangNhap.this, Arrays.asList("public_profile","email"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    private Bundle getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();

        try {
            String id = object.getString("id");
            URL profile_pic;
            try {
                profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?type=small");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));

            ModelDangNhap modelDangNhap = new ModelDangNhap(getActivity());
            modelDangNhap.saveFacebookUserInfo(id,object.getString("first_name"),
                    object.getString("last_name"),object.getString("email"),
                    object.getString("gender"), profile_pic.toString());

        } catch (Exception e) {
            Log.d("error", "BUNDLE Exception : "+e.toString());
        }

        return bundle;
    }
    public void insertApi(JSONObject jsonObject)
    {
        //Insert APi
        EntityUserProfile profile = realm.where(EntityUserProfile.class).findFirst();
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("username",profile.getFirst_name());
        params.put("facebook_id",profile.getFbID());
        params.put("first_name",profile.getFirst_name());
        params.put("last_name",profile.getLast_name());
        params.put("email",profile.getEmail());
        params.put("gender",profile.getGender());

        Log.d("hashMap", profile.toString());
        VolleyHelper volleyHelper = new VolleyHelper(getActivity(),getResources().getString(R.string.url));
        volleyHelper.post("sign_up", new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("loginFB",response.toString());
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                checkErrorCode(error);
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        {
            //Dang nhap thu cong
            String username = edtUsername.getText().toString().trim();
            String password = SystemHelper.MD5(edtPassword.getText().toString());
            entityUserProfile = new EntityUserProfile();

            //Request
            VolleyHelper volleyHelper = new VolleyHelper(getActivity(),getResources().getString(R.string.url),false);
            HashMap<String,String> params = new HashMap<String, String>();
            params.put("username",username);
            params.put("password",password);
            params.put("device", FirebaseInstanceId.getInstance().getToken().toString());
            Log.d("device",FirebaseInstanceId.getInstance().getToken().toString());
            volleyHelper.post("login", new JSONObject(params), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject json = response.getJSONObject("data");
                        entityUserProfile.setToken(json.getString("token"));
                        entityUserProfile.setuID(json.getInt("id"));
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("token",json.getString("token") );
                        editor.apply();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealmOrUpdate(entityUserProfile);
                                Log.d("EntityObj",entityUserProfile.getuID()+"");
                                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                                progressDialog.show();
                                progressDialog.setMessage("Xin hãy chờ trong giây lát");
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        startActivity(new Intent(getActivity(),MainActivity.class));
                                    }
                                },1000);
                            }
                        });
                        initSetting();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (checkErrorCode(error) == 412 || checkErrorCode(error) == 409){
                        edtUsername.setError("Thông tin tài khoản không hợp lệ");
                    }
//                    Log.d("loginError", checkErrorCode(error)+"");
                }
            });


        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
    public void checkFacebook(JSONObject jsonObject){
        VolleyHelper volleyHelper = new VolleyHelper(getActivity(),getResources().getString(R.string.url));
        entityUserProfile = new EntityUserProfile();
        entityUserProfile = realm.where(EntityUserProfile.class).findFirst();
        HashMap<String,String> params = new HashMap<>();
        DeviceUltil deviceUltil = new DeviceUltil(getActivity());
        deviceUltil.CheckPermission();
        params.put("id",entityUserProfile.getFbID());
        params.put("access_token",entityUserProfile.getTokenFB());
        params.put("device",deviceUltil.getImei());
        Log.d("loginFacebook",new JSONObject(params).toString());
        volleyHelper.post("login_facebook", new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    final String json = response.getJSONObject("data").getString("token");


                    Log.d("dataToken1",json.toString());
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            entityUserProfile.setToken(json);
                            realm.copyToRealmOrUpdate(entityUserProfile);
                        }
                    });
                    Log.d("dataToken",entityUserProfile.getTokenFB());
                    startActivity(new Intent(getActivity(),MainActivity.class));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int code = VolleyHelper.checkErrorCode(error);
                if (code == 410)
                {
                    FacebookSignUpFragment facebookSignUpFragment = new FacebookSignUpFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.slide_out_right,R.anim.slide_out_right);
                    fragmentTransaction.replace(R.id.view_root,facebookSignUpFragment);
                    fragmentTransaction.commit();
                }else {
                    Toast.makeText(getActivity(), "Code Error: "+ code, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void initSetting(){
        entitySetting = new EntitySetting();
        entitySetting.setNewsfeed(1);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(entitySetting);
            }
        });
    }
}
