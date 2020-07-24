package com.thisischool.chool.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thisischool.chool.Activities.AddWorkBookActivity;
import com.thisischool.chool.Classes.Constants;
import com.thisischool.chool.Models.WorkBook;
import com.thisischool.chool.R;

import java.util.List;

import static com.thisischool.chool.Classes.Constants.UPDATE_WORKBOOK;
import static com.thisischool.chool.Classes.Constants.WORKBOOK_DATA;
import static com.thisischool.chool.Classes.Constants.WORKBOOK_KEY;

public class WorkBookAdapter extends RecyclerView.Adapter<WorkBookAdapter.WorkBookHolder> {

    private List<WorkBook> list;
    private Context context;
    private static final int max_length = 150;

    public WorkBookAdapter(List<WorkBook> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public WorkBookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorkBookHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.workbook_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WorkBookHolder holder, int position) {
        WorkBook workBook = list.get(position);
        if (workBook != null) {
            holder.title.setText(workBook.getTitle());

            if (workBook.getContent().length() > max_length) {
                holder.content.setText(workBook.getContent().substring(0, max_length) + "...");
            } else {
                holder.content.setText(workBook.getContent());
            }

            holder.itemView.setOnClickListener(view -> {
                context.startActivity(new Intent(context, AddWorkBookActivity.class)
                .putExtra(WORKBOOK_KEY,UPDATE_WORKBOOK)
                .putExtra(WORKBOOK_DATA,workBook));
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class WorkBookHolder extends RecyclerView.ViewHolder {
        TextView title,content;
        public WorkBookHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_workbook_row);
            content = itemView.findViewById(R.id.content_workbook_row);
        }
    }
}
