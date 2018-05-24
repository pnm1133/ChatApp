package com.example.nguyephan.friendapp.ui.main;

import com.example.nguyephan.friendapp.data.pojo.firebase.FireRequestAuth;
import com.example.nguyephan.friendapp.di.AcScope;
import com.example.nguyephan.friendapp.ui.base.BaseContract;

/**
 * Created by nguye phan on 5/9/2018.
 */

public interface MainContract {

    interface View extends BaseContract.View {

        void replaceLoginPage();

        void replaceStartPage();

        void replaceRegisterPage();

    }

    @AcScope
    interface Presenter<V extends View> extends BaseContract.Presenter<V>{

        void checkUserLogin();

        void startLogin(FireRequestAuth fireRequestAuth);

        void cacheUser();

        void startRegister(FireRequestAuth fireRequestAuth);

    }
}
