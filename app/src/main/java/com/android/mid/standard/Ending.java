package com.android.mid.standard;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mid.R;

/**
 * Created by nachanok.boo on 11/24/2015.
 */
public class Ending extends Fragment {
    private ImageView imageView;
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

        imageView = (ImageView) view.findViewById(R.id.image_view);

        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.float_in);
        imageView.startAnimation(animation);

        textView = (TextView) view.findViewById(R.id.text_view);
        textView.startAnimation(animation);
    }
}
