package com.thisischool.chool.Adapters;

import android.content.Context;
import android.content.Intent;
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
import com.thisischool.chool.Activities.MyFriendsActivity;
import com.thisischool.chool.Activities.PrivateChatActivity;
import com.thisischool.chool.Models.Friends;
import com.thisischool.chool.Models.User;
import com.thisischool.chool.OnlineDatabase.MyReferences;
import com.thisischool.chool.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsHolder> {

    private List<Friends> friendsList;
    private Context context;

    public FriendsAdapter(List<Friends> friendsList, Context context) {
        this.friendsList = friendsList;
        this.context = context;
    }

    @NonNull
    @Override
    public FriendsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendsHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.friends_row, parent, false)); // row design need to be change
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsHolder holder, int position) {

        Friends friends = friendsList.get(position);

        if (friends != null) {
            MyReferences.otherUserInfoRef(friends.getFriendId())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        User user = snapshot.getValue(User.class);
                        if (user != null) {
                            holder.name.setText(user.getNickname());
                            holder.status.setText(user.getStatus());
                            Picasso.get().load(user.getProfileImage()).noPlaceholder()
                                    .fit().centerCrop().into(holder.profileImg);

                            holder.sendMsg.setOnClickListener(view -> {
                                Intent intent=new Intent(context, PrivateChatActivity.class);
                                intent.putExtra("Receiver",user.getId());
                                context.startActivity(intent);
                            });

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }

    static class FriendsHolder extends RecyclerView.ViewHolder {

        TextView name, status;
        CircleImageView profileImg;
        ImageView sendMsg;
        public FriendsHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nickname_friends);
            status = itemView.findViewById(R.id.status_friends);
            profileImg = itemView.findViewById(R.id.profle_img_friends);
            sendMsg = itemView.findViewById(R.id.send_msg_friend);


        }
    }
}
