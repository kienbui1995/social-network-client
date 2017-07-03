package adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joker.thanglong.R;

import java.util.ArrayList;

/**
 * Created by joker on 6/24/17.
 */

public class AdapterDetailStudent extends RecyclerView.Adapter<AdapterDetailStudent.ViewHolder>{
    Context context;
    ArrayList<String> items;

    public AdapterDetailStudent(Context context, ArrayList<String> list)
    {
        this.context = context;
        this.items=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_vipham,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        Glide.with(context).load(items.get(position).getAvatar()).into(holder.imgAvavarMember);
        updateText(holder.txtNoiDungViPham,"22/6/2017 14:16",": Không đeo thẻ sinh viên");
        Log.d("aaaaaa",items.size()+"");
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNoiDungViPham;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNoiDungViPham = (TextView) itemView.findViewById(R.id.txtNoiDungViPham);
        }
    }
    private void updateText(TextView textView, String actor, String message) {
        SpannableString spannableString = new SpannableString(actor + message);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD),0,actor.length(),0);
        spannableString.setSpan(new RelativeSizeSpan(1.0f), actor.length(), spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }
}
