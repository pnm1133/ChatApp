package com.example.nguyephan.friendapp.data.pojo.firebase;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by nguye phan on 5/11/2018.
 */

public class FireRequest  {

    public static FireRequest getInStance(@NonNull String email,
                                          @NonNull String password,
                                          String userName,
                                          String uriAvatar){
        return new FireRequest(email,password,userName,uriAvatar);
    }

    public FireRequest(String email, String password, String userName, String uriAvatar) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.uriAvatar = uriAvatar;
    }

    private String email;

    private String password;

    private String userName;

    private String uriAvatar;



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUriAvatar() {
        return uriAvatar;
    }

    public void setUriAvatar(String uriAvatar) {
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
