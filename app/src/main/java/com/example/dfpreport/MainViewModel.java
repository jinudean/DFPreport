package com.example.dfpreport;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    LiveData<List<Profile>> allProfiles;
    LiveData<List<Report>> allReports;
    private final LiveData<List<Category>> allCategories;
    private final LiveData<List<SubCategory>> allSubcategories;
    private final LiveData<List<ListItem>> allListItems;

    private final DFPreportRepository DFPreportRepository;

    public MainViewModel(Application application) {
        super(application);
        DFPreportRepository = new DFPreportRepository(application);
        allProfiles = DFPreportRepository.getAllProfiles();
        allReports = DFPreportRepository.getAllReports();
        allCategories = DFPreportRepository.getAllCategories();
        allSubcategories = DFPreportRepository.getAllSubcategories();
        allListItems = DFPreportRepository.getAllListItems();
    }


    // INSERT
    public void insertProfile(Profile profile) {
        DFPreportRepository.insertProfile(profile);
    }

    public void insertReport(Report report) {
        DFPreportRepository.insertReport(report);
    }

    public void insertAirplaneInfo(AirplaneInfo AirplaneInfo) {
        DFPreportRepository.insertAirplaneInfo(AirplaneInfo);
    }

    public void insertListItem(ListItem listItem) {
        DFPreportRepository.insertListItem(listItem);
    }

    public void insertCategory(Category category) {
        DFPreportRepository.insertCategory(category);
    }

    public void insertSubCategory(SubCategory subCategory) {
        DFPreportRepository.insertSubCategory(subCategory);
    }

    // DELETE
    public void deleteListItem(int id) {
        DFPreportRepository.deleteListItem(id);
    }

    // UPDATE
    public void updateReport(Integer reportId, String inBound, String inflight, String outBound, String outflight) {
        DFPreportRepository.updateReport(reportId, inBound, inflight, outBound, outflight);
    }

    public void updateAirplaneInfo(Integer reportId, String fleetInfo, String tailNumber, String swPartNumber,String mediaVersion) {
        DFPreportRepository.updateAirplaneInfo(reportId, fleetInfo, tailNumber, swPartNumber, mediaVersion);
    }

    public void updateListItem(Integer listItemId, String notes, String photos, Integer subCat) {
        DFPreportRepository.updateListItem(listItemId, notes, photos, subCat);
    }

    public void updateProfileInfo(Integer profileId, String firstName, String lastName, String email, String phone, String companyName, String licenseNumber, String photo) {
        DFPreportRepository.updateProfileInfo(profileId, firstName, lastName, email, phone, companyName, licenseNumber, photo);
    }

    public void addPDF(Integer reportId, String pdf) {
        DFPreportRepository.addPDF(reportId, pdf);
    }

    public LiveData<List<Profile>> getAllProfiles() {
        return allProfiles;
    }

    public LiveData<List<Report>> getAllReports() {
        return allReports;
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public LiveData<List<SubCategory>> getAllSubcategories() {
        return allSubcategories;
    }

    public LiveData<List<ListItem>> getAllListItems() {
        return allListItems;
    }

    public Report getNewReport() {
        return DFPreportRepository.getNewReport();
    }

    public List<AirplaneInfo> getAirplaneInfo(int id) {
        return DFPreportRepository.getAirplaneInfo(id);
    }
}
