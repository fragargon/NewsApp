package com.example.android.newsapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.example.android.newsapp.model.Constant;
import com.example.android.newsapp.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * helper methods to fetch data from Guardian api.
 */

public class QueryUtils {

    /* Tag for the log messages */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /** Create a private constructor because no one should ever create a {@link QueryUtils} object. */
    private QueryUtils() {
    }

    /**
     * Ths method (function) for checking internet connection.
     * @param context of the app.
     * @return true if internet connection is present else return false
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static List<News> fetchNewsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // TODO remove test spinnerLoading delayed HttpRequest (slow connection)
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Perform HTTP request to the URL and receive a JSON response.
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response.
        List<News> newsList;
        newsList = extractResponseFromJson(jsonResponse);
        // Return the list.
        return newsList;
    }

    /**
     *
     * @param requestUrl is the given string url.
     * @return the url object.
     */
    private static URL createUrl(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL", e);
        }
        return url;
    }

    private static String makeHttpRequest (URL url) throws IOException {
        String jsonResponse = "";

        // if URL is null, return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(Constant.READ_TIMEOUT); // 10000 millisecond.
            urlConnection.setConnectTimeout(Constant.CONNECT_TIMEOUT); // 15000 millisecond.
            urlConnection.setRequestMethod(Constant.REQUEST_METHOD); // GET method.
            urlConnection.connect();

            // If the request is successful (code 200)
            // Read the input stream and parse the response.
            if(urlConnection.getResponseCode() == Constant.RESPONSE_CODE /*200*/) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readStreamResponse(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the News JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     *
     * @param inputStream is the JSON stream bytes.
     * @return the JSON stream to string.
     * @throws IOException Problem retrieving the News JSON results.
     */
    private static String readStreamResponse(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<News> extractResponseFromJson(String newsJson) {
        // if the JSON string is empty or null then return early.
        if(TextUtils.isEmpty(newsJson)) {
            return null;
        }
        // create an empty ArrayList that we can start adding news to
        List<News> newsList = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        try {
            // Create a JSONObject from the JSON response string.
            JSONObject baseJsonResponse = new JSONObject(newsJson);
            // Extract the JSONObject associated with the key string "response"
            JSONObject responseJsonObject = baseJsonResponse.getJSONObject(Constant.JSON_KEY_RESPONSE);
            // Extract the JSONArray associated with the key string "results"
            JSONArray resultsArray = responseJsonObject.getJSONArray(Constant.JSON_KEY_RESULTS);

            /* For each element in the resultArray, create a {@Link News} object */
            for (int i = 0; i < resultsArray.length(); i++) {
                // Get a single newsList at index i
                JSONObject currentNews = resultsArray.getJSONObject(i);
                // Extract the value for the key called "webTitle"
                String webTitle = currentNews.getString(Constant.JSON_KEY_WEB_TITLE);
                // Extract the value for the key called "sectionName"
                String sectionName = currentNews.getString(Constant.JSON_KEY_SECTION_NAME);
                // Extract the value for the key called "webPublicationDate"
                String webPublicationDate = currentNews.getString(Constant.JSON_KEY_WEB_PUBLICATION_DATE);
                // Extract the value for the key called "webUrl"
                String webUrl = currentNews.getString(Constant.JSON_KEY_WEB_URL);

                // Create a new {@Link News} object
                News news = new News(sectionName, webTitle,webPublicationDate, webUrl);
                newsList.add(news);
            }
        } catch (JSONException e) {
            // catch the exception here, so the app doesn't crash. Print a log message.
            Log.e(LOG_TAG, "Problem parsing the news JSON results", e);
        }
        // Return the newsList
        return newsList;
    }

}
