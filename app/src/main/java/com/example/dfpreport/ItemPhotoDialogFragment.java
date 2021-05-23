package com.example.dfpreport;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ItemPhotoDialogFragment extends DialogFragment {

    String photoPath;

    public ItemPhotoDialogFragment(String photoPath) {
        this.photoPath = photoPath;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogTheme);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.full_photo_dialog, null);
        ImageView photoImage = view.findViewById(R.id.full_photo_item);
        if (photoImage != null) {
            File file = new File(photoPath);
            Picasso.get().load(file).into(photoImage);
        }
        builder.setView(view)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ItemPhotoDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
