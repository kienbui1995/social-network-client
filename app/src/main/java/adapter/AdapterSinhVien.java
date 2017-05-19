package adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joker.thanglong.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by joker on 3/6/17.
 */

public class AdapterSinhVien extends RecyclerView.Adapter<AdapterSinhVien.ViewHolder> {
    Context context;
    List<String> listSV;

    public AdapterSinhVien(Context context, List<String> listSV) {
        this.context = context;
        this.listSV = listSV;
    }

    @Override
    public AdapterSinhVien.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_item_sinhvien,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterSinhVien.ViewHolder holder, int position) {
        final boolean[] checked = {false};
        holder.imgSinhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (checked[0] == false){
                   holder.imgSinhVien.setAlpha((float) 0.5);
                   holder.imgChecked.setVisibility(View.VISIBLE);
                   checked[0] = true;
               }else {
                   holder.imgChecked.setVisibility(View.INVISIBLE);
                   holder.imgSinhVien.setAlpha((float) 1);
                   checked[0] = false;
               }
            }
        });

        holder.imgSinhVien.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final CharSequence[] items = {"Xem thông tin", "Điểm danh muộn"};

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle(Html.fromHtml("<font color='#FF7F27'>A23998</font>"));
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                       if (item == 1)
                       {
                           Picasso.with(context).load(R.drawable.checked).into(holder.imgChecked);
                           holder.txtDiemDanhMuon.setVisibility(View.VISIBLE);
                           holder.imgSinhVien.setAlpha((float) 0.5);
                           holder.imgChecked.setVisibility(View.VISIBLE);
                       }

                    }
                });
                builder.show();
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return listSV.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgSinhVien;
        private ImageView imgChecked;
        private TextView txtTenSinhVien;
        private TextView txtMaSinhVien;
        private TextView txtDiemDanhMuon;

        public ViewHolder(View itemView) {
            super(itemView);
            imgChecked = (ImageView) itemView.findViewById(R.id.imgChecked);
            imgSinhVien = (ImageView) itemView.findViewById(R.id.imgSinhVien);
            txtTenSinhVien = (TextView) itemView.findViewById(R.id.txtTenSinhVien);
            txtMaSinhVien = (TextView) itemView.findViewById(R.id.txtMaSinhVien);
            txtDiemDanhMuon = (TextView) itemView.findViewById(R.id.txtDiemDanhMuon);

        }
    }
}
