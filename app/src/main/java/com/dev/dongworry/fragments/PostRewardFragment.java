package com.dev.dongworry.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.dev.dongworry.R;


/**
 * 悬赏页面
 */
public class PostRewardFragment extends BaseFragment implements TabHost.OnTabChangeListener{
    private static final String TAB_PUBLISH = "我发起的";
    private static final String TAB_JOIN = "我参与的";

    private TabHost tabHost;

    private PostRewardJoinListFragment postRewardJoinListFragment;
    private PostRewardPublishListFragment postRewardPublishListFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_reward, container, false);
        //得到TabHost对象实例
        tabHost =(TabHost)view.findViewById(R.id.tabhost_post_reward);
        //调用 TabHost.setup()
        tabHost.setup();
        //创建Tab标签
        tabHost.addTab(tabHost.newTabSpec(TAB_PUBLISH).setIndicator(TAB_PUBLISH).setContent(R.id.tab_post_reward_1));
        tabHost.addTab(tabHost.newTabSpec(TAB_JOIN).setIndicator(TAB_JOIN).setContent(R.id.tab_post_reward_2));
        tabHost.setOnTabChangedListener(this);
        updateTab(tabHost,TAB_PUBLISH);
        return view;
    }

    private void updateTab(final TabHost tabHost, String tabId) {
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            View view = tabHost.getTabWidget().getChildAt(i);
            TextView tv = (TextView)view.findViewById(android.R.id.title);
            if (tabHost.getCurrentTab() == i) {
                //Tab被选中的时候标题的颜色
                tv.setTextColor(this.getResources().getColorStateList(R.color.themeColor));
                view.setBackgroundDrawable(getDrawable(R.drawable.tab_style_selected));
            } else {
                //Tab被不选中的时候标题的颜色
                tv.setTextColor(this.getResources().getColorStateList(android.R.color.black));
                view.setBackgroundDrawable(getDrawable(R.drawable.tab_style_unselected));
            }
        }
        switch (tabId){
            case TAB_PUBLISH:
                if(postRewardPublishListFragment == null){
                    postRewardPublishListFragment = new PostRewardPublishListFragment();
                }
                switchContent(curFragment, postRewardPublishListFragment,R.id.tab_post_reward_1);
                break;

            case TAB_JOIN:
                if(postRewardJoinListFragment == null){
                    postRewardJoinListFragment = new PostRewardJoinListFragment();
                }
                switchContent(curFragment, postRewardJoinListFragment,R.id.tab_post_reward_2);
                break;
        }
    }

    @Override
    public void onTabChanged(String tabId) {
        updateTab(tabHost,tabId);
    }
}
