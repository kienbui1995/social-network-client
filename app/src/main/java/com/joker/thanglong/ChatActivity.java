package com.joker.thanglong;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.joker.thanglong.Model.ChatItem;
import com.joker.thanglong.Ultil.ProfileInstance;

import de.hdodenhof.circleimageview.CircleImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import Entity.EntityConversation;
import Entity.EntityUserProfile;
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;
import io.realm.Realm;

public class ChatActivity extends AppCompatActivity {
    public static final int ME = 1;
    public static final int FRIENDS = 2;
    private EmojIconActions emojIcon;
    private ImageView emojiButton;
    private LinearLayout activityChat;
    private Toolbar toolbarNotification;
    private RecyclerView rcvMessage;
    private EmojiconEditText edtNewMessage;
    private ImageView submitButton;
    private DatabaseReference mdatabase;
    private FirebaseRecyclerAdapter<ChatItem,RecyclerView.ViewHolder> mdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<String> chatItems;
    public ChatItem chatItem;
    int myId;
    int uId;
    String idRoom;
    Realm realm;
    EntityUserProfile userProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mdatabase = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        uId = intent.getIntExtra("uId",1) ;
        realm = Realm.getDefaultInstance();
        userProfile = realm.where(EntityUserProfile.class).equalTo("uID",uId).findFirst();
        myId = ProfileInstance.getProfileInstance(this).getProfile().getuID();
        mdatabase.child("conversation").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(uId+"_"+MainActivity.entityUserProfile.getuID()).exists() == true
                        && dataSnapshot.child(MainActivity.entityUserProfile.getuID()+"_"+uId).exists() == false)
                {
                    idRoom = uId+"_"+MainActivity.entityUserProfile.getuID();
                    setupChat();
                    Log.d("idRoom",idRoom.toString());
                }else if (dataSnapshot.child(uId+"_"+MainActivity.entityUserProfile.getuID()).exists() == false
                        && dataSnapshot.child(MainActivity.entityUserProfile.getuID()+"_"+uId).exists() ==false){
                    idRoom = uId+"_"+MainActivity.entityUserProfile.getuID();
                    setupChat();
                    Log.d("idRoom",idRoom.toString());
                }else {
                    idRoom = MainActivity.entityUserProfile.getuID()+"_"+uId;
                    setupChat();
                    Log.d("idRoom",idRoom.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        addControll();
        setupEmoji();
        setupTabs();

//        addEvent();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitChat();
            }
        });
    }

    private void setupEmoji() {
        emojIcon = new EmojIconActions(this,activityChat,edtNewMessage,emojiButton,"#495C66","#DCE1E2","#E6EBEF");
        emojIcon.ShowEmojIcon();
        emojIcon.addEmojiconEditTextList(edtNewMessage);
    }

    private void setupChat() {
        chatItem = new ChatItem();
        linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(true);
        chatItems = new ArrayList<>();
        Log.d("idRoom1",idRoom.toString());
        final Query query = mdatabase.child("conversation/"+idRoom);
        mdapter = new FirebaseRecyclerAdapter<ChatItem,RecyclerView.ViewHolder>(ChatItem.class, R.layout.item_chat_me,RecyclerView.ViewHolder.class,query) {
            @Override
            public int getItemViewType(int position) {
                ChatItem chatItem = getItem(position);
                if (chatItem.getId() == myId){
                    return ME;
                }else {
                    return FRIENDS;
                }
            }

            @Override
            protected void populateViewHolder(RecyclerView.ViewHolder viewHolder, ChatItem model, int position) {
                if (model.getId() == myId){
                    ViewHolderMe viewHolderMe = (ViewHolderMe)viewHolder;
                    viewHolderMe.txtMessageContent.setText(model.getContent());
                    viewHolderMe.txtDate.setText(convertTime((Long) model.getTimeStamp()));

                }else {
                    ViewHolderFriend viewHolderFriend = (ViewHolderFriend) viewHolder;
                    viewHolderFriend.txtMessageContent.setText(model.getContent());
                    viewHolderFriend.txtDate.setText(convertTime((Long) model.getTimeStamp()));
                    Glide.with(getApplicationContext()).load(userProfile.getAvatar())
                            .crossFade().centerCrop().into(viewHolderFriend.imgAvartarChat);
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                switch (viewType){
                    case ME:
                        View userType1 = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_chat_me, parent, false);
                        return new ViewHolderMe(userType1);
                    case FRIENDS:
                        View userType2 = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_chat_friend, parent, false);
                        return new ViewHolderFriend(userType2);
                }
                return super.onCreateViewHolder(parent, viewType);
            }
        };

        mdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
//                Toast.makeText(ChatActivity.this, "Đã thay đổi nội dung", Toast.LENGTH_SHORT).show();
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

//    private void addEvent() {
//        submitChat();
//    }

    private void submitChat() {

        String content = edtNewMessage.getText().toString().trim();
        if (TextUtils.isEmpty(content))
        {
//            edtNewMessage.setError("Mời bạn điền nội dung chat");
            return;
        }

        ChatItem chatItem = new ChatItem(myId, content);
        Map<String, Object> chatValues = chatItem.toMap();
        HashMap<String, Object> childUpdate = new HashMap<>();
        EntityConversation entityConversation = new EntityConversation(idRoom,myId,uId,content, 0);
        EntityConversation entityConversation1 = new EntityConversation(idRoom,myId,uId,content,0);
        Map<String,Object> conversation =entityConversation.toMap();
        Map<String,Object> conversation1 =entityConversation1.toMap();
        mdatabase.child("user").child(uId+"").child("conversation").child(idRoom).setValue(conversation1);
        mdatabase.child("user").child(myId+"").child("conversation").child(idRoom).setValue(conversation);
        String key = mdatabase.child("conversation").child(idRoom).push().getKey();
        childUpdate.put("conversation/"+idRoom+"/" + key,chatValues);
        mdatabase.updateChildren(childUpdate);
        edtNewMessage.setText("");
    }

    private void addControll() {
        emojiButton = (ImageView) findViewById(R.id.emoji_btn);
        activityChat = (LinearLayout) findViewById(R.id.activity_chat);
        toolbarNotification = (Toolbar) findViewById(R.id.toolbarNotification);
        rcvMessage = (RecyclerView) findViewById(R.id.rcvMessages);
        edtNewMessage = (EmojiconEditText) findViewById(R.id.edtNewMessage);
        submitButton = (ImageView) findViewById(R.id.submit_button);

    }
    private void setupTabs() {
        toolbarNotification.setTitle(userProfile.getFull_name());
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


    private class ViewHolderMe extends RecyclerView.ViewHolder {
        private TextView txtMessageContent;
        private TextView txtDate;
        private CircleImageView imgAvartarChat;

        public ViewHolderMe(View itemView) {
            super(itemView);
            txtMessageContent = (TextView) itemView.findViewById(R.id.txtMessageContent);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            imgAvartarChat = (CircleImageView) itemView.findViewById(R.id.imgAvartarChat);
        }
    }

    private class ViewHolderFriend extends RecyclerView.ViewHolder {
        private CircleImageView imgAvartarChat;
        private EmojiconTextView txtMessageContent;
        private TextView txtDate;

        public ViewHolderFriend(View itemView) {
            super(itemView);
            imgAvartarChat = (CircleImageView) itemView.findViewById(R.id.imgAvartarChat);
            txtMessageContent = (EmojiconTextView) itemView.findViewById(R.id.txtMessageContent);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);

        }
    }
}
