package com.mario22gmail.vasile.odometerblockchain;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface BlockchainDb {

    @GET("mario2")
    Call<JsonObject> getHelloWorld();

    @Multipart
    @POST("asset/create")
    Call<JsonObject> createCarAsset(
            @Part("manufacturer") String manufacturer,
            @Part("model") String model,
            @Part("year") String productionYear,
            @Part("meterUnit") String meterUnit,
            @Part("serialNumber") String serialNumber,
            @Part("seller") String seller,
            @Part("dateOfSale") String dateOfSale,
            @Part("originatorSignature") String originatorSignature
    );



    @Multipart
    @POST("asset/transfer")
    Call<JsonObject> transferCarAsset(
            @Part("privateKeyOwner") String privateKeyOwner,
            @Part("publicKeyReceiver") String publicKeyReceiver,
            @Part("transactionId") String transactionId
    );

    @GET("key/generate")
    Call<JsonObject> generateKeyPair();

    @Multipart
    @POST("asset/getByPublicKey")
    Call<JsonArray> getAssetListByPublicKey(@Part("publicKey") String publicKey);



//
//    @FormUrlEncoded
//    @POST("asset/create/")
//    Call<JsonObject> createCarAsset(
//            @Field("manufacturer") String manufacturer,
//            @Field("model") String model,
//            @Field("year") String productionYear,
//            @Field("meterUnit") String meterUnit,
//            @Field("serialNumber") String serialNumber,
//            @Field("seller") String seller,
//            @Field("dateOfSale") String dateOfSale,
//            @Field("originatorSignature") String originatorSignature
//    );


    @Multipart
    @POST("mario3")
    Call<JsonObject> hello(@Part("param") String param);
}
