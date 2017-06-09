package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.joker.thanglong.R;

import java.util.ArrayList;

import Entity.EntityListLike;

/**
 * Created by joker on 3/10/2017.
 */

public class AdapterMemberShow extends RecyclerView.Adapter<AdapterMemberShow.ViewHolder> {
    Context context;
    ArrayList<EntityListLike> items;

    public AdapterMemberShow(Context context, ArrayList<EntityListLike> list)
    {
        this.context = context;
        this.items=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_avatar_member,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("size1",position+"");
        Glide.with(context).load(items.get(position).getAvatar()).into(holder.imgAvavarMember);
       }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvavarMember;
        public ViewHolder(View itemView) {
            super(itemView);
            imgAvavarMember = (ImageView) itemView.findViewById(R.id.imgAvatarMember);
        }
    }
}
