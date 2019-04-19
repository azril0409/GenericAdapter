package app.neetoffice.com.genericadaptersample;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Deo on 2016/3/16.
 */
public class RecyclerGridActivity extends RecyclerListActivity {

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
    }
}
