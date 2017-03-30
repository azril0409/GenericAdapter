package library.neetoffice.com.genericadapter.base;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Deo-chainmeans on 2015/8/4.
 */
public abstract class GenericAdapter<E, T> extends BaseAdapter implements GenericAdapterInterface<E> {
    private final Context context;
    private final int layoutId;
    private final ItemManager<E> manager;

    public GenericAdapter(Context context, Collection<E> items, int layoutId) {
        this.context = context;
        this.layoutId = layoutId;
        manager = new ItemManager<E>(items);
        refresh();
    }

    public final Context getContext() {
        return context;
    }

    public final LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(context);
    }

    @Override
    public final void addAll(Collection<E> items) {
        manager.addAll(items);
        refresh();
    }

    @Override
    public final void setAll(Collection<E> items) {
        manager.setAll(items);
        refresh();
    }

    @Override
    public final void add(E item) {
        manager.add(item);
        refresh();
    }

    @Override
    public final void set(int index, E item) {
        manager.set(index, item);
        refresh();
    }

    @Override
    public final void remove(E item) {
        manager.remove(item);
        refresh();
    }

    @Override
    public final E remove(int position) {
        final E e = manager.remove(position);
        refresh();
        return e;
    }

    @Override
    public final void clear() {
        manager.clear();
        refresh();
    }

    @Override
    public final void setFilter(Filter<E> filter) {
        manager.setFilter(filter);
        refresh();
    }

    @Override
    public void setSort(Comparator<E> sort) {
        manager.setSort(sort);
        refresh();
    }

    @Override
    public void refresh() {
        manager.refresh();
        notifyDataSetChanged();
    }

    @Override
    public final List<E> getItems() {
        return manager.getItems();
    }

    @Override
    public int getCount() {
        return manager.getItemCount();
    }

    @Override
    public E getItem(int position) {
        return manager.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T t = null;
        E e = getItem(position);
        if (e == null) {
            convertView = onCreateNoDateView(parent);
        } else if (convertView != null && convertView.getTag() != null) {
            t = (T) convertView.getTag();
        } else {
            convertView = getLayoutInflater().inflate(layoutId, null, false);
            t = onCreateTag(convertView);
            convertView.setTag(t);
        }
        if (t != null && e != null) {
            onBind(t, e);
        }
        return convertView;
    }

    public View onCreateNoDateView(ViewGroup parent) {
        return new View(getContext());
    }

    public abstract T onCreateTag(View convertView);

    public abstract void onBind(T tag, E item);
}
