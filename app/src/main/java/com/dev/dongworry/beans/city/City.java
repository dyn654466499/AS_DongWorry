package com.dev.dongworry.beans.city;

/**
 * Created by dengyaoning on 16/12/2.
 */

public class City {

    private String cityID;

    private String engineLen;

    private String frameLen;

    private String isNeedDrivingLicense;

    private String isSupported;

    private String platePrefix;

    private String letter;

    private String cityname;

    private String pinyin;

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getCityname() {
        return cityname;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
