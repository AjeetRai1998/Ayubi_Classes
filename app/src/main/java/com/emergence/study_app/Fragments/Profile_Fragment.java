package com.emergence.study_app.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.emergence.study_app.Activity.About_us_Page;
import com.emergence.study_app.Activity.Edit_profile_Activity;
import com.emergence.study_app.Activity.Help_Support_Activity;
import com.emergence.study_app.Activity.Login_Activity;
import com.emergence.study_app.Activity.Notification_Activity;
import com.emergence.study_app.Activity.Privacy_Policy_Activity;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseFragment;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.Image_Path;
import com.emergence.study_app.newTech.retrofit.model.Get_User.ResponseGetUser;
import com.example.study_app.R;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Profile_Fragment extends BaseFragment {
    SwipeRefreshLayout swipeRefreshLayout;

    ImageView menu_icon,back_icon;
    CircleImageView profile_img;
    LinearLayout pr_logout,pr_detail,pr_about,pr_privacy,pr_help,dr_notification,dr_profile,pr_terms;
    TextView pr_name,pr_mobile;
    PreferencesManager preferencesManager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile_, container, false);
        preferencesManager=new PreferencesManager(getActivity());
        pr_mobile=view.findViewById(R.id.pr_mobile);
        pr_name=view.findViewById(R.id.pr_name);
        dr_profile=view.findViewById(R.id.dr_profile);
        pr_about=view.findViewById(R.id.pr_about);
        pr_terms=view.findViewById(R.id.pr_terms_condition);
        pr_logout=view.findViewById(R.id.pr_logout);
        dr_notification=view.findViewById(R.id.dr_notification);
        pr_privacy=view.findViewById(R.id.pr_policy);
        pr_help=view.findViewById(R.id.pr_help);
        profile_img=view.findViewById(R.id.profie);
        pr_detail=view.findViewById(R.id.pr_detail);
        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh);

        String str_id=preferencesManager.getUserId();
        getUserDetail(str_id);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUserDetail(str_id);
                swipeRefreshLayout.setRefreshing(false);
            }
        });



        dr_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Edit_profile_Activity.class));
            }
        });
        pr_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Edit_profile_Activity.class));
            }
        });
        pr_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), About_us_Page.class));
            }
        });
        pr_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Privacy_Policy_Activity.class));
            }
        });
        pr_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Help_Support_Activity.class);
                intent.putExtra("check","support");
                startActivity(intent);            }
        });
        dr_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Notification_Activity.class));
            }
        });

        pr_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogButtonClicked();
            }
        });
        pr_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Help_Support_Activity.class);
                intent.putExtra("check","terms");
                startActivity(intent);


            }
        });

        return view;
    }

    public void showAlertDialogButtonClicked(){

        AlertDialog.Builder builder
                = new AlertDialog.Builder(getActivity());
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        builder.setView(customLayout);
        final AlertDialog alertDialog = builder.show();
        final TextView yes=customLayout.findViewById(R.id.dialog_button_yes);
        final TextView no=customLayout.findViewById(R.id.dialog_button_no);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferencesManager.setUserId("");
                startActivity(new Intent(getActivity(), Login_Activity.class));
                alertDialog.dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });


    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {

    }

    private void getUserDetail(String str_id) {
        ApiServices apiServi= API_Config.getApiClient_ByPost();
        Call<ResponseGetUser> userCall=apiServi.getGetUser(str_id,"GetUser");
        userCall.enqueue(new Callback<ResponseGetUser>() {
            @Override
            public void onResponse(Call<ResponseGetUser> call, Response<ResponseGetUser> response) {
                if (response.body().getRes().equalsIgnoreCase("success")){
                    pr_name.setText(response.body().getData().getName());
                    pr_mobile.setText("+91-"+response.body().getData().getMobile());
                    Glide.with(profile_img)
                            .load(Image_Path.Imagepath+"students/"+response.body().getData().getImage())
                            .fitCenter()
                            .into(profile_img);
                }else {
                }
            }

            @Override
            public void onFailure(Call<ResponseGetUser> call, Throwable t) {


            }
        });
    }
}