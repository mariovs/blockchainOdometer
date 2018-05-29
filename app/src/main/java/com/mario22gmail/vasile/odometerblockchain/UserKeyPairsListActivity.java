package com.mario22gmail.vasile.odometerblockchain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mario22gmail.vasile.odometerblockchain.DataModel.AssetKeys;
import com.mario22gmail.vasile.odometerblockchain.DataModel.DatabaseService;
import com.mario22gmail.vasile.odometerblockchain.DataModel.KeyPair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserKeyPairsListActivity extends AppCompatActivity {

    @BindView(R.id.keyPairsListView)
    ListView keyPairsListView;

    ArrayAdapter<String> arrayAdapter;
    DatabaseService dbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_key_pairs_list);
        ButterKnife.bind(this);
        dbService= DatabaseService.GetInstance(getApplicationContext());
        LoadListView();

    }

    @OnClick(R.id.addKeyPairButton)
    public void AddKeyPairButton(View view)
    {
        RetrofitClient.getService().generateKeyPair().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful())
                {
                    String privateKey = response.body().get("privateKey").getAsString();
                    String publicKey = response.body().get("publicKey").getAsString();

                    KeyPair keyPair = new KeyPair(publicKey, privateKey);

                    dbService.appDatabase.keyPairDao().insert(keyPair);
                    //referesh listView

                    AddItemToAdapter(keyPair.getPublicKey());

                }else {
                    Toast.makeText(getApplicationContext(), "An error occurred " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An failed occurred ", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void AddItemToAdapter(String item)
    {
        arrayAdapter.add(item);
    }

    public void LoadListView(){
        List<KeyPair> keyPairList = dbService.appDatabase.keyPairDao().getAll();
        List<String> publicKeyList = new ArrayList<>();
        for(KeyPair keyPair : keyPairList)
        {
            publicKeyList.add(keyPair.getPublicKey());
        }
        String[] simpleArray = new String[ publicKeyList.size() ];
        arrayAdapter = new ArrayAdapter<>(
                this,R.layout.asset_view, R.id.assetIdTextView, publicKeyList.toArray(simpleArray));

        keyPairsListView.setAdapter(arrayAdapter);
        keyPairsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int itemPosition = position;
                String publicKey = (String) keyPairsListView.getItemAtPosition(itemPosition);
                Log.d(Constants.LogKey, "click on " + publicKey);

                KeyPair keyPair = dbService.appDatabase.keyPairDao().GetKeyPair(publicKey);
                Log.d(Constants.LogKey, "ceva  " + keyPair.getPrivateKey());

//                Intent intent = new Intent(getApplicationContext(), AssetDetailsActivity.class);
//                intent.putExtra(Constants.assetIdConstant, transactionIdString);
//                startActivity(intent);
            }
        });
    }
}
