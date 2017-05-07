package com.joker.hoclazada.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.joker.hoclazada.R;
import com.joker.hoclazada.Ultil.VolleyHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Entity.EntityFollow;
import Entity.EntityUserProfile;
import adapter.AdapterFollowerList;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowerFragment extends Fragment {
    private ListView lvFollowerList;
    ArrayList<EntityFollow> items;
    AdapterFollowerList adapterFollowerList;
    VolleyHelper volleyHelper;

    public FollowerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follower, container, false);
        lvFollowerList = (ListView) view.findViewById(R.id.lvFollowerList);
        getFollowerList();
        return view;
    }

    private void getFollowerList() {
        Realm realm;
        realm = Realm.getDefaultInstance();
        realm.init(getActivity());
        EntityUserProfile profile = realm.where(EntityUserProfile.class).findFirst();
        items = new ArrayList<>();
        volleyHelper = new VolleyHelper(getActivity(),getResources().getString(R.string.url));
        volleyHelper.get("users/"+ profile.getuID()+ "/followers", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    Log.d("getFollower",jsonArray.get(0).toString());
                    for (int i =0; i< jsonArray.length();i++)
                    {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        EntityFollow item = new EntityFollow();
                        item.setId(jsonObject.getString("id"));
                        item.setUsername(jsonObject.getString("username"));
                        item.setAvatar(jsonObject.getString("avatar"));
                        item.setFull_name(jsonObject.getString("full_name"));
                        item.setIs_followed(jsonObject.getBoolean("is_followed"));
                        items.add(item);
                        adapterFollowerList.notifyDataSetChanged();
                    }
                    Log.d("result",items.size()+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("findUser", VolleyHelper.checkErrorCode(error)+"");
            }
        });
        adapterFollowerList = new AdapterFollowerList(getActivity(),R.layout.custom_follow_list,items);
        lvFollowerList.setAdapter(adapterFollowerList);
    }

}

