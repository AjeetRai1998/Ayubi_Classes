package com.emergence.study_app.live;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emergence.study_app.Activity.Purchased_pkg_Activity;
import com.emergence.study_app.liveVideo.Videos;
import com.emergence.study_app.newTech.retrofit.Image_Path;
import com.emergence.study_app.newTech.retrofit.model.My_Order.DataItem;
import com.example.study_app.R;

import java.util.List;

public class LivePackagesAdapter extends RecyclerView.Adapter<LivePackagesAdapter.AdapterViewHolder> {
    List<DataItem> dataItems;
    Context context;
    String am="";

    public LivePackagesAdapter(List<DataItem> dataItems, Context context) {
        this.dataItems = dataItems;
        this.context = context;
    }

    @NonNull
    @Override
    public LivePackagesAdapter.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_inner_home_recycler,parent,false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LivePackagesAdapter.AdapterViewHolder holder, int position) {
        holder.name.setText(dataItems.get(position).getName());
        holder.cut_price_rec.setVisibility(View.GONE);
        holder.price_rec.setVisibility(View.GONE);
        holder.description.setVisibility(View.GONE);
        if (dataItems.get(position).getExpiry_date().equalsIgnoreCase("")){
            holder.tv_expire_date.setVisibility(View.GONE);
        }else {
            holder.tv_expire_date.setText("Expiry date: "+dataItems.get(position).getExpiry_date());
        }

        if (dataItems.get(position).getCombo().equalsIgnoreCase("")){
            holder.tv_combo.setVisibility(View.GONE);
        }else {
//            comb0
            holder.tv_combo.setVisibility(View.GONE);
        }


        Glide.with(holder.img)
                .load(Image_Path.Imagepath+"packages/"+dataItems.get(position).getImage())
                .fitCenter()
                .into(holder.img);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Videos.class);
                intent.putExtra("id",dataItems.get(position).getPackageId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name,description,tv_combo,price_rec,cut_price_rec,tv_expire_date;
        LinearLayout layout;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_combo=itemView.findViewById(R.id.tv_combo);
            tv_expire_date=itemView.findViewById(R.id.tv_expire_date);
            img=itemView.findViewById(R.id.img);
            name=itemView.findViewById(R.id.sub_name);
            price_rec=itemView.findViewById(R.id.price_rec);
            cut_price_rec=itemView.findViewById(R.id.cut_price_rec);
            description=itemView.findViewById(R.id.desc);
            layout=itemView.findViewById(R.id.aub_class_layout);
        }
    }
}
