package com.thisischool.chool.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thisischool.chool.Models.PrivateChatMessage;
import com.thisischool.chool.Models.WorkBook;

import java.util.List;

public class PrivateChatAdapter extends RecyclerView.Adapter<PrivateChatAdapter.PrivateChatHolder> {

    private List<PrivateChatMessage> list;
    private Context context;

    public PrivateChatAdapter(List<PrivateChatMessage> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PrivateChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PrivateChatHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class PrivateChatHolder extends RecyclerView.ViewHolder {

        public PrivateChatHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
