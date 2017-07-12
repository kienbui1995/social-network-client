package com.joker.thanglong.Fragment.Group;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.joker.thanglong.GroupChatActivity;
import com.joker.thanglong.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingGroupFragment extends Fragment {
    private Button txtAddToMenuBar;
    public SettingGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting_group, container, false);
        txtAddToMenuBar = (Button) view.findViewById(R.id.txtAddToMenuBar);
        txtAddToMenuBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), GroupChatActivity.class));
            }
        });
        return view;
    }

}
