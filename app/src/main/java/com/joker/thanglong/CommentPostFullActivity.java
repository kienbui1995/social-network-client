package com.joker.thanglong;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.joker.thanglong.Mention.adapter.RecyclerItemClickListener;
import com.joker.thanglong.Mention.adapter.UsersAdapter;
import com.joker.thanglong.Mention.models.Mention;
import com.joker.thanglong.Mention.models.User;
import com.joker.thanglong.Mention.utils.MentionsLoaderUtils;
import com.joker.thanglong.Model.PostModel;
import com.joker.thanglong.Ultil.ProfileInstance;
import com.joker.thanglong.Ultil.SystemHelper;
import com.joker.thanglong.Ultil.VolleySingleton;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.percolate.caffeine.ViewUtils;
import com.percolate.mentions.Mentions;
import com.percolate.mentions.QueryListener;
import com.percolate.mentions.SuggestionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Entity.EntityComment;
import Entity.EntityStatus;
import Entity.EntityUserProfile;
import adapter.AdapterComment;

public class CommentPostFullActivity extends AppCompatActivity implements QueryListener, SuggestionsListener {
    private TextView txtFullName;
    private TextView txtTimePostStatus;
    private TextView txtContentStatus;
    private ImageView imgPostContent;
    private LikeButton btnLove;
    private TextView txtNumberLike;
    private TextView txtNumberComment;
    private Button btnComment;
    private EditText btnCommentInput;
    private ImageButton btnSubmmitComment;
    private Toolbar toolbar;
    private NestedScrollView nsvPost;

    AdapterComment adapterComment;
    ArrayList<EntityComment> itemsComment;
    RecyclerView rcvComment;
    RecyclerView.LayoutManager layoutManager;
    int idPost;
    PostModel postModel;
    EntityStatus entityStatus;
    EntityComment entityComment;
    EntityUserProfile entityUserProfile;
    Mentions mentions;
    List<User> userList;
    private UsersAdapter usersAdapter;
    private MentionsLoaderUtils mentionsLoaderUtils;
    private RecyclerView mentionsList;
    Handler handler;
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_post_full);
        addControl();
