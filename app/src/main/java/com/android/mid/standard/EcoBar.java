package com.android.mid.standard;

import android.app.Fragment;
import android.content.res.AssetFileDescriptor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
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
    private RelativeLayout relativeLayout_head;
    private RelativeLayout relativeLayout_display;
    private RelativeLayout relativeLayout_bottom;

    private View curveGaugeView;

    private Animation animation;
    private ImageView globe;
    private ImageView curveBar;
    private ImageView batteryStatus;
    private ImageView batteryStatus_blank;
    private Random random;
    private Handler displayHandler;
    private Handler batteryHandler;
    private Handler odoHandler;
    private int status;
    private int curve_status = 0;
    private boolean isRed;
    private int checker = 0;
    private boolean flag = false;

    private ImageView bottom_diviner;
    private TextView dotBlink;
    private TextView title;

    private TextView celText;
    private TextView tempText;
    private TextView dText;
    private TextView eText;
    private TextView fText;
    private TextView clockText;
    private TextView bottom_message;
    private TextView odoText;
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
            int value;
            int randomInt = random.nextInt(6 + 1);

            if (randomInt >= curve_status) {
                value = ((curve_status + 1) > 6) ? 6 :curve_status + 1;
            } else {
                value = curve_status - 1;
            }

            curve_status = value;
            switch (curve_status) {
                case 1:
                    curveBar.setImageDrawable(getResources().getDrawable(R.mipmap.bar_s1, getActivity().getTheme()));
                    break;

                case 2:
                    curveBar.setImageDrawable(getResources().getDrawable(R.mipmap.bar_s2, getActivity().getTheme()));
                    break;

                case 3:
                    curveBar.setImageDrawable(getResources().getDrawable(R.mipmap.bar_s3, getActivity().getTheme()));
                    break;

                case 4:
                    curveBar.setImageDrawable(getResources().getDrawable(R.mipmap.bar_s4, getActivity().getTheme()));
                    break;

                case 5:
                    curveBar.setImageDrawable(getResources().getDrawable(R.mipmap.bar_s5, getActivity().getTheme()));
                    break;

                case 6:
                    curveBar.setImageDrawable(getResources().getDrawable(R.mipmap.bar_s6, getActivity().getTheme()));
                    break;

                case 7:
                    curve_status = 0;
                    break;
            }

            displayHandler.postDelayed(runnable, 2000);
        }
        };

    private Runnable battable = new Runnable() {
        @Override
        public void run() {

            //TODO: DECLARE ANIMATION STATUS BAR
            Animation status_in = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_batt3);
            Animation status_out = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out_batt3);

            switch (status) {
                case KEY_BATTERY_CHARGING:
                    batteryStatus_blank.setVisibility(View.INVISIBLE);

                    if (batteryStatus.getVisibility() == View.VISIBLE) {
                        batteryStatus.setAnimation(status_out);
                        batteryStatus.setVisibility(View.INVISIBLE);
                        bottom_message.setAnimation(status_in);
                        bottom_diviner.setAnimation(status_in);
                        bottom_message.setVisibility(View.VISIBLE);
                        bottom_diviner.setVisibility(View.VISIBLE);

                    } else {
                        batteryStatus.setAnimation(status_in);
                        batteryStatus.setVisibility(View.VISIBLE);
                        bottom_message.setAnimation(status_out);
                        bottom_diviner.setAnimation(status_out);
                        bottom_message.setVisibility(View.INVISIBLE);
                        bottom_diviner.setVisibility(View.INVISIBLE);
                    }

                    break;

                case KEY_BATTERY_LOW:

                    bottom_message.setVisibility(View.INVISIBLE);
                    bottom_diviner.setVisibility(View.INVISIBLE);
                    batteryStatus.setImageDrawable(getResources().getDrawable(R.mipmap.low_bar, getActivity().getTheme()));

                    if(checker == 20){
                        checker = 0;
                        isRed = true;
                        break;

                    }

                    if(checker > 10){
                        batteryStatus.setImageDrawable(getResources().getDrawable(R.mipmap.low_bar, getActivity().getTheme()));
                        checker++;
                        break;

                    }

                    if(checker <= 10){

                        if(checker == 0){
                            batteryStatus.setImageDrawable(getResources().getDrawable(R.mipmap.low_bar, getActivity().getTheme()));
                        }

                        if (isRed) {
                            batteryStatus.setImageDrawable(getResources().getDrawable(R.mipmap.low_bar_blank, getActivity().getTheme()));
                        } else {
                            batteryStatus.setImageDrawable(getResources().getDrawable(R.mipmap.low_bar, getActivity().getTheme()));
                        }
                        checker++;
                        isRed = !isRed;
                        break;
                    }

            }
            Log.d("checker",""+checker);
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
        relativeLayout_head = (RelativeLayout) view.findViewById(R.id.relativeLayout_head);
        relativeLayout_display = (RelativeLayout) view.findViewById(R.id.display);
        relativeLayout_bottom = (RelativeLayout) view.findViewById(R.id.display_bottom);

        curveGaugeView = layoutInflater.inflate(R.layout.item_curve_gauge, null);
        //TODO: SET FONT FACE
        celText = (TextView) view.findViewById(R.id.cel_text);
        dText = (TextView) view.findViewById(R.id.center_text);
        eText = (TextView) view.findViewById(R.id.e_text);
        fText = (TextView) view.findViewById(R.id.f_text);
        tempText = (TextView) view.findViewById(R.id.temperature);
        clockText = (TextView) view.findViewById(R.id.text_clock);
        bottom_message = (TextView) view.findViewById(R.id.bottom_message);

        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MyriadPro-Regular.otf");
        celText.setTypeface(custom_font);
        dText.setTypeface(custom_font);
        eText.setTypeface(custom_font);
        fText.setTypeface(custom_font);
        tempText.setTypeface(custom_font);
        clockText.setTypeface(custom_font);
        bottom_message.setTypeface(custom_font);

        bottom_diviner = (ImageView) view.findViewById(R.id.bottom_diviner);



        relativeLayout_display.addView(curveGaugeView);
        relativeLayout_display.setVisibility(View.INVISIBLE);
        relativeLayout_bottom.setVisibility(View.INVISIBLE);


        random = new Random();
        //TODO: ANIMATION HEADER AND DISPLAY BODY
        Animation animation_in = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_head);
        relativeLayout_head.setAnimation(animation_in);
        callDisplay();

        Bundle bundle = getArguments();
        status = bundle.getInt(KEY_BATTERY_STATUS);


        //TODO: DECLARE ANIMATION STATUS BAR
        final Animation status_fade_out = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out_batt3);
        final Animation status_fade_in = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);

        //TODO: SOUND
        final MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.sound);

        batteryStatus = (ImageView) view.findViewById(R.id.battery_status);
        batteryStatus_blank = (ImageView) view.findViewById(R.id.battery_status_blank);

        if (status > 0) {
            switch (status) {
                case KEY_BATTERY_OK:
                case KEY_BATTERY_CHARGED:
                    if (status == KEY_BATTERY_OK) {
                        bottom_message.setVisibility(View.INVISIBLE);
                        bottom_diviner.setVisibility(View.INVISIBLE);
                        batteryStatus.setImageDrawable(getResources().getDrawable(R.mipmap.ok_bar, getActivity().getTheme()));

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mp.start();
                                batteryStatus.setAnimation(status_fade_out);
                                bottom_message.setAnimation(status_fade_in);
                                bottom_diviner.setAnimation(status_fade_in);
                                bottom_message.setVisibility(View.VISIBLE);
                                bottom_diviner.setVisibility(View.VISIBLE);
                            }
                        }, 11000);

                    } else {
                        bottom_message.setVisibility(View.INVISIBLE);
                        bottom_diviner.setVisibility(View.INVISIBLE);
                        batteryStatus.setImageDrawable(getResources().getDrawable(R.mipmap.chg_bar, getActivity().getTheme()));
                    }

                    batteryStatus.setVisibility(View.VISIBLE);



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
        dotBlink = (TextView) view.findViewById(R.id.dot_blink2);
