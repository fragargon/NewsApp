package com.example.android.newsapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.newsapp.R;
import com.example.android.newsapp.model.News;

import java.util.List;

/**
 * this is a customer adapter to handle the recycler views
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    // Store all the news in list.
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
        News currentNews = newsList.get(position);
        holder.tvSection.setText(currentNews.getSectionName());
        holder.tvTitle.setText(currentNews.getWebTitle());
        holder.tvDate.setText(String.valueOf(currentNews.getWebPublicationDate()));
        holder.tvUrl.setText(currentNews.getWebUrl());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });
    }

    /**
     * Clear all data (a list of {@link News} object)
     */
    public void clearAll() {
        newsList.clear();
        notifyDataSetChanged();
    }

    /**
     *
     * @param news is the data source of the adapter.
     */
    public void addAll(List<News> news) {
        news.clear();
        news.addAll(newsList);
        notifyDataSetChanged();
    }
}
