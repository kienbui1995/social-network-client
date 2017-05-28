package com.joker.thanglong.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.joker.thanglong.CommentPostActivity;
import com.joker.thanglong.Model.PostModel;
import com.joker.thanglong.R;

import java.util.ArrayList;

import Entity.EntityListLike;
import adapter.AdapterLisLike;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListLikeFragment extends Fragment {
    private Button btnBack;
    private ListView lvListLike;
    AdapterLisLike adapterLisLike;
    ArrayList<EntityListLike> listLikes;
    PostModel postModel;
    public ListLikeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_like, container, false);
        postModel = new PostModel(getActivity(), CommentPostActivity.idPost);
        addView(view);
        getData();
        return view;
    }

    private void getData() {
        listLikes = new ArrayList<>();
        postModel.getListLike(new PostModel.VolleyCallbackListLike() {
            @Override
            public void onSuccess(ArrayList<EntityListLike> entityListLikes) {
                listLikes = entityListLikes;
                adapterLisLike = new AdapterLisLike(getActivity(),R.layout.custom_search_result,listLikes);
                lvListLike.setAdapter(adapterLisLike);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStackImmediate();
            }
        });
    }

    private void addView(View view) {
        btnBack = (Button) view.findViewById(R.id.btnBack);
        lvListLike = (ListView) view.findViewById(R.id.lvListLike);

    }

}
