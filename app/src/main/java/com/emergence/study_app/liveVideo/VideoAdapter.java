package com.emergence.study_app.liveVideo;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Activity.MainActivity;
import com.example.study_app.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.AdapterViewholder> {
    Context context;
    List<LiveEvents> model;
    String videoID="";

    public VideoAdapter(Context context, List<LiveEvents> model) {
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public AdapterViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.video_all_list,parent,false);
        return new AdapterViewholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterViewholder holder, int position) {
        holder.tv_title.setText(model.get(position).getTopic());
        holder.desc.setText(Html.fromHtml(model.get(position).getDesc()));

        String videoID1 = model.get(position).getUrl();
        String separated = videoID1.replace("https://www.youtube.com/watch?v=","");

            videoID=separated;


        holder.youtybe_view.initialize(MainActivity.API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                //when initialization is sucess, set the video id to thumbnail to load
                youTubeThumbnailLoader.setVideo(videoID);

                // holder.progressBar2.setVisibility(View.VISIBLE);
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        //when thumbnail loaded successfully release the thumbnail loader as we are showing thumbnail in adapter
                        // holder.progressBar2.setVisibility(View.GONE);
                        youTubeThumbnailLoader.release();
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                        //print or show error when thumbnail load failed
                        //  Log.e(TAG, "Youtube Thumbnail Error");
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                //print or show error when initialization failed
                // Log.e(TAG, "Youtube Initialization Failure");

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, LiveVideo.class);
               // Intent intent=new Intent(context, ExoPlayerActivity.class);
                intent.putExtra("video_id",videoID);
                intent.putExtra("classid",model.get(position).getId());
                intent.putExtra("title",model.get(position).getTopic());
                intent.putExtra("desc",model.get(position).getLivedatetime());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return model.size();
    }
    public class AdapterViewholder extends RecyclerView.ViewHolder {
        TextView tv_title,desc;
        YouTubeThumbnailView youtybe_view;
        public AdapterViewholder(@NonNull View itemView) {
            super(itemView);
            desc=itemView.findViewById(R.id.tv_date);
            tv_title=itemView.findViewById(R.id.title);
            youtybe_view=itemView.findViewById(R.id.youtube_view);
        }
    }
}
