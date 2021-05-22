package com.example.dfpreport;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "airplaneinfo_table")
public class AirplaneInfo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "airplaneInfoId")
    private Integer airplaneInfoId;

    @ColumnInfo(name = "fleetInfo")
    private String fleetInfo;

    @NonNull
    @ColumnInfo(name = "reportId")
    private Integer reportId;

    @NonNull
    @ColumnInfo(name = "tailNumber")
    private String tailNumber;

    @ColumnInfo(name = "swPartNumber")
    private String swPartNumber;

    @ColumnInfo(name = "mediaVersion")
    private String mediaVersion;

    public Integer getAirplaneInfoId() {
        return airplaneInfoId;
    }

    public void setAirplaneInfoId(Integer airplaneInfoId) {
        this.airplaneInfoId = airplaneInfoId;
    }

    public String getFleetInfo() {
        return fleetInfo;
    }

    public void setFleedInfo(String fleetInfo) {
        this.fleetInfo = fleetInfo;
    }

    @NonNull
    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(@NonNull Integer reportId) {
        this.reportId = reportId;
    }

    @NonNull
    public String getTailNumber() {
        return tailNumber;
    }

    public void setTailNumber(@NonNull String tailNumber) {
        this.tailNumber = tailNumber;
    }

    public String getSwPartNumber() {
        return swPartNumber;
    }

    public void setSwPartNumber(String swPartNumber) {
        this.swPartNumber = swPartNumber;
    }

    public String getMediaVersion() {
        return mediaVersion;
    }

    public void setMediaVersion(String mediaVersion) {
        this.mediaVersion = mediaVersion;
    }

    public AirplaneInfo(@NonNull String tailNumber, String fleetInfo, String swPartNumber, String mediaVersion, @NonNull Integer reportId) {
        this.airplaneInfoId = null;
        this.reportId = reportId;
        this.tailNumber = tailNumber;
        this.fleetInfo = fleetInfo;
        this.swPartNumber =swPartNumber;
        this.mediaVersion = mediaVersion;
    }
}
