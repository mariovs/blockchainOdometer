package com.mario22gmail.vasile.odometerblockchain.DataModel;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserAssetDao {

    @Insert
    void insert(UserAsset userAsset);

    @Update
    void update(UserAsset userAsset);

    @Query("SELECT * FROM userasset")
    List<UserAsset> getAll();

    @Query("SELECT * FROM userasset WHERE publicKey = :publicKey")
    UserAsset GetUserAsset(String publicKey);
}
