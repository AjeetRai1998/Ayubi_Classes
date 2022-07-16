package com.emergence.study_app.liveVideo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.emergence.study_app.newTech.constants.BaseFragment;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.responseLive.ResponseLive;
import com.emergence.study_app.newTech.utils.LoggerUtil;
import com.example.study_app.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CompletedVideo extends BaseFragment {
    View view;
    RecyclerView recycler_complte;
    List<LiveEvents> items=new ArrayList<>();
    SwipeRefreshLayout refressh;
    TextView tv_ls;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_completed_video, container, false);
        recycler_complte=view.findViewById(R.id.recycler_complte);
        tv_ls=view.findViewById(R.id.tv_ls);
        AddVideo();
        refressh=view.findViewById(R.id.refressh);
        refressh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                items.clear();
                AddVideo();
                refressh.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {

    }
    private void AddVideo() {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseLive> call=apiServices.LiveVideo("LiveSession", Videos.course_Id);
        call.enqueue(new Callback<ResponseLive>() {
            @Override
            public void onResponse(Call<ResponseLive> call, Response<ResponseLive> response) {
                hideLoading();
                LoggerUtil.logItem(response.body().getData());
                if (response.body().getRes().equalsIgnoreCase("success")) {

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        if (response.body().getData().get(i).getSession().equalsIgnoreCase("end")) {
                            tv_ls.setVisibility(View.GONE);
                            LiveEvents liveEvents = new LiveEvents(response.body().getData().get(i).getId(), "", "", response.body().getData().get(i).getTopic()
                                    , "",
                                    response.body().getData().get(i).getDuration(),
                                    "",
                                    response.body().getData().get(i).getShortDesc(),
                                    response.body().getData().get(i).getUrl(),
                                    response.body().getData().get(i).getLivedate() + " " + response.body().getData().get(i).getLivetime(),
                                    response.body().getData().get(i).getDate() + response.body().getData().get(i).getTime());
                            items.add(liveEvents);
                        }
                        recycler_complte.setHasFixedSize(true);
                        recycler_complte.setNestedScrollingEnabled(false);
                        recycler_complte.setLayoutManager(new LinearLayoutManager(getContext()));

                        recycler_complte.setLayoutManager(new LinearLayoutManager(getContext()));
                        recycler_complte.setAdapter(new CompletedVideoAdapter(getContext(), items));



                      /*  String backend_date=response.body().getData().get(i).getLivedate()+" "+response.body().getData().get(i).getLivetime();
                        String current_date= Utils.getDate()+" "+Utils.getCurrenttime();
                        String current_time=Utils.getCurrenttime();
                        String backend_time=response.body().getData().get(i).getLivetime()+":00";
                        String duration="";
                        String duration1 = response.body().getData().get(i).getDuration();
                        String[] dur=duration1.split(":");
                        if (dur.length<3){
                            duration="00:"+response.body().getData().get(i).getDuration();
                        }else {
                            duration=response.body().getData().get(i).getDuration();
                        }

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date strDate = null;
                        Date strCurrent=null;
                        try {
                            strCurrent=sdf1.parse(current_date);
                            strDate = sdf.parse(backend_date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (strCurrent.after(strDate)) {
                            //  your_date_is_outdated = true;
                            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                            Date date1 = null;
                            Date date2 = null;
                            try {
                                date1 = df.parse(backend_time);
                                date2 = df.parse(current_time);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            long diff = date2.getTime() - date1.getTime();

                            int timeInSeconds = (int) diff / 1000;
                            int hours, minutes, seconds;
                            hours = timeInSeconds / 3600;
                            timeInSeconds = timeInSeconds - (hours * 3600);
                            minutes = timeInSeconds / 60;
                            timeInSeconds = timeInSeconds - (minutes * 60);
                            seconds = timeInSeconds;
                            LoggerUtil.logItem(hours+":"+minutes+":"+seconds);
                            String current_duration=hours+":"+minutes+":"+seconds;

                            Date da1 = null;
                            Date da2 = null;
                            try {

                                da1 = df.parse(duration);
                                da2 = df.parse(current_duration);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (date1.compareTo(date2)>0){
                                LiveEvents liveEvents=new LiveEvents(response.body().getData().get(i).getId(),"","",response.body().getData().get(i).getTopic()
                                        ,"",
                                        response.body().getData().get(i).getDuration(),
                                        "",
                                        response.body().getData().get(i).getShortDesc(),
                                        response.body().getData().get(i).getUrl(),
                                        response.body().getData().get(i).getLivedate()+" "+response.body().getData().get(i).getLivetime(),
                                        response.body().getData().get(i).getDate()+response.body().getData().get(i).getTime());
                                items.add(liveEvents);
                            }else {
                                showMessage("data not available");
                            }
                            // list=response.body().getData();
                        }
                        else{
                            // your_date_is_outdated = false;
                            showMessage("data not found");
                        }

                    }
                    recycler_complte.setHasFixedSize(true);
                    recycler_complte.setNestedScrollingEnabled(false);
                    recycler_complte.setLayoutManager(new LinearLayoutManager(getContext()));

                    recycler_complte.setLayoutManager(new LinearLayoutManager(getContext()));
                    recycler_complte.setAdapter(new CompletedVideoAdapter(getContext(), items));




                }else {
                    showMessage("data not found");
                    hideLoading();*/
                    }
                }else {
                    tv_ls.setVisibility(View.VISIBLE);
                    recycler_complte.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseLive> call, Throwable t) {
                LoggerUtil.logItem(t);
                showMessage("failure");
                hideLoading();

            }
        });

    }


}