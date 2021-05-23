package com.example.dfpreport;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainSweepCheckFragment extends Fragment implements SweepCheckHandler{

    private static final String TAG = "MainInspectionFragment";
    private final FragmentSwitcher fragmentSwitcher;
    private MainViewModel mainViewModel;
    private final int currentReportId;
    private List<Category> allCategories;
    private List<SubCategory> allSubcategories;
    private List<ListItem> allListItems;

    public MainSweepCheckFragment(FragmentSwitcher fragmentSwitcher, int currentReportId) {
        this.fragmentSwitcher = fragmentSwitcher;
        this.currentReportId = currentReportId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_sweepcheck, container, false);
        mainViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(MainViewModel.class);
        RecyclerView miCategoryRecyclerView = view.findViewById(R.id.mi_category_recyclerview);
        final MICategoryAdapter mCategoryAdapter = new MICategoryAdapter(miCategoryRecyclerView.getContext(), this, mainViewModel, currentReportId);
        miCategoryRecyclerView.setAdapter(mCategoryAdapter);
        miCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mainViewModel.getAllCategories().observe(getViewLifecycleOwner(), mCategoryAdapter::setMiCategories);
        mainViewModel.getAllSubcategories().observe(getViewLifecycleOwner(), mCategoryAdapter::setMiSubcategories);
        mainViewModel.getAllListItems().observe(getViewLifecycleOwner(), mCategoryAdapter::setMiListItems);
        Button addCategory = view.findViewById(R.id.mi_add_category);
        addCategory.setOnClickListener(v -> {
            showAddCategoryDialog(mainViewModel);
        });
        Button addSubcategory = view.findViewById(R.id.mi_add_subcategory);
        addSubcategory.setOnClickListener(v -> {
            showAddSubcategoryDialog(mainViewModel);
        });
        Button done = view.findViewById(R.id.mi_done_button);
        done.setOnClickListener(v -> {
            if (allListItems != null && getAllListItems().size() > 0) {
                Fragment fragment = new SummaryFragment(fragmentSwitcher, this);
                fragmentSwitcher.loadFragment(fragment);
            } else {
                Toast.makeText(getContext(), getString(R.string.no_items), Toast.LENGTH_LONG).show();
            }

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

    public void showDeleteItemDialog(MainViewModel mainViewModel, ListItem item) {
        DialogFragment dialog = new DeleteDialogFragment(mainViewModel, item);
        dialog.show(getParentFragmentManager(), "deleteItem");
    }

    @Override
    public void addPDF(String pdf) {
        mainViewModel.addPDF(currentReportId, pdf);
    }

    public void setAllCategories(List<Category> allCategories) {
        this.allCategories = allCategories;
    }

    public void setAllSubcategories(List<SubCategory> allSubcategories) {
        this.allSubcategories = allSubcategories;
    }
    public void setAllListItems(List<ListItem> allListItems) {
        this.allListItems = allListItems;
    }

    @Override
    public List<Category> getAllCategories() {
        return allCategories;
    }

    @Override
    public List<SubCategory> getAllSubcategories() {
        return allSubcategories;
    }

    @Override
    public List<ListItem> getAllListItems() {
        List<ListItem> filterListItems = new ArrayList<>();
        for (int i = 0; i < allListItems.size(); i++) {
            if (allListItems.get(i).getReportId() == currentReportId) {
                filterListItems.add(allListItems.get(i));
            }
        }
        filterListItems.sort(new Comparator() {

            public int compare(Object item1, Object item2) {

                Integer cat1 = ((ListItem) item1).getCategoryId();
                Integer cat2 = ((ListItem) item2).getCategoryId();
                int sComp = cat1.compareTo(cat2);

                if (sComp != 0) {
                    return sComp;
                }

                Integer sub1 = ((ListItem) item1).getSubCategoryId();
                Integer sub2 = ((ListItem) item2).getSubCategoryId();
                return sub1.compareTo(sub2);
            }
        });
        return filterListItems;
    }

    public void showListItemDialog(MainViewModel mainViewModel, List<SubCategory> allSubcategories, ListItem item) {
        DialogFragment dialog = new ListItemDialogFragment(mainViewModel, allSubcategories, currentReportId, item);
        dialog.show(getParentFragmentManager(), "listItem");
    }

}
