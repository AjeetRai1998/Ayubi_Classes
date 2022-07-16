package com.emergence.study_app.Fragments;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Activity.Notes_Video_Activity;
import com.emergence.study_app.Adapter.Notes_Adapter;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseFragment;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.Notes.ResponseNotes;
import com.emergence.study_app.newTech.utils.Utils;
import com.example.study_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notes_Fragment extends BaseFragment {
    RecyclerView notes_recycler;
    public static int notestransition=0;
    String str_pkgId="",str_chptrId="",str_lactureId="";
    PreferencesManager preferencesManager;
//    public static DataItem dataItem;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_notes_, container, false);
        preferencesManager=new PreferencesManager(getContext());
        // Inflate the layout for this fragment
        notes_recycler=view.findViewById(R.id.rec_note);
         str_pkgId= Notes_Video_Activity.package_id;
         str_chptrId= Notes_Video_Activity.chaptr_id;
        str_lactureId= Notes_Video_Activity.lact;
        getNotes();

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                101);


        return view;
    }


    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {

    }

    private void getNotes() {
        showLoading();
        ApiServices apiSer= API_Config.getApiClient_ByPost();
        Call<ResponseNotes > notesCall;
        if(notestransition==1) {
            notesCall = apiSer.getAllNotes("AllNotes", str_pkgId);
        }else{
            notesCall=apiServices.getNotes("Notes",str_pkgId,str_chptrId,str_lactureId);
        }
        notesCall.enqueue(new Callback<ResponseNotes>() {
            @Override
            public void onResponse(Call<ResponseNotes> call, Response<ResponseNotes> response) {
                hideLoading();
                Utils.CheckUserSession(getActivity(),preferencesManager.getUserId(),preferencesManager.getSession_id());

                if (response.body().getRes().equalsIgnoreCase("success")){
                    notes_recycler.setHasFixedSize(true);
                    GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
                    Notes_Adapter notes_adapter=new Notes_Adapter(response.body().getData(),getActivity());
                    notes_recycler.setAdapter(notes_adapter);
                    notes_recycler.setLayoutManager(gridLayoutManager);
                }else {

                }

            }

            @Override
            public void onFailure(Call<ResponseNotes> call, Throwable t) {
                hideLoading();

            }
        });

    }
}