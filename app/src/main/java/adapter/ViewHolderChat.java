package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.joker.hoclazada.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by joker on 4/13/17.
 */

public class ViewHolderChat extends RecyclerView.ViewHolder {
    public TextView txtMessageContent;
    public TextView txtDate;
    public CircleImageView imgAvartarChat;


    public ViewHolderChat(View itemView) {
        super(itemView);
        imgAvartarChat = (CircleImageView) itemView.findViewById(R.id.imgAvartarChat);
        txtMessageContent = (TextView) itemView.findViewById(R.id.txtMessageContent);
        txtDate = (TextView) itemView.findViewById(R.id.txtDate);
    }
}
