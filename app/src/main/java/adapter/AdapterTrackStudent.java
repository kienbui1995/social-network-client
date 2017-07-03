package adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joker.thanglong.R;
import com.joker.thanglong.Ultil.DialogUtil;

import java.util.ArrayList;

/**
 * Created by joker on 6/24/17.
 */

public class AdapterTrackStudent extends RecyclerView.Adapter<AdapterTrackStudent.ViewHolder>{
    Activity context;
    ArrayList<String> items;

    public AdapterTrackStudent(Activity context, ArrayList<String> list)
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
    public void onBindViewHolder(ViewHolder holder, int position) {
//        Glide.with(context).load(items.get(position).getAvatar()).into(holder.imgAvavarMember);
        holder.imgInfoStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.showInfoStudent(context);
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
