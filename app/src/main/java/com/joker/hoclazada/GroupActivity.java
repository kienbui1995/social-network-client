package com.joker.hoclazada;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.StringDef;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.joker.hoclazada.Ultil.FilePath;
import com.squareup.picasso.Picasso;

import adapter.GroupViewPagerAdapter;
import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.gpu.ContrastFilterTransformation;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        addControll();
        addEvent();

    }

    private void ChangeImage() {
        mIntent = new Intent();
        mIntent.setType("image/*");
        mIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(mIntent, getString(R.string.select_picture)),
                1);
    }

    private void addEvent() {
        toolbar.setTitle("Toán tài chính 1.5");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
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
                ChangeImage();
            }
        });
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
                Picasso.with(getApplicationContext())
                        .load(data.getData()).fit()
                        .transform(new BlurTransformation(getApplicationContext(),2))
                        .into(imgAnhBia);
                if (imgAnhBia.getVisibility() != View.VISIBLE) {
                    imgAnhBia.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
