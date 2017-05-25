package com.joker.thanglong.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joker.thanglong.R;

import java.util.ArrayList;

import adapter.AdapterMyGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment {
    private Toolbar toolbar;
    private RecyclerView rcvMyGroup;
    private AdapterMyGroup adapterMyGroup;
    private ArrayList<String> myListGroup;
    private RecyclerView.LayoutManager layoutManager;
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
        myListGroup = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            myListGroup.add("1");
        }
        adapterMyGroup = new AdapterMyGroup(getActivity(),myListGroup);
        layoutManager = new GridLayoutManager(getActivity(),3, LinearLayoutManager.VERTICAL,false);
        rcvMyGroup.setLayoutManager(layoutManager);
        rcvMyGroup.setAdapter(adapterMyGroup);
        adapterMyGroup.notifyDataSetChanged();
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
    }

}
