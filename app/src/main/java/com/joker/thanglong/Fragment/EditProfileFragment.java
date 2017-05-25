package com.joker.thanglong.Fragment;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.joker.thanglong.CustomView.DeleteEditText;
import com.joker.thanglong.Model.PostModel;
import com.joker.thanglong.Model.UserModel;
import com.joker.thanglong.R;
import com.joker.thanglong.Ultil.ProfileInstance;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment implements Validator.ValidationListener{
    Validator validator;
    private FrameLayout viewContent;
    private Toolbar toolbar;
    @Length(min = 6,max = 25,message = "Họ và tên phải từ 12 đến 25 kí tự")
    private DeleteEditText edtFullName;
    private RadioGroup rgSex;
    private RadioButton rbMale;
    private RadioButton rdFemale;
    @Length(max = 255,message = "Nhập tối đa 255 ký tự")
    private EditText edtDescription;
    private Button btnSignUp;
    private UserModel userModel;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        addView(view);
        validator = new Validator(this);
        validator.setValidationListener(this);
        userModel = new UserModel(getActivity(), ProfileInstance.getProfileInstance(getActivity()).getProfile().getuID());
        getData(view);
        addEvent();
        return view;
    }

    private void addEvent() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });
    }

    private void getData(final View view) {
        userModel.getProfile(new PostModel.VolleyCallBackJson() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {
                edtFullName.setText(jsonObject.getString("full_name"));
                if (jsonObject.has("gender")){

                }
                if (jsonObject.has("about")){
                    edtDescription.setText(jsonObject.getString("about"));
                }
            }
        });
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                int a = rgSex.indexOfChild(view.findViewById(i));
                Toast.makeText(getActivity(), a+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addView(View view) {
        viewContent = (FrameLayout) view.findViewById(R.id.viewContent);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        edtFullName = (DeleteEditText) view.findViewById(R.id.edtFullName);
        rgSex = (RadioGroup) view.findViewById(R.id.rgSex);
        rbMale = (RadioButton) view.findViewById(R.id.rbMale);
        rdFemale = (RadioButton) view.findViewById(R.id.rdFemale);
        edtDescription = (EditText) view.findViewById(R.id.edtDescription);
        btnSignUp = (Button) view.findViewById(R.id.btnSignUp);
        toolbar.setTitle("Chỉnh sửa thông tin");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStackImmediate();
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        String full_name = edtFullName.getText().toString();
        String about = edtDescription.getText().toString();
        userModel.updateUser(full_name, about, new PostModel.VolleyCallBackCheck() {
            @Override
            public void onSuccess(boolean status) {
                Toast.makeText(getActivity(), "Cập nhật thông tin thành công", Toast.LENGTH_LONG).show();
                getFragmentManager().popBackStackImmediate();
            }
        });
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
