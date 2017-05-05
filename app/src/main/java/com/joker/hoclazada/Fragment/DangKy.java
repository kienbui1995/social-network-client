package com.joker.hoclazada.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.joker.hoclazada.CustomView.DeleteEditText;
import com.joker.hoclazada.CustomView.PasswordEditText;
import com.joker.hoclazada.R;
import com.joker.hoclazada.Ultil.FilePath;
import com.joker.hoclazada.Ultil.FirebaseHelper;
import com.joker.hoclazada.Ultil.SystemHelper;
import com.joker.hoclazada.Ultil.VolleyHelper;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.CropSquareTransformation;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import Entity.EntityDangKy;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by joker on 2/10/17.
 */

public class DangKy extends Fragment implements Validator.ValidationListener {
    Validator validator;
    @Length(min = 6,max = 25,message = "Họ và tên phải từ 12 đến 25 kí tự")
    private DeleteEditText edtFullName;
    @Email(message = "Email không hợp lệ")
    private DeleteEditText edtEmail;
    private ImageView imgAvartarSignUp;
    private Button chooseAvatar;
    @Length(min = 6,max=15,message = "Tên đăng nhập phải từ 6 đến 15 kí tự",trim = true)
    private DeleteEditText edtUserName;
    @Password(scheme = Password.Scheme.ANY)
    private PasswordEditText edtPassword;
    @ConfirmPassword(message = "Mật khẩu không giống nhau ")
    private PasswordEditText edtRePassword;
    private Button btnSignUp;
    private String url;
    private  Uri file;
    Intent mIntent;
    private String selectedImagePath = "";
    private EntityDangKy entityDangKy;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dangky,container,false);
        addControl(view);
        validator = new Validator(this);
        validator.setValidationListener(this);
        displayAvatar();
        addEvent();
//        Picasso.with(getActivity()).load("http://data.whicdn.com/images/206414193/superthumb.png").into(picassoImageTarget(getApplicationContext(), "imageDir", "my_image.jpeg"));
        return view;
    }

    private void addEvent() {
        chooseAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent = new Intent();
                mIntent.setType("image/*");
                mIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(mIntent, getString(R.string.select_picture)),
                        1);

            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });
    }
    private void uploadAnh(StorageReference folder, Uri file, StorageReference storageReference){
        FirebaseHelper firebaseHelper = new FirebaseHelper(getActivity(),storageReference);
        firebaseHelper.putFile(folder, file, new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                UploadTask.TaskSnapshot taskSnapshot = (UploadTask.TaskSnapshot) o;
                url = taskSnapshot.getDownloadUrl().toString();
                Log.d("firebaseA",url.toString());
                Toast.makeText(getActivity(), "Upload thanh cong", Toast.LENGTH_SHORT).show();
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Upload fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void displayAvatar() {
        Picasso.with(getApplicationContext())
                .load(R.drawable.avatar1)
                .transform(new CropSquareTransformation())
                .into(imgAvartarSignUp);
    }
    private void registerAPI(EntityDangKy entityDangKy)
    {
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("username",entityDangKy.getUsername());
        params.put("full_name",entityDangKy.getFullname());
        params.put("email",entityDangKy.getEmail());
        params.put("password",entityDangKy.getPassword());
        params.put("avatar",entityDangKy.getAvatar());
//        params.put("full_name",);
        Log.d("dangKy",new JSONObject(params).toString());

        final ProgressDialog progressDialog;
        progressDialog = ProgressDialog.show(getActivity(),"","Đang xử lý",true);


        VolleyHelper volleyHelper = new VolleyHelper(getActivity(),getResources().getString(R.string.url));

        volleyHelper.post("sign_up", new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();
                SuccessRegisterFragment successRegisterFragment = new SuccessRegisterFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right);
                fragmentTransaction.replace(R.id.viewContent,successRegisterFragment);
                fragmentTransaction.commit();
                Log.d("successCode",response.toString());
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (VolleyHelper.checkErrorCode(error) == 371)
                {
                    edtEmail.setError("Email đã tồn tại trên hệ thống");
                }else if (VolleyHelper.checkErrorCode(error) == 376)
                {
                    edtUserName.setError("Tên đăng nhập đã tồn tại");
                }
            }
        });
    }
    private void addControl(View view) {
        imgAvartarSignUp = (ImageView) view.findViewById(R.id.imgAvartarSignUp);
        chooseAvatar = (Button) view.findViewById(R.id.chooseAvatar);
        edtUserName = (DeleteEditText) view.findViewById(R.id.edtUserName);
        edtFullName = (DeleteEditText) view.findViewById(R.id.edtFullName);
        edtEmail = (DeleteEditText) view.findViewById(R.id.edtEmail);
        edtPassword = (PasswordEditText) view.findViewById(R.id.edtPassword);
        edtRePassword = (PasswordEditText) view.findViewById(R.id.edtRePassword);
        btnSignUp = (Button) view.findViewById(R.id.btnSignUp);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                selectedImagePath = FilePath.getPath(getApplicationContext(), data.getData());
                Picasso.with(getApplicationContext())
                        .load(data.getData())
                        .transform(new CropCircleTransformation())
                        .into(imgAvartarSignUp);
                if (imgAvartarSignUp.getVisibility() != View.VISIBLE) {
                    imgAvartarSignUp.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onValidationSucceeded() {
        String fullname = edtFullName.getText().toString().trim();
        String userName = edtUserName.getText().toString().trim();
        String email = edtEmail.getText().toString().toString().trim();
        String password =  SystemHelper.MD5(edtPassword.getText().toString());

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        if (selectedImagePath == "")
        {
            entityDangKy = new EntityDangKy(fullname,userName,password,email,"");
            //Send Api
            registerAPI(entityDangKy);
        }else
        {
//            file = Uri.parse("http://data.whicdn.com/images/206414193/superthumb.png");
//            Picasso.with(getActivity()).load("http://data.whicdn.com/images/206414193/superthumb.png").into(picassoImageTarget(getApplicationContext(), "imageDir", "my_image.jpeg"));
            Uri file = Uri.fromFile(new File(selectedImagePath));
            long ts = SystemHelper.getTimeStamp();
            String fileName = "avatar/" +ts+ userName;
            StorageReference folder = storageReference.child(fileName);
            EntityDangKy entityDangKy = new EntityDangKy(fullname,userName,password,email,fileName);
            //Up anh
            uploadAnh(folder,file,storageReference);

            //Send Api
            registerAPI(entityDangKy);
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
    private Target picassoImageTarget(Context context, final String imageDir, final String imageName) {
        Log.d("picassoImageTarget", " picassoImageTarget");
        ContextWrapper cw = new ContextWrapper(context);
        final File directory = cw.getDir(imageDir, Context.MODE_PRIVATE); // path to /data/data/yourapp/app_imageDir
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final File myImageFile = new File(directory, imageName); // Create image file
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(myImageFile);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 50, fos);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i("imageLocal", "image saved to >>>" + myImageFile.getAbsolutePath());

                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {}
            }
        };
    }

}
