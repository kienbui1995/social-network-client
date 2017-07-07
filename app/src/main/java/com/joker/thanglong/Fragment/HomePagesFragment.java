package com.joker.thanglong.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joker.thanglong.ChannelActivity;
import com.joker.thanglong.Model.ChannelModel;
import com.joker.thanglong.R;

import java.util.ArrayList;

import Entity.EntityNotificationChannel;
import adapter.AdapterListNotificationChannel;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePagesFragment extends Fragment {

    private RecyclerView rcvListNotiChannel;
    private RecyclerView rcvPostPage;
    ArrayList<EntityNotificationChannel> items;
    AdapterListNotificationChannel adapterListNotificationChannel;
    ChannelModel channelModel;

    public HomePagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_pages, container, false);
        addView(view);
        addEvent();


        return view;
    }

    private void addEvent() {

        channelModel = new ChannelModel(getActivity());
        channelModel.getNotificationOfChannel(ChannelActivity.channelInfo.getId(), new ChannelModel.VolleyCallbackListNotificationChannel() {
            @Override
            public void onSuccess(ArrayList<EntityNotificationChannel> list) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                adapterListNotificationChannel = new AdapterListNotificationChannel(list,getActivity());
                rcvListNotiChannel.setLayoutManager(layoutManager);
                rcvListNotiChannel.setAdapter(adapterListNotificationChannel);
                rcvListNotiChannel.setNestedScrollingEnabled(false);
                adapterListNotificationChannel.notifyDataSetChanged();
            }
        });

    }

    private void addView(View view) {
        rcvListNotiChannel = (RecyclerView) view.findViewById(R.id.rcvListNotiChannel);
    }

}
