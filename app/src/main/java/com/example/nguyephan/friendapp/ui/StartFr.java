package com.example.nguyephan.friendapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyephan.friendapp.R;
import com.example.nguyephan.friendapp.ui.base.BaseFr;

/**
 * Created by nguye phan on 5/11/2018.
 */

public class StartFr extends BaseFr {

    private static StartFr mStartFr;
    public static StartFr getInstance(){
        if(mStartFr == null){
            mStartFr = new StartFr();
        }
        return mStartFr;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.register_page,container,false);
        return view;
    }
}
