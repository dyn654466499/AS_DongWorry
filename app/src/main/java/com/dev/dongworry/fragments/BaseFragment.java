package com.dev.dongworry.fragments;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.dev.dongworry.R;


public class BaseFragment extends Fragment {
    private Toast mToast;
    private int toast_position = Gravity.CENTER;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
    }

    /**
     * 提示框
     * @param str
     */
    public void showTip(final String str) {
        if (!TextUtils.isEmpty(str)) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mToast.setText(str);
                    mToast.setGravity(toast_position,0,0);
                    mToast.show();
                }
            });
        }
    }

    /**
     * 提示框
     * @param resId
     */
    public void showTip(@StringRes int resId) {
        final String str = getActivity().getResources().getString(resId);
        if (!TextUtils.isEmpty(str)) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mToast.setText(str);
                    mToast.setGravity(toast_position,0,0);
                    mToast.show();
                }
            });
        }
    }

    /**
     * 取消提示框
     */
    public void cancelTip() {
        if (mToast!=null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mToast.cancel();
                }
            });
        }
    }

    public int getColor(@ColorRes int resId){
        return getActivity().getResources().getColor(resId);
    }

    public Drawable getDrawable(@DrawableRes int resId){
        return getActivity().getResources().getDrawable(resId);
    }

}
