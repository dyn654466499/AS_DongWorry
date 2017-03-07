package com.dev.dongworry.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.dev.dongworry.R;
import com.dev.dongworry.views.GoodsDetailView;


public class GoodsDetailActivity extends Activity {
    private GoodsDetailView detailView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		detailView = new GoodsDetailView(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		detailView.onResume();
	}
	
	
}
