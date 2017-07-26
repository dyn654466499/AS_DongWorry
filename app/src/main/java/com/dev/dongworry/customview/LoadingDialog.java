package com.dev.dongworry.customview;

/**
 * Created by dengyaoning on 17/6/18.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.dev.dongworry.R;

/**
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
        super(context, R.style.loadingDialog);
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
        setContentView(R.layout.layout_dialog_progress);
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


