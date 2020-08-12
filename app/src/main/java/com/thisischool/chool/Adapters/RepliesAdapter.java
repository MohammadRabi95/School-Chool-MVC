package com.thisischool.chool.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.thisischool.chool.Models.Replies;
import com.thisischool.chool.Models.User;
import com.thisischool.chool.OnlineDatabase.MyReferences;
import com.thisischool.chool.R;

import java.util.List;

public class RepliesAdapter extends RecyclerView.Adapter<RepliesAdapter.RepliesHolder> {

    private List<Replies> list;
    private Context context;

    public RepliesAdapter(List<Replies> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RepliesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RepliesHolder(LayoutInflater.from(context)
                .inflate(R.layout.replies_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RepliesHolder holder, int position) {

        Replies replies = list.get(position);

        if (replies != null) {
            holder.name.setText(replies.getName() + ": ");
            holder.content.setText(replies.getContent());
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class RepliesHolder extends RecyclerView.ViewHolder {

        TextView name,content;
        public RepliesHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_rep);
            content = itemView.findViewById(R.id.content_rep);

        }
    }
}
