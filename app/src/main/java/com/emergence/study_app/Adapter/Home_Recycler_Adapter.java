package com.emergence.study_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Activity.View_Subject_Activity;
import com.emergence.study_app.newTech.retrofit.model.Subject.DataItem;
import com.emergence.study_app.newTech.retrofit.model.Subject.PackagesItem;
import com.emergence.study_app.newTech.retrofit.model.Subject.VideoItem;
import com.emergence.study_app.newTech.utils.Utils;
import com.example.study_app.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Home_Recycler_Adapter extends RecyclerView.Adapter<Home_Recycler_Adapter.Home_ViewHolder> {
    List<DataItem> dataItems;
    DataItem item;
    Context context;
    List<PackagesItem> list=new ArrayList<>();
    List<VideoItem> videolist=new ArrayList<>();

    public Home_Recycler_Adapter( List<DataItem> dataItems, Context context) {
        this.dataItems = dataItems;
        this.context = context;
    }

    @NonNull
    @Override
    public Home_ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.item_home_recycler,parent,false);

        return new Home_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Home_Recycler_Adapter.Home_ViewHolder holder,  int position) {
        holder.name.setText(dataItems.get(position).getName());

        for (int i=0;i<dataItems.size();i++){
            videolist=dataItems.get(position).getVideo();
            holder.recycler_video_pack.setAdapter(new VideoPackageAdapter(videolist,context));
        }


        for (int i=0;i<dataItems.get(position).getPackages().size();i++){

            if (dataItems.get(position).getPackages().get(i).getActiveStatus().equalsIgnoreCase("true")){
              if (dataItems.get(position).getPackages().get(i).getExpDate().equalsIgnoreCase("")){
                 list.add(dataItems.get(position).getPackages().get(i));
                  // dataItems.get(position).getPackages().add(dataItems.get(position).getPackages().get(i));
                  holder.inner_rec.setAdapter(new Inner_Home_Recycler_Adapter(dataItems.get(position).getPackages(),context));

              }else {
                  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                  Date strDate = null;
                  Date strDate1=null;
                  try {
                      strDate = sdf.parse(dataItems.get(position).getPackages().get(i).getExpDate());
                      strDate1 = sdf.parse(Utils.getDate());
                  } catch (ParseException e) {
                      e.printStackTrace();
                  }



                  if (!strDate1.after(strDate)) {
                      list.add(dataItems.get(position).getPackages().get(i));
//                      item=dataItems.get(position);
                      holder.inner_rec.setAdapter(new Inner_Home_Recycler_Adapter(dataItems.get(position).getPackages(),context));

                  }else {
                     // holder.inner_rec.setAdapter(new Inner_Home_Recycler_Adapter(dataItems.get(position).getPackages(),context));

                  }

              }

               // holder.inner_rec.setAdapter(new Inner_Home_Recycler_Adapter(dataItems.get(position).getPackages(),context));

            }
        }


        holder.btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View_Subject_Activity.data= dataItems;
                Intent intent=new Intent(context.getApplicationContext(), View_Subject_Activity.class);
                intent.putExtra("name",dataItems.get(position).getName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class Home_ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        RecyclerView inner_rec;
        Button btn_view;
        RecyclerView recycler_video_pack;
        public Home_ViewHolder(@NonNull  View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            recycler_video_pack=itemView.findViewById(R.id.recycler_video_pack);
            inner_rec=itemView.findViewById(R.id.inner_recycler_home);
            btn_view=itemView.findViewById(R.id.view_all_btn);
            recycler_video_pack.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            inner_rec.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        }
    }
}
