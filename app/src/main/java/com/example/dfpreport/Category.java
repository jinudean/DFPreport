package com.example.dfpreport;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category_table")
public class Category {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "categoryId")
    private Integer categoryId;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    public void setCategoryId(Integer id) {
        this.categoryId = id;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public Category(@NonNull String title) {
        this.title = title;
        this.categoryId = null;
    }
}