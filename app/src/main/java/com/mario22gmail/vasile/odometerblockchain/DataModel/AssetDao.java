package com.mario22gmail.vasile.odometerblockchain.DataModel;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface AssetDao {

    @Insert
    void insert(AssetKeys assetKeys);

    @Update
    void update(AssetKeys assetKeys);

    @Query("SELECT * FROM assetkeys")
    List<AssetKeys> getAll();

    @Query("SELECT * FROM ASSETKEYS WHERE firstTransactionId = :assetId")
    AssetKeys GetAssetKey(String assetId);

}
