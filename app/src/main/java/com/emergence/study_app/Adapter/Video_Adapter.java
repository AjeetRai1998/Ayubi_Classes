package com.emergence.study_app.Adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Activity.MainActivity;
import com.emergence.study_app.Activity.Youtubeplayer;
import com.emergence.study_app.newTech.retrofit.model.Video.DataItem;
import com.example.study_app.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.List;



public class Video_Adapter extends RecyclerView.Adapter<Video_Adapter.Video_ViewHolder> {
    List<DataItem> dataItems;
    Context context;

    public Video_Adapter(List<DataItem> dataItems, Context context) {
        this.dataItems = dataItems;
        this.context = context;
    }

    @NonNull
    @Override
    public Video_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view =layoutInflater.inflate(R.layout.video_list,parent,false);
        return new Video_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Video_ViewHolder holder, int position) {
        holder.title.setText(dataItems.get(position).getTitle());
        holder.tv_date.setText(dataItems.get(position).getDate());
        holder.tv_video_count.setText(dataItems.get(position).getView_complete()+"/"+dataItems.get(position).getVideoCount());
        holder.youtybe_view.initialize(MainActivity.API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                //when initialization is sucess, set the video id to thumbnail to load
                youTubeThumbnailLoader.setVideo(dataItems.get(position).getUrl());

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
                        Log.e(TAG, "Youtube Thumbnail Error");
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                //print or show error when initialization failed
                Log.e(TAG, "Youtube Initialization Failure");
                Toast.makeText(context, "Youtube Initialization Failure", Toast.LENGTH_SHORT).show();

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataItems.get(position).getVideoCount().equalsIgnoreCase(dataItems.get(position).getView_complete())){
                    Toast.makeText(context, "Your watch limit completed for this video", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent=new Intent(context, Youtubeplayer.class);
                    // Intent intent=new Intent(context, ExoPlayerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("video_id",dataItems.get(position).getUrl());
                    intent.putExtra("title",dataItems.get(position).getTitle());
                    intent.putExtra("desc",dataItems.get(position).getDate());
                    intent.putExtra("v_id",dataItems.get(position).getId());
                    intent.putExtra("type","paid");
                    context.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class  Video_ViewHolder extends RecyclerView.ViewHolder {
        TextView title,tv_date,tv_video_count;
        YouTubeThumbnailView youtybe_view;
        public Video_ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_video_count=itemView.findViewById(R.id.tv_video_count);
            youtybe_view=itemView.findViewById(R.id.youtybe_view);
            tv_date=itemView.findViewById(R.id.tv_date);
            title=itemView.findViewById(R.id.tv_title);
        }
    }
}
