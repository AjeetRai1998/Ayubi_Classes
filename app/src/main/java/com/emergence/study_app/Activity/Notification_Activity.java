package com.emergence.study_app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.emergence.study_app.Adapter.Notification_Adapter;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.Notification.ResponseNotificastion;
import com.example.study_app.R;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification_Activity extends BaseActivity {
    RecyclerView rec_notify;
    ImageView menu,back_btn,notification_icon;
    TextView title_home;
    CircleImageView usr_profile;
    SwipeRefreshLayout noti_act;
    LinearLayout l_nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        menu=findViewById(R.id.menu_icon);
        usr_profile=findViewById(R.id.usr_profile);
        l_nodata=findViewById(R.id.no_data);
        noti_act=findViewById(R.id.notification_at);
        back_btn=findViewById(R.id.back_btn);
        title_home=findViewById(R.id.main_titile);
        notification_icon=findViewById(R.id.img_notification);
        rec_notify=findViewById(R.id.recycler_notification);

        menu.setVisibility(View.GONE);
        usr_profile.setVisibility(View.GONE);
        notification_icon.setVisibility(View.GONE);
        back_btn.setVisibility(View.VISIBLE);
        title_home.setText("Notification");

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getNtificastion();

        noti_act.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNtificastion();
                noti_act.setRefreshing(false);
            }
        });


    }

    private void getNtificastion() {
        showLoading();
        ApiServices apiSer= API_Config.getApiClient_ByPost();
        Call<ResponseNotificastion> responseNotificastions=apiSer.getNotification("Notification");
        responseNotificastions.enqueue(new Callback<ResponseNotificastion>() {
            @Override
            public void onResponse(Call<ResponseNotificastion> call, Response<ResponseNotificastion> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    rec_notify.setLayoutManager(new LinearLayoutManager(Notification_Activity.this));
                    Notification_Adapter notification_adapter=new Notification_Adapter(response.body().getData(),getApplicationContext());
                    rec_notify.setAdapter(notification_adapter);

                }else {
                    rec_notify.setVisibility(View.GONE);
                    l_nodata.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onFailure(Call<ResponseNotificastion> call, Throwable t) {

            }
        });

    }
}