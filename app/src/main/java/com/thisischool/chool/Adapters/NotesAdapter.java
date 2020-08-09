package com.thisischool.chool.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thisischool.chool.Models.Notes;
import com.thisischool.chool.R;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesHolder> {

    private List<Notes> list;
    private Context context;

    public NotesAdapter(List<Notes> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public NotesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.notes_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesHolder holder, int position) {
        Notes notes = list.get(position);
        if (notes != null) {
            holder.name.setText("Note by @" + notes.getName());
            holder.content.setText(notes.getContent());
            holder.title.setText(notes.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class NotesHolder extends RecyclerView.ViewHolder {

        TextView title,content,name;
        public NotesHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_notes);
            content = itemView.findViewById(R.id.content_notes);
            name = itemView.findViewById(R.id.name_notes);
        }
    }
}
