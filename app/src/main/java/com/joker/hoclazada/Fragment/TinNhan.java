package com.joker.hoclazada.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.joker.hoclazada.ChatActivity;
import com.joker.hoclazada.R;

import java.util.ArrayList;

import adapter.AdapterListChat;

/**
 * Created by joker on 2/9/17.
 */

public class TinNhan extends Fragment{
    private ListView lvListChat;
    ArrayList<String> listChat;
    View view;
    AdapterListChat adapterListChat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tinnhan,container,false);
        addControll();
        return view;
    }

    private void addControll() {
        lvListChat = (ListView) view.findViewById(R.id.lvListChat);
        listChat = new ArrayList<>();
        listChat.add("1");
        listChat.add("1");
        listChat.add("1");
        listChat.add("1");
        listChat.add("1");
        listChat.add("1");
        listChat.add("1");
        listChat.add("1");
        listChat.add("1");
        listChat.add("1");
        listChat.add("1");
        listChat.add("1");
        listChat.add("1");
        listChat.add("1");
        listChat.add("1");
        listChat.add("1");
        adapterListChat = new AdapterListChat(getActivity(),R.layout.custom_item_list_chat,listChat);
        lvListChat.setAdapter(adapterListChat);
        lvListChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getActivity(), ChatActivity.class));
            }
        });
    }
}
