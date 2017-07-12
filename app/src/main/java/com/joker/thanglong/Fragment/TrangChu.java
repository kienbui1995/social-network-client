package com.joker.thanglong.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.joker.thanglong.Interface.EndlessScrollListener;
import com.joker.thanglong.Model.PostModel;
import com.joker.thanglong.Model.UserModel;
import com.joker.thanglong.R;
import com.joker.thanglong.Ultil.ProfileInstance;

import java.util.ArrayList;

import Entity.EntityStatus;
import adapter.AdapterHome;
import io.realm.Realm;

/**
 * Created by joker on 2/9/17.
 */

public class TrangChu extends Fragment{

    private ProgressBar progressBarFeed;
    Button btnAdd;
    AdapterHome adapterHome;
    ArrayList<EntityStatus> items;
    ArrayList<EntityStatus> itemsAdd;
    ArrayList<EntityStatus> itemsAdapter;
    private SwipeRefreshLayout swrNewFeed;
    RecyclerView rcvNewFeed;
    RecyclerView.LayoutManager layoutManager;
    private EndlessScrollListener endlessScrollListener;
    UserModel userModel;
    Realm realm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trangchu, container, false);
        itemsAdapter = new ArrayList<>();
        userModel = new UserModel(getActivity(), ProfileInstance.getProfileInstance(getActivity()).getProfile().getuID());
        realm = Realm.getDefaultInstance();
        addControl(view);
        addEvent();
        getData();
        return view;
    }

    private void addEvent() {
        swrNewFeed.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                rcvNewFeed.getRecycledViewPool().clear();
//                items.clear();
//                itemsAdapter.clear();
                getData();
                if (swrNewFeed.isRefreshing()) {
                    swrNewFeed.setRefreshing(false);
                }
            }
        });
    }

    public void getData() {
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("Đang tải dữ liệu");
//        progressDialog.show();

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
                items = new ArrayList<>();
                userModel.getNewfeed(new PostModel.VolleyCallbackListStatus() {
                    @Override
                    public void onSuccess(ArrayList<EntityStatus> entityStatuses) {
//                        RealmResults<EntityStatus> items = realm.where(EntityStatus.class).findAll();
//                        Log.d("size",items.size()+"");
//                        for (int i = 0; i < items.size(); i++) {
//                            itemsAdapter.add(items.get(i));
//                        }
//                        progressDialog.dismiss();
                        itemsAdapter = entityStatuses;
                        initData();
                    }
                },"");
//            }
//        },100
// u0);

    }

    private void initData() {
        itemsAdd = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        adapterHome = new AdapterHome(getActivity(), itemsAdapter,getActivity());
        rcvNewFeed.setLayoutManager(layoutManager);
        rcvNewFeed.setAdapter(adapterHome);
        adapterHome.notifyDataSetChanged();
        endlessScrollListener =  new EndlessScrollListener((LinearLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int page, final int totalItemsCount, RecyclerView view) {
                userModel.getNewfeed(new PostModel.VolleyCallbackListStatus() {
                    @Override
                    public void onSuccess(ArrayList<EntityStatus> entityStatuses) {
                        itemsAdapter.addAll(entityStatuses);
                        adapterHome.notifyDataSetChanged();
                    }
                },"&skip="+ totalItemsCount);
            }
        };
        rcvNewFeed.setOnScrollListener(endlessScrollListener);
    }

    private void addControl(View view) {
        rcvNewFeed = (RecyclerView) view.findViewById(R.id.recycleHome);
        progressBarFeed = (ProgressBar) view.findViewById(R.id.progressBarFeed);
        swrNewFeed = (SwipeRefreshLayout) view.findViewById(R.id.swrNewFeed);
    }


}
