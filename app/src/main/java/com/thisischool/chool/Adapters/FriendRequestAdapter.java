package com.thisischool.chool.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.thisischool.chool.Models.FriendRequest;
import com.thisischool.chool.Models.Friends;
import com.thisischool.chool.Models.User;
import com.thisischool.chool.OnlineDatabase.MyReferences;
import com.thisischool.chool.OnlineDatabase.SendNotification;
import com.thisischool.chool.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.thisischool.chool.Classes.Constants.NO_IMAGE;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.FriendRequestHolder> {

    private List<FriendRequest> list;
    private Context context;
    private User user;

    public FriendRequestAdapter(List<FriendRequest> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public FriendRequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendRequestHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.friend_request_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendRequestHolder holder, int position) {
        user = null;
        FriendRequest friendRequest = list.get(position);
        if (friendRequest != null) {
            MyReferences.otherUserInfoRef(friendRequest.getSenderId())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                user = snapshot.getValue(User.class);
                                if (user != null) {
                                    holder.nickname.setText(user.getNickname());
                                    if (!NO_IMAGE.equals(user.getProfileImage())) {
                                        Picasso.get().load(user.getProfileImage()).noPlaceholder()
                                                .fit().centerCrop().into(holder.profileImage);
                                    }
                                    holder.accept.setOnClickListener(view -> {
                                        acceptRequest(user,position,friendRequest.getReqId());
                                    });
                                    holder.reject.setOnClickListener(view -> {
                                        rejectRequest(friendRequest.getReqId(), position);
                                    });
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    }
                    );

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class FriendRequestHolder extends RecyclerView.ViewHolder {
        TextView nickname;
        ImageView profileImage;
        Button accept,reject;
        public FriendRequestHolder(@NonNull View itemView) {
            super(itemView);
            nickname = itemView.findViewById(R.id.nickname_firendreq_row);
            profileImage = itemView.findViewById(R.id.profile_img_friendreq_row);
            accept = itemView.findViewById(R.id.accept_friendreq_row);
            reject = itemView.findViewById(R.id.reject_friendreq_row);
        }
    }

    private void acceptRequest(@NotNull User user, int position, String id) {
        Friends friends = new Friends(user.getId());
        MyReferences.friendsRef().child(user.getId())
                .setValue(friends).addOnCompleteListener(task -> {
                    MyReferences.friendsRequestRef().child(id)
                            .removeValue().addOnCompleteListener(task1 -> {
                       notifyItemRemoved(position);
                        SendNotification.friendRequestAcceptedNotification(context,
                                user.getNickname(),user.getDeviceToken());
                    });
                });
    }

    private void rejectRequest(String id, int position) {
            MyReferences.friendsRequestRef().child(id).removeValue()
            .addOnCompleteListener(task -> {
                    notifyItemRemoved(position);
    });
    }
}
