package com.joker.hoclazada.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.joker.hoclazada.CustomView.DeleteEditText;
import com.joker.hoclazada.CustomView.PasswordEditText;
import com.joker.hoclazada.MainActivity;
import com.joker.hoclazada.R;
import com.joker.hoclazada.Ultil.SystemHelper;
import com.joker.hoclazada.Ultil.VolleyHelper;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropSquareTransformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import Entity.EntityUserProfile;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */

public class FacebookSignUpFragment extends Fragment implements Validator.ValidationListener {
    Validator validator;
    @Length(min = 6,max=15,message = "Tên đăng nhập phải từ 6 đến 15 kí tự")
    private DeleteEditText edtUserName;
    @Password(scheme = Password.Scheme.ANY,min = 7)
    private PasswordEditText edtPassword;
    private Button btnSignUp;
    private ImageView imgAvatarFacebook;
    private TextView txtFullNameFacebook;
    private TextView txxEmailFacebook;
    private Realm realm;
    private EntityUserProfile entityUserProfile;
    public FacebookSignUpFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_facebook_sign_up, container, false);
        addView(view);
        //Validate
        validator = new Validator(this);
        validator.setValidationListener(this);
        initRealm();
        addEvent();
        return view;
    }

    private void insertAPI() {
        //Insert APi
        EntityUserProfile profile = realm.where(EntityUserProfile.class).findFirst();
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("username",edtUserName.getText().toString());
        params.put("facebook_id",profile.getFbID());
        params.put("password", SystemHelper.MD5(edtPassword.getText().toString()));
        params.put("first_name",profile.getFirst_name());
        params.put("last_name",profile.getLast_name());
        params.put("email",profile.getEmail());
        params.put("gender",profile.getGender());
        params.put("facebook_token",profile.getTokenFB());
        Log.d("hashMap", profile.toString());
        VolleyHelper volleyHelper = new VolleyHelper(getActivity(),getResources().getString(R.string.url));
        volleyHelper.post("sign_up", new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                {
                    try {
                        JSONObject json = response.getJSONObject("data");
                        entityUserProfile.setToken(json.getString("token"));
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealmOrUpdate(entityUserProfile);
                                Log.d("EntityObj",entityUserProfile.getUserName());
                            }
                        });
                        startActivity(new Intent(getActivity(),MainActivity.class));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (VolleyHelper.checkErrorCode(error) == 376)
                {
                    edtUserName.setError("Tên đăng nhập đã tồn tại");
                }
            }
        });

    }

    private void addEvent() {
        EntityUserProfile entityUserProfile = realm.where(EntityUserProfile.class).findFirst();
        Picasso.with(getActivity()).load(entityUserProfile.getAvatar()).transform(new CropSquareTransformation())
                .into(imgAvatarFacebook);
        txtFullNameFacebook.setText(entityUserProfile.getLast_name() + " " +entityUserProfile.getFirst_name());
        txxEmailFacebook.setText(entityUserProfile.getEmail());
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });
    }

    private void initRealm() {
        //Khoi tao realm
        Realm.init(getActivity());
        //Khoi tao doi tuong Realm
        realm = Realm.getDefaultInstance();
    }

    private void addView(View view) {
        edtUserName = (DeleteEditText) view.findViewById(R.id.edtUserName);
        edtPassword = (PasswordEditText) view.findViewById(R.id.edtPassword);
        btnSignUp = (Button) view.findViewById(R.id.btnSignUp);
        imgAvatarFacebook = (ImageView) view.findViewById(R.id.imgAvatarFacebook);
        txtFullNameFacebook = (TextView) view.findViewById(R.id.txtFullNameFacebook);
        txxEmailFacebook = (TextView) view.findViewById(R.id.txxEmailFacebook);
    }

    @Override
    public void onValidationSucceeded() {
        insertAPI();
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
}
