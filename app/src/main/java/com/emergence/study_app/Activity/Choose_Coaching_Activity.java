package com.emergence.study_app.Activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Adapter.Choose_Coaching_Adapter;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.Coaching.ResponseCoaching;
import com.example.study_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Choose_Coaching_Activity extends BaseActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_coaching);
        recyclerView=findViewById(R.id.choose_coaching);

        getCoaching();

    }

    private void getCoaching() {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseCoaching> coachingCall=apiServices.getCoaching("Coaching");
        coachingCall.enqueue(new Callback<ResponseCoaching>() {
            @Override
            public void onResponse(Call<ResponseCoaching> call, Response<ResponseCoaching> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    recyclerView.setLayoutManager(new LinearLayoutManager(Choose_Coaching_Activity.this));
                    recyclerView.setHasFixedSize(true);
                    Choose_Coaching_Adapter coaching_adapter=new Choose_Coaching_Adapter(response.body().getData(),getApplicationContext());
                    recyclerView.setAdapter(coaching_adapter);
                }else {
                }
            }

            @Override
            public void onFailure(Call<ResponseCoaching> call, Throwable t) {
                hideLoading();

            }
        });
    }
}