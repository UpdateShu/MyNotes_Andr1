package com.example.mynotes_andr1.ui.adapters;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes_andr1.ui.adapters.AdapterItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Fragment fragment;

    public ListAdapter(Fragment fragment) { this.fragment = fragment; }

    protected List<AdapterItem> data = new ArrayList<>();

    public void setData(Collection<AdapterItem> items) {
        data.clear();
        data.addAll(items);
    }

    public int addItem(AdapterItem item) {
        data.add(item);
        return data.size();
    }

    public int removeItem(String selectedId) {
        int index = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) instanceof AdapterItem && ((AdapterItem)data.get(i)).getId().equals(selectedId)) {//(selectedNote.getId())) {
                index = i;
                break;
            }
        }

        data.remove(index);
        return index;
    }

    public int updateItem(AdapterItem adapterItem) {
        int index = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) instanceof AdapterItem && ((AdapterItem)data.get(i)).getId().equals(adapterItem.getId())) {
                index = i;
                break;
            }
        }

        data.set(index, adapterItem);
        return index;
    }
}
