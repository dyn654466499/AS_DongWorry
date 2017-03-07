package com.dev.dongworry.views;

import android.app.Activity;
import android.graphics.Color;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.dev.dongworry.R;
import com.dev.dongworry.adapters.CommentAdapter;
import com.dev.dongworry.controlers.BaseControler;
import com.dev.dongworry.controlers.GoodsControler;
import com.dev.dongworry.interfaces.Commands;
import com.dev.dongworry.utils.CommonUtils;
import com.dev.dongworry.utils.DialogUtils;

public class GoodsDetailView extends BaseView implements
		BaseControler.ViewChangeListener {
	private GoodsControler controler;
	private TextView textView_goods_detail_evaluates,
			textView_goods_detail_title_price,
			textView_goods_detail_params_operators,
			textView_goods_detail_params_trafficType,
			textView_goods_detail_params_validTime,
			textView_goods_detail_params_trafficAmount,
			textView_goods_detail_params_trafficStandar,
			textView_goods_detail_params_validArea;
	private boolean flag = false;

	public GoodsDetailView(Activity mActivity) {
		super(mActivity);
		// TODO Auto-generated constructor stub
		controler = new GoodsControler(mActivity);
		controler.setViewChangeListener(this);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mActivity.setContentView(R.layout.activity_goods_detail);
		ListView listView = (ListView) mActivity
				.findViewById(R.id.listView_comment_goods_detail);
		CommentAdapter adapter = new CommentAdapter(mActivity);
		listView.setAdapter(adapter);

		textView_goods_detail_evaluates = (TextView) mActivity
				.findViewById(R.id.textView_goods_detail_evaluates);
		textView_goods_detail_evaluates.setText(String.format(
				getString(R.string.goodsEvaluates), "500"));
		textView_goods_detail_evaluates
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						DialogUtils.showDialog(mActivity, "成功跳转到全部用户评论界面", "",
								new Commands() {

									@Override
									public void executeCommand() {
										// TODO Auto-generated method stub

									}
								});
					}
				});


		/**
		 * 显示并改变商品参数的字体颜色为黑色。
		 */
		textView_goods_detail_title_price = (TextView) mActivity
				.findViewById(R.id.textView_goods_detail_title_price);
		textView_goods_detail_params_operators = (TextView) mActivity
				.findViewById(R.id.textView_goods_detail_params_operators);
		textView_goods_detail_params_trafficType = (TextView) mActivity
				.findViewById(R.id.textView_goods_detail_params_trafficType);
		textView_goods_detail_params_validTime = (TextView) mActivity
				.findViewById(R.id.textView_goods_detail_params_validTime);
		textView_goods_detail_params_trafficAmount = (TextView) mActivity
				.findViewById(R.id.textView_goods_detail_params_trafficAmount);
		textView_goods_detail_params_trafficStandar = (TextView) mActivity
				.findViewById(R.id.textView_goods_detail_params_trafficStandar);
		textView_goods_detail_params_validArea = (TextView) mActivity
				.findViewById(R.id.textView_goods_detail_params_validArea);

		String temp = String.format(getString(R.string.operators), "中国移动");
		textView_goods_detail_params_operators.setText(CommonUtils.setFontType(
				temp, 4, temp.length(), Color.BLACK, 16));
		temp = String.format(getString(R.string.trafficType), "闲时流量");
		textView_goods_detail_params_trafficType.setText(CommonUtils
				.setFontType(temp, 5, temp.length(), Color.BLACK, 16));
		temp = String.format(getString(R.string.validTime),"当天有效");
		textView_goods_detail_params_validTime.setText(CommonUtils.setFontType(
				temp, 5, temp.length(), Color.BLACK, 16));
		temp = String.format(getString(R.string.trafficAmount), "500M");
		textView_goods_detail_params_trafficAmount.setText(CommonUtils
				.setFontType(temp, 4, temp.length(), Color.BLACK, 16));
		temp = String.format(getString(R.string.trafficStandar), "2G/3G/4G");
		textView_goods_detail_params_trafficStandar.setText(CommonUtils
				.setFontType(temp, 5, temp.length(), Color.BLACK, 16));
		temp = String.format(getString(R.string.validArea), "中国");
		textView_goods_detail_params_validArea.setText(CommonUtils.setFontType(
				temp, 5, temp.length(), Color.BLACK, 16));
	}

	@Override
	public void onViewChange(Message msg) {
		// TODO Auto-generated method stub

	}

	public void onResume() {

	}
}
