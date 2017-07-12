package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joker.thanglong.DetailNotificationChannelActivity;
import com.joker.thanglong.R;
import com.joker.thanglong.Ultil.SystemHelper;

import java.util.ArrayList;

import Entity.EntityNotificationChannel;

/**
 * Created by joker on 7/4/17.
 */

public class AdapterListNotificationChannel extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int TEXT = 1;
    public static final int PHOTO = 2;
    ArrayList<EntityNotificationChannel> items;
    Context context;
    View view;
    public AdapterListNotificationChannel(ArrayList<EntityNotificationChannel> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (viewType){
            case TEXT:
                view = inflater.inflate(R.layout.item_noti_channel,parent,false);
                ViewHolderText viewHolderText = new ViewHolderText(view);
                return viewHolderText;
            case PHOTO:
                view = inflater.inflate(R.layout.item_noti_channel_photo,parent,false);
                ViewHolderPhoto viewHolderPhoto = new ViewHolderPhoto(view);
                return viewHolderPhoto;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()){
            case TEXT:
                ViewHolderText holderText = (ViewHolderText) holder;
                holderText.txtTitle.setText(items.get(position).getTittle());
                holderText.txtChannelName.setText(items.get(position).getChannel().getName());
                holderText.txtMessage.setText(items.get(position).getMessage());
                if ((SystemHelper.getTimeStamp()-items.get(position).getCreated_at())<43200){
                holderText.imgNew.setVisibility(View.VISIBLE);
                    holderText.viewRoot.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, DetailNotificationChannelActivity.class);
                            intent.putExtra("id",items.get(position).getId());
                            context.startActivity(intent);
                        }
                    });
            }
                holderText.txtDateTime.setText(SystemHelper.convertDate(items.get(position).getCreated_at()));
                break;
            case PHOTO:
                ViewHolderPhoto holderPhoto = (ViewHolderPhoto) holder;
                holderPhoto.txtTitle.setText(items.get(position).getTittle());
                holderPhoto.txtChannelName.setText(items.get(position).getChannel().getName());
                holderPhoto.txtDateTime.setText(SystemHelper.convertDate(items.get(position).getCreated_at()));
                holderPhoto.txtMessage.setText(items.get(position).getMessage());
                if ((SystemHelper.getTimeStamp()-items.get(position).getCreated_at())<23200){
                    holderPhoto.imgNew.setVisibility(View.VISIBLE);
                 }
                Glide.with(context).load(items.get(position).getPhoto()).crossFade().placeholder(R.drawable.ic_user_default).into(holderPhoto.imgPhotoNoti);
                holderPhoto.viewRoot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, DetailNotificationChannelActivity.class);
                        intent.putExtra("id",items.get(position).getId());
                        context.startActivity(intent);
                    }
                });

                break;
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolderText extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private TextView txtChannelName;
        private TextView txtDateTime;
        private TextView txtMessage;
        private TextView imgNew;
        private ConstraintLayout viewRoot;
        public ViewHolderText(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtChannelName = (TextView) itemView.findViewById(R.id.txtChannelName);
            txtDateTime = (TextView) itemView.findViewById(R.id.txtDateTime);
            txtMessage = (TextView) itemView.findViewById(R.id.txtMessage);
            imgNew = (TextView) itemView.findViewById(R.id.imgNew);
            viewRoot = (ConstraintLayout) itemView.findViewById(R.id.viewRoot);

        }
    }
    public class ViewHolderPhoto extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private TextView txtChannelName;
        private TextView txtDateTime;
        private ImageView imgPhotoNoti;
        private TextView txtMessage;
        private TextView imgNew;
        private ConstraintLayout viewRoot;
        public ViewHolderPhoto(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtChannelName = (TextView) itemView.findViewById(R.id.txtChannelName);
            txtDateTime = (TextView) itemView.findViewById(R.id.txtDateTime);
            imgPhotoNoti = (ImageView) itemView.findViewById(R.id.imgPhotoNoti);
            txtMessage = (TextView) itemView.findViewById(R.id.txtMessage);
            imgNew = (TextView) itemView.findViewById(R.id.imgNew);
            viewRoot = (ConstraintLayout) itemView.findViewById(R.id.viewRoot);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).getPhoto() == null){
            return TEXT;
        }else {
            return PHOTO;
        }
    }
}
