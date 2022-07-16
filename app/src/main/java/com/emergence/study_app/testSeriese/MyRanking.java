package com.emergence.study_app.testSeriese;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Adapter.RankAdapter;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.responseRank.ResponseRank;
import com.example.study_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRanking extends BaseActivity {
    ImageView iv_back;
    RecyclerView recycler_ranking;
    String id="";
    TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ranking);
        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        id=getIntent().getStringExtra("id");
        tv_title=findViewById(R.id.tv_title);
        tv_title.setText(getIntent().getStringExtra("name"));
        AddExamRecycler();
    }
    private void AddExamRecycler() {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseRank> call=apiServices.getRank(id);
        call.enqueue(new Callback<ResponseRank>() {
            @Override
            public void onResponse(Call<ResponseRank> call, Response<ResponseRank> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    recycler_ranking=findViewById(R.id.recycler_ranking);
                    recycler_ranking.setHasFixedSize(true);
                    recycler_ranking.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recycler_ranking.setAdapter(new RankAdapter(getApplicationContext(),response.body().getData()));
                }else {
                }
            }

            @Override
            public void onFailure(Call<ResponseRank> call, Throwable t) {
                hideLoading();

            }
        });



    }

}