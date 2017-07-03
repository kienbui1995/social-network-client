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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.bumptech.glide.Glide;
import com.joker.thanglong.Model.ChannelModel;
import com.joker.thanglong.Model.PostModel;
import com.joker.thanglong.Ultil.DeviceUltil;
import com.joker.thanglong.Ultil.FilePath;
import com.joker.thanglong.Ultil.FirebaseHelper;
import com.joker.thanglong.Ultil.ImagePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import Entity.EntityChannel;
import adapter.PagesViewPagerAdapter;

public class ChannelActivity extends AppCompatActivity {
    private ImageView imgAnhBia;
    private ImageButton imgThayAnhBia;
    private Toolbar toolbar;
    private ImageView avatar;
    private TextView txtNameChannel;
    private TextView txtShortNamePage;
    private Button btnFollowChannel;
    private Button btnMessagePage;
    private TabLayout tabHost;
    private FrameLayout frContent;
    private ViewPager viewPager;
    private PagesViewPagerAdapter pagesViewPagerAdapter;
    private Intent intent;
    private int id_channel;
    private ChannelModel channelModel;
    public static EntityChannel channelInfo;
    private int choose;
    Intent mIntent;
    private String selectedImagePath;
    private File file;
    private Bitmap bmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pages);
        addControll();

        addData();
    }

    private void addData() {
        intent = getIntent();
        id_channel = intent.getIntExtra("idChannel",1);
        Log.d("idChannel",id_channel+"");
        channelModel = new ChannelModel(getApplicationContext());
        channelModel.getSingleChannel(id_channel, new ChannelModel.VolleyCallbackChannel() {
            @Override
            public void onSuccess(final EntityChannel entityChannel) {
                channelInfo = entityChannel;

                addEvent(entityChannel.getName());
                txtNameChannel.setText(entityChannel.getName());
                txtShortNamePage.setText(entityChannel.getShort_name());
                Glide.with(getApplicationContext()).load(entityChannel.getAvatar()).centerCrop().into(avatar);
                Glide.with(getApplicationContext()).load(entityChannel.getCover()).centerCrop().into(imgAnhBia);
                if(!entityChannel.is_followed()){
                    btnFollowChannel.setText("Theo dõi");
                    btnFollowChannel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            channelModel.followChannel(entityChannel.getId(), new PostModel.VolleyCallBackJson() {
                                @Override
                                public void onSuccess(JSONObject jsonObject) throws JSONException {
                                    btnFollowChannel.setText("Hủy theo dõi");
                                    btnFollowChannel.setEnabled(false);
                                }
                            });
                        }
                    });
                }else if (entityChannel.is_admin() && entityChannel.is_followed()){
                    btnFollowChannel.setVisibility(View.GONE);
                    btnMessagePage.setVisibility(View.GONE);
                }else if (entityChannel.is_followed()){
                    btnFollowChannel.setText("Hủy theo dõi");
                    btnFollowChannel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            btnFollowChannel.setText("Theo dõi");
                            btnFollowChannel.setEnabled(false);
                            channelModel.unfollowChannel(entityChannel.getId(), new PostModel.VolleyCallBackJson() {
                                @Override
                                public void onSuccess(JSONObject jsonObject) throws JSONException {
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    private void addControll() {
        imgAnhBia = (ImageView) findViewById(R.id.imgAnhBia);
        imgThayAnhBia = (ImageButton) findViewById(R.id.imgThayAnhBia);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        avatar = (ImageView) findViewById(R.id.avatar);
        txtNameChannel = (TextView) findViewById(R.id.txtNameChannel);
        txtShortNamePage = (TextView) findViewById(R.id.txtShortNamePage);
        btnFollowChannel = (Button) findViewById(R.id.btnFollowChannel);
        btnMessagePage = (Button) findViewById(R.id.btnMessagePage);
        tabHost = (TabLayout) findViewById(R.id.tabHost);
        frContent = (FrameLayout) findViewById(R.id.frContent);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }
    private void addEvent(String name) {
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
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
        pagesViewPagerAdapter = new PagesViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagesViewPagerAdapter);
        if (channelInfo.is_admin()){
            Log.d("isAdmin","truee");
            imgThayAnhBia.setVisibility(View.VISIBLE);
            imgThayAnhBia.setEnabled(true);
            imgThayAnhBia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(ChannelActivity.this, "1", Toast.LENGTH_SHORT).show();
                    new MaterialDialog.Builder(ChannelActivity.this)
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                selectedImagePath = FilePath.getPath(getApplicationContext(), data.getData());
                file = new File(selectedImagePath);
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
                            firebaseHelper.changeImageAvatar(channelInfo.getName(), bmp, "avatar", new FirebaseHelper.FirebaseCallback() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    EntityChannel entityChannel = new EntityChannel();
                                    entityChannel.setAvatar(taskSnapshot.getDownloadUrl().toString());
                                    channelModel.editInfoChannel(channelInfo.getId(),entityChannel);
                                    finish();
                                    startActivity(getIntent());
                                    Toast.makeText(ChannelActivity.this, "Thay đổi ảnh đại diện thành công", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            firebaseHelper.changeImageAvatar(channelInfo.getName(), bmp, "cover", new FirebaseHelper.FirebaseCallback() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    EntityChannel entityChannel = new EntityChannel();
                                    entityChannel.setCover(taskSnapshot.getDownloadUrl().toString());
                                    channelModel.editInfoChannel(channelInfo.getId(),entityChannel);
                                    Toast.makeText(ChannelActivity.this, "Thay đổi ảnh bìa thành công", Toast.LENGTH_SHORT).show();
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
