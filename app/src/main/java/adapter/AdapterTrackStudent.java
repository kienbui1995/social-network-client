package adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joker.thanglong.R;
import com.joker.thanglong.Ultil.DialogUtil;

import java.util.ArrayList;

import Entity.EntityStudent;

/**
 * Created by joker on 6/24/17.
 */

public class AdapterTrackStudent extends RecyclerView.Adapter<AdapterTrackStudent.ViewHolder>{
    Activity context;
    ArrayList<EntityStudent> items;

    public AdapterTrackStudent(Activity context, ArrayList<EntityStudent> list)
    {
        this.context = context;
        this.items=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_search_student,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//        Glide.with(context).load(items.get(position).getAvatar()).into(holder.imgAvavarMember);
        holder.txtStudenttCode.setText(items.get(position).getCode());
        holder.txtNameStudent.setText(items.get(position).getLast_name()+" "+ items.get(position).getFirst_name());
        holder.imgInfoStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.showInfoStudent(context,items.get(position));
            }
        });
        holder.imgAddAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
                DialogUtil.formAddViolation(context,items.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgStudentImage;
        private TextView txtNameStudent;
        private TextView txtStudenttCode;
        private TextView txtError;
        private ImageView imgInfoStudent;
        private ImageView imgAddAction;

        public ViewHolder(View itemView) {
            super(itemView);
            imgStudentImage = (ImageView) itemView.findViewById(R.id.imgStudentImage);
            txtNameStudent = (TextView) itemView.findViewById(R.id.txtNameStudent);
            txtStudenttCode = (TextView) itemView.findViewById(R.id.txtStudenttCode);
            txtError = (TextView) itemView.findViewById(R.id.txtError);
            imgInfoStudent = (ImageView) itemView.findViewById(R.id.imgInfoStudent);
            imgAddAction = (ImageView) itemView.findViewById(R.id.imgAddAction);
        }
    }
}
