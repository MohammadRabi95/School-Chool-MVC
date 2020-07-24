package com.thisischool.chool.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thisischool.chool.Models.Question;

import java.util.List;

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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class QuestionHolder extends RecyclerView.ViewHolder {

        public QuestionHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
