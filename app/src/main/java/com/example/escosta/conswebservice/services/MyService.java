package com.example.escosta.conswebservice.services;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.escosta.conswebservice.model.DataItem;
import com.example.escosta.conswebservice.utils.HttpHelper;
import com.google.gson.Gson;

import java.io.IOException;

/**
 * Created by escosta on 10/05/2017.
 */

public class MyService extends IntentService {

    public static final String TAG = "MyService";
    public static final String MY_SERVICE_MESSAGE = "myServiceMessage";
    public static final String MY_SERVICE_PAYLOAD = "myServicePayload";

    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Uri uri = intent.getData();

        String response;

        try {
            response = HttpHelper.downloadUrl(uri.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Gson gson = new Gson();
        DataItem[] dataItems = gson.fromJson(response, DataItem[].class);

        Intent messageIntent = new Intent(MY_SERVICE_MESSAGE);
        messageIntent.putExtra(MY_SERVICE_PAYLOAD, dataItems);
        LocalBroadcastManager manager =
                LocalBroadcastManager.getInstance(getApplicationContext());
        manager.sendBroadcast(messageIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

}
