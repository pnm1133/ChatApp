package com.example.nguyephan.friendapp.ui.chat.location_fr;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyephan.friendapp.R;

import butterknife.ButterKnife;

public class LocationFr  extends Fragment{

    public static LocationFr getInstance(){
        return new LocationFr();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.location_fr,container,false);
        if(view != null){
            ButterKnife.bind(this,view);
        }
        return view;
    }
}
