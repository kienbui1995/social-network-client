package Model;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.joker.hoclazada.Interface.OnLoadMoreListener;

/**
 * Created by joker on 4/30/17.
 */

public class LoadMore extends RecyclerView.OnScrollListener {
    int itemFirst = 0;
    int totalItem = 0;
    int itemLoadMore = 4;
    OnLoadMoreListener onLoadMoreListener;
    RecyclerView.LayoutManager layoutManager;
    public LoadMore(RecyclerView.LayoutManager layoutManager,OnLoadMoreListener onLoadMoreListener) {
        this.layoutManager = layoutManager;
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        totalItem = layoutManager.getItemCount();
        itemFirst = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();

        Log.d("totalItem",totalItem + " " + (itemFirst+itemLoadMore));
        if (totalItem <= (itemFirst+ itemLoadMore))
        {
            Log.d("totalItem",totalItem + " " + (itemFirst+itemLoadMore));
            onLoadMoreListener.onLoadMore(totalItem);
        }
    }
}
