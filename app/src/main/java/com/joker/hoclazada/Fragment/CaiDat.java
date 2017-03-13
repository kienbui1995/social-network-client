package com.joker.hoclazada.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joker.hoclazada.R;

/**
 * Created by joker on 2/9/17.
 */

public class CaiDat extends Fragment {
    private Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.caidat,container,false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbarA);
        return view;
    }
}
