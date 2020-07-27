package com.thisischool.chool.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thisischool.chool.Models.PrivateChatMessage;
import com.thisischool.chool.Models.PrivateMessages;
import com.thisischool.chool.Models.WorkBook;
import com.thisischool.chool.R;

import java.util.List;

public class PrivateChatAdapter extends RecyclerView.Adapter<PrivateChatAdapter.PrivateChatHolder> {

    private List<PrivateMessages> list;
    private Context context;

    public PrivateChatAdapter(List<PrivateMessages> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PrivateChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PrivateChatAdapter.PrivateChatHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.class_group_chat_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PrivateChatHolder holder, int position) {
        holder.textViewName.setText(list.get(position).getName());
        holder.textViewMessage.setText(list.get(position).getMessage());
        System.out.println("###############################################"+list.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class PrivateChatHolder extends RecyclerView.ViewHolder {
        TextView textViewName,textViewMessage;
        public PrivateChatHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage=itemView.findViewById(R.id.message_row);
            textViewName=itemView.findViewById(R.id.nickname_row);

        }
    }
}
