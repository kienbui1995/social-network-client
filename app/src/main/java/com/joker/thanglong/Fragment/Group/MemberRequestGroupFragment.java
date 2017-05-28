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
public class MemberRequestGroupFragment extends Fragment {
    private RecyclerView rcvMemberRequest;
    private ArrayList<EntityMembership> listMemberRequest;
    private AdapterMemberGroup adapter;
    private RecyclerView.LayoutManager layoutManager;
    GroupModel groupModel;
    public MemberRequestGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_member_request_group, container, false);
        groupModel = new GroupModel(getActivity());
        addView(view);
        initData();
        return view;
    }

    private void initData() {
        listMemberRequest = new ArrayList<>();
        groupModel.getListMemberRequest(GroupActivity.groupInfo.getId(), new GroupModel.VolleyCallbackListMemberGroup() {
            @Override
            public void onSuccess(ArrayList<EntityMembership> listMember) {
                listMemberRequest = listMember;
                layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                adapter = new AdapterMemberGroup(getActivity(),listMemberRequest,2);
                rcvMemberRequest.setLayoutManager(layoutManager);
                rcvMemberRequest.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void addView(View view) {
        rcvMemberRequest = (RecyclerView) view.findViewById(R.id.rcvMemberRequest);
    }

}
