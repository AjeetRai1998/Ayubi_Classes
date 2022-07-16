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


public class LiveVideoFragment extends BaseFragment {
    RecyclerView recycler_video;
    View view;
   // List<DataItem> list=new ArrayList<>();
    List<LiveEvents> items=new ArrayList<>();
    SwipeRefreshLayout refresh;
    public static boolean check=false;
    TextView tv_ls;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_live_video, container, false);
        recycler_video=view.findViewById(R.id.recycler_video);
        tv_ls=view.findViewById(R.id.tv_ls);
        AddVideo();
        refresh=view.findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                items.clear();
                AddVideo();
                refresh.setRefreshing(false);
            }
        });

        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {

    }

    private void AddVideo() {
        items.clear();
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseLive> call=apiServices.LiveVideo("LiveSession", Videos.course_Id);
        call.enqueue(new Callback<ResponseLive>() {
            @Override
            public void onResponse(Call<ResponseLive> call, Response<ResponseLive> response) {
                hideLoading();
                check=false;
                if (response.body() != null) {
                    LoggerUtil.logItem(response.body().getData());
                    if (response.body().getRes().equalsIgnoreCase("success")) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                       if (response.body().getData().get(i).getSession().equalsIgnoreCase("start")) {
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

                       recycler_video.setHasFixedSize(true);
                       recycler_video.setNestedScrollingEnabled(false);
                       recycler_video.setLayoutManager(new LinearLayoutManager(getContext()));
                       recycler_video.setLayoutManager(new LinearLayoutManager(getContext()));
                       recycler_video.setAdapter(new VideoAdapter(getContext(), items));
                         /*   String backend_date = response.body().getData().get(i).getLivedate() + " " + response.body().getData().get(i).getLivetime();
                            String current_date = Utils.getDate() + " " + Utils.getCurrenttime();
                            String current_time = Utils.getCurrenttime();
                            String backend_time = response.body().getData().get(i).getLivetime() + ":00";
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
                            Date strCurrent = null;
                            try {
                                strCurrent = sdf1.parse(current_date);
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
                                LoggerUtil.logItem(hours + ":" + minutes + ":" + seconds);
                                String current_duration = hours + ":" + minutes + ":" + seconds;

                                Date da1 = null;
                                Date da2 = null;
                                try {

                                    da1 = df.parse(duration);//duration
                                    da2 = df.parse(current_duration);//current_duration

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                int isLive=date1.compareTo(date2);

                                if (diff>0) {
                                    if (da2.getTime() - da1.getTime() > 0) {
                                        showMessage("data no available");
                                    } else {
                                        LiveEvents liveEvents = new LiveEvents(response.body().getData().get(i).getId(), response.body().getData().get(i).getLiveuserid(), response.body().getData().get(i).getName(), response.body().getData().get(i).getTopic()
                                                , response.body().getData().get(i).getJsonMemberClass(),
                                                response.body().getData().get(i).getDuration(),
                                                response.body().getData().get(i).getSubject(),
                                                response.body().getData().get(i).getShortDesc(),
                                                response.body().getData().get(i).getUrl(),
                                                response.body().getData().get(i).getLivedate() + "" + response.body().getData().get(i).getLivetime(),
                                                response.body().getData().get(i).getDate() + response.body().getData().get(i).getTime());
                                        items.add(liveEvents);


                                    }
                                }
                                // list=response.body().getData();
                            } else {
                                // your_date_is_outdated = false;
                                showMessage("data not found");
                            }

                        }
                        recycler_video.setHasFixedSize(true);
                        recycler_video.setNestedScrollingEnabled(false);
                        recycler_video.setLayoutManager(new LinearLayoutManager(getContext()));

                        recycler_video.setLayoutManager(new LinearLayoutManager(getContext()));
                        recycler_video.setAdapter(new VideoAdapter(getContext(), items));


                    } else {
                        showMessage("No data available");
                        hideLoading();
                    }
                }else {
                    showMessage("No data available");*/
                        }


                    }else {
                        tv_ls.setVisibility(View.VISIBLE);
                        recycler_video.setVisibility(View.GONE);
                    }
                }else {
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

    @Override
    public void onResume() {
        if (check==true){
            AddVideo();
        }

        super.onResume();
    }
}