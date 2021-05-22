package com.example.dfpreport;

import androidx.fragment.app.Fragment;

public interface FragmentSwitcher {
    void loadFragment(Fragment fragment);
    Profile getProfile();
    void updateCurrentProfile(Profile profile);
    Report getCurrentReport();
    void setCurrentReport(Report report);
    MainViewModel getViewModel();
}
