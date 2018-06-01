package com.mario22gmail.vasile.odometerblockchain;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersAssetsList extends AppCompatActivity {

    @BindView(R.id.userAssetsListView)
    ListView assetsListView;

    @BindView(R.id.publicKeyDetailsTextView)
    TextView publicKeyTextView;

    @BindView(R.id.emptyState)
    ConstraintLayout emptyView;

    ArrayAdapter<String> arrayAdapter;
    List<String> assetIdList;
    String publicKey;
    String privateKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_assets_list);
        ButterKnife.bind(this);
        publicKey = getIntent().getStringExtra(Constants.publicKeyConstant);
        privateKey = getIntent().getStringExtra(Constants.privateKeyConstant);
        publicKeyTextView.setText(publicKey);
        LoadListView();
    }


    public void LoadListView()
    {
        RetrofitClient.getService().getAssetListByPublicKey(publicKey).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.isSuccessful())
                {

                    ArrayList<String> assetList = new ArrayList<>();

                    JsonArray objectsFromResponse = response.body();
                    for(int i = 0; i < objectsFromResponse.size(); i++)
                    {
                        JsonElement element = objectsFromResponse.get(i);
                        JsonObject transactionJson =  element.getAsJsonObject();

                        String transactionId = transactionJson.get("transaction_id").getAsString();
                        Log.d(Constants.LogKey, "transactions found " + transactionId);

                        assetList.add(transactionId);
                    }

                    if(assetList.size() == 0)
                    {
                        emptyView.setVisibility(View.VISIBLE);
                        assetsListView.setVisibility(View.GONE);
                    }else {
                        emptyView.setVisibility(View.GONE);
                        assetsListView.setVisibility(View.VISIBLE);

                        arrayAdapter = new ArrayAdapter<String>(
                                getApplicationContext(),R.layout.asset_view, R.id.assetKeyTextView, assetList);
                        assetsListView.setAdapter(arrayAdapter);
                        assetsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                                String transactionIdString = (String) assetsListView.getItemAtPosition(position);
                                Intent intent = new Intent(getApplicationContext(), AssetDetailsActivity.class);
                                intent.putExtra(Constants.assetIdConstant, transactionIdString);
                                intent.putExtra(Constants.publicKeyConstant, publicKey);
                                intent.putExtra(Constants.privateKeyConstant, privateKey);
                                startActivity(intent);
                            }
                        });
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.buttonQrCode)
    public void GenerateQrCode(View view)
    {
        CustomQRDialog customQRDialog = new CustomQRDialog(this, publicKey);
        customQRDialog.show();
    }
}
