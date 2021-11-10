package library.neetoffice.com.genericadapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Deo on 2016/3/8.
 */
public class NormalHeaderDecoration<E> extends RecyclerView.ItemDecoration {
    private Map<Long, RecyclerView.ViewHolder> mHeaderCache;

    protected StickyHeaderAdapter mAdapter;

    private boolean mRenderInline;

    private boolean isNoDateHasHeader;

    /**
     * @param adapter the sticky header adapter to use
     */
    public NormalHeaderDecoration(StickyHeaderAdapter adapter) {
        this(adapter, false);
    }

    public NormalHeaderDecoration(StickyHeaderAdapter adapter, boolean renderInline) {
        this(adapter, renderInline, false);
    }

    public NormalHeaderDecoration(StickyHeaderAdapter adapter, boolean renderInline, boolean isNoDateHasHeader) {
        mAdapter = adapter;
        mHeaderCache = new HashMap<>();
        mRenderInline = renderInline;
        this.isNoDateHasHeader = isNoDateHasHeader;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int headerHeight = 0;
        if (position != RecyclerView.NO_POSITION && hasHeader(parent, position)) {
            View header = getHeader(parent, position).itemView;
            headerHeight = getHeaderHeightForLayout(header);
        }
        outRect.set(0, headerHeight, 0, 0);
    }

    /**
     * Clears the header view cache. Headers will be recreated and
     * rebound on list scroll after this method has been called.
     */
    public void clearHeaderCache() {
        mHeaderCache.clear();
    }

    private boolean hasHeader(RecyclerView parent, int position) {
        if (position == 0) {
            return parent.getAdapter().getItemViewType(position) == GenericRecyclerAdapter.NODATA ? isNoDateHasHeader : true;
        }
        final RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final int spanCount = gridLayoutManager.getSpanCount();
            if (position < spanCount) {
                return true;
            }
            final int previous = position - spanCount;
            return mAdapter.getHeaderId(position) != mAdapter.getHeaderId(previous);
        } else {
            final int previous = position - 1;
            return mAdapter.getHeaderId(position) != mAdapter.getHeaderId(previous);
        }
    }

    private class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView) {
            super(itemView);
        }
    }

    protected RecyclerView.ViewHolder getHeader(RecyclerView parent, int position) {
        final long key = mAdapter.getHeaderId(position);

        if (mHeaderCache.containsKey(key)) {
            return mHeaderCache.get(key);
        } else {
            final View header = mAdapter.onCreateHeaderViewHolder(parent);
            final Holder holder = new Holder(header);
            //noinspection unchecked
            mAdapter.onBindHeaderViewHolder(header, position);

            final int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
            final int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);
            final int width = header.getLayoutParams() != null ? header.getLayoutParams().width : ViewGroup.LayoutParams.MATCH_PARENT;
            final int height = header.getLayoutParams() != null ? header.getLayoutParams().height : ViewGroup.LayoutParams.WRAP_CONTENT;
            final int childWidth = ViewGroup.getChildMeasureSpec(widthSpec, parent.getPaddingLeft() + parent.getPaddingRight(), width);
            final int childHeight = ViewGroup.getChildMeasureSpec(heightSpec, parent.getPaddingTop() + parent.getPaddingBottom(), height);

            header.measure(childWidth, childHeight);
            header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());
            mHeaderCache.put(key, holder);

            return holder;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int count = parent.getChildCount();

        for (int layoutPos = 0; layoutPos < count; layoutPos++) {
            final View child = parent.getChildAt(layoutPos);

            final int adapterPos = parent.getChildAdapterPosition(child);
            if (adapterPos != RecyclerView.NO_POSITION && ( hasHeader(parent, adapterPos))) {
                View header = getHeader(parent, adapterPos).itemView;
                c.save();
                final int left = 0;//child.getLeft();
                int top = getHeaderTop(parent, child, header, adapterPos, layoutPos);
                c.translate(left, top);
                header.draw(c);
                c.restore();
            }
        }
    }

    protected int getHeaderTop(RecyclerView parent, View child, View header, int adapterPos, int layoutPos) {
        int headerHeight = getHeaderHeightForLayout(header);
        int top = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            top = ((int) child.getY()) - headerHeight;
        } else {
            top = child.getTop() - headerHeight;
        }

        return top;
    }

    protected int getHeaderHeightForLayout(View header) {
        return mRenderInline ? 0 : header.getHeight();
    }

}
