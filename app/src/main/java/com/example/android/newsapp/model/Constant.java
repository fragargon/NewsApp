package com.example.android.newsapp.model;

/**
 * Store Constants for the NewsFeed app.
 */

public class Constant {

    /**
     * Create a private constructor because no one should ever create a {@link Constant} object.
     */
    private Constant() {
    }

    // Constant value for the earthquake loader ID.
    public static final int NEWS_LOADER_ID = 1;

    // Http request params.
    public static final int READ_TIMEOUT = 10000;
    public static final int CONNECT_TIMEOUT = 15000;
    public static final int RESPONSE_CODE = 200;
    public static final String REQUEST_METHOD = "GET";

    // Guardian Api end point
    public static final String BASE_URL = "http://content.guardianapis.com/search?";

    // Query Uri builder
    public static final String KEY_SHOW_TAGS = "show-tags";
    public static final String KEY_CONTRIBUTOR = "contributor";
    public static final String KEY_ORDER_BY = "order-by";
    public static final String KEY_NEWEST = "newest";
    public static final String KEY_SHOW_FIELD = "show-fields";
    public static final String KEY_ALL = "all";
    public static final String KEY_PAGE_SIZE = "page-size";
    public static final String API_KEY = "api-key";
    public static final String KEY_TEST = "test";

    // Extract the key associated with the JSONObject
    public static final String JSON_KEY_RESPONSE = "response";
    public static final String JSON_KEY_RESULTS = "results";
    public static final String JSON_KEY_WEB_TITLE = "webTitle";
    public static final String JSON_KEY_SECTION_NAME = "sectionName";
    public static final String JSON_KEY_WEB_PUBLICATION_DATE = "webPublicationDate";
    public static final String JSON_KEY_WEB_URL = "webUrl";
    public static final String JSON_KEY_TAGS = "tags";
    public static final String JSON_KEY_AUTHOR = "webTitle";
    public static final String JSON_KEY_FIELDS = "fields";
    public static final String JSON_KEY_THUMBNAIL = "thumbnail";

}
