package library.neetoffice.com.genericadapter;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import library.neetoffice.com.genericadapter.base.GenericAdapterInterface;

/**
 * Created by Deo-chainmeans on 2015/6/8.
 */
public abstract class CellView<E> extends FrameLayout {
    public static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
    public static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
    protected E data;
    boolean recyclable = true;
    private GenericAdapterInterface<E> genericAdapter;

    public CellView(Context context) {
        super(context);
        setLayoutParams(WRAP_CONTENT, WRAP_CONTENT);
    }

    public CellView(Context context, boolean recyclable) {
        this(context);
        this.recyclable = recyclable;
    }


    public void setLayoutParams(int width, int height) {
        setLayoutParams(new ViewGroup.LayoutParams(width, height));
    }

    public void onItemClickable(boolean clickable) {
        if (clickable) {
            setOnClickListener(new Listener(data));
        } else {
            setOnClickListener(null);
        }
    }

    public void onItemClick(View cellView, E data) {
    }

    public final GenericAdapterInterface<E> getGenericAdapter() {
        return genericAdapter;
    }

    final void setGenericAdapter(GenericAdapterInterface<E> genericAdapter) {
        this.genericAdapter = genericAdapter;
    }

    public final E getData() {
        return data;
    }

    final void onBindViewHolder(E data) {
        this.data = data;
    }

    public void bind(E e) {
    }

    public String getString(@StringRes int resId) {
        return getContext().getString(resId);
    }

    public String getString(@StringRes int resId, Object... formatArgs) {
        return getContext().getString(resId, formatArgs);
    }

    private class Listener implements OnClickListener {
        private E data;

        private Listener(E data) {
            this.data = data;
        }

        @Override
        public void onClick(View v) {
            onItemClick(v, data);
        }
    }
}
