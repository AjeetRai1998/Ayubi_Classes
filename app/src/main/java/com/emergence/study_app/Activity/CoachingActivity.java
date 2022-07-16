package com.emergence.study_app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Adapter.ChangeCoachingAdapter;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.Coaching.ResponseCoaching;
import com.emergence.study_app.newTech.utils.Utils;
import com.example.study_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoachingActivity extends BaseActivity implements Coupon{
    ImageView iv_back;
    RecyclerView recycler_coaching;
    PreferencesManager preferencesManager;
    public static boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coaching);
        preferencesManager=new PreferencesManager(CoachingActivity.this);
        iv_back=findViewById(R.id.iv_back);
        recycler_coaching=findViewById(R.id.recycler_coaching);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getCoaching();
    }
    private void getCoaching() {
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseCoaching> coachingCall=apiServices.getCoaching("Coaching");
        coachingCall.enqueue(new Callback<ResponseCoaching>() {
            @Override
            public void onResponse(Call<ResponseCoaching> call, Response<ResponseCoaching> response) {
                hideLoading();
                Utils.CheckUserSession(CoachingActivity.this,preferencesManager.getUserId(),preferencesManager.getSession_id());
                if (response.body().getRes().equalsIgnoreCase("success")){
                    check=true;
                    recycler_coaching.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recycler_coaching.setHasFixedSize(true);
                    ChangeCoachingAdapter coaching_adapter=new ChangeCoachingAdapter(getApplicationContext(), response.body().getData(), CoachingActivity.this);
                    recycler_coaching.setAdapter(coaching_adapter);
                }else {

                }
            }

            @Override
            public void onFailure(Call<ResponseCoaching> call, Throwable t) {
                hideLoading();
            }
        });
    }

    @Override
    public void ApplyCoupon(String coupon_id, String coupon_code, String coupon_discount, String min_order_amount) {
        preferencesManager.setCoaching_id(coupon_id);
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();

    }


}