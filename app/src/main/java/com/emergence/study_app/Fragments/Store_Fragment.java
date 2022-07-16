package com.emergence.study_app.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Adapter.Home_Recycler_Adapter;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseFragment;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.Subject.ResponseSubByCoaching;
import com.emergence.study_app.newTech.utils.Utils;
import com.example.study_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Store_Fragment extends BaseFragment {
    RecyclerView store_rec;
    LinearLayout l_nodata;
    PreferencesManager preferencesManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_store_, container, false);
        preferencesManager=new PreferencesManager(getActivity());
        store_rec=view.findViewById(R.id.store_rec);
        l_nodata=view.findViewById(R.id.no_data);
        String str_id=preferencesManager.getUserId();
        String str_cls=preferencesManager.getClass_id();
        String str_coach=preferencesManager.getCoaching_id();
        getSubject(str_id,str_cls,str_coach);

        return view;
    }

    private void getSubject(String str_id,String str_cls,String str_coach) {
        showLoading();
        ApiServices apiServi= API_Config.getApiClient_ByPost();
        Call<ResponseSubByCoaching> call=apiServi.getSubjectByClassAndCoaching("SubjectByClassAndCoaching",str_cls,"1",str_id);
        call.enqueue(new Callback<ResponseSubByCoaching>() {
            @Override
            public void onResponse(Call<ResponseSubByCoaching> call, Response<ResponseSubByCoaching> response) {
                hideLoading();
                Utils.CheckUserSession(getActivity(),preferencesManager.getUserId(),preferencesManager.getSession_id());

                if (response.body().getRes().equalsIgnoreCase("success")){
                    store_rec.setLayoutManager(new LinearLayoutManager(getActivity()));
                    store_rec.setHasFixedSize(true);
                    store_rec.setAdapter(new Home_Recycler_Adapter(response.body().getData(),getActivity()));
                }else{
                    store_rec.setVisibility(View.GONE);
                    l_nodata.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseSubByCoaching> call, Throwable t) {
                hideLoading();

            }
        });
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {

    }
}