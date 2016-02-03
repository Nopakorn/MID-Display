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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.mid.R;

/**
 * Created by nachanok.boo on 11/24/2015.
 */
public class Ending extends Fragment {
    private RelativeLayout relativeLayout;
    private ImageView imageView;
    private ImageView imageIcon;
    private TextView textView;
    private Animation animation;

    public static Ending newInstance() {
        return new Ending();
    }

    public Ending() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.ending, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_layout);

        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        imageView = (ImageView) view.findViewById(R.id.image_view);
        imageView.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_delay);
        imageIcon = (ImageView) view.findViewById(R.id.image_icon);
        imageIcon.setVisibility(View.VISIBLE);
        imageIcon.startAnimation(animation);

        textView = (TextView) view.findViewById(R.id.text_view);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MyriadPro-Regular.otf");
        textView.setTypeface(custom_font);
        textView.setVisibility(View.VISIBLE);
        textView.startAnimation(animation);

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, 3000);*/
    }

    @Override
    public void onStop() {
        super.onStop();
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
        relativeLayout.startAnimation(animation);
    }
}
