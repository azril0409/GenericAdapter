package library.neetoffice.com.genericadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import library.neetoffice.com.genericadapter.base.Filter;
import library.neetoffice.com.genericadapter.base.GenericAdapterInterface;
import library.neetoffice.com.genericadapter.base.ItemManager;

/**
 * Created by Deo-chainmeans on 2015/6/5.
 */
public abstract class GenericRecyclerAdapter<E> extends RecyclerView.Adapter<ViewWrapper> implements GenericAdapterInterface<E> {
    protected static final int NODATA = Integer.MIN_VALUE;
    private final Context context;
    protected final ItemManager<E> manager;

    public GenericRecyclerAdapter(Context context) {
        this(context, new ArrayList<E>());
    }

    public GenericRecyclerAdapter(Context context, Collection<E> items) {
        this.context = context;
        manager = new ItemManager<E>(items);
    }

    public final Context getContext() {
        return context;
    }

    @Override
    public ViewWrapper onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NODATA) {
            final CellView<?> cellView = onCreateNoDataView(parent);
            final ViewWrapper viewWrapper = new ViewWrapper(cellView);
            return viewWrapper;
        } else {
            final CellView<?> cellView = onCreateItemView(parent, viewType);
            cellView.setGenericAdapter(this);
            final ViewWrapper viewWrapper = new ViewWrapper(cellView);
            viewWrapper.setIsRecyclable(cellView.recyclable);
            return viewWrapper;
        }
    }

    @Override
    public final int getItemViewType(int position) {
        if (manager.getIndexCount() > position) {
            return onGetItemViewType(position);
        }
        return NODATA;
    }

    public int onGetItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewWrapper viewWrapper, int position) {
        if (getItemViewType(position) != NODATA) {
            final E e = getItem(position);
            final CellView cellView = viewWrapper.getView();
            if (cellView != null) {
                cellView.onBindViewHolder(e);
                cellView.bind(e);
            }
        } else {
            final CellView cellView = viewWrapper.getView();
            if (cellView != null) {
                cellView.onBindViewHolder(null);
                cellView.bind(null);
            }
        }
        if (getItemClickable(position)) {
            viewWrapper.getView().onItemClickable(true);
        } else {
            viewWrapper.getView().onItemClickable(false);
        }
    }

    public abstract CellView<?> onCreateItemView(ViewGroup parent, int viewType);

    public CellView<?> onCreateNoDataView(ViewGroup parent) {
        return new DefaultNoDataView(getContext());
    }

    public boolean getItemClickable(int position) {
        return false;
    }

    @Override
    public int getItemCount() {
        return manager.getItemCount();
    }

    @Override
    public final void addAll(Collection<E> items) {
        beforeRefresh();
        manager.addAll(items);
        afterRefresh();
    }

    @Override
    public final void setAll(Collection<E> items) {
        beforeRefresh();
        manager.setAll(items);
        afterRefresh();
    }

    @Override
    public final void add(E item) {
        beforeRefresh();
        manager.add(item);
        afterRefresh();
    }

    @Override
    public final void set(int index, E item) {
        beforeRefresh();
        manager.set(index, item);
        afterRefresh();
    }

    @Override
    public final void remove(E item) {
        beforeRefresh();
        manager.remove(item);
        afterRefresh();
    }

    @Override
    public final E remove(int position) {
        beforeRefresh();
        final E e = manager.remove(position);
        afterRefresh();
        return e;
    }

    @Override
    public final void clear() {
        beforeRefresh();
        manager.clear();
        afterRefresh();
    }

    @Override
    public final void setFilter(Filter<E> filter) {
        manager.setFilter(filter);
        refresh();
    }

    @Override
    public final void setSort(Comparator<E> sort) {
        manager.setSort(sort);
        refresh();
    }

    private final void beforeRefresh() {
        if (manager.isNoData()) {
            notifyItemRemoved(0);
        } else {
            notifyItemRangeRemoved(0, manager.getIndexCount());
        }
    }

    private final void afterRefresh() {
        manager.refresh();
        notifyItemRangeInserted(0, manager.getItemCount());
    }

    @Override
    public void refresh() {
        beforeRefresh();
        afterRefresh();
    }

    @Override
    public final List<E> getItems() {
        return manager.getItems();
    }

    @Override
    public E getItem(int position) {
        return manager.getItem(position);
    }

    @Override
    public boolean isNoData() {
        return manager.isNoData();
    }

    @Override
    public void setNoDataViewEnable(boolean enable) {
        manager.setNoDataViewEnable(enable);
        refresh();
    }
}
