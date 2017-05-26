package com.joker.thanglong.Fragment.Group;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joker.thanglong.Model.GroupModel;
import com.joker.thanglong.R;

import java.util.ArrayList;

import Entity.EntityGroup;
import adapter.AdapterListTopGroup;
import adapter.AdapterMyGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment {
    private Toolbar toolbar;
    private RecyclerView rcvMyGroup;
    private AdapterMyGroup adapterMyGroup;
    private RecyclerView rcvGroupPopular;
    private ArrayList<EntityGroup> myListGroup;
    private ArrayList<EntityGroup> groupPopular;
    private AdapterListTopGroup adapterListTopGroup;
    private TextView txtViewAllGroup;
    private RecyclerView.LayoutManager layoutManager;
    private GroupModel groupModel;
    public GroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        addView(view);
        initToolBar();
        initData();
        return view;
    }

    private void initData() {
        groupModel = new GroupModel(getActivity());
        initPopularGroup();
        initMyGroup();
    }

    private void initMyGroup() {
        myListGroup = new ArrayList<>();
        groupModel.getListGroupJoin(new GroupModel.VolleyCallbackListGroup() {
            @Override
            public void onSuccess(ArrayList<EntityGroup> listGroup) {
                myListGroup = listGroup;
                Log.d("size",myListGroup.size()+"");
                adapterMyGroup = new AdapterMyGroup(getActivity(),myListGroup);
                layoutManager = new GridLayoutManager(getActivity(),3, LinearLayoutManager.VERTICAL,false);
                rcvMyGroup.setLayoutManager(layoutManager);
                rcvMyGroup.setAdapter(adapterMyGroup);
                adapterMyGroup.notifyDataSetChanged();
            }
        });


    }

    private void initPopularGroup() {
        groupPopular = new ArrayList<>();
        groupModel.getListGroup(new GroupModel.VolleyCallbackListGroup() {
            @Override
            public void onSuccess(ArrayList<EntityGroup> listGroup) {
                groupPopular = listGroup;
                adapterListTopGroup = new AdapterListTopGroup(getActivity(),groupPopular);
                layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
                rcvGroupPopular.setLayoutManager(layoutManager);
                rcvGroupPopular.setAdapter(adapterListTopGroup);
                adapterListTopGroup.notifyDataSetChanged();
            }
        });
    }

    private void initToolBar() {
        toolbar.setTitle("Nhóm");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStackImmediate();
            }
        });
    }

    private void addView(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        rcvMyGroup = (RecyclerView) view.findViewById(R.id.rcvMyGroup);
        txtViewAllGroup = (TextView) view.findViewById(R.id.txtViewAllGroup);
        rcvGroupPopular = (RecyclerView) view.findViewById(R.id.rcvGroupPopular);
    }

}
