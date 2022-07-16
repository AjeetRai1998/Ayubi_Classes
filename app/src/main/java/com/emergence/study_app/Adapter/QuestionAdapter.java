package com.emergence.study_app.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emergence.study_app.newTech.retrofit.Image_Path;
import com.emergence.study_app.newTech.retrofit.model.responseQuestion.DataItem;
import com.emergence.study_app.testSeriese.MVPClass;
import com.emergence.study_app.testSeriese.QuestionActivity;
import com.example.study_app.R;
import com.jsibbold.zoomage.ZoomageView;

import java.util.List;


public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    Context context;
    List<DataItem> models;
    MVPClass mvpClass;


    public QuestionAdapter(Context context, List<DataItem> models, MVPClass mvpClass) {
        this.context = context;
        this.models = models;
        this.mvpClass=mvpClass;
    }

    @NonNull
    @Override
    public QuestionAdapter.QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new QuestionViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.question_list,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.QuestionViewHolder holder, int position) {
        holder.ans_1.setChecked(false);
        holder.ans_2.setChecked(false);
        holder.ans_3.setChecked(false);
        holder.ans_4.setChecked(false);

        if (models.get(position).getImage().equalsIgnoreCase("")) {
            holder.relative.setVisibility(View.GONE);
        } else {
            holder.relative.setVisibility(View.VISIBLE);
            Glide.with(context).load(Image_Path.Imagepath+ "questions/" + models.get(position).getImage()).into(holder.image);
        }

        holder.qst_title.setText("Q." + (position + 1) + ":- " + Html.fromHtml(models.get(position).getDescription()));
        holder.desc.setText(Html.fromHtml(models.get(position).getAnsdescription()));
        holder.tv_marks.setText(models.get(position).getMarks() + " Marks");
        holder.tv_minus.setText("Negative Marks = "+"-"+models.get(position).getMinusMarking());

        if (models.get(position).getStatus().equalsIgnoreCase("0")) {
         //   holder.rad_group.setOrientation(LinearLayout.VERTICAL);
            holder.iv_ans1.setVisibility(View.GONE);
            holder.iv_ans2.setVisibility(View.GONE);
            holder.iv_ans3.setVisibility(View.GONE);
            holder.iv_ans4.setVisibility(View.GONE);



            String currentString = models.get(position).getOptions();
            String[] separated = currentString.split("GEARUP");
            holder.ans_1.setText(Html.fromHtml(separated[0].toString()));
            holder.ans_2.setText(Html.fromHtml(separated[1].toString()));
            holder.ans_3.setText(Html.fromHtml(separated[2].toString()));
            holder.ans_4.setText(Html.fromHtml(separated[3].toString()));



            if (holder.ans_1.getText().toString().equals(models.get(position).getGAns())) {
                holder.ans_1.setChecked(true);
            } else if (holder.ans_2.getText().toString().equals(models.get(position).getGAns())) {
                holder.ans_2.setChecked(true);
            } else if (holder.ans_3.getText().toString().equals(models.get(position).getGAns())) {
                holder.ans_3.setChecked(true);
            } else if (holder.ans_4.getText().toString().equals(models.get(position).getGAns())) {
                holder.ans_4.setChecked(true);
            } else {

            }

            holder.rad_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if (i == R.id.ans_1) {
                        QuestionActivity.ans = holder.ans_1.getText().toString();
                    } else if (i == R.id.ans_2) {
                        QuestionActivity.ans = holder.ans_2.getText().toString();

                    } else if (i == R.id.ans_3) {
                        QuestionActivity.ans = holder.ans_3.getText().toString();

                    } else if (i == R.id.ans_4) {
                        QuestionActivity.ans = holder.ans_4.getText().toString();
                    }
                }
            });
            holder.tv_skip_question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    QuestionActivity.last_Index = "";
                    QuestionActivity.ans = "notattempt";
                    mvpClass.giveAnswer(String.valueOf(position), models.get(position).getId(), "ans");
                }
            });




        }else {
          //  holder.rad_group.setOrientation(LinearLayout.HORIZONTAL);
            holder.iv_ans1.setVisibility(View.VISIBLE);
            holder.iv_ans2.setVisibility(View.VISIBLE);
            holder.iv_ans3.setVisibility(View.VISIBLE);
            holder.iv_ans4.setVisibility(View.VISIBLE);


            String currentString = models.get(position).getOptions();
            String[] separated = currentString.split("GEARUP");

            Glide.with(context).load(Image_Path.Imagepath + "questions/" +separated[0]).into(holder.iv_ans1);
            Glide.with(context).load(Image_Path.Imagepath+ "questions/" +separated[1]).into(holder.iv_ans2);
            Glide.with(context).load(Image_Path.Imagepath+ "questions/" +separated[2]).into(holder.iv_ans3);
            Glide.with(context).load(Image_Path.Imagepath+"questions/" +separated[3]).into(holder.iv_ans4);

            if (models.get(position).getGAns().equalsIgnoreCase("A")){
                holder.ans_1.setChecked(true);
            }else if (models.get(position).getGAns().equalsIgnoreCase("B")){
                holder.ans_2.setChecked(true);
            }else if (models.get(position).getGAns().equalsIgnoreCase("C")){
                holder.ans_3.setChecked(true);
            }else if (models.get(position).getGAns().equalsIgnoreCase("D")){
                holder.ans_4.setChecked(true);
            }


            holder.rad_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if (i == R.id.ans_1) {
                        QuestionActivity.ans = "A";
                    } else if (i == R.id.ans_2) {
                        QuestionActivity.ans = "B";

                    } else if (i == R.id.ans_3) {
                        QuestionActivity.ans = "C";

                    } else if (i == R.id.ans_4) {
                        QuestionActivity.ans = "D";
                    }
                }
            });
        }

        holder.tv_skip_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (models.get(position).getStatus().equalsIgnoreCase("0")){
                    QuestionActivity.Quest_status="0";
                    QuestionActivity.last_Index = "";
                    QuestionActivity.ans = "notattempt";
                    mvpClass.giveAnswer(String.valueOf(position), models.get(position).getId(), "ans");
                }else {
                    QuestionActivity.Quest_status="1";
                    QuestionActivity.last_Index = "";
                    QuestionActivity.ans = "notattempt";
                    mvpClass.giveAnswer(String.valueOf(position), models.get(position).getId(), "ans");
                }

            }
        });

        holder.btn_sbmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position==(models.size()-1)) {
                    if (models.get(position).getStatus().equalsIgnoreCase("0")) {
                        QuestionActivity.Quest_status = "0";
                        QuestionActivity.last_Index = "last";
                        mvpClass.giveAnswer(String.valueOf(position), models.get(position).getId(), "ans");

                    } else {
                        QuestionActivity.Quest_status = "1";
                        QuestionActivity.last_Index = "last";
                        mvpClass.giveAnswer(String.valueOf(position), models.get(position).getId(), "ans");

                    }
                }else {
                    if (models.get(position).getStatus().equalsIgnoreCase("0")) {
                        QuestionActivity.Quest_status = "0";
                        QuestionActivity.last_Index = "";
                        mvpClass.giveAnswer(String.valueOf(position), models.get(position).getId(), "ans");

                    } else {
                        QuestionActivity.Quest_status = "1";
                        QuestionActivity.last_Index = "";
                        mvpClass.giveAnswer(String.valueOf(position), models.get(position).getId(), "ans");

                    }
                }


            }
        });

        if (position==(models.size()-1)){
            holder.btn_sbmit.setVisibility(View.GONE);
        }else {
            holder.btn_sbmit.setVisibility(View.VISIBLE);
        }

        holder.btn_sb_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (models.get(position).getStatus().equalsIgnoreCase("0"))
                {
                    QuestionActivity.Quest_status="0";
                    QuestionActivity.last_Index = "last";
                    mvpClass.giveAnswer(String.valueOf(position), models.get(position).getId(), "ans");
                }else {
                    QuestionActivity.Quest_status="1";
                    QuestionActivity.last_Index = "last";
                    mvpClass.giveAnswer(String.valueOf(position), models.get(position).getId(), "ans");
                }


            }
        });










    }

    @Override
    public int getItemCount() {
        return models.size();
    }
    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView qst_title,tv_timer,desc,tv_marks,tv_minus,tv_skip_question;
        ZoomageView image;
        CheckBox check;
        RadioButton ans_1,ans_2,ans_3,ans_4;
        Button ntm_next,btn_sb_result,btn_sbmit;
        ZoomageView iv_ans1,iv_ans2,iv_ans3,iv_ans4;
        RelativeLayout relative;
        RadioGroup rad_group;
        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_sbmit=itemView.findViewById(R.id.btn_sbmit);
            iv_ans4=itemView.findViewById(R.id.iv_ans4);
            iv_ans3=itemView.findViewById(R.id.iv_ans3);
            iv_ans2=itemView.findViewById(R.id.iv_ans2);
            iv_ans1=itemView.findViewById(R.id.iv_ans1);
            tv_skip_question=itemView.findViewById(R.id.tv_skip_question);
            check=itemView.findViewById(R.id.check);
            tv_minus=itemView.findViewById(R.id.tv_minus);
            tv_marks=itemView.findViewById(R.id.tv_marks);
            btn_sb_result=itemView.findViewById(R.id.btn_sb_result);//
            rad_group=itemView.findViewById(R.id.rad_group);
            image=itemView.findViewById(R.id.image);
            relative=itemView.findViewById(R.id.relative);
            desc=itemView.findViewById(R.id.desc);
            tv_timer=itemView.findViewById(R.id.tv_timer);
            ans_1=itemView.findViewById(R.id.ans_1);
            ans_2=itemView.findViewById(R.id.ans_2);
            ans_3=itemView.findViewById(R.id.ans_3);
            ans_4=itemView.findViewById(R.id.ans_4);
            qst_title=itemView.findViewById(R.id.qst_title);

        }
    }

}
