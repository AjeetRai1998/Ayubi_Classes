package com.emergence.study_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.newTech.retrofit.model.responseJumpQuestion.DataItem;
import com.emergence.study_app.quiz.QuizQuestion;
import com.emergence.study_app.testSeriese.MVPClass;
import com.example.study_app.R;

import java.util.List;


public class JumpAdapter extends RecyclerView.Adapter<JumpAdapter.AdapterViewHolder> {
    Context context;
    List<DataItem> dataItems;
    MVPClass mvpClass;

    public JumpAdapter(Context context, List<DataItem> dataItems, MVPClass mvpClass) {
        this.context = context;
        this.dataItems = dataItems;
        this.mvpClass=mvpClass;
    }

    @NonNull
    @Override
    public JumpAdapter.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.jump_list,parent,false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JumpAdapter.AdapterViewHolder holder, int position) {
        holder.tv_number.setText(""+(position+1));
        if (dataItems.get(position).isAttempt()==false){
            holder.tv_number.setBackgroundResource(R.drawable.rectangle_stroke_bg);
            holder.tv_number.setTextColor(ContextCompat.getColor(context,R.color.text_color));
        }else {
            holder.tv_number.setBackgroundResource(R.drawable.bg_button);
            holder.tv_number.setTextColor(ContextCompat.getColor(context,R.color.white));
        }
        holder.tv_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuizQuestion.popupWindow.dismiss();
                mvpClass.getClassId(String.valueOf(position-1));

            }
        });

    }
    @Override
    public int getItemCount() {
        return dataItems.size();
    }
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tv_number;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_number=itemView.findViewById(R.id.tv_number);
        }
    }
}
