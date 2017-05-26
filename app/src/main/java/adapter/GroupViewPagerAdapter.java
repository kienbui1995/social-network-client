package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.joker.thanglong.Fragment.Group.AboutGroupFragment;
import com.joker.thanglong.Fragment.Group.HomeGroupFragment;
import com.joker.thanglong.Fragment.Group.SettingGroupFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joker on 3/10/2017.
 */

public class GroupViewPagerAdapter extends FragmentPagerAdapter{
    List<Fragment> listFragment = new ArrayList<Fragment>();
    List<String> listName = new ArrayList<String>();

    public GroupViewPagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
        listFragment.add(new HomeGroupFragment());
        listFragment.add(new AboutGroupFragment());
        listFragment.add(new SettingGroupFragment());

        listName.add("Trang chủ");
        listName.add("Thông tin");
        listName.add("Cài đặt");
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
