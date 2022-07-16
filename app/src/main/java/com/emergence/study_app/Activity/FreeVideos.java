package com.emergence.study_app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Adapter.FreeVideoAdapter;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.responseFreeVideos.ResponseFreeVideo;
import com.example.study_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FreeVideos extends BaseActivity {
    ImageView iv_back;
    RecyclerView recycler_free_videos;
    PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_videos);
        preferencesManager=new PreferencesManager(FreeVideos.this);
         iv_back=findViewById(R.id.iv_back);
        recycler_free_videos=findViewById(R.id.recycler_free_videos);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getFreeVideos();
    }
    private void getFreeVideos() {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseFreeVideo> call=apiServices.getFreeVideo("FreeVideo","1");
        call.enqueue(new Callback<ResponseFreeVideo>() {
            @Override
            public void onResponse(Call<ResponseFreeVideo> call, Response<ResponseFreeVideo> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    recycler_free_videos.setHasFixedSize(true);
                    recycler_free_videos.setNestedScrollingEnabled(false);
                    recycler_free_videos.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                    recycler_free_videos.setAdapter(new FreeVideoAdapter(response.body().getData(), getApplicationContext()));
                }else {

                }
            }

            @Override
            public void onFailure(Call<ResponseFreeVideo> call, Throwable t) {
                hideLoading();
            }
        });
    }

}