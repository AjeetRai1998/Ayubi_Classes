package com.emergence.study_app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.responseWalletPoints.ResponseWalletPoints;
import com.example.study_app.BuildConfig;
import com.example.study_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferAndEarn extends BaseActivity {
    ImageView iv_back;
    Button invite;
    PreferencesManager preferencesManager;
    TextView ref_code,text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_and_earn);
        preferencesManager=new PreferencesManager(ReferAndEarn.this);
         iv_back=findViewById(R.id.iv_back);
         text=findViewById(R.id.text);
        ref_code=findViewById(R.id.ref_code);
        ref_code.setText(preferencesManager.getRef_Id());
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        invite=findViewById(R.id.invite);
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChooser();
            }
        });
        referPoints();
    }
    public void showChooser(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Use my Referral Code *"+preferencesManager.getRef_Id()+"* and get Real Coins in your wallet .Install the app Now : "+"https://play.google.com/store/apps/details?id="+ BuildConfig.APPLICATION_ID);
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
    private void referPoints(){
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseWalletPoints> call=apiServices.getreferPoints("refer-and-earn");
        call.enqueue(new Callback<ResponseWalletPoints>() {
            @Override
            public void onResponse(Call<ResponseWalletPoints> call, Response<ResponseWalletPoints> response) {
               hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    text.setText("Invite Your Friends by using your Referral Code and get extra discount on your purchasing and "+response.body().getData().get(0).getPoints()+" coins in your wallet.");

                }else {

                }
            }

            @Override
            public void onFailure(Call<ResponseWalletPoints> call, Throwable t) {
                hideLoading();

            }
        });
    }
}