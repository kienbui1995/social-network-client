package adapter;

import android.app.Activity;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.joker.hoclazada.R;
import com.joker.hoclazada.Ultil.VolleyHelper;

import org.json.JSONObject;

import java.util.ArrayList;

import Entity.EntityUserProfile;
import Entity.EntityUserSearch;
import io.realm.Realm;

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
        addControl(row);
        addData(position);
        return row;
    }

    private void addData(final int position) {
        txtFullNameSearchItem.setText(items.get(position).getFull_name());
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
                    Follow(items.get(position).getId());
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
    private void Follow(String uId){
        Realm realm;
        realm = Realm.getDefaultInstance();
        realm.init(context);
        EntityUserProfile profile = realm.where(EntityUserProfile.class).findFirst();
        VolleyHelper volleyHelper = new VolleyHelper(context,context.getResources().getString(R.string.url));
        volleyHelper.postHeader("users/" + profile.getuID() + "/subscribers/" + uId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("follow",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("follow",VolleyHelper.checkErrorCode(error)+"");

            }
        });
    }


}
