package com.dev.dongworry.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dev.dongworry.R;
import com.dev.dongworry.adapters.city.CityHotAdapter;
import com.dev.dongworry.adapters.city.CitySelectAdapter;
import com.dev.dongworry.beans.city.CityBase;
import com.dev.dongworry.beans.city.CityList;
import com.dev.dongworry.beans.city.City;
import com.dev.dongworry.interfaces.CallBack;
import com.dev.dongworry.interfaces.Commands;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dengyaoning on 16/12/5.
 */
public class CityView extends RelativeLayout{

    private Context mContext;
    /**
     * 城市列表视
     */
    private ListView lv_city;
    /**
     * 热门城市列表视
     */
    private GridView gv_hotCity;
    /**
     * 右边的字母sideBar
     */
    private CitySideBar sideBar;

    private CitySelectAdapter adapter_city;

    private CityHotAdapter adapter_hotCity;
    /**
     * 城市列表
     */
    private List<City> cityList;

    /**
     * 热门城市列表
     */
    private List<City> cityHotList;

    private RelativeLayout rootView;
    private LinearLayout llayout_hotCity_main;
    private TextView tv_local_city;

    private List<CityList> result;

    private LinearLayout llayout_hotCity;

    public CityView(final Context context) {
        super(context);
        init(context);
    }

    public CityView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CityView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private CallBack callBack;
    private void init(Context context){
        mContext = context;
        initViews();
        initData();
        tv_local_city.setText("定位中...");
        if(context instanceof CallBack) {
            callBack = (CallBack) context;
        }
    }

    private void initViews() {
        rootView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.view_city_select, this);
        llayout_hotCity_main = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.view_hot_city, null);
        llayout_hotCity = (LinearLayout) llayout_hotCity_main.findViewById(R.id.llayout_hotCity);

        lv_city = (ListView)rootView.findViewById(R.id.lv_city);
        gv_hotCity = (GridView) llayout_hotCity_main.findViewById(R.id.gv_hotCity);

        lv_city.addHeaderView(llayout_hotCity_main,null,false);

        tv_local_city = (TextView)llayout_hotCity_main.findViewById(R.id.tv_local_city);

        sideBar = (CitySideBar)rootView.findViewById(R.id.sideBar);
        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new CitySideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                if (adapter_city == null) {
                    return;
                }
                int position = adapter_city.getPositionForSection(s) + 1;
                lv_city.setSelection(position);
            }
        });
        lv_city.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int positionTemp = position - 1;
                if(positionTemp != -1) {
                    try {
                        String cityName = ((City) adapter_city.getItem(positionTemp)).getCityname();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        lv_city.setOnItemClickListener(itemClickListener);

        itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String cityName = (String)adapter_hotCity.getItem(position);
                    if(callBack != null){
                        callBack.onActivityResult(cityName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        gv_hotCity.setOnItemClickListener(itemClickListener);
    }

    private void initData() {
        cityList = new ArrayList<>();
        cityHotList = new ArrayList<>();
        handleData();
    }

    private void setAdapterData() {
        if(cityList == null || cityHotList == null){
            return;
        }

        ArrayList<String> cityHotNames = new ArrayList<>();
        for (int i = 0; i < cityHotList.size(); i++) {
            cityHotNames.add(cityHotList.get(i).getCityname());
        }
        if (adapter_hotCity == null) {
            adapter_hotCity = new CityHotAdapter(mContext, R.layout.adapter_hot_city, cityHotNames);
            gv_hotCity.setAdapter(adapter_hotCity);
        } else {
            adapter_hotCity.update(cityHotNames);
        }

        if(adapter_city == null){
            adapter_city = new CitySelectAdapter(mContext, cityList);
            lv_city.setAdapter(adapter_city);
        }else{
            adapter_city.update(cityList);
        }

    }

    private void handleData(){
        try{
            String data = getCityList();
            Gson gson = new Gson();
            CityBase cityBase = gson.fromJson(data, CityBase.class);
            result = cityBase.getCityList();
            cityHotList = cityBase.getHotCity();
            if (result != null && result.size() > 0) {
                ArrayList<String> sectionTitles = new ArrayList<>();
                for(CityList cityList : result) {
                    String pinyin = cityList.getPinyin();
                    sectionTitles.add(0,pinyin);
                    for (City city : cityList.getCityList()) {
                        city.setLetter(pinyin);
                        this.cityList.add(city);
                    }
                }
                Collections.sort(sectionTitles);
                sectionTitles.add(0,"热");
                String[] letters = new String[sectionTitles.size()];
                sideBar.setLetters(sectionTitles.toArray(letters));
            }
            setAdapterData();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void clearData(){
        cityList.clear();
        cityHotList.clear();
        if(llayout_hotCity != null) {
            llayout_hotCity.setVisibility(GONE);
        }
    }

    public String getCityList(){
        String result = "";
        try {
            InputStream in = getResources().openRawResource(R.raw.city);
            //获取文件的字节数
            int lenght = in.available();
            //创建byte数组
            byte[]  buffer = new byte[lenght];
            //将文件中的数据读到byte数组中
            in.read(buffer);
            result = new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
