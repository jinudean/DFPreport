package com.example.dfpreport;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class DeleteDialogFragment extends DialogFragment {
    private final MainViewModel mainViewModel;
    ListItem listItem;

    public DeleteDialogFragment(MainViewModel mainViewModel, ListItem listItem) {
        this.mainViewModel = mainViewModel;
        this.listItem = listItem;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogTheme);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_delete_item, null);

        builder.setView(view)
                .setPositiveButton("Yes, Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mainViewModel.deleteListItem(listItem.getListItemId());
                        if (listItem.getPhotos() != null) {
                            File photoFile = new File(listItem.getPhotos());
                            if (photoFile.delete()) {
                                Toast.makeText(getContext(), "Item and photo deleted", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DeleteDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
