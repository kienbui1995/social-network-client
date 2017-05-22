package com.joker.thanglong.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joker.thanglong.Interface.EndlessScrollListener;
import com.joker.thanglong.Model.PostModel;
import com.joker.thanglong.Model.UserModel;
import com.joker.thanglong.R;
import com.joker.thanglong.UserProfileActivity;

import java.util.ArrayList;

import Entity.EntityStatus;
import adapter.AdapterHome;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserPostWallFragment extends Fragment {
    private RecyclerView rcvWallPost;
    private AdapterHome adapterHome;
    ArrayList<EntityStatus> items;
    ArrayList<EntityStatus> itemsAdd;
    ArrayList<EntityStatus> itemsAdapter;
    RecyclerView.LayoutManager layoutManager;
    private EndlessScrollListener endlessScrollListener;
    private PostModel postModel;
    private SwipeRefreshLayout swrPostWall ;
    UserModel userModel;

    public UserPostWallFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_post_wall, container, false);
//        UserModel userModel;
        addControl(view);
        addEvent();
        getData();
        return view;
    }

    private void addEvent() {
        swrPostWall.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                if (swrPostWall.isRefreshing()){
                    swrPostWall.setRefreshing(false);
                }
            }
        });
    }

    private void getData() {
        postModel = new PostModel(getActivity(), Integer.parseInt(UserProfileActivity.id),"post");
        postModel.getListPost(0,new PostModel.VolleyCallbackListStatus() {
            @Override
            public void onSuccess(ArrayList<EntityStatus> entityStatuses) {
                items = entityStatuses;
                loadMore();
            }
        });
    }

    private void loadMore() {
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        adapterHome = new AdapterHome(getActivity(), items,getActivity());
        rcvWallPost.setLayoutManager(layoutManager);
//        rcvNewFeed.getRecycledViewPool().clear();
        rcvWallPost.setAdapter(adapterHome);
        endlessScrollListener =  new EndlessScrollListener((LinearLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int page, final int totalItemsCount, RecyclerView vie) {
                postModel.getListPost(totalItemsCount,new PostModel.VolleyCallbackListStatus() {
                    @Override
                    public void onSuccess(ArrayList<EntityStatus> entityStatuses) {
                        items.addAll(entityStatuses);
                        adapterHome.notifyDataSetChanged();
                    }
                });
            }
        };
        rcvWallPost.setOnScrollListener(endlessScrollListener);
    }

    private void addControl(View view) {
        rcvWallPost = (RecyclerView) view.findViewById(R.id.rcvWallPost);
        swrPostWall = (SwipeRefreshLayout) view.findViewById(R.id.swrPostWall);
    }

}
