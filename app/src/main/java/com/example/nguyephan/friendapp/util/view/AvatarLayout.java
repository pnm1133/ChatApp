package com.example.nguyephan.friendapp.util.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.example.nguyephan.friendapp.R;
import com.example.nguyephan.friendapp.util.GlideApp;

public class AvatarLayout extends ConstraintLayout {
    private static final String TAG = AvatarLayout.class.getSimpleName();
    private FloatingActionButton fab;
    private AppCompatImageView img;

    public AvatarLayout(Context context) {
        super(context);
        init(context);
    }

    public AvatarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AvatarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        inflate(context, R.layout.item_avatar,this);
        fab = findViewById(R.id.fab);
        img = findViewById(R.id.image);

//        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//
//
//                return false;
//            }
//        });
    }

    public void showImage(@Nullable String url,Fragment fragment){
        GlideApp.with(fragment)
                .load(url)
                .centerCrop()
                .circleCrop()
                .into(img);
    }

    public void notifyNews(){

    }

    public void isOnline(boolean isOnline){
        if(isOnline){
            fab.setBackground(getResources().getDrawable(R.drawable.background_fab_notify));
        }else {
            fab.setBackground(getResources().getDrawable(R.drawable.background_fab_offline));
        }
    }
}
