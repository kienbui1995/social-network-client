package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joker.thanglong.R;

import java.util.ArrayList;

import Entity.EntityExamSchedule;

/**
 * Created by joker on 7/10/17.
 */

public class AdapterLichThi extends RecyclerView.Adapter<AdapterLichThi.ViewHolder> {
    private Context context;
    private ArrayList<EntityExamSchedule> items;

    public AdapterLichThi(Context context, ArrayList<EntityExamSchedule> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public AdapterLichThi.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_row_schedule,parent,false);
        AdapterLichThi.ViewHolder viewHolder = new AdapterLichThi.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterLichThi.ViewHolder holder, int position) {
        holder.txtSTT.setText((position+1)+"");
        holder.txtNameSubject.setText(items.get(position).getSubject().getName());
        holder.txtCathi.setText(items.get(position).getTime());
        holder.txtNgayThi.setText(items.get(position).getDay());
        holder.txtPhongThi.setText(items.get(position).getRoom().getCode());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtSTT;
        private TextView txtNameSubject;
        private TextView txtCathi;
        private TextView txtPhongThi;
        private TextView txtNgayThi;

        public ViewHolder(View itemView) {
            super(itemView);
            txtSTT = (TextView) itemView.findViewById(R.id.txtSTT);
            txtNameSubject = (TextView) itemView.findViewById(R.id.txtNameSubject);
            txtCathi = (TextView) itemView.findViewById(R.id.txtCathi);
            txtPhongThi = (TextView) itemView.findViewById(R.id.txtPhongThi);
            txtNgayThi = (TextView) itemView.findViewById(R.id.txtNgayThi);
        }
    }
}
