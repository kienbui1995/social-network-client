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

import Entity.EntityFollow;

/**
 * Created by joker on 4/25/17.
 */

public class AdapterFollowingList extends ArrayAdapter {
    //param
    Activity context;
    int resource;
    ArrayList<EntityFollow> items;
    //view
    private ImageView imgAvatarFollow;
    private TextView txtFullNameFollow;
    private TextView txtUserNameFollow;
    private Button btnMessage;
    private UserModel userModel;
    public AdapterFollowingList(Activity context, int resource,ArrayList<EntityFollow> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.items = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("position",position+"");
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(this.resource,null);
        addControl(view);
        addEvent(position);
        return view;
    }

    private void addEvent(final int position) {
        userModel = new UserModel(context);
        Glide.with(context).load(items.get(position).getAvatar()).crossFade().fitCenter().into(imgAvatarFollow);
        imgAvatarFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserProfileActivity.class);
                intent.putExtra("uId",Integer.parseInt(items.get(position).getId()));
                context.startActivity(intent);
            }
        });
        txtFullNameFollow.setText(items.get(position).getFull_name());
        txtFullNameFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserProfileActivity.class);
                intent.putExtra("uId",Integer.parseInt(items.get(position).getId()));
                context.startActivity(intent);
            }
        });
        txtUserNameFollow.setText("@"+items.get(position).getUsername());

        if (items.get(position).is_followed()){
            btnMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new MaterialDialog.Builder(context)
                            .content("Bạn có muốn bỏ theo dõi "+items.get(position).getFull_name()+ " ?")
                            .positiveText(R.string.agree)
                            .theme(Theme.LIGHT)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    Log.d("positionId",items.get(position).getId());
                                    userModel.unFollow(Integer.parseInt(items.get(position).getId()));
                                    items.remove(position);
                                    notifyDataSetChanged();
                                }
                            })
                            .negativeText(R.string.disagree)
                            .show();

                }
            });
        }else {
            btnMessage.setText("Follow");
            btnMessage.setTextColor(Color.parseColor("#FFFFFF"));
            btnMessage.setBackgroundResource(R.drawable.btn_following);
            btnMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userModel.Follow(Integer.parseInt(items.get(position).getId()));
                    btnMessage.setBackgroundResource(R.drawable.btn_unfollow);
                    btnMessage.setTextColor(Color.parseColor("#FFFFFF"));
                    btnMessage.setText("Đã theo dõi");
                }
            });
        }
    }


    private void addControl(View view) {
        imgAvatarFollow = (ImageView) view.findViewById(R.id.imgAvatarFollow);
        txtFullNameFollow = (TextView) view.findViewById(R.id.txtFullNameFollow);
        txtUserNameFollow = (TextView) view.findViewById(R.id.txtUserNameFollow);
        btnMessage = (Button) view.findViewById(R.id.btnMessage);
    }


}
