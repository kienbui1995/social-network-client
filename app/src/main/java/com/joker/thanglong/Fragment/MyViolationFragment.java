package com.joker.thanglong.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joker.thanglong.Model.TrackerModel;
import com.joker.thanglong.R;
import com.joker.thanglong.Ultil.ProfileInstance;

import java.util.ArrayList;

import Entity.EntityViolation;
import adapter.AdapterMyViolation;

/**
 * Created by joker on 2/9/17.
 */

public class MyViolationFragment extends Fragment{
    private Toolbar toolbar;
    private TextView txtSoLanViPham;
    private RecyclerView rcvListViPham;
    private AdapterMyViolation adapterMyViolation;
    private RecyclerView.LayoutManager layoutManager;
    private TrackerModel trackerModel;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lophoc,container,false);
        addView(view);
        addToolbar();
        addData();
        return view;
    }

    private void addData() {
        trackerModel = new TrackerModel(getActivity());
        trackerModel.GetDetailStudent(ProfileInstance.getProfileInstance(getActivity()).getProfile().getCode(), new TrackerModel.VolleyCallBackStudentViolation() {
            @Override
            public void onSuccess(ArrayList<EntityViolation> list) {
                adapterMyViolation = new AdapterMyViolation(getActivity(),list,1);
                layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                rcvListViPham.setLayoutManager(layoutManager);
                rcvListViPham.setAdapter(adapterMyViolation);
                adapterMyViolation.notifyDataSetChanged();
            }
        });
    }

    private void addView(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        txtSoLanViPham = (TextView) view.findViewById(R.id.txtSoLanViPham);
        rcvListViPham = (RecyclerView) view.findViewById(R.id.rcvListViPham);
    }
    private void addToolbar() {
        toolbar.setTitle("Vi phạm của tôi");
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
