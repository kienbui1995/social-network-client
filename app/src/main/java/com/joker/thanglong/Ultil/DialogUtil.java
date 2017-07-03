package com.joker.thanglong.Ultil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.bumptech.glide.Glide;
import com.joker.thanglong.CustomView.TouchImageView;
import com.joker.thanglong.GroupActivity;
import com.joker.thanglong.Model.GroupModel;
import com.joker.thanglong.Model.PostModel;
import com.joker.thanglong.R;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import Entity.EntityClass;
import Entity.EntityGroup;
import adapter.AdapterDetailStudent;

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
    public static void showPopUpAlert(final Context context){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Dialog settingsDialog = new Dialog(context);
                settingsDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                settingsDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
                LayoutInflater view = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                View view1 =view.inflate(R.layout.info_dialog,null);
                settingsDialog.setContentView(view1);
                settingsDialog.show();

            }
        },1000);
    }
    public static void showInfoClass(Activity context, EntityClass item){
        final Dialog settingsDialog = new Dialog(context);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        View view = context.getLayoutInflater().inflate(R.layout.item_dialog_class,null);
        LinearLayout lnRoot = (LinearLayout) view.findViewById(R.id.ln_root);
        ImageView imgCancel = (ImageView) view.findViewById(R.id.imgCancel);
        TextView  txtNameSubject = (TextView) view.findViewById(R.id.txtNameSubject);
        TextView  txtNameTeacher = (TextView) view.findViewById(R.id.txtNameTeacher);
        TextView  txtNameRoom = (TextView) view.findViewById(R.id.txtNameRoom);

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsDialog.dismiss();
            }
        });
        txtNameSubject.setText(item.getSubject().getName());
        txtNameTeacher.setText(item.getTeacher().getName());
        txtNameRoom.setText(item.getRoom().getCode());
        settingsDialog.setContentView(view);
        settingsDialog.show();
    }
    public static void showImageEnlarge(Activity context, String url){
        final Dialog settingsDialog = new Dialog(context);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        View view = context.getLayoutInflater().inflate(R.layout.dialog_image,null);
        TouchImageView photo = (TouchImageView) view.findViewById(R.id.imgLarge);
        Glide.with(context).load(url).crossFade().into(photo);
        settingsDialog.setContentView(view);
        settingsDialog.show();
    }

    public static void showInfoGroup(final Activity context, final EntityGroup item){
        final Dialog settingsDialog = new Dialog(context);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        View view1 = context.getLayoutInflater().inflate(R.layout.info_dialog,null);
        ImageView imgCover = (ImageView) view1.findViewById(R.id.imgCover);
        CircleImageView imgAvatar = (CircleImageView) view1.findViewById(R.id.imgAvatar);
        TextView txtNameGroup = (TextView) view1.findViewById(R.id.txtNameGroup1);
        TextView txtBio = (TextView) view1.findViewById(R.id.txtBio);
        Button btnTotalMember = (Button) view1.findViewById(R.id.btnTotalMember);
        Button btnTotalPost = (Button) view1.findViewById(R.id.btnTotalPost);
        Glide.with(context).load(item.getAvatar()).crossFade().into(imgAvatar);
        Glide.with(context).load(item.getCover()).crossFade().into(imgCover);
        txtNameGroup.setText(item.getName());
        txtBio.setText(item.getDescription());
        btnTotalMember.setText(item.getMembers()+" thành viên");
        btnTotalPost.setText(item.getPosts()+" bài viết");
        if (item.is_admin()) {
            Button btnReject = (Button) view1.findViewById(R.id.btnReject);
            btnReject.setVisibility(View.GONE);
            Button btnAccept = (Button) view1.findViewById(R.id.btnAccept);
            btnAccept.setText("Vào nhóm");
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startIntentToGroup(context,item.getId());
                    settingsDialog.dismiss();
                }
            });
        }else if (item.is_member()){
            Button btnReject = (Button) view1.findViewById(R.id.btnReject);
            btnReject.setText("Ra khỏi nhóm");
            btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    GroupModel groupModel = new GroupModel(context);
                    groupModel.leaveGroup(item.getId(), new PostModel.VolleyCallBackCheck() {
                        @Override
                        public void onSuccess(boolean status) {
                        }
                    });
                    Snackbar.make(view,"Ra khỏi nhóm thành công, kéo xuống để làm mới danh sách",Snackbar.LENGTH_LONG).show();
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            settingsDialog.dismiss();
                        }
                    },1000);
                }

            });
            Button btnAccept = (Button) view1.findViewById(R.id.btnAccept);
            btnAccept.setText("Vào nhóm");
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startIntentToGroup(context,item.getId());
                    settingsDialog.dismiss();
                }
            });
        }else if (item.isCan_join()){
            Button btnReject = (Button) view1.findViewById(R.id.btnReject);
            btnReject.setVisibility(View.GONE);
            Button btnAccept = (Button) view1.findViewById(R.id.btnAccept);
            btnAccept.setText("Yêu cầu vào nhóm ");
            if (item.getPrivacy() == 1){
                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        GroupModel groupModel = new GroupModel(context);
                        groupModel.joinGroup(item.getId(), new PostModel.VolleyCallBackCheck() {
                            @Override
                            public void onSuccess(boolean status) {
                                Snackbar.make(view,"Thành công",Snackbar.LENGTH_INDEFINITE)
                                        .setAction("Vào nhóm", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                startIntentToGroup(context,item.getId());
                                                settingsDialog.dismiss();
                                            }
                                        })
                                        .show();
                            }
                        });
                    }
                });
            }else if (item.getPrivacy()==2){
                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        } else if (item.is_pending()){
            final Button btnReject = (Button) view1.findViewById(R.id.btnReject);
            final Button btnAccept = (Button) view1.findViewById(R.id.btnAccept);
            btnAccept.setText("Đang chờ được xét duyệt");
            btnReject.setText("Hủy bỏ yêu cầu");
            btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    GroupModel groupModel = new GroupModel(context);
                    groupModel.leaveGroup(item.getId(), new PostModel.VolleyCallBackCheck() {
                        @Override
                        public void onSuccess(boolean status) {
                            btnAccept.setText("Yêu cầu tham gia nhóm");
                            btnReject.setVisibility(View.GONE);
                            Snackbar.make(view,"Hủy bỏ thành công",Snackbar.LENGTH_SHORT).show();
                        }
                    });
//                    groupModel.requestAction();
                }
            });
        }else if (item.isCan_request()) {
            Button btnReject = (Button) view1.findViewById(R.id.btnReject);
            btnReject.setVisibility(View.GONE);
            Button btnAccept = (Button) view1.findViewById(R.id.btnAccept);
            btnAccept.setText("Yêu cầu vào nhóm ");
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    GroupModel groupModel = new GroupModel(context);
                    groupModel.joinGroup(item.getId(), new PostModel.VolleyCallBackCheck() {
                        @Override
                        public void onSuccess(boolean status) {
                            Snackbar.make(view,"Thành công",1000).show();
                        }
                    });
                }
            });
        }else {
            Button btnReject = (Button) view1.findViewById(R.id.btnReject);
            btnReject.setVisibility(View.GONE);
            Button btnAccept = (Button) view1.findViewById(R.id.btnAccept);
        }

        txtBio.setMovementMethod(new ScrollingMovementMethod());
        settingsDialog.setContentView(view1);
        settingsDialog.show();
    }

