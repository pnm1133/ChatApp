package com.example.nguyephan.friendapp.util.dilog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyephan.friendapp.R;

/**
 * Created by nguye phan on 5/12/2018.
 */

public class LoadDialog extends DialogFragment {

    int style = DialogFragment.STYLE_NO_TITLE;
    int theme = R.style.loginDialog;

    public static LoadDialog newInstance() {
        return new LoadDialog();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(style,theme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_loading,container,false);
        return view;
    }
}
