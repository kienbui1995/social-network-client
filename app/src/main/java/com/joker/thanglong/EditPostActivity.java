package com.joker.thanglong;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.joker.thanglong.Model.PostModel;

import java.util.HashMap;

import Entity.EntityStatus;
import Entity.EntityUserProfile;

public class EditPostActivity extends AppCompatActivity {
    private Toolbar idToolbar;
    private TextView txtFullNamePost;
    private Spinner spnPrivacy;
    private EditText edtStatusInput;
    private ImageView imgPost;
    int idPost;
    private int privacy;
    PostModel postModel;
    EntityUserProfile entityUserProfile;
    String arrPrivacy[] = {"Công khai","Chỉ cho người theo dõi bạn","Riêng tư"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        Intent intent = getIntent();
        idPost = intent.getIntExtra("idPost",1);
        postModel = new PostModel(this,idPost);
        addControl();
        setupTabs();
        getContent();
        setupSpinner();
    }

    private void getContent() {
        postModel.getSinglePost(new PostModel.VolleyCallbackStatus() {
            @Override
            public void onSuccess(EntityStatus entityStatus) {
                edtStatusInput.setText(entityStatus.getContent());
                txtFullNamePost.setText(entityStatus.getNameId());
                spnPrivacy.setPrompt("Mời bạn chọn: ");
                spnPrivacy.setSelection(entityStatus.getPrivacy() - 1);

            }
        });
    }

    private void addControl() {
        idToolbar = (Toolbar) findViewById(R.id.idToolbar);
        imgPost = (ImageView) findViewById(R.id.imgPost);
        edtStatusInput = (EditText) findViewById(R.id.edtStatusInput);
        spnPrivacy = (Spinner) findViewById(R.id.spnPrivacy);
        txtFullNamePost = (TextView) findViewById(R.id.txtFullNamePost);
    }
    private void setupTabs() {
        idToolbar.setTitle("Sửa bài viết");
        setSupportActionBar(idToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        idToolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        idToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editpost,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itEditPost){
            if (TextUtils.isEmpty(edtStatusInput.getText()))
            {
                edtStatusInput.setError("Xin mời bạn nhập nội dung");
            }else {
                postModel.editPost(edtStatusInput.getText().toString().trim(), privacy, new PostModel.VolleyCallBackCheck() {
                    @Override
                    public void onSuccess(boolean status) {
                        finish();
                        Toast.makeText(EditPostActivity.this, "Sửa bài viết thành công", Toast.LENGTH_SHORT).show();
                    }
                });
                }
            }
        return super.onOptionsItemSelected(item);
        }



    private void setupSpinner() {
        final HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
        for (int i = 0; i < 3; i++)
        {
            spinnerMap.put((i+1),arrPrivacy[i]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,arrPrivacy);
        spnPrivacy.setAdapter(adapter);
        spnPrivacy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                privacy = i+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}

