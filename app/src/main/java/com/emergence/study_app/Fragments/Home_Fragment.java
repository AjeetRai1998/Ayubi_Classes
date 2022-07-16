package com.emergence.study_app.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.emergence.study_app.Activity.CoachingActivity;
import com.emergence.study_app.Activity.ComboActivity;
import com.emergence.study_app.Activity.Coupon;
import com.emergence.study_app.Activity.FreeVideos;
import com.emergence.study_app.Adapter.Banner_Adapter;
import com.emergence.study_app.Adapter.ChangeCoachingAdapter;
import com.emergence.study_app.Adapter.Class_Adapter;
import com.emergence.study_app.Adapter.ComboAdapter;
import com.emergence.study_app.Adapter.FreeVideoAdapter;
import com.emergence.study_app.Adapter.Home_Recycler_Adapter;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseFragment;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.Image_Path;
import com.emergence.study_app.newTech.retrofit.model.Banner.ResponseBanner;
import com.emergence.study_app.newTech.retrofit.model.Class.ResponseClass;
import com.emergence.study_app.newTech.retrofit.model.Coaching.ResponseCoaching;
import com.emergence.study_app.newTech.retrofit.model.Get_User.ResponseGetUser;
import com.emergence.study_app.newTech.retrofit.model.Subject.ResponseSubByCoaching;
import com.emergence.study_app.newTech.retrofit.model.responseCheckAppUpdate.ResponseCheckAppUpdate;
import com.emergence.study_app.newTech.retrofit.model.responseComboOffer.DataItem;
import com.emergence.study_app.newTech.retrofit.model.responseComboOffer.ResponseComboOffer;
import com.emergence.study_app.newTech.retrofit.model.responseFreeVideos.ResponseFreeVideo;
import com.emergence.study_app.newTech.retrofit.model.responseSendToken.ResponseSendToken;
import com.emergence.study_app.newTech.utils.Utils;
import com.emergence.study_app.testSeriese.QuestionActivity;
import com.example.study_app.BuildConfig;
import com.example.study_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.smarteist.autoimageslider.SliderView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_Fragment extends BaseFragment implements Coupon {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView,rec_class;
    PreferencesManager preferencesManager;
    SliderView sliderView;
    RecyclerView recycler_combo_offer,recycler_free_videos,recycler_coaching;
    Button view_all_combo_btn,view_free_videos,view_coaching;
    String token="";
    List<DataItem> list=new ArrayList<>();
    LinearLayout ll_free_videos;
    android.app.AlertDialog alert2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home_, container, false);
        preferencesManager=new PreferencesManager(getActivity());
        view_coaching=view.findViewById(R.id.view_coaching);
        view_coaching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),CoachingActivity.class));
            }
        });

        CheckAppUpdateApi();

        ll_free_videos=view.findViewById(R.id.ll_free_videos);
        view_free_videos=view.findViewById(R.id.view_free_videos);
        recycler_free_videos=view.findViewById(R.id.recycler_free_videos);
        recycler_coaching=view.findViewById(R.id.recycler_coaching);
        view_free_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), FreeVideos.class));
            }
        });


        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            //   Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            // Toast.makeText(MainActivity.this, "Fetching FCM registration token failed", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);

                        updateTOken(token);
                        // Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });



        view_all_combo_btn=view.findViewById(R.id.view_all_combo_btn);
        view_all_combo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ComboActivity.class));
            }
        });

        recyclerView=(RecyclerView)view.findViewById(R.id.home_recycler);
        rec_class=(RecyclerView)view.findViewById(R.id.rec_class);
        rec_class.setVisibility(View.GONE);
        swipeRefreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        recycler_combo_offer=view.findViewById(R.id.recycler_combo_offer);

       sliderView=view.findViewById(R.id.slider);

       swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               getSlider();
               CheckAppUpdateApi();
               FirebaseMessaging.getInstance().setAutoInitEnabled(true);
               FirebaseMessaging.getInstance().getToken()
                       .addOnCompleteListener(new OnCompleteListener<String>() {
                           @Override
                           public void onComplete(@NonNull Task<String> task) {
                               if (!task.isSuccessful()) {
                                   //   Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                   // Toast.makeText(MainActivity.this, "Fetching FCM registration token failed", Toast.LENGTH_SHORT).show();
                                   return;
                               }

                               // Get new FCM registration token
                               token = task.getResult();

                               // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);

                               updateTOken(token);
                               // Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                           }
                       });



               swipeRefreshLayout.setRefreshing(false);
           }
       });
        getSlider();








        return view;


    }

    private void CheckAppUpdateApi() {
        ApiServices apiServices=API_Config.getApiClient_ByPost();
        Call<ResponseCheckAppUpdate> call=apiServices.checkAppUpdate("get_version");
        call.enqueue(new Callback<ResponseCheckAppUpdate>() {
            @Override
            public void onResponse(Call<ResponseCheckAppUpdate> call, Response<ResponseCheckAppUpdate> response) {
                if (response.body().getRes().equalsIgnoreCase("success")){
                    if (response.body().getData().getVersion().equals(BuildConfig.VERSION_NAME)){

                    }else {
                        ShowAppUpdateDailoge(getActivity());
                    }

                }else {
                    showMessage(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResponseCheckAppUpdate> call, Throwable t) {

            }
        });

    }


    public void ShowAppUpdateDailoge(Context ctx) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptView;
        promptView = layoutInflater.inflate(R.layout.update_available, null);
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(ctx);
        alertDialogBuilder.setView(promptView);
        alert2 = alertDialogBuilder.create();
        alert2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert2.setCancelable(false);
        Button ok=promptView.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert2.dismiss();
                getActivity().finish();
            }
        });
        Button result=promptView.findViewById(R.id.result);
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert2.dismiss();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=emergence.infotech.ayubi"));
                startActivity(intent);
            }
        });

        alert2.show();
    }


    public void updateTOken(String tok){
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseSendToken> call=apiServices.sendToken("token_update",preferencesManager.getUserId(),tok);
        call.enqueue(new Callback<ResponseSendToken>() {
            @Override
            public void onResponse(Call<ResponseSendToken> call, Response<ResponseSendToken> response) {
                if (response.body().getRes().equalsIgnoreCase("success")){

                }
            }

            @Override
            public void onFailure(Call<ResponseSendToken> call, Throwable t) {

            }
        });

    }


    private void AddComboOfferItem() {
        ApiServices apiServices=API_Config.getApiClient_ByPost();
        Call<ResponseComboOffer> call=apiServices.getComboOffer("Get_Combo",preferencesManager.getCoaching_id(),preferencesManager.getUserId());
        call.enqueue(new Callback<ResponseComboOffer>() {
            @Override
            public void onResponse(Call<ResponseComboOffer> call, Response<ResponseComboOffer> response) {
               getFreeVideos();
                if (response.body().getRes().equalsIgnoreCase("success")){

                    for (int i=0;i<response.body().getData().size();i++){
                        if (response.body().getData().get(i).getExpiry_date().equalsIgnoreCase("")){
                            list.add(response.body().getData().get(i));
                            //list.remove(i);
                            ComboActivity.check=false;
                            recycler_combo_offer.setVisibility(View.VISIBLE);
                            recycler_combo_offer.setHasFixedSize(true);
                            recycler_combo_offer.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                            recycler_combo_offer.setAdapter(new ComboAdapter(getContext(),list));
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
                                //list.remove(i);
                                ComboActivity.check=false;
                                recycler_combo_offer.setVisibility(View.VISIBLE);
                                recycler_combo_offer.setHasFixedSize(true);
                                recycler_combo_offer.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                                recycler_combo_offer.setAdapter(new ComboAdapter(getContext(),list));
                            }else {

//                            ComboActivity.check=false;
//                            recycler_combo_offer.setVisibility(View.VISIBLE);
//                            recycler_combo_offer.setHasFixedSize(true);
//                            recycler_combo_offer.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
//                            recycler_combo_offer.setAdapter(new ComboAdapter(getContext(),response.body().getData()));
                            }
                        }

                    }

                }else {
                    recycler_combo_offer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseComboOffer> call, Throwable t) {

                hideLoading();
            }
        });

    }

    private void getFreeVideos() {
        ApiServices apiServices=API_Config.getApiClient_ByPost();
        Call<ResponseFreeVideo> call=apiServices.getFreeVideo("FreeVideo","1");
        call.enqueue(new Callback<ResponseFreeVideo>() {
            @Override
            public void onResponse(Call<ResponseFreeVideo> call, Response<ResponseFreeVideo> response) {
                getCoaching();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    ll_free_videos.setVisibility(View.VISIBLE);
                    recycler_free_videos.setVisibility(View.VISIBLE);
                    recycler_free_videos.setHasFixedSize(true);
                    recycler_free_videos.setNestedScrollingEnabled(false);
                    recycler_free_videos.setLayoutManager(new GridLayoutManager(getContext(),2));
                    recycler_free_videos.setAdapter(new FreeVideoAdapter(response.body().getData(), getContext()));
                }else {
                    recycler_free_videos.setVisibility(View.GONE);
                    ll_free_videos.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<ResponseFreeVideo> call, Throwable t) {
                hideLoading();
            }
        });
    }

    private void getStClass() {
        showLoading();
        ApiServices api=API_Config.getApiClient_ByPost();
        Call<ResponseClass> classCall=api.getClass("Class");
        classCall.enqueue(new Callback<ResponseClass>() {
            @Override
            public void onResponse(Call<ResponseClass> call, Response<ResponseClass> response) {

                if (response.body().getRes().equalsIgnoreCase("success")){
                    Class_Adapter class_adapter=new Class_Adapter(response.body().getData(),getActivity());
                    rec_class.setAdapter(class_adapter);
                    rec_class.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

                }else{
                }

            }

            @Override
            public void onFailure(Call<ResponseClass> call, Throwable t) {
                hideLoading();
                showMessage(t.getMessage().toString());

            }
        });
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {

    }

    private void getSlider() {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseBanner> call=apiServices.getBanner("Banner","1");
        call.enqueue(new Callback<ResponseBanner>() {
            @Override
            public void onResponse(Call<ResponseBanner> call, Response<ResponseBanner> response) {
                String str_Class=preferencesManager.getClass_id();
                String str_id=preferencesManager.getUserId();
                Bundle arguments = getArguments();
                if (arguments!=null) {
                    String class_id=arguments.get("class").toString();
                    str_Class=(class_id);
                }
                getSubject(str_Class,str_id);

                if (response.body().getRes().equalsIgnoreCase("success")){
                    sliderView.setVisibility(View.VISIBLE);
                    Banner_Adapter adapter = new Banner_Adapter(response.body().getData(),getActivity());
                    sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
                    sliderView.setSliderAdapter(adapter);
                    sliderView.setScrollTimeInSec(3);
                    sliderView.setAutoCycle(true);
                    sliderView.startAutoCycle();
                }else {
                    sliderView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBanner> call, Throwable t) {
                hideLoading();

            }
        });
    }

    private void getSubject(String class_id,String str_id) {
        ApiServices subservic= API_Config.getApiClient_ByPost();
        Call<ResponseSubByCoaching> coachingCall=subservic.getSubjectByClassAndCoaching("SubjectByClassAndCoaching",class_id,"1",str_id);
        coachingCall.enqueue(new Callback<ResponseSubByCoaching>() {
            @Override
            public void onResponse(Call<ResponseSubByCoaching> call, Response<ResponseSubByCoaching> response) {
              //  AddComboOfferItem();
                getFreeVideos();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setNestedScrollingEnabled(false);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(new Home_Recycler_Adapter(response.body().getData(),getActivity()));

                }else{
                    recyclerView.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<ResponseSubByCoaching> call, Throwable t) {
                hideLoading();
                Toast.makeText(getActivity(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void getCoaching() {
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseCoaching> coachingCall=apiServices.getCoaching("Coaching");
        coachingCall.enqueue(new Callback<ResponseCoaching>() {
            @Override
            public void onResponse(Call<ResponseCoaching> call, Response<ResponseCoaching> response) {
                hideLoading();
                getUser();
                Utils.CheckUserSession(getActivity(),preferencesManager.getUserId(),preferencesManager.getSession_id());

                if (response.body().getRes().equalsIgnoreCase("success")){
                    CoachingActivity.check=false;
                    recycler_coaching.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                    recycler_coaching.setHasFixedSize(true);
                    ChangeCoachingAdapter coaching_adapter=new ChangeCoachingAdapter(getContext(), response.body().getData(),Home_Fragment.this);
                    recycler_coaching.setAdapter(coaching_adapter);
                }else {

                }
            }

            @Override
            public void onFailure(Call<ResponseCoaching> call, Throwable t) {
                hideLoading();
            }
        });
    }


    @Override
    public void ApplyCoupon(String coupon_id, String coupon_code, String coupon_discount, String min_order_amount) {
        preferencesManager.setCoaching_id(coupon_id);
        getSlider();
    }

    private void getUser() {

        ApiServices Api_ser=API_Config.getApiClient_ByPost();
        Call<ResponseGetUser> userCall=Api_ser.getGetUser(preferencesManager.getUserId(),"GetUser");
        userCall.enqueue(new Callback<ResponseGetUser>() {
            @Override
            public void onResponse(Call<ResponseGetUser> call, Response<ResponseGetUser> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    preferencesManager.setMobileNo(response.body().getData().getMobile());
                    preferencesManager.setEmailId(response.body().getData().getEmail());

                }else {
                }

            }

            @Override
            public void onFailure(Call<ResponseGetUser> call, Throwable t) {
                hideLoading();

            }
        });
    }

    @Override
    public void onResume() {
        CheckAppUpdateApi();
        super.onResume();
    }
}