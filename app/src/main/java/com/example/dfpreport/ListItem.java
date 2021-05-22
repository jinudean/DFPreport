package com.example.dfpreport;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "listitem_table")
public class ListItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "listItemId")
    private Integer listItemId;

    @NonNull
    @ColumnInfo(name = "reportId")
    private final Integer reportId;

    @NonNull
    @ColumnInfo(name = "categoryId")
    private final Integer categoryId;

    @ColumnInfo(name = "subCategoryId")
    private final Integer subCategoryId;

    @ColumnInfo(name = "notes")
    private String notes;

    @ColumnInfo(name = "photos")
    private String photos = null;

    public Integer getListItemId() {
        return listItemId;
    }

    @NonNull
    public Integer getReportId() {
        return reportId;
    }

    @NonNull
    public Integer getCategoryId() {
        return categoryId;
    }

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public String getNotes() {
        return notes;
    }

    public String getPhotos() {
        return photos;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public void setListItemId(Integer listItemId) {
        this.listItemId = listItemId;
    }

    public String toString() { return getCategoryId() + " " + getSubCategoryId() + " " + getNotes();}

    public ListItem(@NonNull Integer reportId, @NonNull Integer categoryId, Integer subCategoryId, String notes, String photos) {
        this.reportId = reportId;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.notes = notes;
        this.photos = photos;
        this.listItemId = null;
    }
}
