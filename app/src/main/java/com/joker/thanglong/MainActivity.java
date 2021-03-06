package com.joker.thanglong;

import com.google.firebase.database.DatabaseReference;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.joker.thanglong.Fragment.ChannelFragment;
import com.joker.thanglong.Fragment.Group.GroupFragment;
import com.joker.thanglong.Fragment.MyViolationFragment;
import com.joker.thanglong.Fragment.TestScheduleFragment;
import com.joker.thanglong.Model.UserModel;
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
        setContentView(R.layout.activity_main);
        //Khoi tao realm
        Realm.init(this);
        //Khoi tao doi tuong Realm
        getInfoUser();
        addControl();
        initViewpager();
        initToolBar();
        addEvent();

        nvMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
//                if (entityUserProfile.getRole().equals("student") && item.getItemId() == R.id.itProfile){
//                    item.setVisible(false);
//                }
                switch (id){
                    case R.id.itViPham:
                        getSupportFragmentManager().beginTransaction().add(R.id.frContentHome,new MyViolationFragment())
                                .addToBackStack(null).commit();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.itFollow:
                        startActivity(new Intent(getApplicationContext(),FollowActivity.class));
                        break;
                    case R.id.itTimeTable:
                        Intent intent = new Intent(getApplicationContext(),TimeTableActivity.class);
                        if (entityUserProfile.getRole().equals("student")){
                            intent.putExtra("check",1);
                            startActivity(intent);
                        }else {
                            intent.putExtra("check",2);
                            startActivity(intent);
                        }

                        break;
                    case R.id.itGroup:
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
                        manager = getSupportFragmentManager();
                        transaction = manager.beginTransaction();
                        ChannelFragment channelFragment = new ChannelFragment();
                        transaction.add(R.id.frContentHome,channelFragment,"CHANNEL");
                        transaction.addToBackStack(null);
                        transaction.commit();
                        drawerLayout.closeDrawers();
//                        startActivity(new Intent(getApplicationContext(),GroupActivity.class));
                        break;
                    case R.id.itLichThi:
                        getSupportFragmentManager().beginTransaction().add(R.id.frContentHome,new TestScheduleFragment())
                                .addToBackStack(null).commit();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.itLink:
                        startActivity(new Intent(getApplicationContext(),LinkAccountActivity.class));
                        break;
                    case R.id.itThanhTra:
                        startActivity(new Intent(getApplicationContext(),TrackerActivity.class));
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
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });


    }

    private void addEvent() {
        btnPostStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),PostActivity.class);
                intent.putExtra("postAt",1);
                startActivity(intent);
            }
        });

    }

    private void initToolBar() {
        //Toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        //Tabhost
//        tabHost.setupWithViewPager(viewPager);

        //DrawerLayout
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBarDrawerToggle.syncState();
        //Set su kien click cho Appbar layout
        appBarLayout.addOnOffsetChangedListener(this);
    }

    private void addControl() {
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

    }

    private void initViewpager() {
        int[] mSelectors = new int[] { R.drawable.tab1, R.drawable.tab2, R.drawable.tab3, R.drawable.tab4 };
        setupStrip(tabButtom.getTabStyleDelegate(), STYLE_ROUND);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),mSelectors);
        viewPager.setAdapter(viewPagerAdapter);
        tabButtom.bindViewPager(viewPager);
