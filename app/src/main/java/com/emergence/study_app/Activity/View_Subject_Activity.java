package com.emergence.study_app.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Adapter.Home_Recycler_Adapter;
import com.emergence.study_app.Adapter.Inner_Home_Recycler_Adapter;
import com.emergence.study_app.newTech.retrofit.model.Subject.DataItem;
import com.emergence.study_app.newTech.retrofit.model.Subject.VideoItem;
import com.emergence.study_app.newTech.utils.Utils;
import com.example.study_app.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class View_Subject_Activity extends AppCompatActivity {

    RecyclerView sub_recycler;
    ImageView menu_icon,back_icon;
    TextView title_tool;
    public static List<DataItem> data=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_subject);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        sub_recycler=findViewById(R.id.recycler_sub_cat);
        menu_icon=findViewById(R.id.menu_icon);
        back_icon=findViewById(R.id.back_btn);
        title_tool=findViewById(R.id.main_titile);
        menu_icon.setVisibility(View.GONE);
        back_icon.setVisibility(View.VISIBLE);



        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title_tool.setText(getIntent().getStringExtra("name"));

        Home_Recycler_Adapter inner_home_recycler_adapter=new Home_Recycler_Adapter(data,getApplicationContext());

        sub_recycler.setAdapter(inner_home_recycler_adapter);
        sub_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



/*
        for (int i=0;i<data.getPackages().size();i++){
            if (data.getPackages().get(i).getActiveStatus().equalsIgnoreCase("true")){
                if (data.getPackages().get(i).getExpDate().equalsIgnoreCase("")){
                    // dataItems.get(position).getPackages().add(dataItems.get(position).getPackages().get(i));
                   sub_recycler.setAdapter(new Inner_Home_Recycler_Adapter(data.getPackages(),getApplicationContext()));
                }else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date strDate = null;
                    Date strDate1=null;
                    try {
                        strDate = sdf.parse(data.getPackages().get(i).getExpDate());
                        strDate1 = sdf.parse(Utils.getDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (!strDate1.after(strDate)) {

//                      item=dataItems.get(position);
                        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),2);
                        sub_recycler.setLayoutManager(gridLayoutManager);
                        sub_recycler.setAdapter(new Inner_Home_Recycler_Adapter(data.getPackages(),getApplicationContext()));


                    }else {
                        // holder.inner_rec.setAdapter(new Inner_Home_Recycler_Adapter(dataItems.get(position).getPackages(),context));

                    }

                }





                // holder.inner_rec.setAdapter(new Inner_Home_Recycler_Adapter(dataItems.get(position).getPackages(),context));

            }
        }
*/



    }
}