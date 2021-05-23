package com.example.dfpreport;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.io.File;

public class NewSweepCheck extends Fragment{
    private static final String TAG = NewSweepCheck.class.getSimpleName();
    private EditText inboundFlight;
    private EditText outboundFlight;
    private EditText inbound;
    private EditText outbound;
    private Button createButton;
    private MainViewModel mainViewModel;
    private Report currentReport;
    private boolean updateReport;
    private final FragmentSwitcher fragmentSwitcher;

    public NewSweepCheck(FragmentSwitcher fragmentSwitcher) { this.fragmentSwitcher = fragmentSwitcher; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        updateReport = false;

        View view = inflater.inflate(R.layout.start_sweepcheck, container, false);

        mainViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(MainViewModel.class);

        inbound = view.findViewById(R.id.ns_inbound);
        inboundFlight = view.findViewById(R.id.ns_inboundNumber);
        outbound = view.findViewById(R.id.ns_outbound);
        outboundFlight = view.findViewById(R.id.ns_outboundNumber);

        createButton = view.findViewById(R.id.ni_create_report);

        createButton.setOnClickListener(v -> { createReport(); });

        if (fragmentSwitcher.getCurrentReport() != null) {
            currentReport = fragmentSwitcher.getCurrentReport();
            inbound.setText(currentReport.getInBound());
            inboundFlight.setText(currentReport.getInflight());
            outbound.setText(currentReport.getOutBound());
            outboundFlight.setText(currentReport.getOutflight());



            updateReport = true;
        }


        return view;
    }

    public void createReport() {
        String flightin = inbound.getText().toString();
        String flightout = outbound.getText().toString();
        String flightinNumber = inboundFlight.getText().toString();
        String flightoutNumber = outboundFlight.getText().toString();

        Report report = new Report(fragmentSwitcher.getProfile().getProfileId(), flightin, flightinNumber, flightout, flightoutNumber, null);

        if (updateReport) {
            mainViewModel.updateReport(currentReport.getReportId(), flightin, flightinNumber, flightout, flightoutNumber);
        } else {
            mainViewModel.insertReport(report);
        }
        Fragment fragment = new AirplaneInfoFragment(fragmentSwitcher);
        fragmentSwitcher.loadFragment(fragment);


    }


}
