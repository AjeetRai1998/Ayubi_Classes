package com.emergence.study_app.Activity;

import android.os.Bundle;
import android.widget.TextView;

import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.Get_User.ResponseGetUser;
import com.example.study_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceActivity extends BaseActivity {
    PreferencesManager preferencesManager;
    TextView tv_name,tv_mobile,tv_email,tv_date,tv_order_Id,pack_name,tv_total,tv_total_pay,tv_pack_price,tv_method,tv_sub_totla,tv_cgst,tv_sgst,tv_int_handl;
    double amount=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        preferencesManager=new PreferencesManager(InvoiceActivity.this);
        tv_email=findViewById(R.id.tv_email);
        tv_mobile=findViewById(R.id.tv_mobile);
        tv_name=findViewById(R.id.tv_name);
        tv_date=findViewById(R.id.tv_date);
        tv_order_Id=findViewById(R.id.tv_order_Id);
        tv_method=findViewById(R.id.tv_method);
        tv_pack_price=findViewById(R.id.tv_pack_price);
        pack_name=findViewById(R.id.pack_name);
        tv_sub_totla=findViewById(R.id.tv_sub_totla);
        tv_total_pay=findViewById(R.id.tv_total_pay);
        tv_cgst=findViewById(R.id.tv_cgst);
        tv_sgst=findViewById(R.id.tv_sgst);
        tv_total=findViewById(R.id.tv_total);
        tv_int_handl=findViewById(R.id.tv_int_handl);
        pack_name.setText(getIntent().getStringExtra("name"));

        tv_method.setText(getIntent().getStringExtra("method"));
        tv_date.setText(getIntent().getStringExtra("order_date"));
        String orde=getIntent().getStringExtra("order_id");
        tv_order_Id.setText(orde);
        amount= Double.parseDouble(getIntent().getStringExtra("price"));
        double gst=amount*(18.0/100);
        double intrentcharge=amount*(2.6/100);
        double sb_total=amount-(gst+intrentcharge);
        double sgst=gst/2;
        double cgst=gst/2;

        tv_pack_price.setText("\u20B9 "+amount);
        tv_total.setText("\u20B9 "+amount);
        tv_total_pay.setText("\u20B9 "+amount);
        tv_sub_totla.setText("\u20B9 "+sb_total);
        tv_cgst.setText("\u20B9 "+gst);
        tv_sgst.setText("\u20B9 "+sgst);
        tv_int_handl.setText("\u20B9 "+intrentcharge);



        getUser();
    }
    private void getUser() {
        showLoading();
        ApiServices Api_ser= API_Config.getApiClient_ByPost();
        Call<ResponseGetUser> userCall=Api_ser.getGetUser(preferencesManager.getUserId(),"GetUser");
        userCall.enqueue(new Callback<ResponseGetUser>() {
            @Override
            public void onResponse(Call<ResponseGetUser> call, Response<ResponseGetUser> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    tv_mobile.setText(response.body().getData().getMobile());
                    tv_name.setText(response.body().getData().getName());
                    tv_email.setText(response.body().getData().getEmail());
                }else {
                }

            }

            @Override
            public void onFailure(Call<ResponseGetUser> call, Throwable t) {
                hideLoading();

            }
        });
    }

}