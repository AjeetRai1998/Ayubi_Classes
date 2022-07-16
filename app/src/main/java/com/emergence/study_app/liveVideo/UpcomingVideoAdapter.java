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

public class UpcomingVideoAdapter extends RecyclerView.Adapter<UpcomingVideoAdapter.AdapterViewHolder> {
    Context context;
    List<LiveEvents> model;

    public UpcomingVideoAdapter(Context context, List<LiveEvents> model) {
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.upcoming_list,parent,false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        holder.tv_class.setText(model.get(position).getClasses());
        holder.topic.setText(model.get(position).getTopic());
        holder.tv_date.setText(model.get(position).getLivedatetime());
        holder.timing.setText(model.get(position).getDuration());
        holder.tv_desc.setText(Html.fromHtml(model.get(position).getDesc()));
        holder.subject.setText(model.get(position).getSubject());



    }

    @Override
    public int getItemCount() {
        return model.size();
    }
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_class,tv_date,topic,subject,timing,tv_desc;
        public AdapterViewHolder(@NonNull View itemView) {
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
