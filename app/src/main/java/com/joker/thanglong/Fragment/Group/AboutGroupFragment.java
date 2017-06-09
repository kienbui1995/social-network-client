package com.joker.thanglong.Fragment.Group;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.bumptech.glide.Glide;
import com.joker.thanglong.GroupActivity;
import com.joker.thanglong.ManagerMemberGroupActivity;
import com.joker.thanglong.Model.GroupModel;
import com.joker.thanglong.R;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;

import Entity.EntityGroup;
import adapter.AdapterMemberShow;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutGroupFragment extends Fragment {
    private AdapterMemberShow adapterMemberSh;
    private List<String> list;
    private TextView txtNameGroup;
    private TextView txtBioGroup;
    private TextView txtTotalPost;
    private TextView txtTotalMember;
    private CircleImageView imgAvatarGroup;
    private TextView txtViewAllMember;
    private ImageView imgEditGroupName;
    private ImageView imgEditGroupBio;
    private GroupModel groupModel;
//    private RecyclerView rcvTenMember;

    public AboutGroupFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_group, container, false);
        addView(view);
        addData();
//        list = new ArrayList<>();
//        for (int i = 0; i<10;i++){
//            list.add(i+"");
//        }
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
//        adapterMemberSh = new AdapterMemberShow(getActivity(), list);
//
//        rcvTenMember.setLayoutManager(layoutManager);
//        rcvTenMember.setAdapter(adapterMemberSh);
//        rcvTenMember.setNestedScrollingEnabled(false);
//        adapterMemberSh.notifyDataSetChanged();
        return view;
    }

    private void addData() {
        groupModel = new GroupModel(getActivity());
        txtNameGroup.setText(GroupActivity.groupInfo.getName());
        txtBioGroup.setText(GroupActivity.groupInfo.getDescription());
        txtTotalMember.setText(GroupActivity.groupInfo.getMembers()+"");
        txtTotalPost.setText(GroupActivity.groupInfo.getPosts()+"");
        Glide.with(getActivity()).load(GroupActivity.groupInfo.getAvatar()).fitCenter().crossFade().into(imgAvatarGroup);
        if (GroupActivity.groupInfo.is_admin()){
            editGroup();
        }
        txtViewAllMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ManagerMemberGroupActivity.class);
                intent.putExtra("idGroup",GroupActivity.groupInfo.getId());
                intent.putExtra("privacyGroup",GroupActivity.groupInfo.getPrivacy());
                if (GroupActivity.groupInfo.is_admin()){
                    intent.putExtra("isAdminGroup",true);
                }else {
                    intent.putExtra("isAdminGroup",false);
                }
                startActivity(intent);
            }
        });
    }

    private void editGroup() {
        imgEditGroupBio.setVisibility(View.VISIBLE);
        imgEditGroupName.setVisibility(View.VISIBLE);
        imgEditGroupName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    changeNameGroup();
            }
        });

        imgEditGroupBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBioGroup();
            }
        });
    }

    private void changeBioGroup() {
        new MaterialDialog.Builder(getActivity())
                .title("Chỉnh sửa mô tả nhóm")
                .theme(Theme.LIGHT)
                .inputRangeRes(2, 500, R.color.btn_send_pressed)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(null, GroupActivity.groupInfo.getDescription(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                        EntityGroup profile = new EntityGroup();
                        profile.setDescription(input.toString().trim());
                        groupModel.editInfoGroup(GroupActivity.groupInfo.getId(),profile);
                        txtBioGroup.setText(input);
                        Toast.makeText(getActivity(),"Đổi mô tả nhóm thành công",Toast.LENGTH_LONG).show();
                    }
                }).show();

    }

    private void changeNameGroup() {
        new MaterialDialog.Builder(getActivity())
                .title("Chỉnh sửa tên nhóm")
                .content("Nhập tên nhóm muốn chỉnh sửa")
                .theme(Theme.LIGHT)
                .inputRangeRes(2, 20, R.color.btn_send_pressed)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(null, GroupActivity.groupInfo.getName(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                        EntityGroup profile = new EntityGroup();
                        profile.setName(input.toString().trim());
                        groupModel.editInfoGroup(GroupActivity.groupInfo.getId(),profile);
                        txtNameGroup.setText(input);
                        Toast.makeText(getActivity(),"Đổi tên nhóm thành công",Toast.LENGTH_LONG).show();
                    }
                }).show();

    }

    private void addView(View view) {
//        rcvTenMember = (RecyclerView) view.view.findViewById(R.id.rcvTenMember);
        txtNameGroup = (TextView) view.findViewById(R.id.txtNameGroup);
        txtBioGroup = (TextView) view.findViewById(R.id.txtBioGroup);
        txtTotalPost = (TextView) view.findViewById(R.id.txtTotalPost);
        txtTotalMember = (TextView) view.findViewById(R.id.txtTotalMember);
        txtViewAllMember = (TextView) view.findViewById(R.id.txtViewAllMember);
        imgEditGroupName = (ImageView) view.findViewById(R.id.imgEditGroupName);
        imgEditGroupBio = (ImageView) view.findViewById(R.id.imgEditGroupBio);
        imgAvatarGroup = (CircleImageView) view.findViewById(R.id.imgAvatarGroup);

    }


}
