package com.example.nguyephan.friendapp.data.pojo.chat;

import com.stfalcon.chatkit.commons.models.IDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguye phan on 5/16/2018.
 */

public class Dialog implements IDialog<Message> {

    private String id;

    private String dialogPhoto;

    private String dialogName;

    private ArrayList<User> users;

    private Message lastMessage;

    private int unreadCount;

    public Dialog(String id, String dialogPhoto,
                  String dialogName,
                  ArrayList<User> users,
                  Message lastMessage,
                  int unreadCount) {
        this.id = id;
        this.dialogPhoto = dialogPhoto;
        this.dialogName = dialogName;
        this.users = users;
        this.lastMessage = lastMessage;
        this.unreadCount = unreadCount;
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDialogPhoto() {
        return dialogPhoto;
    }

    @Override
    public String getDialogName() {
        return dialogName;
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public Message getLastMessage() {
        return lastMessage;
    }

    @Override
    public void setLastMessage(Message message) {
        this.lastMessage = message;
    }

    @Override
    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}
