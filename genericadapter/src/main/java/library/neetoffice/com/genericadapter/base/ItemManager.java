package library.neetoffice.com.genericadapter.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Deo-chainmeans on 2017/3/27.
 */

public class ItemManager<E> implements GenericAdapterInterface<E> {
    public final ArrayList<Integer> indexs = new ArrayList<>();
    private ArrayList<E> originalItems;
    private Filter<E> filter = new Filter<E>() {

        @Override
        public boolean filter(E item) {
            return true;
        }
    };
    private final Comparator<Integer> indexSort = new Comparator<Integer>() {
        @Override
        public int compare(Integer lhs, Integer rhs) {
            if (sort != null) {
                synchronized (sort) {
                    final E elhs = originalItems.get(lhs);
                    final E erhs = originalItems.get(rhs);
                    return sort.compare(elhs, erhs);
                }
            } else {
                return lhs - rhs;
            }
        }
    };
    private Comparator<E> sort;
    private boolean enable = true;

    public ItemManager(Collection<E> originalItems) {
        this.originalItems = new ArrayList<>(originalItems);
    }

    public int getItemCount() {
        if (isNoData() && enable) {
            return 1;
        }
        return indexs.size();
    }

    public int getIndexCount() {
        return indexs.size();
    }

    @Override
    public boolean isNoData() {
        return indexs.isEmpty();
    }

    @Override
    public void setNoDataViewEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public void addAll(Collection<E> items) {
        try {
            final int originalSize = originalItems.size();
            final boolean b = originalItems.addAll(items);
        } catch (Exception e) {
        } finally {
        }
    }

    @Override
    public void setAll(Collection<E> items) {
        try {
            originalItems.clear();
            originalItems.addAll(items);
        } catch (Exception e) {
        } finally {
        }
    }

    @Override
    public void add(E item) {
        try {
            final boolean b = originalItems.add(item);
        } catch (Exception e) {
        } finally {
        }
    }

    @Override
    public void set(int index, E item) {
        try {
            originalItems.set(index, item);
        } catch (Exception e) {
        } finally {
        }
    }

    @Override
    public void remove(E item) {
        try {
            originalItems.remove(item);
        } catch (Exception e) {
        } finally {
        }
    }

    @Override
    public E remove(int position) {
        try {
            E e = originalItems.remove(position);
            return e;
        } catch (Exception e) {
        } finally {
        }
        return null;
    }

    @Override
    public void clear() {
        originalItems.clear();
        indexs.clear();
    }

    @Override
    public void setFilter(Filter<E> filter) {
        this.filter = filter;
    }

    @Override
    public void setSort(Comparator<E> sort) {
        this.sort = sort;
    }

    @Override
    public void refresh() {
        indexs.clear();
        for (int index = 0; index < originalItems.size(); index++) {
            final E originalItem = originalItems.get(index);
            if (filter.filter(originalItem)) {
                if (!indexs.contains(index)) {
                    indexs.add(index);
                }
            }
        }
        Collections.sort(indexs, indexSort);
    }

    @Override
    public List<E> getItems() {
        return originalItems;
    }

    @Override
    public E getItem(int position) {
        if (indexs.size() > position) {
            return originalItems.get(indexs.get(position));
        }
        return null;
    }
}
