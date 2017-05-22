package adapter;

import android.app.Activity;
import android.content.Intent;
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
    public AdapterFollowingList(Activity context, int resource, ArrayList objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.items = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(this.resource,null);
        userModel = new UserModel(context,items.get(position).getId());
        addControl(view);
        addEvent(position);
        return view;
    }

    private void addEvent(final int position) {
        imgAvatarFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserProfileActivity.class);
                intent.putExtra("uId",items.get(position).getId());
                context.startActivity(intent);
            }
        });
        txtFullNameFollow.setText(items.get(position).getFull_name());
        txtFullNameFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserProfileActivity.class);
                intent.putExtra("uId",items.get(position).getId());
                context.startActivity(intent);
            }
        });
        txtUserNameFollow.setText("@"+items.get(position).getUsername());
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
                                userModel.unFollow();
                                items.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                        .negativeText(R.string.disagree)
                        .show();


            }
        });
    }


    private void addControl(View view) {
        imgAvatarFollow = (ImageView) view.findViewById(R.id.imgAvatarFollow);
        txtFullNameFollow = (TextView) view.findViewById(R.id.txtFullNameFollow);
        txtUserNameFollow = (TextView) view.findViewById(R.id.txtUserNameFollow);
        btnMessage = (Button) view.findViewById(R.id.btnMessage);
    }


}
