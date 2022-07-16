package com.emergence.study_app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.study_app.R;

public class About_us_Page extends AppCompatActivity {
    ImageView menu,back,notification,profile;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about_us_page);
        menu=findViewById(R.id.menu_icon);
        profile=findViewById(R.id.usr_profile);
        back=findViewById(R.id.back_btn);
        notification=findViewById(R.id.img_notification);
        title=findViewById(R.id.main_titile);


        menu.setVisibility(View.GONE);
        notification.setVisibility(View.GONE);
        profile.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);

        title.setText("About Us");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}