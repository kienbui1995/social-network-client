package com.joker.thanglong.Fragment.Group;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joker.thanglong.GroupActivity;
import com.joker.thanglong.GroupChatActivity;
import com.joker.thanglong.Interface.EndlessScrollListener;
import com.joker.thanglong.Model.PostModel;
import com.joker.thanglong.Model.UserModel;
import com.joker.thanglong.PostActivity;
import com.joker.thanglong.R;
import com.joker.thanglong.Ultil.SystemHelper;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;

import Entity.EntityConversation;
import Entity.EntityStatus;
import Entity.EntityUserProfile;
import adapter.AdapterHome;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeGroupFragment extends Fragment {
    private LinearLayout lnPost;
    private Button btnPost;
    private RecyclerView rcvListPostGroup;
    private AdapterHome adapterHome;
    private ArrayList<EntityStatus> items;
    ArrayList<EntityStatus> itemsAdapter;
    private EndlessScrollListener endlessScrollListener;
    private PostModel postModel;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swpHomeGroup;
    private LinearLayout lnChat;
    private CircleImageView imgAvatar;
    private TextView txtNameChat;
    private TextView txtTimeChat;
    private TextView txtLastMessage;
    private DatabaseReference mReference;
    private EntityConversation entityConversation;
    private TextView txtNhanTin;


    public HomeGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_group, container, false);
        addView(view);
        initEvent();
        addEvent();
        addChat();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        },1000);
        return view;
    }

    private void addChat() {
        mReference = FirebaseDatabase.getInstance().getReference();
        Query query = mReference.child("group").child("conversation").child(GroupActivity.groupInfo.getId()+"");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                entityConversation = dataSnapshot.getValue(EntityConversation.class);
                if (entityConversation !=null){
                    UserModel.realmUser(getActivity(), entityConversation.getIdFrom(), new UserModel.VolleyCallBackProfileUser() {
                        @Override
                        public void onSuccess(EntityUserProfile profile) {
                            txtNameChat.setText(profile.getFull_name());
                            txtLastMessage.setText(entityConversation.getLastMessage());
                            txtTimeChat.setText(SystemHelper.convertTime((Long) entityConversation.getTime()));
                            Glide.with(getActivity()).load(profile.getAvatar()).crossFade().into(imgAvatar);
                            lnChat.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    getActivity().startActivity(new Intent(getActivity(), GroupChatActivity.class));
                                }
                            });
                        }
                    });

                }else {
                    txtNhanTin.setVisibility(View.VISIBLE);
                    txtNhanTin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getActivity().startActivity(new Intent(getActivity(), GroupChatActivity.class));
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addEvent() {
        swpHomeGroup.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                if (swpHomeGroup.isRefreshing()) {
                    swpHomeGroup.setRefreshing(false);
                }
            }
        });
    }

    private void initData() {
        postModel = new PostModel(getActivity(), GroupActivity.groupInfo.getId(),"");
        items = new ArrayList<>();
        postModel.getPostGroup(new PostModel.VolleyCallbackListStatus() {
            @Override
            public void onSuccess(ArrayList<EntityStatus> entityStatuses) {
                itemsAdapter=entityStatuses;
                loadMore();
            }
        },"");
    }

    private void loadMore() {
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        adapterHome = new AdapterHome(getActivity(), itemsAdapter,getActivity());
        rcvListPostGroup.setLayoutManager(layoutManager);
        rcvListPostGroup.setAdapter(adapterHome);
        rcvListPostGroup.setNestedScrollingEnabled(false);
        adapterHome.notifyDataSetChanged();
        endlessScrollListener =  new EndlessScrollListener((LinearLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int page, final int totalItemsCount, RecyclerView view) {
                postModel.getPostGroup(new PostModel.VolleyCallbackListStatus() {
                    @Override
                    public void onSuccess(ArrayList<EntityStatus> entityStatuses) {
                        itemsAdapter.addAll(entityStatuses);
                        adapterHome.notifyDataSetChanged();
                    }
                },"&skip="+ totalItemsCount);
            }
        };
        rcvListPostGroup.setOnScrollListener(endlessScrollListener);
    }

    private void initEvent() {
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PostActivity.class);
                intent.putExtra("postAt",2);
                startActivity(intent);
            }
        });
    }

    private void addView(View view) {
        lnPost = (LinearLayout) view.findViewById(R.id.lnPost);
        btnPost = (Button) view.findViewById(R.id.btnPost);
        rcvListPostGroup = (RecyclerView) view.findViewById(R.id.rcvListPostGroup);
        swpHomeGroup = (SwipeRefreshLayout) view.findViewById(R.id.swpHomeGroup);
        lnChat = (LinearLayout) view.findViewById(R.id.lnChat);
        imgAvatar = (CircleImageView) view.findViewById(R.id.imgAvatar);
        txtNameChat = (TextView) view.findViewById(R.id.txtNameChat);
        txtTimeChat = (TextView) view.findViewById(R.id.txtTimeChat);
        txtLastMessage = (TextView) view.findViewById(R.id.txtLastMessage);
        txtNhanTin = (TextView) view.findViewById(R.id.txtNhanTin);
    }

}
