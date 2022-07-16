package com.emergence.study_app.liveVideo;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Activity.ChatModel;
import com.emergence.study_app.Activity.MainActivity;
import com.emergence.study_app.Adapter.ChatAdapter;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.responseExitLiveSession.ResponseExitLiveSession;
import com.emergence.study_app.newTech.retrofit.model.responseJoinClass.ResponseJoinClass;
import com.emergence.study_app.newTech.retrofit.model.responseSendMessage.ResponseSendMessage;
import com.example.study_app.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveVideo extends  YouTubeBaseActivity {
    private static final String TAG = LiveVideo.class.getSimpleName();
    private String videoID;
    private YouTubePlayerView youTubePlayerView;
    RecyclerView recycler_video;
    String class_id="";
    String videoID1="";
    EditText edit_text;
    PreferencesManager preferencesManager;
    ImageView btn_send;
    android.app.AlertDialog alert2;
    TextView tv_topic,tv_desc;
    ImageView full_view,full_view_exit;
    LinearLayout send_msg,linear_title;
    RecyclerView chat;
    List<ChatModel> model=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_live_video);
        preferencesManager=new PreferencesManager(LiveVideo.this);
         videoID1 = getIntent().getStringExtra("video_id");
        class_id=getIntent().getStringExtra("classid");
        tv_desc=findViewById(R.id.tv_desc);
        chat=findViewById(R.id.chat);
        tv_topic=findViewById(R.id.tv_topic);
        tv_topic.setText(getIntent().getStringExtra("title"));
        tv_desc.setText("Date: "+getIntent().getStringExtra("desc"));
        linear_title=findViewById(R.id.linear_title);
        send_msg=findViewById(R.id.send_msg);
        String[] separated = videoID1.split("watch?v=");
        if (separated.length==2){
            videoID=separated[1].toString();
        }else {
            videoID=videoID1;
        }
        youTubePlayerView = findViewById(R.id.youtube_player_view);



        full_view=findViewById(R.id.full_view);
        full_view_exit=findViewById(R.id.full_view_exit);
        linear_title=findViewById(R.id.linear_title);
        send_msg=findViewById(R.id.send_msg);
        full_view_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (LiveVideo.this).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                full_view.setVisibility(View.VISIBLE);
                full_view_exit.setVisibility(View.GONE);
                linear_title.setVisibility(View.VISIBLE);
                send_msg.setVisibility(View.VISIBLE);
            }
        });
        full_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (LiveVideo.this).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                full_view.setVisibility(View.GONE);
                full_view_exit.setVisibility(View.VISIBLE);
                linear_title.setVisibility(View.GONE);
                send_msg.setVisibility(View.GONE);

            }
        });
        joinSession();
        btn_send=findViewById(R.id.btn_send);
        edit_text=findViewById(R.id.edit_text);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_text.length()==0){
                    Toast.makeText(LiveVideo.this, "please type message", Toast.LENGTH_SHORT).show();
                }else {
                    sendMessage(edit_text.getText().toString());
                }
            }
        });
    }


    private void sendMessage(String mesag) {
        ProgressDialog pd = ProgressDialog.show(LiveVideo.this, "", "Loading..");
        ApiServices apiServices=API_Config.getApiClient_ByPost();
        Call<ResponseSendMessage> call=apiServices.sendMessage("comments",preferencesManager.getUserId(),class_id,mesag);
        call.enqueue(new Callback<ResponseSendMessage>() {
            @Override
            public void onResponse(Call<ResponseSendMessage> call, Response<ResponseSendMessage> response) {
              pd.dismiss();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                if (response.body().getRes().equalsIgnoreCase("success")){
                   model.add(new ChatModel(
                           mesag
                   ));
                    chat.setHasFixedSize(true);
                    chat.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    chat.setAdapter(new ChatAdapter(getApplicationContext(),model));

                    edit_text.setText("");

                }else {
                    Toast.makeText(LiveVideo.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSendMessage> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        ExitExam();
    }

    private void ExitExam() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LiveVideo.this);

        alertDialog.setTitle("Exit Live Session"); // Sets title for your alertbox

        alertDialog.setMessage("Are you sure you wanted to Exit From Session ?"); // Message to be displayed on alertbox

        alertDialog.setIcon(R.mipmap.ic_launcher); // Icon for your alertbox

        /* When positive (yes/ok) is clicked */
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //goToActivityWithFinish(OnlineExam.this, OnlineTest.class,null);
                ExitApi();
            }
        });

        /* When negative (No/cancel) button is clicked*/
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void ExitApi() {
        ProgressDialog pd = ProgressDialog.show(LiveVideo.this, "", "Loading..");
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseExitLiveSession> call=apiServices.exitLiveSession("exit",preferencesManager.getUserId(),class_id);
        call.enqueue(new Callback<ResponseExitLiveSession>() {
            @Override
            public void onResponse(Call<ResponseExitLiveSession> call, Response<ResponseExitLiveSession> response) {
                pd.dismiss();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    finish();
                }else {
                    Toast.makeText(LiveVideo.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseExitLiveSession> call, Throwable t) {
                pd.dismiss();

            }
        });

    }
    private void joinSession() {
        ProgressDialog pd = ProgressDialog.show(LiveVideo.this, "", "Loading..");
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseJoinClass> call=apiServices.joinClass("join",preferencesManager.getUserId(),class_id);
        call.enqueue(new Callback<ResponseJoinClass>() {
            @Override
            public void onResponse(Call<ResponseJoinClass> call, Response<ResponseJoinClass> response) {
               pd.dismiss();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    initializeYoutubePlayer();
                }else {
                    Toast.makeText(LiveVideo.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            @Override
            public void onFailure(Call<ResponseJoinClass> call, Throwable t) {
             pd.dismiss();

            }
        });
    }

    /**
     * initialize the youtube player
     */
    private void initializeYoutubePlayer() {
        youTubePlayerView.initialize(MainActivity.API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer,
                                                boolean wasRestored) {

                //if initialization success then load the video id to youtube player
                if (!wasRestored) {
                    //set the player style here: like CHROMELESS, MINIMAL, DEFAULT
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                 // int duration= youTubePlayer.getDurationMillis();
                  //  Toast.makeText(LiveVideo.this, duration, Toast.LENGTH_SHORT).show();
                    //load the video

                    youTubePlayer.loadVideo(videoID);
                    //OR
                    //cue the video
                    //youTubePlayer.cueVideo(videoID);

                    //if you want when activity start it should be in full screen uncomment below comment
                    //  youTubePlayer.setFullscreen(true);

                    //If you want the video should play automatically then uncomment below comment
                    //  youTubePlayer.play();

                    //If you want to control the full screen event you can uncomment the below code
                    //Tell the player you want to control the fullscreen change
                   /*player.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
                    //Tell the player how to control the change
                    player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                        @Override
                        public void onFullscreen(boolean arg0) {
                            // do full screen stuff here, or don't.
                            Log.e(TAG,"Full screen mode");
                        }
                    });*/

                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                //print or show error if initialization failed
                Log.e(TAG, "Youtube Player View initialization failed");
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver),
                new IntentFilter("MyData")
        );
    }
    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            EndLiveSession();
        }
    };
    private void EndLiveSession() {
        LayoutInflater layoutInflater = LayoutInflater.from(LiveVideo.this);
        View promptView;
        promptView = layoutInflater.inflate(R.layout.dialog_session_ended, null);
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(LiveVideo.this);
        alertDialogBuilder.setView(promptView);
        alert2 = alertDialogBuilder.create();
        alert2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert2.setCancelable(false);
        TextView tv_okay=promptView.findViewById(R.id.tv_okay);
        tv_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LiveVideoFragment.check=true;
                finish();
                alert2.dismiss();
            }
        });
        alert2.show();
    }


}
