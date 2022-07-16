package com.emergence.study_app.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.emergence.study_app.Adapter.Tab_Adapter;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.example.study_app.R;
import com.google.android.material.tabs.TabLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class Notes_Video_Activity extends BaseActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView menu_icon,back_icon,notify_icon;
    TextView title_text;
    CircleImageView usr_profile;
    public static String lacture_name="",package_id="",lact="",chaptr_id="";
    public static int statusTrans=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_videp);
         tabLayout=(TabLayout)findViewById(R.id.tab_layout);
        viewPager=(ViewPager)findViewById(R.id.tab_viewPager);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        //  menu_icon=findViewById(R.id.menu_icon);
        //  usr_profile=findViewById(R.id.usr_profile);
        back_icon=findViewById(R.id.back_btn);
        // notify_icon=findViewById(R.id.img_notification);
        title_text=findViewById(R.id.main_titile);

//        menu_icon.setVisibility(View.GONE);
//        usr_profile.setVisibility(View.GONE);
        back_icon.setVisibility(View.VISIBLE);



        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        notify_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),Notification_Activity.class));
//            }
//        });
        title_text.setText(lacture_name);
        title_text.setSelected(true);
        tabLayout.addTab(tabLayout.newTab().setText("Video"));
        tabLayout.addTab(tabLayout.newTab().setText("Notes"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final Tab_Adapter adapter = new Tab_Adapter (getSupportFragmentManager(),this ,tabLayout.getTabCount());

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        if(statusTrans == 1) {
            viewPager.setCurrentItem(1);
        } else {
            viewPager.setCurrentItem(0);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}