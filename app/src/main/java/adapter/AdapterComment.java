package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.joker.thanglong.R;
import com.joker.thanglong.Ultil.DialogUlti;
import com.joker.thanglong.Ultil.PostUlti;
import com.joker.thanglong.Ultil.SystemHelper;
import com.joker.thanglong.UserProfileActivity;

import java.util.ArrayList;

import Entity.EntityComment;

/**
 * Created by joker on 2/18/17.
 */

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.Viewholder>{
    Context context;
    PostUlti postUlti;
    ArrayList<EntityComment> items;
    private int lastPosition = -1;

    public AdapterComment(Context context, ArrayList<EntityComment> items)
    {
        this.context = context;
        this.items=items;

    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Khoi tao layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_comment,parent,false);
        AdapterComment.Viewholder viewHolder = new AdapterComment.Viewholder(view);
        //Tra ve cai viewholder, va xuong buoc 2
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        addDataForView(holder,position);
//        setAnimation(holder.lnComment,position);
    }

    private void addDataForView(Viewholder holder, final int position) {
        postUlti = new PostUlti((Activity) context,items.get(position).getIdComment());
        holder.txtFullNameComment.setText(items.get(position).getFull_name());
        holder.txtFullNameComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserProfileActivity.class);
                intent.putExtra("uId",items.get(position).getuId()+"");
                Log.d("uIdd",items.get(position).getuId()+"");
                context.startActivity(intent);
            }
        });
        holder.txtContentComment.setText(items.get(position).getMessage());
        Log.d("ago",items.get(position).getCreated_at()+"");
        holder.txtTimeComment.setText(SystemHelper.getTimeAgo(items.get(position).getCreated_at()));
        holder.lnComment.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (items.get(position).isCanDelete()){
                    DialogUlti.initDiaglog(context, context.getResources().getString(R.string.deleteComment), new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            postUlti.DeleteComment(new PostUlti.VolleyCallBackCheck() {
                                @Override
                                public void onSuccess(boolean status) {
                                    Toast.makeText(context, "Xóa bình luận thành công", Toast.LENGTH_SHORT).show();
                                    items.remove(position);
                                    notifyDataSetChanged();

                                }
                            });
                        }
                    });
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView txtFullNameComment;
        private TextView txtContentComment;
        private TextView txtTimeComment;
        private LinearLayout lnComment;

        public Viewholder(View itemView) {
            super(itemView);

            txtFullNameComment = (TextView) itemView.findViewById(R.id.txtFullNameComment);
            txtContentComment = (TextView) itemView.findViewById(R.id.txtContentComment);
            txtTimeComment = (TextView) itemView.findViewById(R.id.txtTimeComment);
            lnComment = (LinearLayout) itemView.findViewById(R.id.lnComment);
        }
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}
