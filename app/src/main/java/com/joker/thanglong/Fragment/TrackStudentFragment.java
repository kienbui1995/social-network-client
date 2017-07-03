package com.joker.thanglong.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.joker.thanglong.CustomView.DeleteEditText;
import com.joker.thanglong.R;

import java.util.ArrayList;

import adapter.AdapterTrackStudent;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackStudentFragment extends Fragment {
    private TextView textView2;
    private Spinner spnTerm;
    private DeleteEditText edtStudentCode;
    private RecyclerView rcvListStudent;
    AdapterTrackStudent adapterTrackStudent;
    ArrayList<String> items;
    RecyclerView.LayoutManager layoutManager;
    public TrackStudentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_track_student, container, false);
        addView(view);
        initData();
        return view;
    }

    private void initData() {
        items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            items.add("1");
        }
        adapterTrackStudent = new AdapterTrackStudent(getActivity(),items);
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rcvListStudent.setLayoutManager(layoutManager);
        rcvListStudent.setAdapter(adapterTrackStudent);
    }

    private void addView(View view) {
        edtStudentCode = (DeleteEditText) view.findViewById(R.id.edtStudentCode);
        rcvListStudent = (RecyclerView) view.findViewById(R.id.rcvListStudent);
    }

}
