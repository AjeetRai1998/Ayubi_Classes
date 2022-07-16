package com.emergence.study_app.Activity;

import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.responseSendToken.ResponseSendToken;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyFirebaseMessagingeService extends FirebaseMessagingService {
    private LocalBroadcastManager broadcaster;
    public static String TAG="AYUBI";
    PreferencesManager preferencesManager;

    @Override
    public void onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this);
        preferencesManager=new PreferencesManager(MyFirebaseMessagingeService.this);
    }

    @Override
    public void onNewToken(String token) {
//        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        updateTOken(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
//        if (remoteMessage.getData().size() > 0) {
//            Log.d(TAG, "Message data payload: " + remoteMessage.getData().get("data"));
//
//
//            try {
//                JSONObject jsonObject = new JSONObject(remoteMessage.getData().get("data"));
//                JSONObject data=jsonObject.getJSONObject("title");

                Intent intent = new Intent("MyData");
                broadcaster.sendBroadcast(intent);

//            }catch (Exception e){
//                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//            if (/* Check if data needs to be processed by long running job */ true) {
//                Toast.makeText(this, "error1", Toast.LENGTH_SHORT).show();
//                // For long-running tasks (10 seconds or more) use WorkManager.
////                scheduleJob();
//            } else {
//                Toast.makeText(this, "error2", Toast.LENGTH_SHORT).show();
//                // Handle message within 10 seconds
////                handleNow();
//            }
//
//        } else {
//            Toast.makeText(this, "error2", Toast.LENGTH_SHORT).show();
//            // Handle message within 10 seconds
////                handleNow();
//        }
//
//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//
//
//
//        }




        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    public void updateTOken(String token){
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseSendToken> call=apiServices.sendToken("token_update",preferencesManager.getUserId(),token);
        call.enqueue(new Callback<ResponseSendToken>() {
            @Override
            public void onResponse(Call<ResponseSendToken> call, Response<ResponseSendToken> response) {
                if (response.body().getRes().equalsIgnoreCase("success")){

                }
            }

            @Override
            public void onFailure(Call<ResponseSendToken> call, Throwable t) {

            }
        });

    }
}
