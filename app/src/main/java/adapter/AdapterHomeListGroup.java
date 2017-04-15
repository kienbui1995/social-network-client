package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.joker.hoclazada.Fragment.DiscoverGroupFragment;
import com.joker.hoclazada.Fragment.HomeListGroupFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joker on 4/7/17.
 */

public class AdapterHomeListGroup extends FragmentPagerAdapter {
    List<Fragment> listFragment = new ArrayList<Fragment>();
    List<String> listName = new ArrayList<String>();
    public AdapterHomeListGroup(FragmentManager fm) {
        super(fm);
        listFragment.add(new HomeListGroupFragment());
        listFragment.add(new DiscoverGroupFragment());
        listName.add("Danh sách");
        listName.add("Hoạt động");
    }


    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listName.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listName.get(position);
    }
}
