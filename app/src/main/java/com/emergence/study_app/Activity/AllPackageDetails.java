package com.emergence.study_app.Activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.Image_Path;
import com.emergence.study_app.newTech.retrofit.model.Subject.PackagesItem;
import com.emergence.study_app.newTech.retrofit.model.responseAllPackageDetails.DataItem;
import com.emergence.study_app.newTech.retrofit.model.responseAllPackageDetails.ResponseAllPackageDetails;
import com.emergence.study_app.newTech.utils.Utils;
import com.example.study_app.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllPackageDetails extends BaseActivity {
    ImageView iv_back;
    ImageView img_sub;
    TextView sub_name,price,cut_price,ct_chapt,ct_lacture,ct_notes,ct_video,description,buy_price;
    Button buy_btn;
    RecyclerView simmilar_recycler;
    CardView buy_lay;
    String packId="";
    String from="";
    PreferencesManager preferencesManager;
    public static DataItem data;
    double handling_charge=0;
    String amount="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_all_package_details);
        preferencesManager=new PreferencesManager(AllPackageDetails.this);

        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        buy_btn=findViewById(R.id.buy_btn);

        AddId();

        packId=getIntent().getStringExtra("id");
        from=getIntent().getStringExtra("from");

        if (from.equalsIgnoreCase("combo")){
            buy_lay.setVisibility(View.GONE);
        }else {
            buy_lay.setVisibility(View.VISIBLE);
        }

        getPackageDetailsApi();
    }

    private void getPackageDetailsApi() {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseAllPackageDetails> call=apiServices.getPackageDetails("SingleProduct",packId);
        call.enqueue(new Callback<ResponseAllPackageDetails>() {
            @Override
            public void onResponse(Call<ResponseAllPackageDetails> call, Response<ResponseAllPackageDetails> response) {
                hideLoading();
                Utils.CheckUserSession(AllPackageDetails.this,preferencesManager.getUserId(),preferencesManager.getSession_id());
                if (response.body().getRes().equalsIgnoreCase("success")){
                    sub_name.setText(response.body().getData().get(0).getName());
                    price.setText("\u20B9 "+response.body().getData().get(0).getPrice());
                    cut_price.setText("\u20B9 "+response.body().getData().get(0).getMrp());
                    buy_price.setText("\u20B9 "+response.body().getData().get(0).getPrice());
                    cut_price.setPaintFlags(cut_price.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                    description.setText(Html.fromHtml(response.body().getData().get(0).getDescription()));
                    ct_chapt.setText(response.body().getData().get(0).getTotalChapters()+" Subjects");
                    ct_lacture.setText(response.body().getData().get(0).getTotalLectures()+" Chapters");
                    ct_notes.setText(response.body().getData().get(0).getTotalNotes()+" Notes");
                    ct_video.setText(response.body().getData().get(0).getTotalVideos()+" Videos");
                    Glide.with(getApplicationContext()).load(Image_Path.Imagepath+"packages/"+response.body().getData().get(0).getImage()).into(img_sub);

                     amount=response.body().getData().get(0).getPrice();

                    handling_charge=(Integer.parseInt(amount)*2.6)/100;
                    amount= String.valueOf(Integer.parseInt(amount)+handling_charge);
                    buy_price.setText("\u20B9"+amount);


                    buy_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(AllPackageDetails.this,Buy_Activity.class);
                            intent.putExtra("course_name",response.body().getData().get(0).getName());
                            intent.putExtra("package_id",response.body().getData().get(0).getId());
                            intent.putExtra("price",""+amount);
                            intent.putExtra("mrp",""+response.body().getData().get(0).getMrp());
                            intent.putExtra("discount",""+response.body().getData().get(0).getDiscount());
                            intent.putExtra("t_video",""+response.body().getData().get(0).getTotalVideos());
                            intent.putExtra("t_lact",""+response.body().getData().get(0).getTotalLectures());
                            intent.putExtra("t_chapt",""+response.body().getData().get(0).getTotalChapters());
                            intent.putExtra("t_note",""+response.body().getData().get(0).getTotalNotes());
                            startActivity(intent);
                            finish();

                        }
                    });

                }else {

                }
            }

            @Override
            public void onFailure(Call<ResponseAllPackageDetails> call, Throwable t) {

            }
        });
    }

    private void AddId() {
        sub_name=findViewById(R.id.sub_name);
        price=findViewById(R.id.price);
        cut_price=findViewById(R.id.cut_price);
        ct_chapt=findViewById(R.id.ct_chapt);
        ct_lacture=findViewById(R.id.ct_lacture);
        ct_notes=findViewById(R.id.ct_notes);
        ct_video=findViewById(R.id.ct_video);
        description=findViewById(R.id.description);
        buy_price=findViewById(R.id.buy_price);
        img_sub=findViewById(R.id.img_sub);
        simmilar_recycler=findViewById(R.id.simmilar_recycler);
        buy_lay=findViewById(R.id.buy_lay);
    }


}