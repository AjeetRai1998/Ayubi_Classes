package com.emergence.study_app.Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Help_Support_Adapter extends RecyclerView.Adapter<Help_Support_Adapter.Help_ViewHolder> {
    @NonNull
    @Override
    public Help_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Help_ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Help_ViewHolder extends RecyclerView.ViewHolder {
        public Help_ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