//        tabButtom.setPromptNum(1,5);
        tabButtom.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                tabButtom.setPromptNum(1,0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK ){
            Intent intent = new Intent(this, PostActivity.class);
            intent.putExtra("postAt",1);
            intent.putExtra("path",destination.getAbsolutePath());
            startActivity(intent);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getInfoUser() {
        realm = Realm.getDefaultInstance();
        profile = realm.where(EntityUserProfile.class).findFirst();
        VolleyHelper volleyHelper = new VolleyHelper(this,getResources().getString(R.string.url));
        volleyHelper.get("users/" + profile.getuID(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("data");
                    realm.beginTransaction();
                    profile.setFull_name(jsonObject.getString("full_name"));
                    if (jsonObject.has("avatar")) profile.setAvatar(jsonObject.getString("avatar"));
                    realm.commitTransaction();
                    View header = nvMenu.getHeaderView(0);
                    final TextView full_name = (TextView) header.findViewById(R.id.txtFullNameNavibar);
                    TextView txtMyHome = (TextView) header.findViewById(R.id.txtMyHome);
                    final ImageView imgAvatar = (ImageView) header.findViewById(R.id.imgAvatar);
                    txtMyHome.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(),UserProfileActivity.class);
                            intent.putExtra("uId",ProfileInstance.getProfileInstance(getApplicationContext()).getProfile().getuID());
                            startActivity(intent);
                        }
                    });
                    entityUserProfile = ProfileInstance.getProfileInstance(getApplicationContext()).getProfile();
                    token= entityUserProfile.getToken();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(getApplicationContext()).load(entityUserProfile.getAvatar()).crossFade().into(imgAvatar);
                            full_name.setText(entityUserProfile.getFull_name());
                            if (entityUserProfile.getRole().equals("student")){
                                Menu menu2 = nvMenu.getMenu();
                                menu2.findItem(R.id.itLink).setVisible(false);
                                menu2.findItem(R.id.itThanhTra).setVisible(false);
                                menu2.findItem(R.id.itAdmin).setVisible(false);
                            }else if (entityUserProfile.getRole().equals("supervisior")){
                                Menu menu2 = nvMenu.getMenu();
                                menu2.findItem(R.id.itViPham).setVisible(false);
                                menu2.findItem(R.id.itLink).setVisible(false);
                                menu2.findItem(R.id.itAdmin).setVisible(false);
                                menu2.findItem(R.id.itTimeTable).setVisible(false);
                                menu2.findItem(R.id.itLichThi).setVisible(false);
                            }else if (entityUserProfile.getRole().equals("user"))
                            {
                                Menu menu2 = nvMenu.getMenu();
                                menu2.findItem(R.id.itViPham).setVisible(false);
                                menu2.findItem(R.id.itAdmin).setVisible(false);
                                menu2.findItem(R.id.itTimeTable).setVisible(false);
                                menu2.findItem(R.id.itThanhTra).setVisible(false);
                                menu2.findItem(R.id.itLichThi).setVisible(false);
                            }else if (entityUserProfile.getRole().equals("teacher")){
                                Menu menu2 = nvMenu.getMenu();
                                menu2.findItem(R.id.itViPham).setVisible(false);
                                menu2.findItem(R.id.itAdmin).setVisible(false);
                                menu2.findItem(R.id.itLink).setVisible(false);
                                menu2.findItem(R.id.itThanhTra).setVisible(false);
                                menu2.findItem(R.id.itLichThi).setVisible(false);
                            }
                        }
                    },1000);
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
        }else if (vitri == R.id.it_search){
            startActivity(new Intent(this,SearchActivity.class));
        }
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (collapsingToolbarLayout.getHeight()+ verticalOffset<= 1.1 *ViewCompat.getMinimumHeight(collapsingToolbarLayout)){
            LinearLayout linearLayout = (LinearLayout) appBarLayout.findViewById(R.id.idLnSearch);
            linearLayout.setVisibility(View.INVISIBLE);
            linearLayout.setEnabled(false);
        }else {
            LinearLayout linearLayout = (LinearLayout) appBarLayout.findViewById(R.id.idLnSearch);
            linearLayout.setVisibility(View.VISIBLE);
            linearLayout.setEnabled(true);
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
        VolleySingleton.getInstance(getApplicationContext()).post(getApplicationContext(),"logout", new JSONObject(parram), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        finish();
                        getSharedPreferences("token", 0).edit().clear().apply();
                        realm.deleteAll();
                        VolleySingleton.mInstance = null;
                        startActivity(new Intent(getApplicationContext(),SignUpIn.class));
                    }
                });
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
