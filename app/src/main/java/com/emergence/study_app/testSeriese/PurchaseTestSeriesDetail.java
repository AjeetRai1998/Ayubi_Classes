package com.emergence.study_app.testSeriese;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Adapter.PurchaseTestSeriesDetailAdapter;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.responseAttemptPaper.ResponseAttemptPaper;
import com.emergence.study_app.newTech.retrofit.model.responseTestSeriesDetails.ResponseTestSeriesDetails;
import com.example.study_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseTestSeriesDetail extends BaseActivity implements MVPClass{
    ImageView iv_back;
    RecyclerView recycler_paid_series;
    String testSeries_Id="";
    String price="";
    TextView tv_title,tv_payable_amount,tv_pay_now;
    PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_test_series_detail);
        preferencesManager=new PreferencesManager(PurchaseTestSeriesDetail.this);
        iv_back=findViewById(R.id.iv_back);
        recycler_paid_series=findViewById(R.id.recycler_paid_series);
        testSeries_Id=getIntent().getStringExtra("id");
        price=getIntent().getStringExtra("price");
        tv_title=findViewById(R.id.tv_title);
        tv_title.setText(getIntent().getStringExtra("name"));
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        purchasedTest();

    }
    private void purchasedTest() {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseTestSeriesDetails> couresesCall=apiServices.getTestSeriesDetails("Test",testSeries_Id,preferencesManager.getUserId());
        couresesCall.enqueue(new Callback<ResponseTestSeriesDetails>() {
            @Override
            public void onResponse(Call<ResponseTestSeriesDetails> call, Response<ResponseTestSeriesDetails> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    recycler_paid_series.setHasFixedSize(true);
                    recycler_paid_series.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    PurchaseTestSeriesDetailAdapter adapter=new PurchaseTestSeriesDetailAdapter(getApplicationContext(),response.body().getData(),PurchaseTestSeriesDetail.this);
                    recycler_paid_series.setAdapter(adapter);
                }else {
                }
            }

            @Override
            public void onFailure(Call<ResponseTestSeriesDetails> call, Throwable t) {

                hideLoading();
            }
        });

    }

    @Override
    public void getClassId(String id) {

    }

    @Override
    public void giveAnswer(String test_id, String title, String timing) {
        if (timing.equalsIgnoreCase("rank")){
            Intent intent=new Intent(context, MyRanking.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("id",test_id);
            intent.putExtra("name",title);
            startActivity(intent);
        }else if (timing.equalsIgnoreCase("result")){
            CompletedAnswer.titl=title;
            Intent intent=new Intent(context, CompletedAnswer.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("Sid",test_id);
            startActivity(intent);
        }else {
            StartTest(test_id,title,timing);
        }

    }
    private void StartTest(String test_id, String title, String timing){
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseAttemptPaper> call=apiServices.attemptPaper("Attempts",preferencesManager.getUserId(),test_id, "");
        call.enqueue(new Callback<ResponseAttemptPaper>() {
            @Override
            public void onResponse(Call<ResponseAttemptPaper> call, Response<ResponseAttemptPaper> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    Intent intent=new Intent(getApplicationContext(),QuestionActivity.class);
                    intent.putExtra("series_Id",test_id);
                    intent.putExtra("title",title);
                    intent.putExtra("timing",timing);
                    startActivity(intent);
                    finish();
                    // goToActivityWithFinish(ExamRules.this,OnlineExam.class,null);
                }else {
                }
            }

            @Override
            public void onFailure(Call<ResponseAttemptPaper> call, Throwable t) {
                hideLoading();

            }
        });
    }

}