package com.emergence.study_app.Activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.Help_Support.ResponseHeloSupport;
import com.emergence.study_app.newTech.retrofit.model.TermsCondition.ResponseTerm;
import com.example.study_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Help_Support_Activity extends BaseActivity {
    ImageView menu_icon,back_icon,notify_icon,profile;
    TextView title_tool,tv_help;
    public static int statusTransit=0;
    String check="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_support);

        menu_icon=findViewById(R.id.menu_icon);
        back_icon=findViewById(R.id.back_btn);
        title_tool=findViewById(R.id.main_titile);
        profile=findViewById(R.id.usr_profile);
        notify_icon=findViewById(R.id.img_notification);
        tv_help=findViewById(R.id.tv_help);
        check=getIntent().getStringExtra("check");


        menu_icon.setVisibility(View.GONE);
        profile.setVisibility(View.GONE);
        notify_icon.setVisibility(View.GONE);
        back_icon.setVisibility(View.VISIBLE);
       if(check.equalsIgnoreCase("terms")){
           title_tool.setText("Terms and Condition");
           getTerms();
       }else{
           title_tool.setText("Help and Support");
           gethelpSupport();
       }
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getTerms() {
        showLoading();
        ApiServices apiServices1=API_Config.getApiClient_ByPost();
        Call<ResponseTerm> termCall=apiServices1.getTermsConditions("TermsConditions");
        termCall.enqueue(new Callback<ResponseTerm>() {
            @Override
            public void onResponse(Call<ResponseTerm> call, Response<ResponseTerm> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    tv_help.setText(Html.fromHtml(response.body().getData().get(0).getTerms()));
                }else {
                    showMessage(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResponseTerm> call, Throwable t) {
                hideLoading();

            }
        });
    }

    private void gethelpSupport() {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseHeloSupport> call=apiServices.getHelpSupport("HelpSupport");
        call.enqueue(new Callback<ResponseHeloSupport>() {
            @Override
            public void onResponse(Call<ResponseHeloSupport> call, Response<ResponseHeloSupport> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    tv_help.setText(Html.fromHtml(response.body().getData().get(0).getHelp()));

                }else{
                    showMessage(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResponseHeloSupport> call, Throwable t) {
                hideLoading();

            }
        });
    }
}