package adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.joker.hoclazada.R;

import java.util.ArrayList;

import Entity.EntityListLike;

/**
 * Created by joker on 5/5/17.
 */

public class AdapterLisLike extends ArrayAdapter {
    Activity context;
    int resource;
    ArrayList<EntityListLike> items;
    private ImageView imgAvatarSearchItem;
    private TextView txtFullNameSearchItem;
    private TextView txtUserNameSearchItem;
    private Button btnFollowUser;



    public AdapterLisLike(@NonNull Activity context, @LayoutRes int resource, @NonNull ArrayList<EntityListLike> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.items = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource,null);
        addControl(row);
        addEvent(position);
        return row;
    }

    private void addEvent(int position) {
        txtFullNameSearchItem.setText(items.get(position).getFull_name());
        txtUserNameSearchItem.setText("@"+ items.get(position).getUsername());
        if (items.get(position).isFollow()){
            btnFollowUser.setBackgroundResource(R.drawable.btn_unfollow);
            btnFollowUser.setTextColor(Color.parseColor("#FFFFFF"));
            btnFollowUser.setText("Đã theo dõi");
        }else {
            btnFollowUser.setText("Theo dõi");
        }

    }

    private void addControl(View row) {
        imgAvatarSearchItem = (ImageView) row.findViewById(R.id.imgAvatarSearchItem);
        txtFullNameSearchItem = (TextView) row.findViewById(R.id.txtFullNameSearchItem);
        txtUserNameSearchItem = (TextView) row.findViewById(R.id.txtUserNameSearchItem);
        btnFollowUser = (Button) row.findViewById(R.id.btnFollowUser);
    }
}
