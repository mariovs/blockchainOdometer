package com.mario22gmail.vasile.odometerblockchain.DataModel;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.security.Key;

@Entity
public class KeyPair {

    @PrimaryKey
    @NonNull
    private String publicKey;

    @ColumnInfo
    private String privateKey;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @NonNull
    public String getPublicKey() {

        return publicKey;
    }

    public void setPublicKey(@NonNull String publicKey) {
        this.publicKey = publicKey;
    }

    public KeyPair()
    {

    }

    @Ignore
    public KeyPair(String publicKey, String privateKey)
    {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }
}
