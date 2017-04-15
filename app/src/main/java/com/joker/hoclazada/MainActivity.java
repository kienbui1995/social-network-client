package com.joker.hoclazada;

import com.google.firebase.database.DatabaseReference;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.joker.hoclazada.Presenter.TrangChu.XuLyDuLieu.PresenterXuLyDuLieu;
import com.joker.hoclazada.Ultil.PutParamFacebook;
import com.joker.hoclazada.Ultil.VolleyHelper;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    private Toolbar toolbar;
    private TabLayout tabHost;
    private ViewPager viewPager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        tabHost = (TabLayout) findViewById(R.id.tabHost);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        toolbar = (Toolbar) findViewById(R.id.toolbarA);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        content = (FrameLayout) findViewById(R.id.content);
//        epMenu = (ExpandableListView) findViewById(R.id.epMenu);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBar);
        btnPostStatus = (Button) findViewById(R.id.btnPost);
//        lvMenu = (ListView) findViewById(R.id.lvMenu);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapToolbar);
        frContent = (LinearLayout) findViewById(R.id.frContent);
        nvMenu = (NavigationView) findViewById(R.id.nvMenu);

//        notification = (TextView) findViewById(R.id.txtNotification);
        //Toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        //Tabhost
        tabHost.setupWithViewPager(viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        //DrawerLayout
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Doc thu preferences
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        String email = prefs.getString("fb_id",null);
//        Log.d("emaiL",email);

        actionBarDrawerToggle.syncState();
        //Set su kien click cho Appbar layout
        appBarLayout.addOnOffsetChangedListener(this);
        //Presenter
        PresenterXuLyDuLieu presenterXuLyDuLieu = new PresenterXuLyDuLieu(this);
        AccessToken accessToken = presenterXuLyDuLieu.LayTenNguoiDungFacebook();
//        Log.d("token",accessToken.toString());
//        Log.d("tokenn",""+accessToken.getUserId());
//        //Tam thoi commment de chay
//        if (accessToken == null){
//            finish();
//            startActivity(new Intent(this,SignUpIn.class));
//        }
//
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
//                        startActivity(new Intent(getApplicationContext(),UserProfileActivity.class));
                    case R.id.itLearing:
                        break;
                    case R.id.itGroup:
//                        //Framgment
//                        manager = getSupportFragmentManager();
//                        transaction = manager.beginTransaction();
//                        GroupFragment groupFragment = new GroupFragment();
//                        transaction.replace(R.id.contentLayout,groupFragment);
//                        transaction.commit();
//                        drawerLayout.closeDrawers();
                        startActivity(new Intent(getApplicationContext(),GroupActivity.class));

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

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", "assss");
        params.put("password","undefine");
        params.put("device","undefifsdfsne");

        VolleyHelper volleyHelper = new VolleyHelper(this,"http://192.168.0.92:8080",params);
volleyHelper.Post("login", new Response.Listener<String>() {
    @Override
    public void onResponse(String response) {
        Log.d("jsonA",response+"");
    }
}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("jsonA",error+"");

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
            LoginManager.getInstance().logOut();
            finish();
            startActivity(new Intent(this,SignUpIn.class));
        }else if (vitri == R.id.itTrangCaNhan){
            startActivity(new Intent(this,UserProfileActivity.class));
        }else if (vitri==R.id.thongBao){
            startActivity(new Intent(this,HomeListGroupActivity.class));
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
}
