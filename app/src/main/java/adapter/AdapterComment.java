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

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.joker.thanglong.Model.PostModel;
import com.joker.thanglong.R;
import com.joker.thanglong.Ultil.DialogUlti;
import com.joker.thanglong.Ultil.SystemHelper;
import com.joker.thanglong.UserProfileActivity;
import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.ValueAnimator;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;

import Entity.EntityComment;

/**
 * Created by joker on 2/18/17.
 */

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.Viewholder>{
    Activity context;
    PostModel postModel;
    ArrayList<EntityComment> items;
    private int lastPosition = -1;

    public AdapterComment(Activity context, ArrayList<EntityComment> items)
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
    public void onBindViewHolder(final Viewholder holder, int position) {
        postModel = new PostModel(context);
        addDataForView(holder,position);
//        setAnimation(holder.lnComment,position);
        if (position == 0){
            int colorFrom = context.getResources().getColor(R.color.background_tab_pressed);
            int colorTo = context.getResources().getColor(R.color.white);
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            colorAnimation.setDuration(5250); // milliseconds
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    holder.lnComment.setBackgroundColor((int) animator.getAnimatedValue());
                }

            });
            colorAnimation.start();
        }


    }

    private void addDataForView(Viewholder holder, final int position) {
        Glide.with(context).load(items.get(position).getAvatar()).fitCenter().crossFade().into(holder.imgAvatar);
        holder.txtFullNameComment.setText(items.get(position).getFull_name());
        holder.txtFullNameComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,UserProfileActivity.class);
                intent.putExtra("uId",items.get(position).getuId());
                context.startActivity(intent);
            }
        });
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
                            postModel.DeleteComment(new PostModel.VolleyCallBackCheck() {
                                @Override
                                public void onSuccess(boolean status) {
                                    items.remove(position);
                                    notifyDataSetChanged();

                                }
                            },items.get(position).getIdComment());
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
        private CircleImageView imgAvatar;

        public Viewholder(View itemView) {
            super(itemView);
            imgAvatar = (CircleImageView) itemView.findViewById(R.id.imgAvatar);
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
