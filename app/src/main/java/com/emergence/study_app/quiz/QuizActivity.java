package com.emergence.study_app.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.responseAttemptPaper.ResponseAttemptPaper;
import com.emergence.study_app.newTech.retrofit.model.responseQuiz.ResponseQuiz;
import com.emergence.study_app.newTech.utils.Utils;
import com.emergence.study_app.testSeriese.CompletedAnswer;
import com.emergence.study_app.testSeriese.MVPClass;
import com.example.study_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends BaseActivity implements MVPClass {
    ImageView iv_back;
    TextView tv_quiz;
    String date="";
    String time="";
    RecyclerView recycler_quiz;
    PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        preferencesManager=new PreferencesManager(QuizActivity.this);
        iv_back=findViewById(R.id.iv_back);
        tv_quiz=findViewById(R.id.tv_quiz);
        recycler_quiz=findViewById(R.id.recycler_quiz);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getQuiz();

    }
    public void getQuiz(){
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseQuiz> call=apiServices.getQuiz("quiz",preferencesManager.getUserId());
        call.enqueue(new Callback<ResponseQuiz>() {
            @Override
            public void onResponse(Call<ResponseQuiz> call, Response<ResponseQuiz> response) {
                hideLoading();
                Utils.CheckUserSession(QuizActivity.this,preferencesManager.getUserId(),preferencesManager.getSession_id());
                if (response.body().getRes().equalsIgnoreCase("success")){
                  //  tv_quiz.setText("Be prepared your Quiz has been scheduled"+ "\nat " +response.body().getData().get(0).getDate()+", "+response.body().getData().get(0).getTime());
//                     date=response.body().getData().get(0).getDate();
//                     time=response.body().getData().get(0).getTime();
//                     tv_quiz.setText(Utils.getCurrenttime());
                    recycler_quiz.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recycler_quiz.setAdapter(new QuizListAdapter(getApplicationContext(),response.body().getData(),QuizActivity.this));
                }else {
                    tv_quiz.setVisibility(View.VISIBLE);
                    tv_quiz.setText("No Quiz Available");

                }
            }

            @Override
            public void onFailure(Call<ResponseQuiz> call, Throwable t) {

            }
        });
    }

    @Override
    public void getClassId(String id) {

    }

    @Override
    public void giveAnswer(String test_id, String title, String timing) {
        if (timing.equalsIgnoreCase("rank")){
            Intent intent=new Intent(context, QuizRanking.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("id",test_id);
            intent.putExtra("name",title);
            startActivity(intent);
        }else if (timing.equalsIgnoreCase("result")){
            CompletedAnswer.titl=title;
            Intent intent=new Intent(context, QuizAnswer.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("Sid",test_id);
            startActivity(intent);
        }else {
            StartTest(test_id,title,timing);
        }

    }
    private void StartTest(String test_id, String title, String timing){
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseAttemptPaper> call=apiServices.attemptQuiz("Attempts",preferencesManager.getUserId(),test_id, "");
        call.enqueue(new Callback<ResponseAttemptPaper>() {
            @Override
            public void onResponse(Call<ResponseAttemptPaper> call, Response<ResponseAttemptPaper> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    Intent intent=new Intent(getApplicationContext(), QuizQuestion.class);
                    intent.putExtra("series_Id",test_id);
                    intent.putExtra("title",title);
                    intent.putExtra("timing",timing);
                    startActivity(intent);
                    finish();
                    // goToActivityWithFinish(ExamRules.this,OnlineExam.class,null);
                }else {
                    showMessage(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResponseAttemptPaper> call, Throwable t) {
                hideLoading();

            }
        });
    }

}