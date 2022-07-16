package com.emergence.study_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emergence.study_app.Activity.AllPackageDetails;
import com.emergence.study_app.newTech.retrofit.Image_Path;
import com.emergence.study_app.newTech.retrofit.model.responseComboOffer.JsonMemberPackageItem;
import com.example.study_app.R;

import java.util.List;

public class ComboPackageAdapter extends RecyclerView.Adapter<ComboPackageAdapter.AdapterViewHolder> {
    Context context;
    List<JsonMemberPackageItem> items;

    public ComboPackageAdapter(Context context, List<JsonMemberPackageItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ComboPackageAdapter.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.combo_package_list,parent,false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComboPackageAdapter.AdapterViewHolder holder, int position) {

        holder.sub_name.setText(items.get(position).getName());
        Glide.with(context).load(Image_Path.Imagepath+"packages/"+items.get(position).getImage()).into(holder.img);
        holder.view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, AllPackageDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("id",items.get(position).getId());
                intent.putExtra("from","combo");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView sub_name,view_details;
        ImageView img;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            sub_name=itemView.findViewById(R.id.sub_name);
            view_details=itemView.findViewById(R.id.view_details);
        }
    }
}
