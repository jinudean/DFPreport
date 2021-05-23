package com.example.dfpreport;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ListItemDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {
    private final MainViewModel mainViewModel;
    List<SubCategory> allSubcategories;
    int reportId;
    int categoryId;
    int subcategoryId;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView itemPhoto;
    String photoPath = null;
    Uri photoUri;
    ListItem listItem = null;
    private static final String TAG = "ListItemDialogFragment";

    public ListItemDialogFragment(MainViewModel mainViewModel, List<SubCategory> allSubcategories, int reportId, ListItem item) {
        this.mainViewModel = mainViewModel;
        this.allSubcategories = allSubcategories;
        this.reportId = reportId;
        this.listItem = item;
    }

    @NotNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogTheme);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_list_item, null);
        Spinner spinner = (Spinner) view.findViewById(R.id.subcategory_spinner);
        Button addPhoto = view.findViewById(R.id.add_photo);
        EditText editText = view.findViewById(R.id.list_item_notes);
        itemPhoto = view.findViewById(R.id.list_item_photo_dialog);
        ArrayAdapter<SubCategory> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, allSubcategories);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        if (listItem != null) {
            editText.setText(listItem.getNotes());
            SubCategory listItemSubcategory = null;
            for (int i = 0; i < allSubcategories.size(); i++) {
                if (allSubcategories.get(i).getSubCategoryId().equals(listItem.getSubCategoryId())) {
                    listItemSubcategory = allSubcategories.get(i);
                    break;
                }
            }
            int spinnerPosition = adapter.getPosition(listItemSubcategory);
            spinner.setSelection(spinnerPosition);
            subcategoryId = listItemSubcategory.getSubCategoryId();
            if (listItem.getPhotos() != null) {
                photoPath = listItem.getPhotos();
                File photoFile = new File(photoPath);
                photoUri = FileProvider.getUriForFile(getActivity(), "com.example.Foundations.file_provider", photoFile);
                bitmapSetup(itemPhoto, photoUri);
            }
        }
        addPhoto.setOnClickListener(v -> {
            takePicture();
        });
        itemPhoto.setOnClickListener(v -> {
            showFullPhoto(photoPath);
        });

        builder.setView(view)
                .setPositiveButton("Submit Item", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String notes = editText.getText().toString();
                        if (listItem != null) {
                            mainViewModel.updateListItem(listItem.getListItemId(), notes, photoPath, subcategoryId);
                        } else {
                            ListItem item = new ListItem(reportId, categoryId, subcategoryId, notes, photoPath);
                            mainViewModel.insertListItem(item);
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ListItemDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        categoryId = allSubcategories.get(position).getCategoryId();
        subcategoryId = allSubcategories.get(position).getSubCategoryId();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showFullPhoto(String path) {
        DialogFragment dialog = new ItemPhotoDialogFragment(path);
        dialog.show(getParentFragmentManager(), "fullPhoto");
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String file_name = System.currentTimeMillis() + ".jpg";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        photoPath = storageDir.getAbsolutePath() + file_name;
        File photoFile = new File(photoPath);
        photoUri = FileProvider.getUriForFile(getActivity(), "com.example.Foundations.file_provider", photoFile);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (BitmapFactory.decodeFile(photoUri.getPath()) != null) {
                bitmapSetup(itemPhoto, photoUri);
            }
        } else {
            Bundle extras = data.getExtras();
            itemPhoto.setImageBitmap((Bitmap) extras.get("data"));
        }

    }

    static void bitmapSetup(ImageView image, Uri uri) {
        Bitmap imageBitmap = BitmapFactory.decodeFile(uri.getPath());
        imageBitmap = resizeBitmap(200, imageBitmap);
        image.setImageBitmap(imageBitmap);
    }

    public static Bitmap resizeBitmap(int targetWidth, Bitmap source){
        double ratio = (double)targetWidth/(double)source.getWidth();
        int targetHeight = (int)(source.getHeight()*ratio);
        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);

        if (result != source){
            source.recycle();
        }
        return result;
    }
}
