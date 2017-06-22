package com.dev.dongworry.fragments.mine;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.dongworry.R;
import com.dev.dongworry.customview.RefreshLayout;
import com.dev.dongworry.fragments.BaseFragment;

/**
 * Created by dengyaoning on 17/5/15.
 */

public class OrderListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        RefreshLayout.OnLoadListener{
    private String requestKey;
    private boolean isRefresh = false;//是否刷新中

    public OrderListFragment(String requestKey) {
        this.requestKey = requestKey;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_orderlist, null);

        return rootView;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoad() {

    }
}
