package com.android.mid.standard;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.mid.R;
import com.android.mid.customizes.Batt5Progress;

import java.util.Calendar;

/**
 * Created by Nopakorn on 2/2/2016.
 */
public class Batt5New extends Fragment {

    private ImageView batteryLevel;
    private LinearLayout  linearLayout;
    private TextView batteryStatus;
    private TextView dotBlink;
    private Animation animation;
    private Handler displayHandler;
    private int stateBattery = 0;



    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            stateBattery++;
            switch (stateBattery){
                case 1:
                    //TODO: START FROM LOW BATTERY DISPLAY
                    batteryLevel.setImageDrawable(getResources().getDrawable(R.mipmap.batt_low, getActivity().getTheme()));
                    break;
                case 2:
                    batteryLevel.setImageDrawable(getResources().getDrawable(R.mipmap.batt_charging, getActivity().getTheme()));
                    break;
                case 3:
                    batteryLevel.setImageDrawable(getResources().getDrawable(R.mipmap.batt_ok, getActivity().getTheme()));
                    break;
                case 4:
                    stateBattery = 0;
                    break;
            }
            displayHandler.postDelayed(runnable, 1000);
        }
    };



    public static Batt5New newInstance() {
        return new Batt5New();
    }

    public Batt5New() {
        // Required empty public constructor
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.batt5new, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        batteryLevel= (ImageView) view.findViewById(R.id.battery_level);

        //TODO: DOTBLINK
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

        displayHandler = new Handler();
        displayHandler.post(runnable);

    }

    public void onStop() {
        super.onStop();
        displayHandler.removeCallbacks(runnable);
    }
}
