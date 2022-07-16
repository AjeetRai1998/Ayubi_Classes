package com.emergence.study_app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Adapter.ComboAdapter;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.responseComboOffer.ResponseComboOffer;
import com.emergence.study_app.newTech.utils.Utils;
import com.example.study_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComboActivity extends BaseActivity {
    ImageView iv_back;
    RecyclerView recycler_combo_offer;
    public static boolean check;
    PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo);
        preferencesManager=new PreferencesManager(ComboActivity.this);
         iv_back=findViewById(R.id.iv_back);
        recycler_combo_offer=findViewById(R.id.recycler_combo_offer);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        AddComboOfferItem();
    }
    private void AddComboOfferItem() {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseComboOffer> call=apiServices.getComboOffer("Get_Combo",preferencesManager.getCoaching_id(),preferencesManager.getUserId());
        call.enqueue(new Callback<ResponseComboOffer>() {
            @Override
            public void onResponse(Call<ResponseComboOffer> call, Response<ResponseComboOffer> response) {
                hideLoading();
                Utils.CheckUserSession(ComboActivity.this,preferencesManager.getUserId(),preferencesManager.getSession_id());
                if (response.body().getRes().equalsIgnoreCase("success")){
                    check=true;
                    recycler_combo_offer.setHasFixedSize(true);
                    recycler_combo_offer.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                    recycler_combo_offer.setAdapter(new ComboAdapter(getApplicationContext(),response.body().getData()));

                }else {
                }
            }

            @Override
            public void onFailure(Call<ResponseComboOffer> call, Throwable t) {

                hideLoading();
            }
        });

    }

}