package com.mario22gmail.vasile.odometerblockchain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.mario22gmail.vasile.odometerblockchain.DataModel.AppDatabase;
import com.mario22gmail.vasile.odometerblockchain.DataModel.AssetKeys;
import com.mario22gmail.vasile.odometerblockchain.DataModel.DatabaseService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAssetActivity extends AppCompatActivity {

    @BindView(R.id.manufacturerEditText)
    EditText manufacturerEditText;

    @BindView(R.id.serialNumberEditText)
    EditText serialNumberEditText;

    @BindView(R.id.modelEditText)
    EditText modelEditText;

    @BindView(R.id.productionYearEditText)
    EditText productionYearEditText;

    @BindView(R.id.unitMeterEditText)
    EditText meterUnitEditText;

    @BindView(R.id.saleDateEditText)
    EditText dateOfSaleEditText;

    @BindView(R.id.selerNameEditText)
    EditText sellerNameEditText;

    @BindView(R.id.signatureEditText)
    EditText signatureEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_asset);
        ButterKnife.bind(this);
        setTitle("Create asset");
//
//        manufacturerEditText.setText("manufacturer1");
//        modelEditText.setText("model1");
//        serialNumberEditText.setText("ceva serial");
//        productionYearEditText.setText("2018");
//        sellerNameEditText.setText("mario Emag");
//        dateOfSaleEditText.setText("2019");
//        meterUnitEditText.setText("km");
//        signatureEditText.setText("signature");
    }


    @OnClick(R.id.addButton)
    public void AddButtonClick(View view)
    {
        String manufacturerText = manufacturerEditText.getText().toString();
        String modelText = modelEditText.getText().toString();
        String serialNumber = serialNumberEditText.getText().toString();
        String yearText = productionYearEditText.getText().toString();
        String sellerText = sellerNameEditText.getText().toString();
        String dateOfSale = dateOfSaleEditText.getText().toString();
        String meterOfUnit = meterUnitEditText.getText().toString();
        String signatureText = signatureEditText.getText().toString();



        RetrofitClient.getService().createCarAsset(manufacturerText,modelText,yearText,meterOfUnit,serialNumber,sellerText,dateOfSale,signatureText).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful())
                {
                    DatabaseService dbService = DatabaseService.GetInstance(getApplicationContext());
                    List<AssetKeys> assetStoredHere = dbService.appDatabase.assetDao().getAll();
                    if(assetStoredHere.size() > 0)
                    {
                        for(AssetKeys asset : assetStoredHere)
                        {
                            Log.d(Constants.LogKey,  "public key : " + asset.getPublicKey());
                            Log.d(Constants.LogKey,  "private key : " + asset.getPrivateKey());
                            Log.d(Constants.LogKey,  "transaction id  : " +  asset.getFirstTransactionId());
                            Log.d(Constants.LogKey, "-----------------");

                        }
                    }

                    String privateKey = response.body().get("privateKey").getAsString();
                    String publicKey = response.body().get("publicKey").getAsString();
                    String transactionId = response.body().get("transactionId").getAsString();
                    AssetKeys asset = new AssetKeys(publicKey, privateKey, transactionId);

                    dbService.appDatabase.assetDao().insert(asset);

                    Intent intent = new Intent(getApplicationContext(), AssetDetailsActivity.class);
                    intent.putExtra(Constants.assetIdConstant, transactionId);
                    startActivity(intent);
                    finish();
                }else{
                    Log.d(Constants.LogKey, "An error occured " + response.message() + " status code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(Constants.LogKey, "error");
            }
        });

    }
}
