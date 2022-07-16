package com.emergence.study_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.newTech.retrofit.model.responseTestSeriese.DataItem;
import com.emergence.study_app.testSeriese.TestSerieseDetails;
import com.example.study_app.R;

import java.util.List;

public class TestSeriesAdapter extends RecyclerView.Adapter<TestSeriesAdapter.AdapterViewHolder> {
   Context context;
   List<DataItem> dataItems;

    public TestSeriesAdapter(Context context, List<DataItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public TestSeriesAdapter.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.test_series_list,parent,false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestSeriesAdapter.AdapterViewHolder holder, int position) {
        holder.exam_title.setText(dataItems.get(position).getName());
        holder.duration.setText(dataItems.get(position).getCoachingId());
        holder.tv_total_qest.setText("Expiry date: "+dataItems.get(position).getExpirydate());
        holder.date_time.setText("Date: "+dataItems.get(position).getDate()+" "+dataItems.get(position).getTime());
       // Glide.with(context).load(dataItems.get(position).getImage()).into(holder.image);
        holder.tv_short_desc.setText("Description:- "+ Html.fromHtml(dataItems.get(position).getDescription()));
        holder.tv_price.setText("\u20B9 "+dataItems.get(position).getPrice());
        holder.tv_view_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, TestSerieseDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("id",dataItems.get(position).getId());
                intent.putExtra("price",dataItems.get(position).getPrice());
                intent.putExtra("name",dataItems.get(position).getName());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }
    public class  AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView exam_title,duration,tv_total_qest,date_time,tv_short_desc,tv_price,tv_view_detail;
        ImageView image;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_view_detail=itemView.findViewById(R.id.tv_view_detail);
            tv_price=itemView.findViewById(R.id.tv_price);
            tv_short_desc=itemView.findViewById(R.id.tv_short_desc);
            image=itemView.findViewById(R.id.image);
            exam_title=itemView.findViewById(R.id.exam_title);
            duration=itemView.findViewById(R.id.duration);
            tv_total_qest=itemView.findViewById(R.id.tv_total_qest);
            date_time=itemView.findViewById(R.id.date_time);
        }
    }
}
