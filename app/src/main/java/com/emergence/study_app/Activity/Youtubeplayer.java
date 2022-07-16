package com.emergence.study_app.Activity;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.responseVideoCountRemove.ResponseVideoCountRemove;
import com.example.study_app.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.skydoves.elasticviews.ElasticImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//
public class Youtubeplayer extends YouTubeBaseActivity{
    private static final String TAG = Youtubeplayer.class.getSimpleName();
    private String videoID;
    private YouTubePlayerView youTubePlayerView;
    RecyclerView recycler_video;
    String course_Id="";
    String videoID1="";
    SeekBar seekbar;
    YouTubePlayer mYouTubePlayer;
    private Handler mHandler = new Handler();
    int playPercent;
    TextView tv_time,tv_end_time,tv_desc,tv_title;
    ImageView iv_play_pause,full_view_exit;
    PreferencesManager preferencesManager;
    String type="";
    ElasticImageView iv_backword,iv_forword;
    int progress=0;
    RelativeLayout rl_seekbar,rl_cantrol;
    LinearLayout ll_desc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_demo);

        preferencesManager=new PreferencesManager(Youtubeplayer.this);
        type=getIntent().getStringExtra("type");
        //get the video id
        videoID1 = getIntent().getStringExtra("video_id");
        String[] separated = videoID1.split("watch?v=");
        if (separated.length==2){
            videoID=separated[1].toString();
        }else {
            videoID=videoID1;
        }

        ll_desc=findViewById(R.id.ll_desc);
        rl_seekbar=findViewById(R.id.rl_seekbar);
        rl_cantrol=findViewById(R.id.rl_cantrol);
        tv_title=findViewById(R.id.tv_title);
        tv_desc=findViewById(R.id.tv_desc);
        tv_title.setText(getIntent().getStringExtra("title"));
        tv_desc.setText(getIntent().getStringExtra("desc"));
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        seekbar=findViewById(R.id.seekbar);
        seekbar.setOnSeekBarChangeListener(mVideoSeekBarChangeListener);
        tv_time=findViewById(R.id.tv_time);
        tv_end_time=findViewById(R.id.tv_end_time);

        initializeYoutubePlayer();


        ImageView full_view=findViewById(R.id.full_view);
        full_view_exit=findViewById(R.id.full_view_exit);
        full_view_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (Youtubeplayer.this).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                full_view.setVisibility(View.VISIBLE);
                full_view_exit.setVisibility(View.GONE);
            }
        });
        full_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (Youtubeplayer.this).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                full_view.setVisibility(View.GONE);
                full_view_exit.setVisibility(View.VISIBLE);


            }
        });
        iv_play_pause=findViewById(R.id.iv_play_pause);
        iv_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mYouTubePlayer.isPlaying()){
                    mYouTubePlayer.pause();
                    iv_play_pause.setBackgroundResource(R.drawable.ic_play);
                }else {
                    mYouTubePlayer.play();
                    iv_play_pause.setBackgroundResource(R.drawable.ic_pause);
                }
            }
        });

        iv_forword=findViewById(R.id.iv_forword);
        iv_forword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                long lengthPlayed = (mYouTubePlayer.getDurationMillis() * seekbar.getProgress()) / 100;
//                mYouTubePlayer.seekToMillis((int) (lengthPlayed+10));
//                seekbar.setProgress( (mYouTubePlayer.getDurationMillis() * seekbar.getProgress()) / 100);
//

                int seconds = (int)((mYouTubePlayer.getCurrentTimeMillis() / 1000) % 60);
                mYouTubePlayer.seekToMillis(mYouTubePlayer.getCurrentTimeMillis()+10000);
//                Toast.makeText(Youtubeplayer.this, mYouTubePlayer.getCurrentTimeMillis()+"", Toast.LENGTH_SHORT).show();
            }
        });
        iv_backword=findViewById(R.id.iv_backword);
        iv_backword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                long lengthPlayed = (mYouTubePlayer.getDurationMillis() * seekbar.getProgress()) / 100;
