package com.joker.thanglong.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joker.thanglong.Model.TrackerModel;
import com.joker.thanglong.R;

import java.util.ArrayList;

import Entity.EntityViolation;
import adapter.AdapterMyViolation;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListViolationFragment extends Fragment {
    private Toolbar toolbar;
    private RecyclerView rcvListViPham;
    private AdapterMyViolation adapterMyViolation;
    private TrackerModel trackerModel;
    private RecyclerView.LayoutManager layoutManager;

    public ListViolationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_violation, container, false);
        addView(view);
        addEvent();
        return view;
    }

    private void addEvent() {
        trackerModel = new TrackerModel(getActivity());
        trackerModel.GetListViolation("TT001", new TrackerModel.VolleyCallBackStudentViolation() {
            @Override
            public void onSuccess(ArrayList<EntityViolation> list) {
                adapterMyViolation = new AdapterMyViolation(getActivity(),list,2);
                layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                rcvListViPham.setLayoutManager(layoutManager);
                rcvListViPham.setAdapter(adapterMyViolation);
                adapterMyViolation.notifyDataSetChanged();
            }
        });
    }

    private void addView(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        rcvListViPham = (RecyclerView) view.findViewById(R.id.rcvListViPham);
    }

}
