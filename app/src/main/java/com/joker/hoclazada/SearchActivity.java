package com.joker.hoclazada;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import adapter.AdapterSearch;

import static com.joker.hoclazada.R.id.idToolbar;

public class SearchActivity extends AppCompatActivity {
    private LinearLayout activitySearch;
    private Button btnBack;
    private EditText edtSearch;
    ListView lvSearch;
    ArrayList<String> result;
    AdapterSearch adapterSearch;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        addControll();
        addEvent();
    }

    private void addEvent() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    lvSearch.setVisibility(View.INVISIBLE);
                }
                else {
                    lvSearch.setVisibility(View.VISIBLE);
                    lvSearch.setAdapter(adapterSearch);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getApplicationContext(),UserProfileActivity.class));
            }
        });
    }

    private void addControll() {
        activitySearch = (LinearLayout) findViewById(R.id.activity_search);
        btnBack = (Button) findViewById(idToolbar);
        edtSearch = (EditText) findViewById(R.id.edtSearch);
        lvSearch = (ListView) findViewById(R.id.lvSearchResult);
        result = new ArrayList<>();
        result.add("1");
        result.add("1");
        result.add("1");
        result.add("1");
        result.add("1");
        result.add("1");
        result.add("1");
        result.add("1");
        result.add("1");
        result.add("1");
        result.add("1");
        result.add("1");
        result.add("1");
        result.add("1");
        adapterSearch = new AdapterSearch(this,R.layout.custom_search_result,result);
    }

    public void ActionBack(View view) {
        finish();
    }
}
