package com.emergence.study_app.liveVideo;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.study_app.R;

import java.util.List;

public class CompletedVideoAdapter extends RecyclerView.Adapter<CompletedVideoAdapter.AdpaterViewHolder> {
    Context context;
    List<LiveEvents> model;

    public CompletedVideoAdapter(Context context, List<LiveEvents> model) {
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public AdpaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.upcoming_list,parent,false);
        return new AdpaterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpaterViewHolder holder, int position) {
      /*  holder.tv_title.setText(model.get(position).getTopic()+" By "+model.get(position).getLiveuserid());
        holder.desc.setText(model.get(position).getDesc());
        holder.date_time.setText(model.get(position).getLivedatetime());

        holder.youtybe_view.initialize(MainActivity.API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                //when initialization is sucess, set the video id to thumbnail to load
                youTubeThumbnailLoader.setVideo(model.get(position).getUrl());

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
                Intent intent=new Intent(context, Youtubeplayer.class).putExtra("video_id",model.get(position).getUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(intent);
            }
        });
*/

        holder.tv_class.setText(model.get(position).getClasses());
        holder.topic.setText(model.get(position).getTopic());
        holder.timing.setText(model.get(position).getDuration());
        holder.tv_desc.setText(Html.fromHtml(model.get(position).getDesc()));
        holder.subject.setText(model.get(position).getSubject());

       // String date_after = Utils.formateDateFromstring("yyyy-MM-dd", "dd, MMM yyyy", model.get(position).getLivedatetime());
        holder.tv_date.setText(model.get(position).getLivedatetime());
    }

    @Override
    public int getItemCount() {
        return model.size();
    }
    public class AdpaterViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_class,tv_date,topic,subject,timing,tv_desc;
        public AdpaterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_desc=itemView.findViewById(R.id.tv_desc);
            timing=itemView.findViewById(R.id.timing);
            subject=itemView.findViewById(R.id.subject);
            topic=itemView.findViewById(R.id.topic);
            tv_date=itemView.findViewById(R.id.tv_date);
            tv_class=itemView.findViewById(R.id.tv_class);
            tv_name=itemView.findViewById(R.id.tv_name);
        }
    }
}
