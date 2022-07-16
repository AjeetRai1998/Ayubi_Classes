package com.emergence.study_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.emergence.study_app.Activity.AllPackageDetails;
import com.emergence.study_app.newTech.retrofit.Image_Path;
import com.example.study_app.R;
import com.emergence.study_app.newTech.retrofit.model.Banner.DataItem;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class Banner_Adapter extends SliderViewAdapter<Banner_Adapter.BannerView>{
  List<DataItem> slider_item;
    Context context;

    public Banner_Adapter(List<DataItem> slider_item, Context context) {
        this.slider_item = slider_item;
        this.context = context;
    }

    @Override
    public BannerView onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, null);
        return new BannerView(inflate);
    }

    @Override
    public void onBindViewHolder(BannerView viewHolder, int position) {
        Glide.with(viewHolder.imageView)
                .load(Image_Path.Imagepath+"banner/"+slider_item.get(position).getImage())
                .fitCenter()
                .into(viewHolder.imageView);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!slider_item.get(position).getPackageId().equalsIgnoreCase("")){
                    Intent intent=new Intent(context, AllPackageDetails.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id",slider_item.get(position).getPackageId());
                    intent.putExtra("from","slide");
                    context.startActivity(intent);
                }
            }
        });


    }

    @Override
    public int getCount() {
        return slider_item.size();
    }

    public class BannerView extends SliderViewAdapter.ViewHolder {
        ImageView imageView;
        public BannerView(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img_slider);
        }
    }
}
