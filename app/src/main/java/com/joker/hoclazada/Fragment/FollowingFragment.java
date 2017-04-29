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
import adapter.AdapterFollowingList;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowingFragment extends Fragment {
    private ListView lvFollowingList;
    ArrayList<EntityFollow> items;
    AdapterFollowingList adapterFollowingList;
    VolleyHelper volleyHelper;

    public FollowingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following, container, false);
        lvFollowingList = (ListView) view.findViewById(R.id.lvFollowingList);
        getFollowingList();
        return view;
    }

    private void getFollowingList() {
        Realm realm;
        realm = Realm.getDefaultInstance();
        realm.init(getActivity());
        EntityUserProfile profile = realm.where(EntityUserProfile.class).findFirst();
        items = new ArrayList<>();
        volleyHelper = new VolleyHelper(getActivity(),getResources().getString(R.string.url));
        volleyHelper.get("users/"+ profile.getuID()+ "/subscribers", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    Log.d("getFollowing",jsonArray.get(0).toString());
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
                        adapterFollowingList.notifyDataSetChanged();
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
        adapterFollowingList = new AdapterFollowingList(getActivity(),R.layout.custom_follow_list,items);
        lvFollowingList.setAdapter(adapterFollowingList);
    }

}
