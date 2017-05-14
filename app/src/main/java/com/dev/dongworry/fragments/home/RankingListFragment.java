package com.dev.dongworry.fragments.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.dongworry.R;
import com.dev.dongworry.fragments.BaseFragment;


/**
 * 排行榜页面.
 */
public class RankingListFragment extends BaseFragment {


    public RankingListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ranking_list, container, false);
    }

}
