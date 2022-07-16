package com.emergence.study_app.liveVideo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.utils.Utils;
import com.example.study_app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Videos extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.recycler_video)
    RecyclerView recyclerVideo;
    @BindView(R.id.tv_live)
    TextView tvLive;
    @BindView(R.id.tv_upcoming)
    TextView tvUpcoming;
    @BindView(R.id.tv_completed)
    TextView tvCompleted;
    public static String course_Id="";
    PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        ButterKnife.bind(this);
        preferencesManager=new PreferencesManager(Videos.this);
        course_Id=getIntent().getStringExtra("id");
         Utils.CheckUserSession(Videos.this,preferencesManager.getUserId(),preferencesManager.getSession_id());

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new LiveVideoFragment()).commit();

    }



    @OnClick({R.id.iv_back,R.id.tv_live,R.id.tv_upcoming,R.id.tv_completed})
    public void onViewClicked(View view) {
        Fragment selected=null;
        switch (view.getId()) {
            case R.id.tv_completed:
                selected=new CompletedVideo();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,selected).commit();
                tvLive.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.live_bg));
                tvLive.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));
                tvUpcoming.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.upcoming_bg));
                tvUpcoming.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));
                tvCompleted.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.completed_selected_bg));
                tvCompleted.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));

                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_live:
                selected=new LiveVideoFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,selected).commit();
                tvLive.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.live_selected_bg));
                tvLive.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                tvUpcoming.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.upcoming_bg));
                tvUpcoming.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));
                tvCompleted.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.completed_stroke_bg));
                tvCompleted.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));

                break;
            case R.id.tv_upcoming:
                selected=new UpcomingVideoFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,selected).commit();
                tvLive.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.live_bg));
                tvLive.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));
                tvUpcoming.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.upcomung_selected));
                tvUpcoming.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                tvCompleted.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.completed_stroke_bg));
                tvCompleted.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));

                break;
        }
    }
}