package com.dev.dongworry.adapters.city;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengyaoning on 16/12/2.
 */
public class CityHotAdapter extends ArrayAdapter {
    private List<String> list = null;
    private List<String> originList = null;

    public CityHotAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        list = objects;
        originList = new ArrayList<>();
        originList.addAll(objects);
    }

    public void update(List list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

}
