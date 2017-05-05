package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.joker.hoclazada.MainActivity;
import com.joker.hoclazada.R;
import com.joker.hoclazada.Ultil.SystemHelper;
import com.joker.hoclazada.Ultil.VolleyHelper;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import Entity.EntityComment;
import Entity.EntityListLike;
import Entity.EntityStatus;

/**
 * Created by joker on 3/3/17.
 */

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ViewHolder>{
    Context context;
    Activity activity;
    private PopupWindow popWindow;
    ArrayList<EntityStatus> items;
    String picRandom;
    private RecyclerView rcvComment;
    private EditText btnCommentInput;
    private ImageButton btnSubmmitComment;
    VolleyHelper volleyHelper;
    ArrayList<EntityComment> itemsComment;
    AdapterComment adapterComment;
    EntityComment entityComment;
    RecyclerView.LayoutManager layoutManager;
    Button btnLikeList;
    AdapterLisLike adapterLisLike;
    ArrayList<EntityListLike> itemsLikeList;
    EntityListLike entityListLike;
    private Button btnBackToComment;
    private ListView lvListLike;

    public AdapterHome(Context context, ArrayList<EntityStatus> items,Activity activity,VolleyHelper volleyHelper)
    {
        this.context = context;
        this.items=items;
        this.activity = activity;
        this.volleyHelper = volleyHelper;
    }
    //1. Khoi tao layout
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Khoi tao layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.custom_recycleview_home_text,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            //Tra ve cai viewholder, va xuong buoc 2
            return viewHolder;
    }
    //3.Chay thu 3, Gan du lieu vao view
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShowPopup(view,activity,position);
            }
        });
        holder.txtOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.txtOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.itembaiviet);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.mn_report_post:
                                //handle menu1 click
                                Toast.makeText(context, "Reported", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.mn_save_post:
                                //handle menu2 click
                                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();

                                break;
//                            case R.id.menu3:
//                                //handle menu3 click
//                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
            }
        });
        holder.txtFullNameFeed.setText(items.get(position).getNameId());
        holder.txtTimePostStatus.setText(SystemHelper.getTimeAgo(items.get(position).getCreatedTime())+"");
        holder.txtContentStatus.setText(items.get(position).getContent());
        Log.d("isLike",items.get(position).isLike()+"'");
        holder.txtNumberLike.setText(items.get(position).getNumberLike()+"");
        if (items.get(position).getNumberComment() == 0)
        {
            holder.txtNumberComment.setVisibility(View.INVISIBLE);
        }else {
            holder.txtNumberComment.setText(items.get(position).getNumberComment()+"");
        }
        if (items.get(position).getNumberLike()==0)
        {
            holder.txtNumberLike.setVisibility(View.INVISIBLE);
        }else {
            holder.txtNumberLike.setText(items.get(position).getNumberLike()+"");
        }

        if (items.get(position).isLike() == true){
            holder.btnLove.setLiked(true);
        }else {
            holder.btnLove.setLiked(false);
        }
        holder.btnLove.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
               volleyHelper.postHeader("statuses/" + items.get(position).getIdStatus() + "/likes",
                       null, new Response.Listener<JSONObject>() {
                           @Override
                           public void onResponse(JSONObject response) {
                               int like = items.get(position).getNumberLike() + 1;
                               holder.txtNumberLike.setText((like+""));
                           }
                       }, new Response.ErrorListener() {
                           @Override
                           public void onErrorResponse(VolleyError error) {
                               int like = items.get(position).getNumberLike() - 1;
                               holder.txtNumberLike.setText((like)+"");
                           }
                       });
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                volleyHelper.delete("statuses/" + items.get(position).getIdStatus() + "/likes",
                        null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("is_Like",response.toString());
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

            }
        });
    }

    private void onShowPopup(View v, Activity activity, int position) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflate the custom popup layout
        View inflatedView = layoutInflater.inflate(R.layout.activity_comment_post, null,false);
        addControl(inflatedView);
        // find the ListView in the popup layout
//        ListView listView = (ListView)inflatedView.findViewById(R.id.commentsListView);
//        LinearLayout headerView = (LinearLayout)inflatedView.findViewById(R.id.headerLayout);
        // get device size
        Display display = activity.getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
