package com.joker.thanglong;

import com.google.firebase.storage.UploadTask;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.bumptech.glide.Glide;
import com.joker.thanglong.Model.GroupModel;
import com.joker.thanglong.Ultil.DeviceUltil;
import com.joker.thanglong.Ultil.FilePath;
import com.joker.thanglong.Ultil.FirebaseHelper;
import com.joker.thanglong.Ultil.ImagePicker;

import java.io.File;

import Entity.EntityGroup;
import adapter.GroupViewPagerAdapter;

public class GroupActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabHost;
    private ViewPager viewPager;
    private FrameLayout frContent;
    private ImageView imgAnhBia;
    private String selectedImagePath;
    Intent mIntent;
    ImageButton imgThayAnhBia;
    GroupViewPagerAdapter groupViewPagerAdapter;
    int idGr;
    public static EntityGroup groupInfo;
    GroupModel groupModel;
    private File file;
    private Bitmap bmp;
    private int choose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        addControll();
        initData();

    }

    private void initData() {
        Intent intent = getIntent();
        idGr = intent.getIntExtra("idGroup",1);
        groupModel = new GroupModel(this);
        getDataGroup();
    }

    private void getDataGroup() {
        groupModel.getInfoGroup(idGr, new GroupModel.VolleyCallbackInfoGroup() {
            @Override
            public void onSuccess(EntityGroup entityGroup) {
                groupInfo = entityGroup;
                addEvent();
            }
        });
    }

    private void addEvent() {
        toolbar.setTitle(groupInfo.getName());
        Glide.with(this).load(groupInfo.getCover()).fitCenter().crossFade().into(imgAnhBia);
        setSupportActionBar(toolbar);
//        toolbar.setLogo(R.drawable.avatar1);
        if (getSupportActionBar() != null){
//            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tabHost.setupWithViewPager(viewPager);
        groupViewPagerAdapter = new GroupViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(groupViewPagerAdapter);

        imgThayAnhBia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(GroupActivity.this)
                        .items(R.array.optionChangeImageGroup)
                        .theme(Theme.LIGHT)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                switch (which){
                                    case 0:
                                        choose= 0;
                                        ChangeAvatar();
                                        break;
                                    case 1:
                                        choose =1;
                                        ChangeAvatar();
                                        break;
                                }
                            }
                        })
                        .show();

            }
        });
    }

    private void ChangeCover() {

    }

    private void ChangeAvatar() {
        DeviceUltil deviceUltil = new DeviceUltil(this);
        deviceUltil.CheckPermissionStorage();
        mIntent = new Intent();
        mIntent.setType("image/*");
        mIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(mIntent, getString(R.string.select_picture)),
                1);
    }

    private void addControll() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabHost = (TabLayout) findViewById(R.id.tabHost);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        frContent = (FrameLayout) findViewById(R.id.frContent);
        imgThayAnhBia = (ImageButton) findViewById(R.id.imgThayAnhBia);
        imgAnhBia = (ImageView) findViewById(R.id.imgAnhBia);

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                selectedImagePath = FilePath.getPath(getApplicationContext(), data.getData());
//                Picasso.with(getApplicationContext())
//                        .load(data.getData()).fit()
//                        .transform(new BlurTransformation(getApplicationContext(),2))
//                        .into(imgAnhBia);
//                if (imgAnhBia.getVisibility() != View.VISIBLE) {
//                    imgAnhBia.setVisibility(View.VISIBLE);
//                }
                file = new File(selectedImagePath);
//                fileSize = Integer.parseInt(String.valueOf(file.length()))/1024;
                bmp = ImagePicker.getImageFromResult(this, resultCode, data);
                uploadImage();
            }
        }
    }

    private void uploadImage() {
        new MaterialDialog.Builder(this)
                .content("Bạn có muốn thay đổi ảnh?")
                .positiveText(R.string.agree)
                .theme(Theme.LIGHT)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        FirebaseHelper firebaseHelper = new FirebaseHelper(getApplicationContext());
                        if (choose == 0){
                            firebaseHelper.changeImageAvatar(groupInfo.getName(), bmp, "avatar", new FirebaseHelper.FirebaseCallback() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    EntityGroup entityGroup = new EntityGroup();
                                    entityGroup.setAvatar(taskSnapshot.getDownloadUrl().toString());
                                    groupModel.editInfoGroup(groupInfo.getId(),entityGroup);
                                    finish();
                                    startActivity(getIntent());
                                    Toast.makeText(GroupActivity.this, "Thay đổi ảnh đại diện thành công", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            firebaseHelper.changeImageAvatar(groupInfo.getName(), bmp, "cover", new FirebaseHelper.FirebaseCallback() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    EntityGroup entityGroup = new EntityGroup();
                                    entityGroup.setCover(taskSnapshot.getDownloadUrl().toString());
                                    groupModel.editInfoGroup(groupInfo.getId(),entityGroup);
                                    Toast.makeText(GroupActivity.this, "Thay đổi ảnh bìa thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(getIntent());
                                }
                            });
                        }
                    }
                })
                .negativeText(R.string.disagree)
                .show();
    }
}
