package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.joker.hoclazada.Fragment.FollowerFragment;
import com.joker.hoclazada.Fragment.FollowingFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joker on 4/25/17.
 */

public class FollowViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> listFragment = new ArrayList<Fragment>();
    List<String> listName = new ArrayList<String>();
    public FollowViewPagerAdapter(FragmentManager fm) {
        super(fm);
        listFragment.add(new FollowingFragment());
        listFragment.add(new FollowerFragment());
        listName.add("Following");
        listName.add("Follower");
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listName.get(position);
    }
}
