package com.emergence.study_app.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Activity.Notes_Video_Activity;
import com.emergence.study_app.Adapter.Video_Adapter;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseFragment;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.Video.ResponseVideo;
import com.emergence.study_app.newTech.utils.Utils;
import com.example.study_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Video_Fragment extends BaseFragment {
    RecyclerView video_recycler;
    View view;
    public static int videoransition=0;
    PreferencesManager preferencesManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view=inflater.inflate(R.layout.fragment_video_, container, false);
         preferencesManager=new PreferencesManager(getContext());
        AddVideo();
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {

    }

    private void AddVideo() {
        String str_pkgId= Notes_Video_Activity.package_id;
        String str_chptrId= Notes_Video_Activity.chaptr_id;
        String str_lactureId= Notes_Video_Activity.lact;

        showLoading();
        video_recycler=view.findViewById(R.id.video_recycler);
        ApiServices apiServices1= API_Config.getApiClient_ByPost();
        Call<ResponseVideo> videoCall;
        if(videoransition==1) {
            videoCall = apiServices1.getAllVideos(preferencesManager.getUserId(),"AllVideos",str_pkgId);
        }else{
            videoCall=apiServices.getVideo(preferencesManager.getUserId(),"Videos",str_pkgId,str_chptrId,str_lactureId);
        }
        videoCall.enqueue(new Callback<ResponseVideo>() {
            @Override
            public void onResponse(Call<ResponseVideo> call, Response<ResponseVideo> response) {
               hideLoading();
                Utils.CheckUserSession(getActivity(),preferencesManager.getUserId(),preferencesManager.getSession_id());
                if (response.body().getRes().equalsIgnoreCase("success")){
                   // video_recycler.setHasFixedSize(true);
                    video_recycler.setLayoutManager(new GridLayoutManager(getContext(),2));
                    video_recycler.setAdapter(new Video_Adapter(response.body().getData(),getActivity()));
                }else {
                }
            }

            @Override
            public void onFailure(Call<ResponseVideo> call, Throwable t) {
                hideLoading();
            }
        });
    }
}