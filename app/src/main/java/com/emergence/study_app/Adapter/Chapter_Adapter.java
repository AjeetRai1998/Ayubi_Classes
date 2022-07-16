package com.emergence.study_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Activity.Lacture_Activity;
import com.emergence.study_app.newTech.retrofit.model.Chapter.DataItem;
import com.example.study_app.R;

import java.util.List;

public class Chapter_Adapter extends RecyclerView.Adapter<Chapter_Adapter.Chapter_ViewHolder> {
    List<DataItem> chapterData;
    Context context;

    public Chapter_Adapter(List<DataItem> chapterData, Context context) {
        this.chapterData = chapterData;
        this.context = context;
    }

    @NonNull
    @Override
    public Chapter_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.item_chapter,parent,false);
        return new Chapter_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Chapter_ViewHolder holder, int position) {
        holder.t_name.setText(chapterData.get(position).getName());
        holder.count.setText((position+1)+"");
        holder.t_name.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.t_name.setHorizontallyScrolling(true);
        holder.t_name.setSelected(true);
        holder.t_name.setSingleLine(true);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Lacture_Activity.chapterdata=chapterData.get(position);
                Lacture_Activity.statusTransit=0;
                Intent intent=new Intent(context.getApplicationContext(), Lacture_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("id",chapterData.get(position).getPackageId());
                intent.putExtra("name",chapterData.get(position).getName());
                intent.putExtra("chapt_id",chapterData.get(position).getId());
                intent.putExtra("chapter", "chapter");
                context.getApplicationContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return chapterData.size();
    }

    public class Chapter_ViewHolder extends RecyclerView.ViewHolder {
        TextView t_name,count;
        LinearLayout layout;
        public Chapter_ViewHolder(@NonNull View itemView) {
            super(itemView);
            t_name=itemView.findViewById(R.id.title);
            layout=itemView.findViewById(R.id.c_layt);
            count=itemView.findViewById(R.id.count);
        }
    }
}
