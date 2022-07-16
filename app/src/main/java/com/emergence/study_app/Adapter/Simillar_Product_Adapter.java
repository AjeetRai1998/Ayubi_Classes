package com.emergence.study_app.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emergence.study_app.newTech.retrofit.Image_Path;
import com.emergence.study_app.newTech.retrofit.model.SimillarProduct.DataItem;
import com.example.study_app.R;

import java.util.List;

public class Simillar_Product_Adapter extends RecyclerView.Adapter<Simillar_Product_Adapter.Simillar_ViewHolder> {
  
    List<DataItem> dataItems;
    Context context;

    public Simillar_Product_Adapter(List<DataItem> dataItems, Context context) {
        this.dataItems = dataItems;
        this.context = context;
    }

    @NonNull
    @Override
    public Simillar_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.item_inner_home_recycler,parent,false);
        return new Simillar_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Simillar_ViewHolder holder, int position) {
        holder.sub_name.setText(dataItems.get(position).getName());
        holder.price_rec.setText("\u20B9 "+dataItems.get(position).getPrice());
        holder.cut_price_rec.setText("\u20B9 "+dataItems.get(position).getMrp());
        holder.cut_price_rec.setPaintFlags(holder.cut_price_rec.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        holder.desc.setText(Html.fromHtml(dataItems.get(position).getDescription()));
        if (dataItems.get(position).getExp_date().equalsIgnoreCase("")){
            holder.tv_expire_date.setVisibility(View.GONE);
        }else {
            holder.tv_expire_date.setText("Expiry date: "+dataItems.get(position).getExp_date());
        }
        holder.tv_combo.setVisibility(View.GONE);

        Glide.with(holder.img)
                .load(Image_Path.Imagepath+"packages/"+dataItems.get(position).getImage())
                .fitCenter()
                .into(holder.img);



//        holder.aub_class_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(context.getApplicationContext(), PackagesItem.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.getApplicationContext().startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class Simillar_ViewHolder extends RecyclerView.ViewHolder {
        TextView sub_name,price_rec,tv_combo,cut_price_rec,desc,tv_expire_date;
        ImageView img;
        LinearLayout aub_class_layout;
        public Simillar_ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_expire_date=itemView.findViewById(R.id.tv_expire_date);
            sub_name=itemView.findViewById(R.id.sub_name);
            price_rec=itemView.findViewById(R.id.price_rec);
            cut_price_rec=itemView.findViewById(R.id.cut_price_rec);
            desc=itemView.findViewById(R.id.desc);
            img=itemView.findViewById(R.id.img);
            aub_class_layout=itemView.findViewById(R.id.aub_class_layout);
            tv_combo=itemView.findViewById(R.id.tv_combo);
        }
    }
}
