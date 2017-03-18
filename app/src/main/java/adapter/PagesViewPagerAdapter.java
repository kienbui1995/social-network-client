package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.joker.hoclazada.Fragment.AboutPagesFragment;
import com.joker.hoclazada.Fragment.HomePagesFragment;
import com.joker.hoclazada.Fragment.PhotoPagesFragment;
import com.joker.hoclazada.Fragment.SettingPagesFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joker on 3/13/17.
 */

public class PagesViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> listFragment = new ArrayList<Fragment>();
    List<String> listName = new ArrayList<String>();
    public PagesViewPagerAdapter(FragmentManager fm) {
        super(fm);
        listFragment.add(new HomePagesFragment());
        listFragment.add(new PhotoPagesFragment());
        listFragment.add(new AboutPagesFragment());
        listFragment.add(new SettingPagesFragment());
        listName.add("Trang chủ");
        listName.add("Ảnh");
        listName.add("Thông tin");
        listName.add("Cài đặt");
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
