package com.emergence.study_app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.emergence.study_app.liveVideo.Videos;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.Image_Path;
import com.emergence.study_app.newTech.retrofit.model.Live_Session.ResponseLiveSession;
import com.emergence.study_app.newTech.retrofit.model.PkgDetail.ResponsePkgDetail;
import com.emergence.study_app.newTech.utils.Utils;
import com.example.study_app.R;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Purchased_pkg_Activity extends BaseActivity {
    ImageView pkg_img,menu_icon,back_icon,notify_icon,img_sub;
    TextView title_main,ct_chapt,ct_lacture,ct_notes,ct_video,sub_name,wwlearn,description;
    CircleImageView profile;
    PreferencesManager preferencesManager;
    LinearLayout lt_chapt,lt_lact,lt_notes,lt_videos,layout_gif;
    Button btn_invoice;
    String order_id="",order_date="",method="",pack_name="",price="";
    CardView card_live;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchased_pkg);
        preferencesManager=new PreferencesManager(Purchased_pkg_Activity.this);
        Utils.CheckUserSession(Purchased_pkg_Activity.this,preferencesManager.getUserId(),preferencesManager.getSession_id());

        order_id=getIntent().getStringExtra("order_id");
        order_date=getIntent().getStringExtra("date");
//        showMessage(order_date);
        method=getIntent().getStringExtra("method");
        pack_name=getIntent().getStringExtra("name");
        price=getIntent().getStringExtra("price");
        btn_invoice=findViewById(R.id.btn_invoice);
        btn_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),InvoiceActivity.class);
                intent.putExtra("order_id",order_id);
                intent.putExtra("order_date",order_date);
                intent.putExtra("method",method);
                intent.putExtra("name",pack_name);
                intent.putExtra("price",price);
                startActivity(intent);
            }
        });

//        if (getIntent().getStringExtra("combo").equalsIgnoreCase("")){
//            btn_invoice.setVisibility(View.VISIBLE);
//        }else {
//            btn_invoice.setVisibility(View.GONE);
//        }



        card_live=findViewById(R.id.card_live);
        layout_gif=findViewById(R.id.layout_gif);
        img_sub=findViewById(R.id.img_sub);
        sub_name=findViewById(R.id.sub_name);

        wwlearn=findViewById(R.id.wwlearn);
        description=findViewById(R.id.description);
        pkg_img=findViewById(R.id.img_sub);
        lt_chapt=findViewById(R.id.lyt_chapt);
        lt_lact=findViewById(R.id.lyt_lact);
        lt_notes=findViewById(R.id.lyt_notes);
        lt_videos=findViewById(R.id.lyt_video);
        menu_icon=findViewById(R.id.menu_icon);
        back_icon=findViewById(R.id.back_btn);
        notify_icon=findViewById(R.id.img_notification);
        title_main=findViewById(R.id.main_titile);
        profile=findViewById(R.id.usr_profile);
        ct_chapt=findViewById(R.id.ct_chapt);
        ct_lacture=findViewById(R.id.ct_lacture);
        ct_notes=findViewById(R.id.ct_notes);
        ct_video=findViewById(R.id.ct_video);

        menu_icon.setVisibility(View.GONE);
        back_icon.setVisibility(View.VISIBLE);
        profile.setVisibility(View.GONE);
        String str_pkgid=getIntent().getStringExtra("pack_id");
        String str_nam=getIntent().getStringExtra("name");
        title_main.setText(str_nam);
        getlivesessionreport(str_pkgid);

        getPkgDetail(str_pkgid);
        notify_icon.setVisibility(View.GONE);
        layout_gif.setVisibility(View.VISIBLE);
        layout_gif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Videos.class);
                intent.putExtra("id",str_pkgid);
                startActivity(intent);
            }
        });

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lt_chapt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Chapter_Activity.class);
                    intent.putExtra("id", str_pkgid);
                    intent.putExtra("name",str_nam);
                    startActivity(intent);
            }
        });
        lt_lact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Lacture_Activity.statusTransit=1;
