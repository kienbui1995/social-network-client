package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.joker.hoclazada.R;
import com.joker.hoclazada.Ultil.SystemHelper;
import com.joker.hoclazada.UserProfileActivity;

import java.util.ArrayList;

import Entity.EntityComment;

/**
 * Created by joker on 2/18/17.
 */

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.Viewholder>{
    Context context;
    ArrayList<EntityComment> items;
    private PopupWindow popWindow;
    private Button btnBackToComment;
    private ListView lvListLike;

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
    }

    private void addDataForView(Viewholder holder, final int position) {
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
        holder.txtTimeComment.setText(SystemHelper.getTimeAgo(items.get(position).getCreated_at()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView txtFullNameComment;
        private TextView txtContentComment;
        private TextView txtTimeComment;

        public Viewholder(View itemView) {
            super(itemView);

            txtFullNameComment = (TextView) itemView.findViewById(R.id.txtFullNameComment);
            txtContentComment = (TextView) itemView.findViewById(R.id.txtContentComment);
            txtTimeComment = (TextView) itemView.findViewById(R.id.txtTimeComment);
        }
    }

}
