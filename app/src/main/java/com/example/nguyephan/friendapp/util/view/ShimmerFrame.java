package com.example.nguyephan.friendapp.util.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.nguyephan.friendapp.R;

public class ShimmerFrame extends LinearLayout {

    public ShimmerFrame(Context context) {
        super(context);
        init();
    }

    public ShimmerFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShimmerFrame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init(){
        inflate(getContext(), R.layout.shimmer_layout,this);

    }


}
