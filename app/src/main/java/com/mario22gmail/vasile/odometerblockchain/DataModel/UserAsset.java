package com.mario22gmail.vasile.odometerblockchain.DataModel;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(foreignKeys = @ForeignKey(entity = KeyPair.class,
        parentColumns = "publicKey",
        childColumns = "publicKey",
        onDelete = ForeignKey.CASCADE))
public class UserAsset {

    @PrimaryKey
    @NonNull
    private String transactionId;

    @ColumnInfo
    private String publicKey;

    @NonNull
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(@NonNull String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public UserAsset(){

    }

    @Ignore
    public UserAsset(String publicKey, String transactionId)
    {
        this.publicKey = publicKey;
        this.transactionId = transactionId;
    }
}
