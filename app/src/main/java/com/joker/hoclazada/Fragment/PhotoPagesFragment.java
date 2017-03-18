package com.joker.hoclazada.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joker.hoclazada.R;

import java.util.Arrays;
import java.util.List;

import adapter.AdapterListImagePages;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoPagesFragment extends Fragment {
    private RecyclerView rcvListImagePage;
    List<String> listPhoto;
    AdapterListImagePages adapterListImagePages;

    public PhotoPagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo_pages, container, false);
        rcvListImagePage = (RecyclerView) view.findViewById(R.id.rcvListImagePages);
        listPhoto = Arrays.asList(getActivity().getResources().getStringArray(R.array.user_photos));
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        adapterListImagePages = new AdapterListImagePages(getActivity(),listPhoto);
        rcvListImagePage.setLayoutManager(layoutManager);
        rcvListImagePage.setAdapter(adapterListImagePages);
        rcvListImagePage.setNestedScrollingEnabled(false);
        adapterListImagePages.notifyDataSetChanged();
        return view;
    }

}
