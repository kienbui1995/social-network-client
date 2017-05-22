package com.joker.thanglong.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joker.thanglong.Model.PostModel;
import com.joker.thanglong.R;

import java.util.ArrayList;

import Entity.EntityComment;
import adapter.AdapterComment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends Fragment {
    ArrayList<EntityComment> items;
    AdapterComment adapterComment;
    PostModel postModel;
    int idPost;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView rcvComment;
    public CommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        init(view);
        getListComment();
        setAdapter();
        return view;
    }

    private void setAdapter() {
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        adapterComment = new AdapterComment(getActivity(),items);
        rcvComment.setLayoutManager(layoutManager);
        rcvComment.setAdapter(adapterComment);
        adapterComment.notifyDataSetChanged();
    }

    private void getListComment() {
        postModel.getComment(0,new PostModel.VolleyCallbackComment() {
            @Override
            public void onSuccess(ArrayList<EntityComment> entityComments) {
                items =entityComments;
            }
        });

    }

    private void init(View view) {
        idPost = getArguments().getInt("idPost");
        items = new ArrayList<>();
        postModel = new PostModel(getActivity(),idPost);
        rcvComment = (RecyclerView) view.findViewById(R.id.rcvComment);
    }

}
