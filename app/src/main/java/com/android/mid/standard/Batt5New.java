package com.android.mid.standard;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
    private RelativeLayout relativeLayout_display;
    private RelativeLayout relativeLayout_head;

    private TextView batteryStatus;
    private TextView dotBlink;
    private Animation animation;
    private Handler displayHandler;
    private Handler odoHandler;
    private int stateBattery = 0;

    private TextView celText;
    private TextView tempText;
    private TextView dText;
    private TextView eText;
    private TextView fText;
    private TextView clockText;

    private boolean flag = false;
    private int km = 2557;

    private Runnable runnable_odo = new Runnable() {
        @Override
        public void run() {
            km = km + 1;
            String odo_t = "ODO";
            tempText.setText(String.format(odo_t+"     "+km));

            displayHandler.postDelayed(runnable_odo, 20000);
        }
    };


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            stateBattery++;
            switch (stateBattery){

                case 1:
                    batteryLevel.setImageDrawable(getResources().getDrawable(R.mipmap.charging_2, getActivity().getTheme()));
                    break;
                case 2:
                    batteryLevel.setImageDrawable(getResources().getDrawable(R.mipmap.charging_3, getActivity().getTheme()));
                    break;
                case 3:
                    batteryLevel.setImageDrawable(getResources().getDrawable(R.mipmap.charging_4, getActivity().getTheme()));
                    break;
                case 4:
                    batteryLevel.setImageDrawable(getResources().getDrawable(R.mipmap.charging_5, getActivity().getTheme()));
                    break;
                case 5:
                    batteryLevel.setImageDrawable(getResources().getDrawable(R.mipmap.ok_6, getActivity().getTheme()));
                    break;
                case 6:
                    batteryLevel.setImageDrawable(getResources().getDrawable(R.mipmap.ok_7, getActivity().getTheme()));
                    break;
                case 7:
                    //TODO: START FROM LOW BATTERY DISPLAY
                    batteryLevel.setImageDrawable(getResources().getDrawable(R.mipmap.low_1, getActivity().getTheme()));
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
        relativeLayout_display = (RelativeLayout) view.findViewById(R.id.display);
        relativeLayout_head = (RelativeLayout) view.findViewById(R.id.relativeLayout_head);

        batteryLevel= (ImageView) view.findViewById(R.id.battery_level);

        //TODO: DOTBLINK
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        dotBlink = (TextView) view.findViewById(R.id.dot_blink2);
//        if (hours >= 10) {
//            dotBlink = (TextView) view.findViewById(R.id.dot_blink2);
//        } else {
//            dotBlink = (TextView) view.findViewById(R.id.dot_blink1);
//        }
        dotBlink.setVisibility(View.VISIBLE);
        dotBlink.startAnimation(animation);

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

        Log.d("FLAG", "before animation " + flag);
        displayHandler = new Handler();


        //TODO: ANIMATION HEADER AND DISPLAY BODYS
        relativeLayout_display.setVisibility(View.INVISIBLE);
        Animation animation_in = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_head);
        relativeLayout_head.setAnimation(animation_in);
        callDisplay();

        odoHandler = new Handler();
        odoHandler.post(runnable_odo);
    }

    public void callDisplay() {
        relativeLayout_display.setVisibility(View.VISIBLE);
        Animation animation_in_display = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_batt5);
        animation_in_display.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                displayHandler.post(runnable);
            }
        });
        relativeLayout_display.setAnimation(animation_in_display);
        Log.d("FLAG","after animation " +flag);


    }

    public void onStop() {
        super.onStop();
        displayHandler.removeCallbacks(runnable);
        odoHandler.removeCallbacks(runnable_odo);
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
        relativeLayout.startAnimation(animation);
    }
}
