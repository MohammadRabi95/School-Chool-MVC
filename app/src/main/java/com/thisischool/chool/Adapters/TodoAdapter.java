package com.thisischool.chool.Adapters;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.thisischool.chool.Activities.AddTodoActivity;
import com.thisischool.chool.Classes.Constants;
import com.thisischool.chool.Models.TODO;
import com.thisischool.chool.R;

import java.util.List;

import static com.thisischool.chool.Classes.Constants.TODO_DATA;
import static com.thisischool.chool.Classes.Constants.TODO_KEY;
import static com.thisischool.chool.Classes.Constants.UPDATE_WORKBOOK;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> {

    private List<TODO> list;
    private Context context;

    public TodoAdapter(List<TODO> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TodoHolder(LayoutInflater.from(context)
                .inflate(R.layout.replies_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        TODO todo = list.get(position);
        if (todo != null) {
            holder.stamp.setText(todo.getStamp());
            holder.title.setText(todo.getTitle());

            if (todo.isFavorite()) {
                DrawableCompat.setTint(
                        DrawableCompat.wrap(holder.fav.getDrawable()),
                        ContextCompat.getColor(context, R.color.colorCoolBlue)
                );
            } else {
                DrawableCompat.setTint(
                        DrawableCompat.wrap(holder.fav.getDrawable()),
                        ContextCompat.getColor(context, R.color.colorThinBlue)
                );
            }

            holder.itemView.setOnClickListener(view -> {
                context.startActivity(new Intent(context, AddTodoActivity.class)
                .putExtra(TODO_KEY,UPDATE_WORKBOOK)
                .putExtra(TODO_DATA,todo));
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class TodoHolder extends RecyclerView.ViewHolder {

        TextView stamp,title;
        ImageView fav;
        public TodoHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
