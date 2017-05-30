package com.joker.thanglong.Fragment.Group;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joker.thanglong.GroupActivity;
import com.joker.thanglong.Model.GroupModel;
import com.joker.thanglong.R;

import java.util.ArrayList;

import Entity.EntityMembership;
import adapter.AdapterMemberGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlockedMemberGroupFragment extends Fragment {
    private RecyclerView rcvMemberBlock;
    private ArrayList<EntityMembership> listMemberBlock;
    private AdapterMemberGroup adapter;
    private RecyclerView.LayoutManager layoutManager;
    private GroupModel groupModel;


    public BlockedMemberGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blocked_member_group, container, false);
        groupModel = new GroupModel(getActivity());

        addView(view);
        initData();
        return view;
    }

    private void initData() {
        listMemberBlock = new ArrayList<>();
        groupModel.getListMemberGroup(GroupActivity.groupInfo.getId(), "blocked", new GroupModel.VolleyCallbackListMemberGroup() {
            @Override
            public void onSuccess(ArrayList<EntityMembership> listMember) {
                listMemberBlock = listMember;
                layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                adapter = new AdapterMemberGroup(getActivity(),listMemberBlock,3);
                rcvMemberBlock.setLayoutManager(layoutManager);
                rcvMemberBlock.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void addView(View view) {
        rcvMemberBlock = (RecyclerView) view.findViewById(R.id.rcvMemberBlock);
    }

}
