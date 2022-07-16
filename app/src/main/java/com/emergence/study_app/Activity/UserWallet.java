package com.emergence.study_app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.Get_User.ResponseGetUser;
import com.emergence.study_app.newTech.retrofit.model.responsePoints.ResponsePoints;
import com.example.study_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserWallet extends BaseActivity {
    ImageView iv_back;
    PreferencesManager preferencesManager;
    TextView tv_mony,tv_points;
    RecyclerView recycler_wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wallet);
        preferencesManager=new PreferencesManager(UserWallet.this);
        iv_back=findViewById(R.id.iv_back);
        tv_points=findViewById(R.id.tv_points);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_mony=findViewById(R.id.tv_mony);
        getUser();
    }
    private void getUser() {
        showLoading();
        ApiServices user_api= API_Config.getApiClient_ByPost();
        Call<ResponseGetUser> userCall=user_api.getGetUser(preferencesManager.getUserId(),"GetUser");
        userCall.enqueue(new Callback<ResponseGetUser>() {
            @Override
            public void onResponse(Call<ResponseGetUser> call, Response<ResponseGetUser> response) {
                getPonts();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    if (response.body().getData().getWallet().equalsIgnoreCase("0")){
                        tv_mony.setText("00");
                    }else {
                        tv_mony.setText(response.body().getData().getWallet());

                    }

                }else {
                }
            }
            @Override
            public void onFailure(Call<ResponseGetUser> call, Throwable t) {
                hideLoading();
            }
        });
    }
    private void getPonts() {

        ApiServices user_api= API_Config.getApiClient_ByPost();
        Call<ResponsePoints> userCall=user_api.getPoints("Convert");
        userCall.enqueue(new Callback<ResponsePoints>() {
            @Override
            public void onResponse(Call<ResponsePoints> call, Response<ResponsePoints> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                  tv_points.setText(response.body().getData().getPoints()+" Points = \u20B9 "+response.body().getData().getAmount());

                }else {
                }
            }
            @Override
            public void onFailure(Call<ResponsePoints> call, Throwable t) {
                hideLoading();
            }
        });
    }


}