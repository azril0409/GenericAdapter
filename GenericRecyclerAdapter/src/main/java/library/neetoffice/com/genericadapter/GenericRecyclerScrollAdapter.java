package library.neetoffice.com.genericadapter;


import androidx.recyclerview.widget.RecyclerView;
import library.neetoffice.com.genericadapter.base.OnScrollCallBack;

/**
 * Created by Deo-chainmeans on 2016/10/15.
 */

public class GenericRecyclerScrollAdapter {
    public static void setScrollListener(RecyclerView recyclerView, OnScrollCallBack callBack) {
        recyclerView.addOnScrollListener(new GenericRecyclerScrollListener(callBack));
    }
}
