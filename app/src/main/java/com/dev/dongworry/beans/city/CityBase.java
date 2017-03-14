package com.dev.dongworry.beans.city;

import java.util.List;

/**
 * Created by dengyaoning on 17/3/14.
 */

public class CityBase {
    List<CityList> cityList;
    List<City> hotCity;

    public List<CityList> getCityList() {
        return cityList;
    }

    public List<City> getHotCity() {
        return hotCity;
    }
}
