package com.joker.thanglong;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.glide.GlideImageLoader;
import com.github.piasy.biv.view.BigImageView;
import com.github.piasy.biv.view.ImageSaveCallback;
import com.joker.thanglong.Ultil.ImageUlti;
import com.joker.thanglong.Ultil.VolleyHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class ImagePostActivity extends AppCompatActivity {
    VolleyHelper volleyHelper;
    private BigImageView mBigImage;
    private Button mBtnLoad;
    private Toolbar toolbar;
    private TextView txtShortContent;
    private TextView txtNumberLove;
    private ImageView imgHeart;
    private TextView line;
    private Button btnLove;
    private Button btnComment;
    private LinearLayout linearLayout;
    private ImageUlti imageUlti;
    private Button btnSave;
    //    private Disposable mPermissionRequest;
    private TextView txtNumberComment;
    private Boolean touch = false;


    Integer idStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BigImageViewer.initialize(GlideImageLoader.with(getApplicationContext()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_post);
        mBigImage = (BigImageView) findViewById(R.id.mBigImage);
        mBtnLoad = (Button) findViewById(R.id.mBtnLoad);
        Intent intent = getIntent();
        idStatus = intent.getIntExtra("idStatus",1);
        Log.d("idStatus",idStatus+"");
        initToolbar();
        getDataPhoto();
        addControl();
        addEvent();

//        loadImage(src);
    }

    private void getDataPhoto() {
        volleyHelper = new VolleyHelper(this,getResources().getString(R.string.url));
        volleyHelper.get("posts/" + idStatus, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = response.getJSONObject("data");
                    Log.d("json",jsonObject.toString());
                    loadImage(jsonObject.getString("photo"));
                    txtShortContent.setText(jsonObject.getString("message"));
                    txtNumberLove.setText(jsonObject.getString("likes"));
                    txtNumberComment.setText(jsonObject.getString("comments"));
//                    txtNumberComment.setText(jsonObject.getString(""));
                    toolbar.setTitle("Ảnh của " + jsonObject.getString("full_name"));
                    btnComment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void addEvent() {
        mBigImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (touch == false) {
                    txtNumberComment.setVisibility(View.INVISIBLE);
                    txtNumberLove.setVisibility(View.INVISIBLE);
                    txtShortContent.setVisibility(View.INVISIBLE);
                    btnComment.setVisibility(View.INVISIBLE);
                    btnLove.setVisibility(View.INVISIBLE);
                    btnSave.setVisibility(View.INVISIBLE);
                    imgHeart.setVisibility(View.INVISIBLE);
                    line.setVisibility(View.INVISIBLE);
                    linearLayout.setVisibility(View.INVISIBLE);
                    toolbar.setVisibility(View.INVISIBLE);
                    touch = true;
                } else {
                    txtNumberComment.setVisibility(View.VISIBLE);
                    txtNumberLove.setVisibility(View.VISIBLE);
                    txtShortContent.setVisibility(View.VISIBLE);
                    btnComment.setVisibility(View.VISIBLE);
                    btnLove.setVisibility(View.VISIBLE);
                    btnSave.setVisibility(View.VISIBLE);
                    imgHeart.setVisibility(View.VISIBLE);
                    line.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                    touch = false;
                }
            }
        });

    }

    private void addControl() {
        txtShortContent = (TextView) findViewById(R.id.txtShortContent);
        txtNumberLove = (TextView) findViewById(R.id.txtNumberLove);
        imgHeart = (ImageView) findViewById(R.id.imgHeart);
        line = (TextView) findViewById(R.id.line);
        btnLove = (Button) findViewById(R.id.btnLove);
        btnComment = (Button) findViewById(R.id.btnComment);
        btnSave = (Button) findViewById(R.id.btnSave);
        txtNumberComment = (TextView) findViewById(R.id.txtNumberComment);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("Ảnh của Tin tức Thăng Long");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadImage(String src) {
        {
            imageUlti = new ImageUlti(getApplicationContext());
            final BigImageView bigImageView = (BigImageView) findViewById(R.id.mBigImage);
            imageUlti.loadImage(bigImageView, src);
            bigImageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new MaterialDialog.Builder(ImagePostActivity.this)
                            .title("Chọn:")
                            .items(R.array.optionImage)
                            .theme(Theme.LIGHT)
                            .itemsCallback(new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                    if (TextUtils.equals(text, "Lưu hình ảnh")) {
                                        bigImageView.setImageSaveCallback(new ImageSaveCallback() {
                                            @Override
                                            public void onSuccess(String uri) {
                                                Toast.makeText(getApplicationContext(),
                                                        "Lưu vào thiết bị thành công",
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onFail(Throwable t) {
                                                t.printStackTrace();
                                                Toast.makeText(getApplicationContext(),
                                                        "Fail",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                            // TODO: Consider calling
                                            //    ActivityCompat#requestPermissions
                                            // here to request the missing permissions, and then overriding
                                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                            //                                          int[] grantResults)
                                            // to handle the case where the user grants the permission. See the documentation
                                            // for ActivityCompat#requestPermissions for more details.
                                            return;
                                        }
                                        bigImageView.saveImageIntoGallery();
                                    }
                                }
                            })
                            .show();
                    return true;
                }
            });
        }
    }

}
