package com.example.nguyephan.friendapp.data.pojo.chat;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.commons.models.MessageContentType;

import java.util.Date;

/**
 * Created by nguye phan on 5/16/2018.
 */

public class Message implements IMessage,MessageContentType.Image,MessageContentType{

    private String id;

    private String text;

    private Date createdAt;

    private User user;

    private Image image;

    private Voice voice;

    public Message(String id, User user, String text, Date createdAt) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.createdAt = createdAt;
    }

    public Message(String id, User user, String text) {
        this(id, user, text, new Date());
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public IUser getUser() {
        return user;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public String getImageUrl() {
        return image == null ? null : image.url;
    }

    public String getStatus() {
        return "Sent";
    }

    public Voice getVoice() {
        return voice;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public static class Image {

        private String url;

        public Image(String url) {
            this.url = url;
        }
    }

    public static class Voice {

        private String url;
        private int duration;

        public Voice(String url, int duration) {
            this.url = url;
            this.duration = duration;
        }

        public String getUrl() {
            return url;
        }

        public int getDuration() {
            return duration;
        }
    }
}
