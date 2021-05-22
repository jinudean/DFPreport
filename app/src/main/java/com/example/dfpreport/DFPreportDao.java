package com.example.dfpreport;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DFPreportDao {
    @Insert
    void insertProfile(Profile profile);

    @Insert
    void insertReport(Report report);

    @Insert
    void insertAirplaneInfo(AirplaneInfo airplaneInfo);

    @Insert
    void insertListItem(ListItem listItem);

    @Insert
    void insertCategory(Category category);

    @Insert
    void insertSubCategory(SubCategory subCategory);

    @Query("UPDATE profile_table SET firstName = :firstName, lastName  = :lastName, email = :email, phone = :phone, station = :station, employeeNumber = :employeeNumber, photo = :photo WHERE profileId = :profileId")
    void updateProfileInfo(Integer profileId, String firstName, String lastName, String email, String phone, String station, String employeeNumber, String photo);

    @Query("UPDATE report_table SET inBound = :inBound, outBound = :outBound WHERE reportId = :reportId")
    void updateReport(Integer reportId, String inBound, String outBound);

    @Query("UPDATE airplaneinfo_table SET fleetInfo = :fleetInfo, tailNumber = :tailNumber, swPartNumber = :swPartNumber, mediaVersion = :mediaVersion WHERE reportId =:reportId")
    void updateAirplaneInfo(Integer reportId, String fleetInfo, String tailNumber, String swPartNumber,String mediaVersion );

    @Query("UPDATE listitem_table SET notes = :notes, photos = :photos, subCategoryId = :subCat WHERE listItemId = :listItemId")
    void updateListItem(Integer listItemId, String notes, String photos, Integer subCat);

    @Query("UPDATE report_table SET pdf = :pdf WHERE reportId = :reportId")
    void addPDF(Integer reportId, String pdf);

    @Query("DELETE FROM listitem_table WHERE listItemId = :listItemId")
    void deleteListItem(Integer listItemId);

    @Query("SELECT * from profile_table ORDER BY profileId ASC")
    LiveData<List<Profile>> getAllProfiles();

    @Query("SELECT * from report_table ORDER BY reportId ASC")
    LiveData<List<Report>> getAllReports();

    @Query("SELECT * from category_table ORDER BY title ASC")
    LiveData<List<Category>> getAllCategories();

    @Query("SELECT * from subcategory_table ORDER BY title ASC")
    LiveData<List<SubCategory>> getAllSubcategories();

    @Query("SELECT * from airplaneinfo_table ORDER BY airplaneInfoId ASC")
    List<AirplaneInfo> getAllAirplaneInfo();

    @Query("SELECT * from listitem_table ORDER BY listItemId ASC")
    LiveData<List<ListItem>> getAllListItems();

    // FETCH REPORT DATA QUERIES
    @Query("SELECT * from airplaneinfo_table where reportId = :reportId")
    List<AirplaneInfo> getAirplaneInfo(Integer reportId);

    @Query("SELECT * from report_table order by reportId DESC limit 1")
    Report getNewReport();
}
