package com.mario22gmail.vasile.odometerblockchain.DataModel;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseService {

    public static String databaseName = "asset-database";
    public AppDatabase appDatabase;

    private DatabaseService(AppDatabase database){
        appDatabase = database;
    }

    private static DatabaseService instance;

    public static DatabaseService GetInstance(Context context){
        if(instance == null)
        {
            AppDatabase appDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, databaseName).allowMainThreadQueries().fallbackToDestructiveMigration().build();
            instance = new DatabaseService(appDatabase);
        }
        return instance;
    }
}
