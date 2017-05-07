package com.joker.hoclazada;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.joker.hoclazada.Ultil.FilePath;
import com.joker.hoclazada.Ultil.VolleyHelper;
import com.squareup.picasso.Picasso;

import io.realm.Realm;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import org.json.JSONException;
import org.json.JSONObject;

import Entity.EntityUserProfile;

import static com.joker.hoclazada.R.menu.profile;

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
    private TextView txtFullNameProfile;
    private TextView txtUserNameProfile;
    private TextView txtPost;
    private TextView txtFollower;
    private TextView txtFollowing;
    private VolleyHelper volleyHelper;
    private EntityUserProfile entityUserProfile;
    private Button btnNhanTin;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        entityUserProfile = new EntityUserProfile();
        addControl();
        getDataProfile();
        displayInfo();
        setupTabs();
        addEvent();
    }

    private void addEvent() {
        changeAvtar();

    }

    private void displayInfo() {
        btnNhanTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("uId",id);
                startActivity(intent);
            }
        });

    }

    private void getDataProfile() {
        {

            btnNhanTin.setVisibility(View.GONE);
            btnFollow.setVisibility(View.GONE);

            volleyHelper = new VolleyHelper(this, getResources().getString(R.string.url));
            volleyHelper.get("users/" + MainActivity.entityUserProfile.getuID(), null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject infoUser = response.getJSONObject("data");
                        txtFullNameProfile.setText(infoUser.getString("full_name"));
                        txtUserNameProfile.setText("@" + infoUser.getString("username"));
                        txtPost.setText(infoUser.getInt("posts")+"");
                        txtFollower.setText(infoUser.getInt("followers")+"");
                        txtFollowing.setText(infoUser.getInt("followings")+"");
                        toolbarProfile.setTitle(infoUser.getString("full_name"));
                        displayInfo();
                        Log.d("getInfoUser", entityUserProfile.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("getInfoUser", VolleyHelper.checkErrorCode(error) + "");
                }
            });
        }
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
        toolbarProfile.setTitle(MainActivity.entityUserProfile.getFull_name());
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
        txtFullNameProfile = (TextView) findViewById(R.id.txtFullNameProfile);
        btnNhanTin = (Button) findViewById(R.id.btnNhanTin);
        txtUserNameProfile = (TextView) findViewById(R.id.txtUserNameProfile);
        txtPost = (TextView) findViewById(R.id.txtPost);
        txtFollower = (TextView) findViewById(R.id.txtFollower);
        txtFollowing = (TextView) findViewById(R.id.txtFollowing);
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
        getMenuInflater().inflate(profile,menu);

        return super.onCreateOptionsMenu(menu);

    }
}
