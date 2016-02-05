package com.android.mid.standard;

import android.app.Fragment;
import android.graphics.Typeface;
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
import android.widget.RelativeLayout;
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
    private RelativeLayout relativeLayout;
    private TextView batteryStatus;
    private TextView dotBlink;
    private Animation animation;
    private Handler displayHandler;
    private int stateBattery = 0;

    private TextView celText;
    private TextView tempText;
    private TextView dText;
    private TextView eText;
    private TextView fText;
    private TextView clockText;


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
                    batteryLevel.setImageDrawable(getResources().getDrawable(R.mipmap.batt_charging_2, getActivity().getTheme()));
                    break;
                case 4:
                    batteryLevel.setImageDrawable(getResources().getDrawable(R.mipmap.batt_charging_3, getActivity().getTheme()));
                    break;
                case 5:
                    batteryLevel.setImageDrawable(getResources().getDrawable(R.mipmap.batt_ok, getActivity().getTheme()));
                    break;
                case 6:
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

        relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_layout);

        batteryLevel= (ImageView) view.findViewById(R.id.battery_level);

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

        displayHandler = new Handler();
        displayHandler.post(runnable);
        //TODO: SET FONT FACE
        celText = (TextView) view.findViewById(R.id.cel_text);
        dText = (TextView) view.findViewById(R.id.center_text);
        eText = (TextView) view.findViewById(R.id.e_text);
        fText = (TextView) view.findViewById(R.id.f_text);
        tempText = (TextView) view.findViewById(R.id.temperature);
        clockText = (TextView) view.findViewById(R.id.text_clock);
        batteryStatus = (TextView) view.findViewById(R.id.batteryStatus);

        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MyriadPro-Regular.otf");
        celText.setTypeface(custom_font);
        dText.setTypeface(custom_font);
        eText.setTypeface(custom_font);
        fText.setTypeface(custom_font);
        tempText.setTypeface(custom_font);
        clockText.setTypeface(custom_font);
        batteryStatus.setTypeface(custom_font);

    }

    public void onStop() {
        super.onStop();
        displayHandler.removeCallbacks(runnable);
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
        relativeLayout.startAnimation(animation);
    }
}
