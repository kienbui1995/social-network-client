package com.joker.thanglong.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.joker.thanglong.CustomView.DeleteEditText;
import com.joker.thanglong.Model.TrackerModel;
import com.joker.thanglong.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import Entity.EntityStudent;
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
    ArrayList<EntityStudent> items;
    RecyclerView.LayoutManager layoutManager;
    TrackerModel trackerModel;
    Timer timer;
    public TrackStudentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_track_student, container, false);
        timer = new Timer();
        items = new ArrayList<>();
        addView(view);
        initData();
        return view;
    }

    private void initData() {
        trackerModel = new TrackerModel(getActivity());
        edtStudentCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        items.clear();
                        trackerModel.findStudent(charSequence.toString(), new TrackerModel.VolleyCallBackListStudent() {
                            @Override
                            public void onSuccess(ArrayList<EntityStudent> list) {
                                items=list;
                                adapterTrackStudent = new AdapterTrackStudent(getActivity(),items);
                                layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                                rcvListStudent.setLayoutManager(layoutManager);
                                rcvListStudent.setAdapter(adapterTrackStudent);
                                adapterTrackStudent.notifyDataSetChanged();
                            }
                        });
                    }
                },1000);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void addView(View view) {
        edtStudentCode = (DeleteEditText) view.findViewById(R.id.edtStudentCode);
        rcvListStudent = (RecyclerView) view.findViewById(R.id.rcvListStudent);
    }

}
