package com.dev.dongworry.customview;

/**
 * Created by dengyaoning on 17/6/18.
 */

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.dongworry.R;

/**
 * 标准控件之加载中对话框
 *
 * @author guhaoxin
 * @version 1.0.0
 * @date 2016.02.25
 */
public class LoadingDialog extends AlertDialog {
    private static final String TAG = LoadingDialog.class.getSimpleName();
    Activity mParentActivity;

    /**
     * 构造函数
     *
     * @param context
     * @param isModal 是否模态
     */
    public LoadingDialog(Context context, boolean isModal) {
        super(context);
        mParentActivity = (Activity)context;
        setCanceledOnTouchOutside(!isModal);
        setCancelable(false);  // 默认不支持撤销
    }

    public LoadingDialog(Context context) {
        this(context, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMessage("正在加载...");
    }

    public void setLoadingText(String text){
        setMessage(text);
    }

    @Override
    public void setOnShowListener(OnShowListener listener) {
        super.setOnShowListener(listener);
    }

    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        super.setOnDismissListener(listener);
    }

    @Override
    public void setOnCancelListener(OnCancelListener listener) {
        super.setOnCancelListener(listener);
    }
}


