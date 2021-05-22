package com.example.dfpreport;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Photo {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "photoId")
    private Integer photoId;

    @NonNull
    @ColumnInfo(name = "listItemId")
    private Integer listItemId;

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    @NonNull
    @ColumnInfo(name = "photoPath")
    private String photoPath;

    @NonNull
    @ColumnInfo(name = "reportId")
    private Integer reportId;

    public Integer getReportId() { return reportId; }

    public Integer getPhotoId() {
        return photoId;
    }

    @NonNull
    public Integer getListItemId() {
        return listItemId;
    }

    @NonNull
    public String getPhotoPath() {
        return photoPath;
    }

    public Photo(@NonNull Integer listItemId, @NonNull String photoPath, @NonNull Integer reportId) {
        this.listItemId = listItemId;
        this.photoPath = photoPath;
        this.photoId = null;
        this.reportId = reportId;
    }


}
