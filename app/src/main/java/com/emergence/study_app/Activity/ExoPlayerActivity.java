package com.emergence.study_app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.emergence.study_app.newTech.utils.Utils;
import com.example.study_app.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.util.Util;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

public class ExoPlayerActivity extends AppCompatActivity {
    SimpleExoPlayer player;
    PlayerView playerView;
    boolean playwhenready=true;
    int currentWindow=0;
    long playbackPosition=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player2);
         playerView=(PlayerView) findViewById(R.id.video_player_view);
         initplayer();

    }

    private void initplayer() {
        player=new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        playYoutubeVideo("https://www.youtube.com/watch?v=KyUT37gNm5w");
    }

    private void playYoutubeVideo(String url) {
        new YouTubeExtractor(this)
        {
            @Override
            protected void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta videoMeta) {
                if (ytFiles!=null){
                    int videoTag=137;// for 1080
                    int audioTag=140;//for m4a
                    MediaSource audioSource=new ProgressiveMediaSource
                            .Factory(new DefaultHttpDataSource.Factory())
                            .createMediaSource(MediaItem.fromUri(ytFiles.get(audioTag).getUrl()));

                    MediaSource videoSource=new ProgressiveMediaSource
                            .Factory(new DefaultHttpDataSource.Factory())
                            .createMediaSource(MediaItem.fromUri(ytFiles.get(videoTag).getUrl()));

                    player.setMediaSource(new MergingMediaSource(
                            true,
                            videoSource,
                            audioSource),
                            true
                    );
                    player.prepare();
                    player.setPlayWhenReady(playwhenready);
                    player.seekTo(currentWindow,playbackPosition);
                }else {
                    Toast.makeText(ExoPlayerActivity.this, "not initilize", Toast.LENGTH_SHORT).show();
                }

            }
        }.extract(url,false,true);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Util.SDK_INT>=24)
        initplayer();
    }

    @Override
    protected void onStop() {

        if (Util.SDK_INT>=24)
        releasePlayer();

        super.onStop();
    }

    private void releasePlayer() {
        if (player!=null){
            playwhenready=player.getPlayWhenReady();
            playbackPosition=player.getCurrentPosition();
            currentWindow=player.getCurrentWindowIndex();
            player.release();
            player=null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Util.SDK_INT<24 || player==null){
          initplayer();
            hideSystemUi();
        }
    }

    private void hideSystemUi() {
        playerView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
    }

    @Override
    protected void onPause() {
        if (Util.SDK_INT<24)
            releasePlayer();

        super.onPause();
    }
}