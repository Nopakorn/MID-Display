package com.android.mid.standard;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.mid.R;

import java.util.Calendar;

/**
 * Created by nachanok.boo on 11/26/2015.
 */
public class Warning extends Fragment {

    private RelativeLayout relativeLayout;
    private RelativeLayout relativeLayout_display;
    private RelativeLayout relativeLayout_head;

    private TextView dotBlink;
    private Animation animation;
    private TextView odometer;
    private int km = 2557;
    private Handler displayHandler;

    private TextView celText;
    private TextView tempText;
    private TextView dText;
    private TextView eText;
    private TextView fText;
    private TextView clockText;
    private TextView message;
    private TextView trip_aText;
    private TextView unitText;

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

        relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_layout);
        relativeLayout_display = (RelativeLayout) view.findViewById(R.id.display);
        relativeLayout_head = (RelativeLayout) view.findViewById(R.id.relativeLayout_head);

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

        //TODO: SET FONT FACE
        celText = (TextView) view.findViewById(R.id.cel_text);
        dText = (TextView) view.findViewById(R.id.center_text);
        eText = (TextView) view.findViewById(R.id.e_text);
        fText = (TextView) view.findViewById(R.id.f_text);
        tempText = (TextView) view.findViewById(R.id.temperature);
        clockText = (TextView) view.findViewById(R.id.text_clock);
        trip_aText = (TextView) view.findViewById(R.id.trip_a);
        message = (TextView) view.findViewById(R.id.message);
        unitText = (TextView) view.findViewById(R.id.unit);

        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MyriadPro-Regular.otf");
        celText.setTypeface(custom_font);
        dText.setTypeface(custom_font);
        eText.setTypeface(custom_font);
        fText.setTypeface(custom_font);
        tempText.setTypeface(custom_font);
        clockText.setTypeface(custom_font);
        trip_aText.setTypeface(custom_font);
        odometer.setTypeface(custom_font);
        message.setTypeface(custom_font);
        unitText.setTypeface(custom_font);

        displayHandler = new Handler();
        displayHandler.post(runnable);

        //TODO: ANIMATION HEADER AND DISPLAY BODY
        relativeLayout_display.setVisibility(View.INVISIBLE);
        Animation animation_in = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_head);
        relativeLayout_head.setAnimation(animation_in);
        callDisplay();
    }

    public void callDisplay() {
        relativeLayout_display.setVisibility(View.VISIBLE);
        Animation animation_in_display = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_display);
        relativeLayout_display.setAnimation(animation_in_display);
    }

    @Override
    public void onStop() {
        super.onStop();

        displayHandler.removeCallbacks(runnable);
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
        relativeLayout.startAnimation(animation);
    }
}
