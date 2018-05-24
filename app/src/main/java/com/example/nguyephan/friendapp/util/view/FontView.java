package com.example.nguyephan.friendapp.util.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.nguyephan.friendapp.R;

/**
 * Created by nguye phan on 5/11/2018.
 */

public class FontView {

    public static void setFontFamilyTextView(Context context,TextView... resources){
        Typeface typeface = ResourcesCompat.getFont(context,R.font.font);

        for (TextView textView : resources){
            textView.setTypeface(typeface);
        }
    }

    public static void setFontFamilyTextInputLayout(Context context,TextInputLayout... resources){
        Typeface typeface = ResourcesCompat.getFont(context,R.font.font);

        for (TextInputLayout textInputLayout : resources){
            textInputLayout.setTypeface(typeface);
        }
    }

}
