package com.mario22gmail.vasile.odometerblockchain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mario22gmail.vasile.odometerblockchain.DataModel.AssetKeys;
import com.mario22gmail.vasile.odometerblockchain.DataModel.DatabaseService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssetDetailsActivity extends AppCompatActivity {

    @BindView(R.id.assetIdValueTextView)
    TextView assetIdTextView;

    @BindView(R.id.publicKeyValueTextView)
    TextView publicKeyTextView;

    @BindView(R.id.privateKeyValueTextView)
    TextView privateKeyTextView;

    @BindView(R.id.transferAssetButton)
    Button transferAssetButton;


    String publicKey;
    String privateKey;
    String assetTransactionId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_details);
        ButterKnife.bind(this);

        setTitle("Asset details");
//        DatabaseService dbService = DatabaseService.GetInstance(getApplicationContext());
        assetTransactionId = getIntent().getStringExtra(Constants.assetIdConstant);
        publicKey = getIntent().getStringExtra(Constants.publicKeyConstant);
        privateKey = getIntent().getStringExtra(Constants.privateKeyConstant);

        assetIdTextView.setText(assetTransactionId);
        publicKeyTextView.setText(publicKey);
        privateKeyTextView.setText(privateKey);


//        asset= dbService.appDatabase.assetDao().GetAssetKey(assetId);
//        if(asset != null)
//        {
//            assetIdTextView.setText(asset.getFirstTransactionId());
//            publicKeyTextView.setText(asset.getPublicKey());
//            privateKeyTextView.setText(asset.getPrivateKey());
//            assetStatusTextView.setText(asset.getAssetStatus());
//        }
//        if(!asset.getAssetStatus().equals(Constants.AssetStatus.Active.name()))
//        {
//            transferAssetButton.setEnabled(false);
//        }
    }

    @OnClick(R.id.displayAssetIdQrButton)
    public void DisplayAssetIdQrButtonClick(View view)
    {
        CustomQRDialog customQRDialog = new CustomQRDialog(this, assetTransactionId);
        customQRDialog.show();
    }

    @OnClick(R.id.displayQrPublicKeyButton)
    public void DisplayPublicKeyQrButtonClick(View view)
    {
        CustomQRDialog customQRDialog = new CustomQRDialog(this, publicKey);
        customQRDialog.show();
    }


    @OnClick(R.id.transferAssetButton)
    public void TransferButtonClick(View view)
    {
//        if(!asset.getAssetStatus().equals(Constants.AssetStatus.Active.name()))
//        {
//            return;
//        }

        IntentIntegrator intent = new IntentIntegrator(this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intent.setPrompt("Scan receiver public key");
        intent.setCameraId(0);
        intent.setBeepEnabled(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null)
        {
            if(intentResult.getContents() == null)
            {
                Toast.makeText(this, "You canceld" , Toast.LENGTH_LONG);
            }else{
                final String receiverPublicKey = intentResult.getContents();
                Log.d(Constants.LogKey, "public key "+ receiverPublicKey);
                Log.d(Constants.LogKey, "private key "+ privateKey);
                Log.d(Constants.LogKey, "asset id "+ assetTransactionId);
                RetrofitClient.getService().transferCarAsset(privateKey,receiverPublicKey, assetTransactionId).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful())
                        {
//                            assetStatusTextView.setText(Constants.AssetStatus.Transferred.name());
//                            asset.setAssetStatus(Constants.AssetStatus.Transferred.name());
//                            DatabaseService dbService = DatabaseService.GetInstance(getApplicationContext());
//                            dbService.appDatabase.assetDao().update(asset);
                            Toast.makeText(getApplicationContext(), "Asset transfered to public key:  " + receiverPublicKey, Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "An error occured " + response.message(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });

            }
        }else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



}
