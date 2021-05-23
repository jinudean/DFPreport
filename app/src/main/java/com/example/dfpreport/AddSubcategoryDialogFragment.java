package com.example.dfpreport;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AddSubcategoryDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "AddSubcategoryDialogFragment";
    private final MainViewModel mainViewModel;
    private int categoryId;
    List<Category> allCategories;

    public AddSubcategoryDialogFragment(MainViewModel mainViewModel, List<Category> allCategories) {
        this.mainViewModel = mainViewModel;
        this.allCategories = allCategories;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogTheme);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_subcategory, null);
        Spinner spinner = (Spinner) view.findViewById(R.id.category_spinner);
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, allCategories);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        builder.setView(view)
                .setPositiveButton("Add Subcategory", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText editText = view.findViewById(R.id.asc_dialog_title_edit);
                        String subcategoryName = editText.getText().toString();
                        SubCategory subcategory = new SubCategory(categoryId, subcategoryName);
                        mainViewModel.insertSubCategory(subcategory);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddSubcategoryDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        categoryId = allCategories.get(position).getCategoryId();
        Log.d(TAG, "onItemSelected: " + categoryId);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
