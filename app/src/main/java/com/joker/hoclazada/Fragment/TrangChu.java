package com.joker.hoclazada.Fragment;

import com.google.firebase.iid.FirebaseInstanceId;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.joker.hoclazada.Interface.EndlessScrollListener;
import com.joker.hoclazada.R;
import com.joker.hoclazada.Ultil.VolleyHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Entity.EntityStatus;
import adapter.AdapterHome;

import static com.joker.hoclazada.MainActivity.entityUserProfile;

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
    VolleyHelper volleyHelper;
    RecyclerView.LayoutManager layoutManager;
    private EndlessScrollListener endlessScrollListener;
    int itemFirst = 0;
    int totalItem = 0;
    int itemLoadMore = 4;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trangchu, container, false);
        volleyHelper = new VolleyHelper(getActivity(),getResources().getString(R.string.url));
        itemsAdapter = new ArrayList<>();
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("tokenI",token.toString());
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
                items.clear();
                itemsAdapter.clear();
                getData();
                if (swrNewFeed.isRefreshing()) {
                    swrNewFeed.setRefreshing(false);
                }
            }
        });
    }

    public void getData() {
        items = new ArrayList<>();
        volleyHelper.get("users/" + entityUserProfile.getuID() + "/home"+"?limit=5", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i =0; i< jsonArray.length();i++)
                    {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        EntityStatus entityStatus = new EntityStatus();
                        entityStatus.setuId(Integer.parseInt(jsonObject.getString("userid")));
                        entityStatus.setContent(jsonObject.getString("message"));
                        entityStatus.setCreatedTime(Long.parseLong(jsonObject.getString("created_at")));
                        entityStatus.setIdStatus(Integer.parseInt(jsonObject.getString("id")));
                        entityStatus.setNameId(jsonObject.getString("full_name"));
                        entityStatus.setLike(Boolean.parseBoolean(jsonObject.getString("is_liked")));
                        entityStatus.setStatus(jsonObject.getInt("status"));
                        if (jsonObject.has("photo")){
                            entityStatus.setImage(jsonObject.getString("photo"));
                            Log.d("image",entityStatus.getImage());
                        }
                        if (!jsonObject.isNull("comments"))
                        {
                            entityStatus.setNumberComment(jsonObject.getInt("comments"));
                        }
                        if (!jsonObject.isNull("likes"))
                        {
                            entityStatus.setNumberLike(jsonObject.getInt("likes"));
                        }
                        items.add(entityStatus);
                    }
                    if (items.size()==0){
                        Toast.makeText(getActivity(), "Chưa có dữ liệu để hiện thị", Toast.LENGTH_LONG).show();
                    }
                    Log.d("result",items.size()+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                itemsAdapter=items;
                initData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("getDataNewFeed",VolleyHelper.checkErrorCode(error)+"");
            }
        });
    }

    private void initData() {
        itemsAdd = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        adapterHome = new AdapterHome(getActivity(), itemsAdapter,getActivity(),volleyHelper);
        rcvNewFeed.setLayoutManager(layoutManager);
        rcvNewFeed.setAdapter(adapterHome);
        endlessScrollListener =  new EndlessScrollListener((LinearLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int page, final int totalItemsCount, RecyclerView view) {
                Log.d("LoadMore",page+" " + totalItemsCount + " ");
                {
                    volleyHelper.get("users/" + entityUserProfile.getuID() + "/home"+"?limit=5&skip="+
                            totalItemsCount, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("data");
                                if (jsonArray == null){
                                    System.exit(0);
                                }
                                Log.d("dataJsonLoadMore",jsonArray.get(0).toString());
                                for (int i =0; i< jsonArray.length();i++)
                                {
                                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                    EntityStatus entityStatus = new EntityStatus();
                                    entityStatus.setuId(Integer.parseInt(jsonObject.getString("userid")));
                                    entityStatus.setContent(jsonObject.getString("message"));
                                    entityStatus.setCreatedTime(Long.parseLong(jsonObject.getString("created_at")));
                                    entityStatus.setIdStatus(Integer.parseInt(jsonObject.getString("id")));
                                    entityStatus.setNameId(jsonObject.getString("full_name"));
                                    entityStatus.setLike(Boolean.parseBoolean(jsonObject.getString("is_liked")));
                                    entityStatus.setStatus(jsonObject.getInt("status"));
                                    if (jsonObject.has("photo")){
                                        entityStatus.setImage(jsonObject.getString("photo"));
                                    }
                                    if (!jsonObject.isNull("comments"))
                                    {
                                        entityStatus.setNumberComment(jsonObject.getInt("comments"));
                                    }
                                    if (!jsonObject.isNull("likes"))
                                    {
                                        entityStatus.setNumberLike(jsonObject.getInt("likes"));
                                    }
                                    itemsAdapter.add(entityStatus);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            adapterHome.notifyDataSetChanged();
                            Log.d("itemAdapter",itemsAdapter.size()+"");
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("getDataNewFeed",VolleyHelper.checkErrorCode(error)+"");
                        }
                    });
                }
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
