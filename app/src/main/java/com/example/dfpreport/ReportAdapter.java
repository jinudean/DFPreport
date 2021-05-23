package com.example.dfpreport;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {
    private static final String TAG = ReportAdapter.class.getSimpleName();
    private List<Report> reports;
    private final LayoutInflater inflater;
    private final SetReportHandler reportHandler;
    int selectedReport = -1;

    static class ReportViewHolder extends RecyclerView.ViewHolder {

        private final Button reportItemView;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            reportItemView = itemView.findViewById(R.id.reportItem);
        }
    }

    public ReportAdapter(Context context, SetReportHandler reportHandler) {
        this.reportHandler = reportHandler;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View reportLayoutView = inflater.inflate(R.layout.report_recyclerview_item, parent, false);
        return new ReportViewHolder(reportLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {

        if (selectedReport==position){
            holder.reportItemView.setBackgroundColor(Color.parseColor("#ffff00"));
        }
        else{
            holder.reportItemView.setBackgroundColor(Color.parseColor("#44bcda"));
        }
        if (reports != null) {
            Report current = reports.get(position);
            holder.reportItemView.setText(current.getFlightInfo());
            holder.reportItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedReport = position;
                    notifyDataSetChanged();
                    reportHandler.setCurrentReport(current);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (reports != null) {
            return reports.size();
        } else return 0;
    }

    void setReports(List<Report> reports) {
        this.reports = reports;
        notifyDataSetChanged();
    }
}
