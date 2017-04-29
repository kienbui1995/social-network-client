package com.joker.hoclazada.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.firebase.ui.database.FirebaseListAdapter;
import com.joker.hoclazada.ChatActivity;
import com.joker.hoclazada.MainActivity;
import com.joker.hoclazada.R;
import com.joker.hoclazada.Ultil.VolleyHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Entity.EntityRoomChat;
import Entity.EntityUserProfile;
import adapter.AdapterListChat;

/**
 * Created by joker on 2/9/17.
 */

public class TinNhan extends Fragment{
    private ListView lvListChat;
    private LinearLayout lnChat;
    private TextView txtLastmess;
    ArrayList<EntityRoomChat> listChat;
    AdapterListChat adapterListChat;
    DatabaseReference mReference;
    FirebaseListAdapter mAdapter;
    VolleyHelper volleyHelper;
    EntityUserProfile entityUserProfile;
    int positions;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tinnhan,container,false);
        addControll(view);


        return view;
    }

    private void addControll(View view) {
        lnChat = (LinearLayout) view.findViewById(R.id.lnChat);
        lvListChat = (ListView) view.findViewById(R.id.lvListChat);
        txtLastmess = (TextView) view.findViewById(R.id.txtLastMessage);
        listChat = new ArrayList<>();
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mReference = FirebaseDatabase.getInstance().getReference();

        Query query = mReference.child("user").child(MainActivity.entityUserProfile.getuID()).child("conversation").orderByChild("time");
        mAdapter = new FirebaseListAdapter<EntityRoomChat>(getActivity(),EntityRoomChat.class,R.layout.custom_item_list_chat,query){

            @Override
            public EntityRoomChat getItem(int position) {
                return super.getItem(getCount() - 1 - position);
            }

            @Override
            public int getCount() {
                return super.getCount();
            }

            @Override
            protected void populateView(final View v, final EntityRoomChat model, int position) {
                ((TextView)v.findViewById(R.id.txtLastMessage)).setText(model.getLastMessage());
                ((TextView)v.findViewById(R.id.txtLastMessage)).setText(model.getLastMessage());
                ((TextView)v.findViewById(R.id.txtTimeChat)).setText(convertTime(model.getTime()));
                entityUserProfile = new EntityUserProfile();
                if (model.getIdTo().equals(MainActivity.entityUserProfile.getuID()))
                {
                    volleyHelper = new VolleyHelper(getActivity(),getResources().getString(R.string.url));
                    volleyHelper.get("users/"+model.getIdFrom(), null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject infoUser = response.getJSONObject("data");
                                entityUserProfile.setFull_name(infoUser.getString("full_name"));
                                entityUserProfile.setuID(infoUser.getString("id"));
                                entityUserProfile.setUserName(infoUser.getString("username"));
                                ((TextView)v.findViewById(R.id.txtNameChat)).setText(entityUserProfile.getFull_name());

                                Log.d("getInfoUser",entityUserProfile.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("getInfoUser",VolleyHelper.checkErrorCode(error)+"");
                        }
                    });
                }else {
                    volleyHelper = new VolleyHelper(getActivity(),getResources().getString(R.string.url));
                    volleyHelper.get("users/"+model.getIdTo(), null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject infoUser = response.getJSONObject("data");
                                entityUserProfile.setFull_name(infoUser.getString("full_name"));
                                entityUserProfile.setuID(infoUser.getString("id"));
                                entityUserProfile.setUserName(infoUser.getString("username"));
                                ((TextView)v.findViewById(R.id.txtNameChat)).setText(entityUserProfile.getFull_name());
                                Log.d("getInfoUser",entityUserProfile.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("getInfoUser",VolleyHelper.checkErrorCode(error)+"");
                        }
                    });

                }
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("checkIf",model.getIdTo()+ "  "+ MainActivity.entityUserProfile.getuID());
                        if (model.getIdTo().equals(MainActivity.entityUserProfile.getuID())){
                            Intent intent = new Intent(getActivity(), ChatActivity.class);
                            intent.putExtra("uId",model.getIdFrom());
                            Log.d("idRoom",model.getIdFrom()+ "  "+ entityUserProfile.getuID());
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(getActivity(), ChatActivity.class);
                            intent.putExtra("uId",model.getIdTo());
                            Log.d("idRoom",model.getIdTo());
                            startActivity(intent);
                        }

                    }
                });
                Log.d("postion",positions+"");

            }


        };

//        adapterListChat = new AdapterListChat(getActivity(),R.layout.custom_item_list_chat,listChat);
        lvListChat.setAdapter(mAdapter);
//        lvListChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                startActivity(new Intent(getActivity(), ChatActivity.class));
//            }
//        });
    }
    static String convertTime(Long unixtime) {
        Date dateObject = new Date(unixtime);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM hh:mmaa");
        return dateFormatter.format(dateObject);
    }
}
