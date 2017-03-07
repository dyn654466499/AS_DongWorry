package com.dev.dongworry.controlers;

import android.app.Activity;
import com.dev.dongworry.models.GoodsDetailModel;

public class GoodsDetailControler extends BaseControler{

	public GoodsDetailControler(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		modelDelegate = new GoodsDetailModel(handler);
	}

}
