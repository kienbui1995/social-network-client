package com.joker.hoclazada;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.joker.hoclazada.Ultil.VolleyHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Entity.EntityUserSearch;
import adapter.AdapterSearch;

import static com.joker.hoclazada.R.id.idToolbar;

public class SearchActivity extends AppCompatActivity {
    private LinearLayout activitySearch;
    private Button btnBack;
    private EditText edtSearch;
    ListView lvSearch;
    ArrayList<EntityUserSearch> result;
    AdapterSearch adapterSearch;
    VolleyHelper volleyHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
                searchAction(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void searchAction(CharSequence query) {

        result = new ArrayList<>();
        volleyHelper = new VolleyHelper(this,getResources().getString(R.string.url));
        volleyHelper.get("find_user?name="+ query, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    Log.d("findUser",jsonArray.get(0).toString());
                    for (int i =0; i< jsonArray.length();i++)
                    {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        EntityUserSearch item = new EntityUserSearch();
                        item.setId(jsonObject.getString("id"));
                        item.setUsername(jsonObject.getString("username"));
                        item.setAvatar(jsonObject.getString("avatar"));
                        item.setFull_name(jsonObject.getString("full_name"));
                        item.setIs_followed(jsonObject.getBoolean("is_followed"));
                        result.add(item);
                        adapterSearch.notifyDataSetChanged();
                    }
                Log.d("result",result.size()+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("findUser",VolleyHelper.checkErrorCode(error)+"");
            }
        });
        adapterSearch = new AdapterSearch(this,R.layout.custom_search_result,result);
        lvSearch.setAdapter(adapterSearch);
        adapterSearch.notifyDataSetChanged();

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
