package com.joker.thanglong.Fragment;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
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
import com.joker.thanglong.CommentPostFullActivity;
import com.joker.thanglong.MainActivity;
import com.joker.thanglong.Model.UserModel;
import com.joker.thanglong.R;
import com.joker.thanglong.Ultil.ProfileInstance;
import com.joker.thanglong.Ultil.VolleyHelper;

import de.hdodenhof.circleimageview.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Entity.EntityRoomChat;
import Entity.EntityUserProfile;
import adapter.AdapterListChat;
import io.github.asvid.notti.Notti;
import io.github.asvid.notti.config.NottiConf;

import static android.content.Context.NOTIFICATION_SERVICE;

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
        Query query = mReference.child("user").child(ProfileInstance.getProfileInstance(getActivity()).getProfile().getuID()+"").child("conversation");
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
                final CircleImageView avatar = (CircleImageView) v.findViewById(R.id.imgAvatar);
                if (model.getIdTo() == MainActivity.entityUserProfile.getuID())
                {
                    UserModel.realmUser(getActivity(), model.getIdFrom(), new UserModel.VolleyCallBackProfileUser() {
                        @Override
                        public void onSuccess(EntityUserProfile profile) {
                            ((TextView)v.findViewById(R.id.txtNameChat)).setText(profile.getFull_name());
                            Glide.with(getActivity()).load(profile.getAvatar()).crossFade().centerCrop().into(avatar);
//                            showNotification(getActivity(),model.getIdFrom(),model.getLastMessage());
                        }
                    });
                }else {
                    UserModel.realmUser(getActivity(), model.getIdTo(), new UserModel.VolleyCallBackProfileUser() {
                        @Override
                        public void onSuccess(EntityUserProfile profile) {
                            ((TextView)v.findViewById(R.id.txtNameChat)).setText(profile.getFull_name());
                            Glide.with(getActivity()).load(profile.getAvatar()).crossFade().centerCrop().into(avatar);
                        }
                    });
//                    showNotification(getActivity(),model.getIdTo(),model.getLastMessage());
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
            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    EntityRoomChat roomChat = dataSnapshot.getValue(EntityRoomChat.class);
                    if (roomChat.getIdFrom()!= MainActivity.entityUserProfile.getuID()){
                        showNotification(getActivity(),11,dataSnapshot.child("lastMessage").getValue().toString());
                    }else if (roomChat.getIdTo() != MainActivity.entityUserProfile.getuID())
                    {
                        showNotification(getActivity(),11,dataSnapshot.child("lastMessage").getValue().toString());
                    }
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
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
    public void showNotification(Context context,int id,String messgage){

        Intent intent = new Intent(context, CommentPostFullActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        /*build the notification here this is only supported for API 11. Since we've targeted API 11 there will be no problem on this*/
        NotificationCompat.Builder notify = new NotificationCompat.Builder(context)
                .setContentTitle("Thang long")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.heart)
                .setVibrate(new long[]{500,500,500})
//                .setOngoing(true)
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(messgage))
                .setContentText(messgage)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pIntent);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT<16){
            /*build notification for HoneyComb to ICS*/
            notificationManager.notify(id, notify.getNotification());
        }if(Build.VERSION.SDK_INT>15){
            /*Notification for Jellybean and above*/
            notificationManager.notify(id, notify.build());
        }
    }
}
