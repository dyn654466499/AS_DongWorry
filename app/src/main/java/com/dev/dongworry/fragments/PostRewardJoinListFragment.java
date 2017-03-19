package com.dev.dongworry.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.dongworry.R;
import com.dev.dongworry.activities.PostRewardJoinActivity;
import com.dev.dongworry.customview.CButton;

public class PostRewardJoinListFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_reward_join,null,false);
        CButton btn_reward_join = (CButton)view.findViewById(R.id.btn_reward_join);
        btn_reward_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PostRewardJoinActivity.class));
            }
        });
        return view;
    }
}
