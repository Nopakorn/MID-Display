package com.android.mid.standard;

import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.mid.R;
import com.android.mid.customizes.CurveCustom;
import com.android.mid.customizes.CurveGauge;

import java.util.Calendar;
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
    private RelativeLayout relativeLayout;
    private CurveGaugeItem curveGaugeItem;
    private Animation animation;
    private ImageView globe;
    private ImageView batteryStatus;
    private Random random;
    private Handler displayHandler;
    private Handler batteryHandler;
    private int status;
    private boolean isRed;
    private TextView bottom_message;
    private ImageView bottom_diviner;
    private TextView dotBlink;
    private TextView accessory;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int value;
            int randomInt = random.nextInt(100 + 1);
//
            if(randomInt >= curveGaugeItem.getProgress()){

                if(curveGaugeItem.getProgress() + 17 >= 100){
                    curveGaugeItem.setProgress(100);
                }else if(curveGaugeItem.getProgress() == 50) {
                    value = curveGaugeItem.getProgress() + 16;
                    curveGaugeItem.setProgress(value);
                }else if(curveGaugeItem.getProgress() == 66){
                    value = curveGaugeItem.getProgress() + 17;
                    curveGaugeItem.setProgress(value);
                }else if(curveGaugeItem.getProgress() == 17){
                    value = curveGaugeItem.getProgress() + 16;
                    curveGaugeItem.setProgress(value);
                }else{
                    value = curveGaugeItem.getProgress() + 17;
                    curveGaugeItem.setProgress(value);
                }

            }else{

                if(curveGaugeItem.getProgress() == 83){
                    value = curveGaugeItem.getProgress() - 17;
                    curveGaugeItem.setProgress(value);
                }else if(curveGaugeItem.getProgress() == 33){
                    value = curveGaugeItem.getProgress() - 16;
                    curveGaugeItem.setProgress(value);
                }else if(curveGaugeItem.getProgress() == 66) {
                    value = curveGaugeItem.getProgress() - 16;
                    curveGaugeItem.setProgress(value);
                }else{
                    value = curveGaugeItem.getProgress() - 17;
                    curveGaugeItem.setProgress(value);
                }
            }
//            value = 83;
//            curveGaugeItem.setProgress(value);
            displayHandler.postDelayed(runnable, 570);
        }
    };

    private Runnable battable = new Runnable() {
        @Override
        public void run() {
            switch (status) {
                case KEY_BATTERY_CHARGING:
                    if (batteryStatus.getVisibility() == View.VISIBLE) {
                        batteryStatus.setVisibility(View.INVISIBLE);
                        bottom_message.setVisibility(View.VISIBLE);
                        bottom_diviner.setVisibility(View.VISIBLE);

                    } else {
                        batteryStatus.setVisibility(View.VISIBLE);
                        bottom_message.setVisibility(View.INVISIBLE);
                        bottom_diviner.setVisibility(View.INVISIBLE);
                    }

                    break;
                case KEY_BATTERY_LOW:
                    if (isRed) {
                        batteryStatus.setImageDrawable(getResources().getDrawable(R.mipmap.low_bar_blank, getActivity().getTheme()));
                    } else {
                        bottom_message.setVisibility(View.INVISIBLE);
                        bottom_diviner.setVisibility(View.INVISIBLE);
                        batteryStatus.setImageDrawable(getResources().getDrawable(R.mipmap.low_bar, getActivity().getTheme()));
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
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_layout);

        View curveGaugeView = layoutInflater.inflate(R.layout.item_curve_gauge, null);
        curveGaugeItem = new CurveGaugeItem(curveGaugeView);
        curveGaugeItem.setTitle("Eco-drive Indicator");
        //curveGaugeItem.setSubTitle("ECO");

        //animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
        globe = curveGaugeItem.getGlobe();
        //globe.startAnimation(animation);

        bottom_message = (TextView) view.findViewById(R.id.bottom_message);
        bottom_diviner = (ImageView) view.findViewById(R.id.bottom_diviner);

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
                        bottom_message.setVisibility(View.INVISIBLE);
                        bottom_diviner.setVisibility(View.INVISIBLE);
                        batteryStatus.setImageDrawable(getResources().getDrawable(R.mipmap.ok_bar, getActivity().getTheme()));
                    } else {
                        bottom_message.setVisibility(View.INVISIBLE);
                        bottom_diviner.setVisibility(View.INVISIBLE);
                        batteryStatus.setImageDrawable(getResources().getDrawable(R.mipmap.chg_bar, getActivity().getTheme()));
                    }

                    batteryStatus.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            batteryStatus.setVisibility(View.INVISIBLE);
                            bottom_message.setVisibility(View.VISIBLE);
                            bottom_diviner.setVisibility(View.VISIBLE);
                        }
                    }, 3000);

                    break;
                case KEY_BATTERY_CHARGING:
                case KEY_BATTERY_LOW:
                    if (status == KEY_BATTERY_CHARGING) {
                        batteryStatus.setImageDrawable(getResources().getDrawable(R.mipmap.chg_bar, getActivity().getTheme()));
                    } else {
                        batteryStatus.setImageDrawable(getResources().getDrawable(R.mipmap.low_bar_blank, getActivity().getTheme()));
                        isRed = false;

                        batteryStatus.setVisibility(View.VISIBLE);
                    }

                    batteryHandler = new Handler();
                    batteryHandler.post(battable);

                    break;
            }
        }
        //TODO: DOTBLINK
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        if (hours >= 10) {
            dotBlink = (TextView) view.findViewById(R.id.dot_blink2);
        } else {
            dotBlink = (TextView) view.findViewById(R.id.dot_blink1);
        }
        dotBlink.setVisibility(View.VISIBLE);
        dotBlink.startAnimation(animation);
    }



    @Override
    public void onStop() {
        super.onStop();

        displayHandler.removeCallbacks(runnable);
        if (batteryHandler != null) {
            batteryHandler.removeCallbacks(battable);
        }
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
        relativeLayout.startAnimation(animation);
    }

    private class CurveGaugeItem {
        private TextView title;
        private TextView subTitle;
        private ImageView globe;
        private CurveGauge curveGauge;

        public CurveGaugeItem(View view) {
            title = (TextView) view.findViewById(R.id.title);
            //subTitle = (TextView) view.findViewById(R.id.sub_title);
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
