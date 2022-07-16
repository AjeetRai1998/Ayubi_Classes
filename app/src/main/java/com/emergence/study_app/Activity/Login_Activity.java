package com.emergence.study_app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.Number_Verify.ResponseMobile;
import com.emergence.study_app.newTech.utils.Utils;
import com.example.study_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends BaseActivity {
    Button btn_next;
    EditText et_mobile;

    public static String loginType="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        btn_next=findViewById(R.id.btn_next);
        et_mobile=findViewById(R.id.et_mobile);


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_mobile.getText().toString().equalsIgnoreCase("")) {
                    et_mobile.setError("Enter Mobile Number");
                    et_mobile.requestFocus();
                } else if (et_mobile.length()!=10){
                    et_mobile.setError("Enter full mobile number");
                    et_mobile.requestFocus();
        }else if (Utils.isNetworkConnectedMainThred(Login_Activity.this)){
                    getNumberVerified(et_mobile.getText().toString());
                }else {
                    showMessage("Not Connected to network");
                }
            }
        });
    }

    private void getNumberVerified(String mobile) {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseMobile> responseMobileCall=apiServices.getRegister(mobile,"Register");
        responseMobileCall.enqueue(new Callback<ResponseMobile>() {
            @Override
            public void onResponse(Call<ResponseMobile> call, Response<ResponseMobile> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    Intent intent=new Intent(new Intent(Login_Activity.this,OTP_Aunthatication_Activity.class));
                  showMessage(response.body().getMsg());
                  loginType=response.body().getLogintype();
                  intent.putExtra("mobile",mobile);
                    startActivity(intent);
                    finish();
                }else {
                    showMessage(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResponseMobile> call, Throwable t) {
                hideLoading();

            }
        });

    }
}