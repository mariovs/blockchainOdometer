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

import com.mario22gmail.vasile.odometerblockchain.DataModel.AssetKeys;
import com.mario22gmail.vasile.odometerblockchain.DataModel.DatabaseService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListAssetsActivity extends AppCompatActivity {

    @BindView(R.id.assetListView)
    ListView assetListView;
    ArrayAdapter<String> arrayAdapter;
    List<String> assetIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_assets);
        ButterKnife.bind(this );

        final DatabaseService dbService = DatabaseService.GetInstance(getApplicationContext());
        List<AssetKeys> assetStoredHere = dbService.appDatabase.assetDao().getAll();
        assetIdList = new ArrayList<>();
        for(AssetKeys asset : assetStoredHere)
        {
            assetIdList.add(asset.getFirstTransactionId());
        }

        arrayAdapter = new ArrayAdapter<String>(
                this,R.layout.asset_view, R.id.assetKeyTextView, assetIdList);

        assetListView.setAdapter(arrayAdapter);
        assetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    String transactionIdString = (String) assetListView.getItemAtPosition(position);
                    Log.d(Constants.LogKey, "click on " + transactionIdString);

                    AssetKeys asset= dbService.appDatabase.assetDao().GetAssetKey(transactionIdString);
                    Log.d(Constants.LogKey, "ceva  " + asset.getPrivateKey());
                    Intent intent = new Intent(getApplicationContext(), AssetDetailsActivity.class);
                    intent.putExtra(Constants.assetIdConstant, transactionIdString);
                    intent.putExtra(Constants.publicKeyConstant, asset.getPublicKey());
                    intent.putExtra(Constants.privateKeyConstant, asset.getPrivateKey());
                    startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        assetIdList.clear();
        final DatabaseService dbService = DatabaseService.GetInstance(getApplicationContext());
        List<AssetKeys> assetStoredHere = dbService.appDatabase.assetDao().getAll();
        for(AssetKeys asset : assetStoredHere)
        {
            assetIdList.add(asset.getFirstTransactionId());
        }


        arrayAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.addAssetButton)
    public void AddAssetClick(View view)
    {
        Intent createNewAssetActivity = new Intent(getApplicationContext(),CreateAssetActivity.class);
        startActivity(createNewAssetActivity);
    }





}


