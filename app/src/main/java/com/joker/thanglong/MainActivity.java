package com.joker.thanglong;

import com.google.firebase.database.DatabaseReference;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.FacebookSdk;
import com.joker.thanglong.Fragment.Group.GroupFragment;
import com.joker.thanglong.Model.UserModel;
import com.joker.thanglong.Ultil.DeviceUltil;
import com.joker.thanglong.Ultil.ProfileInstance;
import com.joker.thanglong.Ultil.PutParamFacebook;
import com.joker.thanglong.Ultil.VolleyHelper;
import com.joker.thanglong.Ultil.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import Entity.EntityUserProfile;
import april.yun.JPagerSlidingTabStrip2;
import april.yun.other.JTabStyleDelegate;
import io.realm.Realm;

import static android.R.attr.name;
import static april.yun.other.JTabStyleBuilder.STYLE_ROUND;

public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    private Toolbar toolbar;
//    private TabLayout tabHost;
    private ViewPager viewPager;
    private JPagerSlidingTabStrip2 tabButtom;
    ViewPagerAdapter viewPagerAdapter;
    private FrameLayout content;
    private DrawerLayout drawerLayout;
    Button btnPostStatus;
    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
//    TextView notification;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private Menu menu;
    String tennguoidung;
    PutParamFacebook putParamFacebook;
    private LinearLayout frContent;
    private NavigationView nvMenu;
    FragmentManager manager;
    FragmentTransaction transaction;
    DatabaseReference databaseReference;
    public static EntityUserProfile entityUserProfile;
    private EntityUserProfile profile;
    public static String token = null;
    Realm realm;
    private ImageButton btnTakePicture;
    private static final int REQUEST_IMAGE = 100;
    File destination;
    Activity activity;
    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        boolean check = ProfileInstance.getProfileInstance(this).checkLogin();
        setContentView(R.layout.activity_main);
        //Khoi tao realm
        Realm.init(this);
        //Khoi tao doi tuong Realm
        realm = Realm.getDefaultInstance();
        getInfoUser();
        entityUserProfile = realm.where(EntityUserProfile.class).findFirst();
        token= entityUserProfile.getToken();
        Log.d("respone1",entityUserProfile.toString());
        activity=this;
//        tabHost = (TabLayout) findViewById(R.id.tabHost);
        userModel = new UserModel(this,ProfileInstance.getProfileInstance(this).getProfile().getuID());
        btnTakePicture = (ImageButton) findViewById(R.id.btnTakePicture);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabButtom = (JPagerSlidingTabStrip2) findViewById(R.id.tab_buttom);
        toolbar = (Toolbar) findViewById(R.id.toolbarA);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
//        content = (FrameLayout) findViewById(R.id.content);
//        epMenu = (ExpandableListView) findViewById(R.id.epMenu);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBar);
        btnPostStatus = (Button) findViewById(R.id.btnPost);
//        lvMenu = (ListView) findViewById(R.id.lvMenu);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapToolbar);
        frContent = (LinearLayout) findViewById(R.id.frContent);
        nvMenu = (NavigationView) findViewById(R.id.nvMenu);
        View header = nvMenu.getHeaderView(0);
        TextView full_name = (TextView) header.findViewById(R.id.txtFullNameNavibar);
        full_name.setText(entityUserProfile.getFull_name());
        //Toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        //Tabhost
//        tabHost.setupWithViewPager(viewPager);

        int[] mSelectors = new int[] { R.drawable.tab1, R.drawable.tab2, R.drawable.tab3, R.drawable.tab4 };
        setupStrip(tabButtom.getTabStyleDelegate(), STYLE_ROUND);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),mSelectors);
        viewPager.setAdapter(viewPagerAdapter);
        tabButtom.bindViewPager(viewPager);
        tabButtom.setPromptNum(1,23);
        tabButtom.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabButtom.setPromptNum(1,0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //DrawerLayout
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBarDrawerToggle.syncState();
        //Set su kien click cho Appbar layout
        appBarLayout.addOnOffsetChangedListener(this);
        btnPostStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),PostActivity.class));
            }
        });

        //
//        Bundle params = new Bundle();
//        params.putString("fields","name");
//        putParamFacebook = new PutParamFacebook(accessToken,params,"name");

        nvMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.itHome:
//                        manager = getSupportFragmentManager();
//                        List<Fragment> fragments = manager.getFragments();
//                        if (fragments != null) {
//                            for (Fragment fragment : fragments) {
//                                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
//                            }
//                        }else {
//                            Toast.makeText(MainActivity.this, "Khong co fragment", Toast.LENGTH_SHORT).show();
//                        }
                    case R.id.itProfile:
//                                                startActivity(new Intent(getApplicationContext(),UserProfileActivity.class));
                    case R.id.itFollow:
                        startActivity(new Intent(getApplicationContext(),FollowActivity.class));
                    case R.id.itLearing:
                        break;
                    case R.id.itGroup:
                        //Framgment
                        manager = getSupportFragmentManager();
                        transaction = manager.beginTransaction();
                        GroupFragment groupFragment = new GroupFragment();
                        transaction.add(R.id.frContentHome,groupFragment,"GROUP");
                        transaction.addToBackStack(null);
                        transaction.commit();
                        drawerLayout.closeDrawers();
