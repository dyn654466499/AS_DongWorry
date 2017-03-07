package com.dev.dongworry.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dev.dongworry.views.GoodsView;

public class GoodsActivity extends Activity {

	GoodsView goodsView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		goodsView = new GoodsView(this);
	}

	public void clickCategoryView(View view){
		goodsView.clickCategoryView(view);
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		goodsView.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		goodsView.onActivityResult(requestCode, resultCode, data);
	}
	
	
}
