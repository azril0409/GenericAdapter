package library.neetoffice.com.genericadapter.base;

import android.content.Context;
import android.widget.AbsListView;

import java.util.Collection;

/**
 * Created by Deo-chainmeans on 2015/8/4.
 */
public abstract class GenericScrollAdapter {

    public static void setScrollListener(AbsListView view, OnScrollCallBack callBack) {
        view.setOnScrollListener(new GenericScrollListener(callBack));
    }
}
