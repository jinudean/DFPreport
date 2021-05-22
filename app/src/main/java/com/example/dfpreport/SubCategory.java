package com.example.dfpreport;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subcategory_table")
public class SubCategory {

    public void setSubCategoryId(Integer subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subCategoryId")
    private Integer subCategoryId;

    @NonNull
    @ColumnInfo(name = "categoryId")
    private final Integer categoryId;

    @NonNull
    @ColumnInfo(name = "title")
    private final String title;

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    @NonNull
    public Integer getCategoryId() {
        return categoryId;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String toString() {
        return title;
    }

    public SubCategory(@NonNull Integer categoryId, @NonNull String title) {
        this.categoryId = categoryId;
        this.title = title;
        this.subCategoryId = null;
    }
}