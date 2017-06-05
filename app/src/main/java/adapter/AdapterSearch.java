package adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.bumptech.glide.Glide;
import com.joker.thanglong.Model.UserModel;
import com.joker.thanglong.R;
import com.joker.thanglong.UserProfileActivity;

import java.util.ArrayList;

import Entity.EntityUserSearch;

/**
 * Created by joker on 2/24/17.
 */

public class AdapterSearch extends ArrayAdapter {
    Activity context;
    int resource;
    ArrayList<EntityUserSearch> items;
    private ImageView imgAvatarSearchItem;
    private TextView txtFullNameSearchItem;
    private TextView txtUserNameSearchItem;
    private Button btnFollowUser;
    private UserModel userModel;


    public AdapterSearch(Activity context, int resource, ArrayList objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.items = objects;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource,null);
        userModel = new UserModel(context,Integer.parseInt(items.get(position).getId()));
        addControl(row);
        addData(position);
        return row;
    }

    private void addData(final int position) {
        Glide.with(context).load(items.get(position).getAvatar()).fitCenter().into(imgAvatarSearchItem);
        txtFullNameSearchItem.setText(items.get(position).getFull_name());
        txtFullNameSearchItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,UserProfileActivity.class);
                intent.putExtra("uId",items.get(position).getId());
                context.startActivity(intent);
            }
        });
        txtUserNameSearchItem.setText("@"+items.get(position).getUsername());
        if (items.get(position).is_followed()){
            btnFollowUser.setBackgroundResource(R.drawable.btn_unfollow);
            btnFollowUser.setTextColor(Color.parseColor("#FFFFFF"));
            btnFollowUser.setText("Đã theo dõi");
        }else {
            btnFollowUser.setText("Theo dõi");
            btnFollowUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new MaterialDialog.Builder(context)
                            .content("Bạn có muốn theo dõi "+items.get(position).getFull_name()+ " ?")
                            .positiveText(R.string.agree)
                            .theme(Theme.LIGHT)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    Log.d("positionId",items.get(position).getId());
                                    btnFollowUser.setBackgroundResource(R.drawable.btn_unfollow);
                                    btnFollowUser.setTextColor(Color.parseColor("#FFFFFF"));
                                    btnFollowUser.setText("Đã theo dõi");
                                    btnFollowUser.setEnabled(false);
                                    userModel.Follow(Integer.parseInt(items.get(position).getId()));
                                    Intent intent = new Intent(context, UserProfileActivity.class);
                                    intent.putExtra("uId",items.get(position).getId());
                                    context.startActivity(intent);
                                }
                            })
                            .negativeText(R.string.disagree)
                            .show();
                }
            });
        }
    }

    private void addControl(View row) {
        imgAvatarSearchItem = (ImageView) row.findViewById(R.id.imgAvatarSearchItem);
        txtFullNameSearchItem = (TextView) row.findViewById(R.id.txtFullNameSearchItem);
        txtUserNameSearchItem = (TextView) row.findViewById(R.id.txtUserNameSearchItem);
        btnFollowUser = (Button) row.findViewById(R.id.btnFollowUser);
    }


}
