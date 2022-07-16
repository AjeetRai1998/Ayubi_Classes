package com.emergence.study_app.exoPlayer;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.study_app.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.util.Util;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

public class ExoPlayerActivity extends AppCompatActivity {
    String videoID1 = "";
    private String BASE_URL = "https://www.youtube.com";
    private String mYoutubeLink = "";

    PlayerView playerView;
    SimpleExoPlayer player;
    boolean playWhenReady=true;
    int currentWindow=0;
    long playbackPosition=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player);
        videoID1 = getIntent().getStringExtra("video_id");
        mYoutubeLink = BASE_URL + "/watch?v=" + videoID1;

        playerView=findViewById(R.id.mPlayerView);
        initPlayer();

        //  extractYoutubeUrl();

//        new YouTubeExtractor(this) {
//            @Override
//            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
//                if (ytFiles != null) {
//                    int itag = 22;
//                    String downloadUrl = ytFiles.get(itag).getUrl();
//                    playVideo(downloadUrl);
//                }
//            }
//        }.extract(mYoutubeLink,true,true);


//        new YoutubeStreamExtractor(new YoutubeStreamExtractor.ExtractorListner(){
//            @Override
//            public void onExtractionDone(List<YTMedia> adaptiveStream, final List<YTMedia> mixedStream, List<YTSubtitles> subtitles, YoutubeMeta meta) {
//                //url to get subtitle
//                String subUrl=subtitles.get(0).getBaseUrl();
//                playVideo(subUrl);
//
//            }
//            @Override
//            public void onExtractionGoesWrong(final ExtractorException e) {
//                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        }).useDefaultLogin().Extract(mYoutubeLink);
//
//
//    }
/*
        new YouTubeExtractor(this) {
            @Override
            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                if (ytFiles != null) {
                    int itag = 22;
                    String downloadUrl = ytFiles.get(itag).getUrl();
                    playVideo(downloadUrl);
                }
            }
        }.extract(mYoutubeLink, true, true);
*/
    }

    private void initPlayer() {
        player=new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        playYoutubeVideo("https://www.youtube.com/watch?v=QJPif-TB1jM");
    }

    private void playYoutubeVideo(String urlv) {
        new YouTubeExtractor(this){

            @Override
            protected void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta videoMeta) {

                if (ytFiles!=null){
                    int audioTAg=140;
                    int videoTAg=137;
                    MediaSource audioSource=new ProgressiveMediaSource
                            .Factory(new DefaultHttpDataSource.Factory())
                            .createMediaSource(MediaItem.fromUri(ytFiles.get(audioTAg).getUrl()));
                    MediaSource videoSource=new ProgressiveMediaSource
                            .Factory(new DefaultHttpDataSource.Factory())
                            .createMediaSource(MediaItem.fromUri(ytFiles.get(videoTAg).getUrl()));
                    player.setMediaSource(new MergingMediaSource(
                            true,
                            videoSource,
                            audioSource),
                            true
                    );
                    player.prepare();
                    player.setPlayWhenReady(playWhenReady);
                    player.seekTo(currentWindow,playbackPosition);
                }else {
                    Toast.makeText(ExoPlayerActivity.this, "null found", Toast.LENGTH_SHORT).show();
                }
            }
        }.extract("https://www.youtube.com/watch?v=QJPif-TB1jM",true,true);

    }

    @Override
    protected void onStart() {

        super.onStart();
        if (Util.SDK_INT>=24){
            initPlayer();
        }
    }

    @Override
    protected void onStop() {
        if (Util.SDK_INT>=24){
            releasePlayer();
        }
        super.onStop();

    }

    private void releasePlayer() {
        if (player!=null){
            playWhenReady=player.getPlayWhenReady();
            playbackPosition=player.getCurrentPosition();
            currentWindow=player.getCurrentWindowIndex();
            player.release();
            player=null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Util.SDK_INT < 24 || player==null){
            initPlayer();
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE|
                View.SYSTEM_UI_FLAG_FULLSCREEN|
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                );
    }

    @Override
    protected void onPause() {
        if (Util.SDK_INT<24){
            releasePlayer();
        }
        super.onPause();
    }
    /* private void extractYoutubeUrl() {
        new YouTubeExtractor(this) {
            @Override
            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                if (ytFiles != null) {
                    playVideo(ytFiles.get(17).getUrl());
                }
            }
        }.extract(mYoutubeLink, true, true);
    }
    private void playVideo(String downloadUrl) {
        SimpleExoPlayerView simpleExoPlayer = findViewById(R.id.mPlayerView);
        simpleExoPlayer.setPlayer(Exoplayer.getSharedInstance(ExoPlayerActivity.this).getSimpleExoPlayerView().getPlayer());
        Exoplayer.getSharedInstance(ExoPlayerActivity.this).playStream(downloadUrl);
    }*/

//        private void playVideo (String downloadUrl){
//            SimpleExoPlayerView mPlayerView = findViewById(R.id.mPlayerView);
//            mPlayerView.setPlayer(ExoPlayerManager.getSharedInstance(ExoPlayerActivity.this).getPlayerView().getPlayer());
//            ExoPlayerManager.getSharedInstance(ExoPlayerActivity.this).playStream(downloadUrl);
//        }
    }
