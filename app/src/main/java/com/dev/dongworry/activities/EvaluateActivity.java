package com.dev.dongworry.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dev.dongworry.R;
import com.dev.dongworry.utils.CommonUtils;


public class EvaluateActivity extends Activity {
	
	private RatingBar ratingBar_evaluate_whole;
	private TextView textView_evaluate_wholeNum,textView_evaluate_totalPrice,textView_evaluate_goodsNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_evaluate);

		textView_evaluate_wholeNum = (TextView)findViewById(R.id.textView_evaluate_wholeNum);
		textView_evaluate_totalPrice = (TextView)findViewById(R.id.textView_evaluate_totalPrice);
		textView_evaluate_goodsNum = (TextView)findViewById(R.id.textView_evaluate_goodsNum);

		/**
		 * 监听ratingBar，改变分数
		 */
		ratingBar_evaluate_whole = (RatingBar)findViewById(R.id.ratingBar_evaluate_whole);
		ratingBar_evaluate_whole.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
										boolean fromUser) {
				// TODO Auto-generated method stub
				textView_evaluate_wholeNum.setText(String.format(getString(R.string.score), String.valueOf(rating)));
			}
		});

		/**
		 * 显示并改变总价和数量的数字为红色。
		 */
		String totalPrice = String.format(getString(R.string.evaluate_totalPrice), "50");
		textView_evaluate_totalPrice.setText(CommonUtils.setFontType(totalPrice,3,totalPrice.length(), Color.RED,18));
		String evaluate_goodsNum = String.format(getString(R.string.evaluate_goodsNum), "1");
		textView_evaluate_goodsNum.setText(CommonUtils.setFontType(evaluate_goodsNum,3,evaluate_goodsNum.length(),Color.RED,18));
	}
}
