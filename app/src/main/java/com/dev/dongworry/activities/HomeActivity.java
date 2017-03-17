package com.dev.dongworry.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.dev.dongworry.R;
import com.dev.dongworry.fragments.BaseFragment;
import com.dev.dongworry.fragments.HomeFragment;
import com.dev.dongworry.fragments.FlowDoctorFragment;
import com.dev.dongworry.fragments.NearbyFragment;
import com.dev.dongworry.fragments.UserCenterFragment;

public class HomeActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    private BottomNavigationBar navigationBar;
    private HomeFragment homeFragment;
    private FlowDoctorFragment flowDoctorFragment;
    private UserCenterFragment userCenterFragment;
    private NearbyFragment nearbyFragment;
    private Context mContext;
    /**
     * 退出时间记录，用于按两次返回键。
     */
    private long exitTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext = this;
        navigationBar = (BottomNavigationBar)findViewById(R.id.home_navi_bar);
        navigationBar.setMode(BottomNavigationBar.MODE_FIXED);//点击模式
        navigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        navigationBar.setTabSelectedListener(this);
        navigationBar.addItem(new BottomNavigationItem(R.drawable.abc_ic_menu_share_holo_light, "首页")).
                addItem(new BottomNavigationItem(R.drawable.navi_bus, "附近")).
                addItem(new BottomNavigationItem(R.drawable.navi_walk, "发布")).
                addItem(new BottomNavigationItem(R.drawable.navi_drive, "悬赏")).
                addItem(new BottomNavigationItem(R.drawable.navi_drive, "我的")).
                setActiveColor(R.color.themeColor).
                setFirstSelectedPosition(0).initialise();
        setDefaultFragment();
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        homeFragment = new HomeFragment();
        changeFragment(homeFragment);

    }

    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0:
                if(homeFragment == null){
                    homeFragment = new HomeFragment();
                }
                changeFragment(homeFragment);
                break;
            case 1:
                if(nearbyFragment == null){
                    nearbyFragment = new NearbyFragment();
                }
                changeFragment(nearbyFragment);
                break;
            case 2:

                break;
            case 3:
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
            case 4:
                if(userCenterFragment == null){
                    userCenterFragment = new UserCenterFragment();
                }
                changeFragment(userCenterFragment);
                break;
            default:
                break;
        }
    }

    private void changeFragment(BaseFragment baseFragment){
        FragmentManager fm = getFragmentManager();
        //开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.ll_home_main, baseFragment);
        // 事务提交
        transaction.commit();
    }


    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onViewChange(Message msg) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(mContext, R.string.toast_exit_app, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
