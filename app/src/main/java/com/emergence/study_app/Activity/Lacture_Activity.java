package com.emergence.study_app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Adapter.Lacture_Adapter;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.Chapter.DataItem;
import com.emergence.study_app.newTech.retrofit.model.Lacture.ResponseLacture;
import com.example.study_app.R;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Lacture_Activity extends BaseActivity {
    public static DataItem chapterdata;
    int pressedButtonNumber;

    ImageView menu_icon,back_icon,notify_icon;
    TextView title_text;
    RecyclerView lacture_recycler;
    CircleImageView usr_profile;
    LinearLayout l_nodata;
    String id="";
    String name="";
    String all_lact="";
    String from="";

    String pkg_id="",str_chptr_id="";
    public static int statusTransit=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lactuure);
        from=getIntent().getStringExtra("chapter");

        id=getIntent().getStringExtra("id");
        name=getIntent().getStringExtra("name");
        all_lact=getIntent().getStringExtra("all_lacture");





        title_text=findViewById(R.id.main_titile);
        l_nodata=findViewById(R.id.no_data);
        menu_icon=findViewById(R.id.menu_icon);
        lacture_recycler=findViewById(R.id.lacture_rec);
        back_icon=findViewById(R.id.back_btn);
        usr_profile=findViewById(R.id.usr_profile);
        notify_icon=findViewById(R.id.img_notification);
         str_chptr_id=getIntent().getStringExtra("chapt_id");
         pkg_id=getIntent().getStringExtra("id");
        title_text.setText(name);



//        if (from.equalsIgnoreCase("details")){
//            getLacture(str_chptr_id,pkg_id);
//
//        }else {
//            String str_idl=getIntent().getStringExtra("id");
//            getAll_lact(str_idl);
//            showMessage("isufhidf jch iudh");
//
//        }

        menu_icon.setVisibility(View.GONE);
        usr_profile.setVisibility(View.GONE);
        back_icon.setVisibility(View.VISIBLE);
        notify_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Notification_Activity.class));
            }
        });
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        getAll_lact();
    }

    private void getAll_lact() {
        showLoading();
        ApiServices apiAlllact=API_Config.getApiClient_ByPost();
        Call<ResponseLacture> allLactureCall;
        if(statusTransit==1) {
            allLactureCall = apiAlllact.getAllLecture("AllLecture", pkg_id);
        }else{
            allLactureCall=apiServices.getLecture("Lecture",pkg_id,str_chptr_id);
        }
        allLactureCall.enqueue(new Callback<ResponseLacture>() {
            @Override
            public void onResponse(Call<ResponseLacture> call, Response<ResponseLacture> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    lacture_recycler.setHasFixedSize(true);
                    lacture_recycler.setLayoutManager(new LinearLayoutManager(Lacture_Activity.this));
                    Lacture_Adapter adapter=new Lacture_Adapter(response.body().getData(),getApplicationContext());
                    lacture_recycler.setAdapter(adapter);
                    lacture_recycler.setVisibility(View.VISIBLE);
                    l_nodata.setVisibility(View.GONE);
                }else {
                    lacture_recycler.setVisibility(View.GONE);
                    l_nodata.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onFailure(Call<ResponseLacture> call, Throwable t) {
                hideLoading();
                Log.e("Lectures",t.getMessage().toString());
            }
        });
    }

}