package adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joker.thanglong.R;
import com.joker.thanglong.Ultil.SystemHelper;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;

import Entity.EntityNotification;

/**
 * Created by joker on 2/15/17.
 */

public class AdapterNotification extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int LIKE = 1;
    public static final int COMMENT = 2;
    public static final int FOLLOW = 6;
    public static final int NULL = 7;
    private Context context;
    private View view;
    private ArrayList<EntityNotification> items;

    public AdapterNotification(Context context, ArrayList<EntityNotification> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (viewType) {
            case LIKE:
                view = inflater.inflate(R.layout.item_notification_like, parent, false);
                ViewHolder viewHolderLike = new ViewHolder(view);
                return viewHolderLike;
            case COMMENT:
                view = inflater.inflate(R.layout.item_notification_like, parent, false);
                ViewHolder viewHolderComment = new ViewHolder(view);
                return viewHolderComment;
            case FOLLOW:
                view = inflater.inflate(R.layout.item_notification_like, parent, false);
                ViewHolder viewHolderFollow = new ViewHolder(view);
                return viewHolderFollow;
            case NULL:
                view = inflater.inflate(R.layout.item_notification_like, parent, false);
                ViewHolder viewHolderNull = new ViewHolder(view);
                return viewHolderNull;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        switch (holder.getItemViewType()){
            case LIKE:
                ViewHolder viewHolderLike = (ViewHolder) holder;
                viewHolderLike.txtDateTime.setText(SystemHelper.getTimeAgo(items.get(position).getUpdatedAt()));
                if (items.get(position).getLast_post().getPhoto() == null)
                {
                    viewHolderLike.lnPhoto.setVisibility(View.GONE);
                }else {
                    Glide.with(context).load(items.get(position).getLast_post().getPhoto()).centerCrop().crossFade()
                            .into(viewHolderLike.imgPost);
                }
                Glide.with(context).load(items.get(position).getActor().getAvatar()).centerCrop().placeholder(R.drawable.ic_user_default).crossFade()
                        .into(viewHolderLike.imgAvatar);
                updateText(viewHolderLike.txtMessage,items.get(position).getActor().getFullName(),
                        " đã thích bài viết của bạn: \"" + items.get(position).getLast_post().getMessage()+ "\"");
                break;
            case COMMENT:
                ViewHolder viewHolderComment = (ViewHolder) holder;
                viewHolderComment.txtDateTime.setText(SystemHelper.getTimeAgo(items.get(position).getUpdatedAt()));
                viewHolderComment.imgSticker.setImageResource(R.drawable.ic_cmt_16);
                if (items.get(position).getLast_post().getPhoto() == null)
                {
                    viewHolderComment.lnPhoto.setVisibility(View.GONE);
                }else {
                    Glide.with(context).load(items.get(position).getLast_post().getPhoto()).centerCrop().crossFade()
                            .into(viewHolderComment.imgPost);
                }
                Glide.with(context).load(items.get(position).getActor().getAvatar()).centerCrop().placeholder(R.drawable.ic_user_default).crossFade()
                        .into(viewHolderComment.imgAvatar);
                updateText(viewHolderComment.txtMessage,items.get(position).getActor().getFullName(),
                        " đã bình luận bài viết của bạn: \"" + items.get(position).getLast_comment().getMessage()+ "\"");
                break;
            case FOLLOW:
                ViewHolder viewHolderFollow = (ViewHolder) holder;
                viewHolderFollow.txtDateTime.setText(SystemHelper.getTimeAgo(items.get(position).getUpdatedAt()));
                viewHolderFollow.imgSticker.setImageResource(R.drawable.ic_following_16);
                if (items.get(position).getLast_user().getAvatar() == null)
                {
                    viewHolderFollow.lnPhoto.setVisibility(View.GONE);
                }else {
                    Glide.with(context).load(items.get(position).getLast_user().getAvatar()).centerCrop().crossFade()
                            .into(viewHolderFollow.imgPost);
                }
                updateText(viewHolderFollow.txtMessage,items.get(position).getActor().getFullName(),
                        " đã theo dõi: "+ items.get(position).getLast_user().getFullName());
                Glide.with(context).load(items.get(position).getActor().getAvatar()).centerCrop().placeholder(R.drawable.ic_user_default).crossFade()
                        .into(viewHolderFollow.imgAvatar);
                break;
            case NULL:
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (items.size() == 0){
            return 0;
        }else {
            return items.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (items.get(position).getActionId()){
            case LIKE:
                return LIKE;
            case COMMENT:
                return COMMENT;
            case FOLLOW:
                return FOLLOW;
            case NULL:
                return NULL;
        }
        return FOLLOW;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout lnRoot;
        private ImageView imgSticker;
        private TextView txtMessage;
        private TextView txtDateTime;
        private ImageView imgPost;
        private LinearLayout lnPhoto;
        private CircleImageView imgAvatar;


        public ViewHolder(View itemView) {
            super(itemView);
            lnRoot = (LinearLayout) itemView.findViewById(R.id.lnRoot);
            imgSticker = (ImageView) itemView.findViewById(R.id.imgSticker);
            txtMessage = (TextView) itemView.findViewById(R.id.txtMessage);
            txtDateTime = (TextView) itemView.findViewById(R.id.txtDateTime);
            imgPost = (ImageView) itemView.findViewById(R.id.imgPost);
            lnPhoto = (LinearLayout) itemView.findViewById(R.id.lnPhoto);
            imgAvatar = (CircleImageView) itemView.findViewById(R.id.imgAvatar);
        }
    }

    private void updateText(TextView textView, String actor, String message) {
        SpannableString spannableString = new SpannableString(actor + message);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD),0,actor.length(),0);
        spannableString.setSpan(new RelativeSizeSpan(1.0f), actor.length(), spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }
}
