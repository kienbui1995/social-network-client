package com.joker.hoclazada.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
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

public class TrangChu extends Fragment {

    Button btnAdd;
    AdapterHome adapterHome;
    ArrayList<EntityStatus> items;
    RecyclerView rcvNewFeed;
    VolleyHelper volleyHelper;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trangchu, container, false);
        volleyHelper = new VolleyHelper(getActivity(),getResources().getString(R.string.url));
        addControl(view);
        getData();
        return view;
    }

    public void getData() {
        items = new ArrayList<>();
        final ProgressDialog progressDialog;
        progressDialog = ProgressDialog.show(getActivity(),"","Đang cập nhật bảng tin của bạn",true);

        volleyHelper.get("users/" + entityUserProfile.getuID() + "/statuses", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               progressDialog.dismiss();
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
                        entityStatus.setNameId("Vô danh");
                        items.add(entityStatus);
                    }
                    if (items.size()==0){
                        Toast.makeText(getActivity(), "Chưa có dữ liệu để hiện thị", Toast.LENGTH_LONG).show();
                    }
                    Log.d("result",items.size()+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        adapterHome = new AdapterHome(getActivity(), items);

        rcvNewFeed.setLayoutManager(layoutManager);
        rcvNewFeed.setAdapter(adapterHome);
        rcvNewFeed.setNestedScrollingEnabled(false);
        adapterHome.notifyDataSetChanged();
    }

    private void addControl(View view) {
        rcvNewFeed = (RecyclerView) view.findViewById(R.id.recycleHome);
    }


}
