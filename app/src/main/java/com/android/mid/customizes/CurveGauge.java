package com.android.mid.customizes;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.android.mid.R;

/**
 * Created by nachanok.boo on 11/26/2015.
 */
public class CurveGauge extends ProgressBar {
    public CurveGauge(Context context) {
        this(context, null);
    }

    public CurveGauge(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CurveGauge(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setProgressDrawable(context.getResources().getDrawable(R.drawable.curve_gauge));
    }
}
