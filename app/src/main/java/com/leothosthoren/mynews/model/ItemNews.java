package com.leothosthoren.mynews.model;

/**
 * Created by Sofiane M. alias Leothos Thoren on 01/02/2018
 */
public class ItemNews {
    private String mTitle;
    private String mDate;
    private int mImage;
    private String mSummary;


    public ItemNews(String title, String date, int image, String summary) {
        mTitle = title;
        mDate = date;
        mImage = image;
        mSummary = summary;

    }

    public String getTitle() {return mTitle;
    }

    public String getDate() {

        return mDate;
    }

    public int getImage() {
        return mImage;
    }

    public String getSummary() {
        return mSummary;
    }

}
