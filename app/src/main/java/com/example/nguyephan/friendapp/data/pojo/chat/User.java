package com.example.nguyephan.friendapp.data.pojo.chat;

import android.net.Uri;

import com.stfalcon.chatkit.commons.models.IUser;

/**
 * Created by nguye phan on 5/12/2018.
 */

public class User implements IUser {

    private String id;

    private String name;

    private String avatar;

    private boolean online;

    public User(String id, String name, String avatar, boolean online) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.online = online;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    public boolean isOnline() {
        return online;
    }
}
