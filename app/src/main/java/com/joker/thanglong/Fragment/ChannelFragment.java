package com.joker.thanglong.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joker.thanglong.Model.ChannelModel;
import com.joker.thanglong.R;

import java.util.ArrayList;

import Entity.EntityChannel;
import adapter.AdapterListTopChannel;
import adapter.AdapterMyChannel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChannelFragment extends Fragment {
    private SwipeRefreshLayout swrChannel;
    private Toolbar toolbar;
    private TextView txtViewAllChan;
    private RecyclerView rcvChannelPopular;
    private RecyclerView rcvMyChannel;
    private AdapterMyChannel adapterMyChannel;
    private AdapterListTopChannel adapterListTopChannel;
    private ChannelModel channelModel;
    private ArrayList<EntityChannel> listChannels;
    private RecyclerView.LayoutManager layoutManager;
    public ChannelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_channel, container, false);
        addView(view);
        initToolBar();
        addData();
        return view;
    }

    private void addData() {
        channelModel = new ChannelModel(getActivity());
        initPopularChannel();
        initMyChannel();
        swrChannel.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initPopularChannel();
                initMyChannel();
                if (swrChannel.isRefreshing()){
                    swrChannel.setRefreshing(false);
                }
            }
        });

    }

    private void initMyChannel() {
        channelModel.getMyChannel(new ChannelModel.VolleyCallbackListChannel() {
            @Override
            public void onSuccess(ArrayList<EntityChannel> listChannel) {
                adapterMyChannel = new AdapterMyChannel(getActivity(),listChannel);
                layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
                rcvMyChannel.setLayoutManager(layoutManager);
                rcvMyChannel.setAdapter(adapterMyChannel);
                adapterMyChannel.notifyDataSetChanged();
            }
        });
    }

    private void initPopularChannel() {
        listChannels = new ArrayList<>();
        channelModel.getListChannel(new ChannelModel.VolleyCallbackListChannel() {
            @Override
            public void onSuccess(ArrayList<EntityChannel> listChannel) {
                listChannels = listChannel;
                adapterListTopChannel = new AdapterListTopChannel(getActivity(),listChannels);
                layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
                rcvChannelPopular.setLayoutManager(layoutManager);
                rcvChannelPopular.setAdapter(adapterListTopChannel);
                adapterListTopChannel.notifyDataSetChanged();
            }
        });

    }
    private void initToolBar() {
        toolbar.setTitle("Kênh");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStackImmediate();
            }
        });
    }

    private void addView(View view) {
        swrChannel = (SwipeRefreshLayout) view.findViewById(R.id.swrChannel);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        txtViewAllChan = (TextView) view.findViewById(R.id.txtViewAllChan);
        rcvChannelPopular = (RecyclerView) view.findViewById(R.id.rcvChannelPopular);
        rcvMyChannel = (RecyclerView) view.findViewById(R.id.rcvMyChannel);
    }

}
