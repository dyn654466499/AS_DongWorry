package com.dev.dongworry.activities;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.dev.dongworry.R;

public class PostRewardJoinActivity extends BaseActivity implements TabHost.OnTabChangeListener{
    private TabHost tabHost;
    private static final String TAB_HOT = "最热";
    private static final String TAB_LATEST = "最新";
    private static final String TAB_POST_REWARD= "赏金";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_reward_join);
        setViewChangeListener(this);
        setTitle("悬赏活动");
        //得到TabHost对象实例
        tabHost =(TabHost)findViewById(R.id.tabhost_post_reward_join);
        //调用 TabHost.setup()
        tabHost.setup();
        //创建Tab标签
        tabHost.addTab(tabHost.newTabSpec(TAB_HOT).setIndicator(TAB_HOT).setContent(R.id.tab_post_reward_join_1));
        tabHost.addTab(tabHost.newTabSpec(TAB_LATEST).setIndicator(TAB_LATEST).setContent(R.id.tab_post_reward_join_2));
        tabHost.addTab(tabHost.newTabSpec(TAB_POST_REWARD).setIndicator(TAB_POST_REWARD).setContent(R.id.tab_post_reward_join_3));
        tabHost.setOnTabChangedListener(this);
        updateTab(tabHost,TAB_HOT);
    }

    private void updateTab(final TabHost tabHost, String tabId) {
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            View view = tabHost.getTabWidget().getChildAt(i);
            TextView tv = (TextView)view.findViewById(android.R.id.title);
            if (tabHost.getCurrentTab() == i) {
                //Tab被选中的时候标题的颜色
                tv.setTextColor(getResources().getColorStateList(R.color.themeColor));
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_style_selected));
            } else {
                //Tab被不选中的时候标题的颜色
                tv.setTextColor(getResources().getColorStateList(android.R.color.black));
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_style_unselected));
            }
        }
        switch (tabId){
            case TAB_HOT:

                break;

            case TAB_LATEST:

                break;

            case TAB_POST_REWARD:

                break;
        }
    }
    @Override
    public void onTabChanged(String tabId) {
        updateTab(tabHost,tabId);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onViewChange(Message msg) {

    }
}
