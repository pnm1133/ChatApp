package com.example.nguyephan.friendapp.data.pojo.firebase;

import android.net.Uri;

/**
 * Created by nguye phan on 5/11/2018.
 */

public class FireRequestAuth {

    private static FireRequestAuth mFireRequestAuth;

    public static FireRequestAuth getInStance(){
        if(mFireRequestAuth == null){
            mFireRequestAuth = new FireRequestAuth();
        }
        return mFireRequestAuth;
    }

    private String email;

    private String password;

    private String userName;

    private Uri uriAvatar;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Uri getUriAvatar() {
        return uriAvatar;
    }

    public void setUriAvatar(Uri uriAvatar) {
        this.uriAvatar = uriAvatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
