
package com.m3libea.nytimessearch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Legacy {

    @SerializedName("wide")
    @Expose
    public String wide;
    @SerializedName("wideheight")
    @Expose
    public String wideheight;
    @SerializedName("widewidth")
    @Expose
    public String widewidth;
    @SerializedName("xlargewidth")
    @Expose
    public String xlargewidth;
    @SerializedName("xlarge")
    @Expose
    public String xlarge;
    @SerializedName("xlargeheight")
    @Expose
    public String xlargeheight;
    @SerializedName("thumbnailheight")
    @Expose
    public String thumbnailheight;
    @SerializedName("thumbnail")
    @Expose
    public String thumbnail;
    @SerializedName("thumbnailwidth")
    @Expose
    public String thumbnailwidth;

    public String getWide() {
        return wide;
    }

    public void setWide(String wide) {
        this.wide = wide;
    }

    public String getWideheight() {
        return wideheight;
    }

    public void setWideheight(String wideheight) {
        this.wideheight = wideheight;
    }

    public String getWidewidth() {
        return widewidth;
    }

    public void setWidewidth(String widewidth) {
        this.widewidth = widewidth;
    }

    public String getXlargewidth() {
        return xlargewidth;
    }

    public void setXlargewidth(String xlargewidth) {
        this.xlargewidth = xlargewidth;
    }

    public String getXlarge() {
        return xlarge;
    }

    public void setXlarge(String xlarge) {
        this.xlarge = xlarge;
    }

    public String getXlargeheight() {
        return xlargeheight;
    }

    public void setXlargeheight(String xlargeheight) {
        this.xlargeheight = xlargeheight;
    }

    public String getThumbnailheight() {
        return thumbnailheight;
    }

    public void setThumbnailheight(String thumbnailheight) {
        this.thumbnailheight = thumbnailheight;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnailwidth() {
        return thumbnailwidth;
    }

    public void setThumbnailwidth(String thumbnailwidth) {
        this.thumbnailwidth = thumbnailwidth;
    }

    public Legacy() {
    }
}
