package com.emergence.study_app.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Adapter.Course_Adapter;
import com.emergence.study_app.Adapter.VideoPackageOrderAdapter;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseFragment;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.My_Order.DataItem;
import com.emergence.study_app.newTech.retrofit.model.My_Order.ResponseMyOrder;
import com.emergence.study_app.newTech.retrofit.model.videoPackageOrder.ResponseVideoPackageOrder;
import com.emergence.study_app.newTech.utils.Utils;
import com.example.study_app.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Course_Fragment extends BaseFragment {
    RecyclerView recyclerView;
    LinearLayout l_nodata;
    PreferencesManager preferencesManager;
    List<DataItem> list=new ArrayList<>();
    TextView tv_live_course,tv_recorder_course;
    RecyclerView recycler_video_pack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_course_, container, false);
        preferencesManager=new PreferencesManager(getActivity());
        String str_id=preferencesManager.getUserId();
        l_nodata=view.findViewById(R.id.no_data);
        tv_live_course=view.findViewById(R.id.tv_live_course);
        recycler_video_pack=view.findViewById(R.id.recycler_video_pack);
        tv_recorder_course=view.findViewById(R.id.tv_recorder_course);
        recyclerView=view.findViewById(R.id.recycler_course);

        tv_recorder_course.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.GONE);
                getVideoPackageOrder();
                tv_recorder_course.setTextColor(getContext().getColor(R.color.white));
                tv_recorder_course.setBackgroundResource(R.drawable.rect_bg);

                tv_live_course.setTextColor(getContext().getColor(R.color.colorPrimary));
                tv_live_course.setBackgroundResource(R.drawable.rectangle_stroke_bg);

            }
        });
        tv_live_course.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                recycler_video_pack.setVisibility(View.GONE);
                getMyOrder();
                tv_recorder_course.setTextColor(getContext().getColor(R.color.colorPrimary));
                tv_recorder_course.setBackgroundResource(R.drawable.rectangle_stroke_bg);

                tv_live_course.setTextColor(getContext().getColor(R.color.white));
                tv_live_course.setBackgroundResource(R.drawable.rect_bg);

            }
        });
        getMyOrder();

        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {

    }

    private void getMyOrder() {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseMyOrder> myOrderCall=apiServices.getMyOrders("MyOrders",preferencesManager.getUserId(),"Package");
        myOrderCall.enqueue(new Callback<ResponseMyOrder>() {
            @Override
            public void onResponse(Call<ResponseMyOrder> call, Response<ResponseMyOrder> response) {
                hideLoading();
                Utils.CheckUserSession(getActivity(),preferencesManager.getUserId(),preferencesManager.getSession_id());

                if (response.body().getRes().equalsIgnoreCase("success")){
                    recyclerView.setVisibility(View.VISIBLE);
                    l_nodata.setVisibility(View.GONE);
                    list.clear();
                    for (int i=0;i<response.body().getData().size();i++){
                        if (response.body().getData().get(i).getExpiry_date().equalsIgnoreCase("")){
                            list.add(response.body().getData().get(i));
                            Course_Adapter course_adapter=new Course_Adapter(list,getActivity());
                            GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(course_adapter);
                            recyclerView.setLayoutManager(gridLayoutManager);
                        }else {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date strDate = null;
                            Date strDate1=null;
                            try {
                                strDate = sdf.parse(response.body().getData().get(i).getExpiry_date());
                                strDate1 = sdf.parse(Utils.getDate());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (!strDate1.after(strDate)) {
                                list.add(response.body().getData().get(i));
                                Course_Adapter course_adapter=new Course_Adapter(list,getActivity());
                                GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setAdapter(course_adapter);
                                recyclerView.setLayoutManager(gridLayoutManager);
                            }else {
//                            Course_Adapter course_adapter=new Course_Adapter(response.body().getData(),getActivity());
//                            GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
//                            recyclerView.setHasFixedSize(true);
//                            recyclerView.setAdapter(course_adapter);
//                            recyclerView.setLayoutManager(gridLayoutManager);
                            }
                        }



                    }



                }else {
                    recyclerView.setVisibility(View.GONE);
                    l_nodata.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ResponseMyOrder> call, Throwable t) {
                hideLoading();

            }
        });

    }



    private void getVideoPackageOrder() {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseVideoPackageOrder> myOrderCall=apiServices.getVideoPackageOrder("VideoOrders",preferencesManager.getUserId(),"Package");
        myOrderCall.enqueue(new Callback<ResponseVideoPackageOrder>() {
            @Override
            public void onResponse(Call<ResponseVideoPackageOrder> call, Response<ResponseVideoPackageOrder> response) {
                hideLoading();
                Utils.CheckUserSession(getActivity(),preferencesManager.getUserId(),preferencesManager.getSession_id());
                if (response.body().getRes().equalsIgnoreCase("success")){
                    recycler_video_pack.setVisibility(View.VISIBLE);
                    l_nodata.setVisibility(View.GONE);
                    VideoPackageOrderAdapter course_adapter=new VideoPackageOrderAdapter(response.body().getData(),getActivity());
                    GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
                    recycler_video_pack.setHasFixedSize(true);
                    recycler_video_pack.setAdapter(course_adapter);
                    recycler_video_pack.setLayoutManager(gridLayoutManager);

                }else {
                    recycler_video_pack.setVisibility(View.GONE);
                    l_nodata.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ResponseVideoPackageOrder> call, Throwable t) {
                hideLoading();

            }
        });

    }

}