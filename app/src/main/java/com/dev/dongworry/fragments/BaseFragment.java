package com.dev.dongworry.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.dev.dongworry.customview.LoadingDialog;


public class BaseFragment extends Fragment {
    private Toast mToast;
    private int toast_position = Gravity.CENTER;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
        curFragment = new BaseFragment();
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

    protected BaseFragment curFragment;
    public void switchContent(BaseFragment from, BaseFragment to, int resId) {
        if (curFragment != to) {
            curFragment = to;
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(resId, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    /**
     * 标准控件——加载对话框
     */
    protected LoadingDialog mLoadingDialog;

    protected void showLoadingDialog() {
        showLoadingDialog(null);
    }
    /**
     * 显示加载对话框
     * @param message 默认"正在加载..."
     */
    protected void showLoadingDialog(String message) {
        if(mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getActivity());
            mLoadingDialog.setCanceledOnTouchOutside(false);
            if (!TextUtils.isEmpty(message))
                mLoadingDialog.setLoadingText(message);
            mLoadingDialog.show();
        }
    }
    /**
     * 使加载对话框消失
     */
    protected void dismissLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.cancel();
            mLoadingDialog = null;
        }
    }
}
