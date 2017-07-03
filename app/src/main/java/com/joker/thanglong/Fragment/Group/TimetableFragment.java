package com.joker.thanglong.Fragment.Group;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joker.thanglong.Model.TimeTableModel;
import com.joker.thanglong.R;
import com.joker.thanglong.TimeTableActivity;
import com.kelin.scrollablepanel.library.ScrollablePanel;

import java.util.ArrayList;

import Entity.EntityClass;
import adapter.AdapterTimeTable;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimetableFragment extends Fragment {
    private ScrollablePanel spTimeTable;
    private AdapterTimeTable adapterTimeTable;
    private TimeTableModel timeTableModel;
    public TimetableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.detail_time_table, container, false);
        timeTableModel = new TimeTableModel(getActivity());
        addView(view);
        addData(TimeTableActivity.term);
        return view;
    }

    private void addView(View view) {
        spTimeTable = (ScrollablePanel) view.findViewById(R.id.spTimeTable);
    }
    private void addData(int code) {
        timeTableModel.getDataTimeTable(code, new TimeTableModel.VolleyCallbackGetDataTimeTable() {
            @Override
            public void onSuccess(ArrayList<EntityClass> itemsClass) {
                adapterTimeTable = new AdapterTimeTable(itemsClass,getActivity());
                spTimeTable.setPanelAdapter(adapterTimeTable);
                spTimeTable.notifyDataSetChanged();
            }
        });
    }

}
