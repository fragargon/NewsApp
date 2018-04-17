package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.newsapp.adapter.EmptyRecyclerView;
import com.example.android.newsapp.adapter.NewsAdapter;
import com.example.android.newsapp.model.Constant;
import com.example.android.newsapp.model.News;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<News>>{

    /* Tag for the log messages */
    private static final String LOG_TAG = MainActivity.class.getName();

    /* Various initializer */
    private NewsAdapter newsAdapter;
    private EmptyRecyclerView recyclerView;
    private TextView tv1;
    private TextView tv2;
    private ImageView imageView;
    private View emptyView;
    private List<News> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Getting the UI */
        initViews();

        /* set empty view */
        recyclerView.setEmptyView(emptyView);

        /* set the layout recyclerView. */
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        /* Create a new adapter that takes an empty list of earthquakes as input */
        if (newsList == null) newsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(this, newsList);
        /* Setting adapter to recycler view. */
        recyclerView.setAdapter(newsAdapter);

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();
        // Initialize the loader. Pass in the int ID constant
        loaderManager.initLoader(Constant.NEWS_LOADER_ID, null, this);
    }

    private void initViews () {
        // casting the Views.
        recyclerView = findViewById(R.id.recycler_view);
        emptyView = findViewById(R.id.empty_view);
        tv1 = findViewById(R.id.text_view_empty1);
        tv2 = findViewById(R.id.text_view_empty2);
        imageView = findViewById(R.id.image_view_empty);

    }

    /**
     * Instantiate and return a new Loader for the given ID.
     */
    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        // Create a new loader for the given URL
        return new NewsLoader(this, Constant.BASE_URL);
    }

    /**
     * Called when a previously created loader has finished its load.
     */
    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        // Set empty state text to display "No news information found…"
        tv1.setText(R.string.no_data);
        tv2.setText(R.string.no_data_1);
        imageView.setImageResource(R.drawable.nodatafound);
        // Clear the adapter of previous news data.
        newsAdapter.clearAll();

        // If there is a valid list of {@link News}
        if((data != null) || !data.isEmpty()) {
            newsAdapter.addAll(data);
        }
    }

    /**
     * Called when a previously created loader is being reset,
     * and thus making its data unavailable.
     */
    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so clear out existing data.
        newsAdapter.clearAll();
    }
}
