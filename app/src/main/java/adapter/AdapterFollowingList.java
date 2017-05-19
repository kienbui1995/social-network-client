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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.joker.thanglong.R;
import com.joker.thanglong.Ultil.VolleyHelper;
import com.joker.thanglong.UserProfileActivity;

import org.json.JSONObject;

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
                                unFollow(Integer.parseInt(items.get(position).getId()));
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

    private void unFollow(int uId)
    {
        VolleyHelper volleyHelper = new VolleyHelper(context,context.getResources().getString(R.string.url));
        volleyHelper.delete("users/" +uId + "/subscriptions", null, new Response.Listener<JSONObject>() {
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
