package com.example.dfpreport;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MISubcategoryAdapter extends RecyclerView.Adapter<MISubcategoryAdapter.MISubcategoryAdapterViewHolder> {

    private static final String TAG = "MISubcategoryAdapter";
    private List<SubCategory> miSubcategories;
    private final List<ListItem> miListItems;
    private final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    ListItemHandler listItemHandler;

    @NonNull
    @Override
    public MISubcategoryAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MISubcategoryLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mi_subcategory_item, parent, false);
        return new MISubcategoryAdapterViewHolder(MISubcategoryLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull MISubcategoryAdapterViewHolder holder, int position) {
        if (miSubcategories != null) {
            SubCategory currentSubcategory = miSubcategories.get(position);
            int subcategoryId = currentSubcategory.getSubCategoryId();
            holder.MISubcategoryItemView.setText(currentSubcategory.getTitle());
            List<ListItem> filteredListItemList = new ArrayList<>();
            for (int i = 0; i < miListItems.size(); i++) {
                if (miListItems.get(i).getSubCategoryId() == subcategoryId) {
                    filteredListItemList.add(miListItems.get(i));
                }
            }
            LinearLayoutManager layoutManager = new LinearLayoutManager(holder.miListItemRecyclerView.getContext());
            MIListItemAdapter miListItemAdapter = new MIListItemAdapter(filteredListItemList, listItemHandler);
            holder.miListItemRecyclerView.setLayoutManager(layoutManager);
            holder.miListItemRecyclerView.setAdapter(miListItemAdapter);
            holder.miListItemRecyclerView.setRecycledViewPool(viewPool);
        }
    }

    @Override
    public int getItemCount() {
        if (miSubcategories != null) {
            return miSubcategories.size();
        } else return 0;
    }

    void setMISubcategories(List<SubCategory> subcategories) {
        this.miSubcategories = subcategories;
        notifyDataSetChanged();
    }

    static class MISubcategoryAdapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView MISubcategoryItemView;
        private final RecyclerView miListItemRecyclerView;

        public MISubcategoryAdapterViewHolder(View itemView) {
            super(itemView);
            MISubcategoryItemView = itemView.findViewById(R.id.mi_subcategory_item_title);
            miListItemRecyclerView = itemView.findViewById(R.id.mi_listitem_recyclerview);
        }
    }

    public MISubcategoryAdapter(List<SubCategory> miSubcategories, List<ListItem> miListItems, ListItemHandler listItemHandler
    ) {
        this.miSubcategories = miSubcategories;
        this.miListItems = miListItems;
        this.listItemHandler = listItemHandler;
    }

}

