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

public class UpcomingVideoFragment extends BaseFragment {
    View view;
    RecyclerView recycler_upcoming;
   // List<DataItem> itemList=new ArrayList<>();
    List<LiveEvents> items=new ArrayList<>();
    SwipeRefreshLayout refresh;
    TextView tv_ls;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_upcoming_video, container, false);
        recycler_upcoming=view.findViewById(R.id.recycler_upcoming);

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
                        if (response.body().getData().get(i).getSession().equalsIgnoreCase("no")) {
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

                        recycler_upcoming.setHasFixedSize(true);
                        recycler_upcoming.setNestedScrollingEnabled(false);
                        recycler_upcoming.setLayoutManager(new LinearLayoutManager(getContext()));
                        recycler_upcoming.setLayoutManager(new LinearLayoutManager(getContext()));
                        recycler_upcoming.setAdapter(new UpcomingVideoAdapter(getContext(), items));
                      /*  String backend_date=response.body().getData().get(i).getLivedate()+" "+response.body().getData().get(i).getLivetime();
                        String current_date= Utils.getDate()+" "+Utils.getCurrenttime();
                        String current_time=Utils.getCurrenttime();

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date strDate = null;
                        Date strDate1 = null;
                        try {
                            strDate = sdf.parse(backend_date);
                            strDate1 = sdf.parse(current_date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (strDate1.after(strDate)) {
                          //  your_date_is_outdated = true;
                          showMessage("data not found");
                          //  itemList=response.body().getData();

                        }
                        else{
                            LiveEvents liveEvents=new LiveEvents(response.body().getData().get(i).getId(),"","",response.body().getData().get(i).getTopic()
                            ,"",
                                    response.body().getData().get(i).getDuration(),
                                    "",
                                    response.body().getData().get(i).getShortDesc(),
                                    response.body().getData().get(i).getUrl(),
                                    response.body().getData().get(i).getLivedate()+" "+response.body().getData().get(i).getLivetime(),
                                    response.body().getData().get(i).getDate()+response.body().getData().get(i).getTime());
                            items.add(liveEvents);
                           // your_date_is_outdated = false;

                        }

                    }
                    recycler_upcoming.setHasFixedSize(true);
                    recycler_upcoming.setNestedScrollingEnabled(false);
                    recycler_upcoming.setLayoutManager(new LinearLayoutManager(getContext()));

                    recycler_upcoming.setLayoutManager(new LinearLayoutManager(getContext()));
                    recycler_upcoming.setAdapter(new UpcomingVideoAdapter(getContext(), items));



                }else {
                    showMessage(response.body().getMsg());
                    hideLoading();*/
                    }
                }else {
                    tv_ls.setVisibility(View.VISIBLE);
                    recycler_upcoming.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseLive> call, Throwable t) {
                hideLoading();

            }
        });

    }

}