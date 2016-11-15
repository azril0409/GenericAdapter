package library.neetoffice.com.genericadapter.base;

import android.widget.AbsListView;

/**
 * Created by Deo-chainmeans on 2016/10/15.
 */

class GenericScrollListener implements AbsListView.OnScrollListener {
    private int firstVisibleItem;
    private int visibleItemCount;
    private int totalItemCount;
    private final OnScrollCallBack callBack;

    GenericScrollListener(OnScrollCallBack callBack) {
        this.callBack = callBack;
    }

    public void onScroll(AbsListView view, int f, int v, int t) {
        firstVisibleItem = f;
        visibleItemCount = v;
        totalItemCount = t;
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == GenericScrollListener.SCROLL_STATE_IDLE & (firstVisibleItem + visibleItemCount + 1 >= totalItemCount)) {
            callBack.onScrollEnd();
        }
    }
}
