package com.leothosthoren.mynews.model;

/**
 * Created by Sofiane M. alias Leothos Thoren on 01/02/2018
 */
public class ItemNews {
    private String mTitle;
    private String mDate;
    private int mImage;
    private String mSummary;
    private int mColor;

    public ItemNews(String title, String date, int image, String summary, int color) {
        mTitle = title;
        mDate = date;
        mImage = image;
        mSummary = summary;
        mColor = color;
    }

    public String getTitle() {
        return mTitle;
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

    public int getColor() {
        return mColor;
    }
}
