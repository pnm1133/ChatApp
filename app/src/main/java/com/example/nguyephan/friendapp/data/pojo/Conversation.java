package com.example.nguyephan.friendapp.data.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.nguyephan.friendapp.data.pojo.chat.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Conversation implements Parcelable {
    @SerializedName("senderId")
    @Expose
    private String senderId;
    @SerializedName("lastMessage")
    @Expose
    private String lastMessage;
    @SerializedName("unSeenCount")
    @Expose
    private Integer unSeenCount;
    @SerializedName("conversationTitle")
    @Expose
    private String conversationTitle;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("senderAvatar")
    @Expose
    private String senderAvatar;
    @SerializedName("sender")
    @Expose
    private User sender;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }


    public String getConversationTitle() {
        return conversationTitle;
    }

    public void setConversationTitle(String conversationTitle) {
        this.conversationTitle = conversationTitle;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Integer getUnSeenCount() {
        return unSeenCount;
    }

    public void setUnSeenCount(Integer unSeenCount) {
        this.unSeenCount = unSeenCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderAvatar() {
        return senderAvatar;
    }

    public void setSenderAvatar(String senderAvatar) {
        this.senderAvatar = senderAvatar;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.senderId);
        dest.writeString(this.lastMessage);
        dest.writeValue(this.unSeenCount);
        dest.writeString(this.conversationTitle);
        dest.writeString(this.id);
        dest.writeString(this.senderAvatar);
        dest.writeParcelable(this.sender, flags);
    }

    public Conversation() {
    }

    protected Conversation(Parcel in) {
        this.senderId = in.readString();
        this.lastMessage = in.readString();
        this.unSeenCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.conversationTitle = in.readString();
        this.id = in.readString();
        this.senderAvatar = in.readString();
        this.sender = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Conversation> CREATOR = new Creator<Conversation>() {
        @Override
        public Conversation createFromParcel(Parcel source) {
            return new Conversation(source);
        }

        @Override
        public Conversation[] newArray(int size) {
            return new Conversation[size];
        }
    };
}
