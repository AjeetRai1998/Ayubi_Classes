package com.emergence.study_app.live;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.emergence.study_app.Adapter.Course_Adapter;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.My_Order.DataItem;
import com.emergence.study_app.newTech.retrofit.model.My_Order.ResponseMyOrder;
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

public class LivePackages extends BaseActivity {
    ImageView iv_back;
    RecyclerView recyclerView;
    LinearLayout l_nodata;
    PreferencesManager preferencesManager;
    List<DataItem> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_packages);
        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        preferencesManager=new PreferencesManager(LivePackages.this);
        String str_id=preferencesManager.getUserId();
        l_nodata=findViewById(R.id.no_data);
        recyclerView=findViewById(R.id.recycler_course);
        getMyOrder(str_id);
    }
    private void getMyOrder(String str_id) {
        showLoading();
        showMessage(str_id);
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseMyOrder> myOrderCall=apiServices.getMyOrders("MyOrders",str_id,"Package");
        myOrderCall.enqueue(new Callback<ResponseMyOrder>() {
            @Override
            public void onResponse(Call<ResponseMyOrder> call, Response<ResponseMyOrder> response) {
                hideLoading();
                Utils.CheckUserSession(LivePackages.this,preferencesManager.getUserId(),preferencesManager.getSession_id());

                if (response.body().getRes().equalsIgnoreCase("success")){
                    for (int i=0;i<response.body().getData().size();i++){
                        if (response.body().getData().get(i).getExpiry_date().equalsIgnoreCase("")){
                            list.add(response.body().getData().get(i));
                            LivePackagesAdapter course_adapter=new LivePackagesAdapter(list,getApplicationContext());
                            GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),2);
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
                                LivePackagesAdapter course_adapter=new LivePackagesAdapter(list,getApplicationContext());
                                GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),2);
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

}