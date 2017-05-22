package com.joker.thanglong.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joker.thanglong.Interface.EndlessScrollListener;
import com.joker.thanglong.R;
import com.joker.thanglong.Model.PostModel;
import com.joker.thanglong.UserProfileActivity;

import java.util.ArrayList;

import Entity.EntityStatus;
import adapter.AdapterListPhoto;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserPictureWallFragment extends Fragment {
    private RecyclerView rcvWallPhoto;
    private AdapterListPhoto adapterListPhoto;
    private ArrayList<EntityStatus> listPhoto;
    private SwipeRefreshLayout swrPictureWall;
    private EndlessScrollListener endlessScrollListener;
    PostModel postModel;
    String uID;
    boolean isLoad = false;
    public UserPictureWallFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_picture_wall, container, false);
       if (!isLoad){
           getIntent();
           addControl(view);
           getData();
           addEvent();
       }
        return view;
    }

    private void addEvent() {
        swrPictureWall.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                if (swrPictureWall.isRefreshing()){
                    swrPictureWall.setRefreshing(false);
                }
            }
        });

    }

    private void getIntent() {
        uID = UserProfileActivity.id;
    }

    private void getData() {
        listPhoto = new ArrayList<>();
        postModel = new PostModel(getActivity(),Integer.parseInt(uID),"photo");
        postModel.getListPost(0,new PostModel.VolleyCallbackListStatus() {
            @Override
            public void onSuccess(ArrayList<EntityStatus> entityStatuses) {
                listPhoto=entityStatuses;
               loadMore();
            }
        });
    }

    private void loadMore() {
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        adapterListPhoto = new AdapterListPhoto(getActivity(),listPhoto);
        rcvWallPhoto.setLayoutManager(layoutManager);
        rcvWallPhoto.setAdapter(adapterListPhoto);
        endlessScrollListener =  new EndlessScrollListener((StaggeredGridLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int page, final int totalItemsCount, RecyclerView vie) {
                Log.d("totalItem",totalItemsCount+"");
                postModel.getListPost(totalItemsCount,new PostModel.VolleyCallbackListStatus() {
                    @Override
                    public void onSuccess(ArrayList<EntityStatus> entityStatuses) {
                        listPhoto.addAll(entityStatuses);
                        adapterListPhoto.notifyDataSetChanged();
                    }
                });
            }
        };
        rcvWallPhoto.setOnScrollListener(endlessScrollListener);
    }

    private void addControl(View view) {
        rcvWallPhoto = (RecyclerView) view.findViewById(R.id.rcvWallPhoto);
        swrPictureWall = (SwipeRefreshLayout) view.findViewById(R.id.swrPictureWall);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isLoad){
            isLoad=true;
        }
    }
}
