package com.android.mid.standard;

import android.app.Fragment;
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
import com.android.mid.customizes.CurveGauge;

import java.util.Random;

/**
 * Created by nachanok.boo on 11/26/2015.
 */
public class EcoBar extends Fragment {
    public static final String KEY_BATTERY_STATUS = "battery_status";
    public static final int KEY_BATTERY_OK = 1;
    public static final int KEY_BATTERY_CHARGED = 2;
    public static final int KEY_BATTERY_CHARGING = 3;
    public static final int KEY_BATTERY_LOW = 4;

    private LinearLayout linearLayout;
    private LayoutInflater layoutInflater;
    private CurveGaugeItem curveGaugeItem;
    private Animation animation;
    private ImageView globe;
    private ImageView batteryStatus;
    private Random random;
    private Handler displayHandler;
    private Handler batteryHandler;
    private int status;
    private boolean isRed;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int value;
            int randomInt = random.nextInt(100 + 1);

            if (randomInt >= curveGaugeItem.getProgress()) {
                value = ((curveGaugeItem.getProgress() + 5) > 100) ? 100 : curveGaugeItem.getProgress() + 5;
            } else {
                value = curveGaugeItem.getProgress() - 1;
            }

            curveGaugeItem.setProgress(value);

            displayHandler.postDelayed(runnable, 100);
        }
    };

    private Runnable battable = new Runnable() {
        @Override
        public void run() {
            switch (status) {
                case KEY_BATTERY_CHARGING:
                    if (batteryStatus.getVisibility() == View.VISIBLE) {
                        batteryStatus.setVisibility(View.INVISIBLE);
                    } else {
                        batteryStatus.setVisibility(View.VISIBLE);
                    }

                    break;
                case KEY_BATTERY_LOW:
                    if (isRed) {
                        batteryStatus.setImageDrawable(getResources().getDrawable(R.mipmap.battery_low_black, getActivity().getTheme()));
                    } else {
                        batteryStatus.setImageDrawable(getResources().getDrawable(R.mipmap.battery_low_red, getActivity().getTheme()));
                    }

                    isRed = !isRed;

                    break;
            }

            batteryHandler.postDelayed(battable, 1000);
        }
    };

    public static EcoBar newInstance(int batteryStatus) {
        EcoBar fragment = new EcoBar();

        Bundle bundle = new Bundle();
        bundle.putInt(KEY_BATTERY_STATUS, batteryStatus);

        fragment.setArguments(bundle);

        return fragment;
    }

    public EcoBar() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.eco_bar, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutInflater = LayoutInflater.from(getActivity());

        View curveGaugeView = layoutInflater.inflate(R.layout.item_curve_gauge, null);
        curveGaugeItem = new CurveGaugeItem(curveGaugeView);
        curveGaugeItem.setTitle("Eco-drive Indicator");
        curveGaugeItem.setSubTitle("ECO");

        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
        globe = curveGaugeItem.getGlobe();
        globe.startAnimation(animation);

        linearLayout = (LinearLayout) view.findViewById(R.id.display);
        linearLayout.addView(curveGaugeView);

        random = new Random();

        displayHandler = new Handler();
        displayHandler.post(runnable);

        Bundle bundle = getArguments();
        status = bundle.getInt(KEY_BATTERY_STATUS);

        batteryStatus = (ImageView) view.findViewById(R.id.battery_status);
        if (status > 0) {
            switch (status) {
                case KEY_BATTERY_OK:
                case KEY_BATTERY_CHARGED:
                    if (status == KEY_BATTERY_OK) {
                        batteryStatus.setImageDrawable(getResources().getDrawable(R.mipmap.battery_ok, getActivity().getTheme()));
                    } else {
                        batteryStatus.setImageDrawable(getResources().getDrawable(R.mipmap.battery_charging, getActivity().getTheme()));
                    }

                    batteryStatus.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            batteryStatus.setVisibility(View.INVISIBLE);
                        }
                    }, 3000);

                    break;
                case KEY_BATTERY_CHARGING:
                case KEY_BATTERY_LOW:
                    if (status == KEY_BATTERY_CHARGING) {
                        batteryStatus.setImageDrawable(getResources().getDrawable(R.mipmap.battery_charging, getActivity().getTheme()));
                    } else {
                        batteryStatus.setImageDrawable(getResources().getDrawable(R.mipmap.battery_low_black, getActivity().getTheme()));
                        isRed = false;

                        batteryStatus.setVisibility(View.VISIBLE);
                    }

                    batteryHandler = new Handler();
                    batteryHandler.post(battable);

                    break;
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        displayHandler.removeCallbacks(runnable);
        if (batteryHandler != null) {
            batteryHandler.removeCallbacks(battable);
        }
    }

    private class CurveGaugeItem {
        private TextView title;
        private TextView subTitle;
        private ImageView globe;
        private CurveGauge curveGauge;

        public CurveGaugeItem(View view) {
            title = (TextView) view.findViewById(R.id.title);
            subTitle = (TextView) view.findViewById(R.id.sub_title);
            globe = (ImageView) view.findViewById(R.id.globe);
            curveGauge = (CurveGauge) view.findViewById(R.id.curve_gauge);
        }

        public void setTitle(String text) {
            title.setText(text);
        }

        public void setSubTitle(String text) {
            subTitle.setText(text);
        }

        public ImageView getGlobe() {
            return globe;
        }

        public void setProgress(int value) {
            curveGauge.setProgress(value);
        }

        public int getProgress() {
            return curveGauge.getProgress();
        }
    }
}
