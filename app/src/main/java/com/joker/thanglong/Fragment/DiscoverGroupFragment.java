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

import adapter.AdapterDiscoverGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverGroupFragment extends Fragment {
    private RecyclerView rcvListDiscoverGroup;
    AdapterDiscoverGroup adapterDiscoverGroup;


    public DiscoverGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_discover_group, container, false);
        rcvListDiscoverGroup = (RecyclerView) view.findViewById(R.id.rcvListDiscoverGroup);
        List<String> list = new ArrayList<>();
        for (int i =0;i <15; i++)
        {
            list.add("1");
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        adapterDiscoverGroup = new AdapterDiscoverGroup(getActivity(),list);
        rcvListDiscoverGroup.setLayoutManager(layoutManager);
        rcvListDiscoverGroup.setAdapter(adapterDiscoverGroup);
        rcvListDiscoverGroup.setNestedScrollingEnabled(true);
        adapterDiscoverGroup.notifyDataSetChanged();
        return view;
    }

}
