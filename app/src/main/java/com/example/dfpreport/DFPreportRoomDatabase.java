package com.example.dfpreport;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Profile.class, Category.class, AirplaneInfo.class, SubCategory.class, ListItem.class, Report.class }, version = 1,exportSchema = false)
public abstract class DFPreportRoomDatabase extends RoomDatabase {

    public abstract DFPreportDao dfPreportDao();

    private static final String TAG = DFPreportRoomDatabase.class.getSimpleName();
    private static volatile DFPreportRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    
    static DFPreportRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (DFPreportRoomDatabase.class) {
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),DFPreportRoomDatabase.class, "dfpreport_databse").addCallback(startRoomDatabaseCallback).allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback startRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                DFPreportDao dfPreportDao = INSTANCE.dfPreportDao();
                Category category = new Category("HeadEnd");
                dfPreportDao.insertCategory(category);
                SubCategory subCategory = new SubCategory(1, "Server#1");
                dfPreportDao.insertSubCategory(subCategory);
                subCategory = new SubCategory(1, "Server#2");
                dfPreportDao.insertSubCategory(subCategory);

                //second category
                category = new Category("NetWork");
                dfPreportDao.insertCategory(category);
                subCategory = new SubCategory(2, "WAP");
                dfPreportDao.insertSubCategory(subCategory);

                //third category
                category = new Category("Accessories");
                dfPreportDao.insertCategory(category);
                subCategory = new SubCategory(3, "Onboard Media Loader");
                dfPreportDao.insertSubCategory(subCategory);
                subCategory = new SubCategory(3, "FAP #1");
                dfPreportDao.insertSubCategory(subCategory);
                subCategory = new SubCategory(3, "FAP #2");
                dfPreportDao.insertSubCategory(subCategory);

                //forth category
                category = new Category("Power");
                dfPreportDao.insertCategory(category);
                subCategory = new SubCategory(4, "MCU");
                dfPreportDao.insertSubCategory(subCategory);
                subCategory = new SubCategory(4, "Seat Power Module");
                dfPreportDao.insertSubCategory(subCategory);

                //fifth category
                category = new Category("Monitors (ITU)");
                dfPreportDao.insertCategory(category);
                subCategory = new SubCategory(5, "Delta One <Rows 1-8> P/N L0005-01 {18.5 inch}");
                dfPreportDao.insertSubCategory(subCategory);
                subCategory = new SubCategory(5, "Delta Primium Select <Rows 20-23> P/N L0003-01 {13.3 inch}");
                dfPreportDao.insertSubCategory(subCategory);
                subCategory = new SubCategory(5, "Delta Comport <Rows 30-36> P/N L0001-02 {10.1 inch}");
                dfPreportDao.insertSubCategory(subCategory);
                subCategory = new SubCategory(5, "Delta Economy <Rows 37-58> P/N L0001-02 {10.1 inch}");
                dfPreportDao.insertSubCategory(subCategory);
                subCategory = new SubCategory(5, "Public Display <Wall 30 & 42> P/N L0003-01 {13.3 inch}");

                //sixth category
                dfPreportDao.insertSubCategory(subCategory);
                category = new Category("Monthly Media Cotents Load");
                dfPreportDao.insertCategory(category);
                subCategory = new SubCategory(6, "CEO Video");
                dfPreportDao.insertSubCategory(subCategory);
                subCategory = new SubCategory(6, "Safety Video");
                dfPreportDao.insertSubCategory(subCategory);
                subCategory = new SubCategory(6, "Content Volume");
                dfPreportDao.insertSubCategory(subCategory);
                subCategory = new SubCategory(6, "Load Procedure");
                dfPreportDao.insertSubCategory(subCategory);

                //seventh category
                category = new Category("S/W Upgrade");
                dfPreportDao.insertCategory(category);
                subCategory = new SubCategory(7, "New S/W Version");
                dfPreportDao.insertSubCategory(subCategory);
                subCategory = new SubCategory(7, "ERA #");
                dfPreportDao.insertSubCategory(subCategory);

            });
        }
    };
    
}

