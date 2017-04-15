package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joker.hoclazada.R;

import java.util.List;

/**
 * Created by joker on 4/8/17.
 */

public class AdapterDiscoverGroup extends RecyclerView.Adapter<AdapterDiscoverGroup.ViewHolder> {
    Context context;
    List<String> list;
    public AdapterDiscoverGroup(Context context, List<String> list)
    {
        this.context = context;
        this.list=list;
    }
    @Override
    public AdapterDiscoverGroup.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_rycycleview_post_group,parent,false);
        AdapterDiscoverGroup.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterDiscoverGroup.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
