package adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.joker.thanglong.Model.GroupModel;
import com.joker.thanglong.Model.PostModel;
import com.joker.thanglong.R;
import com.joker.thanglong.Ultil.DialogUtil;
import com.joker.thanglong.Ultil.SystemHelper;

import java.util.ArrayList;

import Entity.EntityMembership;

/**
 * Created by joker on 5/24/17.
 */

public class AdapterMemberGroup extends RecyclerView.Adapter<AdapterMemberGroup.Viewholder> {
    ArrayList<EntityMembership> items;
    Activity context;
    int type;
    GroupModel groupModel;
    public AdapterMemberGroup(Activity context, ArrayList<EntityMembership> items,int type)
    {
        this.context = context;
        this.items = items;
        this.type = type;
        groupModel = new GroupModel(context);
    }

    @Override
    public AdapterMemberGroup.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_member,parent,false);
        AdapterMemberGroup.Viewholder viewholder = new AdapterMemberGroup.Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(AdapterMemberGroup.Viewholder holder, int position) {
        switch (type){
            case 1:
                initListMember(holder,position);
                break;
            case 2:
                initListMemberRequest(holder,position);
            case 3:
        }
    }

    private void initListMemberRequest(final Viewholder holder, final int position) {
        holder.txtFullName.setText(items.get(position).getFull_name());
        Glide.with(context).load(items.get(position).getAvatar()).crossFade().fitCenter().into(holder.imgAvatar);
        holder.txtDateJoin.setText("Tham gia: "+ SystemHelper.getTimeAgo(items.get(position).getCreated_at()));
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DialogUtil.initDiaglog(context, "Bạn có muốn thêm thành viên này vào nhóm?", new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        groupModel.requestAction(items.get(position).getIdGr(), 2, new PostModel.VolleyCallBackCheck() {
                            @Override
                            public void onSuccess(boolean status) {
                                items.remove(position);
                                Snackbar.make(view,"Thêm thành công",Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DialogUtil.initDiaglog(context, "Bạn có muốn từ chối thành viên này vào nhóm?", new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        groupModel.requestAction(items.get(position).getIdGr(), 3, new PostModel.VolleyCallBackCheck() {
                            @Override
                            public void onSuccess(boolean status) {
                                items.remove(position);
                                Snackbar.make(view,"Từ chối thành công",Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

    }

    private void initListMember(final Viewholder holder, final int position) {
        holder.txtFullName.setText(items.get(position).getFull_name());
        holder.btnReject.setVisibility(View.GONE);
        holder.btnAccept.setVisibility(View.GONE);
        Glide.with(context).load(items.get(position).getAvatar()).crossFade().fitCenter().into(holder.imgAvatar);
        holder.txtDateJoin.setText("Tham gia: "+ SystemHelper.getTimeAgo(items.get(position).getCreated_at()));
        //DOi voi member

        if (items.get(position).isCan_edit()){
            holder.imgSetting.setVisibility(View.VISIBLE);
            holder.imgSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(context,holder.imgSetting);
                    if (items.get(position).getRole()==1){
                        popup.inflate(R.menu.opntion_member_group);
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()){
                                    case R.id.mn_make_admin_group:
                                        controlAction("Bạn có muốn cho thành viên này thành quản trị viên",2,position);
                                        break;
                                    case R.id.mn_block_user:
                                        controlAction("Bạn có muốn chặn thành viên này?",4,position);
                                        items.remove(position);
                                        notifyDataSetChanged();
                                        break;
                                    case R.id.mn_kick_user:
                                        kickUser("Bạn có muốn xóa thành viên này khỏi nhóm?",position);
                                        break;
                                }
                                return false;
                            }
                        });
                        popup.show();
                    }else if (items.get(position).getRole() == 2){
                        popup.inflate(R.menu.option_admin_group_2);
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()){
                                    case R.id.mn_delete_admin_group:
                                        controlAction("Bạn có muốn xóa quyền quản trị của thành viên này?",1,position);
                                        break;
                                    case R.id.mn_block_user:
                                        controlAction("Bạn có muốn chặn thành viên này?",4,position);
//                                        items.remove(position);
//                                        notifyDataSetChanged();
                                        break;
                                    case R.id.mn_kick_user:
                                        kickUser("Bạn có muốn xóa thành viên này khỏi nhóm?",position);
                                        break;
                                }
                                return false;
                            }
                        });
                        popup.show();
                    }
                }
            });

        }
    }

    private void kickUser(String message, final int position) {
        DialogUtil.initDiaglog(context, message, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                groupModel.kickUser(items.get(position).getIdGr(), new PostModel.VolleyCallBackCheck() {
                    @Override
                    public void onSuccess(boolean status) {
                        Toast.makeText(context, "Thực hiện thành công", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    private void controlAction(String message, final int role, final int position) {
        DialogUtil.initDiaglog(context, message, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                groupModel.setRole(items.get(position).getIdGr(), role, new PostModel.VolleyCallBackCheck() {
                    @Override
                    public void onSuccess(boolean status) {
                        Toast.makeText(context, "Thực hiện thành công", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView txtFullName;
        private Button btnAccept;
        private Button btnReject;
        private ImageView imgSetting;
        private TextView txtDateJoin;
        public Viewholder(View itemView) {
            super(itemView);
            txtDateJoin = (TextView) itemView.findViewById(R.id.txtDateJoin);
            imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatarMember);
            txtFullName = (TextView) itemView.findViewById(R.id.txtFullName);
            btnAccept = (Button) itemView.findViewById(R.id.btnAccept);
            btnReject = (Button) itemView.findViewById(R.id.btnReject);
            imgSetting = (ImageView) itemView.findViewById(R.id.imgSetting);
        }
    }
}