//    public static void refreshFragment(Activity activity){
//        android.app.Fragment fm = activity.getFragmentManager().findFragmentByTag("GROUP");
//        activity.getFragmentManager().beginTransaction().detach(fm).attach(fm).commit();
//    }
    public static void startIntentToGroup(Activity context,int idGr)
    {
        Intent intent = new Intent(context,GroupActivity.class);
        intent.putExtra("idGroup",idGr);
        context.startActivity(intent);
    }

    public static void alert(String message,Context context){
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .content(message)
                .positiveText(R.string.agree)
                .show();
    }

    public static void showInfoStudent(Activity context){
        final Dialog settingsDialog = new Dialog(context);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        View view = context.getLayoutInflater().inflate(R.layout.item_detail_student,null);
//        TouchImageView photo = (TouchImageView) view.findViewById(R.id.imgLarge);
//        Glide.with(context).load(url).crossFade().into(photo);
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            arrayList.add("1");
        }
        ImageView imgStudentImage = (ImageView) view.findViewById(R.id.imgStudentImage);
        TextView txtStudentName = (TextView) view.findViewById(R.id.txtStudentName);
        TextView txtStudentCode = (TextView) view.findViewById(R.id.txtStudentCode);
        RecyclerView rcvListViPham = (RecyclerView) view.findViewById(R.id.rcvListViPham);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        AdapterDetailStudent adapterDetailStudent = new AdapterDetailStudent(context,arrayList);
        rcvListViPham.setLayoutManager(layoutManager);
        rcvListViPham.setAdapter(adapterDetailStudent);
        adapterDetailStudent.notifyDataSetChanged();
        settingsDialog.setContentView(view);
        settingsDialog.show();

    }

}
