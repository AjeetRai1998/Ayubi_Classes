package com.emergence.study_app.Activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.Privacy_Policy.ResponsePrivacyPolicy;
import com.example.study_app.R;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Privacy_Policy_Activity extends BaseActivity {
    TextView tv_privacy,title_txt;
    ImageView menu_icon,back_icon,notify_icon;
    CircleImageView profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
         tv_privacy=findViewById(R.id.tv_privcy);
        title_txt=findViewById(R.id.main_titile);
        menu_icon=findViewById(R.id.menu_icon);
        back_icon=findViewById(R.id.back_btn);
        notify_icon=findViewById(R.id.img_notification);
        profile=findViewById(R.id.usr_profile);
        title_txt.setText("Privacy and Policy");
        menu_icon.setVisibility(View.GONE);
        notify_icon.setVisibility(View.GONE);
        profile.setVisibility(View.GONE);
        back_icon.setVisibility(View.VISIBLE);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getprivacyPolicy();
    }
    private void getprivacyPolicy() {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponsePrivacyPolicy> call=apiServices.getPrivacyPolicy("PrivacyPolicy");
        call.enqueue(new Callback<ResponsePrivacyPolicy>() {
            @Override
            public void onResponse(Call<ResponsePrivacyPolicy> call, Response<ResponsePrivacyPolicy> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    tv_privacy.setText(Html.fromHtml(response.body().getData().get(0).getPrivacy()));
                }else{
                    showMessage(response.body().getMsg());
                }
            }
            @Override
            public void onFailure(Call<ResponsePrivacyPolicy> call, Throwable t) {
                hideLoading();
            }
        });
    }
}