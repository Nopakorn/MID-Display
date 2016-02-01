package com.android.mid.customizes;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.android.mid.R;

/**
 * Created by Nopakorn on 11/27/2015.
 */
public class Batt5Progress extends ProgressBar {
    public Batt5Progress(Context context) {
        this(context, null);
    }

    public Batt5Progress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Batt5Progress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setProgressDrawable(context.getResources().getDrawable(R.drawable.batt5_progress));
    }
}

