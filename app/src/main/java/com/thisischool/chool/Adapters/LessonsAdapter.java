package com.thisischool.chool.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thisischool.chool.Models.Lesson;
import com.thisischool.chool.Models.WorkBook;

import java.util.List;

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.LessonHolder> {

    private List<Lesson> list;
    private Context context;

    public LessonsAdapter(List<Lesson> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public LessonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull LessonHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class LessonHolder extends RecyclerView.ViewHolder {

        public LessonHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
