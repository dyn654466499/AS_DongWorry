package com.dev.dongworry.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.dev.dongworry.R;
import com.dev.dongworry.fragments.HomeFragment;
import com.dev.dongworry.fragments.NearbyFragment;
import com.dev.dongworry.fragments.PostRewardFragment;
import com.dev.dongworry.fragments.PublishFragment;
import com.dev.dongworry.fragments.UserCenterFragment;
import com.dev.dongworry.utils.PermissionUtils;

public class HomeActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    private BottomNavigationBar navigationBar;
    private HomeFragment homeFragment;
    private PublishFragment publishFragment;
    private PostRewardFragment postRewardFragment;
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
//        PermissionUtils.requestPermission(this, PermissionUtils.CODE_RECORD_AUDIO, mPermissionGrant);
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
        navigationBar.selectTab(0);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0:
                if(homeFragment == null){
                    homeFragment = new HomeFragment();
                }
                switchFragment(curFragment,homeFragment,R.id.ll_home_main);
                break;
            case 1:
                if(nearbyFragment == null){
                    nearbyFragment = new NearbyFragment();
                }
                switchFragment(curFragment,nearbyFragment,R.id.ll_home_main);
                break;
            case 2:
                if(publishFragment == null){
                    publishFragment = new PublishFragment();
                }
                switchFragment(curFragment,publishFragment,R.id.ll_home_main);
                break;
            case 3:
                if(postRewardFragment == null){
                    postRewardFragment = new PostRewardFragment();
                }
                switchFragment(curFragment,postRewardFragment,R.id.ll_home_main);
                break;
            case 4:
                if(userCenterFragment == null){
                    userCenterFragment = new UserCenterFragment();
                }
                switchFragment(curFragment,userCenterFragment,R.id.ll_home_main);
                break;
            default:
                break;
        }
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

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_RECORD_AUDIO:
//					Toast.makeText(mContext, "Result Permission Grant CODE_RECORD_AUDIO", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_GET_ACCOUNTS:
//					Toast.makeText(mContext, "Result Permission Grant CODE_GET_ACCOUNTS", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_PHONE_STATE:
//					Toast.makeText(mContext, "Result Permission Grant CODE_READ_PHONE_STATE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_CALL_PHONE:
//					Toast.makeText(mContext, "Result Permission Grant CODE_CALL_PHONE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_CAMERA:
//					Toast.makeText(mContext, "Result Permission Grant CODE_CAMERA", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
//					Toast.makeText(mContext, "Result Permission Grant CODE_ACCESS_FINE_LOCATION", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
//					Toast.makeText(mContext, "Result Permission Grant CODE_ACCESS_COARSE_LOCATION", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_WRITE_SETTINGS:
                    break;
                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
                    break;
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
//					Toast.makeText(mContext, "Result Permission Grant CODE_WRITE_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_MULTI_PERMISSION:
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);
    }


}
