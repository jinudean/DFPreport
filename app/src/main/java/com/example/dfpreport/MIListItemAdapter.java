package com.example.dfpreport;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MIListItemAdapter extends RecyclerView.Adapter<MIListItemAdapter.MIListItemAdapterViewHolder> {

    private static final String TAG = "MIListItemAdapter";
    private List<ListItem> miListItems;
    ListItemHandler listItemHandler;

    @NonNull
    @Override
    public MIListItemAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MIListItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mi_listitem_item, parent, false);
        return new MIListItemAdapterViewHolder(MIListItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull MIListItemAdapterViewHolder holder, int position) {
        if (miListItems != null) {
            ListItem currentListItem = miListItems.get(position);
            ListItem selectedItem = listItemHandler.getCurrentListItem();
            if (selectedItem != null) {
                if (selectedItem.getListItemId().equals(currentListItem.getListItemId())) {
                    holder.MIListItemItemView.setBackgroundColor(Color.parseColor("#00BFFF"));
                } else {
                    holder.MIListItemItemView.setBackgroundColor(Color.TRANSPARENT);
                }
            }
            String testTitle = currentListItem.getNotes();
            holder.MIListItemItemView.setText(testTitle);
            holder.MIListItemItemView.setOnClickListener(v -> {
                if (selectedItem != null) {
                    if(selectedItem.getListItemId().equals(currentListItem.getListItemId())) {
                        listItemHandler.setListItem(null);
                    } else {
                        listItemHandler.setListItem(currentListItem);
                    }
                } else {
                    listItemHandler.setListItem(currentListItem);
                }
                notifyDataSetChanged();
            });
        }
    }

    @Override
    public int getItemCount() {
        if (miListItems != null) {
            return miListItems.size();
        } else return 0;
    }

    void setMiListItems(List<ListItem> listItems) {
        this.miListItems = listItems;
        notifyDataSetChanged();
    }

    static class MIListItemAdapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView MIListItemItemView;

        public MIListItemAdapterViewHolder(View itemView) {
            super(itemView);
            MIListItemItemView = itemView.findViewById(R.id.mi_list_item_item_title);
        }
    }

    public MIListItemAdapter(List<ListItem> miListItems, ListItemHandler listItemHandler) {
        this.miListItems = miListItems;
        this.listItemHandler = listItemHandler;
    }

}

