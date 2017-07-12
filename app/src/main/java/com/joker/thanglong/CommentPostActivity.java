package com.joker.thanglong;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.joker.thanglong.Fragment.ListLikeFragment;
import com.joker.thanglong.Interface.EndlessScrollListener;
import com.joker.thanglong.Mention.adapter.RecyclerItemClickListener;
import com.joker.thanglong.Mention.adapter.UsersAdapter;
import com.joker.thanglong.Mention.models.Mention;
import com.joker.thanglong.Mention.models.User;
import com.joker.thanglong.Mention.utils.MentionsLoaderUtils;
import com.joker.thanglong.Model.PostModel;
import com.joker.thanglong.Ultil.VolleySingleton;
import com.percolate.caffeine.ViewUtils;
import com.percolate.mentions.Mentions;
import com.percolate.mentions.QueryListener;
import com.percolate.mentions.SuggestionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Entity.EntityComment;
import adapter.AdapterComment;

public class CommentPostActivity extends AppCompatActivity implements QueryListener, SuggestionsListener {
    private ListView lvComment;
    ArrayList<EntityComment> dsComment;
    AdapterComment adapterComment;
    private LinearLayout headerLayout;
    private Button btnStatusLike;
    private FrameLayout frRoot;
    private RecyclerView rcvComment;
    private EditText btnCommentInput;
    private ImageButton btnSubmmitComment;
    private RecyclerView.LayoutManager layoutManager;
    private EndlessScrollListener endlessScrollListener;
    private PostModel postModel;
    public static int idPost;
    private int numberLike;
    private String message;
    private RecyclerView mentionsList;
    private TextView mentionsEmptyView;
    Mentions mentions;
    List<User> userList;
    private UsersAdapter usersAdapter;
    private MentionsLoaderUtils mentionsLoaderUtils;
    Handler handler;
    private Timer timer;
    private Button btnLoadPrevious;
    int i=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_post);
        //get Id post
        Intent intent = getIntent();
        idPost = intent.getIntExtra("idPost",1);
        numberLike = intent.getIntExtra("likes",1);
        postModel = new PostModel(this,idPost);
        timer = new Timer();
        addView();
        initData();
        initMention();
        setupMentionsList();
        btnSubmmitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComment();
            }
        });
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

    private void addComment() {
        if (!TextUtils.isEmpty(btnCommentInput.getText().toString())){
            ArrayList<JSONObject> listMention = new ArrayList<>();
           if (!mentions.getInsertedMentions().isEmpty()){
               for (int i = 0; i< mentions.getInsertedMentions().size();i++){
                   String json = mentions.getInsertedMentions().get(i).toString();
                   try {
                       listMention.add(new JSONObject(json));
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }
           }
            postModel.addComment(new PostModel.VolleyCallBackJson() {
                @Override
                public void onSuccess(JSONObject jsonObject) throws JSONException {
                    dsComment.clear();
                    initData();
                    btnCommentInput.setText("");
                }
            },btnCommentInput.getText().toString(),listMention);

        }else {
            btnCommentInput.setError("Mời bạn nhập nội dung");

        }
    }



    private void initData() {
        btnStatusLike.setText(numberLike+"");
        btnStatusLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().add(R.id.frRoot,new ListLikeFragment(),"listlike")
                        .addToBackStack(null).setCustomAnimations(R.anim.dialog_enter,R.anim.dialog_exit)
                        .commit();
            }
        });
        //init Array list comment
        dsComment = new ArrayList<>();
        //Get comment
        postModel.getComment(0,2, new PostModel.VolleyCallbackComment() {
            @Override
            public void onSuccess(ArrayList<EntityComment> entityComments) {
                dsComment = entityComments;
                Collections.reverse(dsComment);
                if (dsComment.size()<5){
                    btnLoadPrevious.setVisibility(View.GONE);
                }
                loadMore();
            }
        });
    }

    private void loadMore() {
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adapterComment = new AdapterComment(this,dsComment);
        rcvComment.setLayoutManager(layoutManager);
        rcvComment.setAdapter(adapterComment);
        rcvComment.setNestedScrollingEnabled(false);
        adapterComment.notifyDataSetChanged();
        btnLoadPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postModel.getComment(i, 2, new PostModel.VolleyCallbackComment() {
                    @Override
                    public void onSuccess(ArrayList<EntityComment> entityComments) {
                        dsComment.addAll(0,entityComments);
                        adapterComment.notifyDataSetChanged();
                        if (entityComments.size()<5){
                            btnLoadPrevious.setVisibility(View.GONE);
                        }
                        i+=5;
                    }
                });
            }
        });


//        endlessScrollListener = new EndlessScrollListener((LinearLayoutManager) layoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                postModel.getComment(totalItemsCount,1,new PostModel.VolleyCallbackComment() {
//                    @Override
//                    public void onSuccess(ArrayList<EntityComment> entityComments) {
//                        dsComment.addAll(entityComments);
//                        adapterComment.notifyDataSetChanged();
//                    }
//                });
//            }
//        };

//        rcvComment.setOnScrollListener(endlessScrollListener);
    }


    private void addView() {
        headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        btnStatusLike = (Button) findViewById(R.id.btnStatusLike);
        rcvComment = (RecyclerView) findViewById(R.id.rcvComment);
        btnCommentInput = (EditText) findViewById(R.id.btnCommentInput);
        btnSubmmitComment = (ImageButton) findViewById(R.id.btnSubmmitComment);
        mentionsList = (RecyclerView) findViewById(R.id.mentions_list);
        mentionsEmptyView = (TextView) findViewById(R.id.mentions_empty_view);
        frRoot = (FrameLayout) findViewById(R.id.frRoot);
        btnLoadPrevious = (Button) findViewById(R.id.btnLoadPrevious);
    }

    @Override
    public void onQueryReceived(final String s) {
        userList = new ArrayList<>();
        timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                VolleySingleton.getInstance(getApplicationContext()).get("posts/"+ idPost+"/users?filter=" + s, null, new Response.Listener<JSONObject>() {
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
                                    user.setImageUrl(jsonObject.getString("avatar"));
                                    user.setuId(jsonObject.getInt("id"));
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
        usersAdapter.clear();
    }

    @Override
    public void displaySuggestions(boolean b) {
        if (b) {
            ViewUtils.showView(this, R.id.mentions_list_layout);
        } else {
            ViewUtils.hideView(this, R.id.mentions_list_layout);
        }

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
}
