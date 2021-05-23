package com.example.dfpreport;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DashFragment extends Fragment implements SetReportHandler{
    private Report currentReport;

    private Profile currentProfile;
    private final FragmentSwitcher fragmentSwitcher;

    public DashFragment(FragmentSwitcher fragmentSwitcher) {
        this.fragmentSwitcher=fragmentSwitcher;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(getContext(), MainActivity.class);
                requireActivity().finish();
                startActivity(intent);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentSwitcher.setCurrentReport(null);
        View view = inflater.inflate(R.layout.fragment_dash, container, false);


        RecyclerView reportRecyclerView = view.findViewById(R.id.report_recyclerview);
        final ReportAdapter reportAdapter = new ReportAdapter(reportRecyclerView.getContext(), this);
        reportRecyclerView.setAdapter(reportAdapter);
        reportRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MainViewModel mainViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(MainViewModel.class);
        mainViewModel.getAllReports().observe(getViewLifecycleOwner(), reportAdapter::setReports);

        Button newInspection = view.findViewById(R.id.dash_frag_new_inspection);
        newInspection.setOnClickListener(v -> {
            Fragment fragment = new NewSweepCheck(fragmentSwitcher);
            fragmentSwitcher.loadFragment(fragment);
        });
        Button selectInspection = view.findViewById(R.id.dash_frag_select_inspection);
        selectInspection.setOnClickListener(v -> {
            if (currentReport != null) {
                Fragment fragment = new NewSweepCheck(fragmentSwitcher);
                fragmentSwitcher.setCurrentReport(currentReport);
                fragmentSwitcher.loadFragment(fragment);
            } else {
                Toast.makeText(getContext(), R.string.select_insp, Toast.LENGTH_SHORT).show();
            }

        });
        return view;
    }

    @Override
    public void setCurrentReport(Report currentReport) {
        this.currentReport = currentReport;
    }
}
