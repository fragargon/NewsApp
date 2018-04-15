package com.example.android.newsapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.newsapp.adapter.NewsAdapter;
import com.example.android.newsapp.model.Constant;
import com.example.android.newsapp.model.News;
import com.example.android.newsapp.utils.QueryUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /** Adapter for the List of News */
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting the UI and set the layout recyclerView.
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a new adapter that takes an empty list of earthquakes as input
        newsAdapter = new NewsAdapter(this, new ArrayList<News>());

        // Setting adapter to recycler view.
        recyclerView.setAdapter(newsAdapter);

        // Start the AsyncTask to fetch the earthquake data
        NewsAsyncTask task = new NewsAsyncTask();
        task.execute(Constant.BASE_URL);
    }

    private class NewsAsyncTask extends AsyncTask<String, Void, List<News>>{

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param urls The parameters of the task.
         * @return A result, defined by the subclass of this task.
         */
        @Override
        protected List<News> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            List<News> result = QueryUtils.fetchNewsData(urls[0]);
            return result;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param data The result of the operation computed by {@link #doInBackground}.
         */
        @Override
        protected void onPostExecute(List<News> data) {
            // Clear the adapter of previous earthquake data
            newsAdapter.clearAll();

            // If there is a valid lis of {@link News}
            if(data != null || !data.isEmpty()) {
                newsAdapter.addAll(data);
            }
        }
    }
}
