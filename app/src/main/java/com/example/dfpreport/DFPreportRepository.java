package com.example.dfpreport;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DFPreportRepository {
    
    private static final String TAG = DFPreportRepository.class.getSimpleName();
    private final DFPreportDao dfPreportDao;
    private final LiveData<List<Profile>> allProfiles;
    private final LiveData<List<Report>> allReports;
    private final LiveData<List<Category>> allCategories;
    private final LiveData<List<SubCategory>> allSubcategories;
    private final LiveData<List<ListItem>> allListItems;
    private List<AirplaneInfo> allAirplaneInfo;
    private List<AirplaneInfo> currentAirplaneInfo;

    DFPreportRepository(Application application) {
        DFPreportRoomDatabase db = DFPreportRoomDatabase.getDatabase(application);
        dfPreportDao = db.dfPreportDao();
        allProfiles = dfPreportDao.getAllProfiles();
        allReports = dfPreportDao.getAllReports();
        allCategories = dfPreportDao.getAllCategories();
        allSubcategories = dfPreportDao.getAllSubcategories();
        allListItems = dfPreportDao.getAllListItems();
        DFPreportRoomDatabase.databaseWriteExecutor.execute(() -> {
            allAirplaneInfo = dfPreportDao.getAllAirplaneInfo();
        });
    }

    // INSERT QUERIES
    void insertProfile(final Profile profile) {
        DFPreportRoomDatabase.databaseWriteExecutor.execute(() -> {
            dfPreportDao.insertProfile(profile);
        });
    }

    void insertReport(final Report report) {
        DFPreportRoomDatabase.databaseWriteExecutor.execute(() -> {
            dfPreportDao.insertReport(report);
        });
    }

    void insertAirplaneInfo(final AirplaneInfo airplaneInfo) {
        DFPreportRoomDatabase.databaseWriteExecutor.execute(() -> {
            dfPreportDao.insertAirplaneInfo(airplaneInfo);
        });
    }

    void insertListItem(final ListItem listItem) {
        DFPreportRoomDatabase.databaseWriteExecutor.execute(() -> {
            dfPreportDao.insertListItem(listItem);
        });
    }

    void insertCategory(final Category category) {
        DFPreportRoomDatabase.databaseWriteExecutor.execute(() -> {
            dfPreportDao.insertCategory(category);
        });
    }

    void insertSubCategory(final SubCategory subCategory) {
        DFPreportRoomDatabase.databaseWriteExecutor.execute(() -> {
            dfPreportDao.insertSubCategory(subCategory);
        });
    }

    void updateReport(Integer reportId, String inBound, String outBound ) {
        DFPreportRoomDatabase.databaseWriteExecutor.execute(() -> {
            dfPreportDao.updateReport(reportId,inBound,outBound);
        });
    }

    void updateAirplaneInfo(Integer reportId, String fleetInfo, String tailNumber, String swPartNumber,String mediaVersion ) {
        DFPreportRoomDatabase.databaseWriteExecutor.execute(() -> {
            dfPreportDao.updateAirplaneInfo(reportId, fleetInfo, tailNumber, swPartNumber, mediaVersion);
        });
    }

    void updateListItem(Integer listItemId, String notes, String photos, Integer subCat) {
        DFPreportRoomDatabase.databaseWriteExecutor.execute(() -> {
            dfPreportDao.updateListItem(listItemId, notes, photos, subCat);
        });
    }

    void updateProfileInfo(Integer profileId, String firstName, String lastName, String email, String phone, String station, String employeeNumber, String photo) {
        DFPreportRoomDatabase.databaseWriteExecutor.execute(() -> {
            dfPreportDao.updateProfileInfo(profileId, firstName, lastName, email, phone, station, employeeNumber, photo);
        });
    }

    void addPDF(Integer reportId, String pdf) {
        DFPreportRoomDatabase.databaseWriteExecutor.execute(() -> {
            dfPreportDao.addPDF(reportId, pdf);
        });
    }




    void deleteListItem(Integer listItemId) {
        DFPreportRoomDatabase.databaseWriteExecutor.execute(() -> {
            dfPreportDao.deleteListItem(listItemId);
        });
    }


    LiveData<List<Profile>> getAllProfiles() {
        return allProfiles;
    }
    LiveData<List<Report>> getAllReports() {
        return allReports;
    }

    LiveData<List<Category>> getAllCategories() { return allCategories; }
    LiveData<List<SubCategory>> getAllSubcategories() { return allSubcategories; }
    LiveData<List<ListItem>> getAllListItems() { return allListItems; }

    Report getNewReport() {
        return dfPreportDao.getNewReport();
    }

    List<AirplaneInfo> getAirplaneInfo(int id) {
        currentAirplaneInfo = dfPreportDao.getAirplaneInfo(id);
        return currentAirplaneInfo;
    }
}
