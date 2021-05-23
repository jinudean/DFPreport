package com.example.dfpreport;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

public class AddCategoryDialogFragment extends DialogFragment {

    private final MainViewModel mainViewModel;

    public AddCategoryDialogFragment(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogTheme);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_category, null);

        builder.setView(view)
                .setPositiveButton("Add Category", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText editText = view.findViewById(R.id.ac_dialog_title_edit);
                        String categoryName = editText.getText().toString();
                        Category category = new Category(categoryName);
                        mainViewModel.insertCategory(category);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddCategoryDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}