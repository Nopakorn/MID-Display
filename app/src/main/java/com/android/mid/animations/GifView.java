package com.android.mid.animations;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;

import com.android.mid.R;

/**
 * Created by nachanok.boo on 11/26/2015.
 */
public class GifView extends View {
    private Movie movie;
    private long start;
    private int id;

    public GifView(Context context) {
        super(context);

        initialize();
    }

    public GifView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initialize();
    }

    public GifView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initialize();
    }

    private void initialize() {
        //movie = Movie.decodeStream(getContext().getResources().openRawResource(R.mipmap.globe));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        super.onDraw(canvas);

        long now = android.os.SystemClock.uptimeMillis();
        start = (start == 0) ? now : start;

        if (movie != null) {
            movie.setTime((int) ((now - start) % (movie.duration())));
            movie.draw(canvas, getWidth() - movie.width(), getHeight() - movie.height());

            this.invalidate();
        }
    }

    public void setResource(int resId) {
        this.id = resId;

        initialize();
    }

    public int getResource() {
        return this.id;
    }
}
