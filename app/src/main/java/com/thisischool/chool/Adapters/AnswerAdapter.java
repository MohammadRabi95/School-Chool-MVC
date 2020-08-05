package com.thisischool.chool.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thisischool.chool.Models.Answers;
import com.thisischool.chool.R;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerHolder> {

    private List<Answers> list;
    private Context context;

    public AnswerAdapter(List<Answers> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AnswerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AnswerHolder(LayoutInflater.from(context)
                .inflate(R.layout.answer_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerHolder holder, int position) {
        Answers answers = list.get(position);

        if (answers != null) {
            holder.ans.setText(answers.getAnswer());
            holder.name.setText("Answer by @" + answers.getName());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class AnswerHolder extends RecyclerView.ViewHolder {

        TextView ans,name;
        public AnswerHolder(@NonNull View itemView) {
            super(itemView);
            ans = itemView.findViewById(R.id.answer_answer_row);
            name = itemView.findViewById(R.id.name_answer_row);
        }
    }
}
