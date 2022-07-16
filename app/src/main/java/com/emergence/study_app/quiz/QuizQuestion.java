package com.emergence.study_app.quiz;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.emergence.study_app.Adapter.JumpAdapter;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.responseAnswer.ResponseAnswer;
import com.emergence.study_app.newTech.retrofit.model.responseFinishTestSeriese.ResponseFinishTestSeriese;
import com.emergence.study_app.newTech.retrofit.model.responseJumpQuestion.ResponseJumpQuestion;
import com.emergence.study_app.newTech.retrofit.model.responseQuestion.ResponseQuestion;
import com.emergence.study_app.testSeriese.MVPClass;
import com.example.study_app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizQuestion extends BaseActivity implements MVPClass {
    TextView tv_timer;
    ViewPager2 question_viewpager;
    TextView tv_title;
    public static boolean check=false;
    public static String test_Id="";
    PreferencesManager preferencesManager;
    public static String ans = "";
    public static String last_Index="";
    FloatingActionButton btn_answer_sheet;
    RecyclerView recycler_answer;
    public static PopupWindow popupWindow;
    public static String hour,mint,second;
    android.app.AlertDialog alert2;
    public static String Quest_status="";
    String duration="";
    public static boolean isCompleted=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_quiz_question);
        preferencesManager=new PreferencesManager(QuizQuestion.this);
         tv_timer=findViewById(R.id.tv_timer);
        tv_title=findViewById(R.id.tv_title);
        question_viewpager=findViewById(R.id.question_viewpager);
        test_Id=getIntent().getStringExtra("series_Id");
        duration=getIntent().getStringExtra("timing");
        tv_title.setText(getIntent().getStringExtra("title"));
        AddQuestion();
        CountreaderOTp();
        btn_answer_sheet=findViewById(R.id.btn_answer_sheet);
        btn_answer_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnswerList(view);
            }
        });
    }
    private void AnswerList(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popup = inflater.inflate(R.layout.answerbook_list_popup, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        Button btn_back_to_exam=popup.findViewById(R.id.btn_back_to_exam);
        recycler_answer=popup.findViewById(R.id.recycler_answer);


        boolean focusable = true;
        popupWindow = new PopupWindow(popup, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        btn_back_to_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                // startActivity(new Intent(getApplicationContext(),ExamTest.class));
            }
        });
        View container = popupWindow.getContentView().getRootView();
        if (container != null) {
            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
            p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            p.dimAmount = 0.5f;
            if (wm != null) {
                wm.updateViewLayout(container, p);
            }
        }

        AddRecyclerJump();

    }

    private void AddRecyclerJump() {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseJumpQuestion> call=apiServices.JumptoQuestionQuiz("GetQuestionsList",preferencesManager.getUserId(),test_Id);
        call.enqueue(new Callback<ResponseJumpQuestion>() {
            @Override
            public void onResponse(Call<ResponseJumpQuestion> call, Response<ResponseJumpQuestion> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    recycler_answer.setHasFixedSize(true);
                    recycler_answer.setNestedScrollingEnabled(false);
                    recycler_answer.setLayoutManager(new GridLayoutManager(getApplicationContext(),5));
                    recycler_answer.setAdapter(new JumpAdapter(getApplicationContext(),response.body().getData(),QuizQuestion.this));
                }else {
                    showMessage(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResponseJumpQuestion> call, Throwable t) {
                hideLoading();
            }
        });
    }
    private void CountreaderOTp() {
        String  currentString = duration+":00";
        String[] separated = currentString.split(":");

        hour=separated[0].toString();
        mint=separated[1].toString();
        second=separated[2].toString();

        long hr=Long.parseLong(hour);
        long mt=Long.parseLong(mint);
        long sc=Long.parseLong(second);
        long total=(hr*60)+mt+(sc/60);

        long millisInFuture = total*1000*60;
        long countDownInterval = 1000;
        new CountDownTimer(millisInFuture, countDownInterval) {
            public void onTick(long millisUntilFinished) {
                //  tv_timer.setText("Verifying your OTP in - " + "00:" + millisUntilFinished / 1000 + " " + "Sec");
                tv_timer.setText(""+String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }
            public void onFinish() {
                if (isCompleted==false){
                    ShowDailoge(QuizQuestion.this);
                }

            }
        }.start();
    }
    public void ShowDailoge(Context ctx) {
        LayoutInflater layoutInflater = LayoutInflater.from(QuizQuestion.this);
        View promptView;
        promptView = layoutInflater.inflate(R.layout.exit_layout, null);
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(ctx);
        alertDialogBuilder.setView(promptView);
        alert2 = alertDialogBuilder.create();
        alert2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert2.setCancelable(false);
        Button ok=promptView.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                last_Index="finish";
                fiishTest();
                alert2.dismiss();
            }
        });
        Button result=promptView.findViewById(R.id.result);
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                last_Index="last";
                fiishTest();
                alert2.dismiss();
            }
        });

        alert2.show();
    }





    @Override
    public void onBackPressed() {
        if (isCompleted==false){
            ExitExam();
        }

        // super.onBackPressed();
    }
    private void ExitExam() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(QuizQuestion.this);

        alertDialog.setTitle("Exit Exam"); // Sets title for your alertbox

        alertDialog.setMessage("Are you sure you wanted to Exit From Exam ?\n Your attempted questions will be saved."); // Message to be displayed on alertbox

        alertDialog.setIcon(R.mipmap.ic_launcher); // Icon for your alertbox

        /* When positive (yes/ok) is clicked */
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                last_Index="finish";
                fiishTest();
                //goToActivityWithFinish(OnlineExam.this, OnlineTest.class,null);
            }
        });

        /* When negative (No/cancel) button is clicked*/
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
    private void AddQuestion(){
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseQuestion> call=apiServices.getQuizQuestion("GetQuestions",test_Id,preferencesManager.getUserId());
        call.enqueue(new Callback<ResponseQuestion>() {
            @Override
            public void onResponse(Call<ResponseQuestion> call, Response<ResponseQuestion> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    question_viewpager.setUserInputEnabled(false);
                    question_viewpager.setAdapter(new QuizAdapter(getApplicationContext(),response.body().getData(),QuizQuestion.this));
                }else {
                    showMessage(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResponseQuestion> call, Throwable t) {
                hideLoading();

            }
        });
    }

    @Override
    public void getClassId(String id) {
        question_viewpager.setCurrentItem(Integer.parseInt(id)+1);
        // question_viewpager.setUserInputEnabled(true);
       /* if (check==true){
            question_viewpager.setCurrentItem(Integer.parseInt(id)+1);
        }else {
            question_viewpager.setCurrentItem(Integer.parseInt(id)-1);
        }*/

    }

    @Override
    public void giveAnswer(String pos, String ques_id, String answer) {
        getanswer(pos,ques_id,answer);
    }

    private void getanswer(String pos, String ques_id, String answer) {
        showLoading();
        ApiServices apiServices=API_Config.getApiClient_ByPost();
        Call<ResponseAnswer> call=apiServices.getQuizAnswer("Answer",preferencesManager.getUserId(),test_Id,ques_id,ans,Quest_status);
        call.enqueue(new Callback<ResponseAnswer>() {
            @Override
            public void onResponse(Call<ResponseAnswer> call, Response<ResponseAnswer> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    ans="";
                    Quest_status="";
                    if (last_Index.equalsIgnoreCase("last")){
                        fiishTest();
                    }else if (last_Index.equalsIgnoreCase("finish")){
                        fiishTest();
//                      finish();
                    }else {
                        ans="";
                        question_viewpager.setCurrentItem(Integer.parseInt(pos)+1);
                    }

                }else {
                    showMessage("Something went wrong !");
                }
            }

            @Override
            public void onFailure(Call<ResponseAnswer> call, Throwable t) {
                hideLoading();
            }
        });
    }
    public void fiishTest(){
        showLoading();
        ApiServices apiServices=API_Config.getApiClient_ByPost();
        Call<ResponseFinishTestSeriese> call=apiServices.finishQuizTestSeriese("FinishTest",preferencesManager.getUserId(),test_Id);
        call.enqueue(new Callback<ResponseFinishTestSeriese>() {
            @Override
            public void onResponse(Call<ResponseFinishTestSeriese> call, Response<ResponseFinishTestSeriese> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    isCompleted=true;
                    if (last_Index.equalsIgnoreCase("last")){
                       /* CompletedAnswer.titl="Result";
                        Intent intent=new Intent(getApplicationContext(),QuizAnswer.class);
                        intent.putExtra("Sid",test_Id);
                        startActivity(intent);*/
                        finish();
                        showMessage("Exam Submitted successfully");
                    }else if (last_Index.equalsIgnoreCase("finish")){
                        finish();
                        showMessage("Exam Submitted successfully");
                    }
                }else {

                }
            }

            @Override
            public void onFailure(Call<ResponseFinishTestSeriese> call, Throwable t) {
                hideLoading();

            }
        });
    }
}