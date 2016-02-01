package com.android.mid.customizes;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.android.mid.R;

/**
 * Created by nachanok.boo on 11/25/2015.
 */
public class Gauge extends ProgressBar {
    public Gauge(Context context) {
        this(context, null);
    }

    public Gauge(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Gauge(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setProgressDrawable(context.getResources().getDrawable(R.drawable.gauge));
    }
}
