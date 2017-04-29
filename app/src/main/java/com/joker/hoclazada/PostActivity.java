package com.joker.hoclazada;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.joker.hoclazada.Ultil.FilePath;
import com.joker.hoclazada.Ultil.FirebaseHelper;
import com.joker.hoclazada.Ultil.VolleyHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

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

    String selectedImagePath;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        addControl();
        setupTabs();
        addEvent();
//        LoadImage();


    }

    private void LoadImage(){
        StorageReference fileRef = mStorageReference.child("images").child("1489649998_hoainam");
        fileRef.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {
//                Glide.with(getApplicationContext()).load(storageMetadata.getDownloadUrl()).into(imgPost);
                Picasso.with(getApplicationContext())
                        .load(storageMetadata.getDownloadUrl())
                        .placeholder( R.drawable.progress_loading )
                        .into(imgPost);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void addEvent() {
        btnPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPrivacy.performLongClick();
            }
        });
        registerForContextMenu(btnPrivacy);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
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
        btnPrivacy = (Button) findViewById(R.id.btnPrivacy);
        btnSelectImage = (Button) findViewById(R.id.btnSelectImage);
        imgPost = (ImageView) findViewById(R.id.imgPost);
        edtStatusInput = (EditText) findViewById(R.id.edtStatusInput);
        mStorageReference = FirebaseStorage.getInstance().getReference();
    }
    private void UploadFile(String selectedImagePath){
        Log.d("PathImage",selectedImagePath+"");
        Uri file = Uri.fromFile(new File(selectedImagePath));
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        String fileName = "images/" +ts+"_hoainam";
        StorageReference riversRef = mStorageReference.child(fileName);

//        riversRef.putFile(file)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        // Get a URL to the uploaded content
//                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                        Toast.makeText(PostActivity.this, "Upload thanh cong", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        // Handle unsuccessful uploads
//                        Toast.makeText(PostActivity.this, "Upload fail", Toast.LENGTH_SHORT).show();
//                        // ...
//                    }
//                });

        FirebaseHelper firebaseHelper = new FirebaseHelper(this,mStorageReference);
        firebaseHelper.putFile(riversRef, file, new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {

                UploadTask.TaskSnapshot taskSnapshot = (UploadTask.TaskSnapshot) o;
                Uri url = taskSnapshot.getDownloadUrl();
                Log.d("firebaseA",url.toString());
                Toast.makeText(PostActivity.this, "Upload thanh cong", Toast.LENGTH_SHORT).show();
            }
        }, new OnFailureListener() {
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
                PostStatus();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void PostStatus() {
        final ProgressDialog progressDialog;
        progressDialog = ProgressDialog.show(this,"","Đang cập nhật trạng thái của bạn",true);
        VolleyHelper volleyHelper = new VolleyHelper(this,getResources().getString(R.string.url));
        HashMap<String,String> parram = new HashMap<>();
        parram.put("message",edtStatusInput.getText().toString());
        volleyHelper.postHeader("users/" + MainActivity.entityUserProfile.getuID() + "/statuses", new JSONObject(parram), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Toast.makeText(PostActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                Log.d("postStatus",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("postStatus",VolleyHelper.checkErrorCode(error)+"");
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.privacy,menu);
        menu.setHeaderTitle("Thay đổi quyền riêng tư");
        menu.setHeaderIcon(R.drawable.ic_mode_edit_black_24dp);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itfollowing){
            btnPrivacy.setText("Following");
        }else if (item.getItemId() == R.id.itOnlyMe){
            btnPrivacy.setText("Only Me");
        }
        return super.onContextItemSelected(item);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                selectedImagePath = FilePath.getPath(getApplicationContext(), data.getData());
                Picasso.with(getApplicationContext())
                        .load(data.getData())
                        .into(imgPost);
                UploadFile(selectedImagePath);
                if (imgPost.getVisibility() != View.VISIBLE) {
                    imgPost.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
