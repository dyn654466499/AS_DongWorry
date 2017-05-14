package com.dev.dongworry.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.dongworry.R;
import com.dev.dongworry.fragments.dynamic.DynamicFragment;
import com.dev.dongworry.fragments.home.DiscoveryFragment;
import com.dev.dongworry.fragments.mine.MineFragment;
import com.dev.dongworry.managers.login.LoginManager;
import com.dev.dongworry.utils.PermissionUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.widget.MsgView;

import java.util.ArrayList;

import static com.dev.dongworry.R.id.tv_home_localCity;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private Context mContext;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String[] mTitles;
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
        mTitles = getResources().getStringArray(R.array.label_nav_main);
        Drawable drawable = getResources().getDrawable(R.drawable.icon_arrows_gray_down);
        drawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        TextView tv_home_localCity = (TextView)findViewById(R.id.tv_home_localCity);
        tv_home_localCity.setOnClickListener(this);
        tv_home_localCity.setCompoundDrawablesWithIntrinsicBounds(null,null,drawable,null);

        ImageButton imageBtn_home_search = (ImageButton)findViewById(R.id.imageBtn_home_search);
        imageBtn_home_search.setOnClickListener(this);

        mFragments.add(new MineFragment());
        mFragments.add(new DiscoveryFragment());
        mFragments.add(new DynamicFragment());
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
        final CommonTabLayout mTabLayout = (CommonTabLayout)findViewById(R.id.tabLayout_home);
        mTabLayout.setTabData(mTabEntities, this, R.id.ll_home_main, mFragments);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mTabLayout.setCurrentTab(1);

        int showDot_position = 1;
        mTabLayout.showDot(showDot_position);
        MsgView msgView = mTabLayout.getMsgView(showDot_position);
        msgView.setBackgroundColor(getResources().getColor(R.color.white));

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //禁止滑动
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if(drawer.getTag() instanceof Integer){
                    Intent intent;
                    switch ((int)drawer.getTag()){
                        case R.id.btn_drawer_login:
                            intent = new Intent(HomeActivity.this,LoginActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.llayout_drawer_setting:
                            intent = new Intent(HomeActivity.this,SettingActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.llayout_drawer_logout:

                            break;
                    }
                    drawer.setTag(null);
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button btn_drawer_login = (Button)navigationView.getHeaderView(0).findViewById(R.id.btn_drawer_login);
        btn_drawer_login.setOnClickListener(this);

        LinearLayout llayout_drawer_setting = (LinearLayout) navigationView.findViewById(R.id.llayout_drawer_setting);
        llayout_drawer_setting.setOnClickListener(this);

        LinearLayout llayout_drawer_logout = (LinearLayout) navigationView.findViewById(R.id.llayout_drawer_logout);
        llayout_drawer_logout.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_item_loose_change) {
            // Handle the camera action
        } else if (id == R.id.menu_item_recommend_money) {

        } else if (id == R.id.menu_item_my_auth) {

        } else if (id == R.id.menu_item_my_task) {

        } else if (id == R.id.menu_item_transaction_history) {

        } else if (id == R.id.menu_item_my_collection) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        switch (v.getId()){
            case tv_home_localCity:
                intent = new Intent(this, CityActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.imageBtn_home_search:
                intent = new Intent(this, SearchActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.btn_drawer_login:
                drawer.setTag(R.id.btn_drawer_login);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.llayout_drawer_setting:
                drawer.setTag(R.id.llayout_drawer_setting);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.llayout_drawer_logout:
                drawer.setTag(R.id.llayout_drawer_logout);
                drawer.closeDrawer(GravityCompat.START);
                break;
        }
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

    @Override
    public void onViewChange(Message msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
