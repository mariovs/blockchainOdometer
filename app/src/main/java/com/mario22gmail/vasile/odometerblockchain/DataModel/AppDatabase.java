package com.mario22gmail.vasile.odometerblockchain.DataModel;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {AssetKeys.class, KeyPair.class, UserAsset.class}, version = 4, exportSchema = false)
public abstract class AppDatabase  extends RoomDatabase{
    public abstract AssetDao assetDao();

    public abstract KeyPairDao keyPairDao();

    public abstract UserAssetDao userAssetDao();
}
