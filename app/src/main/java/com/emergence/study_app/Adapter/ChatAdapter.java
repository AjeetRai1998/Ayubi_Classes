package com.emergence.study_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Activity.ChatModel;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.example.study_app.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.AdapterViewHolder> {
    Context context;
    List<ChatModel> model;
    PreferencesManager preferencesManager;

    public ChatAdapter(Context context, List<ChatModel> model) {
        this.context = context;
        this.model = model;
        preferencesManager=new PreferencesManager(context);
    }

    @NonNull
    @Override
    public ChatAdapter.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.chat_list,parent,false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.AdapterViewHolder holder, int position) {
        holder.message.setText(model.get(position).getMessage());
        holder.tv_name.setText(preferencesManager.getFull_Name());

    }

    @Override
    public int getItemCount() {
        return model.size();
    }
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,message;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.message);
            tv_name=itemView.findViewById(R.id.tv_name);
        }
    }
}
