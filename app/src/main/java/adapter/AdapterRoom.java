package adapter;

import android.app.Activity;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joker.thanglong.R;
import com.joker.thanglong.Ultil.DialogUtil;

import java.util.ArrayList;

import Entity.EntityClass;

/**
 * Created by joker on 3/14/17.
 */

public class AdapterRoom extends RecyclerView.Adapter<AdapterRoom.ViewHolder> {
    Activity context;
    ArrayList<EntityClass> items;
    public AdapterRoom(Activity context, ArrayList<EntityClass> list)
    {
        this.context = context;
        this.items=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_room,parent,false);
        AdapterRoom.ViewHolder viewHolder = new AdapterRoom.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        updateText(holder.tvSemester,"Tên kì: ",items.get(position).getTerm().getName());
        updateText(holder.tvShift,"Ca: ",items.get(position).getStart()+"-"+items.get(position).getEnd());
        updateText(holder.tvSubjectName,"Tên môn: ",items.get(position).getSubject().getName());
        holder.rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.showDetailClass(context,items.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSubjectName;
        private TextView tvSemester;
        private TextView tvShift;
        private RelativeLayout rlRoot;



        public ViewHolder(View itemView) {
            super(itemView);
            tvSubjectName = (TextView) itemView.findViewById(R.id.tvSubjectName);
            tvSemester = (TextView) itemView.findViewById(R.id.tvSemester);
            tvShift = (TextView) itemView.findViewById(R.id.tvShift);
            rlRoot = (RelativeLayout) itemView.findViewById(R.id.rl_root);
        }
    }
    private void updateText(TextView textView, String actor, String message) {
        SpannableString spannableString = new SpannableString(actor + message);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD),0,actor.length(),0);
        spannableString.setSpan(new RelativeSizeSpan(1.0f), actor.length(), spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }
}
