package com.joker.thanglong.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.joker.thanglong.Model.TimeTableModel;
import com.joker.thanglong.R;

import java.util.ArrayList;
import java.util.HashMap;

import Entity.EntityExamSchedule;
import Entity.EntityTerm;
import adapter.AdapterLichThi;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestScheduleFragment extends Fragment {
    private Spinner spnTerm;
    private RecyclerView rcvLichThi;
    private ArrayList<EntityExamSchedule> itemSchedule;
    private AdapterLichThi adapterLichThi;
    private RecyclerView.LayoutManager layoutManager;
    private TimeTableModel timeTableModel;
    private Toolbar toolbar;

    public TestScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_schedule, container, false);
        addView(view);
        timeTableModel = new TimeTableModel(getActivity());
        setupSpinner();
        addToolbar();
        return view;
    }

    private void addView(View view) {
        spnTerm = (Spinner) view.findViewById(R.id.spnTerm);
        rcvLichThi = (RecyclerView) view.findViewById(R.id.rcvLichThi);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
    }
    private void setupSpinner() {
        timeTableModel.getTerm(new TimeTableModel.VolleyCallbackGetTerm() {
            @Override
            public void onSuccess(final ArrayList<EntityTerm> itemsTerm) {
                ArrayList<String> listNameTerm = new ArrayList<String>();
                for (int i = 0; i < itemsTerm.size(); i++) {
                    listNameTerm.add(itemsTerm.get(i).getName());
                }
                final HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
                for (int i = 0; i < itemsTerm.size(); i++)
                {
                    spinnerMap.put((i+1),itemsTerm.get(i).getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item_term,listNameTerm);
                spnTerm.setAdapter(adapter);
                spnTerm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        timeTableModel.getLichThi(itemsTerm.get(i).getCode(), new TimeTableModel.VolleyCallbackGetLichThi() {
                            @Override
                            public void onSuccess(ArrayList<EntityExamSchedule> items) {
                                adapterLichThi = new AdapterLichThi(getActivity(),items);
                                layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                                rcvLichThi.setLayoutManager(layoutManager);
                                rcvLichThi.setAdapter(adapterLichThi);
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });
    }
    private void addToolbar() {
        toolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStackImmediate();
            }
        });
    }

}
