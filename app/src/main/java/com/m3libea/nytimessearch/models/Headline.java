
package com.m3libea.nytimessearch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Headline {

    @SerializedName("main")
    @Expose
    public String main;

    public String getMain() {
        return main;
    }

    public Headline() {
    }

    public void setMain(String main) {
        this.main = main;
    }

}
