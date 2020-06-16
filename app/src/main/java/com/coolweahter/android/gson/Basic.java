package com.coolweahter.android.gson;

import com.google.gson.annotations.SerializedName;

public class Basic {
    @SerializedName("location")
    public String cityName;

    @SerializedName("cid")
    public String weatherId;

//    public Update update;
//
//    public class Update{
//        @SerializedName("update")
//        public String updateTime;
//    }
}
