package com.leothosthoren.mynews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sofiane M. alias Leothos Thoren on 20/02/2018
 */
public class MostPopular {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("num_results")
    @Expose
    private Integer numResults;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MostPopular withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public MostPopular withCopyright(String copyright) {
        this.copyright = copyright;
        return this;
    }

    public Integer getNumResults() {
        return numResults;
    }

    public void setNumResults(Integer numResults) {
        this.numResults = numResults;
    }

    public MostPopular withNumResults(Integer numResults) {
        this.numResults = numResults;
        return this;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public MostPopular withResults(List<Result> results) {
        this.results = results;
        return this;
    }

    public class Result {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("adx_keywords")
        @Expose
        private String adxKeywords;
        @SerializedName("column")
        @Expose
        private String column;
        @SerializedName("section")
        @Expose
        private String section;
        @SerializedName("byline")
        @Expose
        private String byline;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("abstract")
        @Expose
        private String _abstract;
        @SerializedName("published_date")
        @Expose
        private String publishedDate;
        @SerializedName("source")
        @Expose
        private String source;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("asset_id")
        @Expose
        private Integer assetId;
        @SerializedName("views")
        @Expose
        private Integer views;
        @SerializedName("des_facet")
        @Expose
        private List<String> desFacet = null;
        @SerializedName("org_facet")
        @Expose
        private List<String> orgFacet = null;
        @SerializedName("per_facet")
        @Expose
        private String perFacet;
        @SerializedName("geo_facet")
        @Expose
        private String geoFacet;
        @SerializedName("media")
        @Expose
        private List<Medium> media = null;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Result withUrl(String url) {
            this.url = url;
            return this;
        }

        public String getAdxKeywords() {
            return adxKeywords;
        }

        public void setAdxKeywords(String adxKeywords) {
            this.adxKeywords = adxKeywords;
        }

        public Result withAdxKeywords(String adxKeywords) {
            this.adxKeywords = adxKeywords;
            return this;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public Result withColumn(String column) {
            this.column = column;
            return this;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public Result withSection(String section) {
            this.section = section;
            return this;
        }

        public String getByline() {
            return byline;
        }

        public void setByline(String byline) {
            this.byline = byline;
        }

        public Result withByline(String byline) {
            this.byline = byline;
            return this;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Result withType(String type) {
            this.type = type;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Result withTitle(String title) {
            this.title = title;
            return this;
        }

        public String getAbstract() {
            return _abstract;
        }

        public void setAbstract(String _abstract) {
            this._abstract = _abstract;
        }

        public Result withAbstract(String _abstract) {
            this._abstract = _abstract;
            return this;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public void setPublishedDate(String publishedDate) {
            this.publishedDate = publishedDate;
        }

        public Result withPublishedDate(String publishedDate) {
            this.publishedDate = publishedDate;
            return this;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public Result withSource(String source) {
            this.source = source;
            return this;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Result withId(Integer id) {
            this.id = id;
            return this;
        }

        public Integer getAssetId() {
            return assetId;
        }

        public void setAssetId(Integer assetId) {
            this.assetId = assetId;
        }

        public Result withAssetId(Integer assetId) {
            this.assetId = assetId;
            return this;
        }

        public Integer getViews() {
            return views;
        }

        public void setViews(Integer views) {
            this.views = views;
        }

        public Result withViews(Integer views) {
            this.views = views;
            return this;
        }

        public List<String> getDesFacet() {
            return desFacet;
        }

        public void setDesFacet(List<String> desFacet) {
            this.desFacet = desFacet;
        }

        public Result withDesFacet(List<String> desFacet) {
            this.desFacet = desFacet;
            return this;
        }

        public List<String> getOrgFacet() {
            return orgFacet;
        }

        public void setOrgFacet(List<String> orgFacet) {
            this.orgFacet = orgFacet;
        }

        public Result withOrgFacet(List<String> orgFacet) {
            this.orgFacet = orgFacet;
            return this;
        }

        public String getPerFacet() {
            return perFacet;
        }

        public void setPerFacet(String perFacet) {
            this.perFacet = perFacet;
        }

        public Result withPerFacet(String perFacet) {
            this.perFacet = perFacet;
            return this;
        }

        public String getGeoFacet() {
            return geoFacet;
        }

        public void setGeoFacet(String geoFacet) {
            this.geoFacet = geoFacet;
        }

        public Result withGeoFacet(String geoFacet) {
            this.geoFacet = geoFacet;
            return this;
        }

        public List<Medium> getMedia() {
            return media;
        }

        public void setMedia(List<Medium> media) {
            this.media = media;
        }

        public Result withMedia(List<Medium> media) {
            this.media = media;
            return this;
        }

    }

    public class Medium {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("subtype")
        @Expose
        private String subtype;
        @SerializedName("caption")
        @Expose
        private String caption;
        @SerializedName("copyright")
        @Expose
        private String copyright;
        @SerializedName("approved_for_syndication")
        @Expose
        private Integer approvedForSyndication;
        @SerializedName("media-metadata")
        @Expose
        private List<MediaMetadatum> mediaMetadata = null;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Medium withType(String type) {
            this.type = type;
            return this;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public Medium withSubtype(String subtype) {
            this.subtype = subtype;
            return this;
        }

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public Medium withCaption(String caption) {
            this.caption = caption;
            return this;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        public Medium withCopyright(String copyright) {
            this.copyright = copyright;
            return this;
        }

        public Integer getApprovedForSyndication() {
            return approvedForSyndication;
        }

        public void setApprovedForSyndication(Integer approvedForSyndication) {
            this.approvedForSyndication = approvedForSyndication;
        }

        public Medium withApprovedForSyndication(Integer approvedForSyndication) {
            this.approvedForSyndication = approvedForSyndication;
            return this;
        }

        public List<MediaMetadatum> getMediaMetadata() {
            return mediaMetadata;
        }

        public void setMediaMetadata(List<MediaMetadatum> mediaMetadata) {
            this.mediaMetadata = mediaMetadata;
        }

        public Medium withMediaMetadata(List<MediaMetadatum> mediaMetadata) {
            this.mediaMetadata = mediaMetadata;
            return this;
        }

    }

    public class MediaMetadatum {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("format")
        @Expose
        private String format;
        @SerializedName("height")
        @Expose
        private Integer height;
        @SerializedName("width")
        @Expose
        private Integer width;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public MediaMetadatum withUrl(String url) {
            this.url = url;
            return this;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public MediaMetadatum withFormat(String format) {
            this.format = format;
            return this;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public MediaMetadatum withHeight(Integer height) {
            this.height = height;
            return this;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public MediaMetadatum withWidth(Integer width) {
            this.width = width;
            return this;
        }

    }

}




