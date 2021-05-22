package com.example.dfpreport;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Report {
    @PrimaryKey(autoGenerate = true )
    @ColumnInfo(name ="reportId")
    private Integer reportId;


}