//                        startActivity(new Intent(getApplicationContext(),GroupActivity.class));

                        break;
                    case R.id.itPage:
                        startActivity(new Intent(getApplicationContext(),PagesActivity.class));
                        break;
                    case R.id.itDiemDanh:
                        startActivity(new Intent(getApplicationContext(),TrangChuDiemDanhActivity.class));
                        break;
                    case R.id.itSetting:
                        startActivity(new Intent(getApplicationContext(),SettingActivity.class));
                        break;

                }

                return false;
            }
        });
        destination = new File(Environment.getExternalStorageDirectory(), name + ".jpg");
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeviceUltil deviceUltil = new DeviceUltil(activity);
                deviceUltil.CheckPermissionStorage();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK ){
            Intent intent = new Intent(this, PostActivity.class);
            intent.putExtra("path",destination.getAbsolutePath());
            startActivity(intent);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getInfoUser() {

        profile = realm.where(EntityUserProfile.class).findFirst();
        VolleyHelper volleyHelper = new VolleyHelper(this,getResources().getString(R.string.url));
        volleyHelper.get("users/" + profile.getuID(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("data");
                    realm.beginTransaction();
                    profile.setFull_name(jsonObject.getString("full_name"));
                    realm.commitTransaction();
//                    full_name.setText(profile.getFull_name().toString());
                    Log.d("respone",profile.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.trangchu,menu);
        this.menu = menu;
        MenuItem iGioHang = menu.findItem(R.id.itGioHang);
        View giaoDienGioHang = MenuItemCompat.getActionView(iGioHang);
//        TextView gioHang = giaoDienGioHang.findViewById(R.id.)
        giaoDienGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View vi = inflater.inflate(R.layout.custom_item_giohang,null);
                TextView notification = (TextView) findViewById(R.id.txtNotification);
                notification.setVisibility(View.GONE);
                startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Check su kien khi bam vao DrawerToggle
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        int vitri = item.getItemId();
        if (vitri == R.id.itDangXuat)
        {
            logOut();
//            LoginManager.getInstance().logOut();
//            finish();
//            startActivity(new Intent(this,SignUpIn.class));
        }else if (vitri == R.id.itTrangCaNhan){
            Intent intent = new Intent(this,UserProfileActivity.class);
            intent.putExtra("uId",ProfileInstance.getProfileInstance(this).getProfile().getuID());
            startActivity(intent);
        }else if (vitri==R.id.thongBao){
            startActivity(new Intent(this, ManagerMemberGroupActivity.class));
        }else if (vitri == R.id.it_search){
            startActivity(new Intent(this,SearchActivity.class));
        }
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (collapsingToolbarLayout.getHeight()+ verticalOffset<= 1.1 *ViewCompat.getMinimumHeight(collapsingToolbarLayout)){
            LinearLayout linearLayout = (LinearLayout) appBarLayout.findViewById(R.id.idLnSearch);
            linearLayout.setAlpha(0);
        }else {
            LinearLayout linearLayout = (LinearLayout) appBarLayout.findViewById(R.id.idLnSearch);
            linearLayout.setAlpha(1);
        }
    }
    public void logOut(){

        //Update token ve null
//        entityUserProfile = new EntityUserProfile();
//         entityUserProfile = realm.where(EntityUserProfile.class).findFirst();
//        VolleyHelper volleyHelper = new VolleyHelper(getApplicationContext(),getResources().getString(R.string.url));
        HashMap<String,String> parram = new HashMap<>();
        parram.put("token", PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token",""));
        Log.d("token",new JSONObject(parram).toString());
        VolleySingleton.getInstance(getApplicationContext()).post("logout", new JSONObject(parram), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        getSharedPreferences("token", 0).edit().clear().apply();
                        realm.deleteAll();
                        finish();
                        startActivity(new Intent(getApplicationContext(),SignUpIn.class));
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("errorCode1",VolleyHelper.checkErrorCode(error)+"");
//                Toast.makeText(getApplicationContext(), VolleyHelper.checkErrorCode(error), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
    private void setupStrip(JTabStyleDelegate tabStyleDelegate, int type) {
        tabStyleDelegate.setJTabStyle(type)
                .setShouldExpand(true)
                .setFrameColor(Color.TRANSPARENT)
//                .setTabTextSize(13)
                .setTextColor(Color.WHITE,Color.GRAY)
                //.setTextColor(R.drawable.tabstripbg)
                .setDividerColor(Color.TRANSPARENT)
                .setDividerPadding(0)
                .setUnderlineColor(Color.parseColor("#3045C01A"))
                .setUnderlineHeight(10);
//                .setIndicatorColor(Color.parseColor("#7045C01A"))
//                .setIndicatorHeight(4);
    }
    private int getDimen(int dimen) {
        return (int) getResources().getDimension(dimen);
    }

}
