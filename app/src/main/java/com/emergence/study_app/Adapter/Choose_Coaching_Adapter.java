package com.emergence.study_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Activity.User_Detail_Activity;
import com.example.study_app.R;
import com.emergence.study_app.newTech.retrofit.model.Coaching.DataItem;

import java.util.List;

public class Choose_Coaching_Adapter extends RecyclerView.Adapter<Choose_Coaching_Adapter.Choose_ViewHolder> {
    List<DataItem> dataItems;
    Context context;

    public Choose_Coaching_Adapter(List<DataItem> dataItems, Context context) {
        this.dataItems = dataItems;
        this.context = context;
    }

    @NonNull
    @Override
    public Choose_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_coaching,parent,false);
        return new Choose_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Choose_ViewHolder holder, int position) {

        holder.c_name.setText(dataItems.get(position).getName());
        holder.layout_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context.getApplicationContext(), User_Detail_Activity.class);
                intent.putExtra("coaching_id",dataItems.get(position).getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class Choose_ViewHolder extends RecyclerView.ViewHolder {
        TextView c_name;
        LinearLayout layout_notify;
        public Choose_ViewHolder(@NonNull View itemView) {
            super(itemView);
            c_name=itemView.findViewById(R.id.t_coaching);
            layout_notify=itemView.findViewById(R.id.layout);
        }
    }
}
