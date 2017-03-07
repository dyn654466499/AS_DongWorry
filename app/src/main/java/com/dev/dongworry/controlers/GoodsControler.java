package com.dev.dongworry.controlers;

import android.app.Activity;

import com.dev.dongworry.models.GoodsModel;

public class GoodsControler extends BaseControler{

	public GoodsControler(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		modelDelegate = new GoodsModel(handler);
	}

}
