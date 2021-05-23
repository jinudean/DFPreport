package com.example.dfpreport;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

public class PDFsFragment extends Fragment implements PDFHandler {

    private String pdf = null;
    MainViewModel mainViewModel;

    public PDFsFragment(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pdfs, container, false);
        RecyclerView pdfRecyclerView = view.findViewById(R.id.pdf_recyclerview);
        PDFAdapter pdfAdapter = new PDFAdapter(pdfRecyclerView.getContext(), this);
        pdfRecyclerView.setAdapter(pdfAdapter);
        pdfRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mainViewModel.getAllReports().observe(getViewLifecycleOwner(), pdfAdapter::setPDFs);

        Button sendPDF = view.findViewById(R.id.send_pdf);
        sendPDF.setOnClickListener(v -> {
            if (pdf != null) {
                Intent sendPDFEmail = new Intent(Intent.ACTION_SEND);
                sendPDFEmail.setData(Uri.parse("mailto:"));
                sendPDFEmail.setType("text/plain");
                sendPDFEmail.putExtra(android.content.Intent.EXTRA_SUBJECT, "Inspection Report PDF");
                File file = new File(pdf);
                Uri contentUri = FileProvider.getUriForFile(getContext(), "com.example.Foundations.file_provider", file);
                sendPDFEmail.putExtra(Intent.EXTRA_STREAM, contentUri);
                startActivity(sendPDFEmail);
            } else {
                Toast.makeText(getActivity(), "Please select a pdf", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void setPDF(String pdf) {
        this.pdf = pdf;
    }
}