package com.example.nguyephan.friendapp.ui.base;

import android.support.annotation.StringRes;

/**
 * Created by nguye phan on 5/9/2018.
 */

public interface BaseContract {

    interface View {

        void errorMessage(String string);

        boolean isInternetConnection();

        void errorInternetConnection();

        void showKeyboard();

        void showLoadDialog();

        void hideLoadDialog();

    }

    interface Presenter<V extends  View> {

        void onAttach(V v);

        void onDetach();

        boolean isAttachView();

        V getView();

    }
}
