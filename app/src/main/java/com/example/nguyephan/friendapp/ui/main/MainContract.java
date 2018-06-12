package com.example.nguyephan.friendapp.ui.main;

import com.example.nguyephan.friendapp.data.pojo.firebase.FireRequest;
import com.example.nguyephan.friendapp.di.AcScope;
import com.example.nguyephan.friendapp.ui.base.BaseContract;
import com.example.nguyephan.friendapp.util.page.Page;

/**
 * Created by nguye phan on 5/9/2018.
 */

public interface MainContract {

    interface View extends BaseContract.View {

        void replaceLoginPage();

        void replaceChatPage();

        void replaceRegisterPage();

    }

    @AcScope
    interface Presenter<V extends View> extends BaseContract.Presenter<V>{

        void checkUserLogin();

        void startLogin(FireRequest fireRequest);

        void cacheUser();

        void startRegister(FireRequest fireRequest);

        void saveTokenSession(Page page);

    }
}
