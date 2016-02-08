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
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.mid.R;

/**
 * Created by nachanok.boo on 11/23/2015.
 */
public class Opening extends Fragment {

    private RelativeLayout relativeLayout;
    private ImageView imageView;
    private TextView textView;
    private ProgressBar blooming;
    private Animation animation;
    private Handler displayHandler;

    public static Opening newInstance() {
        return new Opening();
    }

    public Opening() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.opening, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_layout);

        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.blooming_zoomin);
        blooming = (ProgressBar) view.findViewById(R.id.blooming);
        blooming.setVisibility(View.VISIBLE);
        //blooming.setProgress();
        blooming.startAnimation(animation);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                blooming.setVisibility(View.GONE);
                //imageView.setVisibility(View.VISIBLE);
            }
        }, 3000);



        textView = (TextView) view.findViewById(R.id.text_view);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MyriadPro-Regular.otf");
        textView.setTypeface(custom_font);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
                textView.setVisibility(View.VISIBLE);
                textView.startAnimation(animation);
            }
        }, 1500);

    }

    @Override
    public void onStop() {
        super.onStop();
        //displayHandler.removeCallbacks(runable);
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
        relativeLayout.startAnimation(animation);
    }
}