//        mDeviceHeight = size.y;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        // fill the data to the list items
//        setSimpleList(listView);
        // set height depends on the device size
        popWindow = new PopupWindow(inflatedView, width-120,height, true );
        // set a background drawable with rounders corners
        popWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.fb_popup_bg));
        popWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popWindow.setAnimationStyle(R.style.PopupAnimation);
        popWindow.setOutsideTouchable(true);
        // show the popup at bottom of the screen and set some margin at bottom ie,
        popWindow.showAtLocation(v, Gravity.BOTTOM, 0,130);
        //Event
        addEvent(position,v);
    }

    private void addEvent(final int position, final View v) {
        btnLikeList.setText(items.get(position).getNumberLike()+"");
        btnLikeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShowPopupLikeList(v,activity,position);
            }

            private void onShowPopupLikeList(View v, Activity activity, int position) {
                LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                // inflate the custom popup layout
                View inflatedView = layoutInflater.inflate(R.layout.custom_like_list, null,false);
                addControlListLike(inflatedView);
                // find the ListView in the popup layout
//        ListView listView = (ListView)inflatedView.findViewById(R.id.commentsListView);
//        LinearLayout headerView = (LinearLayout)inflatedView.findViewById(R.id.headerLayout);
                // get device size
                Display display = activity.getWindowManager().getDefaultDisplay();
                final Point size = new Point();
                display.getSize(size);
//        mDeviceHeight = size.y;
                DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                int width = displayMetrics.widthPixels;
                int height = displayMetrics.heightPixels;
                // fill the data to the list items
//        setSimpleList(listView);
                // set height depends on the device size
                popWindow = new PopupWindow(inflatedView, size.x-120,size.y, true );
                // set a background drawable with rounders corners
                popWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.fb_popup_bg));
                popWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                popWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                popWindow.setAnimationStyle(R.style.PopupAnimationLike);
                popWindow.setOutsideTouchable(true);
                // show the popup at bottom of the screen and set some margin at bottom ie,
                popWindow.showAtLocation(v, Gravity.FILL_VERTICAL, 0,130);
                //Event
                addEventListLike(position);
            }

            private void addEventListLike(final int position) {
                itemsLikeList = new ArrayList<EntityListLike>();
                volleyHelper.get("statuses/" + items.get(position).getIdStatus() + "/likes", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            if (jsonArray.equals("null"))
                            {
                                System.exit(0);
                            }else {
                                for (int i = 0; i< jsonArray.length(); i++)
                                {
                                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                    entityListLike = new EntityListLike();
                                    entityListLike.setFull_name(jsonObject.getString("full_name"));
                                    entityListLike.setUsername(jsonObject.getString("username"));
                                    entityListLike.setFollow(jsonObject.getBoolean("is_followed"));
                                    entityListLike.setId(jsonObject.getInt("id"));
                                    itemsLikeList.add(entityListLike);
                                    adapterLisLike.notifyDataSetChanged();
                                    Log.d("full_name",entityListLike.getFull_name());
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                adapterLisLike= new AdapterLisLike(activity,R.layout.custom_search_result,itemsLikeList);
                lvListLike.setAdapter(adapterLisLike);
                adapterLisLike.notifyDataSetChanged();
            }

            private void addControlListLike(View inflatedView) {
                btnBackToComment = (Button) inflatedView.findViewById(R.id.btnBackToComment);
                lvListLike = (ListView) inflatedView.findViewById(R.id.lvListLike);

            }
        });
        //init Volley
        final VolleyHelper volleyHelper = new VolleyHelper(context,context.getResources().getString(R.string.url));
        //Add comment
        btnSubmmitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(btnCommentInput.getText().toString().trim()))
                {
                    btnCommentInput.setError("Mời bạn nhập nội dung");
                    return;
                }else {
                    addComment(volleyHelper,position);
                }

            }
        });
        //Get content of comment
        getContentComment(position);

    }

    private void getContentComment(final int position) {
        itemsComment = new ArrayList<>();
        volleyHelper.get("statuses/" + items.get(position).getIdStatus() + "/comments?sort=created_at", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i< jsonArray.length();i++){
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                entityComment = new EntityComment();
                                entityComment.setuId(Integer.parseInt(jsonObject.getString("userid")));
                                entityComment.setFull_name(jsonObject.getString("full_name"));
                                entityComment.setIdComment(Integer.parseInt(jsonObject.getString("id")));
                                entityComment.setMessage(jsonObject.getString("message"));
                                entityComment.setUsername(jsonObject.getString("username"));
                                entityComment.setCreated_at(Long.parseLong(jsonObject.getString("created_at")));
                                itemsComment.add(entityComment);
                                adapterComment.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        adapterComment = new AdapterComment(context,itemsComment);
        rcvComment.setLayoutManager(layoutManager);
        rcvComment.setAdapter(adapterComment);
        adapterComment.notifyDataSetChanged();
    }

    private void addComment(VolleyHelper volleyHelper, int position) {
        HashMap<String,String> parrams = new HashMap<>();
        parrams.put("message",btnCommentInput.getText().toString().trim());
        volleyHelper.postHeader("statuses/" + items.get(position).getIdStatus() + "/comments", new JSONObject(parrams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("postCommentSucees",response.toString());
                        entityComment = new EntityComment();
                        entityComment.setFull_name(MainActivity.entityUserProfile.getFull_name());
                        entityComment.setMessage(btnCommentInput.getText().toString());
                        entityComment.setCreated_at(SystemHelper.getTimeStamp());
                        itemsComment.add(entityComment);
                        adapterComment.notifyDataSetChanged();
                        btnCommentInput.setText("");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("postCommentSucees",VolleyHelper.checkErrorCode(error)+"");

                    }
                });
    }

    private void addControl(View inflatedView) {
        btnLikeList = (Button) inflatedView.findViewById(R.id.btnStatusLike);
        rcvComment = (RecyclerView) inflatedView.findViewById(R.id.rcvComment);
        btnCommentInput = (EditText) inflatedView.findViewById(R.id.btnCommentInput);
        btnSubmmitComment = (ImageButton) inflatedView.findViewById(R.id.btnSubmmitComment);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    //2.Ham nay chay thu 2, find view o day, nhan cai return o Buoc 1
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtFullNameFeed;
        private TextView txtTimePostStatus;
        private LikeButton btnLove;
        private TextView txtContentStatus;
        private TextView txtOption;
        private TextView txtNumberLike;
        private TextView txtNumberComment;


        Button btnComment;
        public ViewHolder(View itemView) {
            super(itemView);
            txtFullNameFeed = (TextView) itemView.findViewById(R.id.txtFullNameFeed);
            txtTimePostStatus = (TextView) itemView.findViewById(R.id.txtTimePostStatus);
            txtContentStatus = (TextView) itemView.findViewById(R.id.txtContentStatus);
            btnLove = (LikeButton) itemView.findViewById(R.id.btnLove);
            btnComment = (Button) itemView.findViewById(R.id.btnComment);
            txtOption = (TextView) itemView.findViewById(R.id.txtOption);
            txtNumberLike = (TextView) itemView.findViewById(R.id.txtNumberLike);
            txtNumberComment = (TextView) itemView.findViewById(R.id.txtNumberComment);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position %2 * 2;
    }
}
