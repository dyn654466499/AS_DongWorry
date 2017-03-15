package com.dev.dongworry.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.dev.dongworry.R;
import com.dev.dongworry.customview.CityView;
import com.dev.dongworry.customview.CustomEditText;
import com.dev.dongworry.interfaces.CallBack;

/**
 * Created by dengyaoning on 17/3/14.
 */

public class CityActivity extends BaseActivity implements CallBack{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        setViewChangeListener(this);
        setTitle("城市列表");
        CustomEditText editText = (CustomEditText)findViewById(R.id.et_city_search);
        CityView cityView = (CityView)findViewById(R.id.cityView);
        cityView.setSearchEditText(editText);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onViewChange(Message msg) {

    }

    @Override
    public void onActivityResult(Object object) {
        Intent intent = new Intent();
        intent.putExtra("cityname",(String)object);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
}
