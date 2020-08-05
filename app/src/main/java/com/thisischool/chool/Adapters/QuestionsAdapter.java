package com.thisischool.chool.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thisischool.chool.Activities.AnswersActivity;
import com.thisischool.chool.Models.Question;
import com.thisischool.chool.R;

import java.util.List;

import static com.thisischool.chool.Classes.Constants.QUESTION_ID;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionHolder> {

    private List<Question> list;
    private Context context;

    public QuestionsAdapter(List<Question> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuestionHolder(LayoutInflater.from(context)
        .inflate(R.layout.questions_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {

        Question question = list.get(position);
        if (question != null) {
            holder.nickname.setText("Posted by @"+question.getName());
            holder.question.setText(question.getQuestion());
            holder.ansCount.setText(question.getAnswersCount() + " Answers Available");

            holder.itemView.setOnClickListener(view -> {
                context.startActivity(new Intent(context, AnswersActivity.class)
                .putExtra(QUESTION_ID,question.getId()));
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class QuestionHolder extends RecyclerView.ViewHolder {

        TextView nickname,question,ansCount;
        public QuestionHolder(@NonNull View itemView) {
            super(itemView);
            nickname = itemView.findViewById(R.id.nickname_question_row);
            question = itemView.findViewById(R.id.question_question_row);
            ansCount = itemView.findViewById(R.id.ans_count_question_row);
        }
    }
}
