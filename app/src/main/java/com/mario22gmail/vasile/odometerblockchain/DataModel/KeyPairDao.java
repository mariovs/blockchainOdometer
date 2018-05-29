package com.mario22gmail.vasile.odometerblockchain.DataModel;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface KeyPairDao {

    @Insert
    void insert(KeyPair keyPair);

    @Update
    void update(KeyPair keyPair);

    @Query("SELECT * FROM keypair")
    List<KeyPair> getAll();

    @Query("SELECT * FROM keypair WHERE publicKey = :publicKey")
    KeyPair GetKeyPair(String publicKey);
}
