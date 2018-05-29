package com.mario22gmail.vasile.odometerblockchain.DataModel;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.mario22gmail.vasile.odometerblockchain.Constants;

import java.util.ArrayList;


@Entity
public class AssetKeys {

    @NonNull
    @PrimaryKey
    private String privateKey;

    @ColumnInfo
    private String publicKey;

    @ColumnInfo
    private String firstTransactionId;

    @ColumnInfo
    private String assetStatus;


    public String getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getFirstTransactionId() {
        return firstTransactionId;
    }

    public void setFirstTransactionId(String transactionIdList) {
        this.firstTransactionId = transactionIdList;
    }

    public AssetKeys(){

    }



    @Ignore
    public AssetKeys(String publicKey, String privateKey, String transactionId)
    {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.firstTransactionId = transactionId;
        this.assetStatus = Constants.AssetStatus.Active.name();
    }
}
