package com.example.dfpreport;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "report_table")
public class Report {
    @PrimaryKey(autoGenerate = true )
    @ColumnInfo(name ="reportId")
    private Integer reportId;

    @NonNull
    @ColumnInfo(name = "profileId")
    private Integer profileId;

    @ColumnInfo(name = "inBound")
    private String inBound;

    @ColumnInfo(name = "inflight")
    private String inflight;

    @ColumnInfo(name = "outBound")
    private String outBound;

    @ColumnInfo(name = "outflight")
    private String outflight;

    @ColumnInfo(name = "pdf")
    private String pdf;

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    @NonNull
    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(@NonNull Integer profileId) {
        this.profileId = profileId;
    }

    public String getInBound() {
        return inBound;
    }

    public void setInBound(String inBound) {
        this.inBound = inBound;
    }

    public String getOutBound() {
        return outBound;
    }

    public void setOutBound(String outBound) {
        this.outBound = outBound;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getInflight() {
        return inflight;
    }

    public void setInflight(String inflight) {
        this.inflight = inflight;
    }

    public String getOutflight() {
        return outflight;
    }

    public void setOutflight(String outflight) {
        this.outflight = outflight;
    }

    public String getFlightInfo() {
        StringBuilder flight = new StringBuilder();
        flight.append("Inbound : [");
        flight.append(getInBound());
        flight.append("] + ");
        flight.append(getInflight());
        flight.append(" <- SEA -> [");
        flight.append(getOutBound());
        flight.append("] + ");
        flight.append(getOutflight());
        return flight.toString();
    }

    public Report(@NonNull Integer profileId, String inBound, String inflight, String outBound, String outflight, String pdf){
        this.reportId = null;
        this.profileId = profileId;
        this.inBound = inBound;
        this.inflight = inflight;
        this.outBound = outBound;
        this.outflight = outflight;
        this.pdf = pdf;
    }
}
