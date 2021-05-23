package com.example.dfpreport;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CheckListsFragment extends Fragment implements ChecklistHandler{

    private List<Category> allCategories;

    public CheckListsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_lists, container, false);
        RecyclerView checklistCategoryRecyclerView = view.findViewById(R.id.cl_category_recyclerview);
        MainViewModel mainViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(MainViewModel.class);
        final ChecklistCategoryAdapter checklistCategoryAdapter = new ChecklistCategoryAdapter(checklistCategoryRecyclerView.getContext(), this, mainViewModel);
        checklistCategoryRecyclerView.setAdapter(checklistCategoryAdapter);
        checklistCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mainViewModel.getAllCategories().observe(getViewLifecycleOwner(), checklistCategoryAdapter::setCategories);
        mainViewModel.getAllSubcategories().observe(getViewLifecycleOwner(), checklistCategoryAdapter::setSubcategories);
        Button addCategory = view.findViewById(R.id.cl_add_category);
        addCategory.setOnClickListener(v -> {
            showAddCategoryDialog(mainViewModel);
        });
        Button addSubcategory = view.findViewById(R.id.cl_add_subcategory);
        addSubcategory.setOnClickListener(v -> {
            showAddSubcategoryDialog(mainViewModel);
        });

        return view;
    }

    private void showAddCategoryDialog(MainViewModel mainViewModel) {
        DialogFragment dialog = new AddCategoryDialogFragment(mainViewModel);
        dialog.show(getParentFragmentManager(), "addCategory");
    }

    public void showAddSubcategoryDialog(MainViewModel mainViewModel) {
        DialogFragment dialog = new AddSubcategoryDialogFragment(mainViewModel, allCategories);
        dialog.show(getParentFragmentManager(), "addSubcategory");
    }

    @Override
    public void setAllCategories(List<Category> allCategories) {
        this.allCategories = allCategories;
    }

}