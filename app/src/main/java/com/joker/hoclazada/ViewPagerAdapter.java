package com.joker.hoclazada;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.joker.hoclazada.Fragment.TinNhan;
import com.joker.hoclazada.Fragment.TrangChu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joker on 2/9/17.
 */

public class ViewPagerAdapter  extends FragmentPagerAdapter{
    List<Fragment> listFragment = new ArrayList<Fragment>();
    List<String> listName = new ArrayList<String>();
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        listFragment.add(new TrangChu());
        listFragment.add(new TinNhan());

        listName.add("Trang chủ");
        listName.add("Tin nhắn");
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listName.get(position);
    }
}
