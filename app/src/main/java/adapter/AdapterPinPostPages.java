package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joker.thanglong.R;

import java.util.List;

/**
 * Created by joker on 3/14/17.
 */

public class AdapterPinPostPages extends RecyclerView.Adapter<AdapterPinPostPages.ViewHolder> {
    Context context;
    List<String> list;
    public AdapterPinPostPages(Context context, List<String> list)
    {
        this.context = context;
        this.list=list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_pin_post_pages,parent,false);
        AdapterPinPostPages.ViewHolder viewHolder = new AdapterPinPostPages.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

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
