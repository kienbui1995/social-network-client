package adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import Entity.EntityRoomChat;

/**
 * Created by joker on 2/24/17.
 */

public class AdapterListChat extends ArrayAdapter{
    Activity context;
    int resource;
    ArrayList<EntityRoomChat> objects;
    public AdapterListChat(Activity context, int resource, ArrayList objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource,null);
        return row;
    }


}
