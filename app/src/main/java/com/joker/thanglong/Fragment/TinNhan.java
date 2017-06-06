package com.joker.thanglong.Fragment;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.joker.thanglong.ChatActivity;
import com.joker.thanglong.MainActivity;
import com.joker.thanglong.Model.UserModel;
import com.joker.thanglong.R;
import com.joker.thanglong.Ultil.VolleyHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Entity.EntityRoomChat;
import Entity.EntityUserProfile;
import adapter.AdapterListChat;
import io.github.asvid.notti.Notti;
import io.github.asvid.notti.config.NottiConf;

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
    private TextView txtOnline;
    EntityUserProfile entityUserProfile;
//    private  notti;
    private Notti notti;
    int positions;
    int check = -1;
    private ImageView imgAvatar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tinnhan,container,false);
        addControll(view);
        notti = new Notti(getActivity(),new NottiConf(R.drawable.heart));
        addEvent();
        return view;
    }

    private void addEvent() {
        mReference = FirebaseDatabase.getInstance().getReference();
        Query query = mReference.child("user").child(MainActivity.entityUserProfile.getuID()+"").child("conversation");
        mAdapter = new FirebaseListAdapter<EntityRoomChat>(getActivity(),EntityRoomChat.class,R.layout.custom_item_list_chat,query){

//            @Override
//            public EntityRoomChat getItem(int position) {
//                return super.getItem(getCount() - 1 - position);
//            }

            @Override
            public int getCount() {
                return super.getCount();
            }

            @Override
            protected void populateView(final View v, final EntityRoomChat model, int position) {
                ((TextView)v.findViewById(R.id.txtLastMessage)).setText(model.getLastMessage());
                ((TextView)v.findViewById(R.id.txtTimeChat)).setText(convertTime(model.getTime()));
                entityUserProfile = new EntityUserProfile();
                if (model.getIdTo() == MainActivity.entityUserProfile.getuID())
                {
                    UserModel.realmUser(getActivity(), model.getIdFrom(), new UserModel.VolleyCallBackProfileUser() {
                        @Override
                        public void onSuccess(EntityUserProfile profile) {
                            ((TextView)v.findViewById(R.id.txtNameChat)).setText(profile.getFull_name());
                            Glide.with(getActivity()).load(profile.getAvatar()).centerCrop().
                                    crossFade().into(((ImageView) v.findViewById(R.id.imgAvatar)));
                        }
                    });


//                    UserModel userModel = new UserModel(getActivity(),model.getIdFrom());
//                    userModel.getProfile(new PostModel.VolleyCallBackJson() {
//                        @Override
//                        public void onSuccess(JSONObject jsonObject) throws JSONException {
//                            try {
//                                JSONObject infoUser = jsonObject;
//                                entityUserProfile.setFull_name(infoUser.getString("full_name"));
//                                entityUserProfile.setuID(infoUser.getString("id"));
//                                entityUserProfile.setUserName(infoUser.getString("username"));
//                                ((TextView)v.findViewById(R.id.txtNameChat)).setText(entityUserProfile.getFull_name());
//                                Handler handler = new Handler();
//                                handler.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
////                                        ;
//                                    }
//                                },2000);
//                                Log.d("getInfoUser",entityUserProfile.toString());
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
                }else {
                    UserModel.realmUser(getActivity(), model.getIdTo(), new UserModel.VolleyCallBackProfileUser() {
                        @Override
                        public void onSuccess(EntityUserProfile profile) {
                            ((TextView)v.findViewById(R.id.txtNameChat)).setText(profile.getFull_name());
                        }
                    });

                }
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("checkIf",model.getIdTo()+ "  "+ MainActivity.entityUserProfile.getuID());
                        if (model.getIdTo() == MainActivity.entityUserProfile.getuID()){
//                            mReference.child("user").child(ProfileInstance.getProfileInstance(getActivity()).getProfile().getuID())
//                                    .child("conversation")
//                                    .child(model.getName()).child("timeRead").setValue(1);
                            Intent intent = new Intent(getActivity(), ChatActivity.class);
                            intent.putExtra("uId",model.getIdFrom());
                            Log.d("idRoom",model.getIdFrom()+ "  "+ entityUserProfile.getuID());
                            startActivity(intent);
                        }else {
//                            mReference.child("user").child(ProfileInstance.getProfileInstance(getActivity()).getProfile().getuID())
//                                    .child("conversation")
//                                    .child(model.getName()).child("timeRead").setValue(1);
                            Intent intent = new Intent(getActivity(), ChatActivity.class);
                            intent.putExtra("uId",model.getIdTo());
                            Log.d("idRoom",model.getIdTo()+"");
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

    private void addControll(View view) {
        txtOnline = (TextView) view.findViewById(R.id.txtOnline);
        lnChat = (LinearLayout) view.findViewById(R.id.lnChat);
        lvListChat = (ListView) view.findViewById(R.id.lvListChat);
        txtLastmess = (TextView) view.findViewById(R.id.txtLastMessage);
        imgAvatar = (ImageView) view.findViewById(R.id.imgAvatar);

        listChat = new ArrayList<>();
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
    static String convertTime(Long unixtime) {
        Date dateObject = new Date(unixtime);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM hh:mmaa");
        return dateFormatter.format(dateObject);
    }
}
