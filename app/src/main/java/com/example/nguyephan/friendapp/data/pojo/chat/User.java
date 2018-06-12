package com.example.nguyephan.friendapp.data.pojo.chat;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.nguyephan.friendapp.data.pojo.Conversation;
import com.example.nguyephan.friendapp.data.pojo.Friend;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguye phan on 5/12/2018.
 */

public class User implements IUser,Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("isOnline")
    @Expose
    private boolean isOnline;

    public List<Conversation> getConversationList() {
        return conversationList;
    }

    public void setConversationList(List<Conversation> conversationList) {
        this.conversationList = conversationList;
    }

    public List<Friend> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(List<Friend> friendsList) {
        this.friendsList = friendsList;
    }

    private List<Conversation> conversationList = null;

    private List<Friend> friendsList = null;


    public User(String id, String name, String avatar, boolean isOnline) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.isOnline = isOnline;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.avatar);
        dest.writeByte(this.isOnline ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.conversationList);
        dest.writeList(this.friendsList);
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.avatar = in.readString();
        this.isOnline = in.readByte() != 0;
        this.conversationList = in.createTypedArrayList(Conversation.CREATOR);
        this.friendsList = new ArrayList<Friend>();
        in.readList(this.friendsList, Friend.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
