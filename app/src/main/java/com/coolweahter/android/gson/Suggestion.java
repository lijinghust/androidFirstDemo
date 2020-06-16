package com.coolweahter.android.gson;

import com.google.gson.annotations.SerializedName;

public class Suggestion {
    @SerializedName("cw")
    public CarWash carWash;

    @SerializedName("comf")
    public Comfort comfort;

    public Sport sport;

    public class CarWash{
        @SerializedName("txt")
        public String info;
    }
    public class Comfort{
        @SerializedName("txt")
        public String info;
    }
    public class Sport{
        @SerializedName("txt")
        public String info;
    }
}
