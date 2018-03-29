package com.leothosthoren.mynews.model;

/**
 * Created by Sofiane M. alias Leothos Thoren on 29/03/2018
 */
public class ArticleManager {

  private String section;
  private String title;
  private String date;
  private String imageUri;
  private String articleUri;

    public String getSection (){
        return section;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getArticleUri() {
        return articleUri;
    }

    public void manageTopoStories (TopStories.Resultum resultum){
        resultum.getSection();
        resultum.getPublishedDate();

    }
}
