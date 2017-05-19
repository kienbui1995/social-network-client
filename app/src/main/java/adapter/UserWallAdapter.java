package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.joker.thanglong.Fragment.UserPictureWallFragment;
import com.joker.thanglong.Fragment.UserPostWallFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joker on 5/11/17.
 */

public class UserWallAdapter extends FragmentPagerAdapter{
    List<Fragment> listFragment = new ArrayList<Fragment>();
    List<String> listName = new ArrayList<String>();
    public UserWallAdapter(FragmentManager fm) {
        super(fm);
        listFragment.add(new UserPostWallFragment());
        listFragment.add(new UserPictureWallFragment());
        listName.add("Trang chủ");
        listName.add("Hình ảnh");
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
        return null;
    }
}
