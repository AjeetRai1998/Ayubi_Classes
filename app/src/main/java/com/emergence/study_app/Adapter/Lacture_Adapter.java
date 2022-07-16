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

import com.emergence.study_app.Activity.Notes_Video_Activity;
import com.emergence.study_app.newTech.retrofit.model.Lacture.DataItem;
import com.example.study_app.R;

import java.util.List;

public class Lacture_Adapter extends RecyclerView.Adapter<Lacture_Adapter.Lacture_ViewHolder> {
    List<DataItem> lacture;
    Context context;

    public Lacture_Adapter(List<DataItem> lacture, Context context) {
        this.lacture = lacture;
        this.context = context;
    }

    @NonNull
    @Override
    public Lacture_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_chapter,parent,false);
        return new Lacture_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Lacture_ViewHolder holder, int position) {
        holder.t_name.setText(lacture.get(position).getName());
        holder.count.setText((position+1)+"");
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notes_Video_Activity.lacture_name=lacture.get(position).getName();
                Notes_Video_Activity.package_id=lacture.get(position).getPackageId();
                Notes_Video_Activity.lact=lacture.get(position).getId();
                Notes_Video_Activity.chaptr_id=lacture.get(position).getChapterId();
//                Notes_Fragment.dataItem=lacture.get(position);
                Intent intent=new Intent(context.getApplicationContext(), Notes_Video_Activity.class);
                intent.putExtra("pkg_id",lacture.get(position).getPackageId());
                intent.putExtra("chptr_id",lacture.get(position).getChapterId());
                intent.putExtra("lacture_id",lacture.get(position).getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lacture.size();
    }

    public class Lacture_ViewHolder extends RecyclerView.ViewHolder {
        TextView t_name,count;
        LinearLayout layout;
        public Lacture_ViewHolder(@NonNull View itemView) {
            super(itemView);
            t_name=itemView.findViewById(R.id.title);
            layout=itemView.findViewById(R.id.c_layt);
            count=itemView.findViewById(R.id.count);
        }
    }
}
