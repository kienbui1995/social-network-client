package com.joker.hoclazada.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.joker.hoclazada.R;
import com.joker.hoclazada.Ultil.FilePath;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.CropSquareTransformation;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by joker on 2/10/17.
 */

public class DangKy extends Fragment {
    Button chonAvatar;
    ImageView avatar;
    Intent mIntent;
    private String selectedImagePath = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dangky,container,false);
        chonAvatar = (Button) view.findViewById(R.id.chooseAvatar);
        avatar = (ImageView) view.findViewById(R.id.avatar);
        Picasso.with(getApplicationContext())
                .load(R.drawable.avatar1)
                .transform(new CropSquareTransformation())
                .into(avatar);
        chonAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent = new Intent();
                mIntent.setType("image/*");
                mIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(mIntent, getString(R.string.select_picture)),
                        1);
            }
        });


        return view;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                selectedImagePath = FilePath.getPath(getApplicationContext(), data.getData());
                Picasso.with(getApplicationContext())
                        .load(data.getData())
                        .transform(new CropCircleTransformation())
                        .into(avatar);
                if (avatar.getVisibility() != View.VISIBLE) {
                    avatar.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
