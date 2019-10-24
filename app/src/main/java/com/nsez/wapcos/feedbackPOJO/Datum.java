package com.nsez.wapcos.feedbackPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("mess")
    @Expose
    private String mess;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("type")
    @Expose
    private String type;

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
