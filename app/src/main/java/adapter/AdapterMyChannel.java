package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joker.thanglong.ChannelActivity;
import com.joker.thanglong.GroupActivity;
import com.joker.thanglong.R;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;

import Entity.EntityChannel;

/**
 * Created by joker on 3/30/17.
 */

public class AdapterMyChannel extends RecyclerView.Adapter<AdapterMyChannel.ViewHolder>{
        Activity context;
        ArrayList<EntityChannel> items;

    public AdapterMyChannel(Activity context, ArrayList<EntityChannel> list) {
        this.context = context;
        this.items = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_group,parent,false);
        AdapterMyChannel.ViewHolder viewHolder = new AdapterMyChannel.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (items.size() == position+1 && items.size()>9){
            holder.frMore.setVisibility(View.VISIBLE);
            holder.txtNameGroup.setVisibility(View.GONE);
            holder.imgAvatar.setVisibility(View.GONE);
            holder.frMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, GroupActivity.class));
                }
            });
        }else {
            holder.txtNameGroup.setText(items.get(position).getName());
            Glide.with(context).load(items.get(position).getAvatar()).crossFade().fitCenter().into(holder.imgAvatar);
            holder.frItemGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChannelActivity.class);
                    intent.putExtra("idChannel",items.get(position).getId());
                    context.startActivity(intent);
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
        private LinearLayout frItemGroup;


        public ViewHolder(View view) {
            super(view);
            imgAvatar = (CircleImageView) view.findViewById(R.id.imgAvatar);
            frItemGroup = (LinearLayout) view.findViewById(R.id.frItemGroup);
            frMore = (FrameLayout) view.findViewById(R.id.frMore);
            txtMore = (TextView) view.findViewById(R.id.txtMore);
            txtNameGroup = (TextView) view.findViewById(R.id.txtNameGroup1);
        }
    }
}
