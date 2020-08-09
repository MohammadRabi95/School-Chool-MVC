package com.thisischool.chool.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.thisischool.chool.Classes.Controller;
import com.thisischool.chool.Models.FriendRequest;
import com.thisischool.chool.Models.Info;
import com.thisischool.chool.Models.User;
import com.thisischool.chool.OnlineDatabase.MyReferences;
import com.thisischool.chool.OnlineDatabase.SendNotification;
import com.thisischool.chool.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoHolder> {

    private List<Info> list;
    private Context context;

    public InfoAdapter(List<Info> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public InfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InfoHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.info_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InfoHolder holder, int position) {
        Info info = list.get(position);
        if (info != null) {
            setData(info.getId(),holder.profileImg,holder.addFriendBtn,holder.name);

            Query query = MyReferences.sendFriendRef(info.getId())
                    .orderByChild("senderId").equalTo(Controller.CurrentUser.getUID());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        holder.addFriendBtn.setVisibility(View.GONE);
                    } else {
                        holder.addFriendBtn.setVisibility(View.VISIBLE);
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
        return list.size();
    }

    static class InfoHolder extends RecyclerView.ViewHolder {

        CircleImageView profileImg;
        TextView name;
        ImageView addFriendBtn;
        public InfoHolder(@NonNull View itemView) {
            super(itemView);
            profileImg = itemView.findViewById(R.id.profle_img_info);
            name = itemView.findViewById(R.id.nickname_info);
            addFriendBtn = itemView.findViewById(R.id.add_as_friend_info);
        }
    }

    private void setData(String id, CircleImageView img, ImageView add, TextView nick) {
        MyReferences.otherUserInfoRef(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        Picasso.get().load(user.getProfileImage()).noPlaceholder()
                                .centerCrop().fit().into(img);
                        nick.setText(user.getNickname());
                        add.setOnClickListener(view -> {
                            add.setVisibility(View.GONE);
                            DatabaseReference ref = MyReferences.sendFriendRef(id);
                            String pid = ref.push().getKey();
                            FriendRequest friendRequest = new FriendRequest(Controller.CurrentUser.getUID(),pid);
                            ref.child(pid).setValue(friendRequest)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            SendNotification.friendRequestNotification(context,
                                                    Controller.CurrentUser.getUserNickname(context),
                                                    user.getDeviceToken());
                                        }
                                    });
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
