package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.joker.thanglong.Fragment.Group.BlockedMemberGroupFragment;
import com.joker.thanglong.Fragment.Group.MemberGroupFragment;
import com.joker.thanglong.Fragment.Group.MemberRequestGroupFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joker on 5/24/17.
 */

public class ManagerMemberGroupPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> listFragment = new ArrayList<Fragment>();
    List<String> listName = new ArrayList<String>();
    public ManagerMemberGroupPagerAdapter(FragmentManager fm,boolean isAdmin,int groupType) {
        super(fm);
        initFragment(isAdmin,groupType);
    }

    private void initFragment(boolean isAdmin,int groupType) {
        if (isAdmin){
            Log.d("isAdmin","11");
            if (groupType==1) {
                Log.d("isAdmin", "12");
                listFragment.add(new MemberGroupFragment());
                listFragment.add(new BlockedMemberGroupFragment());
                listName.add("Thành viên");
                listName.add("Chặn");
            }else if(groupType == 2){
                    listFragment.add(new MemberGroupFragment());
                    listFragment.add(new MemberRequestGroupFragment());
                    listFragment.add(new BlockedMemberGroupFragment());
                    listName.add("Thành viên");
                    listName.add("Yêu cầu");
                    listName.add("Chặn");
            }
        }else {
            listFragment.add(new MemberGroupFragment());
            listName.add("Thành viên");
        }
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
