package com.joker.thanglong.Fragment.Group;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.joker.thanglong.CustomView.DeleteEditText;
import com.joker.thanglong.Model.TrackerModel;
import com.joker.thanglong.R;

import java.util.ArrayList;

import Entity.EntityClass;
import adapter.AdapterRoom;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailRoomFragment extends Fragment {
    private Spinner spnShift;
    private DeleteEditText edtStudentCode;
    private RecyclerView rcvListStudent;
    private int shift;
    private Button btnSearch;
    AdapterRoom adapterRoom;
    TrackerModel trackerModel;
    RecyclerView.LayoutManager layoutManager;
    public DetailRoomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_track_room, container, false);
        addView(view);
        setupSpiner();
        addEvent();
        return view;
    }

    private void addEvent() {
        trackerModel = new TrackerModel(getActivity());
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trackerModel.searchRoom(edtStudentCode.getText().toString().trim(), shift, new TrackerModel.VolleyCallBackListRoom() {
                    @Override
                    public void onSuccess(ArrayList<EntityClass> list) {
                        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                        adapterRoom = new AdapterRoom(getActivity(),list);
                        rcvListStudent.setLayoutManager(layoutManager);
                        rcvListStudent.setAdapter(adapterRoom);
                        adapterRoom.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void setupSpiner() {
//            int arrPrivacy[] = {2,3,4,5,6,7};
//            final HashMap<Integer,Integer> spinnerMap = new HashMap<Integer, Integer>();
//            for (int i = 0; i < 3; i++)
//            {
//                spinnerMap.put((i+1),arrPrivacy[i]);
//            }
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext()
                    ,R.array.day,R.layout.support_simple_spinner_dropdown_item
                    );
            spnShift.setAdapter(adapter);
            spnShift.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    shift = i+1;
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
    }

    private void addView(View view) {
        spnShift = (Spinner) view.findViewById(R.id.spnShift);
        edtStudentCode = (DeleteEditText) view.findViewById(R.id.edtStudentCode);
        rcvListStudent = (RecyclerView) view.findViewById(R.id.rcvListStudent);
        btnSearch = (Button) view.findViewById(R.id.btnSearch);

    }

}
