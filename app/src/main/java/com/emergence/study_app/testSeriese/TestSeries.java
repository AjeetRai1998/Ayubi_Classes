package com.emergence.study_app.testSeriese;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Adapter.TestSeriesAdapter;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.responseTestSeriese.ResponseTestSeriese;
import com.emergence.study_app.newTech.utils.Utils;
import com.example.study_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestSeries extends BaseActivity {
    ImageView iv_back;
    RecyclerView recycler_testSeriese;
    PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_series);
        preferencesManager=new PreferencesManager(TestSeries.this);
        iv_back=findViewById(R.id.iv_back);
        recycler_testSeriese=findViewById(R.id.recycler_testSeriese);
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
        Call<ResponseTestSeriese> call=apiServices.getTestSeries("TestSeries");
        call.enqueue(new Callback<ResponseTestSeriese>() {
            @Override
            public void onResponse(Call<ResponseTestSeriese> call, Response<ResponseTestSeriese> response) {
                hideLoading();
                Utils.CheckUserSession(TestSeries.this,preferencesManager.getUserId(),preferencesManager.getSession_id());
                if (response.body().getRes().equalsIgnoreCase("success")){
                    recycler_testSeriese.setHasFixedSize(true);
                    recycler_testSeriese.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recycler_testSeriese.setAdapter(new TestSeriesAdapter(getApplicationContext(),response.body().getData()));
                }else {
                }
            }

            @Override
            public void onFailure(Call<ResponseTestSeriese> call, Throwable t) {
                hideLoading();

            }
        });



    }

}