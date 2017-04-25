package com.joker.hoclazada.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joker.hoclazada.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditPofileFragment extends Fragment {


    public EditPofileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_pofile, container, false);
    }

}
