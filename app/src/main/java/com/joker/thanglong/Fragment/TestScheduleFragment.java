package com.joker.thanglong.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joker.thanglong.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestScheduleFragment extends Fragment {


    public TestScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_schedule, container, false);
    }

}
