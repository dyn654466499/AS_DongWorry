package com.dev.dongworry.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.dev.dongworry.interfaces.Commands;
import com.dev.dongworry.R;

/**
 * 对话框工具类，后续可扩展
 * @author 邓耀宁
 *
 */
public class DialogUtils {

	/**
	 * 通过命令模式设置Dialog按确定键后所执行的命令。
	 * @param context
	 * @param title
	 * @param message
	 * @param sureCommand 自定义命令（按确定键执行）
	 */
	public static void showDialog(Context context,String title, String message, final Commands sureCommand) {
		new AlertDialog.Builder(context)
				.setTitle(title)
				.setMessage(message)
				.setCancelable(false)
				.setPositiveButton(context.getString(R.string.sure),
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								if(sureCommand != null)sureCommand.executeCommand();
							}
						})
				.setNegativeButton(context.getString(R.string.cancel),
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						}).create().show();
	}

	/**
	 * 通过Dialog显示自定义View的内容
	 * @param context
	 * @param title
	 * @param message
	 * @param view
	 */
	public static void showDialog(Context context,String title, String message, View view) {
		AlertDialog dialog = new AlertDialog.Builder(context)
				.setTitle(title)
				.setMessage(message)
				.setView(view)
				.setCancelable(false)
				.create();
		dialog.show();
	}


}
