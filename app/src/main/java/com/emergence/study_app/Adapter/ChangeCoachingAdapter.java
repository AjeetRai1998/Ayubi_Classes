package com.emergence.study_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emergence.study_app.Activity.CoachingActivity;
import com.emergence.study_app.Activity.Coupon;
import com.emergence.study_app.newTech.retrofit.Image_Path;
import com.emergence.study_app.newTech.retrofit.model.Coaching.DataItem;
import com.example.study_app.R;

import java.util.List;

public class ChangeCoachingAdapter extends RecyclerView.Adapter<ChangeCoachingAdapter.AdapterViewHolder> {
    Context context;
    List<DataItem> dataItems;
    Coupon coupon;
    View view;

    public ChangeCoachingAdapter(Context context, List<DataItem> dataItems,Coupon coupon) {
        this.context = context;
        this.dataItems = dataItems;
        this.coupon=coupon;
    }

    @NonNull
    @Override
    public ChangeCoachingAdapter.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        if (CoachingActivity.check==true){
            view=inflater.inflate(R.layout.all_coaching,parent,false);
        }else {
            view=inflater.inflate(R.layout.coaching_list,parent,false);
        }
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChangeCoachingAdapter.AdapterViewHolder holder, int position) {
        holder.tv_title.setText(dataItems.get(position).getName());
        Glide.with(context).load(Image_Path.Imagepath+"admins/" +dataItems.get(position).getImage()).into(holder.text);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coupon.ApplyCoupon(dataItems.get(position).getId(),"","","");
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        ImageView text;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            text=itemView.findViewById(R.id.text);
        }
    }
}
