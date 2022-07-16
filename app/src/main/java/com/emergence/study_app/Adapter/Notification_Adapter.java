package com.emergence.study_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emergence.study_app.newTech.retrofit.Image_Path;
import com.example.study_app.R;
import com.emergence.study_app.newTech.retrofit.model.Notification.DataItem;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Notification_Adapter extends RecyclerView.Adapter<Notification_Adapter.Notify_ViewHolder> {
    List<DataItem> n_data;
    Context context;

    public Notification_Adapter(List<DataItem> n_data, Context context) {
        this.n_data = n_data;
        this.context = context;
    }

    @NonNull
    @Override
    public Notify_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_notification,parent,false);
        return new Notify_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Notify_ViewHolder holder, int position) {
       holder.title_notify.setText(n_data.get(position).getTitle());
       holder.body_notify.setText(n_data.get(position).getDescription());
       holder.date.setText(n_data.get(position).getDate());
        Glide.with(holder.img)
                .load(Image_Path.Imagepath+"notification/"+n_data.get(position).getImage())
                .fitCenter()
                .into(holder.img);


    }

    @Override
    public int getItemCount() {
        return n_data.size();
    }

    public class Notify_ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout_notify;
        TextView title_notify,body_notify,date;
        CircleImageView img;
        public Notify_ViewHolder(@NonNull View itemView) {
            super(itemView);
            title_notify=itemView.findViewById(R.id.title_notification);
            body_notify=itemView.findViewById(R.id.body_notification);
            layout_notify=itemView.findViewById(R.id.layout_notify);
            date=itemView.findViewById(R.id.n_date);
            img=itemView.findViewById(R.id.nt_img);
        }
    }
}
