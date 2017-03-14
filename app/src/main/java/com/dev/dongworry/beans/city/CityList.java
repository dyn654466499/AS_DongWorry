package com.dev.dongworry.beans.city;

import java.util.List;


/**
 * 城市列表
 * Created by dengyaoning420 on 2011/12/7.
 */
public class CityList {
        /** 城市列表 */

        List<City> cityList;

        String pinyin;

        public List<City> getCityList() {
            return cityList;
        }

        public void setCityList(List<City> cityList) {
            this.cityList = cityList;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
