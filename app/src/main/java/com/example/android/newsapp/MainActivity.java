package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.newsapp.adapter.EmptyRecyclerView;
import com.example.android.newsapp.adapter.NewsAdapter;
import com.example.android.newsapp.loader.NewsLoader;
import com.example.android.newsapp.model.Constant;
import com.example.android.newsapp.model.News;
import com.example.android.newsapp.utils.QueryUtils;

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
    private View loadingSpinner;
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
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        /* Create a new adapter that takes an empty list of earthquakes as input */
        if (newsList == null) newsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(this, newsList);
        /* Setting adapter to recycler view. */
        recyclerView.setAdapter(newsAdapter);

        // If there is a network connection, fetch data
        if (QueryUtils.isConnected(this)) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant
            loaderManager.initLoader(Constant.NEWS_LOADER_ID, null, this);
            // Otherwise, display error
        } else {
            upDateViewNoData();
        }
    }

    /**
     * This method castings the activity's views.
     */
    private void initViews () {
        recyclerView = findViewById(R.id.recycler_view);
        emptyView = findViewById(R.id.empty_view);
        tv1 = findViewById(R.id.text_view_empty1);
        tv2 = findViewById(R.id.text_view_empty2);
        imageView = findViewById(R.id.image_view_empty);
        loadingSpinner = findViewById(R.id.loading_spinner);

    }

    /**
     * This method update the views when no dat or
     * internet connection are available.
     */
    private void upDateViewNoData() {
        loadingSpinner.setVisibility(View.GONE);
        tv1.setText(R.string.no_data);
        imageView.setImageResource(R.drawable.nodatafound);
        // Check if connected is true, display text if no data.
        // else no connected to internet display text no connection.
        if(QueryUtils.isConnected(this)) {
            tv2.setText(R.string.no_data_1);
        } else {
            tv2.setText(R.string.no_internet_connection);
        }
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     */
    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        // getString retrieve a String value for the max News item
        String maxNews = sharedPreferences.getString(
                getString(R.string.settings_max_news_key),
                getString(R.string.settings_max_news_default));
        // getString retrieve a String value for Order-By item
        String orderBy = sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));

        // parse breaks apart the URI string
        Uri baseUri = Uri.parse(Constant.BASE_URL);
        // buildUpon prepares the base Uri
        Uri.Builder builder = baseUri.buildUpon();
        // append query parameter
        builder.appendQueryParameter(Constant.KEY_SHOW_TAGS, Constant.KEY_CONTRIBUTOR);
        builder.appendQueryParameter(Constant.KEY_ORDER_BY, orderBy);
        builder.appendQueryParameter(Constant.KEY_SHOW_FIELD, Constant.KEY_ALL);
        builder.appendQueryParameter(Constant.KEY_PAGE_SIZE, maxNews);
        builder.appendQueryParameter(Constant.API_KEY, Constant.KEY_TEST);

        // Create a new loader for the given URL
        return new NewsLoader(this, builder.toString());
    }

    /**
     * Called when a previously created loader has finished its load.
     */
    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {

        // Clear the adapter of previous news data.
        newsAdapter.clearAll();

        // If there is a valid list of {@link News}
        if ((data != null) && !data.isEmpty()) {
            newsAdapter.addAll(data);
        } else { // set empty state
            upDateViewNoData();
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

    /**
     * @param menu The options menu which display the menu item.
     * @return true for the menu to be displayed;
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    /**
     * @param item The menu item that was selected.
     * @return boolean Return true to allow
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