//        initMention();
        setupToolbar();
        Intent intent = getIntent();
        idPost = intent.getIntExtra("idPost",1);
        postModel = new PostModel(this,idPost);
        entityUserProfile = ProfileInstance.getProfileInstance(this).getProfile();
        getPostContent();
        initMention();
        setupMentionsList();

    }

    private void setupMentionsList() {
        mentionsList.setLayoutManager(new LinearLayoutManager(this));
        usersAdapter = new UsersAdapter(this);
        mentionsList.setAdapter(usersAdapter);
        mentionsList.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(final View view, final int position) {
                final User user = usersAdapter.getItem(position);
                if (user != null) {
                    final Mention mention = new Mention();
                    mention.setMentionName(user.getFullName());
                    mention.setMentionId(user.getuId());
                    mentions.insertMention(mention);
                }

            }
        }));

    }

    private void initMention() {
        mentions = new Mentions.Builder(getApplicationContext(),btnCommentInput)
                .suggestionsListener(this)
                .queryListener(this)
                .build();
    }

    private void submitComment() {
        btnSubmmitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(btnCommentInput.getText().toString())) {
                    btnCommentInput.setError("Mời bạn nhập bình luận");
                } else {
                    postModel.CommentPost(btnCommentInput.getText().toString().trim());
                    entityComment = new EntityComment();
                    entityComment.setFull_name(entityUserProfile.getFull_name());
                    entityComment.setMessage(btnCommentInput.getText().toString());
                    entityComment.setCreated_at(SystemHelper.getTimeStamp());
                    itemsComment.add(entityComment);
//                    float y = rcvComment.getChildAt(itemsComment.size()-1).getY();
//                    nsvPost.smoothScrollTo(0, (int) y);
                    btnCommentInput.setText("");
                }

            }
        });
    }


    private void setupToolbar() {

            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
    }

    private void getPostContent() {
        postModel.getSinglePost(new PostModel.VolleyCallbackStatus() {
            @Override
            public void onSuccess(EntityStatus entityStatus) {
                txtNumberLike.setText(entityStatus.getNumberLike()+"");
                txtNumberComment.setText(entityStatus.getNumberComment()+"");
                if (entityStatus.isLike()){
                    btnLove.setLiked(true);
                }else {
                    btnLove.setLiked(false);
                }
                btnLove.setOnLikeListener(new OnLikeListener() {
                    @Override
                    public void liked(LikeButton likeButton) {
                        postModel.LikePost(new PostModel.VolleyCallBackJson() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) throws JSONException {
                                JSONObject json = jsonObject.getJSONObject("data");
                                txtNumberLike.setText(json.getString("likes"));
                            }
                        });
                    }

                    @Override
                    public void unLiked(LikeButton likeButton) {
                        postModel.UnLikePost(new PostModel.VolleyCallBackJson() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) throws JSONException {
                                JSONObject json = jsonObject.getJSONObject("data");
                                txtNumberLike.setText(json.getString("likes"));
                            }
                        });
                    }
                });
                txtFullName.setText(entityStatus.getNameId());
                txtContentStatus.setText(entityStatus.getContent());
                txtTimePostStatus.setText(SystemHelper.getTimeAgo(entityStatus.getCreatedTime()));
                if (entityStatus.getImage()!= null){
                    Glide.with(getApplicationContext())
                            .load(entityStatus.getImage())
                            .fitCenter()
                            .centerCrop()
                            .placeholder(R.drawable.progress_loading)
//                        .error(R.mipmap.future_studio_launcher) // will be displayed if the image cannot be loaded
                            .crossFade()
                            .into(imgPostContent);
                }else {
                    imgPostContent.setVisibility(View.GONE);
                }
                toolbar.setTitle("Bài viết của " + entityStatus.getNameId());
                addEvent();
                submitComment();
            }
        });
    }

    private void addEvent() {
        itemsComment = new ArrayList<>();
        postModel.getComment(0,2,new PostModel.VolleyCallbackComment() {
            @Override
            public void onSuccess(ArrayList<EntityComment> entityComments) {
                itemsComment = entityComments;
                layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                adapterComment = new AdapterComment(CommentPostFullActivity.this,itemsComment);
                rcvComment.setLayoutManager(layoutManager);
                rcvComment.setNestedScrollingEnabled(false);
                rcvComment.setAdapter(adapterComment);
                adapterComment.notifyDataSetChanged();
//                Handler handler =new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        float y = rcvComment.getChildAt(itemsComment.size()-1).getY();
//                        nsvPost.smoothScrollTo(0, (int) y);
//                    }
//                },1000);
            }
        });
    }

    private void addControl() {
        rcvComment = (RecyclerView) findViewById(R.id.rcvComment);
        txtFullName = (TextView) findViewById(R.id.txtFullName);
        txtTimePostStatus = (TextView) findViewById(R.id.txtTimePostStatus);
        txtContentStatus = (TextView) findViewById(R.id.txtContentStatus);
        imgPostContent = (ImageView) findViewById(R.id.imgPostContent);
        btnLove = (LikeButton) findViewById(R.id.btnLove);
        txtNumberLike = (TextView) findViewById(R.id.txtNumberLike);
        txtNumberComment = (TextView) findViewById(R.id.txtNumberComment);
        btnComment = (Button) findViewById(R.id.btnComment);
        btnCommentInput = (EditText) findViewById(R.id.btnCommentInput);
        btnSubmmitComment = (ImageButton) findViewById(R.id.btnSubmmitComment);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nsvPost = (NestedScrollView) findViewById(R.id.nsvPost);
        mentionsList = (RecyclerView) findViewById(R.id.mentions_list);
    }

    @Override
    public void onQueryReceived(final String s) {
        userList = new ArrayList<>();
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                VolleySingleton.getInstance(getApplicationContext()).get("find_user?name=" + s, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            if(!response.getJSONArray("data").equals("null")) {
                                //Value is not null
                                for (int i =0; i< jsonArray.length();i++)
                                {
                                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                    User user = new User();
                                    user.setFirstName(jsonObject.getString("full_name"));
//                            user.setLastName(jsonObject.getString(""));
                                    user.setUsername(jsonObject.getString("username"));
                                    user.setImageUrl("");
                                    userList.add(user);
//                            adapterSearch.notifyDataSetChanged();
                                }
                                mentionsLoaderUtils = new MentionsLoaderUtils(getApplicationContext(),userList);
                                final List<User> users = mentionsLoaderUtils.searchUsers(s);
                                if (users != null && !users.isEmpty()) {
                                    usersAdapter.clear();
                                    usersAdapter.setCurrentQuery(s);
                                    usersAdapter.addAll(users);
                                    showMentionsList(true);
                                } else {
                                    showMentionsList(false);
                                }
                            }else {
                                System.exit(0);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },getApplicationContext());

            }
        },1000);
    }

    private void showMentionsList(boolean b) {
        ViewUtils.showView(this, R.id.mentions_list_layout);
        if (b) {
            ViewUtils.showView(this, R.id.mentions_list);
            ViewUtils.hideView(this, R.id.mentions_empty_view);
        } else {
            ViewUtils.hideView(this, R.id.mentions_list);
            ViewUtils.showView(this, R.id.mentions_empty_view);
        }

    }

    @Override
    public void displaySuggestions(boolean b) {
        if (b) {
            ViewUtils.showView(this, R.id.mentions_list_layout);
        } else {
            ViewUtils.hideView(this, R.id.mentions_list_layout);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        idPost = intent.getIntExtra("idPost",1);
        postModel = new PostModel(this,idPost);
        getPostContent();
    }
}
