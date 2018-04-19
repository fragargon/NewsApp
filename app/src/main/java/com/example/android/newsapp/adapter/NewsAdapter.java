package com.example.android.newsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.newsapp.R;
import com.example.android.newsapp.model.News;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * this is a customer adapter to handle the recycler views
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    // Store all the data news in list.
    private List<News> newsList;
    // This context we will use to inflate the layout.
    private Context context;

    /**
     *
     * @param context holds the application resources.
     * @param newsList is the list of the news object
     */
    public NewsAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    /**
     * This method returns a new instance of ViewHolder.
     * @param parent is the ViewGroup which the viewHolder will be inflate.
     * @param viewType is the layout.
     * @return the view.
     */
    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_news, parent,false);
        return new ViewHolder(view);
    }

    /**
     * This returns the size of the List.
     * @return the size of the array.
     */
    @Override
    public int getItemCount() {
        return newsList.size();
    }

    /**
     * Include the RecyclerView ViewHolder
     * Represents the data that is to be shown with the ViewHolder.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView tvSection;
        private TextView tvTitle;
        private TextView tvDate;
        private TextView tvUrl;

        /**
         * Represents the views of RecyclerView.
         * @param itemNews is the object wherein the views are.
         */
        private ViewHolder(View itemNews) {
            super(itemNews);
            cardView = itemNews.findViewById(R.id.item_card_view);
            tvSection = itemNews.findViewById(R.id.item_section);
            tvTitle = itemNews.findViewById(R.id.item_title);
            tvDate = itemNews.findViewById(R.id.item_date);
            tvUrl = itemNews.findViewById(R.id.item_url);
        }
    }

    /**
     * This method binds the data to the view holder.
     * @param holder handle the views
     * @param position is the position in the list view.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Find news at the given osition in the list
        final News currentNews = newsList.get(position);
        // Set section name to display.
        holder.tvSection.setText(currentNews.getSectionName());
        // Set title name to display.
        holder.tvTitle.setText(currentNews.getWebTitle());
        // Set the date to display.
        String date = currentNews.getWebPublicationDate();
        String newsDate = formatDate(date);
        holder.tvDate.setText(newsDate);
        //set the author's name to display.
        holder.tvUrl.setText(currentNews.getWebUrl());
        // set an OnclickListener on that view.
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickWebUrl(currentNews.getWebUrl());
            }
        });
    }

    /**
     * This method create an implicit web browser intent.
     * @param openUrl is the web url of the item click
     */
    private void onClickWebUrl(String openUrl) {
        Uri webPage = Uri.parse(openUrl);
        Intent i = new Intent(Intent.ACTION_VIEW, webPage);
        if(i.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(i);
        }
    }

    /**
     * This method format the date into a specific pattern.
     * @param dateObj is the web publication date.
     * @return a date formatted's string.
     */
    private String formatDate(String dateObj) {
        String dateFormatted = "";
        SimpleDateFormat inputDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        SimpleDateFormat outputDate = new SimpleDateFormat("EE dd MM YYYY", Locale.getDefault());
        try {
            Date newDate = inputDate.parse(dateObj);
            return outputDate.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormatted;
    }

    /**
     * Clear all data (a list of {@link News} object)
     */
    public void clearAll() {
        newsList.clear();
        notifyDataSetChanged();
    }

    /**
     * Update RecyclerView data
     * @param news is the data source of the adapter.
     */
    public void addAll(List<News> news) {
        newsList.clear();
        newsList.addAll(news);
        notifyDataSetChanged();
    }
}
