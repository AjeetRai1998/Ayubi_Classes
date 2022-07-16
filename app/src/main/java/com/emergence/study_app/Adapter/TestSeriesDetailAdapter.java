package com.emergence.study_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.newTech.retrofit.model.responseTestSeriesDetails.DataItem;
import com.example.study_app.R;

import java.util.List;

public class TestSeriesDetailAdapter extends RecyclerView.Adapter<TestSeriesDetailAdapter.AdapterViewHolder> {
    Context context;
    List<DataItem> dataItems;

    public TestSeriesDetailAdapter(Context context, List<DataItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public TestSeriesDetailAdapter.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.test_series_detail_list,parent,false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestSeriesDetailAdapter.AdapterViewHolder holder, int position) {
        holder.date_time.setText("Date: "+dataItems.get(position).getDate()+","+dataItems.get(position).getTime());
        holder.tv_title.setText(dataItems.get(position).getName());
        holder.tv_totl_quest.setText("No. of Quest: "+dataItems.get(position).getQuestions());
        holder.tv_timing.setText("Duration: "+dataItems.get(position).getTimming());
        holder.tv_text.setText(dataItems.get(position).getName().charAt(0)+"");

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tv_text,tv_title,tv_totl_quest,tv_timing,date_time,tv_start_test;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_text=itemView.findViewById(R.id.tv_text);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_totl_quest=itemView.findViewById(R.id.tv_totl_quest);
            tv_timing=itemView.findViewById(R.id.tv_timing);
            date_time=itemView.findViewById(R.id.date_time);
        }
    }
}
