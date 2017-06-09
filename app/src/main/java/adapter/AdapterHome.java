package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.joker.thanglong.CommentPostActivity;
import com.joker.thanglong.EditPostActivity;
import com.joker.thanglong.GroupActivity;
import com.joker.thanglong.ImagePostActivity;
import com.joker.thanglong.Interface.EndlessScrollListener;
import com.joker.thanglong.Model.PostModel;
import com.joker.thanglong.R;
import com.joker.thanglong.Ultil.CommentUlti;
import com.joker.thanglong.Ultil.SystemHelper;
import com.joker.thanglong.Ultil.VolleyHelper;
import com.joker.thanglong.Ultil.VolleySingleton;
import com.joker.thanglong.UserProfileActivity;
import com.like.LikeButton;
import com.like.OnLikeListener;

import de.hdodenhof.circleimageview.CircleImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Entity.EntityComment;
import Entity.EntityListLike;
import Entity.EntityStatus;

/**
 * Created by joker on 3/3/17.
 */

public class AdapterHome extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
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
    private ProgressDialog progressDialog;
    View view;
    PostModel postModel;
    CommentUlti commentUlti;
    EndlessScrollListener endlessScrollListener;
//    public AdapterHome(Context context, ArrayList<EntityStatus> items,Activity activity,VolleyHelper volleyHelper)
//    {
//        this.context = context;
//        this.items=items;
//        this.activity = activity;
//        this.volleyHelper = volleyHelper;
//
//    }

    public AdapterHome(Context context, ArrayList<EntityStatus> items,Activity activity)
    {
        this.context = context;
        this.items=items;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (viewType){
            case 1:
                view = inflater.inflate(R.layout.custom_recycleview_home_text,parent,false);
                ViewHolderText viewHolderText = new ViewHolderText(view);
                return viewHolderText;
            case 2:
                view = inflater.inflate(R.layout.custom_recycleview_home,parent,false);
                ViewHolderPhoto viewHolderPhoto = new ViewHolderPhoto(view);
                return viewHolderPhoto;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        progressDialog = new ProgressDialog(activity);
        Log.d("idStatus",items.get(position).getIdStatus()+"");
        commentUlti = new CommentUlti();
        holder.setIsRecyclable(false);
        switch (holder.getItemViewType()) {
            case 1:
                final ViewHolderText holderText = (ViewHolderText) holder;
                holderText.btnComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, CommentPostActivity.class);
                        intent.putExtra("idPost",items.get(position).getIdStatus());
                        intent.putExtra("likes",items.get(position).getNumberLike());
                        context.startActivity(intent);
                        activity.overridePendingTransition(R.anim.bottom_up,R.anim.bottom_down);
//                        commentUlti.onShowUp(view,activity,position);
//                        addEvent(position,view);
                    }
                });

                Glide.with(context).load(items.get(position).getAvatar())
                        .fitCenter()
                        .centerCrop()
                        .crossFade()
                        .into(holderText.imgAvatar);

                holderText.txtOption.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //creating a popup menu
                        PopupMenu popup = new PopupMenu(context, holderText.txtOption);
                        //inflating menu from xml resource
                        if (items.get(position).isCanEdit() == true){
                            popup.inflate(R.menu.itembaivietedit);
                        }else {
                            popup.inflate(R.menu.itembaiviet);
                        }

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
                                    case R.id.mn_edit_post:
                                        //handle menu3 click
                                        Intent intent = new Intent(activity, EditPostActivity.class);
                                        intent.putExtra("idPost",items.get(position).getIdStatus());
                                        activity.startActivity(intent);
                                        break;
                                    case R.id.mn_delete_post:
                                        PostModel postModel1 = new PostModel(activity);
                                        progressDialog = ProgressDialog.show(activity,"","Đang xóa bài viết",true);
                                        postModel1.DeletePost(items.get(position).getIdStatus(),new PostModel.VolleyCallBackCheck() {
                                            @Override
                                            public void onSuccess(boolean status) {
                                                progressDialog.dismiss();
                                                items.remove(position);
                                                notifyDataSetChanged();
                                            }
                                        });
                                        break;
                                }
                                return false;
                            }
                        });
                        //displaying the popup
                        popup.show();
                    }
                });
                holderText.txtFullNameFeed.setText(items.get(position).getNameId());
                holderText.txtTimePostStatus.setText(SystemHelper.getTimeAgo(items.get(position).getCreatedTime())+"");
                holderText.txtContentStatus.setText(items.get(position).getContent());
                holderText.txtFullNameFeed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, UserProfileActivity.class);
                        intent.putExtra("uId",items.get(position).getuId());
                        context.startActivity(intent);
                    }
                });

                Log.d("isLike",items.get(position).isLike()+"'");
                holderText.txtNumberLike.setText(items.get(position).getNumberLike()+"");
                if (items.get(position).getNumberComment() == 0)
                {
                    holderText.txtNumberComment.setVisibility(View.GONE);
                    holderText.imgTotalCmt.setVisibility(View.GONE);
                }else {
                    holderText.txtNumberComment.setText(items.get(position).getNumberComment()+"");
                }
                if (items.get(position).getNumberLike()==0)
                {
                    holderText.txtNumberLike.setVisibility(View.GONE);
                    holderText.imgTotalLike.setVisibility(View.GONE);
                }else {
                    holderText.txtNumberLike.setText(items.get(position).getNumberLike()+"");
                }

                if (items.get(position).isLike() == true){
                    holderText.btnLove.setLiked(true);
                }else {
                    holderText.btnLove.setLiked(false);
                }
                if (items.get(position).getPlace() != null){
                    holderText.txtSymbol.setVisibility(View.VISIBLE);
                    holderText.txtNameGroup.setVisibility(View.VISIBLE);
                    holderText.txtNameGroup.setText(items.get(position).getPlace().getName());
                    holderText.txtNameGroup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, GroupActivity.class);
                            intent.putExtra("idGroup",items.get(position).getPlace().getId());
                            context.startActivity(intent);
                        }
                    });
                }

                holderText.btnLove.setOnLikeListener(new OnLikeListener() {
                    @Override
                    public void liked(LikeButton likeButton) {
                        VolleySingleton.getInstance(activity).post(context,"posts/" + items.get(position).getIdStatus() + "/likes", null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = response.getJSONObject("data");
                                    holderText.txtNumberLike.setText(jsonObject.getString("likes"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }

                    @Override
                    public void unLiked(LikeButton likeButton) {
                        VolleySingleton.getInstance(activity).delete(context, "posts/" + items.get(position).getIdStatus() + "/likes", null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Like", "Like: + " + items.get(position).getIdStatus());
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = response.getJSONObject("data");
                                    holderText.txtNumberLike.setText(jsonObject.getString("likes"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                    }
                });
                break;
            case 2:
                final ViewHolderPhoto holderPhoto = (ViewHolderPhoto) holder;
//                holder.setIsRecyclable(false);
                holderPhoto.imgPostContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ImagePostActivity.class);
                        intent.putExtra("idStatus",items.get(position).getIdStatus());
                        context.startActivity(intent);
                    }
                });
                //Change avatar
                Glide.with(context).load(items.get(position).getAvatar())
                        .fitCenter()
                        .centerCrop()
                        .crossFade()
                        .into(holderPhoto.imgAvatar);

                if (items.get(position).getPlace() != null){
                    holderPhoto.txtSymbol.setVisibility(View.VISIBLE);
                    holderPhoto.txtNameGroup.setVisibility(View.VISIBLE);
                    holderPhoto.txtNameGroup.setText(items.get(position).getPlace().getName());
                    holderPhoto.txtNameGroup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, GroupActivity.class);
                            intent.putExtra("idGroup",items.get(position).getPlace().getId());
                            context.startActivity(intent);
                        }
                    });
                }

                holderPhoto.txtOption.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //creating a popup menu
                        PopupMenu popup = new PopupMenu(context, holderPhoto.txtOption);
                        //inflating menu from xml resource
                        if (items.get(position).isCanEdit() == true){
                            popup.inflate(R.menu.itembaivietedit);
                        }else {
                            popup.inflate(R.menu.itembaiviet);
                        }

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
                                    case R.id.mn_edit_post:
                                        //handle menu3 click
                                        Intent intent = new Intent(activity, EditPostActivity.class);
                                        intent.putExtra("idPost",items.get(position).getIdStatus());
                                        activity.startActivity(intent);
                                        break;
                                    case R.id.mn_delete_post:
                                        PostModel postModel1 = new PostModel(activity);
                                        progressDialog = ProgressDialog.show(activity,"","Đang xóa bài viết",true);
                                        postModel1.DeletePost(items.get(position).getIdStatus(),new PostModel.VolleyCallBackCheck() {
                                            @Override
                                            public void onSuccess(boolean status) {
                                                progressDialog.dismiss();
                                                items.remove(position);
                                                notifyDataSetChanged();
//                                                else {
//                                                    progressDialog.dismiss();
//                                                }
                                            }
                                        });
                                        break;
                                }
                                return false;
                            }
                        });
                        //displaying the popup
                        popup.show();
                    }
                });

                holderPhoto.txtFullNameFeed.setText(items.get(position).getNameId());
                holderPhoto.txtFullNameFeed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, UserProfileActivity.class);
                        intent.putExtra("uId",items.get(position).getuId());
                        context.startActivity(intent);
                    }
                });
                holderPhoto.txtTimePostStatus.setText(SystemHelper.getTimeAgo(items.get(position).getCreatedTime())+"");
                holderPhoto.txtContentStatus.setText(items.get(position).getContent());
