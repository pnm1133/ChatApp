package com.example.nguyephan.friendapp.ui.chat.gift_fr;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyephan.friendapp.R;

import butterknife.ButterKnife;

public class GiftFr extends Fragment {

    public static GiftFr getInstance(){
        return new GiftFr();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gift_fr,container,false);
        if(view != null){
            ButterKnife.bind(this,view);
        }
        return view;
    }
}
