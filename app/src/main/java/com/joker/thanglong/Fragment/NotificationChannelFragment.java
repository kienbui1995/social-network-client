package com.joker.thanglong.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joker.thanglong.Model.ChannelModel;
import com.joker.thanglong.R;

import java.util.ArrayList;

import Entity.EntityNotificationChannel;
import adapter.AdapterListNotificationChannel;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationChannelFragment extends Fragment {
    private RecyclerView rcvListNotiChannel;
    private AdapterListNotificationChannel adapterListNotificationChannel;
    private RecyclerView.LayoutManager layoutManager;
    private ChannelModel channelModel;


    public NotificationChannelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification_channel, container, false);
        addView(view);
        addData();
        return view;
    }

    private void addData() {
        channelModel = new ChannelModel(getActivity());
        channelModel.getListNoticationChannel(new ChannelModel.VolleyCallbackListNotificationChannel() {
            @Override
            public void onSuccess(ArrayList<EntityNotificationChannel> list) {
                adapterListNotificationChannel = new AdapterListNotificationChannel(list,getActivity());
                layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                rcvListNotiChannel.setLayoutManager(layoutManager);
                rcvListNotiChannel.setAdapter(adapterListNotificationChannel);
                adapterListNotificationChannel.notifyDataSetChanged();
            }
        });
    }

    private void addView(View view) {
        rcvListNotiChannel = (RecyclerView) view.findViewById(R.id.rcvListNotiChannel);
    }

}
