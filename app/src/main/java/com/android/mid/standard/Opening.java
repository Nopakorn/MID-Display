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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.mid.R;

/**
 * Created by nachanok.boo on 11/23/2015.
 */
public class Opening extends Fragment {
    private ImageView imageView;
    private TextView textView;
    private Animation animation;
    private RelativeLayout relativeLayout;

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

        imageView = (ImageView) view.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);

        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        imageView.startAnimation(animation);

        textView = (TextView) view.findViewById(R.id.text_view);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setVisibility(View.VISIBLE);
                //textView.startAnimation(animation);
            }
        }, 3000);

        /*relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_ending);
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.float_in);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                relativeLayout.startAnimation(animation);
            }
        }, 6000);*/
    }
}
