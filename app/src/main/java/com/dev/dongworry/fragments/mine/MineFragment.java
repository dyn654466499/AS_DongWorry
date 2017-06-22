package com.dev.dongworry.fragments.mine;

/**
 * Created by dengyaoning on 17/5/10.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.dongworry.R;
import com.dev.dongworry.activities.SettingActivity;
import com.dev.dongworry.fragments.BaseFragment;
import com.dev.dongworry.fragments.home.HomeFragment;
import com.dev.dongworry.fragments.home.NearbyFragment;
import com.dev.dongworry.fragments.home.PostRewardFragment;
import com.dev.dongworry.fragments.home.RankingListFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

/**
 * 我的
 */
public class MineFragment extends BaseFragment {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String[] mTitles;
    private int currentPosition = 0;
    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mine, null);
        mTitles = getResources().getStringArray(R.array.label_nav_mine);
        mFragments.add(new UserCenterFragment());
        mFragments.add(new BaseFragment());
        mFragments.add(new OrderFragment());
        for (final String title : mTitles){
            mTabEntities.add(new CustomTabEntity() {
                @Override
                public String getTabTitle() {
                    return title;
                }

                @Override
                public int getTabSelectedIcon() {
                    return 0;
                }

                @Override
                public int getTabUnselectedIcon() {
                    return 0;
                }
            });
        }
        final CommonTabLayout mTabLayout = (CommonTabLayout)rootView.findViewById(R.id.tabLayout_mine);
        mTabLayout.setTabData(mTabEntities, getActivity(), R.id.ll_mine_main, mFragments);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                currentPosition = position;
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        return rootView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        try {
            if (mFragments != null && mFragments.size() > 0 && currentPosition < mFragments.size()) {
                mFragments.get(currentPosition).onHiddenChanged(hidden);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}