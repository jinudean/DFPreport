package com.example.dfpreport;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChecklistSubcategoryAdapter extends RecyclerView.Adapter<ChecklistSubcategoryAdapter.ChecklistSubcategoryViewHolder> {

    private static final String TAG = "ChecklistSubcategoryAda";
    private final List<SubCategory> subcategories;

    public ChecklistSubcategoryAdapter(List<SubCategory> subcategories) {
        this.subcategories = subcategories;
    }

    @NonNull
    @Override
    public ChecklistSubcategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View checklistSubCategoryViewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_subcategory_recyclerview, parent, false);
        return new ChecklistSubcategoryViewHolder(checklistSubCategoryViewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull ChecklistSubcategoryViewHolder holder, int position) {
        if (subcategories != null) {
            holder.checklistSubcategoryItemView.setText(subcategories.get(position).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        if (subcategories != null) {
            return subcategories.size();
        } else return 0;
    }

    static class ChecklistSubcategoryViewHolder extends RecyclerView.ViewHolder {

        private final TextView checklistSubcategoryItemView;

        public ChecklistSubcategoryViewHolder(View itemView) {
            super(itemView);
            checklistSubcategoryItemView = itemView.findViewById(R.id.cl_subcategory_rv_item_title);
        }
    }
}
