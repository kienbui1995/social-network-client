package com.joker.thanglong.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.joker.thanglong.R;
import com.joker.thanglong.Ultil.ProfileInstance;
import com.joker.thanglong.Model.UserModel;

import java.util.ArrayList;

import Entity.EntityFollow;
import adapter.AdapterFollowingList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowerFragment extends Fragment {
    private ListView lvFollowerList;
    ArrayList<EntityFollow> items;
    AdapterFollowingList adapterFollowingList;
    UserModel userModel;

    public FollowerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follower, container, false);
        lvFollowerList = (ListView) view.findViewById(R.id.lvFollowerList);
        userModel = new UserModel(getActivity(), ProfileInstance.getProfileInstance(getActivity()).getProfile().getuID());
        getFollowerList();
        return view;
    }

    private void getFollowerList() {
        items = new ArrayList<>();
        userModel.getListFollow("followers", new UserModel.VolleyCallBackFollow() {
            @Override
            public void onSuccess(ArrayList<EntityFollow> listFollow) {
                items = listFollow;
                adapterFollowingList = new AdapterFollowingList(getActivity(),R.layout.custom_follow_list,items);
                lvFollowerList.setAdapter(adapterFollowingList);
            }
        });

    }

}
