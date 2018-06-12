package com.example.nguyephan.friendapp.util.page;

/**
 * Created by nguye phan on 5/13/2018.
 */

public enum Page {
    Login("Login"),Register("Register"),LoadDialog("LoadingDialog"),ManAc("Main");
    String tag;
    Page(String tag){
        this.tag = tag;
    }
    public String getTag() {
        return tag;
    }
}
