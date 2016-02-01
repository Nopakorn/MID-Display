package com.android.mid.standard;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.mid.R;
import com.android.mid.customizes.Batt5Progress;

/**
 * Created by Nopakorn on 11/27/2015.
 */
public class Batt5 extends Fragment {
    private ImageView batteryStatus;
    private LinearLayout  linearLayout;
    private Batt5Progress batt5Progress;
    private Handler displayHandler;
    private int stateBattery = 0;


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int value;
            stateBattery++;

            switch (stateBattery){
                case 1:
                    value = 0;
                    batt5Progress.setProgress(value);
                    batteryStatus.setImageDrawable(getResources().getDrawable(R.mipmap.battery_low_red, getActivity().getTheme()));
                    batteryStatus.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    value = 51;
                    batt5Progress.setProgress(value);
                    batteryStatus.setImageDrawable(getResources().getDrawable(R.mipmap.battery_charging, getActivity().getTheme()));
                    batteryStatus.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    value = 64;
                    batt5Progress.setProgress(value);
                    batteryStatus.setImageDrawable(getResources().getDrawable(R.mipmap.battery_charging, getActivity().getTheme()));
                    batteryStatus.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    value = 77;
                    batt5Progress.setProgress(value);
                    batteryStatus.setImageDrawable(getResources().getDrawable(R.mipmap.battery_charging, getActivity().getTheme()));
                    batteryStatus.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    value = 100;
                    batt5Progress.setProgress(value);
                    batteryStatus.setImageDrawable(getResources().getDrawable(R.mipmap.battery_ok, getActivity().getTheme()));
                    batteryStatus.setVisibility(View.VISIBLE);
                    break;
                case 6:
                    stateBattery = 0;
                    break;
            }
            displayHandler.postDelayed(runnable, 1000);
        }
    };
    public static Batt5 newInstance() {
        return new Batt5();
    }

    public Batt5() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.batt5, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        batt5Progress = (Batt5Progress) view.findViewById(R.id.batt5_progress);
        batteryStatus = (ImageView) view.findViewById(R.id.battery_status);

        displayHandler = new Handler();
        displayHandler.post(runnable);

    }

    public void onStop() {
        super.onStop();
        displayHandler.removeCallbacks(runnable);
    }
}
