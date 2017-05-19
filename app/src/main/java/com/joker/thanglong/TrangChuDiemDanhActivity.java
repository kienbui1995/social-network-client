package com.joker.thanglong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.AdapterSinhVien;

/**
 * Created by joker on 3/6/17.
 */

public class TrangChuDiemDanhActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView txtInfoDiemDanh;
    private RecyclerView rcDanhSachSV;
    private List<String> list;
    AdapterSinhVien adapterSinhVien;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_trangchu_diemdanh);
        addControll();
        addEvent();
    }

    private void addEvent() {
        list = new ArrayList<>();
        for (int i = 0; i< 20;i++){
            String a = i+ "hii";
            list.add(a);
        }
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL,false);
        adapterSinhVien = new AdapterSinhVien(this, list);

        rcDanhSachSV.setLayoutManager(layoutManager);
        rcDanhSachSV.setAdapter(adapterSinhVien);
        rcDanhSachSV.setNestedScrollingEnabled(false);
        adapterSinhVien.notifyDataSetChanged();
    }

    private void addControll() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtInfoDiemDanh = (TextView) findViewById(R.id.txtInfoDiemDanh);
        rcDanhSachSV = (RecyclerView) findViewById(R.id.rcDanhSachSV);
        toolbar.setTitle("Điểm danh");
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.diemdanhthucong,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
