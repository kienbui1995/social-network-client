package com.joker.thanglong.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.joker.thanglong.R;

import java.util.ArrayList;
import java.util.List;

import adapter.AdapterPinPostPages;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePagesFragment extends Fragment {

    private RecyclerView rcvPinTopPage;
    private Button btnGhimTop;
    private RecyclerView rcvPostPage;
    List<String> listPinPost;
    AdapterPinPostPages adapterPinPostPages;


    public HomePagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_pages, container, false);
        rcvPinTopPage = (RecyclerView) view.findViewById(R.id.rcvPinTopPage);
        listPinPost = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            String ten = "Bai viet" + i;
            listPinPost.add(ten);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        adapterPinPostPages = new AdapterPinPostPages(getActivity(), listPinPost);

        rcvPinTopPage.setLayoutManager(layoutManager);
        rcvPinTopPage.setAdapter(adapterPinPostPages);
        rcvPinTopPage.setNestedScrollingEnabled(false);
        adapterPinPostPages.notifyDataSetChanged();
        return view;
    }

}
