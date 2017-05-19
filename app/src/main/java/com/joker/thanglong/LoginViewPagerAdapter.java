package com.joker.thanglong;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.joker.thanglong.Fragment.DangKy;
import com.joker.thanglong.Fragment.DangNhap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joker on 2/10/17.
 */

public class LoginViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> listTab = new ArrayList<Fragment>();
    List<String> listNameTab = new ArrayList<String>();
    public LoginViewPagerAdapter(FragmentManager fm) {
        super(fm);
        listTab.add(new DangNhap());
        listTab.add(new DangKy());

        listNameTab.add("Đăng nhập");
        listNameTab.add("Đăng ký");
    }

    @Override
    public Fragment getItem(int position) {
        return listTab.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return listNameTab.get(position);
    }
}
