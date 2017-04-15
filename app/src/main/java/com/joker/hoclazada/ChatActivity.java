package com.joker.hoclazada;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.joker.hoclazada.Model.ChatItem;
import com.joker.hoclazada.Presenter.TrangChu.XuLyDuLieu.PresenterXuLyDuLieu;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import adapter.ViewHolderChat;

public class ChatActivity extends AppCompatActivity {


    private LinearLayout activityChat;
    private Toolbar toolbarNotification;
    private RecyclerView rcvMessage;
    private EditText edtNewMessage;
    private ImageView submitButton;
    private DatabaseReference mdatabase;
    private FirebaseRecyclerAdapter<ChatItem,ViewHolderChat> mdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<String> chatItems;
    public ChatItem chatItem;
    String evalue;
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotifyMgr;
    int mNotificationId = 001;
    String strContent;
    String myId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_chat);
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mdatabase = FirebaseDatabase.getInstance().getReference();
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.coin_2);
        addControll();
        setupTabs();
        addEvent();
        setupChat();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitChat();
            }
        });
    }

    private void setupChat() {
        chatItem = new ChatItem();
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        myId=prefs.getString("fb_id",null);
        linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(true);
        chatItems = new ArrayList<>();
        final Query query = mdatabase.child("chat/433174420346960KienBui1");
        mdapter = new FirebaseRecyclerAdapter<ChatItem,ViewHolderChat>(ChatItem.class,R.layout.item_chat_friend,ViewHolderChat.class,query) {
            @Override
            protected void populateViewHolder(ViewHolderChat viewHolder, ChatItem model, int position) {
                Log.d("itemss",chatItems.size()+"");
//                viewHolder.setIsRecyclable(false);
                if (model.getUserName().equals(myId))
                {
                    viewHolder.txtDate.setText(convertTime(Long.valueOf(model.getTimeStamp().toString())));
                    viewHolder.txtMessageContent.setText(model.getContent());
                    viewHolder.txtMessageContent.setBackgroundResource(R.drawable.bg_item_chat_round_me);
                    viewHolder.txtMessageContent.setPadding(40,20,20,40);
                    Picasso.with(getApplicationContext())
                            .load(prefs.getString("fb_profileURL",null))
                            .into(viewHolder.imgAvartarChat);
                }
                else {
                    chatItem=model;
                    viewHolder.txtDate.setText(convertTime(Long.valueOf(model.getTimeStamp().toString())));
                    viewHolder.txtMessageContent.setText(model.getContent());
//                    viewHolder.txtMessageContent.setBackgroundResource(R.drawable.bg_item_chat_round_me);
                    Picasso.with(getApplicationContext())
                            .load("https://graph.facebook.com/" + model.getUserName() + "/picture?type=small")
                            .into(viewHolder.imgAvartarChat);
                }
//                Log.d("ModelArray",chatItem.getUserName()+" "+ prefs.getString("fb_id", null));
            }


//            @Override
//            public int getItemViewType(int position) {
//                    Log.d("sizeArray",chatItem.toString()+" "+ prefs.getString("fb_id", null));
////                String id = chatItems.get(position).getUserName();
//                if (chatItem.getUserName() == null)
//                {
//                    return R.layout.item_chat_friend;
//                }else {
//                    if (Long.valueOf(prefs.getString("fb_id", null)) == Long.valueOf(prefs.getString("fb_id", null))) {
//                        return R.layout.item_chat_me;
//                    } else
//                        return item_chat_friend;
//                }
//            }

        };
        mdatabase.child("chat/433174420346960KienBui1").limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if ((dataSnapshot.child("userName").getValue().toString()).equals(prefs.getString("fb_id",null)))
                {
                    return;
                }
                strContent = dataSnapshot.child("content").getValue().toString();
                mBuilder =
                        (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.ic_send_light)
                                .setContentTitle(dataSnapshot.child("userName").getValue().toString())
                                .setContentText(strContent);
                long[] pattern = {500,500,500};
                mBuilder.setVibrate(pattern);
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                mBuilder.setSound(alarmSound);
                mNotifyMgr =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                mNotifyMgr.notify(mNotificationId, mBuilder.build());
                Intent resultIntent = new Intent(getApplicationContext(), ChatItem.class);
//                resultIntent.putExtra("content", strContent);

                PendingIntent resultPendingIntent =
                        PendingIntent.getActivity(
                                getApplicationContext(),
                                0,
                                resultIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                // Set content intent;
                mBuilder.setContentIntent(resultPendingIntent);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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

        mdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                Toast.makeText(ChatActivity.this, "Đã thay đổi nội dung", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = mdapter.getItemCount();
                int lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    rcvMessage.scrollToPosition(positionStart);
                }


            }
        });
        rcvMessage.setLayoutManager(linearLayoutManager);
        rcvMessage.setNestedScrollingEnabled(true);
        rcvMessage.setAdapter(mdapter);
    }

    private void addEvent() {
        submitChat();
    }

    private void submitChat() {
        String content = edtNewMessage.getText().toString().trim();
        if (TextUtils.isEmpty(content))
        {
//            edtNewMessage.setError("Mời bạn điền nội dung chat");
            return;
        }

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        PresenterXuLyDuLieu presenterXuLyDuLieu = new PresenterXuLyDuLieu(this);
        AccessToken accessToken = presenterXuLyDuLieu.LayTenNguoiDungFacebook();
        final String uId = accessToken.getUserId();
        HashMap<String,Object> timeFirebase = new HashMap<>();
        timeFirebase.put("date", ServerValue.TIMESTAMP);
        Log.d("aaaa",timeFirebase.get("date").toString());
        Long tsLong = System.currentTimeMillis()/1000;
        final String timeStamp1 = tsLong.toString();
        ChatItem chatItem = new ChatItem(uId, content);
        Map<String, Object> chatValues = chatItem.toMap();
        HashMap<String, Object> childUpdate = new HashMap<>();
        String key = mdatabase.child("chat").child("433174420346960KienBui1").push().getKey();
        childUpdate.put("/chat/" + "433174420346960"+ "KienBui1/" + key , chatValues);
        mdatabase.updateChildren(childUpdate);
        edtNewMessage.setText(prefs.getString("profile_pic",null));
    }

    private void addControll() {
        activityChat = (LinearLayout) findViewById(R.id.activity_chat);
        toolbarNotification = (Toolbar) findViewById(R.id.toolbarNotification);
        rcvMessage = (RecyclerView) findViewById(R.id.rcvMessages);
        edtNewMessage = (EditText) findViewById(R.id.edtNewMessage);
        submitButton = (ImageView) findViewById(R.id.submit_button);

    }
    private void setupTabs() {
        toolbarNotification.setTitle("Hoài Nam");
        setSupportActionBar(toolbarNotification);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbarNotification.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbarNotification.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private String getDate(long timeStamp){
        String timezone = "GMT+7";
        DateFormat objFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mmaa");
        objFormatter.setTimeZone(TimeZone.getTimeZone(timezone));

        Calendar objCalendar =
                Calendar.getInstance(TimeZone.getTimeZone(timezone));
        objCalendar.setTimeInMillis(timeStamp*1000);//edit
        String result = objFormatter.format(objCalendar.getTime());
        objCalendar.clear();
        return result;
    }

    static String convertTime(Long unixtime) {
        Date dateObject = new Date(unixtime);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yy hh:mmaa");
        return dateFormatter.format(dateObject);
    }

}