//        if (hours >= 10) {
//            dotBlink = (TextView) view.findViewById(R.id.dot_blink2);
//        } else {
//            dotBlink = (TextView) view.findViewById(R.id.dot_blink1);
//        }
        dotBlink.setVisibility(View.VISIBLE);
        dotBlink.startAnimation(animation);


        //TODO: CURVE

        curveBar = (ImageView) view.findViewById(R.id.curve_bar);
        title = (TextView) view.findViewById(R.id.title);
        title.setText("Eco-drive Indicator");
        title.setTypeface(custom_font);
        displayHandler = new Handler();
        displayHandler.post(runnable);
        odoHandler = new Handler();
        odoHandler.post(runnable_odo);
    }

    public void callDisplay() {
        relativeLayout_display.setVisibility(View.VISIBLE);
        relativeLayout_bottom.setVisibility(View.VISIBLE);
        Animation animation_in_display = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_display);
        relativeLayout_display.setAnimation(animation_in_display);
        relativeLayout_bottom.setAnimation(animation_in_display);
    }


    @Override
    public void onStop() {
        super.onStop();

        displayHandler.removeCallbacks(runnable);
        odoHandler.removeCallbacks(runnable_odo);
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
            globe = (ImageView) view.findViewById(R.id.globe);
            //curveGauge = (CurveGauge) view.findViewById(R.id.curve_gauge);
            //subTitle = (TextView) view.findViewById(R.id.sub_title);
            Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MyriadPro-Regular.otf");
            title.setTypeface(custom_font);
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
