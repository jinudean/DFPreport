package com.example.dfpreport;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PDFAdapter extends RecyclerView.Adapter<PDFAdapter.PDFViewHolder>{

    private static final String TAG = "PDFAdapter";
    private List<Report> reports;
    private final LayoutInflater inflater;
    private PDFHandler pdfHandler;
    int selected = -1;

    public PDFAdapter(Context context, PDFHandler pdfHandler) {
        inflater = LayoutInflater.from(context);
        this.pdfHandler = pdfHandler;
    }

    @NonNull
    @Override
    public PDFViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View pdfLayoutView = inflater.inflate(R.layout.pdf_recyclerview_item, parent, false);
        return new PDFViewHolder(pdfLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull PDFViewHolder holder, int position) {
        if (reports != null) {
            if (selected==position){
                holder.pdfItemView.setBackgroundColor(Color.parseColor("#ffff00"));
            } else{
                holder.pdfItemView.setBackgroundColor(Color.parseColor("#44bcda"));
            }
            Report current = reports.get(position);
            holder.pdfItemView.setText(current.getFlightInfo());
            holder.pdfItemView.setOnClickListener(v -> {
                selected = position;
                notifyDataSetChanged();
                pdfHandler.setPDF(current.getPdf());
            });
        }
    }

    @Override
    public int getItemCount() {
        if (reports != null) {
            return reports.size();
        } else return 0;
    }

    void setPDFs(List<Report> reports) {
        List<Report> filtered = new ArrayList<>();
        for (int i = 0; i < reports.size(); i++) {
            if (reports.get(i).getPdf() != null) {
                filtered.add(reports.get(i));
            }
        }
        this.reports = filtered;
        notifyDataSetChanged();
    }

    static class PDFViewHolder extends RecyclerView.ViewHolder {

        private final Button pdfItemView;

        public PDFViewHolder(@NonNull View itemView) {
            super(itemView);
            pdfItemView = itemView.findViewById(R.id.pdfItem);
        }
    }
}