//                mYouTubePlayer.seekToMillis((int) (lengthPlayed-10));
//                seekbar.setProgress( (mYouTubePlayer.getDurationMillis() * seekbar.getProgress()) / 100);

                mYouTubePlayer.seekToMillis(mYouTubePlayer.getCurrentTimeMillis()-10000);

            }
        });


    }

    SeekBar.OnSeekBarChangeListener mVideoSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

            long lengthPlayed = (mYouTubePlayer.getDurationMillis() * seekBar.getProgress()) / 100;
            mYouTubePlayer.seekToMillis((int) lengthPlayed);
        }
    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run(){
            displayCurrentTime();
            mHandler.postDelayed(this, 100);
        }
    };
    private String formatTime(int millis) {
        int seconds = millis / 1000;
        int minutes = seconds / 60;
        int hours = minutes / 60;

        return (hours == 0 ? "" : hours + ":") + String.format("%02d:%02d", minutes % 60, seconds % 60);
    }




    private void displayCurrentTime() {
        if (null == mYouTubePlayer) return;
        String formattedTime = formatTime(mYouTubePlayer.getDurationMillis() - mYouTubePlayer.getCurrentTimeMillis());
        tv_time.setText(formattedTime);
        playPercent = (int) (((float) mYouTubePlayer.getCurrentTimeMillis()/(float) mYouTubePlayer.getDurationMillis()) * 100);
        //  System.out.println("get youtube displayTime 2 : "+playPercent);
        // update live progress
        seekbar.setProgress(playPercent, true);
    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {
            mHandler.postDelayed(runnable,100);
            displayCurrentTime();
        }

        @Override
        public void onPaused() {
            mHandler.removeCallbacks(runnable);
        }

        @Override
        public void onStopped() {
            mHandler.removeCallbacks(runnable);
        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {
            mHandler.postDelayed(runnable,100);
        }
    };
    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {
            displayCurrentTime();
            String formattedTime = formatTime(mYouTubePlayer.getDurationMillis());
            tv_end_time.setText(formattedTime);




        }

        @Override
        public void onVideoEnded() {
            // mYouTubePlayer.loadVideo(videoID);

            if (type.equalsIgnoreCase("free")){
                mYouTubePlayer.loadVideo(videoID);
            }else {
                CallCountApi();
            }


        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

    private void CallCountApi() {
        ProgressDialog pd = ProgressDialog.show(Youtubeplayer.this, "", "Loading..");
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseVideoCountRemove> call=apiServices.getVideoCount("video_view",getIntent().getStringExtra("v_id"),preferencesManager.getUserId());
        call.enqueue(new Callback<ResponseVideoCountRemove>() {
            @Override
            public void onResponse(Call<ResponseVideoCountRemove> call, Response<ResponseVideoCountRemove> response) {
                pd.dismiss();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    Toast.makeText(Youtubeplayer.this, "Video Ended", Toast.LENGTH_SHORT).show();

                    finish();
                }else {

                }
            }

            @Override
            public void onFailure(Call<ResponseVideoCountRemove> call, Throwable t) {
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
                if(youTubePlayer != null) {
                    mYouTubePlayer = youTubePlayer;
                    youTubePlayer.loadVideo(videoID);

                    displayCurrentTime();

                    // start buffering

                    // set style and show control layout
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                    //  videoControlLayout.setVisibility(View.VISIBLE);

                    youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
                    youTubePlayer.setPlaybackEventListener(playbackEventListener);


/*
                    youTubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                        @Override
                        public void onFullscreen(boolean arg0) {
                            // do full screen stuff here, or don't.
                            Log.e(TAG,"Full screen mode");
                        }
                    });
*/
                }

             /*   //if initialization success then load the video id to youtube player
                if (!wasRestored) {
                    //set the player style here: like CHROMELESS, MINIMAL, DEFAULT
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                    youTubePlayer.setShowFullscreenButton(true);
                    youTubePlayer.loadVideo(videoID);
//                    int duration= youTubePlayer.getDurationMillis();
//                    Toast.makeText(Youtubeplayer.this, ""+duration+"", Toast.LENGTH_SHORT).show();
                  //  youTubePlayer.setShowFullscreenButton(false);

                    //load the video



                    //OR

                    //cue the video
                    //youTubePlayer.cueVideo(videoID);

                    //if you want when activity start it should be in full screen uncomment below comment
                    //  youTubePlayer.setFullscreen(true);

                    //If you want the video should play automatically then uncomment below comment
                    //  youTubePlayer.play();

                    //If you want to control the full screen event you can uncomment the below code
                    //Tell the player you want to control the fullscreen change
              // youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
                    //Tell the player how to control the change

                  *//*  player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                        @Override
                        public void onFullscreen(boolean arg0) {
                            // do full screen stuff here, or don't.
                            Log.e(TAG,"Full screen mode");
                        }
                    });*//*



                }*/
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                //print or show error if initialization failed
                Log.e(TAG, "Youtube Player View initialization failed");
            }
        });
    }

    @Override
    protected void onResume() {
        // mYouTubePlayer.loadVideo(videoID);
        super.onResume();
        iv_play_pause.setBackgroundResource(R.drawable.ic_pause);
    }

    @Override
    protected void onPause() {
        super.onPause();
        iv_play_pause.setBackgroundResource(R.drawable.ic_play);
    }
}
