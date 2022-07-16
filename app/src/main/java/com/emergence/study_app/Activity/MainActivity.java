package com.emergence.study_app.Activity;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.emergence.study_app.Fragments.Course_Fragment;
import com.emergence.study_app.Fragments.Home_Fragment;
import com.emergence.study_app.Fragments.Profile_Fragment;
import com.emergence.study_app.Fragments.Store_Fragment;
import com.emergence.study_app.live.LivePackages;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.Image_Path;
import com.emergence.study_app.newTech.retrofit.model.Get_User.ResponseGetUser;
import com.emergence.study_app.newTech.retrofit.model.Notification.ResponseNotificastion;
import com.emergence.study_app.newTech.utils.Utils;
import com.emergence.study_app.quiz.QuizActivity;
import com.emergence.study_app.testSeriese.PurchasedTestSeries;
import com.emergence.study_app.testSeriese.TestSeries;
import com.example.study_app.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends BaseActivity {


    public static String API_KEY="AIzaSyARRG4WuA7ByNLbZ7wP3CTbLK0aFdszj2I";
    ImageView menu_icon,notification_icon;
    CircleImageView profile_img,tool_profile;
    TextView title_main,tv_mobile,tv_name,tv_cart_count;
    LinearLayout dr_profile,dr_terms_condition,dr_about,dr_privacy,dr_course,dr_course_store,dr_logout,dr_setting,dr_support,dr_notification;
    BottomNavigationView bottomNavigationView;
    PreferencesManager preferencesManager;
    String class_id="";
    DrawerLayout drawerLayout;
    LinearLayout ll_test_series,ll_my_test_series,ll_quiz,ll_refer,ll_wallet,ll_coaching,ll_live;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         preferencesManager=new PreferencesManager(MainActivity.this);
       // Utils.CheckUserSession(MainActivity.this,preferencesManager.getUserId(),preferencesManager.getSession_id());
        ll_quiz=findViewById(R.id.ll_quiz);
        title_main=findViewById(R.id.main_titile);
        dr_terms_condition=findViewById(R.id.terms);
       // tool_profile=findViewById(R.id.usr_profile);
        profile_img=findViewById(R.id.dr_img);
        dr_privacy=findViewById(R.id.dr_privacy);
        dr_about=findViewById(R.id.dr_about);
        dr_notification=findViewById(R.id.dr_notification);
        dr_support=findViewById(R.id.dr_support);
        tv_mobile=findViewById(R.id.tv_mobile);
        tv_name=findViewById(R.id.tv_name);
        dr_logout=findViewById(R.id.dr_logout);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        menu_icon=findViewById(R.id.menu_icon);
        dr_profile=findViewById(R.id.dr_profile);
        notification_icon=findViewById(R.id.img_notification);
        dr_course=findViewById(R.id.dr_course);
        dr_course_store=findViewById(R.id.dr_course_store);
        drawerLayout=findViewById(R.id.drawer);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        tv_cart_count=findViewById(R.id.tv_cart_count);
        getNtificastion();
        getSupportFragmentManager().beginTransaction().replace(R.id.parent_frame, new Home_Fragment()).commit();
        title_main.setText("Home");
        notification_icon.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(getApplicationContext(), Notification_Activity.class));
          }
        });

        if (Utils.isNetworkConnectedMainThred(MainActivity.this)){
        }else {
            showMessage("Not Connected to network");

        }

      String str_id=preferencesManager.getUserId();
      getUser(str_id);
        ll_coaching=findViewById(R.id.ll_coaching);
        ll_coaching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CoachingActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        ll_wallet=findViewById(R.id.ll_wallet);
        ll_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserWallet.class));
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        ll_refer=findViewById(R.id.ll_refer);
        ll_refer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ReferAndEarn.class));
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        ll_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), QuizActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
      menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
      dr_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),Edit_profile_Activity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
      dr_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.parent_frame, new Course_Fragment()).commit();
                title_main.setText("My Course");
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
      dr_course_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.parent_frame, new Store_Fragment()).commit();
                title_main.setText("Course Store");
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
      dr_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Help_Support_Activity.class);
                intent.putExtra("check","support");
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
      dr_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Notification_Activity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
      dr_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Privacy_Policy_Activity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
            }

        });
        ll_test_series=findViewById(R.id.ll_test_series);
        ll_test_series.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TestSeries.class));
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        ll_my_test_series=findViewById(R.id.ll_my_test_series);
        ll_my_test_series.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PurchasedTestSeries.class));
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        dr_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),About_us_Page.class));
                drawerLayout.closeDrawer(GravityCompat.START);
            }

        });
      dr_terms_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Help_Support_Activity.class);
                intent.putExtra("check","terms");
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
      dr_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                showAlertDialogButtonClicked();

            }
        });

        ll_live=findViewById(R.id.ll_live);
        ll_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LivePackages.class));

            }
        });

    }

    private void getNtificastion() {
        ApiServices apiSer= API_Config.getApiClient_ByPost();
        Call<ResponseNotificastion> responseNotificastions=apiSer.getNotification("Notification");
        responseNotificastions.enqueue(new Callback<ResponseNotificastion>() {
            @Override
            public void onResponse(Call<ResponseNotificastion> call, Response<ResponseNotificastion> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    tv_cart_count.setText(""+response.body().getData().size());
                }else {
                    tv_cart_count.setText("0");

                }

            }

            @Override
            public void onFailure(Call<ResponseNotificastion> call, Throwable t) {

            }
        });

    }


    private void getUser(String str_id) {
        showLoading();
        ApiServices user_api= API_Config.getApiClient_ByPost();
        Call<ResponseGetUser> userCall=user_api.getGetUser(str_id,"GetUser");
        userCall.enqueue(new Callback<ResponseGetUser>() {
            @Override
            public void onResponse(Call<ResponseGetUser> call, Response<ResponseGetUser> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    preferencesManager.setRef_Id(response.body().getData().getStudentId());
                    tv_mobile.setText("+91-"+response.body().getData().getMobile());
                    preferencesManager.setFull_Name(response.body().getData().getName());
                    tv_name.setText(response.body().getData().getName());
                    Glide.with(profile_img)
                            .load(Image_Path.Imagepath+"students/"+response.body().getData().getImage())
                            .fitCenter()
                            .into(profile_img);
                    Glide.with(profile_img)
                            .load(Image_Path.Imagepath+"students/"+response.body().getData().getImage())
                            .fitCenter()
                            .into(profile_img);
                }else {
                }
            }
            @Override
            public void onFailure(Call<ResponseGetUser> call, Throwable t) {
                hideLoading();
            }
        });
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selected_Fragment=null;
            switch (item.getItemId()){
                case R.id.nav_home:
                    selected_Fragment=new Home_Fragment();
                    title_main.setText("Home");
                    break;
                case R.id.nav_course:
                    selected_Fragment=new Course_Fragment();
                    title_main.setText("My Course");
                    break;
                case R.id.nav_store:
                    selected_Fragment=new Store_Fragment();
                    title_main.setText("Course Store");
                    break;
                case R.id.nav_profile:
                    selected_Fragment=new Profile_Fragment();
                    title_main.setText("My Profile");
                    break;
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.parent_frame, selected_Fragment)
                    .commit();
            return true;
        }
    };
    public void showAlertDialogButtonClicked(){
        AlertDialog.Builder builder
                = new AlertDialog.Builder(this);
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        builder.setView(customLayout);
        final AlertDialog alertDialog = builder.show();
        final TextView yes=customLayout.findViewById(R.id.dialog_button_yes);
        final TextView no=customLayout.findViewById(R.id.dialog_button_no);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferencesManager.setUserId("");
                preferencesManager.setClass_id("");
                startActivity(new Intent(getApplicationContext(),Login_Activity.class));
                finish();
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

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            // finishAffinity();
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
 
}