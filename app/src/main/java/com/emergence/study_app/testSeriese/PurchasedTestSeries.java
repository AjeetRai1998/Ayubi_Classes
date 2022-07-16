package com.emergence.study_app.testSeriese;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Adapter.PurchasedTestSeriesAdapter;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.responsePurchasedTestSeries.ResponsePurchasedTestSeries;
import com.example.study_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchasedTestSeries extends BaseActivity {
    ImageView iv_back;
    RecyclerView recycler_paid_series;
    PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchased_test_series);
        preferencesManager=new PreferencesManager(PurchasedTestSeries.this);
        iv_back=findViewById(R.id.iv_back);
        recycler_paid_series=findViewById(R.id.recycler_paid_series);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        AddExamRecycler();
    }
    private void AddExamRecycler() {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponsePurchasedTestSeries> call=apiServices.purchasedTestSeries("MyOrders",preferencesManager.getUserId(),"TestSeries");
        call.enqueue(new Callback<ResponsePurchasedTestSeries>() {
            @Override
            public void onResponse(Call<ResponsePurchasedTestSeries> call, Response<ResponsePurchasedTestSeries> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    recycler_paid_series.setHasFixedSize(true);
                    recycler_paid_series.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recycler_paid_series.setAdapter(new PurchasedTestSeriesAdapter(getApplicationContext(),response.body().getData()));
                }else {

                }
            }

            @Override
            public void onFailure(Call<ResponsePurchasedTestSeries> call, Throwable t) {
                hideLoading();

            }
        });



    }

}