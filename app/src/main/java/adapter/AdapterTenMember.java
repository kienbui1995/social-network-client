package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.joker.thanglong.R;
import com.joker.thanglong.UserProfileActivity;

import java.util.List;

/**
 * Created by joker on 3/10/2017.
 */

public class AdapterTenMember extends RecyclerView.Adapter<AdapterTenMember.ViewHolder> {
    Context context;
    List<String> list;
    public AdapterTenMember(Context context, List<String> list)
    {
        this.context = context;
        this.list=list;
    }
    //1. Khoi tao layout
    @Override
    public AdapterTenMember.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Khoi tao layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (viewType ==0){
            View view = inflater.inflate(R.layout.custom_avatar_member,parent,false);
            AdapterTenMember.ViewHolder viewHolder = new AdapterTenMember.ViewHolder(view);
            //Tra ve cai viewholder, va xuong buoc 2
            return viewHolder;
        }else {
            View view = inflater.inflate(R.layout.custom_avatar_member,parent,false);
            AdapterTenMember.ViewHolder viewHolder = new AdapterTenMember.ViewHolder(view);
            //Tra ve cai viewholder, va xuong buoc 2
            return viewHolder;
        }
    }
    //3.Chay thu 3, Gan du lieu vao view
    @Override
    public void onBindViewHolder(final AdapterTenMember.ViewHolder holder, final int position) {
        holder.imgAvavarMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, UserProfileActivity.class));
            }
        });
//        holder.txtLove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                holder.txtNumberLove.setText("21");
//            }
//        });


        //position tuong ung voi tung gia tri trong list

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    //2.Ham nay chay thu 2, find view o day, nhan cai return o Buoc 1
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgAvavarMember;
        public ViewHolder(View itemView) {
            super(itemView);
            imgAvavarMember = (ImageView) itemView.findViewById(R.id.imgAvatarMember);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return position %2 * 2;
    }
}
