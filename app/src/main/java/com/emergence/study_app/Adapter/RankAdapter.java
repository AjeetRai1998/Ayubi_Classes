package com.emergence.study_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.newTech.retrofit.model.responseRank.DataItem;
import com.example.study_app.R;

import java.util.List;


public class RankAdapter extends RecyclerView.Adapter<RankAdapter.AdapterViewHolder> {
    Context context;
    List<DataItem> model;

    public RankAdapter(Context context, List<DataItem> model) {
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public RankAdapter.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.rank_list,parent,false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankAdapter.AdapterViewHolder holder, int position) {
        if (model.get(position).getName()!=null){
            holder.name.setText(model.get(position).getName());
        }
       holder.mark.setText(model.get(position).getMarks()+"");
        holder.total.setText(model.get(position).getTotalMarks()+"");
        holder.rank.setText((position+1)+"");

    }

    @Override
    public int getItemCount() {
        return model.size();
    }
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView total,mark,rank,name;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            total=itemView.findViewById(R.id.total);
            mark=itemView.findViewById(R.id.mark);
            rank=itemView.findViewById(R.id.rank);
            name=itemView.findViewById(R.id.name);
        }
    }
}
