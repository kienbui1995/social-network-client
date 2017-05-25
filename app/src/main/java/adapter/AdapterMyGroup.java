package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.joker.thanglong.GroupActivity;
import com.joker.thanglong.R;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;

/**
 * Created by joker on 3/30/17.
 */

public class AdapterMyGroup extends RecyclerView.Adapter<AdapterMyGroup.ViewHolder>{
        Context context;
        ArrayList<String> items;

    public AdapterMyGroup(Context context, ArrayList<String> list) {
        this.context = context;
        this.items = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_group,parent,false);
        AdapterMyGroup.ViewHolder viewHolder = new AdapterMyGroup.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (items.size() == position+1){
            holder.frMore.setVisibility(View.VISIBLE);
            holder.txtNameGroup.setVisibility(View.GONE);
            holder.imgAvatar.setVisibility(View.GONE);
            holder.frMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, GroupActivity.class));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imgAvatar;
        private FrameLayout frMore;
        private TextView txtMore;
        private TextView txtNameGroup;


        public ViewHolder(View view) {
            super(view);
            imgAvatar = (CircleImageView) view.findViewById(R.id.imgAvatar);
            frMore = (FrameLayout) view.findViewById(R.id.frMore);
            txtMore = (TextView) view.findViewById(R.id.txtMore);
            txtNameGroup = (TextView) view.findViewById(R.id.txtNameGroup);
        }
    }
}
