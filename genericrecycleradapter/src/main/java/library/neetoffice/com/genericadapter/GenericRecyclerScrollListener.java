package library.neetoffice.com.genericadapter;


import androidx.recyclerview.widget.RecyclerView;
import library.neetoffice.com.genericadapter.base.OnScrollCallBack;

/**
 * Created by Deo-chainmeans on 2016/10/15.
 */
class GenericRecyclerScrollListener extends RecyclerView.OnScrollListener {
    private final OnScrollCallBack callBack;

    GenericRecyclerScrollListener(OnScrollCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE && !recyclerView.canScrollHorizontally(1)) {
            if (callBack != null) {
                callBack.onScrollEnd();
            }
        }
    }
}
