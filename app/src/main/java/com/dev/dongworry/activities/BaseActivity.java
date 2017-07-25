package com.dev.dongworry.activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.dongworry.R;
import com.dev.dongworry.customview.LoadingDialog;
import com.dev.dongworry.fragments.BaseFragment;
import com.dev.dongworry.interfaces.ViewChangeListener;
import com.dev.dongworry.models.BaseModel;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 该Activity作为controler的抽象类，负责向model转发view的业务逻辑计算请求，并通知view改变其状态。
 * @author  邓耀宁
 *
 */
public abstract class BaseActivity extends FragmentActivity implements OnClickListener,ViewChangeListener{
    /**
     * model的代理对象，需要相应的子类设置它（多态）
     */
    private BaseModel modelDelegate;
    /**
     * 与UI线程通信的handler
     */
    protected Handler handler;
    /**
     * view状态监听者
     */
    protected ViewChangeListener viewChangeListener;
    /**
     * 使用线程池
     */
    private ThreadPoolExecutor executor;
    
    protected boolean isActive = false;
    
    private Toast mToast;
    
    /**
     * 设置Model代理
     * @param modelDelegate
     */
	public void setModelDelegate(BaseModel modelDelegate) {
		this.modelDelegate = modelDelegate;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (executor == null)
			executor = new ThreadPoolExecutor(2, 5, 3000, TimeUnit.SECONDS,
					new LinkedBlockingQueue<Runnable>(3),
					new ThreadPoolExecutor.DiscardOldestPolicy());

		handler = new Handler(getMainLooper()) {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if(viewChangeListener != null){
					viewChangeListener.onViewChange(msg);
				}
			}
		};
		mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
	}

	public void setTitle(String title){
		TextView tv_common_title = (TextView)findViewById(R.id.tv_common_title);
		if(tv_common_title != null){
			tv_common_title.setText(title);
		}
	}
	    /**
	     * 提示框
	     * @param str
	     */
		public void showTip(final String str) {
			if (!TextUtils.isEmpty(str)) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mToast.setText(str);
						mToast.setGravity(Gravity.CENTER,0,0);
						mToast.show();
					}
				});
			}
		}

		/**
		 * 取消提示框
		 */
		public void cancelShowTip() {
			if (mToast!=null) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mToast.cancel();
					}
				});
			}
		}
		
	/**
	 * 设置view状态监听者
	 * @param activity
	 */
    public void setViewChangeListener(final Activity activity) {
		if(activity instanceof ViewChangeListener) {
			this.viewChangeListener = (ViewChangeListener)activity;
		}
		ImageButton imageBtn_back = (ImageButton)activity.findViewById(R.id.imageBtn_left);
		if(imageBtn_back != null){
			imageBtn_back.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					activity.finish();
				}
			});
		}
	}

    /**
     * 需设置model代理后，才能给给model转发请求，通过封装Message传递参数
     * @param changeStateMessage
     */
	public void notifyModel(final Message changeStateMessage) {
		// TODO Auto-generated method stub
		/**
		 * 交付给子线程做业务逻辑计算
		 */
		if(modelDelegate != null) {
			executor.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					modelDelegate.changeModelState(changeStateMessage);
				}

			});
		}
	}
	/**
	 * 需设置model代理后，才能给model转发请求
	 * @param changeState
	 */
	public void notifyModel(final int changeState) {
		// TODO Auto-generated method stub
		/**
		 * 交付给子线程做业务逻辑计算
		 */
		if(modelDelegate != null) {
			executor.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					modelDelegate.changeModelState(changeState);
				}
			});
		}
	}
	
	/**
	 * 获取当前类名
	 * @return 类名
	 */
    protected String getTAG(){
 	   return this.getClass().getSimpleName();
    }
    
    @Override
    protected void onStop() {
            // TODO Auto-generated method stub
            super.onStop();
            if (!isAppOnForeground()) {
                    //app 进入后台
                    isActive = false ;//记录当前已经进入后台
            }
    }

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!isActive) {
			// app 从后台唤醒，进入前台
			isActive = true;
		}
	}

    /**
     * 程序是否在前台运行
     * 
     * @return
     */
    public boolean isAppOnForeground() {
            // Returns a list of application processes that are running on the
            // device
            ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            String packageName = getApplicationContext().getPackageName();

            List<RunningAppProcessInfo> appProcesses = activityManager
                            .getRunningAppProcesses();
            if (appProcesses == null)
                    return false;

            for (RunningAppProcessInfo appProcess : appProcesses) {
                    // The name of the process that this object is associated with.
                    if (appProcess.processName.equals(packageName)
                                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                            return true;
                    }
            }
            return false;
    }

	protected BaseFragment curFragment = new BaseFragment();
	public void switchFragment(BaseFragment from, BaseFragment to, int resId) {
		if (curFragment != to) {
			curFragment = to;
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
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
			mLoadingDialog = new LoadingDialog(this);
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
