package library.neetoffice.com.genericadapter;


import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Deo-chainmeans on 2015/6/5.
 */
public class ViewWrapper extends RecyclerView.ViewHolder {
    CellView view;

    public ViewWrapper(CellView itemView) {
        super(itemView);
        view = itemView;
    }

    public CellView getView() {
        return view;
    }
}
