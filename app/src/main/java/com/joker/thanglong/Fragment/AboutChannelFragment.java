package com.joker.thanglong.Fragment;


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
import com.joker.thanglong.ChannelActivity;
import com.joker.thanglong.Model.ChannelModel;
import com.joker.thanglong.R;

import de.hdodenhof.circleimageview.CircleImageView;

import Entity.EntityChannel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutChannelFragment extends Fragment {
    private CircleImageView imgAvatarChannel;
    private TextView txtBioGroup;
    private TextView txtNameChannel;
    private TextView txtShortName;
    private TextView txtTotalMember;
    private TextView txtViewAllMember;
    private ImageView imgEditGroupBio;
    private ImageView imgEditGroupName;
    private ImageView imgEditShortName;
    private ChannelModel channelModel;
    
    public AboutChannelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        addView(view);
        initData();
        return view;
    }

    private void initData() {
        channelModel = new ChannelModel(getActivity());
        txtNameChannel.setText(ChannelActivity.channelInfo.getName());
        txtBioGroup.setText(ChannelActivity.channelInfo.getDescription());
        txtShortName.setText(ChannelActivity.channelInfo.getShort_name());
        txtTotalMember.setText(ChannelActivity.channelInfo.getTotalFollower()+"");
        Glide.with(getActivity()).load(ChannelActivity.channelInfo.getAvatar()).fitCenter().crossFade().into(imgAvatarChannel);
        if (ChannelActivity.channelInfo.is_admin()){
            editChannel();
            imgEditGroupBio.setVisibility(View.VISIBLE);
            imgEditGroupName.setVisibility(View.VISIBLE);
            imgEditShortName.setVisibility(View.VISIBLE);
        }
    }

    private void editChannel() {
        imgEditGroupName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editName();
            }
        });
        imgEditGroupBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editBio();
            }
        });
        imgEditShortName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editShortName();
            }
        });
    }

    private void editName() {
        new MaterialDialog.Builder(getActivity())
                .title("Chỉnh sửa tên kênh")
                .content("Nhập tên kênh muốn chỉnh sửa")
                .theme(Theme.LIGHT)
                .inputRangeRes(2, 20, R.color.btn_send_pressed)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(null, ChannelActivity.channelInfo.getName(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                        EntityChannel profile = new EntityChannel();
                        profile.setName(input.toString().trim());
                        channelModel.editInfoChannel(ChannelActivity.channelInfo.getId(),profile);
                        txtNameChannel.setText(input);
                        Toast.makeText(getActivity(),"Đổi tên kênh thành công",Toast.LENGTH_LONG).show();
                    }
                }).show();

    }

    private void editShortName() {
        new MaterialDialog.Builder(getActivity())
                .title("Chỉnh sửa tên ngắn gọn =")
                .content("Nhập tên ngắn gọn muốn chỉnh sửa")
                .theme(Theme.LIGHT)
                .inputRangeRes(2, 20, R.color.btn_send_pressed)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(null, ChannelActivity.channelInfo.getShort_name(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                        EntityChannel profile = new EntityChannel();
                        profile.setShort_name(input.toString().trim());
                        channelModel.editInfoChannel(ChannelActivity.channelInfo.getId(),profile);
                        txtNameChannel.setText(input);
                        Toast.makeText(getActivity(),"Đổi tên ngắn gọn thành công",Toast.LENGTH_LONG).show();
                    }
                }).show();

    }

    private void editBio() {
        new MaterialDialog.Builder(getActivity())
                .title("Chỉnh sửa mô tả kênh")
                .content("Nhập mô tả muốn chỉnh sửa")
                .theme(Theme.LIGHT)
                .inputRangeRes(2, 20, R.color.btn_send_pressed)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(null, ChannelActivity.channelInfo.getDescription(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                        EntityChannel profile = new EntityChannel();
                        profile.setName(input.toString().trim());
                        channelModel.editInfoChannel(ChannelActivity.channelInfo.getId(),profile);
                        txtNameChannel.setText(input);
                        Toast.makeText(getActivity(),"Đổi mô tả thành công",Toast.LENGTH_LONG).show();
                    }
                }).show();

    }

    private void addView(View view) {
        imgAvatarChannel = (CircleImageView) view.findViewById(R.id.imgAvatarChannel);
        txtBioGroup = (TextView) view.findViewById(R.id.txtBioGroup);
        txtNameChannel = (TextView) view.findViewById(R.id.txtNameChannel);
        txtShortName = (TextView) view.findViewById(R.id.txtShortName);
        txtTotalMember = (TextView) view.findViewById(R.id.txtTotalMember);
        txtViewAllMember = (TextView) view.findViewById(R.id.txtViewAllMember);
        imgEditGroupBio = (ImageView) view.findViewById(R.id.imgEditGroupBio);
        imgEditGroupName = (ImageView) view.findViewById(R.id.imgEditGroupName);
        imgEditShortName = (ImageView) view.findViewById(R.id.imgEditShortName);
    }

}
