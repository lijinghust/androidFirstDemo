package com.coolweahter.android.gson;

import com.google.gson.annotations.SerializedName;

public class Now {
    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond_txt")
    public String more;
//    @SerializedName("cond")
//    public More more;
//
//    public class More{
//        @SerializedName("txt")
//        public String info;
//    }
}
