package com.mp3cutter.Biit.Custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class MyTextView1 extends TextView {

    public MyTextView1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView1(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/ITCEDSCR.TTF");
            setTypeface(tf);
        }
    }

}