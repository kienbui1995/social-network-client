package com.joker.thanglong;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.joker.thanglong.Ultil.DeviceUltil;
import com.joker.thanglong.Ultil.FilePath;
import com.joker.thanglong.Ultil.ImagePicker;
import com.joker.thanglong.Ultil.PostUlti;
import com.joker.thanglong.Ultil.ProfileInstance;
import com.joker.thanglong.Ultil.SystemHelper;
import com.joker.thanglong.Model.UserModel;
import com.joker.thanglong.Ultil.VolleyHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;

import Entity.EntityUserProfile;
import adapter.UserWallAdapter;

import static com.joker.thanglong.R.menu.profile;

public class UserProfileActivity extends AppCompatActivity {
    private CoordinatorLayout content;
    private ListView lvProfile;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private LinearLayout vUserProfileRoot;
    private ImageView ivUserProfilePhoto;
    private LinearLayout vUserDetails;
    private ImageView idVerifi;
    private Button btnFollow;
    private LinearLayout vUserStats;
    private Toolbar toolbarProfile;
    private TabLayout tlUserProfileTabs;
    private Intent mIntent;
    private String selectedImagePath = "";
    private TextView txtFullNameProfile;
    private TextView txtUserNameProfile;
    private VolleyHelper volleyHelper;
    private EntityUserProfile entityUserProfile;
    private Button btnNhanTin;
    private ViewPager viewPager;
    private  UserWallAdapter userWallAdapter;
    private TextView txtFollower;
    private TextView txtFollowing;
    private TextView txtNumberOfPost;
    public static String id = null;
    private UserModel userModel;
    private Bitmap bmp;
    Uri downloadUri;
    File file;
    int fileSize;
    StorageReference mStorageReference;
    public UserProfileActivity() {

    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        addControl();
        getDataProfile();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setupTabs();
            }
        },1000);
        addEvent();
    }

    private void addEvent() {
        changeAvtar();

    }

    private void displayInfo() {

    }

    private void getDataProfile() {
        Intent intent = getIntent();
        id = intent.getStringExtra("uId");
//        Log.d("id",id.toString());
        if (id == null) {
            id = ProfileInstance.getProfileInstance(this).getProfile().getuID();
            getProfile(id);
            btnNhanTin.setVisibility(View.GONE);
            btnFollow.setVisibility(View.GONE);
        } else {
            getProfile(id);
        }
    }

    private void getProfile(final String id) {
        userModel = new UserModel(this, id);
        userModel.getProfile(new PostUlti.VolleyCallBackJson() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {
                JSONObject object = jsonObject;
                txtFullNameProfile.setText(object.getString("full_name"));
                txtUserNameProfile.setText("@" + object.getString("username"));
                txtFollowing.setText(object.getString("followings"));
                txtFollower.setText(object.getString("followers"));
                toolbarProfile.setTitle(object.getString("full_name"));
                txtNumberOfPost.setText(object.getString("posts"));
                btnNhanTin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                        intent.putExtra("uId",id);
                        startActivity(intent);
                    }
                });

            }
        });

    }

    private void changeAvtar() {
        ivUserProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivUserProfilePhoto.performLongClick();
            }
        });
        registerForContextMenu(ivUserProfilePhoto);
    }

    private void setupTabs() {
        setSupportActionBar(toolbarProfile);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbarProfile.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbarProfile.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tlUserProfileTabs.setupWithViewPager(viewPager);
        userWallAdapter = new UserWallAdapter(getSupportFragmentManager());
        viewPager.setAdapter(userWallAdapter);
        tlUserProfileTabs.getTabAt(0).setIcon(R.drawable.ic_home_white_36dp);
        tlUserProfileTabs.getTabAt(1).setIcon(R.drawable.ic_picture_white_32dp);
//        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_info_user));
//        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_list_white_24dp));
//        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_grid_on_white_24dp));
//        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_label_white));

    }

    private void addControl() {
        txtFollower = (TextView) findViewById(R.id.txtFollower);
        txtFollowing = (TextView) findViewById(R.id.txtFollowing);
        content = (CoordinatorLayout) findViewById(R.id.content);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        vUserProfileRoot = (LinearLayout) findViewById(R.id.vUserProfileRoot);
        ivUserProfilePhoto = (ImageView) findViewById(R.id.ivUserProfilePhoto);
        vUserDetails = (LinearLayout) findViewById(R.id.vUserDetails);
        idVerifi = (ImageView) findViewById(R.id.idVerifi);
        btnFollow = (Button) findViewById(R.id.btnFollow);
        vUserStats = (LinearLayout) findViewById(R.id.vUserStats);
        toolbarProfile = (Toolbar) findViewById(R.id.toolbarProfile);
        tlUserProfileTabs = (TabLayout) findViewById(R.id.tlUserProfileTabs);
        txtFullNameProfile = (TextView) findViewById(R.id.txtFullNameProfile);
        btnNhanTin = (Button) findViewById(R.id.btnNhanTin);
        txtUserNameProfile = (TextView) findViewById(R.id.txtUserNameProfile);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        txtNumberOfPost = (TextView) findViewById(R.id.txtNumberOfPost);
        mStorageReference = FirebaseStorage.getInstance().getReference();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                selectedImagePath = FilePath.getPath(getApplicationContext(), data.getData());
                Picasso.with(getApplicationContext())
                        .load(data.getData())
                        .fit().centerCrop()
                        .into(ivUserProfilePhoto);
                file = new File(selectedImagePath);
                fileSize = Integer.parseInt(String.valueOf(file.length()))/1024;
                bmp = ImagePicker.getImageFromResult(this, resultCode, data);
                String fileName = "images/photos/" + SystemHelper.getTimeStamp() + "_" + MainActivity.entityUserProfile.getUserName();
                UploadFile(fileName,bmp);
                Log.d("data", fileSize+"'");
//                if (imgPost.getVisibility() != View.VISIBLE) {
//                    imgPost.setVisibility(View.VISIBLE);
//                }

            }

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.avatar,menu);
        menu.setHeaderTitle("Thay đổi ảnh đại diện");
        menu.setHeaderIcon(R.drawable.ic_mode_edit_black_24dp);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itChangeAvatar){
            DeviceUltil deviceUltil = new DeviceUltil(this);
            deviceUltil.CheckPermissionStorage();
            mIntent = new Intent();
            mIntent.setType("image/*");
            mIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(
                    Intent.createChooser(mIntent, getString(R.string.select_picture)),
                    1);
        }else {
            Toast.makeText(this, "Tinh nang dang duoc hoan thanh", Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(profile,menu);
        return super.onCreateOptionsMenu(menu);

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
//                PostStatus("posts",downloadUri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Upload fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
