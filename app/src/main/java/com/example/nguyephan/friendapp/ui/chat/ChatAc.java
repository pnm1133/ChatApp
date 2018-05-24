package com.example.nguyephan.friendapp.ui.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.nguyephan.friendapp.R;
import com.example.nguyephan.friendapp.data.api.firebase.FCFirestoreImp;
import com.example.nguyephan.friendapp.data.api.firebase.FCStorageConnect;
import com.example.nguyephan.friendapp.data.pojo.chat.Dialog;
import com.example.nguyephan.friendapp.data.pojo.chat.Message;
import com.example.nguyephan.friendapp.data.pojo.chat.User;
import com.example.nguyephan.friendapp.data.pojo.static2.DialogsFixtures;
import com.example.nguyephan.friendapp.di.Component.AcComponent;
import com.example.nguyephan.friendapp.ui.base.BaseAc;
import com.example.nguyephan.friendapp.ui.main.MainAc;
import com.example.nguyephan.friendapp.util.GlideApp;
import com.example.nguyephan.friendapp.util.camera.CameraApp;
import com.example.nguyephan.friendapp.util.camera.CameraBuilder;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class ChatAc extends BaseAc {

    @Inject
    FCFirestoreImp mFCFirestoreImp;

    @Inject
    FCStorageConnect mFCStorageConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mFCFirestoreImp.readInforUser();

//        mFCStorageConnect.addAvatar();


        DialogsList dialogsList = findViewById(R.id.dialogsList);

        DialogsListAdapter dialogsListAdapter = new DialogsListAdapter<>( new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                //If you using another library - write here your way to load image
                GlideApp.with(ChatAc.this)
                        .load(url)
                        .into(imageView);
            }
        });


        ArrayList<User> users = new ArrayList<>();
        users.add(DialogsFixtures.getUser());
        users.add(DialogsFixtures.getUser());
        users.add(DialogsFixtures.getUser());
        users.add(DialogsFixtures.getUser());

        Message message1 = new Message("1",DialogsFixtures.getUser(),"Con me no ... haha");
        Message message2 = new Message("2",DialogsFixtures.getUser(),"du moa....");
        Message message3 = new Message("3",DialogsFixtures.getUser(),"anh yeu em!!!!!!");
        Message message4 = new Message("4",DialogsFixtures.getUser(),"fuck ui...?");

        ArrayList<Dialog> list = new ArrayList<>();
        list.add(new Dialog("1", "http://i.imgur.com/pv1tBmT.png","Nguyen",users ,message1,1));
        list.add(new Dialog("1", "http://i.imgur.com/R3Jm1CL.png","Nhom 3 con dien",users ,message2,5));
        list.add(new Dialog("1", "http://i.imgur.com/pv1tBmT.png","Tinh don phuong",users ,message3,2));
        list.add(new Dialog("1", "http://i.imgur.com/ROz4Jgh.png","Yeu em",users ,message4,0));


        dialogsListAdapter.addItems(list);

//        dialogsListAdapter.addItems(new Dialog("1","http://i.imgur.com/pv1tBmT.png","Nguyen",users,message,2));

        dialogsList.setAdapter(dialogsListAdapter);
    }

    @Override
    public void build(AcComponent acComponent) {
        acComponent.inject(this);
    }
}
