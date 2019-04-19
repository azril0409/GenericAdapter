package app.neetoffice.com.genericadaptersample;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;
import library.neetoffice.com.genericadapter.CellView;
import library.neetoffice.com.genericadapter.GenericStickyHeaderRecyclerAdapter;
import library.neetoffice.com.genericadapter.base.Filter;

/**
 * Created by Deo on 2016/3/17.
 */
public class RecyclerHeaderGridActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, TextWatcher, Filter<String> {
    private Comparator<String> desc = new Comparator<String>() {
        @Override
        public int compare(String lhs, String rhs) {
            return Integer.valueOf(lhs) - Integer.valueOf(rhs);
        }
    };
    private Comparator<String> asc = new Comparator<String>() {
        @Override
        public int compare(String lhs, String rhs) {
            return Integer.valueOf(rhs) - Integer.valueOf(lhs);
        }
    };
    private Adapter adapter;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        EditText editText = (EditText) findViewById(R.id.editText);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        toolbar.inflateMenu(R.menu.menu_list);
        toolbar.setOnMenuItemClickListener(this);
        adapter = new Adapter(this, new ArrayList<String>());
        adapter.setFilter(this);
        recyclerView.setLayoutManager(adapter.getGridLayoutManager(2, GridLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new FadeInUpAnimator());
        recyclerView.setAdapter(adapter);
        editText.addTextChangedListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.desc) {
            if (adapter != null) {
                adapter.setSort(desc);
                return true;
            }
        } else if (item.getItemId() == R.id.asc) {
            if (adapter != null) {
                adapter.setSort(asc);
                return true;
            }
        }else if (item.getItemId() == R.id.clear) {
            if (adapter != null) {
                adapter.clear();
                return true;
            }
        }else if (item.getItemId() == R.id.add) {
            if (adapter != null) {
                adapter.setAll(Arrays.asList(getResources().getStringArray(R.array.items)));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean filter(String item) {
        if (text == null) {
            return true;
        }
        return item.contains(text);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        text = s.toString();
        adapter.refresh();
    }

    private static class SrtingCell extends CellView<String> {
        private TextView textView;

        public SrtingCell(Context context) {
            super(context);
            inflate(context, R.layout.cell_string, this);
            textView = (TextView) findViewById(R.id.textView);
        }

        @Override
        public void bind(String s) {
            super.bind(s);
            textView.setText(s);
        }
    }

    private class Adapter extends GenericStickyHeaderRecyclerAdapter<String, String> {

        public Adapter(Context context, Collection<String> items) {
            super(context, items);
        }

        @Override
        public CellView<String> onCreateHeaderViewHolder(ViewGroup parent) {
            SrtingCell cell = new SrtingCell(getContext());
            cell.setBackgroundColor(Color.BLUE);
            return cell;
        }

        @Override
        public long getHeaderId(int position) {
            return getItem(position).charAt(0);
        }

        @Override
        public String getHeader(long headerId) {
            return String.valueOf((char) headerId);
        }

        @Override
        public CellView<String> onCreateItemView(ViewGroup parent, int viewType) {
            SrtingCell cell = new SrtingCell(getContext());
            return cell;
        }
    }
}
