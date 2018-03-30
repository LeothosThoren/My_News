
package com.leothosthoren.mynews.model.search.articles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchArticle {

    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}