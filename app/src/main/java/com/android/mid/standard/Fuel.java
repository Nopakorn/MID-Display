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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.mid.R;
import com.android.mid.customizes.Gauge;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by nachanok.boo on 11/25/2015.
 */
public class Fuel extends Fragment {

    private TextView celText;
    private TextView tempText;
    private TextView dText;
    private TextView eText;
    private TextView fText;
    private TextView clockText;
    private TextView bottom_message;

    private RelativeLayout relativeLayout_head;
    private RelativeLayout relativeLayout_display;
    private RelativeLayout relativeLayout_bottom;
    private ImageView bg_ring;

    private TextView dotBlink;
    private Animation animation;
    private LinearLayout linearLayout;
    private LayoutInflater layoutInflater;
    private RelativeLayout relativeLayout;
    private GaugeItem gaugeItem1;
    private GaugeItem gaugeItem2;
    private Random random;
    private Handler displayHandler;
    private Handler odoHandler;

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
            int randomInt = random.nextInt(100 + 1);

            if (randomInt >= gaugeItem1.getProgress()) {
                value = ((gaugeItem1.getProgress() + 5) > 100) ? 100 : gaugeItem1.getProgress() + 5;
            } else {
                value = gaugeItem1.getProgress() - 1;
            }

            gaugeItem1.setProgress(value);

            randomInt = random.nextInt(100 + 1);

            if (randomInt >= gaugeItem2.getProgress()) {
                value = ((gaugeItem2.getProgress() + 5) > 100) ? 100 : gaugeItem2.getProgress() + 5;
            } else {
                value = gaugeItem2.getProgress() - 1;
            }

            gaugeItem2.setProgress(value);

            displayHandler.postDelayed(runnable, 100);
        }
    };

    public static Fuel newInstance() {
        return new Fuel();
    }

    public Fuel() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fuel, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_layout);
        relativeLayout_head = (RelativeLayout) view.findViewById(R.id.relativeLayout_head);
        relativeLayout_display = (RelativeLayout) view.findViewById(R.id.display);
        relativeLayout_bottom = (RelativeLayout) view.findViewById(R.id.display_bottom);

        bg_ring = (ImageView) view.findViewById(R.id.energy_bg);

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

        layoutInflater = LayoutInflater.from(getActivity());

        View gaugeView1 = layoutInflater.inflate(R.layout.item_gauge, null);
        gaugeItem1 = new GaugeItem(gaugeView1);
        gaugeItem1.setTitle("Fuel Consumption");
        gaugeItem1.setUnit("km/L");
        for (int i = 1; i <= 4; i++) {
            gaugeItem1.setInterval(i, ((i - 1) * 20));
        }

        View gaugeView2 = layoutInflater.inflate(R.layout.item_gauge, null);
        gaugeItem2 = new GaugeItem(gaugeView2);
        gaugeItem2.setTitle("Average Fuel Consumption");
        gaugeItem2.setUnit("km/L");
        for (int i = 1; i <= 4; i++) {
            gaugeItem2.setInterval(i, ((i - 1) * 20));
        }

        //View resetView = layoutInflater.inflate(R.layout.item_reset, null);

        linearLayout = (LinearLayout) view.findViewById(R.id.display_linear);
        linearLayout.addView(gaugeView1);
        linearLayout.addView(gaugeView2);

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

        random = new Random();
        displayHandler = new Handler();
        displayHandler.post(runnable);

        //TODO: ANIMATION HEADER AND DISPLAY BODY
        relativeLayout_display.setVisibility(View.INVISIBLE);
        relativeLayout_bottom.setVisibility(View.INVISIBLE);
        Animation animation_in = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_head);
        relativeLayout_head.setAnimation(animation_in);
        bg_ring.setAnimation(animation_in);
        callDisplay();

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
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
        relativeLayout.startAnimation(animation);
    }

    private class GaugeItem {
        private TextView title;
        private TextView unit;
        private TextView interval1;
        private TextView interval2;
        private TextView interval3;
        private TextView interval4;
        private Gauge gauge;
        private ImageView imageLevel;

        public GaugeItem(View view) {
            title = (TextView) view.findViewById(R.id.title);
            unit = (TextView) view.findViewById(R.id.unit);
            interval1 = (TextView) view.findViewById(R.id.interval1);
            interval2 = (TextView) view.findViewById(R.id.interval2);
            interval3 = (TextView) view.findViewById(R.id.interval3);
            interval4 = (TextView) view.findViewById(R.id.interval4);
            gauge = (Gauge) view.findViewById(R.id.gauge);

            Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MyriadPro-Regular.otf");
            title.setTypeface(custom_font);
            unit.setTypeface(custom_font);
            interval1.setTypeface(custom_font);
            interval2.setTypeface(custom_font);
            interval3.setTypeface(custom_font);
            interval4.setTypeface(custom_font);
        }

        public void setTitle(String text) {
            title.setText(text);
        }

        public void setUnit(String text) {
            unit.setText(text);
        }

        public void setInterval(int position, int value) {
            switch (position) {
                case 1:
                    interval1.setText(String.valueOf(value));

                    break;
                case 2:
                    interval2.setText(String.valueOf(value));

                    break;
                case 3:
                    interval3.setText(String.valueOf(value));

                    break;
                case 4:
                    interval4.setText(String.valueOf(value));

                    break;
                default:
                    interval1.setText(String.valueOf(0));
                    interval2.setText(String.valueOf(0));
                    interval3.setText(String.valueOf(0));
                    interval4.setText(String.valueOf(0));

                    break;
            }
        }

        public void setProgressLevel(){

        }

        public void setProgress(int value) {
            gauge.setProgress(value);
        }

        public int getProgress() {
            return gauge.getProgress();
        }
    }
}