//                Intent intent = new Intent(getApplicationContext(), Lacture_Activity.class);
//                intent.putExtra("id",str_pkgid);
//                intent.putExtra("name",str_nam);
//                intent.putExtra("all_lacture", "1");
//                startActivity(intent);

            }
        });
        lt_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Notes_Fragment.notestransition=1;
//                Notes_Video_Activity.statusTrans=0;
//                Notes_Video_Activity.package_id=str_pkgid;
//                Notes_Video_Activity.lacture_name=str_nam;
//                Intent intent = new Intent(Purchased_pkg_Activity.this, Notes_Video_Activity.class);
//                startActivity(intent);
            }
        });
        lt_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Notes_Video_Activity.statusTrans=1;
//                Video_Fragment.videoransition=1;
//                Notes_Video_Activity.package_id=str_pkgid;
//                Notes_Video_Activity.lacture_name=str_nam;
//                Intent intent = new Intent(Purchased_pkg_Activity.this, Notes_Video_Activity.class);
//                startActivity(intent);
            }
        });

        if (getIntent().getStringExtra("from").equalsIgnoreCase("video")){
            card_live.setVisibility(View.GONE);
        }else {
            card_live.setVisibility(View.VISIBLE);
        }




    }

    private void getPkgDetail(String str_pkgid) {
        showLoading();
        ApiServices api=API_Config.getApiClient_ByPost();
        Call<ResponsePkgDetail> pkgDetailCall=api.getSingleProduct("SingleProduct",str_pkgid);
        pkgDetailCall.enqueue(new Callback<ResponsePkgDetail>() {
            @Override
            public void onResponse(Call<ResponsePkgDetail> call, Response<ResponsePkgDetail> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    sub_name.setText(response.body().getData().get(0).getName());
                    wwlearn.setText(Html.fromHtml(response.body().getData().get(0).getWwlearn()));
                    description.setText(Html.fromHtml(response.body().getData().get(0).getDescription()));
                    ct_chapt.setText(response.body().getData().get(0).getTotalChapters()+" Study Material");
                    ct_lacture.setText(response.body().getData().get(0).getTotalLectures()+" Chapters");
                    ct_notes.setText(response.body().getData().get(0).getTotalNotes()+" Notes");
                    ct_video.setText(response.body().getData().get(0).getTotalVideos()+" Videos");
                    Glide.with(img_sub)
                            .load(Image_Path.Imagepath+"packages/"+response.body().getData().get(0).getImage())
                            .fitCenter()
                            .into(img_sub);
                }else{
                    ct_chapt.setText("0"+" Study Material");
                    ct_lacture.setText("0"+" Chapters");
                    ct_notes.setText("0"+" Notes");
                    ct_video.setText("0"+" Videos");
                    Glide.with(img_sub)
                            .load(Image_Path.Imagepath+"packages/"+getIntent().getStringExtra("image"))
                            .fitCenter()
                            .into(img_sub);

                    sub_name.setText(getIntent().getStringExtra("name"));
                }
            }

            @Override
            public void onFailure(Call<ResponsePkgDetail> call, Throwable t) {
                hideLoading();

            }
        });

    }

    private void getlivesessionreport(String str_pkgid) {
        ApiServices apiSer= API_Config.getApiClient_ByPost();
        Call<ResponseLiveSession> responseLiveSessionCall=apiSer.getLiveSession("LiveSession",str_pkgid);
        responseLiveSessionCall.enqueue(new Callback<ResponseLiveSession>() {
            @Override
            public void onResponse(Call<ResponseLiveSession> call, Response<ResponseLiveSession> response) {
                if (response.body().getRes().equalsIgnoreCase("success")) {
                    layout_gif.setVisibility(View.VISIBLE);
                } else {
                    layout_gif.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseLiveSession> call, Throwable t) {
                showMessage(t.getMessage().toString());
            }
        });
        }
    }
