package com.joker.thanglong;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.joker.thanglong.Model.UserModel;

import java.util.ArrayList;

import Entity.EntityUserSearch;
import adapter.AdapterSearch;

import static com.joker.thanglong.R.id.idToolbar;

public class SearchActivity extends AppCompatActivity {
    private LinearLayout activitySearch;
    private Button btnBack;
    private EditText edtSearch;
    ListView lvSearch;
    ArrayList<EntityUserSearch> result;
    AdapterSearch adapterSearch;
    UserModel userModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        userModel = new UserModel(this);
        result = new ArrayList<>();
        addControll();
        addEvent();
    }

    private void addEvent() {
        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getApplicationContext(),UserProfileActivity.class));
            }
        });

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
//                    searchAction();
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                result.clear();
                searchAction(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void searchAction(CharSequence query) {
        userModel.search(query, new UserModel.VolleyCallBackSearch() {
            @Override
            public void onSuccess(ArrayList<EntityUserSearch> listUser) {
                result = listUser;
                adapterSearch = new AdapterSearch(SearchActivity.this,R.layout.custom_search_result,result);
                lvSearch.setAdapter(adapterSearch);
                adapterSearch.notifyDataSetChanged();
            }
        });

    }

    private void addControll() {
        activitySearch = (LinearLayout) findViewById(R.id.activity_search);
        btnBack = (Button) findViewById(idToolbar);
        edtSearch = (EditText) findViewById(R.id.edtSearch);
        lvSearch = (ListView) findViewById(R.id.lvSearchResult);
//        String array[] = getResources().getStringArray(R.array.user_photos);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array);

    }

    public void ActionBack(View view) {
        finish();
    }
}