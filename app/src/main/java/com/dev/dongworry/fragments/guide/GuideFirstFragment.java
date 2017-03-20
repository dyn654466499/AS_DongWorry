package com.dev.dongworry.fragments.guide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.dongworry.R;
import com.dev.dongworry.fragments.BaseFragment;

public class GuideFirstFragment extends BaseFragment {


    public GuideFirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guide_first, container, false);
    }

}
