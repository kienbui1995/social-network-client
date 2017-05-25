package com.joker.thanglong.Fragment.Group;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joker.thanglong.R;

import java.util.ArrayList;

import adapter.AdapterMemberGroup;

import static com.joker.thanglong.R.id.rcvMemberRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlockedMemberGroupFragment extends Fragment {
    private RecyclerView rcvMemberBlock;
    private ArrayList<String> listMemberBlock;
    private AdapterMemberGroup adapter;
    private RecyclerView.LayoutManager layoutManager;



    public BlockedMemberGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blocked_member_group, container, false);
        addView(view);
        initData();
        return view;
    }

    private void initData() {
        listMemberBlock = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listMemberBlock.add("hihihi");
        }
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        adapter = new AdapterMemberGroup(getActivity(),listMemberBlock,3);
        rcvMemberBlock.setLayoutManager(layoutManager);
        rcvMemberBlock.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void addView(View view) {
        rcvMemberBlock = (RecyclerView) view.findViewById(R.id.rcvMemberBlock);
    }

}
