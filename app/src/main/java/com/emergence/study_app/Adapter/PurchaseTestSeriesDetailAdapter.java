package com.emergence.study_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.newTech.retrofit.model.responseTestSeriesDetails.DataItem;
import com.emergence.study_app.testSeriese.MVPClass;
import com.example.study_app.R;

import java.util.List;

public class PurchaseTestSeriesDetailAdapter extends RecyclerView.Adapter<PurchaseTestSeriesDetailAdapter.AdapterViewHolder> {
    Context context;
    List<DataItem> dataItems;
    MVPClass mvpClass;

    public PurchaseTestSeriesDetailAdapter(Context context, List<DataItem> dataItems,MVPClass mvpClass) {
        this.context = context;
        this.dataItems = dataItems;
        this.mvpClass=mvpClass;
    }
    @NonNull
    @Override
    public PurchaseTestSeriesDetailAdapter.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.purchased_test_list,parent,false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseTestSeriesDetailAdapter.AdapterViewHolder holder, int position) {
        holder.date_time.setText("Date: "+dataItems.get(position).getDate()+","+dataItems.get(position).getTime());
        holder.tv_title.setText(dataItems.get(position).getName());
        holder.tv_totl_quest.setText("No. of Quest: "+dataItems.get(position).getQuestions());
        holder.tv_timing.setText("Duration: "+dataItems.get(position).getTimming());
        holder.tv_text.setText(dataItems.get(position).getName().charAt(0)+"");

        if (dataItems.get(position).getIsAttempt().equalsIgnoreCase("true")){
            holder.tv_ranking.setVisibility(View.VISIBLE);
            holder.tv_result.setVisibility(View.VISIBLE);
            holder.tv_start_test.setVisibility(View.GONE);
        }else {
            holder.tv_ranking.setVisibility(View.GONE);
            holder.tv_result.setVisibility(View.GONE);
            holder.tv_start_test.setVisibility(View.VISIBLE);
        }

        holder.tv_start_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mvpClass.giveAnswer(dataItems.get(position).getId(),dataItems.get(position).getName(),dataItems.get(position).getTimming());
            }
        });

        holder.tv_ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mvpClass.giveAnswer(dataItems.get(position).getId(),dataItems.get(position).getName(),"rank");
            }
        });
        holder.tv_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mvpClass.giveAnswer(dataItems.get(position).getId(),dataItems.get(position).getName(),"result");
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tv_text,tv_title,tv_totl_quest,tv_timing,date_time,tv_start_test,tv_ranking,tv_result;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_result=itemView.findViewById(R.id.tv_result);
            tv_ranking=itemView.findViewById(R.id.tv_ranking);
            tv_start_test=itemView.findViewById(R.id.tv_start_test);
            tv_text=itemView.findViewById(R.id.tv_text);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_totl_quest=itemView.findViewById(R.id.tv_totl_quest);
            tv_timing=itemView.findViewById(R.id.tv_timing);
            date_time=itemView.findViewById(R.id.date_time);
        }
    }
}
