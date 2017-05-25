package adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.joker.thanglong.R;

import java.util.ArrayList;

/**
 * Created by joker on 5/24/17.
 */

public class AdapterMemberGroup extends RecyclerView.Adapter<AdapterMemberGroup.Viewholder> {
    ArrayList<String> items;
    Activity context;
    int type;
    public AdapterMemberGroup(Activity context, ArrayList<String> items,int type)
    {
        this.context = context;
        this.items = items;
        this.type = type;
    }

    @Override
    public AdapterMemberGroup.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_member,parent,false);
        AdapterMemberGroup.Viewholder viewholder = new AdapterMemberGroup.Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(AdapterMemberGroup.Viewholder holder, int position) {
        switch (type){
            case 1:
                memberTab(holder,position);
                break;
            case 2:
                memberRequest(holder,position);
                break;
            case 3:
                listBlocks(holder,position);
                break;
        }
    }

    private void memberTab(Viewholder holder, int position) {
        holder.btnAccept.setVisibility(View.GONE);
        holder.btnReject.setVisibility(View.GONE);
    }

    private void memberRequest(Viewholder holder, int position) {

    }

    private void listBlocks(Viewholder holder, int position) {
        holder.btnAccept.setVisibility(View.GONE);
        holder.btnReject.setText("Bỏ chặn");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView txtFullName;
        private Button btnAccept;
        private Button btnReject;
        private ImageView imgSetting;

        public Viewholder(View itemView) {
            super(itemView);

            imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            txtFullName = (TextView) itemView.findViewById(R.id.txtFullName);
            btnAccept = (Button) itemView.findViewById(R.id.btnAccept);
            btnReject = (Button) itemView.findViewById(R.id.btnReject);
            imgSetting = (ImageView) itemView.findViewById(R.id.imgSetting);
        }
    }
}
