package com.example.nguyephan.friendapp.ui.main;

import android.arch.lifecycle.ViewModel;

/**
 * Created by nguye phan on 5/9/2018.
 */

public class MainModel extends ViewModel {

    private MainContract.Presenter<MainContract.View> presenter;

    private String email;

    private String password;

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

    public void setPresenter(MainContract.Presenter<MainContract.View> presenter) {
        this.presenter = presenter;
    }

    public MainContract.Presenter<MainContract.View> getPresenter() {
        return presenter;
    }
}
