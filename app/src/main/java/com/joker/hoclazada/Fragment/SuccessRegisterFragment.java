package com.joker.hoclazada.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.joker.hoclazada.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuccessRegisterFragment extends Fragment {

    private Button btnSwitchLogin;
    private ViewPager viewPager;
    public SuccessRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_success_register, container, false);
        btnSwitchLogin = (Button) view.findViewById(R.id.btnSwitchLogin);
        btnSwitchLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager = (ViewPager) getActivity().findViewById(R.id.viewPager);
                viewPager.setCurrentItem(0);
            }
        });
//        YoYo.with(Techniques.FadeIn).duration(2000).playOn(btnSwitchLogin);
        return view;
    }

}
