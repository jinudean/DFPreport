package com.example.dfpreport;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import static android.widget.TextView.BufferType.EDITABLE;

public class AirplaneInfoFragment extends Fragment {
    private final FragmentSwitcher fragmentSwitcher;
    private int currentReportId;
    private EditText fleetinfo;
    private EditText tailNumber;
    private EditText swPartNumber;
    private EditText mediaversion;
    private AirplaneInfo airplaneInfo;
    private Button submit;


    private static final String TAG = AirplaneInfoFragment.class.getSimpleName();

    public AirplaneInfoFragment(FragmentSwitcher fragmentSwitcher) {
        this.fragmentSwitcher = fragmentSwitcher;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_airplaneinfo, container, false);
        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        fleetinfo = view.findViewById(R.id.ac_fleetinfo_input);
        tailNumber = view.findViewById(R.id.ac_tailnumber_input);
        swPartNumber = view.findViewById(R.id.ac_swpart_input);
        mediaversion = view.findViewById(R.id.ac_media_input);

        submit = view.findViewById(R.id.sd_submit);
        submit.setOnClickListener(v -> {
            checkInputs();
            mainViewModel.updateAirplaneInfo(currentReportId,
                    fleetinfo.getText().toString(),
                    tailNumber.getText().toString(),
                    swPartNumber.getText().toString(),
                    mediaversion.getText().toString()

            );
            Fragment fragment = new MainSweepCheckFragment(fragmentSwitcher, currentReportId);
            fragmentSwitcher.loadFragment(fragment);
        });

        if (fragmentSwitcher.getCurrentReport() != null) {
            Log.d(TAG, "onCreateView: loading airplaneinfo");
            currentReportId = fragmentSwitcher.getCurrentReport().getReportId();
            airplaneInfo = mainViewModel.getAirplaneInfo(currentReportId).get(0);
            fleetinfo.setText(airplaneInfo.getFleetInfo(), EDITABLE);
            tailNumber.setText(airplaneInfo.getTailNumber(), EDITABLE);
            swPartNumber.setText(airplaneInfo.getSwPartNumber(), EDITABLE);
            mediaversion.setText(airplaneInfo.getMediaVersion(), EDITABLE);

        } else {
            Report report = mainViewModel.getNewReport();
            currentReportId = report.getReportId();
            airplaneInfo = new AirplaneInfo("N/A","A339","-009", "APR",currentReportId);
            mainViewModel.insertAirplaneInfo(airplaneInfo);
            fragmentSwitcher.setCurrentReport(report);
        }
        return view;
    }

    void checkInputs() {
        if (fleetinfo.getText().toString().equals("")) {
            fleetinfo.setText("N/A");
        }
        if (tailNumber.getText().toString().equals("")) {
            tailNumber.setText("N/A");
        }
        if (swPartNumber.getText().toString().equals("")) {
            swPartNumber.setText("Not Factory version");
        }
        if (mediaversion.getText().toString().equals("")) {
            mediaversion.setText("Current Month");
        }

    }
}
