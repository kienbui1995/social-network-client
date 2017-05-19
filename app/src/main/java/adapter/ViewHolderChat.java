package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.joker.thanglong.R;

import de.hdodenhof.circleimageview.CircleImageView;

import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

/**
 * Created by joker on 4/13/17.
 */

public class ViewHolderChat extends RecyclerView.ViewHolder {
    public EmojiconTextView txtMessageContent;
    public TextView txtDate;
    public CircleImageView imgAvartarChat;


    public ViewHolderChat(View itemView) {
        super(itemView);
        imgAvartarChat = (CircleImageView) itemView.findViewById(R.id.imgAvartarChat);
        txtMessageContent = (EmojiconTextView) itemView.findViewById(R.id.txtMessageContent);
        txtDate = (TextView) itemView.findViewById(R.id.txtDate);
    }
}
