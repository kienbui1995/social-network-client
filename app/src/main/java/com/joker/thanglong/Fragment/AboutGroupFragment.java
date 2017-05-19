package com.joker.thanglong.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joker.thanglong.R;

import java.util.ArrayList;
import java.util.List;

import adapter.AdapterTenMember;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutGroupFragment extends Fragment {
    private AdapterTenMember adapterTenMember;
    private List<String> list;
    private RecyclerView rcvTenMember;

    public AboutGroupFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_group, container, false);
        rcvTenMember = (RecyclerView) view.findViewById(R.id.rcvTenMember);
        list = new ArrayList<>();
        for (int i = 0; i<10;i++){
            list.add(i+"");
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        adapterTenMember = new AdapterTenMember(getActivity(), list);

        rcvTenMember.setLayoutManager(layoutManager);
        rcvTenMember.setAdapter(adapterTenMember);
        rcvTenMember.setNestedScrollingEnabled(false);
        adapterTenMember.notifyDataSetChanged();
        return view;
    }


}
