package com.joker.thanglong;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.joker.thanglong.Ultil.DeviceUltil;
import com.joker.thanglong.Ultil.FilePath;
import com.joker.thanglong.Ultil.ImagePicker;
import com.joker.thanglong.Ultil.SystemHelper;
import com.joker.thanglong.Ultil.VolleyHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;

public class PostActivity extends AppCompatActivity {
    private LinearLayout activityPost;
    private Toolbar idToolbar;
    private Button btnPrivacy;
    private Button btnSelectImage;
    private ImageView imgPost;
    private Intent mIntent;
    private StorageReference mStorageReference;
    private EditText edtStatusInput;
    private Spinner spnPrivacy;
    private TextView txtFullNamePost;
    private int privacy;
    String selectedImagePath ="";
    Bitmap bmp;
    Uri downloadUri;
    File file;
    int fileSize;
    ProgressDialog progressDialog;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        activity =this;
        Intent intent = getIntent();
        selectedImagePath = intent.getStringExtra("path");
        addControl();
        if (selectedImagePath != null){
            Log.d("selectedImagePath",selectedImagePath);
            file = new File(selectedImagePath);
            fileSize = Integer.parseInt(String.valueOf(file.length()))/1024;
            bmp = ImagePicker.getImageFromCamera(this,Uri.fromFile(new File(selectedImagePath)));
            Picasso.with(this)
                    .load(new File(selectedImagePath))
                    .fit().centerCrop()
                    .into(imgPost);
        }
        setupTabs();
        setupSpinner();
        addEvent();

//        LoadImage();


    }

    private void setupSpinner() {
        String arrPrivacy[] = {"Công khai","Chỉ cho người theo dõi bạn","Riêng tư"};
        final HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
        for (int i = 0; i < 3; i++)
        {
            spinnerMap.put((i+1),arrPrivacy[i]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,arrPrivacy);
        spnPrivacy.setAdapter(adapter);
        spnPrivacy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                privacy = i+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

//    private void LoadImage(){
//        StorageReference fileRef = mStorageReference.child("images").child("1489649998_hoainam");
//        fileRef.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
//            @Override
//            public void onSuccess(StorageMetadata storageMetadata) {
////                Glide.with(getApplicationContext()).load(storageMetadata.getDownloadUrl()).into(imgPost);
//                Picasso.with(getApplicationContext())
//                        .load(storageMetadata.getDownloadUrl())
//                        .placeholder( R.drawable.progress_loading )
//                        .into(imgPost);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
//    }

    private void addEvent() {
        txtFullNamePost.setText(MainActivity.entityUserProfile.getFull_name());
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeviceUltil deviceUltil = new DeviceUltil(activity);
                deviceUltil.CheckPermissionStorage();
                mIntent = new Intent();
                mIntent.setType("image/*");
                mIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(mIntent, getString(R.string.select_picture)),
                        1);
            }
        });
    }

    private void setupTabs() {
        idToolbar.setTitle("Đăng bài viết");
        setSupportActionBar(idToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        idToolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        idToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void addControl() {
        activityPost = (LinearLayout) findViewById(R.id.activity_post);
        idToolbar = (Toolbar) findViewById(R.id.idToolbar);
        btnSelectImage = (Button) findViewById(R.id.btnSelectImage);
        imgPost = (ImageView) findViewById(R.id.imgPost);
        edtStatusInput = (EditText) findViewById(R.id.edtStatusInput);
        spnPrivacy = (Spinner) findViewById(R.id.spnPrivacy);
        txtFullNamePost = (TextView) findViewById(R.id.txtFullNamePost);
        mStorageReference = FirebaseStorage.getInstance().getReference();
    }

    private void UploadFile(String fileName,Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] data = stream.toByteArray();
        StorageReference filepath = mStorageReference.child(fileName);
        UploadTask uploadTask = filepath.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                downloadUri = taskSnapshot.getDownloadUrl();
                PostStatus("posts",downloadUri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PostActivity.this, "Upload fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.postbai,menu);
//        menu.findItem(R.id.itPost).setTitle(Html.fromHtml("<font color='#ff3824'>Đăng</font>"));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itPost){
            if (TextUtils.isEmpty(edtStatusInput.getText()))
            {
                edtStatusInput.setError("Xin mời bạn nhập nội dung");
            }else {
                if (selectedImagePath == null){
                    progressDialog = ProgressDialog.show(this,"","Đang cập nhật trạng thái của bạn",true);
                    PostStatus("posts",null);
                }else {
                    progressDialog = ProgressDialog.show(this,"","Đang cập nhật trạng thái của bạn",true);
                    String fileName = "images/photos/" + SystemHelper.getTimeStamp() + "_" + MainActivity.entityUserProfile.getUserName();
                    UploadFile(fileName,bmp);
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
    private void PostStatus(String type,Uri downloadUri) {
        VolleyHelper volleyHelper = new VolleyHelper(this,getResources().getString(R.string.url));
        HashMap<String,String> parram = new HashMap<>();
        parram.put("message",edtStatusInput.getText().toString());
        if (downloadUri !=null){
            parram.put("photo",downloadUri.toString());
        }
        HashMap parramNumber = new HashMap();
        parramNumber.put("status",1);
        parramNumber.put("privacy", privacy);
        parram.putAll(parramNumber);
        volleyHelper.postHeader("users/" + MainActivity.entityUserProfile.getuID() + "/"+type, new JSONObject(parram), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Toast.makeText(PostActivity.this, "Cập nhật thành công", Toast.LENGTH_LONG).show();
                finish();
                Log.d("postStatus",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("postStatus",VolleyHelper.checkErrorCode(error)+"");
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                selectedImagePath = FilePath.getPath(getApplicationContext(), data.getData());
                Picasso.with(getApplicationContext())
                        .load(data.getData())
                        .fit().centerCrop()
                        .into(imgPost);
                file = new File(selectedImagePath);
                fileSize = Integer.parseInt(String.valueOf(file.length()))/1024;
                bmp = ImagePicker.getImageFromResult(this, resultCode, data);
                Log.d("data", fileSize+"'");
//                if (imgPost.getVisibility() != View.VISIBLE) {
//                    imgPost.setVisibility(View.VISIBLE);
//                }

            }

        }
    }
}
