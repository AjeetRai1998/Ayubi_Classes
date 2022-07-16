package com.emergence.study_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Activity.Coupon;
import com.emergence.study_app.newTech.retrofit.model.responseCoupon.DataItem;
import com.emergence.study_app.newTech.utils.Utils;
import com.example.study_app.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.AdapterViewHolder> {
    Context context;
    List<DataItem> dataItems;
    Coupon coupon;


    public CouponAdapter(Context context, List<DataItem> dataItems,Coupon coupon) {
        this.context = context;
        this.dataItems = dataItems;
        this.coupon=coupon;
    }

    @NonNull
    @Override
    public CouponAdapter.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.coupon_list,parent,false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponAdapter.AdapterViewHolder holder, int position) {

        holder.tv_title.setText("Get \u20B9 "+dataItems.get(position).getDiscount()+" rupees discount on order above \u20B9 "+dataItems.get(position).getCriteria());
        holder.tv_code.setText(dataItems.get(position).getCode());
        holder.tv_expires.setText(dataItems.get(position).getValid());

        holder.tv_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date strDate = null;
                Date strDate1=null;
                try {
                    strDate = sdf.parse(dataItems.get(position).getValid());
                    strDate1 = sdf.parse(Utils.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (strDate1.after(strDate)) {
                    Toast.makeText(context, "This Coupon has expired ! \n Please select another Coupon", Toast.LENGTH_SHORT).show();
                }else {
                    coupon.ApplyCoupon(dataItems.get(position).getId(),dataItems.get(position).getCode(),dataItems.get(position).getDiscount(),dataItems.get(position).getCriteria());

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_code,tv_expires,tv_apply;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_apply=itemView.findViewById(R.id.tv_apply);
            tv_expires=itemView.findViewById(R.id.tv_expires);
            tv_code=itemView.findViewById(R.id.tv_code);
            tv_title=itemView.findViewById(R.id.tv_title);
        }
    }
}
