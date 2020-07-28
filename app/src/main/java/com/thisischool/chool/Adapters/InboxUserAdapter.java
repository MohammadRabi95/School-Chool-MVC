package com.thisischool.chool.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thisischool.chool.Activities.PrivateChatActivity;
import com.thisischool.chool.Classes.Controller;
import com.thisischool.chool.Models.InboxMyUser;
import com.thisischool.chool.Models.PrivateMessages;
import com.thisischool.chool.Models.User;
import com.thisischool.chool.R;

import java.util.List;

public class InboxUserAdapter extends RecyclerView.Adapter<InboxUserAdapter.InboxUserHolder>  {
    Context context;
    List<User> list;
String lastMessage;
    public InboxUserAdapter(Context context, List<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InboxUserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InboxUserHolder(LayoutInflater.from(context).inflate(R.layout.user_design,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull InboxUserHolder holder, int position) {
holder.user.setText(list.get(position).getNickname());
checkLastMessage(list.get(position).getId(),holder.message);
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(context, PrivateChatActivity.class);
        intent.putExtra("Receiver",list.get(position).getId());
        context.startActivity(intent);
    }
});
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class InboxUserHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView user,message;
        public InboxUserHolder(@NonNull View itemView) {
            super(itemView);
            profile=itemView.findViewById(R.id.ImageView_User_UserDesignId);
            user=itemView.findViewById(R.id.TextView_UserName_UserDesignId);
            message=itemView.findViewById(R.id.TextView_message_UserDesignId);
        }
    }
   void checkLastMessage(String userId,TextView lastMsg){
       String UID=Controller.CurrentUser.getUID();
       lastMessage="default";
       DatabaseReference messageReference= FirebaseDatabase.getInstance().getReference("Inboxs");
       messageReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


               for(DataSnapshot post: dataSnapshot.getChildren()){
                   PrivateMessages chat=new PrivateMessages();
                   if(post!=null){
                       chat.setSender(post.child("sender").getValue().toString());
                       chat.setReceiver(post.child("receiver").getValue().toString());
                       chat.setMessage(post.child("message").getValue().toString());
                       chat.setName(post.child("name").getValue().toString());
                   }
                   if(chat.getReceiver().equals(UID)&&chat.getSender().equals(userId)||
                           chat.getSender().equals(UID)&&chat.getReceiver().equals(userId)){
                     lastMessage=chat.getMessage();

                   }


               }
               switch (lastMessage){
                   case "default":
                       lastMsg.setText("No Message");
                       break;
                   default:
                       lastMsg.setText(lastMessage);
                       break;
               }
               lastMessage="default";


           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }

       });
   }
}
