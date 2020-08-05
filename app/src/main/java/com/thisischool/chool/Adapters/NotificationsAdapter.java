package com.thisischool.chool.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thisischool.chool.Activities.ClassChatGroupActivity;
import com.thisischool.chool.Activities.FriendRequestActivity;
import com.thisischool.chool.Activities.InboxActivity;
import com.thisischool.chool.Models.Notification;
import com.thisischool.chool.R;

import java.util.List;

import static com.thisischool.chool.Classes.Constants.TO_CLASS_CHAT_GROUP;
import static com.thisischool.chool.Classes.Constants.TO_FRIEND_REQUEST;
import static com.thisischool.chool.Classes.Constants.TO_INBOX;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationHolder> {

    private List<Notification> list;
    private Context context;

    public NotificationsAdapter(List<Notification> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationHolder(LayoutInflater.from(context)
                .inflate(R.layout.notification_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {

        Notification notification = list.get(position);
        if (notification != null) {
            holder.title.setText(notification.getTitle());
            holder.body.setText(notification.getBody());

            holder.itemView.setOnClickListener(view -> {
                if (!"".equals(String.valueOf(notification.getmCase()))) {
                    Intent intent;
                    switch (notification.getmCase()) {
                        case TO_CLASS_CHAT_GROUP:
                            intent = new Intent(context, ClassChatGroupActivity.class);
                            break;
                        case TO_FRIEND_REQUEST:
                            intent = new Intent(context, FriendRequestActivity.class);
                            break;
                        case TO_INBOX:
                            intent = new Intent(context, InboxActivity.class);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + notification.getmCase());
                    }
                    context.startActivity(intent);
                } else {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class NotificationHolder extends RecyclerView.ViewHolder {

        TextView title,body;
        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_notifi);
            body = itemView.findViewById(R.id.body_notifi);
        }
    }
}
