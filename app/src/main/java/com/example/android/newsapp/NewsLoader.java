package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.newsapp.model.News;
import com.example.android.newsapp.utils.QueryUtils;

import java.util.List;

/**
 * Loads a list of news by using an AsyncTask to perform the network request to the given URL.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    /* Tag for log messages */
    private static final String LOG_TAG = NewsLoader.class.getName();
    /* Query URL */
    private String queryUrl;

    /**
     *
     * @param context of the activity.
     * @param url to load data from.
     */
    public NewsLoader(Context context, String url) {
        super(context);
        queryUrl = url;
    }

    /**
     * Subclasses must implement this to take care of loading their data,
     * as per {@link #startLoading()}.  This is not called by clients directly,
     * but as a result of a call to {@link #startLoading()}.
     */
    @Override
    protected void onStartLoading() {
        // Trigger the loadInBackground() method to execute.
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<News> loadInBackground() {
        if(queryUrl== null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of news.
        List<News> newsData = QueryUtils.fetchNewsData(queryUrl);
        return newsData;
    }
}
