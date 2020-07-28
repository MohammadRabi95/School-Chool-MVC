package com.thisischool.chool.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.thisischool.chool.Activities.PrivateChatActivity;
import com.thisischool.chool.BuildConfig;
import com.thisischool.chool.Classes.AppHelper;
import com.thisischool.chool.Classes.Controller;
import com.thisischool.chool.Models.FriendRequest;
import com.thisischool.chool.OnlineDatabase.MyReferences;
import com.thisischool.chool.OnlineDatabase.SendNotification;
import com.thisischool.chool.Models.ClassChatGroupMessage;
import com.thisischool.chool.Models.User;
import com.thisischool.chool.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;

import static com.thisischool.chool.Classes.Constants.NO_IMAGE;

public class ClassChatGroupAdapter extends RecyclerView.Adapter<ClassChatGroupAdapter.ClassChatGroupHolder> {

    private static final String TAG = "ClassChatGroupAdapter";
    private static final String LIKE = "like";
    private static final String LIKED = "liked";
    private List<ClassChatGroupMessage> messageList;
    private Context context;
    private User user;

    public ClassChatGroupAdapter(List<ClassChatGroupMessage> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
    }

    @NonNull
    @Override
    public ClassChatGroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClassChatGroupHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.class_group_chat_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClassChatGroupHolder holder, int position) {
        ClassChatGroupMessage message = messageList.get(position);

        if (message != null) {
            holder.nickname.setText(message.getNickname());
            holder.message.setText(message.getMessage());
            holder.count.setText(String.valueOf(message.getMessageLikes()));

            if (!message.getPostImageUrl().equals(NO_IMAGE)) {
                holder.image.setVisibility(View.VISIBLE);
                holder.image.setOnClickListener(v -> {
                    showMessageImage(message.getPostImageUrl());
                });
            }

            holder.itemView.setOnClickListener(view -> {
                showUserProfile(message.getSenderId());
            });

            holder.like.setOnClickListener(view -> {
                likeOrDislikeMessage(message.getSenderId(), message.getMessageId(),
                        holder.like, holder.count);
            });

            if (message.getSenderId().equals(Controller.CurrentUser.getUID())) {
                holder.line.setVisibility(View.VISIBLE);
        //        holder.delete.setVisibility(View.VISIBLE);
            /*    holder.delete.setOnClickListener(view -> {

                });*/
            }

            numberOfLikes(message.getMessageId(), holder.count);
            isLiked(message.getMessageId(), holder.like);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    private void showMessageImage(String url) {
        Dialog dialog = new Dialog(context);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.message_img_dialog);
        GifImageView imageView = dialog.findViewById(R.id.msg_img_dialog);
        Picasso.get().load(url).noPlaceholder().fit().centerCrop().into(imageView);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void showUserProfile(String userId) {
        DatabaseReference reference = MyReferences.otherUserInfoRef(userId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User mUser = snapshot.getValue(User.class);
                    if (mUser != null) {
                        TextView likeCount, status;
                        ImageButton sendMessage, sendFriendRequest;

                        Dialog dialog = new Dialog(context);
                        dialog.setCancelable(true);
                        dialog.setContentView(R.layout.profile_dialog);
                        Objects.requireNonNull(dialog.getWindow())
                                .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        ImageView imageView = dialog.findViewById(R.id.profile_img_dialog);
                        likeCount = dialog.findViewById(R.id.total_likes_dialog);
                        status = dialog.findViewById(R.id.status_dialog);
                        sendFriendRequest = dialog.findViewById(R.id.add_as_friend);
                        sendMessage = dialog.findViewById(R.id.send_msg_dialog);

                        if (userId.equals(Controller.CurrentUser.getUID())) {
                            sendFriendRequest.setVisibility(View.GONE);
                            sendMessage.setVisibility(View.GONE);
                        }

                        if (!NO_IMAGE.equals(mUser.getProfileImage())) {
                            Picasso.get().load(mUser.getProfileImage()).noPlaceholder().fit().centerCrop().into(imageView);
                        }
                        likeCount.setText(mUser.getTotalLikes() + "");
                        status.setText(mUser.getStatus());

                        sendFriendRequest.setOnClickListener(view -> {
                            sendFriendRequest.setVisibility(View.GONE);
                            DatabaseReference ref = MyReferences.sendFriendRef(userId);
                            String pid = ref.push().getKey();
                            FriendRequest friendRequest = new FriendRequest(Controller.CurrentUser.getUID(),pid);
                            ref.child(pid).setValue(friendRequest)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    SendNotification.friendRequestNotification(context,
                                            Controller.CurrentUser.getUserNickname(context),
                                            mUser.getDeviceToken());
                                }
                            });
                        });
                        sendMessage.setOnClickListener(view -> {
                            // Send Private message to Class mate...
                            Intent intent=new Intent(context, PrivateChatActivity.class);
                            intent.putExtra("Receiver",mUser.getId());
                            context.startActivity(intent);
                        });

                        Query query = MyReferences.sendFriendRef(userId)
                                .orderByChild("senderId").equalTo(Controller.CurrentUser.getUID());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    sendFriendRequest.setVisibility(View.GONE);
                                }
                                dialog.show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                AppHelper.showToast(context, error.getMessage());
            }
        });
    }

    private void likeOrDislikeMessage(String senderId, String messageId,
                                      @NotNull ImageView like, TextView view) {
        if (like.getTag().equals(LIKE)) {
            int count = Integer.parseInt(view.getText().toString()) + 1;
            view.setText(count + "");
            DrawableCompat.setTint(
                    DrawableCompat.wrap(like.getDrawable()),
                    ContextCompat.getColor(context, R.color.colorCoolBlue)
            );
            MyReferences.likedMessageRef(context, messageId)
                    .child(Controller.CurrentUser.getUID()).setValue(LIKED);
            MyReferences.otherUserInfoRef(senderId)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                User user = snapshot.getValue(User.class);
                                assert user != null;
                                int likes = user.getTotalLikes() + 1;
                                User mUser = new User(user.getMobileNum(), user.getNickname(),
                                        user.getClassId(), user.getDeviceToken(), user.getProfileImage(),
                                        user.getStatus(), likes, senderId);
                                MyReferences.otherUserInfoRef(senderId).setValue(mUser)
                                        .addOnCompleteListener(task -> {
                                            if (task.isSuccessful()) {
                                                numberOfLikes(messageId, view);
                                                // change color of like etc etc
                                            }
                                        }).addOnFailureListener(e -> {

                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        } else {
            int count = Integer.parseInt(view.getText().toString()) - 1;
            view.setText(count + "");
            DrawableCompat.setTint(
                    DrawableCompat.wrap(like.getDrawable()),
                    ContextCompat.getColor(context, R.color.colorThinBlue)
            );
            MyReferences.likedMessageRef(context, messageId).child(senderId).removeValue()
                    .addOnCompleteListener(task -> {
                        MyReferences.otherUserInfoRef(senderId)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            User user = snapshot.getValue(User.class);
                                            assert user != null;
                                            int likes = user.getTotalLikes() - 1;
                                            User mUser = new User(user.getMobileNum(), user.getNickname(),
                                                    user.getClassId(), user.getDeviceToken(), user.getProfileImage(),
                                                    user.getStatus(), likes, senderId);
                                            MyReferences.otherUserInfoRef(senderId).setValue(mUser)
                                                    .addOnCompleteListener(task -> {
                                                        if (task.isSuccessful()) {
                                                            numberOfLikes(messageId, view);
                                                            // change color of like etc etc
                                                        }
                                                    }).addOnFailureListener(e -> {

                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                    });

        }
    }

    private void numberOfLikes(String messageId, TextView view) {
        MyReferences.likedMessageRef(context, messageId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            view.setText(snapshot.getChildrenCount() + "");
                        } else {
                            view.setText("0");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
        if ("".equals(MyReferences.likedMessageRef(context, messageId).getKey())) {
            view.setText("0");
        }
    }

    private void isLiked(String messageId, final ImageView imageView) {
        MyReferences.likedMessageRef(context, messageId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(Controller.CurrentUser.getUID()).exists()) {
                            // imageView.setImageResource(R.drawable.ic_liked);
                            DrawableCompat.setTint(
                                    DrawableCompat.wrap(imageView.getDrawable()),
                                    ContextCompat.getColor(context, R.color.colorCoolBlue)
                            );
                            imageView.setTag(LIKED);
                        } else {
                            // imageView.setImageResource(R.drawable.ic_like2);
                            imageView.setTag(LIKE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }

    static class ClassChatGroupHolder extends RecyclerView.ViewHolder {

        TextView nickname, message, count, line;
        ImageView image, like, delete;

        public ClassChatGroupHolder(@NonNull View itemView) {
            super(itemView);
            nickname = itemView.findViewById(R.id.nickname_row);
            message = itemView.findViewById(R.id.message_row);
            count = itemView.findViewById(R.id.likeCounter_row);
            image = itemView.findViewById(R.id.message_img_row);
            like = itemView.findViewById(R.id.messageLike_row);
           // delete = itemView.findViewById(R.id.message_del_row);
            line = itemView.findViewById(R.id.line_row);
        }
    }

}
