package com.emergence.study_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Fragments.Home_Fragment;
import com.emergence.study_app.newTech.retrofit.model.Class.DataItem;
import com.emergence.study_app.newTech.retrofit.model.Get_User.Data;
import com.example.study_app.R;

import java.util.List;

public class Class_Adapter extends RecyclerView.Adapter<Class_Adapter.Class_ViewHolder> {
    List<DataItem> dataItems;
    Context context;

    public Class_Adapter(List<DataItem> dataItems, Context context) {
        this.dataItems = dataItems;
        this.context = context;
    }

    @NonNull
    @Override
    public Class_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.class_item,parent,false);
        return new Class_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Class_ViewHolder holder, int position) {
        holder.class_name.setText(dataItems.get(position).getName());
        holder.txt_name.setText(dataItems.get(position).getName().split(" ")[0]);
        holder.class_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new Home_Fragment();
                Bundle bundle=new Bundle();
                bundle.putString("class",dataItems.get(position).getId());
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.parent_frame, myFragment).addToBackStack(null).commit();


            }
        });

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class Class_ViewHolder extends RecyclerView.ViewHolder {
        TextView class_name,txt_name;
        LinearLayout class_layout;
        public Class_ViewHolder(@NonNull View itemView) {
            super(itemView);
            class_name=itemView.findViewById(R.id.class_name);
            class_layout=itemView.findViewById(R.id.class_layout);
            txt_name=itemView.findViewById(R.id.txt_name);
        }
    }
}
