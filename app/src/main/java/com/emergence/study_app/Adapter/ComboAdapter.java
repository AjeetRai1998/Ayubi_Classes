package com.emergence.study_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emergence.study_app.Activity.ComboActivity;
import com.emergence.study_app.Activity.ComboDetailsActivity;
import com.emergence.study_app.newTech.retrofit.Image_Path;
import com.emergence.study_app.newTech.retrofit.model.responseComboOffer.DataItem;
import com.example.study_app.R;

import java.util.List;

public class ComboAdapter extends RecyclerView.Adapter<ComboAdapter.AdapterViewHolder> {
    Context context;
    View view;
    public ComboAdapter(Context context, List<DataItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }
    List<DataItem> dataItems;
    @NonNull
    @Override
    public ComboAdapter.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        if (ComboActivity.check==true){
            view =inflater.inflate(R.layout.full_view_combo,parent,false);
        }else {
            view =inflater.inflate(R.layout.combo_list,parent,false);
        }
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComboAdapter.AdapterViewHolder holder, int position) {
        Glide.with(context).load(Image_Path.Imagepath+"combo/"+dataItems.get(position).getImage()).into(holder.img);
        holder.sub_name.setText(dataItems.get(position).getName());
        holder.desc.setText(Html.fromHtml(dataItems.get(position).getDescription()));
        holder.price_rec.setText("\u20B9 "+dataItems.get(position).getPrice());
        holder.cut_price_rec.setText("\u20B9 "+dataItems.get(position).getTotalMrp());
        holder.cut_price_rec.setPaintFlags(holder.cut_price_rec.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        if (dataItems.get(position).getExpiry_date().equalsIgnoreCase("")){
            holder.tv_ex_date.setVisibility(View.GONE);
        }else {
            holder.tv_ex_date.setText("Expiry date: "+dataItems.get(position).getExpiry_date());
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComboDetailsActivity.pack_list=dataItems.get(position).getJsonMemberPackage();
                ComboDetailsActivity.test_list=dataItems.get(position).getTestseries();
                Intent intent=new Intent(context, ComboDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("name",dataItems.get(position).getName());
                intent.putExtra("price",dataItems.get(position).getPrice());
                intent.putExtra("desc",dataItems.get(position).getDescription());
                intent.putExtra("id",dataItems.get(position).getId());
                intent.putExtra("cut_price",dataItems.get(position).getTotalMrp());
                intent.putExtra("purchase",dataItems.get(position).getPurchase());
                intent.putExtra("image",Image_Path.Imagepath+"combo/"+dataItems.get(position).getImage());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        if (ComboActivity.check==false){
            if (dataItems.size()>5){
                return 5;
            }else {
                return dataItems.size();
            }
        }else {
            return dataItems.size();
        }

    }
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView sub_name,price_rec,cut_price_rec,desc,tv_ex_date;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ex_date=itemView.findViewById(R.id.tv_ex_date);
            img=itemView.findViewById(R.id.img);
            sub_name=itemView.findViewById(R.id.sub_name);
            price_rec=itemView.findViewById(R.id.price_rec);
            cut_price_rec=itemView.findViewById(R.id.cut_price_rec);
            desc=itemView.findViewById(R.id.desc);
        }
    }
}
