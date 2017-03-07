package com.dev.dongworry.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.dev.dongworry.R;
import com.dev.dongworry.views.EvaluateView;




public class EvaluateActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new EvaluateView(this);
	}
}
