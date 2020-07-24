package com.thisischool.chool.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.thisischool.chool.Classes.AppHelper;
import com.thisischool.chool.OnlineDatabase.SendMessage;
import com.thisischool.chool.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ImageMessageActivity extends AppCompatActivity {

    private ImageView imageView;
    public static final String TAG = "UploadMenu";
    public static final int PICK_IMAGE_REQUEST = 1;
    private Uri path;
    private static final String CLASS_GROUP_CHAT_IMAGES_STORAGE_DB = "Class_Group_Chat";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_message);
        EditText msgEdit = findViewById(R.id.msg_imgmsg);
        imageView = findViewById(R.id.imgview_imgmsg);

        findViewById(R.id.send_imgmsg).setOnClickListener(view -> {
            if (msgEdit.getText().toString().isEmpty()) {
                msgEdit.setError("Empty Messages Cannot be Sent");
                msgEdit.requestFocus();
                return;
            }
            uploadImage(msgEdit.getText().toString());
            startActivity(new Intent(ImageMessageActivity.this, ClassChatGroupActivity.class));
            finish();
        });

        imageView.setOnClickListener(view -> {
            selectImage();
        });
    }
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture for your menu Item's Display Picture"),PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode , int resultCode , Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data
                != null && data.getData() != null){
            path = data.getData();
            Picasso.get().load(path).fit().centerCrop().noPlaceholder().into(imageView);
        }
    }
    private void uploadImage(String msg) {
        if (path != null) {
                try {
                    final StorageReference mStorageRef = FirebaseStorage.getInstance().getReference(CLASS_GROUP_CHAT_IMAGES_STORAGE_DB)
                            .child(System.currentTimeMillis() + "" + "." + AppHelper.getFileExtension(this,path));

                    mStorageRef.putFile(path).addOnSuccessListener(taskSnapshot -> mStorageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String url = uri.toString();
                        SendMessage.sendMessageToGroupChat(this,msg,url);
                    })).addOnFailureListener(e -> {
                        AppHelper.showToast(ImageMessageActivity.this,e.getMessage());
                    });

                } catch (Exception e) {
                    Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                }

            }
        }

}