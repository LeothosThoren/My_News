
package com.leothosthoren.mynews.model.most.popular;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Medium {

    @SerializedName("url")
    @Expose
    private String url;

    public String getImgUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
