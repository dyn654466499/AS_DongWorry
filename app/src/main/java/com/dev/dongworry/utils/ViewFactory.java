package com.dev.dongworry.utils;

import android.content.Context;
import android.view.View;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.dev.dongworry.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dengyaoning on 17/4/7.
 */

public class ViewFactory {

    private ViewFactory(){

    }
    private static ViewFactory viewFactory;
    public static ViewFactory getInstance(){
        if(viewFactory == null){
            synchronized (ViewFactory.class){
                if(viewFactory == null){
                    viewFactory = new ViewFactory();
                }
            }
        }
        return viewFactory;
    }
     public Adapter getSimpleGridViewAdapter(Context context, String[] texts, int[] icons){
         // 新建List
         ArrayList<Map<String, Object>> data_list = new ArrayList<>();
         // 获取数据
         for (int i = 0; i < icons.length; i++) {
             Map<String, Object> map = new HashMap<>();
             map.put("iv_item_icon", icons[i]);
             map.put("tv_item_name", texts[i]);
             data_list.add(map);
         }
         String[] from = { "iv_item_icon",
                 "tv_item_name" };
         int[] to = { R.id.iv_item_icon,
                 R.id.tv_item_name};
         final SimpleAdapter adapter = new SimpleAdapter(context, data_list,
                 R.layout.item_simple_grid, from, to);
         return adapter;
     }
}
