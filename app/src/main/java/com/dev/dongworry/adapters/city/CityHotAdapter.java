package com.dev.dongworry.adapters.city;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.dev.dongworry.beans.city.City;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.dev.dongworry.R.string.update;

/**
 * Created by dengyaoning on 16/12/2.
 */
public class CityHotAdapter extends ArrayAdapter implements Filterable{
    private List<String> list;
    private List<String> originList;
    private List<City> cityList;

    public CityHotAdapter(Context context, int resource, List objects,List<City> cityList) {
        super(context, resource, objects);
        this.list = objects;
        this.cityList = cityList;
        originList = new ArrayList<>();
        originList.addAll(objects);
    }

    public void update(List list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                    update((ArrayList<String>) results.values);
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults result = new FilterResults();
                List<String> allJournals = new ArrayList<>();
                allJournals.addAll(originList);
                if (constraint == null || constraint.length() == 0) {
                    result.values = allJournals;
                    result.count = allJournals.size();
                } else {
                    List<String> filteredList = new ArrayList<>();
                    for (int i = 0; i < allJournals.size();i++) {
                        String j = allJournals.get(i);
                        City city = cityList.get(i);
                        if (j.contains(constraint) || city.getLetter().equalsIgnoreCase(constraint.toString()))
                            filteredList.add(j);
                    }
                    result.values = filteredList;
                    result.count = filteredList.size();
                }
                return result;
            }
        };
        return filter;
    }
}
