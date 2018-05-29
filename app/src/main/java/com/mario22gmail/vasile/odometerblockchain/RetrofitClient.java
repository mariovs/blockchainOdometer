package com.mario22gmail.vasile.odometerblockchain;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    static String baseUrl = "http://192.168.2.58:5000/";

    private static BlockchainDb service;
    static BlockchainDb getService() {
        if(service == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.readTimeout(10, TimeUnit.MINUTES);
            httpClient.connectTimeout(10, TimeUnit.MINUTES);
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(StringConverterFactory.create())
                    // custom interceptor
                    .addConverterFactory(GsonConverterFactory.create());


            Retrofit retrofit = builder
                    .client(httpClient.build())
                    .build();
            service =retrofit.create(BlockchainDb.class);
        }
        return service;
    }
}
