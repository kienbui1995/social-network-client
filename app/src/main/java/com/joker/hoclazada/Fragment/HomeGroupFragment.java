package com.joker.hoclazada.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.joker.hoclazada.R;

import java.util.ArrayList;
import java.util.List;

import adapter.AdapterHome;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeGroupFragment extends Fragment {
    Button btnAdd;
    AdapterHome adapterHome;
    List<String> list;
    RecyclerView recyclerView;

    public HomeGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_group, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rcvListPostGroup);
        list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            String ten = "Bai viet" + i;
            list.add(ten);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        adapterHome = new AdapterHome(getActivity(), list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterHome);
        recyclerView.setNestedScrollingEnabled(false);
        adapterHome.notifyDataSetChanged();
        return view;
    }

}
