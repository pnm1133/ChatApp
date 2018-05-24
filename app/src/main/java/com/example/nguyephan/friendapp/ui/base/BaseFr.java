package com.example.nguyephan.friendapp.ui.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.example.nguyephan.friendapp.di.Component.AcComponent;

/**
 * Created by nguye phan on 5/9/2018.
 */

public class BaseFr extends Fragment implements BaseContract.View {

    private BaseAc mBaseAc;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof BaseAc){
            mBaseAc = (BaseAc) context;
        }
    }
    @Override
    public void errorMessage(String string) {

    }

    @Override
    public boolean isInternetConnection() {
        return mBaseAc.isInternetConnection();
    }

    @Override
    public void errorInternetConnection() {
        mBaseAc.errorInternetConnection();
    }

    @Override
    public void showKeyboard() {
        mBaseAc.showKeyboard();
    }

    @Override
    public void hideLoadDialog() {
        mBaseAc.hideLoadDialog();
    }

    @Override
    public void showLoadDialog() {
        mBaseAc.showLoadDialog();
    }

    public AcComponent getActivityComponent(){
        return mBaseAc.getAcComponent();
    }
}
