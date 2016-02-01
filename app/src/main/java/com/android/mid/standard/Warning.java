package com.android.mid.standard;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.android.mid.R;

import java.util.Calendar;

/**
 * Created by nachanok.boo on 11/26/2015.
 */
public class Warning extends Fragment {
    private TextView dotBlink;
    private Animation animation;
    private TextView odometer;
    private int km = 2557;
    private Handler displayHandler;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            km = km + 1;

            if (km != Integer.parseInt(odometer.getText().toString())) {
                odometer.setText(String.format("%06d", km));
            }

            displayHandler.postDelayed(runnable, 20000);
        }
    };

    public static Warning newInstance() {
        return new Warning();
    }

    public Warning() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.warning, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR);
        if (hours >= 10) {
            dotBlink = (TextView) view.findViewById(R.id.dot_blink2);
        } else {
            dotBlink = (TextView) view.findViewById(R.id.dot_blink1);
        }
        dotBlink.setVisibility(View.VISIBLE);
        dotBlink.startAnimation(animation);

        odometer = (TextView) view.findViewById(R.id.odometer);

        displayHandler = new Handler();
        displayHandler.post(runnable);
    }

    @Override
    public void onStop() {
        super.onStop();

        displayHandler.removeCallbacks(runnable);
    }
}
