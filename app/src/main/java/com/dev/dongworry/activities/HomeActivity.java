package com.dev.dongworry.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.dev.dongworry.R;
import com.dev.dongworry.fragments.BaseFragment;
import com.dev.dongworry.fragments.CountryFragment;
import com.dev.dongworry.fragments.FlowDoctorFragment;
import com.dev.dongworry.fragments.UserCenterFragment;

public class HomeActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    private BottomNavigationBar navigationBar;
    private CountryFragment countryFragment;
    private FlowDoctorFragment flowDoctorFragment;
    private UserCenterFragment userCenterFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationBar = (BottomNavigationBar)findViewById(R.id.home_navi_bar);
        navigationBar.setMode(BottomNavigationBar.MODE_DEFAULT);//点击模式
        navigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        navigationBar.setTabSelectedListener(this);
        navigationBar.addItem(new BottomNavigationItem(R.drawable.abc_ic_menu_share_holo_light, "机构")).
                addItem(new BottomNavigationItem(R.drawable.navi_bus, "专家")).
                addItem(new BottomNavigationItem(R.drawable.navi_drive, "中介")).
                setActiveColor(R.color.blue).
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
        countryFragment = new CountryFragment();
        changeFragment(countryFragment);

    }

    @Override
    public void onTabSelected(int position) {

        switch (position) {
            case 0:
                if(countryFragment == null){
                    countryFragment = new CountryFragment();
                }
                changeFragment(countryFragment);
                break;
            case 1:
                if(flowDoctorFragment == null){
                    flowDoctorFragment = new FlowDoctorFragment();
                }
                changeFragment(flowDoctorFragment);
                break;
            case 2:
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

}
