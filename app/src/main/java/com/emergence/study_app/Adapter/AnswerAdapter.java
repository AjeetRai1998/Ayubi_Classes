package com.emergence.study_app.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emergence.study_app.newTech.retrofit.Image_Path;
import com.emergence.study_app.newTech.retrofit.model.responseAnswerList.DataItem;
import com.example.study_app.R;

import java.util.List;


public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AdapterViewHolder> {
    Context context;
    List<DataItem> models;

    public AnswerAdapter(Context context, List<DataItem> models) {
        this.context = context;
        this.models = models;
    }
    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.answer_list,parent,false);
        return new AdapterViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AnswerAdapter.AdapterViewHolder holder, int position) {
        int sum=0;
        int minus=0;
        int obtain=0;
        int total_marks=0;

        if (models.get(position).getImage().equalsIgnoreCase("")){
            holder.qust_imag.setVisibility(View.GONE);
        }else {
            holder.qust_imag.setVisibility(View.VISIBLE);
            Glide.with(context).load(Image_Path.Imagepath + "questions/" +models.get(position).getImage()).into(holder.qust_imag);
        }


        if (models.get(position).isAttempt()==true) {

            if (models.get(position).getStatus().equalsIgnoreCase("0")){
                holder.iv_ans1.setVisibility(View.GONE);
                holder.iv_ans2.setVisibility(View.GONE);
                holder.iv_ans3.setVisibility(View.GONE);
                holder.iv_ans4.setVisibility(View.GONE);
                holder.ans1.setVisibility(View.VISIBLE);
                holder.ans2.setVisibility(View.VISIBLE);
                holder.ans3.setVisibility(View.VISIBLE);
                holder.ans4.setVisibility(View.VISIBLE);



                holder.iv_check.setVisibility(View.VISIBLE);
                holder.tv_you_ans.setText(models.get(position).getGAns());
                if (models.get(position).getAnswer().equals(models.get(position).getGAns())) {
                    holder.iv_check.setImageResource(R.drawable.ic_check_mark);
                    if (models.get(position).getMarks().equalsIgnoreCase("")) {
                        sum = sum + 0;
                    } else {
                        //   sum = sum + Integer.parseInt(models.get(position).getMarks());
                    }

                } else {
                    holder.iv_check.setImageResource(R.drawable.ic_wrong_ans);
                    if (models.get(position).getMinusMarking().equalsIgnoreCase("")) {
                        minus = minus + 0;
                    } else {
                        //  minus = minus + Integer.parseInt(models.get(position).getMinusMarking());
                    }

                }
                if (models.get(position).getMarks().equalsIgnoreCase("")) {
                    total_marks = total_marks + 0;
                } else {
                    // total_marks = total_marks + Integer.parseInt(models.get(position).getMarks());
                }
                //  obtain=sum-minus;
                // CompletedAnswer.tv_marks_obtain.setText("Total Marks Obtain: "+obtain+"/"+total_marks);
                holder.tv_ans_desc.setText("Ans Description:- " + Html.fromHtml(models.get(position).getAnsdescription()));

                holder.tv_mark.setText(models.get(position).getMarks());
                holder.tv_minus_mark.setText(models.get(position).getMinusMarking());

                holder.tv_correct_ans.setText(models.get(position).getAnswer());
                holder.q_title.setText("Quest-" + (position + 1) + ". " + Html.fromHtml(models.get(position).getDescription()));
                String currentString = models.get(position).getOptions();
                String[] separated = currentString.split("GEARUP");
                holder.ans1.setText(separated[0].toString());
                holder.ans2.setText(separated[1].toString());
                holder.ans3.setText(separated[2].toString());
                holder.ans4.setText(separated[3].toString());
            }else {
                holder.iv_ans1.setVisibility(View.VISIBLE);
                holder.iv_ans2.setVisibility(View.VISIBLE);
                holder.iv_ans3.setVisibility(View.VISIBLE);
                holder.iv_ans4.setVisibility(View.VISIBLE);


                holder.iv_check.setVisibility(View.VISIBLE);
                holder.tv_you_ans.setText(models.get(position).getGAns());
                if (models.get(position).getAnswer().equals(models.get(position).getGAns())) {
                    holder.iv_check.setImageResource(R.drawable.ic_check_mark);
                    if (models.get(position).getMarks().equalsIgnoreCase("")) {
                        sum = sum + 0;
                    } else {
                        //   sum = sum + Integer.parseInt(models.get(position).getMarks());
                    }

                } else {
                    holder.iv_check.setImageResource(R.drawable.ic_wrong_ans);
                    if (models.get(position).getMinusMarking().equalsIgnoreCase("")) {
                        minus = minus + 0;
                    } else {
                        //  minus = minus + Integer.parseInt(models.get(position).getMinusMarking());
                    }

                }
                if (models.get(position).getMarks().equalsIgnoreCase("")) {
                    total_marks = total_marks + 0;
                } else {
                    // total_marks = total_marks + Integer.parseInt(models.get(position).getMarks());
                }
                //  obtain=sum-minus;
                // CompletedAnswer.tv_marks_obtain.setText("Total Marks Obtain: "+obtain+"/"+total_marks);
                holder.tv_ans_desc.setText("Ans Description:- " + Html.fromHtml(models.get(position).getAnsdescription()));

                holder.tv_mark.setText(models.get(position).getMarks());
                holder.tv_minus_mark.setText(models.get(position).getMinusMarking());

                holder.tv_correct_ans.setText(models.get(position).getAnswer());
                holder.q_title.setText("Quest-" + (position + 1) + ". " + Html.fromHtml(models.get(position).getDescription()));
                String currentString = models.get(position).getOptions();
                String[] separated = currentString.split("GEARUP");
                holder.ans1.setText("A: ");
                holder.ans2.setText("B: ");
                holder.ans3.setText("C: ");
                holder.ans4.setText("D: ");

                Glide.with(context).load(Image_Path.Imagepath + "questions/" +separated[0]).into(holder.iv_ans1);
                Glide.with(context).load(Image_Path.Imagepath + "questions/" +separated[1]).into(holder.iv_ans2);
                Glide.with(context).load(Image_Path.Imagepath + "questions/" +separated[2]).into(holder.iv_ans3);
                Glide.with(context).load(Image_Path.Imagepath  + "questions/" +separated[3]).into(holder.iv_ans4);



            }
        }else {


            if (models.get(position).getStatus().equalsIgnoreCase("0")){
                holder.iv_ans1.setVisibility(View.GONE);
                holder.iv_ans2.setVisibility(View.GONE);
                holder.iv_ans3.setVisibility(View.GONE);
                holder.iv_ans4.setVisibility(View.GONE);
                holder.iv_check.setVisibility(View.GONE);
                holder.tv_you_ans.setText("Not Attempted");
                holder.tv_ans_desc.setText("Ans Description:- " + Html.fromHtml(models.get(position).getAnsdescription()));
                holder.tv_mark.setText(models.get(position).getMarks());
                holder.tv_minus_mark.setText(models.get(position).getMinusMarking());
                holder.tv_correct_ans.setText(models.get(position).getAnswer());
                holder.q_title.setText("Quest-" + (position + 1) + ". " + Html.fromHtml(models.get(position).getDescription()));
                String currentString = models.get(position).getOptions();
                String[] separated = currentString.split("GEARUP");
                holder.ans1.setText(separated[0].toString());
                holder.ans2.setText(separated[1].toString());
                holder.ans3.setText(separated[2].toString());
                holder.ans4.setText(separated[3].toString());
            }else {
                holder.iv_ans1.setVisibility(View.VISIBLE);
                holder.iv_ans2.setVisibility(View.VISIBLE);
                holder.iv_ans3.setVisibility(View.VISIBLE);
                holder.iv_ans4.setVisibility(View.VISIBLE);
                String currentString = models.get(position).getOptions();
                String[] separated = currentString.split("GEARUP");
                holder.ans1.setText("A: ");
                holder.ans2.setText("B: ");
                holder.ans3.setText("C: ");
                holder.ans4.setText("D: ");

                Glide.with(context).load(Image_Path.Imagepath + "questions/" +separated[0]).into(holder.iv_ans1);
                Glide.with(context).load(Image_Path.Imagepath  + "questions/" +separated[1]).into(holder.iv_ans2);
                Glide.with(context).load(Image_Path.Imagepath  + "questions/" +separated[2]).into(holder.iv_ans3);
                Glide.with(context).load(Image_Path.Imagepath  + "questions/" +separated[3]).into(holder.iv_ans4);


            }



        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView q_title,tv_you_ans,tv_correct_ans,tv_ans_desc,tv_minus_mark,tv_mark;
        RadioButton ans1,ans2,ans3,ans4;
        ImageView iv_check,qust_imag;
        ImageView iv_ans4,iv_ans3,iv_ans2,iv_ans1;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            qust_imag=itemView.findViewById(R.id.qust_imag);
            iv_ans1=itemView.findViewById(R.id.iv_ans1);
            iv_ans2=itemView.findViewById(R.id.iv_ans2);
            iv_ans4=itemView.findViewById(R.id.iv_ans4);
            iv_ans3=itemView.findViewById(R.id.iv_ans3);
            tv_mark=itemView.findViewById(R.id.tv_mark);
            tv_minus_mark=itemView.findViewById(R.id.tv_minus_mark);
            tv_ans_desc=itemView.findViewById(R.id.tv_ans_desc);
            iv_check=itemView.findViewById(R.id.iv_check);
            tv_you_ans=itemView.findViewById(R.id.tv_you_ans);
            q_title=itemView.findViewById(R.id.q_title);
            ans4=itemView.findViewById(R.id.ans4);
            ans3=itemView.findViewById(R.id.ans3);
            ans2=itemView.findViewById(R.id.ans2);
            ans1=itemView.findViewById(R.id.ans1);
            tv_correct_ans=itemView.findViewById(R.id.tv_correct_ans);
        }
    }
}
