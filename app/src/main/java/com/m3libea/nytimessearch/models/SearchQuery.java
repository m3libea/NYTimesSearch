package com.m3libea.nytimessearch.models;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by m3libea on 9/20/17.
 */

public class SearchQuery {


    private enum SortDir{
        NEWEST,
        OLDEST
    }


    private String query = null;
    private Date beginDate = null;
    private SortDir sort = null;
    private int page = 0;
    private List<String> newsDesks = null;

    public SearchQuery(String query, Date beginDate, SortDir sort, int page, List<String> newsDesks) {
        this.query = query;
        this.beginDate = beginDate;
        this.sort = sort;
        this.page = page;
        this.newsDesks = newsDesks;
    }

    public SearchQuery() {
    }

    public SearchQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public int getPage() {
        return page;
    }

    public List<String> getNewsDesks() {
        return newsDesks;
    }

    public SortDir getSort() {
        return sort;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public void setSort(String sort) {

        switch(sort){
            case "Newest":
                this.sort = SortDir.NEWEST;
            case "Oldest":
                this.sort = SortDir.OLDEST;
        }
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setNewsDesks(List<String> newsDesks) {
        this.newsDesks = newsDesks;
    }

    public String getFormattedDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return beginDate == null ? null : df.format(beginDate);
    }

    public String getFormattedSort(){
        String s = null;

        if (sort != null) {
            switch (sort){
                case NEWEST:
                    s = "newest";
                    break;
                case OLDEST:
                    s = "oldest";
                    break;
            }
        }

        return s;
    }

    public String getFormattedDesks(){

        if (newsDesks == null) {
            return null;
        }

        List<String> aux = new ArrayList<>();

        for (String s: newsDesks){
            aux.add("\"" + s + "\"");
        }

        StringBuffer sb = new StringBuffer();
        sb.append("new_desk:(");
        sb.append(TextUtils.join(" ", aux));
        sb.append(")");
        return sb.toString();
    }

}
