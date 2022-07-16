package com.emergence.study_app.quiz;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.responseAnswerList.DataItem;
import com.emergence.study_app.newTech.retrofit.model.responseAnswerList.ResponseAnswerList;
import com.example.study_app.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizAnswer extends BaseActivity {
    public static String titl="";
    RecyclerView recyc_ans;
    TextView tv_title;
    ImageView iv_back;
    PreferencesManager preferencesManager;
    public static TextView tv_marks_obtain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_answer);
        preferencesManager=new PreferencesManager(QuizAnswer.this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        iv_back=findViewById(R.id.iv_back);
        recyc_ans=findViewById(R.id.recyc_ans);
        tv_title=findViewById(R.id.tv_title);
        tv_title.setText(titl);
        tv_marks_obtain=findViewById(R.id.tv_marks_obtain);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        AddExamRecycler(getIntent().getStringExtra("Sid"));
    }

    private void AddExamRecycler(String sid) {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseAnswerList> call=apiServices.QuizanswerList("GetCompleteTestsDetails",preferencesManager.getUserId(), sid);
        call.enqueue(new Callback<ResponseAnswerList>() {
            @Override
            public void onResponse(Call<ResponseAnswerList> call, Response<ResponseAnswerList> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    recyc_ans.setHasFixedSize(true);
                    recyc_ans.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyc_ans.setAdapter(new QuizAnswerAdapter(getApplicationContext(),response.body().getData()));
                    tv_marks_obtain.setVisibility(View.VISIBLE);
                    getData(response.body().getData());
                }else {
                    showMessage(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResponseAnswerList> call, Throwable t) {
                hideLoading();

            }
        });



    }

    private void getData(List<DataItem> data) {
        float sum=0;
        float minus=0;
        float obtain=0;
        float total_marks=0;

        for (int i=0;i<data.size();i++){
            if (data.get(i).isAttempt()==true) {
                if (data.get(i).getAnswer().equals(data.get(i).getGAns())) {
                    sum = sum + Float.parseFloat(data.get(i).getMarks());
                } else {
                    minus = minus + Float.parseFloat(data.get(i).getMinusMarking());
                }

                total_marks = total_marks + Float.parseFloat(data.get(i).getMarks());
            }else {
                total_marks = total_marks + Float.parseFloat(data.get(i).getMarks());
            }
        }
        obtain=sum-minus;
        tv_marks_obtain.setText("Total Marks Obtain: "+obtain+"/"+total_marks);


    }

}