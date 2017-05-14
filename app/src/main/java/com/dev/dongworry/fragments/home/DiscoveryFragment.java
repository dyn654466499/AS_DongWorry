package com.dev.dongworry.fragments.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.dongworry.R;
import com.dev.dongworry.fragments.BaseFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

/**
 * 主页的发现tab
 */
public class DiscoveryFragment extends BaseFragment {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String[] mTitles;
    private int currentPosition = 0;
    public DiscoveryFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_discovery, null);
        mTitles = getResources().getStringArray(R.array.label_nav_discovery);
        mFragments.add(new HomeFragment());
        mFragments.add(new NearbyFragment());
        mFragments.add(new PostRewardFragment());
        mFragments.add(new RankingListFragment());
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
        final CommonTabLayout mTabLayout = (CommonTabLayout)rootView.findViewById(R.id.tabLayout_discovery);
        mTabLayout.setTabData(mTabEntities, getActivity(), R.id.ll_discovery_main, mFragments);
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
