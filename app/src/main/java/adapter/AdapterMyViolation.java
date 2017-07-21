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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joker.thanglong.R;
import com.joker.thanglong.Ultil.SystemHelper;

import java.util.ArrayList;

import Entity.EntityViolation;

/**
 * Created by joker on 6/24/17.
 */

public class AdapterDetailStudent extends RecyclerView.Adapter<AdapterDetailStudent.ViewHolder>{
    Context context;
    ArrayList<EntityViolation> items;

    public AdapterDetailStudent(Context context, ArrayList<EntityViolation> list)
    {
        this.context = context;
        this.items=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_vipham,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (items.get(position).getPhoto() != null){
            holder.imgPhoto.setVisibility(View.VISIBLE);
            Glide.with(context).load(items.get(position).getPhoto()).into(holder.imgPhoto);
        }
        holder.txtMessage.setText(items.get(position).getMessage());
        holder.txtPlace.setText(items.get(position).getPlace());
        holder.txtDateTime.setText(SystemHelper.getTimeAgo(items.get(position).getTime_at()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtMessage;
        private TextView txtPlace;
        private TextView txtDateTime;
        private ImageView imgPhoto;


        public ViewHolder(View itemView) {
            super(itemView);
            txtMessage = (TextView) itemView.findViewById(R.id.txtMessage);
            txtPlace = (TextView) itemView.findViewById(R.id.txtPlace);
            txtDateTime = (TextView) itemView.findViewById(R.id.txtDateTime);
            imgPhoto = (ImageView) itemView.findViewById(R.id.imgPhoto);
        }
    }
    private void updateText(TextView textView, String actor, String message) {
        SpannableString spannableString = new SpannableString(actor + message);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD),0,actor.length(),0);
        spannableString.setSpan(new RelativeSizeSpan(1.0f), actor.length(), spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }
}
