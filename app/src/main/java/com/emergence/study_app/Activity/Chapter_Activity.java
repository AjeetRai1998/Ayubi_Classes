package com.emergence.study_app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.emergence.study_app.Adapter.Chapter_Adapter;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.Chapter.ResponseGetChapter;
import com.example.study_app.R;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chapter_Activity extends BaseActivity {
    ImageView menu_icon,back_icon,notify_icon;
    TextView title_txt;
    CircleImageView profile_img;
    SwipeRefreshLayout chapter_act;
    RecyclerView chapter_rec;
    LinearLayout l_nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        menu_icon=findViewById(R.id.menu_icon);
        back_icon=findViewById(R.id.back_btn);
        profile_img=findViewById(R.id.usr_profile);
        notify_icon=findViewById(R.id.img_notification);
        title_txt=findViewById(R.id.main_titile);
        chapter_rec=findViewById(R.id.rec_chapter);
        chapter_act=findViewById(R.id.chapter_at);
        l_nodata=findViewById(R.id.no_data);

      String pack_id=getIntent().getStringExtra("id");
        title_txt.setText(getIntent().getStringExtra("name"));
        getChapter(pack_id);

        menu_icon.setVisibility(View.GONE);
        profile_img.setVisibility(View.GONE);
        back_icon.setVisibility(View.VISIBLE);

        chapter_act.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getChapter(pack_id);
                chapter_act.setRefreshing(false);
            }
        });

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        notify_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Notification_Activity.class));
            }
        });
    }

    private void getChapter(String pack_id) {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseGetChapter> chapterCall=apiServices.getChapter("Chapter",pack_id);
        chapterCall.enqueue(new Callback<ResponseGetChapter>() {
            @Override
            public void onResponse(Call<ResponseGetChapter> call, Response<ResponseGetChapter> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    Chapter_Adapter adapter=new Chapter_Adapter(response.body().getData(),getApplicationContext());
                    chapter_rec.setAdapter(adapter);
                    chapter_rec.setHasFixedSize(true);
                    chapter_rec.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }else {
                    chapter_rec.setVisibility(View.GONE);
                    l_nodata.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<ResponseGetChapter> call, Throwable t) {
                hideLoading();

            }
        });
    }
}