package com.joker.thanglong;

import com.google.firebase.storage.UploadTask;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.joker.thanglong.CustomView.DeleteEditText;
import com.joker.thanglong.Model.ChannelModel;
import com.joker.thanglong.Model.PostModel;
import com.joker.thanglong.Ultil.DeviceUltil;
import com.joker.thanglong.Ultil.FilePath;
import com.joker.thanglong.Ultil.FirebaseHelper;
import com.joker.thanglong.Ultil.ImagePicker;
import com.joker.thanglong.Ultil.SystemHelper;

import java.io.File;

import Entity.EntityNotificationChannel;

public class CreateNotificationChannelActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DeleteEditText txtTitle;
    private EditText txtMessage;
    private EditText txtTime;
    private EditText txtPlace;
    private FloatingActionButton fabSubmit;
    private ChannelModel channelModel;
    private EntityNotificationChannel entityNotificationChannel;
    private Button btnAttachPhoto;
    String selectedImagePath ="";
    ProgressDialog progressDialog;
    Bitmap bmp;
    File file;
    int choose = 0;
    FirebaseHelper firebaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notification_channel);
        addView();
        addToolbar();
        fabSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtTitle.getText().toString().isEmpty() && txtMessage.getText().toString().isEmpty()){
                    txtTitle.setError("Không được để trống");
                    txtMessage.setError("Không được để trống");
                }else {
//                    progressDialog = new ProgressDialog(getApplicationContext());
//                    progressDialog.setMessage("Đang cập nhật");
//                    progressDialog.show();
                    writeNoti();
                }
            }
        });
        btnAttachPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeviceUltil deviceUltil = new DeviceUltil(CreateNotificationChannelActivity.this);
                deviceUltil.CheckPermissionStorage();
                Intent mIntent = new Intent();
                mIntent.setType("image/*");
                mIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(mIntent, getString(R.string.select_picture)),
                        1);

            }
        });

    }

    private void writeNoti() {
        channelModel = new ChannelModel(this);
        entityNotificationChannel = new EntityNotificationChannel();
        entityNotificationChannel.setChannel(ChannelActivity.channelInfo);
        entityNotificationChannel.setMessage(txtMessage.getText().toString().trim());
        entityNotificationChannel.setTime(txtTime.getText().toString().trim());
        entityNotificationChannel.setTittle(txtTitle.getText().toString().trim());
        entityNotificationChannel.setPlace(txtPlace.getText().toString().trim());
        if (choose == 0){
            channelModel.writeNotification(entityNotificationChannel, new PostModel.VolleyCallBackCheck() {
                @Override
                public void onSuccess(boolean status) {
//                    progressDialog.dismiss();
                    Toast.makeText(CreateNotificationChannelActivity.this, "Cập nhật thông báo thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }else if (choose==1){
            firebaseHelper  = new FirebaseHelper(getApplicationContext());
            firebaseHelper.UploadFile(SystemHelper.getTimeStamp() + "", bmp, new FirebaseHelper.FirebaseCallback() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    entityNotificationChannel.setPhoto(taskSnapshot.getDownloadUrl().toString());
                    channelModel.writeNotification(entityNotificationChannel, new PostModel.VolleyCallBackCheck() {
                        @Override
                        public void onSuccess(boolean status) {
//                            progressDialog.dismiss();
                            Toast.makeText(CreateNotificationChannelActivity.this, "Cập nhật thông báo thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            });
        }
    }

    private void addToolbar() {
        toolbar.setTitle("Viết thông báo");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void addView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTitle = (DeleteEditText) findViewById(R.id.txtTitle);
        txtMessage = (EditText) findViewById(R.id.txtMessage);
        txtTime = (EditText) findViewById(R.id.txtTime);
        txtPlace = (EditText) findViewById(R.id.txtPlace);
        fabSubmit = (FloatingActionButton) findViewById(R.id.fabSubmit);
        btnAttachPhoto = (Button) findViewById(R.id.btnAttachPhoto);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                selectedImagePath = FilePath.getPath(getApplicationContext(), data.getData());
                file = new File(selectedImagePath);
                bmp = ImagePicker.getImageFromResult(this, resultCode, data);
                choose=1;
            }
        }
    }


}
