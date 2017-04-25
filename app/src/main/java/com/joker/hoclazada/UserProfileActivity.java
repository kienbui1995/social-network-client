package com.joker.hoclazada;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.joker.hoclazada.Ultil.FilePath;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

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
    private String selectedImagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        addControl();
        setupTabs();
        addEvent();
    }

    private void addEvent() {
        ivUserProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivUserProfilePhoto.performLongClick();
            }
        });
        registerForContextMenu(ivUserProfilePhoto);
    }

    private void setupTabs() {
        toolbarProfile.setTitle("Hoài Nam");
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
        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_info_user));
        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_list_white_24dp));
        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_grid_on_white_24dp));
//        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_label_white));
    }

    private void addControl() {
        content = (CoordinatorLayout) findViewById(R.id.content);
        lvProfile = (ListView) findViewById(R.id.lvProfile);
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
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                selectedImagePath = FilePath.getPath(getApplicationContext(), data.getData());
                Picasso.with(getApplicationContext())
                        .load(data.getData())
                        .transform(new CropCircleTransformation())
                        .into(ivUserProfilePhoto);
                if (ivUserProfilePhoto.getVisibility() != View.VISIBLE) {
                    ivUserProfilePhoto.setVisibility(View.VISIBLE);
                }
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
        getMenuInflater().inflate(R.menu.profile,menu);

        return super.onCreateOptionsMenu(menu);

    }
}
