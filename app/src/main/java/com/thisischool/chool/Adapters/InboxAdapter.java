package com.thisischool.chool.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thisischool.chool.Models.Inbox;
import com.thisischool.chool.Models.WorkBook;

import java.util.List;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.InboxHolder> {

    private List<Inbox> list;
    private Context context;

    public InboxAdapter(List<Inbox> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public InboxHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull InboxHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class InboxHolder extends RecyclerView.ViewHolder {

        public InboxHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
