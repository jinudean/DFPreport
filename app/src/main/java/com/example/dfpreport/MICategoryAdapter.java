package com.example.dfpreport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MICategoryAdapter extends RecyclerView.Adapter<MICategoryAdapter.MICategoryAdapterViewHolder> implements ListItemHandler {

    private static final String TAG = "miCategoryAdapter";
    private final LayoutInflater inflater;
    private final int currentReportId;
    private List<Category> miCategories;
    private List<SubCategory> miSubcategories;
    private List<ListItem> miListItems;
    MISubcategoryAdapter miSubcategoryAdapter;
    private final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    SweepCheckHandler sweepCheckHandler;
    MainViewModel mainViewModel;
    ListItem currentListItem = null;
    Context mContext;

    @NonNull
    @Override
    public MICategoryAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View miCategoryLayoutView = inflater.inflate(R.layout.mi_category_item, parent, false);
        return new MICategoryAdapterViewHolder(miCategoryLayoutView);
    }



    @Override
    public void onBindViewHolder(@NonNull MICategoryAdapterViewHolder holder, int position) {
        if (miCategories != null && miSubcategories != null) {
            Category currentCategory = miCategories.get(position);
            int categoryId = currentCategory.getCategoryId();
            holder.miCategoryItemView.setText(currentCategory.getTitle());
            List<SubCategory> filteredSubcatList = new ArrayList<>();
            List<ListItem> filteredListItemList = new ArrayList<>();
            for (int i = 0; i < miSubcategories.size(); i++) {
                if (miSubcategories.get(i).getCategoryId() == categoryId) {
                    filteredSubcatList.add(miSubcategories.get(i));
                    int subCategoryId = miSubcategories.get(i).getSubCategoryId();
                    if (miListItems != null) {
                        for (int y = 0; y < miListItems.size(); y++) {
                            if (miListItems.get(y).getCategoryId() == categoryId && subCategoryId == miListItems.get(y).getSubCategoryId() &&
                                    currentReportId == miListItems.get(y).getReportId()) {
                                filteredListItemList.add(miListItems.get(y));
                            }
                        }
                    }

                }
            }
            holder.addItem.setOnClickListener(v -> {
                sweepCheckHandler.showListItemDialog(mainViewModel, filteredSubcatList, null);
            });
            holder.editItem.setOnClickListener(v -> {
                if (currentListItem != null) {
                    sweepCheckHandler.showListItemDialog(mainViewModel, filteredSubcatList, currentListItem);
                    currentListItem = null;
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext,"Please select an item", Toast.LENGTH_SHORT).show();
                }
            });
            holder.deleteItem.setOnClickListener(v -> {
                sweepCheckHandler.showDeleteItemDialog(mainViewModel, currentListItem);
                currentListItem = null;
                notifyDataSetChanged();
            });
            if (filteredSubcatList.size() == 0) {
                holder.addItem.setVisibility(View.GONE);
                holder.editItem.setVisibility(View.GONE);
            } else {
                holder.addItem.setVisibility(View.VISIBLE);
                holder.editItem.setVisibility(View.VISIBLE);
            }
            if (currentListItem != null) {
                if (currentListItem.getCategoryId() == categoryId) {
                    holder.deleteItem.setVisibility(View.VISIBLE);
                } else {
                    holder.deleteItem.setVisibility(View.GONE);
                }
            } else {
                holder.deleteItem.setVisibility(View.GONE);
            }
            LinearLayoutManager layoutManager = new LinearLayoutManager(holder.miSubcategoryRecyclerView.getContext());
            miSubcategoryAdapter = new MISubcategoryAdapter(filteredSubcatList, filteredListItemList, this);
            holder.miSubcategoryRecyclerView.setLayoutManager(layoutManager);
            holder.miSubcategoryRecyclerView.setAdapter(miSubcategoryAdapter);
            holder.miSubcategoryRecyclerView.setRecycledViewPool(viewPool);
        }
    }



    @Override
    public int getItemCount() {
        if (miCategories != null) {
            return miCategories.size();
        } else return 0;
    }

    void setMiCategories(List<Category> categories) {
        this.miCategories = categories;
        sweepCheckHandler.setAllCategories(categories);
        notifyDataSetChanged();
    }

    void setMiListItems(List<ListItem> listItems) {
        this.miListItems = listItems;
        sweepCheckHandler.setAllListItems(listItems);
        notifyDataSetChanged();
    }

    void setMiSubcategories(List<SubCategory> subcategories) {
        this.miSubcategories = subcategories;
        sweepCheckHandler.setAllSubcategories(subcategories);
        notifyDataSetChanged();
    }

    @Override
    public void setListItem(ListItem item) {
        currentListItem = item;
        notifyDataSetChanged();
    }

    @Override
    public ListItem getCurrentListItem() {
        return currentListItem;
    }

    static class MICategoryAdapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView miCategoryItemView;
        private final RecyclerView miSubcategoryRecyclerView;
        Button addItem;
        Button editItem;
        Button deleteItem;

        public MICategoryAdapterViewHolder(View itemView) {
            super(itemView);
            miCategoryItemView = itemView.findViewById(R.id.mi_category_item_title);
            miSubcategoryRecyclerView = itemView.findViewById(R.id.mi_subcategory_recyclerview);
            addItem = itemView.findViewById(R.id.mi_add_item);
            editItem = itemView.findViewById(R.id.mi_edit_item);
            deleteItem = itemView.findViewById(R.id.delete_item);
            deleteItem.setVisibility(View.GONE);
        }
    }

    public MICategoryAdapter(Context context, SweepCheckHandler sweepCheckHandler, MainViewModel mainViewModel, int currentReportId) {
        inflater = LayoutInflater.from(context);
        this.mainViewModel = mainViewModel;
        this.sweepCheckHandler = sweepCheckHandler;
        this.currentReportId = currentReportId;
        mContext = context;
    }
}
