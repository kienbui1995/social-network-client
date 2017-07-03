package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joker.thanglong.ChannelActivity;
import com.joker.thanglong.R;

import java.util.ArrayList;

import Entity.EntityChannel;

/**
 * Created by joker on 3/30/17.
 */

public class AdapterListTopChannel extends RecyclerView.Adapter<AdapterListTopChannel.ViewHolder>{
        Activity context;
        ArrayList<EntityChannel> items;

    public AdapterListTopChannel(Activity context, ArrayList<EntityChannel> list) {
        this.context = context;
        this.items = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_group_popular,parent,false);
        AdapterListTopChannel.ViewHolder viewHolder = new AdapterListTopChannel.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txtNameGroup.setText(items.get(position).getName());
        Glide.with(context).load(items.get(position).getCover()).fitCenter().centerCrop().crossFade().into(holder.txtCover);
        holder.txtCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChannelActivity.class);
                intent.putExtra("idChannel",items.get(position).getId());
                context.startActivity(intent);

            }
        });
    }

//    private void initInfoDiaglogGroup(int position) {
//        Dialog settingsDialog = new Dialog(context);
//        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        settingsDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
//        View view1 = context.getLayoutInflater().inflate(R.layout.info_dialog,null);
//        ImageView imgCover = (ImageView) view1.findViewById(R.id.imgCover);
//        CircleImageView imgAvatar = (CircleImageView) view1.findViewById(R.id.imgAvatar);
//        TextView txtNameGroup = (TextView) view1.findViewById(R.id.txtNameGroup);
//        TextView txtBio = (TextView) view1.findViewById(R.id.txtBio);
//        Glide.with(context).load(items.get(position).getAvatar()).crossFade().into(imgAvatar);
//        Glide.with(context).load(items.get(position).getCover()).crossFade().into(imgCover);
//        txtNameGroup.setText(items.get(position).getName());
//        txtBio.setText(items.get(position).getDescription());
//        txtBio.setMovementMethod(new ScrollingMovementMethod());
//        settingsDialog.setContentView(view1);
//        settingsDialog.show();
//    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView txtCover;
        private TextView txtNameGroup;

        public ViewHolder(View view) {
            super(view);
            txtCover = (ImageView) view.findViewById(R.id.txtCover);
            txtNameGroup = (TextView) view.findViewById(R.id.txtNameGroup1);

        }
    }
}
