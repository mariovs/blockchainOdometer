package com.mario22gmail.vasile.odometerblockchain;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mario22gmail.vasile.odometerblockchain.DataModel.AppDatabase;
import com.mario22gmail.vasile.odometerblockchain.DataModel.AssetKeys;
import com.mario22gmail.vasile.odometerblockchain.DataModel.DatabaseService;

import net.glxn.qrgen.android.QRCode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    String logKey = "MMM:";
    AppDatabase dbInstance;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        //Remove title bar//Remove

        DatabaseService databaseService = DatabaseService.GetInstance(getApplicationContext());
        dbInstance = databaseService.appDatabase;
//        if(dbInstance.assetDao().getAll().size() > 0)
//        {
//            helloWorldEditText.setText(dbInstance.assetDao().getAll().get(0).getPublicKey());
//        }

    }

//    @OnClick(R.id.PressMeButton)
//    public void ButtonClick(View view){
//        RetrofitClient.getService().getHelloWorld().enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
//                String responseFromPython = response.body().get("mario").getAsString();
//                Log.d(logKey, responseFromPython);
//                helloWorldEditText.setText(responseFromPython);
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Log.d(logKey, "log failed call: "  + call.toString());
//                Log.d(logKey, "log failed t: "  + call.toString());
//            }
//        });
//    }

    @OnClick(R.id.PressMeButton)
    public void ButtonClick(View view){
        Intent assetList = new Intent(this, ListAssetsActivity.class);
        startActivity(assetList);
    }

    @OnClick(R.id.buttonWriteDb)
    public void WriteDbClick(View view)
    {
        Intent userKeyPariIntent = new Intent(getApplicationContext(), UserKeyPairsListActivity.class);
        startActivity(userKeyPariIntent);
    }

//    @OnClick(R.id.createAssetButton)
    public void AddNewAssetClick(View view)
    {
        Intent createNewAssetActivity = new Intent(getApplicationContext(),CreateAssetActivity.class);
        startActivity(createNewAssetActivity);
    }

//    @OnClick(R.id.scanButton)
    public void ScanButtonClick(View view)
    {
        IntentIntegrator intent = new IntentIntegrator(this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intent.setPrompt("Scan");
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
//                helloWorldEditText.setText(intentResult.getContents());
                Toast.makeText(this, intentResult.getContents(), Toast.LENGTH_LONG);
            }
        }else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

//    @OnClick(R.id.createQRButton)
//    public void CreateQRCode(View view)
//    {
//        String textFromTextView = helloWorldEditText.getText().toString();
//
//        Bitmap qrCodeGenerated = QRCode.from(textFromTextView).bitmap();
//        QRCodeImageView.setImageBitmap(qrCodeGenerated);
//    }
}
