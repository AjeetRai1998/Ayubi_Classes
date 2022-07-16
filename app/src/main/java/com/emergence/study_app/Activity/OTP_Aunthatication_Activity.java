package com.emergence.study_app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.VerifyOtp.ResponseOTP;
import com.example.study_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTP_Aunthatication_Activity extends BaseActivity implements TextWatcher {
    EditText box1,box2,box3,box4,box5,box6;
    Button btn_next;
    TextView t_mobile;
    PreferencesManager preferencesManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_aunthatication);
        preferencesManager=new PreferencesManager(OTP_Aunthatication_Activity.this);
        btn_next=findViewById(R.id.btn_next);
        t_mobile=findViewById(R.id.mobile_t);
        box1=findViewById(R.id.box1);
        box2=findViewById(R.id.box2);
        box3=findViewById(R.id.box3);
        box4=findViewById(R.id.box4);
        box5=findViewById(R.id.box5);
        box6=findViewById(R.id.box6);

        box1.addTextChangedListener(this);
        box2.addTextChangedListener(this);
        box3.addTextChangedListener(this);
        box4.addTextChangedListener(this);
        box5.addTextChangedListener(this);
        box6.addTextChangedListener(this);
        String mobile=getIntent().getStringExtra("mobile");
        t_mobile.setText("mobile no. +91-"+mobile);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String et_otp=box1.getText().toString()+box2.getText().toString()+box3.getText().toString()+box4.getText().toString()+box5.getText().toString()+box6.getText().toString();

                getOTPVerify(et_otp,mobile);
            }
        });
    }

    private void getOTPVerify(String et_otp,String mobile) {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseOTP> otpCall=apiServices.getVerifyOTP(mobile,et_otp,"VerifyOTP");
        otpCall.enqueue(new Callback<ResponseOTP>() {
            @Override
            public void onResponse(Call<ResponseOTP> call, Response<ResponseOTP> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    preferencesManager.setSession_id(response.body().getData().getSession_id());
                    if(Login_Activity.loginType.equalsIgnoreCase("new")) {
//                        Intent intent = new Intent(OTP_Aunthatication_Activity.this, Choose_Coaching_Activity.class);
////                        Intent intent = new Intent(OTP_Aunthatication_Activity.this, MainActivity.class);
//                        preferencesManager.setUserId(response.body().getData().getId());
//                        startActivity(intent);
//                        finish();
                        Intent intent=new Intent(context.getApplicationContext(), User_Detail_Activity.class);
                        intent.putExtra("coaching_id","");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        preferencesManager.setUserId(response.body().getData().getId());
                        context.getApplicationContext().startActivity(intent);
                    }else{
                        Intent intent = new Intent(OTP_Aunthatication_Activity.this, MainActivity.class);
                        preferencesManager.setUserId(response.body().getData().getId());
                     //   preferencesManager.setCoaching_id(response.body().getData().getCoaching());
                        preferencesManager.setClass_id(response.body().getData().getJsonMemberClass());
                        startActivity(intent);
                        finish();
                    }

                }else {
                }

            }

            @Override
            public void onFailure(Call<ResponseOTP> call, Throwable t) {
                hideLoading();

            }
        });

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length()==1){

            if (box1.length()==1){
                box2.requestFocus();

            }if (box2.length()==1){
                box3.requestFocus();
            }if (box3.length()==1){
                box4.requestFocus();
            }if (box4.length()==1){
                box5.requestFocus();
            }if (box5.length()==1){
                box6.requestFocus();
            }
        }else if (s.length()==0){
            if (box6.length()==0){
                box5.requestFocus();
            }
            if (box5.length()==0){
                box4.requestFocus();
            }
            if (box4.length()==0){
                box3.requestFocus();
            } if(box3.length()==0){
                box2.requestFocus();
            } if (box2.length()==0){
                box1.requestFocus();
            }
        }

    }
}