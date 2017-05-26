package com.joker.thanglong.Ultil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.bumptech.glide.Glide;
import com.joker.thanglong.GroupActivity;
import com.joker.thanglong.R;

import de.hdodenhof.circleimageview.CircleImageView;

import Entity.EntityGroup;

/**
 * Created by joker on 5/11/17.
 */

public class DialogUtil {
    public static void initDiaglog(Context context, String content, MaterialDialog.SingleButtonCallback callback){
        new MaterialDialog.Builder(context)
                .content(content)
                .positiveText(R.string.agree)
                .theme(Theme.LIGHT)
                .onPositive(callback)
                .negativeText(R.string.disagree)
                .show();
    }
    public static void showInfoGroup(final Activity context, EntityGroup item){
        Dialog settingsDialog = new Dialog(context);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        View view1 = context.getLayoutInflater().inflate(R.layout.info_dialog,null);
        ImageView imgCover = (ImageView) view1.findViewById(R.id.imgCover);
        CircleImageView imgAvatar = (CircleImageView) view1.findViewById(R.id.imgAvatar);
        TextView txtNameGroup = (TextView) view1.findViewById(R.id.txtNameGroup);
        TextView txtBio = (TextView) view1.findViewById(R.id.txtBio);
        Glide.with(context).load(item.getAvatar()).crossFade().into(imgAvatar);
        Glide.with(context).load(item.getCover()).crossFade().into(imgCover);
        txtNameGroup.setText(item.getName());
        txtBio.setText(item.getDescription());
        if (item.is_admin()) {
            Button btnReject = (Button) view1.findViewById(R.id.btnReject);
            btnReject.setVisibility(View.GONE);
            Button btnAccept = (Button) view1.findViewById(R.id.btnAccept);
            btnAccept.setText("Vào nhóm");
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, GroupActivity.class));
                }
            });
        }else if (item.is_member()){
            Button btnReject = (Button) view1.findViewById(R.id.btnReject);
            btnReject.setText("Ra khỏi nhóm");
            Button btnAccept = (Button) view1.findViewById(R.id.btnAccept);
            btnAccept.setText("Vào nhóm");
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, GroupActivity.class));
                }
            });
        }else if (item.is_pending()){
            Button btnReject = (Button) view1.findViewById(R.id.btnReject);
            btnReject.setVisibility(View.GONE);
            Button btnAccept = (Button) view1.findViewById(R.id.btnAccept);
            btnAccept.setText("Đang chờ được xét duyệt");
        }else if (item.isCan_request()) {
            Button btnReject = (Button) view1.findViewById(R.id.btnReject);
            btnReject.setVisibility(View.GONE);
            Button btnAccept = (Button) view1.findViewById(R.id.btnAccept);
            btnAccept.setText("Yêu cầu vào nhóm ");
        }

        txtBio.setMovementMethod(new ScrollingMovementMethod());
        settingsDialog.setContentView(view1);
        settingsDialog.show();
    }

}