//                Picasso.with(context).load(items.get(position).getImage())
//                        .fit().centerCrop()
//                        .into(holderPhoto.imgPostContent);
                Glide.with(context)
                        .load(items.get(position).getImage())
                        .fitCenter()
                        .centerCrop()
                        .placeholder(R.drawable.progress_loading) // can also be a drawable
//                        .error(R.mipmap.future_studio_launcher) // will be displayed if the image cannot be loaded
                        .crossFade()
                        .into(holderPhoto.imgPostContent);
                holderPhoto.txtNumberLike.setText(items.get(position).getNumberLike()+"");
                holderPhoto.btnComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        onShowPopup(view,activity,position);
                        Intent intent = new Intent(context, CommentPostActivity.class);
                        intent.putExtra("idPost",items.get(position).getIdStatus());
                        intent.putExtra("likes",items.get(position).getNumberLike());
                        context.startActivity(intent);
                        activity.overridePendingTransition(R.anim.bottom_up,R.anim.bottom_down);

                    }
                });

                if (items.get(position).getNumberComment() == 0)
                {
                    holderPhoto.txtNumberComment.setVisibility(View.GONE);
                    holderPhoto.imgTotalCmt.setVisibility(View.GONE);
                }else {
                    holderPhoto.txtNumberComment.setText(items.get(position).getNumberComment()+"");
                }
                if (items.get(position).getNumberLike()==0)
                {
                    holderPhoto.txtNumberLike.setVisibility(View.GONE);
                    holderPhoto.imgTotalLike.setVisibility(View.GONE);
                }else {
                    holderPhoto.txtNumberLike.setText(items.get(position).getNumberLike()+"");
                }

                if (items.get(position).isLike() == true){
                    holderPhoto.btnLove.setLiked(true);
                }else {
                    holderPhoto.btnLove.setLiked(false);
                }
                holderPhoto.btnLove.setOnLikeListener(new OnLikeListener() {
                    @Override
                    public void liked(LikeButton likeButton) {
                        VolleySingleton.getInstance(activity).post(context,"posts/" + items.get(position).getIdStatus() + "/likes", null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = response.getJSONObject("data");
                                    holderPhoto.txtNumberLike.setText(jsonObject.getString("likes"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }

                    @Override
                    public void unLiked(LikeButton likeButton) {
                        VolleySingleton.getInstance(activity).delete(context, "posts/" + items.get(position).getIdStatus() + "/likes", null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = response.getJSONObject("data");
                                    holderPhoto.txtNumberLike.setText(jsonObject.getString("likes"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                    }
                });
                break;
        }
    }

//    private void onShowPopup(View v, Activity activity, int position) {
//        LayoutInflater layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        // inflate the custom popup layout
//        View inflatedView = layoutInflater.inflate(R.layout.activity_comment_post, null,false);
//        addControl(inflatedView);
//        // find the ListView in the popup layout
////        ListView listView = (ListView)inflatedView.findViewById(R.id.commentsListView);
////        LinearLayout headerView = (LinearLayout)inflatedView.findViewById(R.id.headerLayout);
//        // get device size
//        Display display = activity.getWindowManager().getDefaultDisplay();
//        final Point size = new Point();
//        display.getSize(size);
////        mDeviceHeight = size.y;
//        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
//        int width = displayMetrics.widthPixels;
//        int height = displayMetrics.heightPixels;
//        // fill the data to the list items
////        setSimpleList(listView);
//        // set height depends on the device size
//        popWindow = new PopupWindow(inflatedView, width,height, true );
//        // set a background drawable with rounders corners
//        popWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.fb_popup_bg));
//        popWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
//        popWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
//        popWindow.setAnimationStyle(R.style.PopupAnimation);
//        popWindow.setOutsideTouchable(true);
//        // show the popup at bottom of the screen and set some margin at bottom ie,
//        popWindow.showAtLocation(v, Gravity.BOTTOM, 0,130);
//        //Event
//        addEvent(position,v);
//    }

//    private void addEvent(final int position, final View v) {
//        btnLikeList.setText(items.get(position).getNumberLike()+"");
//        btnLikeList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onShowPopupLikeList(v,activity,position);
//            }
//
//            private void onShowPopupLikeList(View v, Activity activity, int position) {
//                LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                // inflate the custom popup layout
//                View inflatedView = layoutInflater.inflate(R.layout.custom_like_list, null,false);
//                addControlListLike(inflatedView);
//                Display display = activity.getWindowManager().getDefaultDisplay();
//                final Point size = new Point();
//                display.getSize(size);
//                DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
//                int width = displayMetrics.widthPixels;
//                int height = displayMetrics.heightPixels;
//                popWindow = new PopupWindow(inflatedView, size.x,size.y, true );
//                // set a background drawable with rounders corners
//                popWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.fb_popup_bg));
//                popWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
//                popWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
//                popWindow.setAnimationStyle(R.style.PopupAnimationLike);
//                popWindow.setOutsideTouchable(true);
//                // show the popup at bottom of the screen and set some margin at bottom ie,
//                popWindow.showAtLocation(v, Gravity.FILL_VERTICAL, 0,130);
//                //Event
////                addEventListLike(position);
//            }
//
////            private void addEventListLike(final int position) {
////                itemsLikeList = new ArrayList<EntityListLike>();
//                volleyHelper.get("posts/" + items.get(position).getIdStatus() + "/likes", null, new Response.Listener<JSONObject>() {
////                    @Override
////                    public void onResponse(JSONObject response) {
////                        try {
////                            JSONArray jsonArray = response.getJSONArray("data");
////                            if (jsonArray.equals("null"))
////                            {
////                                System.exit(0);
////                            }else {
////                                for (int i = 0; i< jsonArray.length(); i++)
////                                {
////                                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
////                                    entityListLike = new EntityListLike();
////                                    entityListLike.setFull_name(jsonObject.getString("full_name"));
////                                    entityListLike.setUsername(jsonObject.getString("username"));
////                                    entityListLike.setFollow(jsonObject.getBoolean("is_followed"));
////                                    entityListLike.setId(jsonObject.getInt("id"));
////                                    itemsLikeList.add(entityListLike);
////                                    adapterLisLike.notifyDataSetChanged();
////                                    Log.d("full_name",entityListLike.getFull_name());
////                                }
////                            }
////                        } catch (JSONException e) {
////                            e.printStackTrace();
////                        }
////                    }
////                }, new Response.ErrorListener() {
////                    @Override
////                    public void onErrorResponse(VolleyError error) {
////
////                    }
////                });
////                adapterLisLike= new AdapterLisLike(activity,R.layout.custom_search_result,itemsLikeList);
////                lvListLike.setAdapter(adapterLisLike);
////                adapterLisLike.notifyDataSetChanged();
////            }
//
//            private void addControlListLike(View inflatedView) {
//                btnBackToComment = (Button) inflatedView.findViewById(R.id.btnBackToComment);
//                lvListLike = (ListView) inflatedView.findViewById(R.id.lvListLike);
//
//            }
//        });
//        //init Volley
////        final VolleyHelper volleyHelper = new VolleyHelper(context,context.getResources().getString(R.string.url));
//        //Get content of comment
//        getContentComment(position);
//        //Add comment
//        btnSubmmitComment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (TextUtils.isEmpty(btnCommentInput.getText().toString().trim()))
//                {
////                    btnCommentInput.setError("Mời bạn nhập nội dung");
//                }else {
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            addComment(position);
//                        }
//                    },300);
//                }
//
//            }
//        });
//
//    }
//
//    private void getContentComment(final int position) {
//        itemsComment = new ArrayList<>();
//        postModel = new PostModel(activity,items.get(position).getIdStatus());
//        postModel.getComment(0,new PostModel.VolleyCallbackComment() {
//            @Override
//            public void onSuccess(ArrayList<EntityComment> entityComments) {
//                itemsComment =entityComments;
//                loadMore();
//            }
//        });
//
//    }
//    private void loadMore() {
//        layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
//        adapterComment = new AdapterComment(context,itemsComment);
//        rcvComment.setLayoutManager(layoutManager);
//        rcvComment.setAdapter(adapterComment);
//        adapterComment.notifyDataSetChanged();
//        endlessScrollListener = new EndlessScrollListener((LinearLayoutManager) layoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                postModel.getComment(totalItemsCount,new PostModel.VolleyCallbackComment() {
//                    @Override
//                    public void onSuccess(ArrayList<EntityComment> entityComments) {
//                        itemsComment.addAll(entityComments);
//                        Log.d("ScrollComment",true+"");
//                        adapterComment.notifyDataSetChanged();
//                    }
//                });
//            }
//        };
//        rcvComment.setOnScrollListener(endlessScrollListener);
//    }
//    private void addComment(int position) {
//        String message = btnCommentInput.getText().toString();
//        postModel.addComment(new PostModel.VolleyCallBackJson() {
//            @Override
//            public void onSuccess(JSONObject jsonObject) throws JSONException {
//                entityComment = new EntityComment();
//                entityComment.setFull_name(ProfileInstance.getProfileInstance(activity).getProfile().getFull_name());
//                entityComment.setMessage(btnCommentInput.getText().toString());
//                entityComment.setCreated_at(SystemHelper.getTimeStamp());
//                itemsComment.add(entityComment);
////                adapterComment.notifyDataSetChanged();
//                btnCommentInput.setText("");
//            }
//        },message);
//    }

//    private void addControl(View inflatedView) {
//        btnLikeList = (Button) inflatedView.findViewById(R.id.btnStatusLike);
//        rcvComment = (RecyclerView) inflatedView.findViewById(R.id.rcvComment);
//        btnCommentInput = (EditText) inflatedView.findViewById(R.id.btnCommentInput);
//        btnSubmmitComment = (ImageButton) inflatedView.findViewById(R.id.btnSubmmitComment);
//
//    }

    @Override
    public int getItemCount() {
        if (items==null){
            return 0;
        }else {
            return items.size();
        }
    }

    //2.Ham nay chay thu 2, find view o day, nhan cai return o Buoc 1
    public class ViewHolderText extends RecyclerView.ViewHolder {
        private TextView txtFullNameFeed;
        private TextView txtTimePostStatus;
        private LikeButton btnLove;
        private TextView txtContentStatus;
        private TextView txtOption;
        private TextView txtNumberLike;
        private TextView txtNumberComment;
        private CircleImageView imgAvatar;
        private TextView txtSymbol;
        private TextView txtNameGroup;
        private ImageView imgTotalLike;
        private ImageView imgTotalCmt;

        Button btnComment;
        public ViewHolderText(View itemView) {
            super(itemView);
            txtFullNameFeed = (TextView) itemView.findViewById(R.id.txtFullNameFeed);
            txtTimePostStatus = (TextView) itemView.findViewById(R.id.txtTimePostStatus);
            txtContentStatus = (TextView) itemView.findViewById(R.id.txtContentStatus);
            btnLove = (LikeButton) itemView.findViewById(R.id.btnLove);
            btnComment = (Button) itemView.findViewById(R.id.btnComment);
            txtOption = (TextView) itemView.findViewById(R.id.txtOption);
            txtNumberLike = (TextView) itemView.findViewById(R.id.txtNumberLike);
            txtNumberComment = (TextView) itemView.findViewById(R.id.txtNumberComment);
            imgAvatar = (CircleImageView) itemView.findViewById(R.id.imgAvatar);
            txtSymbol = (TextView) itemView.findViewById(R.id.txtSymbol);
            txtNameGroup = (TextView) itemView.findViewById(R.id.txtNameGroup);
            imgTotalLike = (ImageView) itemView.findViewById(R.id.imgTotalLike);
            imgTotalCmt = (ImageView) itemView.findViewById(R.id.imgTotalCmt);

        }
    }

    public class ViewHolderPhoto extends RecyclerView.ViewHolder{
        private TextView txtFullNameFeed;
        private TextView txtTimePostStatus;
        private TextView txtContentStatus;
        private ImageView imgPostContent;
        private LikeButton btnLove;
        private TextView txtNumberLike;
        private TextView txtNumberComment;
        private Button btnComment;
        private TextView txtOption;
        private CircleImageView imgAvatar;
        private TextView txtSymbol;
        private TextView txtNameGroup;
        private ImageView imgTotalCmt;
        private ImageView imgTotalLike;

        public ViewHolderPhoto(View itemView) {
            super(itemView);
            imgTotalCmt = (ImageView) itemView.findViewById(R.id.imgTotalCmt);
            txtFullNameFeed = (TextView) itemView.findViewById(R.id.txtFullNameFeed);
            txtTimePostStatus = (TextView) itemView.findViewById(R.id.txtTimePostStatus);
            txtContentStatus = (TextView) itemView.findViewById(R.id.txtContentStatus);
            imgPostContent = (ImageView) itemView.findViewById(R.id.imgPostContent);
            txtOption = (TextView) itemView.findViewById(R.id.txtOption);
            btnLove = (LikeButton) itemView.findViewById(R.id.btnLove);
            txtNumberLike = (TextView) itemView.findViewById(R.id.txtNumberLike);
            txtNumberComment = (TextView) itemView.findViewById(R.id.txtNumberComment);
            btnComment = (Button) itemView.findViewById(R.id.btnComment);
            imgAvatar = (CircleImageView) itemView.findViewById(R.id.imgAvatar);
            txtSymbol = (TextView) itemView.findViewById(R.id.txtSymbol);
            txtNameGroup = (TextView) itemView.findViewById(R.id.txtNameGroup);
            imgTotalLike = (ImageView) itemView.findViewById(R.id.imgTotalLike);

        }
    }


    @Override
    public int getItemViewType(int position) {
//        Log.d("image",items.get(position).getImage().toString());
        int type = 1;
        if (items.get(position).getImage() == null){
            type = 1;
        }else {
            type = 2;
        }
        return type;
    }

}
