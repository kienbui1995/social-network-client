package com.joker.thanglong;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.joker.thanglong.Interface.EndlessScrollListener;
import com.joker.thanglong.Model.PostModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Entity.EntityComment;
import adapter.AdapterComment;

public class CommentPostActivity extends AppCompatActivity {
    private ListView lvComment;
    ArrayList<EntityComment> dsComment;
    AdapterComment adapterComment;
    private LinearLayout headerLayout;
    private Button btnStatusLike;
    private RecyclerView rcvComment;
    private EditText btnCommentInput;
    private ImageButton btnSubmmitComment;
    private RecyclerView.LayoutManager layoutManager;
    private EndlessScrollListener endlessScrollListener;
    private PostModel postModel;
    private int idPost;
    private int numberLike;
    private String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_post);
        //get Id post
        Intent intent = getIntent();
        idPost = intent.getIntExtra("idPost",1);
        numberLike = intent.getIntExtra("likes",1);
        postModel = new PostModel(this,idPost);
        addView();
        initData();
        btnSubmmitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComment();
            }
        });
    }

    private void addComment() {
        if (!TextUtils.isEmpty(btnCommentInput.getText().toString())){
            postModel.addComment(new PostModel.VolleyCallBackJson() {
                @Override
                public void onSuccess(JSONObject jsonObject) throws JSONException {
                    dsComment.clear();
                    initData();
                }
            },btnCommentInput.getText().toString());
        }else {
            btnCommentInput.setError("Mời bạn nhập nội dung");
        }
    }

    private void initData() {
        btnStatusLike.setText(numberLike+"");
        //init Array list comment
        dsComment = new ArrayList<>();
        //Get comment
        postModel.getComment(0, new PostModel.VolleyCallbackComment() {
            @Override
            public void onSuccess(ArrayList<EntityComment> entityComments) {
                dsComment = entityComments;
                loadMore();
            }
        });
    }

    private void loadMore() {
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adapterComment = new AdapterComment(this,dsComment);
        rcvComment.setLayoutManager(layoutManager);
        rcvComment.setAdapter(adapterComment);
        adapterComment.notifyDataSetChanged();
        endlessScrollListener = new EndlessScrollListener((LinearLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                postModel.getComment(totalItemsCount,new PostModel.VolleyCallbackComment() {
                    @Override
                    public void onSuccess(ArrayList<EntityComment> entityComments) {
                        dsComment.addAll(entityComments);
                        adapterComment.notifyDataSetChanged();
                    }
                });
            }
        };
        rcvComment.setOnScrollListener(endlessScrollListener);
    }


    private void addView() {
        headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        btnStatusLike = (Button) findViewById(R.id.btnStatusLike);
        rcvComment = (RecyclerView) findViewById(R.id.rcvComment);
        btnCommentInput = (EditText) findViewById(R.id.btnCommentInput);
        btnSubmmitComment = (ImageButton) findViewById(R.id.btnSubmmitComment);
    }
}
