package adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.joker.hoclazada.R;
import com.joker.hoclazada.Ultil.SystemHelper;

import java.util.ArrayList;

import Entity.EntityStatus;

/**
 * Created by joker on 3/3/17.
 */

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ViewHolder>{
    Context context;
    ArrayList<EntityStatus> items;
    String picRandom;
    public AdapterHome(Context context, ArrayList<EntityStatus> items)
    {
        this.context = context;
        this.items=items;
    }
    //1. Khoi tao layout
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Khoi tao layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.custom_recycleview_home_text,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            //Tra ve cai viewholder, va xuong buoc 2
            return viewHolder;
    }
    //3.Chay thu 3, Gan du lieu vao view
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.txtFullNameFeed.setText(items.get(position).getNameId());
        holder.txtTimePostStatus.setText(SystemHelper.getTimeAgo(items.get(position).getCreatedTime())+"");
        holder.txtContentStatus.setText(items.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    //2.Ham nay chay thu 2, find view o day, nhan cai return o Buoc 1
    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cvHome;
        private TextView txtFullNameFeed;
        private TextView txtTimePostStatus;
        private ImageView imgNumberLove;
        private TextView txtContentStatus;

        Button btnComment;
        public ViewHolder(View itemView) {
            super(itemView);
            cvHome = (CardView) itemView.findViewById(R.id.cvHome);
            txtFullNameFeed = (TextView) itemView.findViewById(R.id.txtFullNameFeed);
            txtTimePostStatus = (TextView) itemView.findViewById(R.id.txtTimePostStatus);
            imgNumberLove = (ImageView) itemView.findViewById(R.id.imgNumberLove);
            txtContentStatus = (TextView) itemView.findViewById(R.id.txtContentStatus);
//            btnComment = (Button) findViewById(R.id.btnComment);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position %2 * 2;
    }
}
