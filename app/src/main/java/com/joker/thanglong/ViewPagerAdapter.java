package com.joker.thanglong;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.joker.thanglong.Fragment.NotificationChannelFragment;
import com.joker.thanglong.Fragment.TinNhan;
import com.joker.thanglong.Fragment.TrangChu;

import java.util.ArrayList;
import java.util.List;

import april.yun.ISlidingTabStrip;

/**
 * Created by joker on 2/9/17.
 */

public class ViewPagerAdapter  extends FragmentPagerAdapter implements ISlidingTabStrip.IconTabProvider{
    List<Fragment> listFragment = new ArrayList<Fragment>();
    List<String> listName = new ArrayList<String>();
    private int[] mSelectors;
    public ViewPagerAdapter(FragmentManager fm, int[] mSelectors) {
        super(fm);
        listFragment.add(new TrangChu());
        listFragment.add(new TinNhan());
        listFragment.add(new NotificationChannelFragment());

        listName.add("Trang chủ");
        listName.add("Tin nhắn");
        listName.add("Thông báo");
        this.mSelectors = mSelectors;
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

    @Override
    public int[] getPageIconResIds(int position) {
        return null;
    }

    @Override
    public int getPageIconResId(int position) {
        return 0;
    }


}
