package com.example.android.newsapp.model;

/**
 * Creating a custom class called News.
 * {@link News} represents a single platform release.
 * It contains the variable that the user read.
 */

public class News {

    // Initialize strings.
    private String sectionName;
    private String webTitle;
    private String webPublicationDate;
    private String webUrl;

    /**
     *
     * @param sectionName is the sectionName name for news.
     * @param webTitle is the webTitle of the news.
     * @param webPublicationDate is the publication's webPublicationDate of the news.
     * @param webUrl is the name's webUrl of the news.
     */
    public News(String sectionName, String webTitle, String webPublicationDate, String webUrl) {

        this.sectionName = sectionName;
        this.webTitle = webTitle;
        this.webPublicationDate = webPublicationDate;
        this.webUrl = webUrl;
    }

    /**
     *
     * @return the string of the sectionName name.
     */
    public String getSectionName() {
        return sectionName;
    }

    /**
     *
     * @return the string of the webTitle name.
     */
    public String getWebTitle() {
        return webTitle;
    }

    /**
     *
     * @return the long of the webPublicationDate
     */
    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    /**
     *
     * @return the string of the webUrl name.
     */
    public String getWebUrl() {
        return webUrl;
    }

    /** Return the string representation of the (@link News) object */
    @Override
    public String toString() {
        return "News{" +
                "sectionName='" + sectionName + '\'' +
                "webTitle='" + webTitle + '\'' +
                "webPublicationDate='" + webPublicationDate + '\'' +
                "webUrl='" + webUrl + '\'' +
                '}';
    }

}
