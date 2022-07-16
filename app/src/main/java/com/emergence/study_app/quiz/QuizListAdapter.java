package com.emergence.study_app.quiz;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.newTech.retrofit.model.responseQuiz.DataItem;
import com.emergence.study_app.newTech.utils.Utils;
import com.emergence.study_app.testSeriese.MVPClass;
import com.example.study_app.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class QuizListAdapter extends RecyclerView.Adapter<QuizListAdapter.AdapterViewHolder> {
    Context context;
    List<DataItem> dataItems;
    MVPClass mvpClass;
    Date strDate;
    Date result;
    Date start_date;

    public QuizListAdapter(Context context, List<DataItem> dataItems,MVPClass mvpClass) {
        this.context = context;
        this.dataItems = dataItems;
        this.mvpClass=mvpClass;
    }

    @NonNull
    @Override
    public QuizListAdapter.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.quiz_list,parent,false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizListAdapter.AdapterViewHolder holder, int position) {

        holder.tv_title.setText(dataItems.get(position).getName());
        holder.tv_totl_quest.setText("No. of Quest: "+dataItems.get(position).getQuestions());
        holder.tv_timing.setText("Duration: "+dataItems.get(position).getTimming());
        holder.tv_text.setText(dataItems.get(position).getName().charAt(0)+"");

        String stardate=dataItems.get(position).getQuizOpen();
        String resultdate=dataItems.get(position).getResult();


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
             strDate = sdf.parse(dataItems.get(position).getResult());
            start_date = sdf.parse(dataItems.get(position).getQuizOpen());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            result = sdf1.parse(Utils.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }








       /* if (Utils.getDate().equalsIgnoreCase(dataItems.get(position).getQuizOpen())){
            if (dataItems.get(position).getStatusCheck().equalsIgnoreCase("true")){
                if (Utils.getDate().equalsIgnoreCase(dataItems.get(position).getResult())){
                    holder.tv_ranking.setVisibility(View.VISIBLE);
                    holder.tv_result.setVisibility(View.VISIBLE);
                    holder.tv_start_test.setVisibility(View.GONE);
                    holder.date_time.setText("Result: "+dataItems.get(position).getResult());
                }else {
                    holder.tv_ranking.setVisibility(View.GONE);
                    holder.tv_result.setVisibility(View.GONE);
                    holder.tv_start_test.setVisibility(View.GONE);
                    holder.date_time.setText("Result: "+dataItems.get(position).getResult());
                }

            }else {
                holder.tv_ranking.setVisibility(View.GONE);
                holder.tv_result.setVisibility(View.GONE);
                holder.tv_start_test.setVisibility(View.VISIBLE);
                holder.date_time.setText("Valid for: "+dataItems.get(position).getQuizOpen());
            }

        }else if (System.currentTimeMillis() > strDate.getTime()){
            holder.tv_ranking.setVisibility(View.GONE);
            holder.tv_result.setVisibility(View.GONE);
            holder.tv_start_test.setVisibility(View.GONE);
            holder.date_time.setText("You did not attempted this Quiz ");

        }else {
            holder.tv_ranking.setVisibility(View.GONE);
            holder.tv_result.setVisibility(View.GONE);
            holder.tv_start_test.setVisibility(View.GONE);
            holder.date_time.setText("Quiz Date: "+dataItems.get(position).getQuizOpen());
        }*/

        if (dataItems.get(position).getStatusCheck().equalsIgnoreCase("true")){

            if (strDate.after(result)) {
                // your_date_is_outdated = true;
                holder.tv_ranking.setVisibility(View.GONE);
                holder.tv_result.setVisibility(View.GONE);
                holder.tv_not_parti.setVisibility(View.GONE);
                holder.tv_upcoming.setVisibility(View.VISIBLE);
                holder.tv_upcoming.setText("Upcoming Result");
                holder.tv_start_test.setVisibility(View.GONE);
                holder.date_time.setText("Quiz Result Date: "+dataItems.get(position).getResult());

            }
            else if (result.after(strDate)){
                //  your_date_is_outdated = false;
                holder.tv_ranking.setVisibility(View.VISIBLE);
                holder.tv_result.setVisibility(View.VISIBLE);
                holder.tv_not_parti.setVisibility(View.GONE);
                holder.tv_upcoming.setVisibility(View.GONE);
                holder.tv_start_test.setVisibility(View.GONE);
                holder.date_time.setText("Quiz Result Date: "+dataItems.get(position).getResult());
            }else {
                holder.tv_ranking.setVisibility(View.VISIBLE);
                holder.tv_result.setVisibility(View.VISIBLE);
                holder.tv_not_parti.setVisibility(View.GONE);
                holder.tv_upcoming.setVisibility(View.GONE);
                holder.tv_start_test.setVisibility(View.GONE);
                holder.date_time.setText("Quiz Result Date: "+dataItems.get(position).getResult());
            }

        }else {

            if (start_date.after(result)){
                holder.tv_ranking.setVisibility(View.GONE);
                holder.tv_result.setVisibility(View.GONE);
                holder.tv_not_parti.setVisibility(View.GONE);
                holder.tv_upcoming.setVisibility(View.VISIBLE);
                holder.tv_upcoming.setText("Upcoming Quiz");
                holder.tv_start_test.setVisibility(View.GONE);
                holder.date_time.setText("Quiz Date: "+dataItems.get(position).getQuizOpen());

            }else if (start_date.before(result)){
                holder.tv_ranking.setVisibility(View.GONE);
                holder.tv_result.setVisibility(View.GONE);
                holder.tv_start_test.setVisibility(View.GONE);
                holder.tv_upcoming.setVisibility(View.GONE);
                holder.tv_not_parti.setVisibility(View.VISIBLE);
                holder.tv_start_test.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context,R.color.red_btn_bg_color)));
                holder.date_time.setText("Quiz Date: "+dataItems.get(position).getQuizOpen()+" You did not participated");
            }else {
                holder.tv_ranking.setVisibility(View.GONE);
                holder.tv_result.setVisibility(View.GONE);
                holder.tv_not_parti.setVisibility(View.GONE);
                holder.tv_upcoming.setVisibility(View.GONE);
                holder.tv_start_test.setVisibility(View.VISIBLE);
                holder.date_time.setText("Quiz Date: "+dataItems.get(position).getQuizOpen());
            }


        }

        holder.tv_start_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mvpClass.giveAnswer(dataItems.get(position).getId(),dataItems.get(position).getName(),dataItems.get(position).getTimming());
            }
        });

        holder.tv_ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mvpClass.giveAnswer(dataItems.get(position).getId(),dataItems.get(position).getName(),"rank");
            }
        });
        holder.tv_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mvpClass.giveAnswer(dataItems.get(position).getId(),dataItems.get(position).getName(),"result");
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tv_text,tv_title,tv_totl_quest,tv_timing,date_time,tv_upcoming,tv_start_test,tv_ranking,tv_result,tv_not_parti;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_upcoming=itemView.findViewById(R.id.tv_upcoming);
            tv_not_parti=itemView.findViewById(R.id.tv_not_parti);
            tv_result=itemView.findViewById(R.id.tv_result);
            tv_ranking=itemView.findViewById(R.id.tv_ranking);
            tv_start_test=itemView.findViewById(R.id.tv_start_test);
            tv_text=itemView.findViewById(R.id.tv_text);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_totl_quest=itemView.findViewById(R.id.tv_totl_quest);
            tv_timing=itemView.findViewById(R.id.tv_timing);
            date_time=itemView.findViewById(R.id.date_time);
        }
    }
}
