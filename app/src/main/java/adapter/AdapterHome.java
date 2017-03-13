package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.joker.hoclazada.CommentPostActivity;
import com.joker.hoclazada.R;

import java.util.List;

/**
 * Created by joker on 3/3/17.
 */

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ViewHolder>{
    Context context;
    List<String> list;
    public AdapterHome(Context context, List<String> list)
    {
        this.context = context;
        this.list=list;
    }
    //1. Khoi tao layout
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Khoi tao layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (viewType ==0){
            View view = inflater.inflate(R.layout.custom_recycleview_home,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            //Tra ve cai viewholder, va xuong buoc 2
            return viewHolder;
        }else {
            View view = inflater.inflate(R.layout.custom_recycleview_home_text,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            //Tra ve cai viewholder, va xuong buoc 2
            return viewHolder;
        }
    }
    //3.Chay thu 3, Gan du lieu vao view
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, CommentPostActivity.class));
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
        private TextView txtTieuDe;
        private TextView txtLove;
        private TextView txtNumberLove;

        Button btnComment;
        public ViewHolder(View itemView) {
            super(itemView);
            btnComment = (Button) itemView.findViewById(R.id.btnComment);
            txtLove = (TextView) itemView.findViewById(R.id.txtLove);
            txtNumberLove = (TextView) itemView.findViewById(R.id.txtNumberLove);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position %2 * 2;
    }
}
