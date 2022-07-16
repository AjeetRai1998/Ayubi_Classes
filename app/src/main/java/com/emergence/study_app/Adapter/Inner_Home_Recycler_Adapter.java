package com.emergence.study_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emergence.study_app.Activity.Packages_Detail_Activity;
import com.emergence.study_app.Activity.Purchased_pkg_Activity;
import com.emergence.study_app.newTech.retrofit.Image_Path;
import com.emergence.study_app.newTech.retrofit.model.Subject.PackagesItem;
import com.emergence.study_app.newTech.retrofit.model.Subject.VideoItem;
import com.example.study_app.R;

import java.util.ArrayList;
import java.util.List;

public class Inner_Home_Recycler_Adapter extends RecyclerView.Adapter<Inner_Home_Recycler_Adapter.Inner_ViewHolder> {
    List<PackagesItem> modelList;
    Context context;
     List<VideoItem> videoItems;

    public Inner_Home_Recycler_Adapter(List<PackagesItem> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public Inner_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_inner_home_recycler,parent,false);
        return new Inner_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Inner_ViewHolder holder, int position) {
        holder.sub_name.setText(modelList.get(position).getName());
        holder.price.setText("\u20B9"+modelList.get(position).getPrice());
        holder.cut_price.setText("\u20B9"+modelList.get(position).getMrp());
        holder.cut_price.setPaintFlags(holder.cut_price.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        holder.desc.setText(modelList.get(position).getDescription());

        if (modelList.get(position).getExpDate().equalsIgnoreCase("")){
            holder.tv_expire_date.setVisibility(View.GONE);
        }else {
            holder.tv_expire_date.setText("Expiry date: "+modelList.get(position).getExpDate());
        }
        holder.tv_combo.setVisibility(View.GONE);

        Glide.with(holder.image)
                .load(Image_Path.Imagepath+"packages/"+modelList.get(position).getImage())
                .fitCenter()
                .into(holder.image);


        holder.layout_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (modelList.get(position).getIsPurchased().equalsIgnoreCase("yes")){
                  String am="";
                    if (modelList.get(position).getAmountOrder().equalsIgnoreCase("0")){
                        am=modelList.get(position).getPrice();
                    }else {
                        am=modelList.get(position).getAmountOrder();
                    }
                    String order_id=modelList.get(position).getOrderId();
                    Intent intent=new Intent(context, Purchased_pkg_Activity.class);
                    intent.putExtra("pack_id",modelList.get(position).getId());
                    intent.putExtra("name",modelList.get(position).getName());
                    intent.putExtra("order_id",order_id);
                    intent.putExtra("date",modelList.get(position).getOrderDate()+" "+modelList.get(position).getOrderTime());
                    intent.putExtra("method",modelList.get(position).getMethod());
                    intent.putExtra("price",am);
                    intent.putExtra("image",modelList.get(position).getImage());
                    intent.putExtra("combo",modelList.get(position).getCombo());
                    intent.putExtra("from","pack");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.getApplicationContext().startActivity(intent);



                }else {
                    Packages_Detail_Activity.data=modelList.get(position);
                    Intent intent=new Intent(context.getApplicationContext(), Packages_Detail_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("from","pack");
                    context.getApplicationContext().startActivity(intent);
                }
               /* Packages_Detail_Activity.data=modelList.get(position);
                Intent intent=new Intent(context.getApplicationContext(), Packages_Detail_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);*/


            }
        });
//        holder.recycler_video_pack.setAdapter(new VideoPackageAdapter(videoItems,context));




    }
//    private void removeItem(Inner_ViewHolder holder, int position) {
//        int actualPosition = holder.getAdapterPosition();
//        modelList.remove(actualPosition);
//        notifyItemRemoved(actualPosition);
//        notifyItemRangeChanged(actualPosition, modelList.size());
//    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class Inner_ViewHolder extends RecyclerView.ViewHolder {
        TextView sub_name,price,tv_combo,cut_price,desc,tv_expire_date;
        LinearLayout layout_subject;
        ImageView image;
        RecyclerView recycler_video_pack;
        public Inner_ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_combo=itemView.findViewById(R.id.tv_combo);
            tv_expire_date=itemView.findViewById(R.id.tv_expire_date);
            desc=itemView.findViewById(R.id.desc);
            sub_name=itemView.findViewById(R.id.sub_name);
            price=itemView.findViewById(R.id.price_rec);
            cut_price=itemView.findViewById(R.id.cut_price_rec);
            image=itemView.findViewById(R.id.img);
            layout_subject=itemView.findViewById(R.id.aub_class_layout);

        }
    }
}
