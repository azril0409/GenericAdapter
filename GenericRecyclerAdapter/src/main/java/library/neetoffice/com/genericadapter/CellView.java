package library.neetoffice.com.genericadapter;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import library.neetoffice.com.genericadapter.base.GenericAdapterInterface;

/**
 * Created by Deo-chainmeans on 2015/6/8.
 */
public abstract class CellView<E> extends FrameLayout {
    public static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
    public static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
    final boolean recyclable;
    protected E data;
    private GenericAdapterInterface<E> genericAdapter;
    private OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            onItemClick(v, data);
        }
    };
    private OnLongClickListener longClickListener = new OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            return CellView.this.onLongClick(v, data);
        }
    };

    public CellView(Context context) {
        super(context);
        setLayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        recyclable = true;
    }

    public CellView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        recyclable = true;
    }

    public CellView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        recyclable = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CellView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setLayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        recyclable = true;
    }

    public CellView(Context context, boolean recyclable) {
        super(context);
        setLayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        this.recyclable = recyclable;
    }



    public void setLayoutParams(int width, int height) {
        setLayoutParams(new ViewGroup.LayoutParams(width, height));
    }

    public void onItemClickable(boolean clickable) {
        if (clickable) {
            setOnClickListener(clickListener);
            setOnLongClickListener(longClickListener);
        } else {
            setOnClickListener(null);
            setOnLongClickListener(null);
        }
    }

    public void onItemClick(View cellView, E data) {
    }

    public boolean onLongClick(View v, E data) {
        return true;
    }

    public final GenericAdapterInterface<E> getGenericAdapter() {
        return genericAdapter;
    }

    final void setGenericAdapter(GenericAdapterInterface genericAdapter) {
        this.genericAdapter = genericAdapter;
    }

    final void onBindViewHolder(E data) {
        this.data = data;
    }

    public void bind(E e) {
    }

    public final E getData() {
        return data;
    }

    public String getString(@StringRes int resId) {
        return getContext().getString(resId);
    }

    public String getString(@StringRes int resId, Object... formatArgs) {
        return getContext().getString(resId, formatArgs);
    }

    public final boolean isRecyclable() {
        return recyclable;
    }
}
