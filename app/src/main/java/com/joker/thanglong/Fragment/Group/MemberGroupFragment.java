package com.joker.thanglong.Fragment.Group;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.joker.thanglong.R;

import java.util.ArrayList;

import adapter.AdapterMemberGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberGroupFragment extends Fragment {
    private EditText edtSearchMember;
    private TextView textView18;
    private RecyclerView rcvMember;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<String> listMember;
    AdapterMemberGroup adapterMemberGroup;
    public MemberGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_member_group, container, false);
        addView(view);
        initData();
        return view;
    }

    private void addView(View view) {
        edtSearchMember = (EditText) view.findViewById(R.id.edtSearchMember);
        textView18 = (TextView) view.findViewById(R.id.textView18);
        rcvMember = (RecyclerView) view.findViewById(R.id.rcvMember);
    }

    private void initData() {
        listMember = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listMember.add("hihihi");
        }
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        adapterMemberGroup = new AdapterMemberGroup(getActivity(),listMember,1);
        rcvMember.setLayoutManager(layoutManager);
        rcvMember.setAdapter(adapterMemberGroup);
        adapterMemberGroup.notifyDataSetChanged();
    }

}
