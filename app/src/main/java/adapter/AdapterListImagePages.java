package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.joker.hoclazada.ImagePostActivity;
import com.joker.hoclazada.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by joker on 3/15/17.
 */

public class AdapterListImagePages extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final int cellSize;
    private final List<String> photos;
    private boolean lockedAnimations = false;
    private int lastAnimatedItem = -1;
    private static int screenWidth = 0;
    private static final int PHOTO_ANIMATION_DELAY = 600;
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();

    public AdapterListImagePages(Context context,List<String> photos) {
        this.context = context;
        this.photos = photos;
        this.cellSize = getScreenWidth(context) / 3;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_photos, parent, false);
        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        layoutParams.height =cellSize;
        layoutParams.width = cellSize;
        layoutParams.setFullSpan(false);
        view.setLayoutParams(layoutParams);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindPhoto((PhotoViewHolder) holder, position);
        ((PhotoViewHolder) holder).ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ImagePostActivity.class));
            }
        });
    }

    private void bindPhoto(final PhotoViewHolder holder, int position) {
        Picasso.with(context)
                .load(photos.get(position))
                .resize(cellSize, cellSize)
                .centerCrop()
                .into(holder.ivPhoto, new Callback() {
                    @Override
                    public void onSuccess() {
                        animatePhoto(holder);
                    }

                    @Override
                    public void onError() {

                    }
                });
        if (lastAnimatedItem < position) lastAnimatedItem = position;
    }

    private void animatePhoto(PhotoViewHolder viewHolder) {
        if (!lockedAnimations) {
            if (lastAnimatedItem == viewHolder.getPosition()) {
                setLockedAnimations(true);
            }

            long animationDelay = PHOTO_ANIMATION_DELAY + viewHolder.getPosition() * 30;

            viewHolder.flRoot.setScaleY(0);
            viewHolder.flRoot.setScaleX(0);

            viewHolder.flRoot.animate()
                    .scaleY(1)
                    .scaleX(1)
                    .setDuration(200)
                    .setInterpolator(INTERPOLATOR)
                    .setStartDelay(animationDelay)
                    .start();
        }
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder {
       FrameLayout flRoot;
        ImageView ivPhoto;
        public PhotoViewHolder(View view) {
            super(view);
            flRoot = (FrameLayout) view.findViewById(R.id.flRoot);
            ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto);

        }
    }
    public void setLockedAnimations(boolean lockedAnimations) {
        this.lockedAnimations = lockedAnimations;
    }
    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }
}
